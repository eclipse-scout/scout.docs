/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Column, StaticLookupCall, Table} from '@eclipse-scout/core';

export class ColumnLookupCall extends StaticLookupCall<Column> {
  table: Table;

  constructor(table: Table) {
    super();

    this.data = [];
    this.setTable(table);
  }

  protected override _data(): any[] {
    return this.data;
  }

  setTable(table: Table) {
    this.table = table;
    this._rebuildData();
  }

  protected _rebuildData() {
    this.data = this.table.columns.map(column => {
      return [column, column.text];
    });
  }
}
