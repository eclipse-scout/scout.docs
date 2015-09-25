/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.company;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.company.CompanyFormData;
import org.eclipse.scout.contacts.shared.company.CompanySearchFormData;
import org.eclipse.scout.contacts.shared.company.CompanyTablePageData;
import org.eclipse.scout.contacts.shared.company.CreateCompanyPermission;
import org.eclipse.scout.contacts.shared.company.ICompanyService;
import org.eclipse.scout.contacts.shared.company.ReadCompanyPermission;
import org.eclipse.scout.contacts.shared.company.UpdateCompanyPermission;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class CompanyService implements ICompanyService {

  @Override
  public CompanyTablePageData getCompanyTableData(SearchFilter filter) throws ProcessingException {
    CompanyTablePageData pageData = new CompanyTablePageData();
    CompanySearchFormData searchData = (CompanySearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder(SQLs.COMPANY_PAGE_SELECT);
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (searchData != null) {
      addToWhere(sqlWhere, searchData.getName().getValue(), "name", "name");
      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sqlWhere, searchData.getHomepage().getValue(), "url", "homepage");
    }

    String sql = sqlSelect.append(sqlWhere).append(SQLs.COMPANY_PAGE_DATA_SELECT_INTO).toString();

    SQL.selectInto(sql, searchData, new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public CompanyFormData create(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getCompanyId())) {
      formData.setCompanyId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.COMPANY_INSERT, formData);

    return store(formData);
  }

  @Override
  public CompanyFormData load(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadCompanyPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.COMPANY_SELECT, formData);

    return formData;
  }

  @Override
  public CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    return formData;
  }

  @Override
  public CompanyFormData store(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateCompanyPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.COMPANY_UPDATE, formData);

    return formData;
  }

  protected void addToWhere(StringBuilder sqlWhere, String fieldValue, String sqlAttribute, String searchAttribute) {
    if (StringUtility.hasText(fieldValue)) {
      sqlWhere.append(String.format(SQLs.AND_LIKE_CAUSE, sqlAttribute, searchAttribute, "%"));
    }
  }
}
