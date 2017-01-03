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
package org.eclipse.scout.contacts.server.person;

import java.util.UUID;

import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.CreatePersonPermission;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.contacts.shared.person.ReadPersonPermission;
import org.eclipse.scout.contacts.shared.person.PersonSearchFormData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.contacts.shared.person.UpdatePersonPermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

//tag::all[]
//tag::getTableData[]
public class PersonService implements IPersonService {

  //end::all[]
  @Override
  public PersonTablePageData getPersonTableData(SearchFilter filter, String organizationId) {
    PersonTablePageData pageData = new PersonTablePageData();
    PersonSearchFormData searchData = (PersonSearchFormData) filter.getFormData();
    StringBuilder sql = new StringBuilder();

    sql.append(SQLs.PERSON_PAGE_SELECT);
    // end::getTableData[]
    // tag::addOrganizationCriteria[]
    sql.append(" WHERE 1 = 1 ");
    addToWhere(sql, organizationId, "organization_id", "organizationId");
    // end::addOrganizationCriteria[]

    if (searchData != null) {
      addToWhere(sql, searchData.getFirstName().getValue(), "first_name", "firstName");
      addToWhere(sql, searchData.getLastName().getValue(), "last_name", "lastName");
      addToWhere(sql, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sql, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sql, searchData.getOrganization().getValue(), "organization_id", "organization");
    }

    // tag::getTableData[]
    sql.append(SQLs.PERSON_PAGE_DATA_SELECT_INTO);

    SQL.selectInto(sql.toString(), searchData, new NVPair("organizationId", organizationId), new NVPair("page", pageData));

    return pageData;
  }
  // end::getTableData[]
  // tag::addOrganizationCriteria[]

  protected void addToWhere(StringBuilder sqlWhere, String fieldValue, String sqlAttribute, String searchAttribute) {
    if (StringUtility.hasText(fieldValue)) {
      sqlWhere.append(String.format(SQLs.AND_LIKE_CAUSE, sqlAttribute, searchAttribute));
    }
  }
  // end::addOrganizationCriteria[]

  //tag::all[]
  @Override
  public PersonFormData create(PersonFormData formData) {
    if (!ACCESS.check(new CreatePersonPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    // add a unique person id if necessary
    if (StringUtility.isNullOrEmpty(formData.getPersonId())) {
      formData.setPersonId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.PERSON_INSERT, formData); // <1>

    return store(formData); // <2>
  }

  @Override
  public PersonFormData load(PersonFormData formData) {
    if (!ACCESS.check(new ReadPersonPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.PERSON_SELECT, formData); // <3>

    return formData;
  }

  @Override
  public PersonFormData store(PersonFormData formData) {
    if (!ACCESS.check(new UpdatePersonPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.PERSON_UPDATE, formData); // <4>

    return formData;
  }
  // tag::getTableData[]
}
// end::all[]
// end::getTableData[]
