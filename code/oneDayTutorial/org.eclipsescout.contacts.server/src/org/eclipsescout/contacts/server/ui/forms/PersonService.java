/**
 *
 */
package org.eclipsescout.contacts.server.ui.forms;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.ui.forms.CreatePersonPermission;
import org.eclipsescout.contacts.shared.ui.forms.IPersonService;
import org.eclipsescout.contacts.shared.ui.forms.PersonFormData;
import org.eclipsescout.contacts.shared.ui.forms.ReadPersonPermission;
import org.eclipsescout.contacts.shared.ui.forms.UpdatePersonPermission;

/**
 * @author mzi
 */
public class PersonService extends AbstractService implements IPersonService {

  @Override
  public PersonFormData prepareCreate(PersonFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreatePersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mzi] business logic here.
    return formData;
  }

  @Override
  public PersonFormData create(PersonFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreatePersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (StringUtility.isNullOrEmpty(formData.getPersonId())) {
      formData.setPersonId(UUID.randomUUID().toString());
    }

    SQL.insert("INSERT INTO PERSON (person_id) "
        + "SELECT :personId "
        + "FROM PERSON "
        + "WHERE person_id = :personId "
        + "HAVING count(*) = 0",
        formData);

    return store(formData);
  }

  @Override
  public PersonFormData load(PersonFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadPersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto("SELECT "
        + "picture_url, "
        + "first_name, "
        + "last_name, "
        + "company_id, "
        + "headline, "
        + "location, "
        + "date_of_birth "
        + "FROM PERSON "
        + "WHERE PERSON_ID = :personId "
        + "INTO "
        + ":pictureURL, "
        + ":firstName, "
        + ":lastName, "
        + ":company, "
        + ":headline, "
        + ":location, "
        + ":dateOfBirth",
        formData);

    return formData;
  }

  @Override
  public PersonFormData store(PersonFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdatePersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update("UPDATE PERSON SET "
        + "picture_url = :pictureURL, "
        + "first_name = :firstName, "
        + "last_name = :lastName, "
        + "headline = :headline, "
        + "company_id = :company, "
        + "location = :location, "
        + "date_of_birth = :dateOfBirth "
        + "WHERE person_id = :personId ",
        formData);

    return formData;
  }
}
