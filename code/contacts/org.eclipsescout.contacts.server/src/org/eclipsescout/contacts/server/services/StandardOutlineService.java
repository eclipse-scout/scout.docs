/**
 *
 */
package org.eclipsescout.contacts.server.services;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.services.IStandardOutlineService;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.CompanySearchFormData;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.CompanyTablePageData;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.ContactsSearchFormData;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.ContactsTablePageData;

/**
 * @author mzi
 */
public class StandardOutlineService extends AbstractService implements IStandardOutlineService {

  @Override
  public ContactsTablePageData getContactsTableData(SearchFilter filter, String pageCompanyId) throws ProcessingException {
    ContactsTablePageData pageData = new ContactsTablePageData();
    ContactsSearchFormData searchData = (ContactsSearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder(TEXTS.get("SqlContactPageDataSelect"));
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (searchData != null) {
      addToWhere(sqlWhere, searchData.getFirstName().getValue(), "first_name", "firstName");
      addToWhere(sqlWhere, searchData.getLastName().getValue(), "last_name", "lastName");
      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sqlWhere, searchData.getCompany().getValue(), "company_id", "company");
      addToWhere(sqlWhere, pageCompanyId, "company_id", "pageCompanyId");
    }

    String sqlInto = TEXTS.get("SqlContactPageDataSelectInto");
    String sql = sqlSelect.append(sqlWhere).append(sqlInto).toString();

    SQL.selectInto(sql, searchData, new NVPair("pageCompanyId", pageCompanyId), new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public CompanyTablePageData getCompanyTableData(SearchFilter filter) throws ProcessingException {
    CompanyTablePageData pageData = new CompanyTablePageData();
    CompanySearchFormData searchData = (CompanySearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder(TEXTS.get("SqlCompanyPageDataSelect"));
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (searchData != null) {
      addToWhere(sqlWhere, searchData.getName().getValue(), "name", "name");
      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sqlWhere, searchData.getHomepage().getValue(), "url", "homepage");
    }

    String sqlInto = TEXTS.get("SqlCompanyPageDataSelectInto");
    String sql = sqlSelect.append(sqlWhere).append(sqlInto).toString();

    SQL.selectInto(sql, searchData, new NVPair("page", pageData));

    return pageData;
  }

  private void addToWhere(StringBuilder sqlWhere, String fieldValue, String sqlAttribute, String searchAttribute) {
    if (StringUtility.hasText(fieldValue)) {
      sqlWhere.append(TEXTS.get("SqlAndUpperLikeUpper", sqlAttribute, searchAttribute));
    }
  }
}
