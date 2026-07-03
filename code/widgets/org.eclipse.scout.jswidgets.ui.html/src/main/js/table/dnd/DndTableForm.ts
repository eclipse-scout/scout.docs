/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {dates, Form, FormModel, icons, InitModelOf, Table, TableAcceptRowDropEvent, TableRowDropType, TableRowDropTypesDo, TableRowModel} from '@eclipse-scout/core';
import {ColumnLookupCall, DndTableFormWidgetMap} from '../../index';
import model from './DndTableFormModel';

export class DndTableForm extends Form {
  declare widgetMap: DndTableFormWidgetMap;

  table: Table;
  rowNo: number;
  groupNo: number;

  constructor() {
    super();

    this.rowNo = 1;
    this.groupNo = 1;
  }

  static GROUP_SIZE = 2;

  protected override _jsonModel(): FormModel {
    return model();
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.table = this.widget('Table');
    this.table.on('acceptRowDrop', event => this._onTableAcceptRowDrop(event));

    this.widget('PropertiesBox').setTable(this.table);
    let tableField = this.widget('TableField');
    this.widget('FormFieldPropertiesBox').setField(tableField);
    this.widget('GridDataBox').setField(tableField);
    this.widget('FormFieldActionsBox').setField(tableField);
    this.widget('WidgetActionsBox').setField(this.table);
    this.widget('EventsTab').setField(this.table);

    this.widget('RemoveAll').on('action', this._onRemoveAllRows.bind(this));
    this.widget('InsertFlat').on('action', this._onInsertFlat.bind(this));
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

  protected _onTableAcceptRowDrop(event: TableAcceptRowDropEvent) {
    if (event.targetRow?.id === '2') {
      event.dropTypes.before = TableRowDropType.FORBIDDEN;
      event.dropTypes.after = TableRowDropType.FORBIDDEN;
    }
    if (event.targetRow?.id === '3') {
      event.dropTypes = TableRowDropTypesDo.of(TableRowDropType.NONE);
    }
    if (event.targetRow?.id === '1') {
      event.dropTypes.inside = TableRowDropType.FORBIDDEN;
    }
    if (event.targetRow?.id === '4' && event.targetRow.childRows.length) {
      event.dropTypes.inside = TableRowDropType.NONE;
    }
  }

  protected _onRemoveAllRows() {
    this.table.deleteAllRows();
  }

  protected _onInsertFlat() {
    this._insertFlatRows();
  }

  protected _onInsertFew() {
    this._insertFewRows();
  }

  protected _onInsertMany() {
    this._insertManyRows();
  }

  protected _insertFlatRows() {
    let rows: TableRowModel[] = [];
    for (let i = 0; i < 5; i++) {
      rows.push({
        id: 'row' + i,
        cells: [
          'Row #' + i, null, null, Boolean(i % 2)
        ]
      });
    }
    this.table.insertRows(rows);
  }

  protected _insertFewRows() {
    let daltonId = this._nextRowId(),
      simpsonsId = this._nextRowId();
    this.table.insertRows(this._scrumbleOrder([{
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
        'Wiliam Dalton', 'smarter', dates.parseJsonDate('1945-15-03'), true
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
    }]));
  }

  protected _insertManyRows() {
    let i = 0,
      allRows = [],
      createParentWithManyChildren = function(id: string, name: string, childCount: number) {
        let rows = [],
          i,
          rowId;
        rows.push(createRow(id, null, null, [name + '_parent' + ' (' + childCount + ')', null, null]));

        for (i = 0; i < childCount; i++) {
          rowId = this._nextRowId();
          rows.push(createRow(rowId, id, null, [
            name + rowId,
            'Any title',
            dates.parseJsonDate('2015-10-20')
          ]));
        }
        return rows;
      }.bind(this);

    for (i = 0; i < 100; i++) {
      allRows = allRows.concat(createParentWithManyChildren(this._nextRowId(), 'Abc', Math.floor(Math.random() * 100)));
    }

    this.table.insertRows(allRows);

    function createRow(id: string, parentId: string, iconId: string, cells: any[]): TableRowModel {
      return {
        id: id,
        parentRow: parentId,
        iconId: iconId,
        cells: cells
      };
    }
  }

  protected _nextRowId(): string {
    return '' + this.rowNo++;
  }

  protected _scrumbleOrder(rows: TableRowModel[]): TableRowModel[] {
    return rows;
    // return rows.sort((a, b) => {
    //   return 0.5 - Math.random();
    // });
  }

  protected _onAddRowMenuAction() {
    let id = this._nextRowId(),
      parentId = null,
      selectedRow = this.table.selectedRow();
    if (selectedRow) {
      parentId = selectedRow.id;
    }

    this.table.insertRow({
      id: id,
      parentRow: parentId,
      iconId: null,
      cells: [
        'New Row ' + id,
        'Any title',
        dates.parseJsonDate('2015-10-20')
      ]
    });
  }

  protected _onDeleteRowMenuAction() {
    this.table.deleteRows(this.table.selectedRows);
  }

  protected _onToggleGroupNoColumnMenuAction() {
    let column = this.table.columnById('GroupNo');
    column.setVisible(!column.visible);
  }
}
