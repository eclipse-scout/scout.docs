/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, TableRowDropType} from '@eclipse-scout/core';

export class TableRowDropTypeLookupCall extends StaticLookupCall<TableRowDropType> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return TableRowDropTypeLookupCall.DATA;
  }

  static DATA = [
    [TableRowDropType.ALLOWED, 'allowed'],
    [TableRowDropType.FORBIDDEN, 'forbidden'],
    [TableRowDropType.NONE, 'none']
  ];
}
