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
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarDescriptor;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.shared.services.calendar.ICalendarService;

@ClassId("c878f380-0a06-4893-9153-d2b7eeb05611")
public class CalendarsLookupCall extends LocalLookupCall<ICalendarDescriptor> {

  private static final long serialVersionUID = 1L;

  private List<ICalendarDescriptor> m_calendarDescriptors;

  public CalendarsLookupCall() {
    m_calendarDescriptors = new ArrayList<>(BEANS.get(ICalendarService.class).getAllCalendars());
  }

  public List<ICalendarDescriptor> getCalendarDescriptors() {
    return m_calendarDescriptors;
  }

  public void addCalendar(ICalendarDescriptor calendar) {
    boolean isPresent = m_calendarDescriptors.stream()
        .anyMatch(cal -> Objects.equals(cal.getCalendarId(), calendar.getCalendarId()));
    if (!isPresent) {
      m_calendarDescriptors.add(calendar);
    }
  }

  public void addCalendars(List<ICalendarDescriptor> calendars) {
    for (ICalendarDescriptor calendar : calendars) {
      addCalendar(calendar);
    }
  }

  @Override
  protected List<? extends ILookupRow<ICalendarDescriptor>> execCreateLookupRows() {
    return m_calendarDescriptors.stream()
        .filter(ICalendarDescriptor::isSelectable) // Filter unselectable
        .filter(c -> c.getParentId() != null) // Filter groups
        .map(calendar -> new LookupRow<>(calendar, calendar.getName()))
        .collect(Collectors.toList());
  }
}
