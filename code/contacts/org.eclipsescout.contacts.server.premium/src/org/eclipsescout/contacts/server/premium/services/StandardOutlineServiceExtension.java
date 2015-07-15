/**
 *
 */
package org.eclipsescout.contacts.server.premium.services;

import java.util.HashMap;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.BeanArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipsescout.contacts.server.services.StandardOutlineService;
import org.eclipsescout.contacts.shared.premium.ui.desktop.outlines.ContactsTableDataExtension;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.ContactsTablePageData;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.ContactsTablePageData.ContactsTableRowData;

/**
 * @author mzi
 */
public class StandardOutlineServiceExtension extends StandardOutlineService { // <1>

  @Override
  public ContactsTablePageData getContactsTableData(SearchFilter filter, String pageCompanyId) throws ProcessingException {
    ContactsTablePageData pageData = super.getContactsTableData(filter, pageCompanyId);

    // load data for additional column
    addEventCounts(pageData);

    return pageData;
  }

  private void addEventCounts(ContactsTablePageData pageData) throws ProcessingException { // <2>
    // get event counts
    // background info: https://www.eclipse.org/forums/index.php/t/310526/
    BeanArrayHolder<EventCounterBean> arrayHolder = new BeanArrayHolder<>(EventCounterBean.class); // <3>
    SQL.selectInto(TEXTS.get("SqlContactPageEventCounts"), new NVPair("h", arrayHolder));

    // create map for event counts
    HashMap<String, Long> eventCounts = new HashMap<>();
    for (EventCounterBean counter : arrayHolder.getBeans()) {
      eventCounts.put(counter.getContactId(), counter.getEvents());
    }

    // add event counts to page data rows
    for (ContactsTableRowData row : pageData.getRows()) {
      long events = eventCounts.getOrDefault(row.getContactId(), 0L);
      row.getContribution(ContactsTableDataExtension.class).setEvents(events);
    }
  }
}
