/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, BaseDoEntity, Column, IColumnConfigDo, ITableCustomizerDo, scout, Table, TableCustomizer, TableCustomizerCreateColumnsOptions, tableUiPreferences, typeName} from '@eclipse-scout/core';
import {SampleColumnFactory, SampleCustomColumnForm} from '../index';

export class SampleTableCustomizer extends TableCustomizer {

  customColumnConfigs: SampleCustomColumnDo[] = [];

  override async setCustomizerData(customizerData: SampleTableCustomizerDo): Promise<void> {
    let oldCustomColumnIds = new Set(this.customColumnConfigs.map(columnConfig => columnConfig.columnId).filter(Boolean));

    this.customColumnConfigs = arrays.ensure(customizerData?.customColumns).filter(columnConfig => !!columnConfig.columnId);

    // Delete all custom columns that are no longer part of the customizer data
    let preservedColumns = this.table.columns.filter(column => !oldCustomColumnIds.has(column.buildUuid()));
    tableUiPreferences.withIgnoreTableEvents(() => {
      this.table.setColumns(preservedColumns);
    });

    // Create and insert new columns
    await this.createColumns(this.customColumnConfigs, {
      insertIntoTable: true
    });
  }

  override getCustomizerData(): SampleTableCustomizerDo {
    if (arrays.empty(this.customColumnConfigs)) {
      return null;
    }
    return scout.create(SampleTableCustomizerDo, {
      customColumns: [...this.customColumnConfigs]
    });
  }

  override async createColumns(columnConfigs: SampleCustomColumnDo[], options?: TableCustomizerCreateColumnsOptions): Promise<Column<any>[]> {
    columnConfigs = arrays.ensure(columnConfigs).filter(columnConfig => !!columnConfig.columnId);

    if (arrays.empty(columnConfigs)) {
      return []; // done
    }

    let newColumns = columnConfigs.map(columnConfig => this._createColumn(columnConfig));

    if (options?.insertIntoTable) {
      let newColumnIds = new Set(columnConfigs.map(columnConfig => columnConfig.columnId).filter(Boolean));
      let preservedColumns = this.table.columns.filter(column => !newColumnIds.has(column.buildUuid()));
      let columns = [...preservedColumns];

      let insertPosition = columns.length;
      if (options?.positionOrInsertAfterColumn instanceof Column) {
        let index = preservedColumns.indexOf(options.positionOrInsertAfterColumn);
        if (index !== -1) {
          insertPosition = index + 1;
        }
      } else if (typeof options?.positionOrInsertAfterColumn === 'number') {
        insertPosition = Math.max(0, options.positionOrInsertAfterColumn);
      }
      arrays.insertAll(columns, newColumns, insertPosition);

      // Set new column structure synchronously (this will also save it to the ui preferences)
      this.table.setColumns(columns);
    }

    return newColumns;
  }

  protected _createColumn(columnConfig: SampleCustomColumnDo): Column<any> {
    return SampleColumnFactory.get().createColumn({
      parent: this.table,
      columnConfig: columnConfig
    });
  }

  override async addCustomColumn(positionOrInsertAfterColumn?: number | Column<any>): Promise<Column<any>[]> {
    let form = scout.create(SampleCustomColumnForm, {
      parent: this.table,
      hiddenColumns: this.table.organizer.getInvisibleColumns()
    });
    form.open();
    let newColumns: Column<any>[] = [];
    form.whenSave().then(async () => {
      if (form.data) {
        let newColumnConfig = form.data;
        let newColumn = await this.addCustomColumnConfig(newColumnConfig, positionOrInsertAfterColumn);
        newColumns.push(newColumn);
      } else if (form.hiddenColumns) {
        form.hiddenColumns.forEach(column => {
          column.setVisible(true);
          let position = typeof positionOrInsertAfterColumn === 'number' ? positionOrInsertAfterColumn : this.table.visibleColumns().indexOf(positionOrInsertAfterColumn);
          if (position >= 0) {
            this.table.moveColumn(column, position);
          }
        });
        arrays.pushAll(newColumns, form.hiddenColumns);
      }
    });
    await form.whenClose();
    return newColumns;
  }

  override async addCustomColumnConfig(columnConfig: SampleCustomColumnDo, positionOrInsertAfterColumn?: number | Column<any>): Promise<Column<any>> {
    this.customColumnConfigs.push(columnConfig);

    let column = this._createColumn(columnConfig);
    this.table.insertColumn(column, positionOrInsertAfterColumn);
    this.table.reload(Table.ReloadReason.ORGANIZE_COLUMNS);

    return this.table.columnByUuid(columnConfig.columnId);
  }

  override async modifyCustomColumn(column: Column<any>): Promise<Column<any>[]> {
    let oldColumnConfigs = this.customColumnConfigs.find(columnConfig => columnConfig.columnId === column.buildUuid());
    let form = scout.create(SampleCustomColumnForm, {
      parent: this.table,
      data: oldColumnConfigs
    });
    form.open();
    let newColumns: Column<any>[] = [];
    form.whenSave().then(() => {
      let newColumnConfig = form.data;
      let newColumn = this._modifyColumnConfig(column, oldColumnConfigs, newColumnConfig);
      newColumns.push(newColumn);
    });
    await form.whenClose();
    return newColumns;
  }

  override async modifyCustomColumnConfig(newColumnConfig: SampleCustomColumnDo): Promise<Column<any>> {
    let oldColumnConfig = this.customColumnConfigs.find(columnConfig => columnConfig.columnId === newColumnConfig.columnId);
    if (!oldColumnConfig) {
      return null;
    }
    let column = this.table.columnByUuid(newColumnConfig.columnId);
    if (!column) {
      return null;
    }
    return this._modifyColumnConfig(column, oldColumnConfig, newColumnConfig);
  }

  protected _modifyColumnConfig(column: Column<any>, oldColumnConfig: SampleCustomColumnDo, newColumnConfig: SampleCustomColumnDo): Column<any> {
    arrays.replace(this.customColumnConfigs, oldColumnConfig, newColumnConfig);

    this.table.setColumns(this.table.columns.map(col => col === column ? this._createColumn(newColumnConfig) : col));
    this.table.reload(Table.ReloadReason.ORGANIZE_COLUMNS);

    return this.table.columnByUuid(newColumnConfig.columnId);
  }

  override removeCustomColumns(columns: Column<any>[]) {
    columns.forEach(column => {
      let oldColumnConfig = this.customColumnConfigs.find(columnConfig => columnConfig.columnId === column.buildUuid());
      arrays.remove(this.customColumnConfigs, oldColumnConfig);
    });
    this.table.deleteColumns(columns);
  }

  override removeAllCustomColumns() {
    let columnsToDelete = this.customColumnConfigs.map(columnConfig => this.table.columnByUuid(columnConfig.columnId)).filter(Boolean);
    arrays.clear(this.customColumnConfigs);
    this.table.deleteColumns(columnsToDelete);
  }

  override isCustomizable(column: Column<any>): boolean {
    return this.customColumnConfigs.some(columnConfig => columnConfig.columnId === column.buildUuid());
  }
}

@typeName('jswidgets.SampleTableCustomizer')
export class SampleTableCustomizerDo extends BaseDoEntity implements ITableCustomizerDo {
  customColumns: SampleCustomColumnDo[];
}

@typeName('jswidgets.SampleCustomColumn')
export class SampleCustomColumnDo extends BaseDoEntity implements IColumnConfigDo {
  columnId: string;
  columnType: SampleCustomColumnType;
  name: string;
  width: number;
}

export type SampleCustomColumnType = 'string' | 'number' | 'boolean' | 'date';
