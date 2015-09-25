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
package org.eclipse.scout.contacts.server.contact;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.contact.ContactFormData;
import org.eclipse.scout.contacts.shared.contact.ContactsSearchFormData;
import org.eclipse.scout.contacts.shared.contact.ContactsTablePageData;
import org.eclipse.scout.contacts.shared.contact.CreateContactPermission;
import org.eclipse.scout.contacts.shared.contact.IContactService;
import org.eclipse.scout.contacts.shared.contact.ReadContactPermission;
import org.eclipse.scout.contacts.shared.contact.UpdateContactPermission;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class ContactService implements IContactService {

  @Override
  public ContactsTablePageData getContactsTableData(SearchFilter filter, String pageCompanyId) throws ProcessingException {
    ContactsTablePageData pageData = new ContactsTablePageData();
    ContactsSearchFormData searchData = (ContactsSearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder(SQLs.CONTACT_PAGE_SELECT);
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (searchData != null) {
      addToWhere(sqlWhere, searchData.getFirstName().getValue(), "first_name", "firstName");
      addToWhere(sqlWhere, searchData.getLastName().getValue(), "last_name", "lastName");
      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
      addToWhere(sqlWhere, searchData.getCompany().getValue(), "company_id", "company");
      addToWhere(sqlWhere, pageCompanyId, "company_id", "pageCompanyId");
    }

    String sql = sqlSelect.append(sqlWhere).append(SQLs.CONTACT_PAGE_DATA_SELECT_INTO).toString();

    SQL.selectInto(sql, searchData, new NVPair("pageCompanyId", pageCompanyId), new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public ContactFormData create(ContactFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateContactPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getContactId())) {
      formData.setContactId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.CONTACT_INSERT, formData);

    return store(formData);
  }

  @Override
  public ContactFormData load(ContactFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadContactPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.CONTACT_SELECT, formData);

    return formData;
  }

  @Override
  public ContactFormData store(ContactFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateContactPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.CONTACT_UPDATE, formData);

    return formData;
  }

  protected void addToWhere(StringBuilder sqlWhere, String fieldValue, String sqlAttribute, String searchAttribute) {
    if (StringUtility.hasText(fieldValue)) {
      sqlWhere.append(String.format(SQLs.AND_LIKE_CAUSE, sqlAttribute, searchAttribute, "%"));
    }
  }
}
