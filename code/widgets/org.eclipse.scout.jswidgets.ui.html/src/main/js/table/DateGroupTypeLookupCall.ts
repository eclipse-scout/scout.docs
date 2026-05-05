/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {DateGroupType, StaticLookupCall, TableGroupingStyle} from '@eclipse-scout/core';

export class DateGroupTypeLookupCall extends StaticLookupCall<TableGroupingStyle> {

  protected override _data(): any[] {
    return [
      [DateGroupType.YEAR, 'YEAR'],
      [DateGroupType.MONTH, 'MONTH'],
      [DateGroupType.MONTH_AND_YEAR, 'MONTH_AND_YEAR'],
      [DateGroupType.CALENDAR_WEEK, 'CALENDAR_WEEK'],
      [DateGroupType.WEEKDAY, 'WEEKDAY'],
      [DateGroupType.DATE, 'DATE']
    ];
  }
}
