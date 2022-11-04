/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {StaticLookupCall, Table, TableCheckableStyle} from '@eclipse-scout/core';

export class CheckableStyleLookupCall extends StaticLookupCall<TableCheckableStyle> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return CheckableStyleLookupCall.DATA;
  }

  static DATA = [
    [Table.CheckableStyle.CHECKBOX, 'checkbox'],
    [Table.CheckableStyle.TABLE_ROW, 'table_row'],
    [Table.CheckableStyle.CHECKBOX_TABLE_ROW, 'checkbox_table_row']
  ];
}
