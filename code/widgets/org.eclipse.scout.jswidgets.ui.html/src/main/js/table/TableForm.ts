/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Column, dates, Form, FormModel, GroupBox, HtmlTile, icons, InitModelOf, MessageBoxes, models, numbers, scout, TabItem, TableAppLinkActionEvent, TableRowModel} from '@eclipse-scout/core';
import {BooleanColumnPropertiesBox, ColumnLookupCall, DateColumnPropertiesBox, LocaleLookupCall, NumberColumnPropertiesBox, SmartColumnPropertiesBox, TableFieldTable, TableFormWidgetMap} from '../index';
import TableFormModel from './TableFormModel';

export class TableForm extends Form {
  declare widgetMap: TableFormWidgetMap;

  table: TableFieldTable;
  rowNo: number;
  groupNo: number;

  constructor() {
    super();

    this.rowNo = 0;
    this.groupNo = 0;
  }

  static GROUP_SIZE = 2;

  protected override _jsonModel(): FormModel {
    return models.get(TableFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.table = this.widget('Table');
    this.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
    this.widget('MoveToTopMenu').on('action', this._onMoveToTopMenuAction.bind(this));
    this.widget('MoveUpMenu').on('action', this._onMoveUpMenuAction.bind(this));
    this.widget('MoveDownMenu').on('action', this._onMoveDownMenuAction.bind(this));
    this.widget('MoveToBottomMenu').on('action', this._onMoveToBottomMenuAction.bind(this));
    this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
    this.widget('ToggleRowEnabledMenu').on('action', this._onToggleRowEnabledMenuAction.bind(this));
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
      let newPropertiesBoxField = this._createPropertiesBox(newColumn, columnPropertyTab) as ColumnPropertiesBox;
      if (newPropertiesBoxField !== null) {
        columnPropertyTab.insertFieldBefore(newPropertiesBoxField, columnPropertiesBox);
        newPropertiesBoxField.setColumn(newColumn);
      }
      this._removePropertiesBoxes(newColumn.objectType as string, columnPropertyTab);
    });
    targetField.setValue(this.table.visibleColumns()[0]);

    this.table.insertRows([this._createRow(), this._createRow(), this._createRow()]);

    let stringColumn = this.table.columnById('StringColumn');
    let dateColumn = this.table.columnById('DateColumn');
    let iconColumn = this.table.columnById('IconColumn');
    this.table.setTileProducer(row => {
      let icon = icons.parseIconId(this.table.cell(iconColumn, row).iconId);
      let model = {
        parent: this,
        content: '<div style="text-align: center;"><p>' + this.table.cellText(stringColumn, row) + '</p></div>' +
          '<div class="font-icon" style="font-size: 50px; text-align: center;">' + icon.iconCharacter + '</div>' +
          '<div style="text-align: center;"><p>' + this.table.cellText(dateColumn, row) + '</p></div>'
      };
      return scout.create(HtmlTile, model);
    });
  }

  protected _createPropertiesBox(newColumn: Column, parent: TabItem): GroupBox {
    switch (newColumn.objectType) {
      case 'BooleanColumn':
        return scout.create(BooleanColumnPropertiesBox, {
          id: 'BooleanColumnPropertyField',
          label: 'Boolean Column Properties',
          parent: parent
        });
      case 'DateColumn':
        return scout.create(DateColumnPropertiesBox, {
          id: 'DateColumnPropertyField',
          label: 'Date Column Properties',
          parent: parent
        });
      case 'NumberColumn':
        return scout.create(NumberColumnPropertiesBox, {
          id: 'NumberColumnPropertyField',
          label: 'Number Column Properties',
          parent: parent
        });
      case 'SmartColumn':
        return scout.create(SmartColumnPropertiesBox, {
          id: 'SmartColumnPropertyField',
          label: 'Smart Column Properties',
          parent: parent
        });
      default:
        return null;
    }
  }

  protected _removePropertiesBoxes(newColumnTypeName: string, tabBox: TabItem) {
    if (newColumnTypeName !== 'BooleanColumn') {
      this._removePropertyBox('BooleanColumnPropertyField', tabBox);
    }
    if (newColumnTypeName !== 'NumberColumn') {
      this._removePropertyBox('NumberColumnPropertyField', tabBox);
    }
    if (newColumnTypeName !== 'DateColumn') {
      this._removePropertyBox('DateColumnPropertyField', tabBox);
    }
    if (newColumnTypeName !== 'SmartColumn') {
      this._removePropertyBox('SmartColumnPropertyField', tabBox);
    }
  }

  protected _removePropertyBox(propertyBoxId: string, tabBox: TabItem) {
    let boxToRemove = this.widget(propertyBoxId, GroupBox) as ColumnPropertiesBox;
    if (boxToRemove) {
      boxToRemove.setColumn(null);
      tabBox.deleteField(boxToRemove);
    }
  }

  protected _createRow(): TableRowModel {
    let date = new Date();
    let iconList = [icons.STAR, icons.CLOCK, icons.FOLDER];
    let locales = LocaleLookupCall.DATA.map(lookupRow => {
      return lookupRow[0];
    });

    let rowIcon = iconList[this.rowNo % iconList.length];
    let iconValue = iconList[this.rowNo % iconList.length];
    let internalValue = 'ROW_' + this.rowNo;
    let stringValue = 'Row #' + this.rowNo;
    let dateValue = dates.shift(date, 0, 0, -this.groupNo);
    let numberValue = this.rowNo;
    let numberValue2 = numbers.randomInt(1000);
    let smartValue = locales[this.rowNo % locales.length];
    let booleanValue = this.rowNo % 2 === 0;
    let htmlValue = '<span class="app-link" data-ref="' + this.rowNo + '">App Link</span>';

    if (this.rowNo % TableForm.GROUP_SIZE === 0) {
      this.groupNo++;
    }
    this.rowNo++;

    return {
      iconId: rowIcon,
      cells: [internalValue, stringValue, dateValue, numberValue, numberValue2, smartValue, booleanValue, iconValue, htmlValue]
    };
  }

  protected _onAddRowMenuAction() {
    this.table.insertRow(this._createRow());
  }

  protected _onMoveToTopMenuAction() {
    this.table.moveRowToTop(this.table.selectedRows[0]);
  }

  protected _onMoveUpMenuAction() {
    this.table.moveVisibleRowUp(this.table.selectedRows[0]);
  }

  protected _onMoveDownMenuAction() {
    this.table.moveVisibleRowDown(this.table.selectedRows[0]);
  }

  protected _onMoveToBottomMenuAction() {
    this.table.moveRowToBottom(this.table.selectedRows[0]);
  }

  protected _onDeleteRowMenuAction() {
    this.table.deleteRows(this.table.selectedRows);
  }

  protected _onToggleRowEnabledMenuAction() {
    this.table.selectedRows.forEach(row => row.setEnabled(!row.enabled));
    this.table.updateRows(this.table.selectedRows);
  }

  protected _onAppLinkAction(event: TableAppLinkActionEvent) {
    MessageBoxes.createOk(this)
      .withBody('Link with ref \'' + event.ref + '\' has been clicked.')
      .buildAndOpen();
  }
}

type ColumnPropertiesBox = GroupBox & {
  setColumn(column: Column);
};
