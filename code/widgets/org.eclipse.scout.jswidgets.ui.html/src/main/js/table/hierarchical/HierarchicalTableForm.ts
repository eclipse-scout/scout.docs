/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {dates, Form, FormModel, icons, InitModelOf, numbers, Table, TableRowModel} from '@eclipse-scout/core';
import {ColumnLookupCall, HierarchicalTableFormWidgetMap} from '../../index';
import model from './HierarchicalTableFormModel';

export class HierarchicalTableForm extends Form {
  declare widgetMap: HierarchicalTableFormWidgetMap;

  table: Table;
  rowNo = 1;
  groupNo = 1;

  protected override _jsonModel(): FormModel {
    return model();
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.table = this.widget('Table');

    this.widget('PropertiesBox').setTable(this.table);
    this.widget('RowPropertiesBox').setTable(this.table);

    let tableField = this.widget('TableField');
    this.widget('FormFieldPropertiesBox').setField(tableField);
    this.widget('GridDataBox').setField(tableField);
    this.widget('FormFieldActionsBox').setField(tableField);
    this.widget('WidgetActionsBox').setField(this.table);
    this.widget('EventsTab').setField(this.table);

    this.widget('RemoveAll').on('action', this._onRemoveAllRows.bind(this));
    this.widget('InsertFewFlat').on('action', this._onInsertFewFlat.bind(this));
    this.widget('InsertFew').on('action', this._onInsertFew.bind(this));
    this.widget('InsertMany').on('action', this._onInsertMany.bind(this));
    this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
    this.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));

    let targetField = this.widget('Column.TargetField');
    targetField.setLookupCall(new ColumnLookupCall(this.table));
    targetField.on('propertyChange:value', event => {
      let newColumn = event.newValue;

      let columnPropertiesBox = this.widget('Column.PropertiesBox');
      columnPropertiesBox.setColumn(newColumn);
      columnPropertiesBox.setEnabled(!!newColumn);
    });
    targetField.setValue(this.table.columns[0]);

    this._insertFewRows();
    this.table.expandAll();
  }

  protected _onRemoveAllRows() {
    this.table.deleteAllRows();
  }

  protected _onInsertFewFlat() {
    this._insertFewRowsFlat();
  }

  protected _onInsertFew() {
    this._insertFewRows();
  }

  protected _onInsertMany() {
    this._insertManyRows();
  }

  protected _insertFewRowsFlat() {
    let rows: TableRowModel[] = [];
    for (let i = 0; i < 10; i++) {
      let rowId = this._nextRowId();
      rows.push({
        id: rowId,
        cells: [
          `Row_${rowId}`, null, null, Boolean(i % 2)
        ]
      });
    }
    this.table.insertRows(rows);
  }

  protected _insertFewRows() {
    let daltonId = this._nextRowId();
    let simpsonsId = this._nextRowId();
    this.table.insertRows([{
      id: daltonId,
      iconId: icons.WORLD,
      cells: [
        'Dalton brothers', null, null, true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Joe Dalton', 'the smartest', dates.parseJsonDate('1940-10-20'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'William Dalton', 'smarter', dates.parseJsonDate('1945-15-03'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Jack Dalton', 'smart', dates.parseJsonDate('1944-09-15'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      enabled: false,
      cells: [
        'Averell Dalton', 'not so smart', dates.parseJsonDate('1945-11-23'), true
      ]
    }, {
      id: simpsonsId,
      iconId: icons.GROUP,
      cells: [
        'Simpsons', 'a simple family', null, false
      ]
    }, {
      id: this._nextRowId(),
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Homer Simpson', 'Daddy', dates.parseJsonDate('1960-12-23'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Marge Simpson', 'Mom', dates.parseJsonDate('1964-05-02'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Bart Simpson', 'Boy', dates.parseJsonDate('1985-10-08'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Lisa Simpson', 'Girl', dates.parseJsonDate('1987-03-17'), true
      ]
    }, {
      id: this._nextRowId(),
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Maggie Simpson', 'Baby', dates.parseJsonDate('1988-08-14'), true
      ]
    }]);
  }

  protected _insertManyRows() {
    let baseDate = dates.parseJsonDate('2015-10-20');

    let rows: TableRowModel[] = [];
    for (let i = 0; i < 100; i++) {
      let parentRowId = this._nextRowId();
      let childCount = Math.floor(Math.random() * 100);
      rows.push({
        id: parentRowId,
        cells: [
          `Parent_${parentRowId} (${childCount})`
        ]
      });

      for (let j = 0; j < childCount; j++) {
        let rowId = this._nextRowId();
        rows.push({
          id: rowId,
          parentRow: parentRowId,
          cells: [
            `Child_${rowId}`,
            numbers.randomId(),
            dates.shift(baseDate, null, null, j)
          ]
        });
      }
    }
    this.table.insertRows(rows);
  }

  protected _nextRowId(): string {
    return '' + this.rowNo++;
  }

  protected _onAddRowMenuAction() {
    let rowId = this._nextRowId();
    let selectedRow = this.table.selectedRow();
    let parentId = selectedRow ? selectedRow.id : null;

    this.table.insertRow({
      id: rowId,
      parentRow: parentId,
      iconId: null,
      cells: [
        `Row_${rowId}`,
        numbers.randomId(),
        dates.shift(dates.parseJsonDate('2015-10-20'), null, null, numbers.randomInt(1000))
      ]
    });
  }

  protected _onDeleteRowMenuAction() {
    this.table.deleteRows(this.table.selectedRows);
  }
}
