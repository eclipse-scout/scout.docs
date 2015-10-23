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
package org.eclipse.scout.contacts.server.person;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonCreatePermission;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.contacts.shared.person.PersonReadPermission;
import org.eclipse.scout.contacts.shared.person.PersonSearchFormData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.contacts.shared.person.PersonUpdatePermission;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class PersonService implements IPersonService {

  @Override
  public PersonTablePageData getTableData(SearchFilter filter, String organizationId) {
    PersonTablePageData pageData = new PersonTablePageData();
    PersonSearchFormData searchData = (PersonSearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder(SQLs.PERSON_PAGE_SELECT);
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (searchData != null) {
      addToWhere(sqlWhere, searchData.getFirstName().getValue(), "first_name", "firstName");
      addToWhere(sqlWhere, searchData.getLastName().getValue(), "last_name", "lastName");
      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sqlWhere, searchData.getOrganization().getValue(), "organization_id", "organization");
      addToWhere(sqlWhere, organizationId, "organization_id", "organizationId");
    }

    String sql = sqlSelect.append(sqlWhere).append(SQLs.PERSON_PAGE_DATA_SELECT_INTO).toString();

    SQL.selectInto(sql, searchData, new NVPair("organizationId", organizationId), new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public PersonFormData create(PersonFormData formData) {
    if (!ACCESS.check(new PersonCreatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getPersonId())) {
      formData.setPersonId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.PERSON_INSERT, formData);

    return store(formData);
  }

  @Override
  public PersonFormData load(PersonFormData formData) {
    if (!ACCESS.check(new PersonReadPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.PERSON_SELECT, formData);

    return formData;
  }

  @Override
  public PersonFormData store(PersonFormData formData) {
    if (!ACCESS.check(new PersonUpdatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.PERSON_UPDATE, formData);

    return formData;
  }

  protected void addToWhere(StringBuilder sqlWhere, String fieldValue, String sqlAttribute, String searchAttribute) {
    if (StringUtility.hasText(fieldValue)) {
      sqlWhere.append(String.format(SQLs.AND_LIKE_CAUSE, sqlAttribute, searchAttribute, "%"));
    }
  }
}
