/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Column, InitModelOf, StaticLookupCall, StaticLookupCallModel, Table} from '@eclipse-scout/core';

export class ColumnLookupCall extends StaticLookupCall<Column> implements ColumnLookupCallModel {
  declare model: ColumnLookupCallModel;
  table: Table;

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.setTable(this.table);
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

export interface ColumnLookupCallModel extends StaticLookupCallModel<Column> {
  table?: Table;
}
