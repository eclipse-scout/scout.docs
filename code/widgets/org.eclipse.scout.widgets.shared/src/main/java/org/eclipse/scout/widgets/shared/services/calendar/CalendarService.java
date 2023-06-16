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

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.LazyValue;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarResourceDo;

public class CalendarService implements ICalendarService {

  private LazyValue<CalendarResourceDo> m_ownCalendarGroup = new LazyValue<>(this::createOwnCalendarGroup);
  private LazyValue<CalendarResourceDo> m_otherCalendarGroup = new LazyValue<>(this::createOtherCalendarGroup);

  private LazyValue<CalendarResourceDo> m_businessCalendar = new LazyValue<>(this::createBusinessCalendar);
  private LazyValue<CalendarResourceDo> m_privateCalendar = new LazyValue<>(this::createPrivateCalendar);
  private LazyValue<CalendarResourceDo> m_lisaTurnerCalendar = new LazyValue<>(this::createLisaTurnerCalendar);
  private LazyValue<CalendarResourceDo> m_johnDoeCalendar = new LazyValue<>(this::createJohnDoeCalendar);


  @Override
  public List<CalendarResourceDo> getAllResources() {
    return List.of(getOwnCalendarGroup(),
        getOtherCalendarGroup(),
        getBusinessCalendar(),
        getPrivateCalendar(),
        getLisaTurnerCalendar(),
        getJohnDoeCalendar());
  }

  protected CalendarResourceDo createOwnCalendarGroup() {
    return BEANS.get(CalendarResourceDo.class).withName("My calendars");
  }

  protected CalendarResourceDo createOtherCalendarGroup() {
    return BEANS.get(CalendarResourceDo.class).withName("Other calendars");
  }

  public CalendarResourceDo createBusinessCalendar() {
    return BEANS.get(CalendarResourceDo.class)
        .withName("Business Calendar")
        .withParentId(getOwnCalendarGroup().getResourceId())
        .withCssClass("calendar-color-orange");
  }

  protected CalendarResourceDo createPrivateCalendar() {
    return BEANS.get(CalendarResourceDo.class)
        .withName("Private Calendar")
        .withParentId(getOwnCalendarGroup().getResourceId())
        .withCssClass("calendar-color-green");
  }

  protected CalendarResourceDo createJohnDoeCalendar() {
    return BEANS.get(CalendarResourceDo.class)
        .withName("John Doe")
        .withParentId(getOtherCalendarGroup().getResourceId())
        .withSelectable(false)
        .withCssClass("calendar-color-red");
  }

  protected CalendarResourceDo createLisaTurnerCalendar() {
    return BEANS.get(CalendarResourceDo.class)
        .withName("Lisa Turner")
        .withParentId(getOtherCalendarGroup().getResourceId())
        .withSelectable(false)
        .withCssClass("calendar-color-blue");
  }


  public CalendarResourceDo getOwnCalendarGroup() {
    return m_ownCalendarGroup.get();
  }

  public CalendarResourceDo getOtherCalendarGroup() {
    return m_otherCalendarGroup.get();
  }

  @Override
  public CalendarResourceDo getBusinessCalendar() {
    return m_businessCalendar.get();
  }

  @Override
  public CalendarResourceDo getPrivateCalendar() {
    return m_privateCalendar.get();
  }

  @Override
  public CalendarResourceDo getLisaTurnerCalendar() {
    return m_lisaTurnerCalendar.get();
  }

  @Override
  public CalendarResourceDo getJohnDoeCalendar() {
    return m_johnDoeCalendar.get();
  }
}
