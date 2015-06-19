/**
 *
 */
package org.eclipsescout.contacts.server.premium.services;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.premium.services.IEventManagementOutlineService;
import org.eclipsescout.contacts.shared.premium.ui.desktop.outlines.EventsTablePageData;

/**
 * @author mzi
 */
public class EventManagementOutlineService extends AbstractService implements IEventManagementOutlineService {

  @Override
  public EventsTablePageData getEventsTableData(SearchFilter filter, String companyId) throws ProcessingException {
    EventsTablePageData pageData = new EventsTablePageData();

    StringBuilder sqlSelect = new StringBuilder(TEXTS.get("SqlEventPageDataSelect"));
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (StringUtility.hasText(companyId)) {
      sqlWhere.append(TEXTS.get("SqlEventPageDataWhereCompany"));
    }

    String sqlInto = TEXTS.get("SqlEventPageDataSelectInto");
    String sql = sqlSelect.append(sqlWhere).append(sqlInto).toString();

    SQL.selectInto(sql, new NVPair("companyId", companyId), new NVPair("page", pageData));

    return pageData;
  }
}
