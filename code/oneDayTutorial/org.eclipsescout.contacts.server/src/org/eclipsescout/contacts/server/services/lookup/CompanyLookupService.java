package org.eclipsescout.contacts.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;
import org.eclipsescout.contacts.shared.services.lookup.ICompanyLookupService;

public class CompanyLookupService extends AbstractSqlLookupService<String> implements ICompanyLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT "
        + "company_id, "
        + "name "
        + "FROM COMPANY "
        + "WHERE 1=1 "
        + "<key> AND company_id = :key </key> "
        + "<text> AND UPPER(name) LIKE UPPER(:text) </text> "
        + "<all> </all> ";
  }
}
