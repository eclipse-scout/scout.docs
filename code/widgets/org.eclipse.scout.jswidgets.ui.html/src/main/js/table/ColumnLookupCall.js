/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {StaticLookupCall} from '@eclipse-scout/core';

export default class ColumnLookupCall extends StaticLookupCall {

  constructor(table) {
    super();

    this.data = [];
    this.setTable(table);
  }

  _data() {
    return this.data;
  }

  setTable(table) {
    this.table = table;
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.table.columns.map(column => {
      return [column, column.text];
    });
  }
}
