/**
 *
 */
package org.eclipsescout.contacts.server.services;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.server.ServerSession;
import org.eclipsescout.contacts.shared.services.ILinkedInService;
import org.eclipsescout.contacts.shared.ui.forms.IPersonService;
import org.eclipsescout.contacts.shared.ui.forms.PersonFormData;
import org.osgi.framework.ServiceRegistration;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author mzi
 */
public class LinkedInService extends AbstractService implements ILinkedInService {
  private final static String LINKEDIN_TOKEN = "linkedin_access_token";
  private final static String LINKEDIN_SECRET = "linkedin_access_secret";
  private final static String LINKEDIN_CONNECTIONS = "http://api.linkedin.com/v1/people/~/connections";
  private static IScoutLogger LOG = ScoutLogManager.getLogger(LinkedInService.class);
  private OAuthService m_service = null;
  private Token m_requestToken = null;
  private Token m_accessToken = null;

  @Override
  public void initializeService(ServiceRegistration registration) {
    super.initializeService(registration);

    m_service = new ServiceBuilder()
        .provider(LinkedInApi.withScopes("r_network"))
        .apiKey("CiEgwWDkA5BFpNrc0RfGyVuSlOh4tig5kOTZ9q97qcXNrFl7zqk-Ts7DqRGaKDCV")
        .apiSecret("dhho4dfoCmiQXrkw4yslork5XWLFnPSuMR-8gscPVjY4jqFFHPYWJKgpFl4uLTM6")
        .build();
  }

  @Override
  public String getAuthUrl() throws ProcessingException {
    m_requestToken = m_service.getRequestToken();
    String authLink = m_service.getAuthorizationUrl(m_requestToken);

    return authLink;
  }

  @Override
  public void refreshToken(String securityCode) throws ProcessingException {
    // turn request token into access token
    m_accessToken = m_service.getAccessToken(m_requestToken, new Verifier(securityCode));

    // make sure current user has param records for linkedin token
    String userName = ServerSession.get().getUserId();
    SQL.insert("INSERT INTO USERS_PARAM (username, param) ("
        + "SELECT :username as username, :param as param "
        + "FROM USERS_PARAM "
        + "WHERE username = :username AND param = :param HAVING count(*) = 0"
        + ")",
        new NVPair("username", userName), new NVPair("param", LINKEDIN_TOKEN));

    SQL.insert("INSERT INTO USERS_PARAM (username, param) ("
        + "SELECT :username as username, :param as param "
        + "FROM USERS_PARAM "
        + "WHERE username = :username AND param = :param HAVING count(*) = 0"
        + ")",
        new NVPair("username", userName), new NVPair("param", LINKEDIN_SECRET));

    // update param records with new access token
    SQL.update("UPDATE USERS_PARAM set value = :value "
        + "WHERE param = :param AND username = :username",
        new NVPair("value", m_accessToken.getToken()),
        new NVPair("username", userName), new NVPair("param", LINKEDIN_TOKEN));

    SQL.update("UPDATE USERS_PARAM set value = :value "
        + "WHERE param = :param AND username = :username",
        new NVPair("value", m_accessToken.getSecret()),
        new NVPair("username", userName), new NVPair("param", LINKEDIN_SECRET));
  }

  @Override
  public void updateContacts() throws ProcessingException {
    try {
      IPersonService service = SERVICES.getService(IPersonService.class);
      NodeList persons = readContacts();

      for (int i = 0; i < persons.getLength(); i++) {
        if (persons.item(i) instanceof Element) {
          Element person = (Element) persons.item(i);
          LOG.info(DomUtility.getString(person));

          // load existing person data
          PersonFormData formData = new PersonFormData();
          formData.setPersonId(DomUtility.getValue(person, "id"));
          service.load(formData);

          formData.getPictureURL().setValue(
              DomUtility.getValue(person, "picture-url"));
          formData.getFirstName().setValue(
              DomUtility.getValue(person, "first-name"));
          formData.getLastName().setValue(
              DomUtility.getValue(person, "last-name"));
          formData.getHeadline().setValue(
              StringUtility.substring(DomUtility.getValue(person, "headline"), 0, 64));
          formData.getLocation().setValue(
              DomUtility.getValue(person, "location", "name"));

          // save new/updated person data
          service.create(formData);
        }
      }
    }
    catch (Exception e) {
      throw new ProcessingException("LinkedIn Error", e);
    }
  }

  private NodeList readContacts() throws ProcessingException {
    // check if we need to load the access token
    if (m_accessToken == null) {
      m_accessToken = getToken();
    }

    // create singned linkedin request and get response
    OAuthRequest request = new OAuthRequest(Verb.GET, LINKEDIN_CONNECTIONS);
    m_service.signRequest(m_accessToken, request);
    Response response = request.send();

    // parse linkedin response stream
    Element element = null;

    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = builder.parse(response.getStream());
      element = document.getDocumentElement();
    }
    catch (Exception e) {
      throw new ProcessingException(e.getMessage(), e);
    }

    // basic error handling
    if (element.getNodeName().equals("error")) {
      LOG.error(DomUtility.getString(element));
      throw new ProcessingException(DomUtility.getString(element));
    }

    return element.getChildNodes();
  }

  private Token getToken() throws ProcessingException {
    String userName = ServerSession.get().getUserId();
    StringHolder value = new StringHolder();
    StringHolder secret = new StringHolder();

    SQL.selectInto("SELECT value INTO :value FROM USERS_PARAM "
        + "WHERE param = :param AND username = :username",
        new NVPair("value", value),
        new NVPair("param", LINKEDIN_TOKEN),
        new NVPair("username", userName));

    SQL.selectInto("SELECT value INTO :secret FROM USERS_PARAM "
        + "WHERE param = :param AND username = :username",
        new NVPair("secret", secret),
        new NVPair("param", LINKEDIN_SECRET),
        new NVPair("username", userName));

    if (StringUtility.isNullOrEmpty(value.getValue())) {
      String message = "No valid LinkedIn token stored for user "
          + "'" + userName + "'. Please (re)create a token";
      LOG.error(message);

      throw new ProcessingException(message);
    }

    return new Token(value.getValue(), secret.getValue());
  }
}
