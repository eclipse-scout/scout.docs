package org.eclipse.scout.contacts.ui.html;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.scout.commons.SecurityUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.ServletFilterHelper;

@Bean
public class FormAuthenticator {

  private ServletFilterHelper m_servletFilterHelper;

  public void init(FilterConfig filterConfig) {
    m_servletFilterHelper = BEANS.get(ServletFilterHelper.class);
  }

  public void destroy() {
    m_servletFilterHelper = null;
  }

  /**
   * @return true if the request was handled (caller returns), false if nothing was done (caller continues)
   */
  public boolean handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    resp.setHeader("Cache-Control", "no-cache");
    resp.setHeader("Pragma", "no-cache");
    resp.setDateHeader("Expires", 0);

    String[] cred = readCredentials(req);
    if (cred == null || cred.length != 2 || StringUtility.isNullOrEmpty(cred[0]) || StringUtility.isNullOrEmpty(cred[1])) {
      return forbidden();
    }

    Principal principal = checkCredentials(cred);
    if (principal == null) {
      return forbidden();
    }

    // force a new http session
    HttpSession session = req.getSession(false);
    if (session != null) {
      session.invalidate();
    }

    m_servletFilterHelper.putPrincipalOnSession(req, principal);
    return true;
  }

  protected Principal checkCredentials(String[] cred) throws ServletException {
    String username = cred[0];
    String password = cred[1];
    if (isValidUser(username, password)) {
      return new SimplePrincipal(username);
    }
    return null;
  }

  protected boolean isValidUser(String username, String passwordClearText) throws ServletException {
    // TODO [dwi]: Implement Credential validation strategy (LDAP, DataSource, etc)
    // When using DataSource strategies, ensure to store only safe hashes (probably use org.eclipse.scout.commons.SecurityUtility).

    final class CredEntry {
      final String m_username;
      final byte[] m_password; // Base64 Hash
      final byte[] salt;

      public CredEntry(String usr, String clearTextPwd) throws ProcessingException {
        this.salt = SecurityUtility.createRandomBytes();
        m_username = usr;
        m_password = SecurityUtility.hash(clearTextPwd.getBytes(StandardCharsets.UTF_8), salt);
      }
    }

    try {
      List<CredEntry> acceptedCredentials = Arrays.asList(new CredEntry("admin", "admin"), new CredEntry("scott", "tiger"));
      for (CredEntry userEntry : acceptedCredentials) {
        if (username.equalsIgnoreCase(userEntry.m_username)) {
          byte[] providedPwdHash = SecurityUtility.hash(passwordClearText.getBytes(StandardCharsets.UTF_8), userEntry.salt);
          return Arrays.equals(providedPwdHash, userEntry.m_password);
        }
      }
      return false;
    }
    catch (Exception e) {
      throw new ServletException(e);
    }
  }

  protected boolean forbidden() {
    try {
      Thread.sleep(500L);
    }
    catch (InterruptedException e) {
      //nop
    }
    return false;
  }

  protected String[] readCredentials(HttpServletRequest req) {
    String[] cred = m_servletFilterHelper.parseBasicAuthRequest(req);
    if (cred != null) {
      return cred;
    }
    String user = req.getParameter("user");
    if (user != null) {
      return new String[]{user, req.getParameter("password")};
    }
    return null;
  }
}
