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
package org.eclipse.scout.contacts.events.server.person;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.contacts.events.server.EventCountBean;
import org.eclipse.scout.contacts.events.server.sql.SQLs;
import org.eclipse.scout.contacts.events.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.events.shared.person.PersonTablePageDataExtension;
import org.eclipse.scout.contacts.server.person.PersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData.PersonTableRowData;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Replace
public class PersonServiceExtension extends PersonService {

  @Override
  public PersonTablePageData getPersonTableData(SearchFilter filter, String organizationId) {
    PersonTablePageData pageData = super.getPersonTableData(filter, organizationId);

    // Add number of events to persons
    BeanArrayHolder<EventCountBean> arrayHolder = new BeanArrayHolder<>(EventCountBean.class);
    SQL.selectInto(SQLs.EVENT_COUNT_BY_PERSON, new NVPair("bean", arrayHolder));

    // Create a map to access event count by person
    Map<String, Long> eventCounts = new HashMap<>();
    for (EventCountBean counter : arrayHolder.getBeans()) {
      eventCounts.put(counter.getPersonId(), counter.getEventCount());
    }

    // Add event count to persons
    for (PersonTableRowData personRow : pageData.getRows()) {
      long eventCount = NumberUtility.nvl(eventCounts.get(personRow.getPersonId()), 0L);
      personRow.getContribution(PersonTablePageDataExtension.class).setEvents(eventCount);
    }

    return pageData;
  }

  @Override
  public PersonFormData load(PersonFormData formData) {
    formData = super.load(formData);

    PersonFormTabExtensionData extensionData = formData.getContribution(PersonFormTabExtensionData.class);
    SQL.selectInto(SQLs.PERSON_EVENT_SELECT, extensionData, formData);

    return formData;
  }
}
