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
import org.eclipsescout.contacts.shared.CompanyFormData;
import org.eclipsescout.contacts.shared.CreateCompanyPermission;
import org.eclipsescout.contacts.shared.ReadCompanyPermission;
import org.eclipsescout.contacts.shared.UpdateCompanyPermission;
import org.eclipsescout.contacts.shared.services.ICompanyService;

/**
 * @author mzi
 */
public class CompanyService extends AbstractService implements ICompanyService {

  @Override
  public CompanyFormData create(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (StringUtility.isNullOrEmpty(formData.getCompanyId())) {
      formData.setCompanyId(UUID.randomUUID().toString());
    }

    SQL.insert(TEXTS.get("SqlCompanyInsert"), formData);

    return store(formData);
  }

  @Override
  public CompanyFormData load(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto(TEXTS.get("SqlCompanySelect"), formData);

    return formData;
  }

  @Override
  public CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    return formData;
  }

  @Override
  public CompanyFormData store(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update(TEXTS.get("SqlCompanyUpdate"), formData);

    return formData;
  }
}
