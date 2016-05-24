/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.organization;

import java.util.UUID;

import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationCreatePermission;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.organization.OrganizationReadPermission;
import org.eclipse.scout.contacts.shared.organization.OrganizationSearchFormData;
import org.eclipse.scout.contacts.shared.organization.OrganizationPageData;
import org.eclipse.scout.contacts.shared.organization.OrganizationUpdatePermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class OrganizationService implements IOrganizationService {

  @Override
  public OrganizationPageData getTableData(SearchFilter filter) {
    OrganizationPageData pageData = new OrganizationPageData();
    OrganizationSearchFormData searchData = (OrganizationSearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder(SQLs.ORGANIZATION_PAGE_SELECT);
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (searchData != null) {
      addToWhere(sqlWhere, searchData.getName().getValue(), "name", "name");
      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sqlWhere, searchData.getHomepage().getValue(), "url", "homepage");
    }

    String sql = sqlSelect.append(sqlWhere).append(SQLs.ORGANIZATION_PAGE_DATA_SELECT_INTO).toString();

    SQL.selectInto(sql, searchData, new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public OrganizationFormData create(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationCreatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getOrganizationId())) {
      formData.setOrganizationId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.ORGANIZATION_INSERT, formData);

    return store(formData);
  }

  @Override
  public OrganizationFormData load(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationReadPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.ORGANIZATION_SELECT, formData);

    return formData;
  }

  @Override
  public OrganizationFormData prepareCreate(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationCreatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    return formData;
  }

  @Override
  public OrganizationFormData store(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationUpdatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.ORGANIZATION_UPDATE, formData);

    return formData;
  }

  protected void addToWhere(StringBuilder sqlWhere, String fieldValue, String sqlAttribute, String searchAttribute) {
    if (StringUtility.hasText(fieldValue)) {
      sqlWhere.append(String.format(SQLs.AND_LIKE_CAUSE, sqlAttribute, searchAttribute, "%"));
    }
  }
}
