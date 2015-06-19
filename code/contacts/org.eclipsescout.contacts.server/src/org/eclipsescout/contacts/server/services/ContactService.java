/**
 *
 */
package org.eclipsescout.contacts.server.services;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.ContactFormData;
import org.eclipsescout.contacts.shared.CreateContactPermission;
import org.eclipsescout.contacts.shared.ReadContactPermission;
import org.eclipsescout.contacts.shared.UpdateContactPermission;
import org.eclipsescout.contacts.shared.services.IContactService;

/**
 * @author mzi
 */
public class ContactService extends AbstractService implements IContactService {

  @Override
  public ContactFormData create(ContactFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateContactPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (StringUtility.isNullOrEmpty(formData.getContactId())) {
      formData.setContactId(UUID.randomUUID().toString());
    }

    SQL.insert(TEXTS.get("SqlContactInsert"), formData);

    return store(formData);
  }

  @Override
  public ContactFormData load(ContactFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadContactPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto(TEXTS.get("SqlContactSelect"), formData);

    return formData;
  }

  @Override
  public ContactFormData store(ContactFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateContactPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update(TEXTS.get("SqlContactUpdate"), formData);

    return formData;
  }
}
