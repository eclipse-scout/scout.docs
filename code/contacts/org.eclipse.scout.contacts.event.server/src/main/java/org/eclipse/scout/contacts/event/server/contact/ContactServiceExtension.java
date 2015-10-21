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
package org.eclipse.scout.contacts.event.server.contact;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.commons.annotations.Replace;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.BeanArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.contacts.event.server.EventCountBean;
import org.eclipse.scout.contacts.event.server.sql.SQLs;
import org.eclipse.scout.contacts.event.shared.contact.ContactFormTabExtensionData;
import org.eclipse.scout.contacts.event.shared.contact.ContactTableDataExtension;
import org.eclipse.scout.contacts.server.contact.ContactService;
import org.eclipse.scout.contacts.shared.contact.ContactFormData;
import org.eclipse.scout.contacts.shared.contact.ContactTablePageData;
import org.eclipse.scout.contacts.shared.contact.ContactTablePageData.ContactTableRowData;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Replace
public class ContactServiceExtension extends ContactService {

  @Override
  public ContactTablePageData getContactTableData(SearchFilter filter, String pageCompanyId) throws ProcessingException {
    ContactTablePageData pageData = super.getContactTableData(filter, pageCompanyId);

    // Add number of events to contacts
    BeanArrayHolder<EventCountBean> arrayHolder = new BeanArrayHolder<>(EventCountBean.class);
    SQL.selectInto(SQLs.EVENT_COUNT_BY_CONTACT, new NVPair("bean", arrayHolder));

    // Create a map to access event count by contact
    Map<String, Long> eventCounts = new HashMap<>();
    for (EventCountBean counter : arrayHolder.getBeans()) {
      eventCounts.put(counter.getContactId(), counter.getEventCount());
    }

    // Add event count to contacts
    for (ContactTableRowData contactRow : pageData.getRows()) {
      long eventCount = eventCounts.getOrDefault(contactRow.getContactId(), 0L);
      contactRow.getContribution(ContactTableDataExtension.class).setEvents(eventCount);
    }

    return pageData;
  }

  @Override
  public ContactFormData load(ContactFormData formData) throws ProcessingException {
    formData = super.load(formData);

    ContactFormTabExtensionData extensionData = formData.getContribution(ContactFormTabExtensionData.class);
    SQL.selectInto(SQLs.CONTACT_EVENT_SELECT, extensionData, formData);

    return formData;
  }
}
