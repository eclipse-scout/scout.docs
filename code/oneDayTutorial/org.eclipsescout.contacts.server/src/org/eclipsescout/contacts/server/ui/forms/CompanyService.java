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
import org.eclipsescout.contacts.shared.ui.forms.CompanyFormData;
import org.eclipsescout.contacts.shared.ui.forms.CreateCompanyPermission;
import org.eclipsescout.contacts.shared.ui.forms.ICompanyService;
import org.eclipsescout.contacts.shared.ui.forms.ReadCompanyPermission;
import org.eclipsescout.contacts.shared.ui.forms.UpdateCompanyPermission;

/**
 * @author mzi
 */
public class CompanyService extends AbstractService implements ICompanyService {

  @Override
  public CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mzi] business logic here.
    return formData;
  }

  @Override
  public CompanyFormData create(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    if (StringUtility.isNullOrEmpty(formData.getCompanyId())) {
      formData.setCompanyId(UUID.randomUUID().toString());
    }

    SQL.insert("INSERT INTO COMPANY (company_id) "
        + "SELECT :companyId "
        + "FROM COMPANY "
        + "WHERE company_id = :companyId "
        + "HAVING count(*) = 0",
        formData);

    return store(formData);
  }

  @Override
  public CompanyFormData load(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto("SELECT "
        + "name, "
        + "location, "
        + "url "
        + "FROM COMPANY "
        + "WHERE company_id = :companyId "
        + "INTO "
        + ":name, "
        + ":location, "
        + ":URL",
        formData);

    return formData;
  }

  @Override
  public CompanyFormData store(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update("UPDATE COMPANY SET "
        + "name = :name, "
        + "location = :location, "
        + "url = :URL "
        + "WHERE company_id = :companyId ",
        formData);

    return formData;
  }
}
