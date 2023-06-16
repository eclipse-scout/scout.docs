/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
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

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarDescriptor;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarDescriptor;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("c878f380-0a06-4893-9153-d2b7eeb05611")
public class CalendarsLookupCall extends LocalLookupCall<ICalendarDescriptor> {

  private static final long serialVersionUID = 1L;

  private List<ICalendarDescriptor> m_calendars;

  public CalendarsLookupCall() {
    m_calendars = new ArrayList<>();
    m_calendars.add(new CalendarDescriptor(1L, "Business Calendar", true, "calendar-appointment-orange"));
    m_calendars.add(new CalendarDescriptor(2L, "Private Calendar", true, "calendar-appointment-green"));
  }

  public List<ICalendarDescriptor> getCalendars() {
    return m_calendars;
  }

  public void addCalendar(ICalendarDescriptor calendar) {
    boolean isPresent = m_calendars.stream()
        .anyMatch(cal -> Objects.equals(cal.getCalendarId(), calendar.getCalendarId()));
    if (!isPresent) {
      m_calendars.add(calendar);
    }
  }

  public void addCalendars(List<ICalendarDescriptor> calendars) {
    for (ICalendarDescriptor calendar : calendars) {
      addCalendar(calendar);
    }
  }

  @Override
  protected List<? extends ILookupRow<ICalendarDescriptor>> execCreateLookupRows() {
    return m_calendars.stream()
        .map(calendar -> new LookupRow<>(calendar, calendar.getName()))
        .collect(Collectors.toList());
  }
}
