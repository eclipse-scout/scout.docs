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
import {dates, Form, icons, MessageBoxes, models, scout} from '@eclipse-scout/core';
import {ColumnLookupCall, LocaleLookupCall} from '../index';
import TableFormModel from './TableFormModel';

export default class TableForm extends Form {

  constructor() {
    super();

    this.rowNo = 0;
    this.groupNo = 0;
  }

  static GROUP_SIZE = 2;

  _jsonModel() {
    return models.get(TableFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.table = this.widget('Table');
    this.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
    this.widget('MoveToTopMenu').on('action', this._onMoveToTopMenuAction.bind(this));
    this.widget('MoveUpMenu').on('action', this._onMoveUpMenuAction.bind(this));
    this.widget('MoveDownMenu').on('action', this._onMoveDownMenuAction.bind(this));
    this.widget('MoveToBottomMenu').on('action', this._onMoveToBottomMenuAction.bind(this));
    this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
    this.table.on('appLinkAction', this._onAppLinkAction.bind(this));

    this.widget('PropertiesBox').setTable(this.table);
    let tableField = this.widget('TableField');
    this.widget('FormFieldPropertiesBox').setField(tableField);
    this.widget('GridDataBox').setField(tableField);
    this.widget('WidgetActionsBox').setField(tableField);
    this.widget('FormFieldActionsBox').setField(tableField);
    this.widget('EventsTab').setField(this.table);

    let targetField = this.widget('Column.TargetField');
    targetField.setLookupCall(new ColumnLookupCall(this.table));
    targetField.on('propertyChange:value', event => {
      let newColumn = event.newValue;

      let columnPropertiesBox = this.widget('Column.PropertiesBox');
      columnPropertiesBox.setColumn(newColumn);
      columnPropertiesBox.setEnabled(!!newColumn);

      let columnPropertyTab = this.widget('ColumnProperties');
      let newPropertiesBoxField = this._createPropertiesBox(newColumn, columnPropertyTab);
      if (newPropertiesBoxField !== null) {
        columnPropertyTab.insertFieldBefore(newPropertiesBoxField, columnPropertiesBox);
        newPropertiesBoxField.setColumn(newColumn);
      }
      this._removePropertiesBoxes(newColumn.objectType, columnPropertyTab);
    });
    targetField.setValue(this.table.columns[0]);

    this.table.insertRows([this._createRow(), this._createRow(), this._createRow()]);

    this.table.createTileForRow = function(row) {
      let icon = icons.parseIconId(row.cells[5].iconId);
      let model = {
        parent: this,
        content: '<div style="text-align: center;"><p>' + row.cells[0].text + '</p></div>' +
          '<div class="font-icon" style="font-size: 50px; text-align: center;">' + icon.iconCharacter + '</div>' +
          '<div style="text-align: center;"><p>' + row.cells[1].text + '</p></div>'
      };
      return scout.create('HtmlTile', model);
    };
  }

  _createPropertiesBox(newColumn, parent) {
    switch (newColumn.objectType) {
      case 'BooleanColumn':
        return scout.create('jswidgets.BooleanColumnPropertiesBox', {
          id: 'BooleanColumnPropertyField',
          label: 'Boolean Column Properties',
          parent: parent
        });
      case 'DateColumn':
        return scout.create('jswidgets.DateColumnPropertiesBox', {
          id: 'DateColumnPropertyField',
          label: 'Date Column Properties',
          parent: parent
        });
      case 'NumberColumn':
        return scout.create('jswidgets.NumberColumnPropertiesBox', {
          id: 'NumberColumnPropertyField',
          label: 'Number Column Properties',
          parent: parent
        });
      case 'SmartColumn':
        return scout.create('jswidgets.SmartColumnPropertiesBox', {
          id: 'SmartColumnPropertyField',
          label: 'Smart Column Properties',
          parent: parent
        });
      default:
        return null;
    }
  }

  _removePropertiesBoxes(newColumnTypeName, tabBox) {
    if (newColumnTypeName !== 'BooleanColumn') {
      this._removePropertyBox('BooleanColumnPropertyField', tabBox);
    }
    if (newColumnTypeName !== 'NumberColumn') {
      this._removePropertyBox('NumberColumnPropertyField', tabBox);
    }
    if (newColumnTypeName !== 'DateColumn') {
      this._removePropertyBox('DateColumnPropertyField', tabBox);
    }
  }

  _removePropertyBox(propertyBoxId, tabBox) {
    let boxToRemove = this.widget(propertyBoxId);
    if (boxToRemove) {
      boxToRemove.setColumn(null);
      tabBox.deleteField(boxToRemove);
    }
  }

  _createRow() {
    let date = new Date();
    let iconList = [icons.STAR, icons.CLOCK, icons.FOLDER];
    let locales = LocaleLookupCall.DATA.map(lookupRow => {
      return lookupRow[0];
    });

    let rowIcon = iconList[this.rowNo % iconList.length];
    let iconValue = iconList[this.rowNo % iconList.length];
    let stringValue = 'Row #' + this.rowNo;
    let dateValue = dates.shift(date, 0, 0, -this.groupNo);
    let numberValue = this.rowNo;
    let smartValue = locales[this.rowNo % locales.length];
    let booleanValue = this.rowNo % 2 === 0;
    let htmlValue = '<span class="app-link" data-ref="' + this.rowNo + '">App Link</span>';

    if (this.rowNo % TableForm.GROUP_SIZE === 0) {
      this.groupNo++;
    }
    this.rowNo++;

    return {
      iconId: rowIcon,
      cells: [stringValue, dateValue, numberValue, smartValue, booleanValue, iconValue, htmlValue]
    };
  }

  _onAddRowMenuAction() {
    this.table.insertRow(this._createRow());
  }

  _onMoveToTopMenuAction() {
    this.table.moveRowToTop(this.table.selectedRows[0]);
  }

  _onMoveUpMenuAction() {
    this.table.moveVisibleRowUp(this.table.selectedRows[0]);
  }

  _onMoveDownMenuAction() {
    this.table.moveVisibleRowDown(this.table.selectedRows[0]);
  }

  _onMoveToBottomMenuAction() {
    this.table.moveRowToBottom(this.table.selectedRows[0]);
  }

  _onDeleteRowMenuAction() {
    this.table.deleteRows(this.table.selectedRows);
  }

  _onAppLinkAction(event) {
    MessageBoxes.createOk(this)
      .withBody('Link with ref \'' + event.ref + '\' has been clicked.')
      .buildAndOpen();
  }
}
