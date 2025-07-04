/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {BooleanColumn, Column, Constructor, DateColumn, NumberColumn, ObjectOrChildModel, scout} from '@eclipse-scout/core';
import {SampleCustomColumnDo, SampleCustomColumnType} from '../index';

export class SampleColumnFactory {

  protected static _INSTANCE;

  static get(): SampleColumnFactory {
    if (!SampleColumnFactory._INSTANCE) {
      SampleColumnFactory._INSTANCE = scout.create(SampleColumnFactory);
    }
    return SampleColumnFactory._INSTANCE;
  }

  createColumn(columnData: SampleCustomColumnDo): ObjectOrChildModel<Column> {
    return {
      uuid: columnData.columnId,
      objectType: this._columnTypeToObjectType(columnData.columnType),
      text: columnData.name,
      width: columnData.width
    };
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
