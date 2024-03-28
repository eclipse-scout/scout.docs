/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.shared.services.calendar;

import java.util.List;

import org.eclipse.scout.rt.platform.util.LazyValue;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarDescriptor;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarDescriptor;

public class CalendarService implements ICalendarService {

  private LazyValue<ICalendarDescriptor> m_ownCalendarGroup = new LazyValue<>(this::createOwnCalendarGroup);
  private LazyValue<ICalendarDescriptor> m_otherCalendarGroup = new LazyValue<>(this::createOtherCalendarGroup);

  private LazyValue<ICalendarDescriptor> m_businessCalendar = new LazyValue<>(this::createBusinessCalendar);
  private LazyValue<ICalendarDescriptor> m_privateCalendar = new LazyValue<>(this::createPrivateCalendar);
  private LazyValue<ICalendarDescriptor> m_lisaTurnerCalendar = new LazyValue<>(this::createLisaTurnerCalendar);
  private LazyValue<ICalendarDescriptor> m_johnDoeCalendar = new LazyValue<>(this::createJohnDoeCalendar);


  @Override
  public List<ICalendarDescriptor> getAllCalendars() {
    return List.of(getOwnCalendarGroup(),
        getOtherCalendarGroup(),
        getBusinessCalendar(),
        getPrivateCalendar(),
        getLisaTurnerCalendar(),
        getJohnDoeCalendar());
  }

  protected ICalendarDescriptor createOwnCalendarGroup() {
    return new CalendarDescriptor().withName("My calendars");
  }

  protected ICalendarDescriptor createOtherCalendarGroup() {
    return new CalendarDescriptor().withName("Other calendars");
  }

  public ICalendarDescriptor createBusinessCalendar() {
    return new CalendarDescriptor()
        .withName("Business Calendar")
        .withParentId(getOwnCalendarGroup().getCalendarId())
        .withCssClass("calendar-color-orange");
  }

  protected ICalendarDescriptor createPrivateCalendar() {
    return new CalendarDescriptor()
        .withName("Private Calendar")
        .withParentId(getOwnCalendarGroup().getCalendarId())
        .withCssClass("calendar-color-green");
  }

  protected ICalendarDescriptor createJohnDoeCalendar() {
    return new CalendarDescriptor()
        .withName("John Doe")
        .withParentId(getOtherCalendarGroup().getCalendarId())
        .withSelectable(false)
        .withCssClass("calendar-color-red");
  }

  protected ICalendarDescriptor createLisaTurnerCalendar() {
    return new CalendarDescriptor()
        .withName("Lisa Turner")
        .withParentId(getOtherCalendarGroup().getCalendarId())
        .withSelectable(false)
        .withCssClass("calendar-color-blue");
  }


  public ICalendarDescriptor getOwnCalendarGroup() {
    return m_ownCalendarGroup.get();
  }

  public ICalendarDescriptor getOtherCalendarGroup() {
    return m_otherCalendarGroup.get();
  }

  @Override
  public ICalendarDescriptor getBusinessCalendar() {
    return m_businessCalendar.get();
  }

  @Override
  public ICalendarDescriptor getPrivateCalendar() {
    return m_privateCalendar.get();
  }

  @Override
  public ICalendarDescriptor getLisaTurnerCalendar() {
    return m_lisaTurnerCalendar.get();
  }

  @Override
  public ICalendarDescriptor getJohnDoeCalendar() {
    return m_johnDoeCalendar.get();
  }
}
