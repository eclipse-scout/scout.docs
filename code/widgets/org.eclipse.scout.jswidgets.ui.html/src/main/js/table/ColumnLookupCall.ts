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
