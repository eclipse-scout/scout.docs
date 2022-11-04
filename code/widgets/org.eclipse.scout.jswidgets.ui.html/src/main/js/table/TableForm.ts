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
import {Column, dates, Form, FormModel, GroupBox, HtmlTile, icons, InitModelOf, MessageBoxes, models, scout, TabItem, Table, TableAppLinkActionEvent, TableRowModel} from '@eclipse-scout/core';
import {BooleanColumnPropertiesBox, ColumnLookupCall, DateColumnPropertiesBox, LocaleLookupCall, NumberColumnPropertiesBox, SmartColumnPropertiesBox, TableFormWidgetMap} from '../index';
import TableFormModel from './TableFormModel';

export class TableForm extends Form {
  declare widgetMap: TableFormWidgetMap;

  table: Table;
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
      return scout.create(HtmlTile, model);
    };
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

  protected _onAppLinkAction(event: TableAppLinkActionEvent) {
    MessageBoxes.createOk(this)
      .withBody('Link with ref \'' + event.ref + '\' has been clicked.')
      .buildAndOpen();
  }
}

type ColumnPropertiesBox = GroupBox & {
  setColumn(column: Column);
};
