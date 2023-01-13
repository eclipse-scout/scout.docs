/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.events.server;

import java.util.UUID;

import org.eclipse.scout.contacts.events.server.sql.SQLs;
import org.eclipse.scout.contacts.events.shared.event.CreateEventPermission;
import org.eclipse.scout.contacts.events.shared.event.EventFormData;
import org.eclipse.scout.contacts.events.shared.event.EventTablePageData;
import org.eclipse.scout.contacts.events.shared.event.IEventService;
import org.eclipse.scout.contacts.events.shared.event.ReadEventPermission;
import org.eclipse.scout.contacts.events.shared.event.UpdateEventPermission;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.TableBeanHolderFilter;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class EventService implements IEventService {

  @Override
  public EventTablePageData getTableData(SearchFilter filter, String organizationId) {
    EventTablePageData pageData = new EventTablePageData();

    StringBuilder sqlSelect = new StringBuilder(SQLs.EVENT_PAGE_DATA_SELECT);
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (StringUtility.hasText(organizationId)) {
      sqlWhere.append(SQLs.EVENT_PAGE_DATA_WHERE_CLAUSE);
    }

    String sql = sqlSelect.append(sqlWhere).append(SQLs.EVENT_PAGE_DATA_INTO).toString();

    SQL.selectInto(sql, new NVPair("organizationId", organizationId), new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public EventFormData create(EventFormData formData) {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getEventId())) {
      formData.setEventId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.EVENT_INSERT, formData);

    return store(formData);
  }

  @Override
  public EventFormData load(EventFormData formData) {
    if (!ACCESS.check(new ReadEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.EVENT_SELECT, formData);
    SQL.selectInto(SQLs.EVENT_PARTICIPANTS_SELECT, formData);

    return formData;
  }

  @Override
  public EventFormData prepareCreate(EventFormData formData) {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    return formData;
  }

  @Override
  public EventFormData store(EventFormData formData) {
    if (!ACCESS.check(new UpdateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.EVENT_UPDATE, formData);

    TableBeanHolderFilter deletedParticipants = new TableBeanHolderFilter(formData.getParticipantTableField(), ITableBeanRowHolder.STATUS_DELETED);
    TableBeanHolderFilter insertedParticipants = new TableBeanHolderFilter(formData.getParticipantTableField(), ITableBeanRowHolder.STATUS_INSERTED);
    NVPair eventId = new NVPair("eventId", formData.getEventId());

    SQL.delete(SQLs.EVENT_PARTICIPANTS_DELETE, deletedParticipants, eventId);
    SQL.insert(SQLs.EVENT_PARTICIPANTS_INSERT, insertedParticipants, eventId);

    return formData;
  }
}
