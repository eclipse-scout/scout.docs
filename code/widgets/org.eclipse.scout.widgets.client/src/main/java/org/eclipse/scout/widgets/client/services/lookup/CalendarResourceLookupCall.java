/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarResourceDo;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.shared.services.calendar.ICalendarService;

@ClassId("c878f380-0a06-4893-9153-d2b7eeb05611")
public class CalendarResourceLookupCall extends LocalLookupCall<CalendarResourceDo> {

  private static final long serialVersionUID = 1L;

  private List<CalendarResourceDo> m_calendarResources;

  public CalendarResourceLookupCall() {
    m_calendarResources = new ArrayList<>(BEANS.get(ICalendarService.class).getAllResources());
  }

  public List<CalendarResourceDo> getCalendarResources() {
    return m_calendarResources;
  }

  public void addCalendar(CalendarResourceDo resource) {
    boolean isPresent = m_calendarResources.stream()
        .anyMatch(cal -> Objects.equals(cal.getResourceId(), resource.getResourceId()));
    if (!isPresent) {
      m_calendarResources.add(resource);
    }
  }

  public void addCalendars(List<CalendarResourceDo> calendars) {
    for (CalendarResourceDo calendar : calendars) {
      addCalendar(calendar);
    }
  }

  @Override
  protected List<? extends ILookupRow<CalendarResourceDo>> execCreateLookupRows() {
    return m_calendarResources.stream()
        .filter(this::isLeafCalendar) // Filter groups
        .map(resource -> new LookupRow<>(resource, resource.getName()))
        .collect(Collectors.toList());
  }

  protected boolean isLeafCalendar(CalendarResourceDo resourceDo) {
    return m_calendarResources.stream().noneMatch(desc -> resourceDo.getResourceId().equals(desc.getParentId()));
  }
}
