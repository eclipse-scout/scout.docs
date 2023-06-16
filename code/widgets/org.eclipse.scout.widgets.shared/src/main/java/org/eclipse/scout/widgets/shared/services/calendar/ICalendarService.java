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

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarResourceDo;

public interface ICalendarService extends IService {

  List<CalendarResourceDo> getAllResources();

  CalendarResourceDo getBusinessCalendar();

  CalendarResourceDo getPrivateCalendar();

  CalendarResourceDo getLisaTurnerCalendar();

  CalendarResourceDo getJohnDoeCalendar();
}
