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
import {Form, icons, models} from '@eclipse-scout/core';
import HierarchicalTableFormModel from './HierarchicalTableFormModel';
import {ColumnLookupCall} from '../../index';

export default class HierarchicalTableForm extends Form {

  constructor() {
    super();

    this.rowNo = 1;
    this.groupNo = 1;
  }

  static GROUP_SIZE = 2;

  _jsonModel() {
    return models.get(HierarchicalTableFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.table = this.widget('Table');

    this.widget('PropertiesBox').setTable(this.table);
    let tableField = this.widget('TableField');
    this.widget('FormFieldPropertiesBox').setField(tableField);
    this.widget('GridDataBox').setField(tableField);
    this.widget('FormFieldActionsBox').setField(tableField);
    this.widget('WidgetActionsBox').setField(this.table);
    this.widget('EventsTab').setField(this.table);

    this.widget('RemoveAll').on('action', this._onRemoveAllRows.bind(this));
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

  _onRemoveAllRows() {
    this.table.deleteAllRows();
  }

  _onInsertFew() {
    this._insertFewRows();
  }

  _onInsertMany() {
    this._insertManyRows();
  }

  _insertFewRows() {
    let daltonId = this.rowNo++,
      simpsonsId = this.rowNo++;
    this.table.insertRows(this._scrumbleOrder([{
      id: daltonId,
      iconId: icons.WORLD,
      cells: [
        'Dalton brothers', null, null, true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Joe Dalton', 'the smartest', '20.10.1940', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Wiliam Dalton', 'smarter', '03.05.1942', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Jack Dalton', 'smart', '15.09.1944', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: icons.PERSON_SOLID,
      enabled: false,
      cells: [
        'Averell Dalton', 'not so smart', '23.11.1945', true
      ]
    }, {
      id: simpsonsId,
      iconId: icons.GROUP,
      cells: [
        'Simpsons', 'a simple family', null, false
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Homer Simpson', 'Daddy', '23.12.1960', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Marge Simpson', 'Mom', '02.05.1964', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Bart Simpson', 'Boy', '08.10.1985', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Lisa Simpson', 'Girl', '17.03.1987', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: icons.PERSON_SOLID,
      cells: [
        'Maggie Simpson', 'Baby', '14.08.1988', true
      ]
    }

    ]));
  }

  _insertManyRows() {
    let i = 0,
      allRows = [],
      createParentWithManyChildren = function(id, name, childCount) {
        let rows = [],
          i,
          rowId;
        rows.push(createRow(id, null, null, [name + '_parent' + ' (' + childCount + ')', null, null]));

        for (i = 0; i < childCount; i++) {
          rowId = this.rowNo++;
          rows.push(createRow(rowId, id, null, [
            name + rowId,
            'Any title',
            '20.10.2015'
          ]));
        }
        return rows;
      }.bind(this);

    for (i = 0; i < 100; i++) {
      allRows = allRows.concat(createParentWithManyChildren(this.rowNo++, 'Abc', Math.floor(Math.random() * 100)));
    }

    this.table.insertRows(allRows);

    function createRow(id, parentId, iconId, cells) {
      return {
        id: id,
        parentRow: parentId,
        iconId: iconId,
        cells: cells
      };
    }

  }

  _scrumbleOrder(rows) {
    return rows.sort((a, b) => {
      return 0.5 - Math.random();
    });
  }

  _onAddRowMenuAction() {
    let id = this.rowNo++,
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
        '20.10.2015'
      ]
    });
  }

  _onDeleteRowMenuAction() {
    this.table.deleteRows(this.table.selectedRows);
  }

  _onToggleGroupNoColumnMenuAction() {
    let column = this.table.columnById('GroupNo');
    column.setVisible(!column.visible);
  }
}
