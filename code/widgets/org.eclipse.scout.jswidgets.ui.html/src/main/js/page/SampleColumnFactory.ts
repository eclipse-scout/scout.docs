/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {BooleanColumn, Column, Constructor, DateColumn, NumberColumn, scout, Table} from '@eclipse-scout/core';
import {SampleCustomColumnDo, SampleCustomColumnType} from '../index';

export class SampleColumnFactory {

  protected static _INSTANCE;

  static get(): SampleColumnFactory {
    if (!SampleColumnFactory._INSTANCE) {
      SampleColumnFactory._INSTANCE = scout.create(SampleColumnFactory);
    }
    return SampleColumnFactory._INSTANCE;
  }

  createColumn(param: SampleColumnFactoryParam): Column<any> {
    let columnConfig = param.columnConfig;
    let objectType = this._columnTypeToObjectType(columnConfig.columnType);
    return scout.create(objectType, {
      parent: param.parent,
      uuid: columnConfig.columnId,
      text: columnConfig.name,
      width: columnConfig.width
    });
  }

  protected _columnTypeToObjectType(columnType: SampleCustomColumnType): Constructor<Column<any>> {
    switch (columnType) {
      case 'number':
        return NumberColumn;
      case 'boolean':
        return BooleanColumn;
      case 'date':
        return DateColumn;
      default:
        return Column;
    }
  }
}

export class SampleColumnFactoryParam {
  parent: Table;
  columnConfig: SampleCustomColumnDo;
}
