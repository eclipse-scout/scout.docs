/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, BaseDoEntity, Column, ITableCustomizerDo, scout, Table, TableCustomizer, typeName} from '@eclipse-scout/core';
import {SampleColumnFactory, SampleCustomColumnForm} from '../index';

export class SampleTableCustomizer extends TableCustomizer {

  columnDatas: SampleCustomColumnDo[] = [];

  override setCustomizerData(customizerData: ITableCustomizerDo) {
    let oldCustomColumnIds = new Set(this.columnDatas.map(columnData => columnData.columnId).filter(Boolean));

    if (customizerData instanceof SampleTableCustomizerDo) {
      this.columnDatas = [...arrays.ensure(customizerData.columns).filter(columnData => !!columnData.columnId)];
    } else {
      this.columnDatas = [];
    }

    let newCustomColumnIds = new Set(this.columnDatas.map(columnData => columnData.columnId).filter(Boolean));
    let customColumnIdsToDelete = new Set([...oldCustomColumnIds].filter(columnId => !newCustomColumnIds.has(columnId)));

    // Preserve non-custom columns and all custom columns that are still existing
    let preservedColumns = this.table.columns
      .filter(column => !customColumnIdsToDelete.has(column.buildUuid()));
    // Create new column instances for all custom columns that don't yet exist
    let newColumns = this.columnDatas
      .filter(columnData => !oldCustomColumnIds.has(columnData.columnId))
      .map(columnData => SampleColumnFactory.get().createColumn(columnData))
      .filter(Boolean);

    this.table.setColumns([
      ...preservedColumns,
      ...newColumns
    ]);
  }

  override getCustomizerData(): ITableCustomizerDo {
    if (arrays.empty(this.columnDatas)) {
      return null;
    }
    return scout.create(SampleTableCustomizerDo, {
      columns: [...this.columnDatas]
    });
  }

  override addColumn(insertAfterColumn?: Column<any>): JQuery.Promise<void> {
    let form = scout.create(SampleCustomColumnForm, {
      parent: this.table,
      hiddenColumns: this.table.organizer.getInvisibleColumns()
    });
    form.open();
    return form.whenSave().then(() => {
      if (form.data) {
        let columnData = form.data;
        this.columnDatas.push(columnData);
        this.table.insertColumn(SampleColumnFactory.get().createColumn(columnData), insertAfterColumn);
        this.table.reload(Table.ReloadReason.ORGANIZE_COLUMNS);
      } else if (form.hiddenColumns) {
        form.hiddenColumns.forEach(column => {
          column.setVisible(true);
          let position = this.table.visibleColumns().indexOf(insertAfterColumn);
          if (position >= 0) {
            this.table.moveColumn(column, position);
          }
        });
      }
    });
  }

  override modifyColumn(column: Column<any>): JQuery.Promise<void> {
    let oldColumnData = this.columnDatas.find(columnData => columnData.columnId === column.buildUuid());
    let form = scout.create(SampleCustomColumnForm, {
      parent: this.table,
      data: oldColumnData
    });
    form.open();
    return form.whenSave().then(() => {
      let newColumnData = form.data;
      arrays.replace(this.columnDatas, oldColumnData, newColumnData);
      this.table.setColumns(this.table.columns.map(col => col === column ? SampleColumnFactory.get().createColumn(newColumnData) : col));
      this.table.reload(Table.ReloadReason.ORGANIZE_COLUMNS);
    });
  }

  override removeColumns(columns: Column<any>[]) {
    columns.forEach(column => {
      let oldColumnData = this.columnDatas.find(columnData => columnData.columnId === column.buildUuid());
      arrays.remove(this.columnDatas, oldColumnData);
    });
    this.table.deleteColumns(columns);
  }

  override removeAllColumns() {
    let columnsToDelete = this.columnDatas.map(columnData => this.table.columnByUuid(columnData.columnId)).filter(Boolean);
    arrays.clear(this.columnDatas);
    this.table.deleteColumns(columnsToDelete);
  }

  override isCustomizable(column: Column<any>): boolean {
    return this.columnDatas.some(columnData => columnData.columnId === column.buildUuid());
  }
}

@typeName('jswidgets.SampleTableCustomizer')
export class SampleTableCustomizerDo extends BaseDoEntity implements ITableCustomizerDo {
  columns: SampleCustomColumnDo[];
}

@typeName('jswidgets.SampleCustomColumn')
export class SampleCustomColumnDo extends BaseDoEntity {
  columnId: string;
  columnType: SampleCustomColumnType;
  name: string;
  width: number;
}

export type SampleCustomColumnType = 'string' | 'number' | 'boolean' | 'date';
