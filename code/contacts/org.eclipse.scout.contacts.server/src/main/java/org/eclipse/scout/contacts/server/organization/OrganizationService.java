/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.organization;

import java.util.UUID;

import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.organization.CreateOrganizationPermission;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.organization.OrganizationTablePageData;
import org.eclipse.scout.contacts.shared.organization.ReadOrganizationPermission;
import org.eclipse.scout.contacts.shared.organization.UpdateOrganizationPermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

// tag::getTableData[]
// tag::all[]
public class OrganizationService implements IOrganizationService {
  // end::all[]

  @Override
  public OrganizationTablePageData getOrganizationTableData(SearchFilter filter) {
    OrganizationTablePageData pageData = new OrganizationTablePageData();
    // end::getTableData[]
    // tag::allOrgs[]

    String sql = SQLs.ORGANIZATION_PAGE_SELECT + SQLs.ORGANIZATION_PAGE_DATA_SELECT_INTO; // <1>
    SQL.selectInto(sql, new NVPair("page", pageData)); // <2>

    // end::allOrgs[]
    // tag::getTableData[]
    return pageData;
  }
  // end::getTableData[]
  // tag::all[]

  @Override
  public OrganizationFormData create(OrganizationFormData formData) {
    if (!ACCESS.check(new CreateOrganizationPermission())) {
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
    if (!ACCESS.check(new ReadOrganizationPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.ORGANIZATION_SELECT, formData);

    return formData;
  }

  @Override
  public OrganizationFormData store(OrganizationFormData formData) {
    if (!ACCESS.check(new UpdateOrganizationPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.ORGANIZATION_UPDATE, formData);

    return formData;
  }
  //tag::getTableData[]
}
//end::getTableData[]
//end::all[]
