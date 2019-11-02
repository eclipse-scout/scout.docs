/*
 * Copyright (c) 2017 BSI Business Systems Integration AG. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0 which accompanies this
 * distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors: BSI Business Systems Integration AG - initial API and
 * implementation
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
    this.widget('FormFieldPropertiesBox').setField(this.widget('TableField'));
    this.widget('GridDataBox').setField(this.widget('TableField'));
    this.widget('WidgetActionsBox').setField(this.widget('TableField'));
    this.widget('EventsTab').setField(this.table);

    var targetField = this.widget('Column.TargetField');
    targetField.setLookupCall(new ColumnLookupCall(this.table));
    targetField.setValue(this.table.columns[0]);
    targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

    this._onTargetPropertyChange({
      propertyName: 'value',
      newValue: targetField.value
    });

    this.table.insertRows([this._createRow(), this._createRow(), this._createRow()]);

    this.table.createTileForRow = function(row) {
      var icon = icons.parseIconId(row.cells[5].iconId);
      var model = {
        parent: this,
        content: '<div style="text-align: center;"><p>' + row.cells[0].text + '</p></div>' +
          '<div class="font-icon" style="font-size: 50px; text-align: center;">' + icon.iconCharacter + '</div>' +
          '<div style="text-align: center;"><p>' + row.cells[1].text + '</p></div>'
      };
      return new scout.create('HtmlTile', model);
    };
  }

  _onTargetPropertyChange(event) {
    if (event.propertyName === 'value') {
      var oldColumn = event.oldValue;
      var newColumn = event.newValue;

      var columnPropertiesBox = this.widget('Column.PropertiesBox');
      columnPropertiesBox.setColumn(newColumn);
      columnPropertiesBox.setEnabled(!!newColumn);

      var columnPropertyTab = this.widget('ColumnProperties');
      var newPropertiesBoxField = this._createPropertiesBox(newColumn, columnPropertyTab);
      if (newPropertiesBoxField !== null) {
        columnPropertyTab.insertFieldBefore(newPropertiesBoxField, columnPropertiesBox);
        newPropertiesBoxField.setColumn(newColumn);
      }
      this._removePropertiesBoxes(newColumn.objectType, columnPropertyTab);

      this.validateLayoutTree();
    }
  }

  _createPropertiesBox(newColumn, parent) {
    switch (newColumn.objectType) {
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
      default:
        return null;
    }
  }

  _removePropertiesBoxes(newColumnTypeName, tabBox) {
    if (newColumnTypeName !== 'DateColumn') {
      this._removePropertyBox('DateColumnPropertyField', tabBox);
    }
    if (newColumnTypeName !== 'NumberColumn') {
      this._removePropertyBox('NumberColumnPropertyField', tabBox);
    }
  }

  _removePropertyBox(propertyBoxId, tabBox) {
    var boxToRemove = this.widget(propertyBoxId);
    if (boxToRemove) {
      boxToRemove.setColumn(null);
      tabBox.deleteField(boxToRemove);
    }
  }

  _createRow() {
    var date = new Date();
    var iconList = [icons.STAR_BOLD, icons.PERSON_SOLID, icons.FOLDER_BOLD];
    var locales = LocaleLookupCall.DATA.map(function(lookupRow) {
      return lookupRow[0];
    });

    var rowIcon = iconList[this.rowNo % iconList.length];
    var iconValue = iconList[this.rowNo % iconList.length];
    var stringValue = 'Row #' + this.rowNo;
    var dateValue = dates.shift(date, 0, 0, -this.groupNo);
    var numberValue = this.rowNo;
    var smartValue = locales[this.rowNo % locales.length];
    var booleanValue = this.rowNo % 2 === 0;
    var htmlValue = '<span class="app-link" data-ref="' + this.rowNo + '">App Link</span>';

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
      .withBody("Link with ref '" + event.ref + "' has been clicked.")
      .buildAndOpen();
  }
}
