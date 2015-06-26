package org.eclipsescout.contacts.server.services;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.services.IStandardOutlineService;

public class StandardOutlineService extends AbstractService implements IStandardOutlineService {

  @Override
  public Object[][] getPersonTableData(String companyId) throws ProcessingException {
    String stmt = "SELECT person_id, first_name, last_name, headline FROM PERSON";

    if (StringUtility.isNullOrEmpty(companyId)) {
      return SQL.select(stmt);
    }

    return SQL.select(stmt + " WHERE company_id = :companyId",
        new NVPair("companyId", companyId));
  }

  @Override
  public Object[][] getCompanyTableData() throws ProcessingException {
    return SQL.select("SELECT company_id, name, location FROM COMPANY");
  }
}
