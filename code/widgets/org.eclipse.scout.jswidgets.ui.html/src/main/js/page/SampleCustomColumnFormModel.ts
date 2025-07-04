/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CancelMenu, Column, Form, FormModel, GroupBox, ListBox, NumberField, OkMenu, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {SampleCustomColumnType, SampleCustomColumnTypeLookupCall} from '../index';

export default (): FormModel => ({
  objectType: Form,
  title: 'Column Form',
  saveNeededVisible: false,
  rootGroupBox: {
    objectType: GroupBox,
    gridColumnCount: 1,
    menus: [
      {
        id: 'OkMenu',
        objectType: OkMenu
      },
      {
        id: 'CancelMenu',
        objectType: CancelMenu
      }
    ],
    fields: [
      {
        id: 'TabBox',
        objectType: TabBox,
        tabItems: [
          {
            id: 'CustomColumnTab',
            objectType: TabItem,
            label: 'New column',
            gridColumnCount: 1,
            fields: [
              {
                id: 'NameField',
                objectType: StringField,
                label: 'Name'
              },
              {
                id: 'ColumnTypeField',
                objectType: SmartField<SampleCustomColumnType>,
                label: 'Column type',
                mandatory: true,
                displayStyle: SmartField.DisplayStyle.DROPDOWN,
                lookupCall: SampleCustomColumnTypeLookupCall
              },
              {
                id: 'WidthField',
                objectType: NumberField,
                label: 'Width',
                value: 150,
                minValue: Column.DEFAULT_MIN_WIDTH
              }
            ]
          },
          {
            id: 'HiddenColumnsTab',
            objectType: TabItem,
            label: 'Hidden columns',
            gridColumnCount: 1,
            fields: [
              {
                id: 'HiddenColumnsField',
                objectType: ListBox<Column<any>>,
                label: 'Hidden columns',
                labelVisible: false,
                gridDataHints: {
                  h: 3
                }
              }
            ]
          }
        ]
      }
    ]
  }
});

export interface SampleCustomColumnFormModel extends FormModel {
  hiddenColumns?: Column[];
}

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type SampleCustomColumnFormWidgetMap = {
  'OkMenu': OkMenu;
  'CancelMenu': CancelMenu;
  'TabBox': TabBox;
  'CustomColumnTab': TabItem;
  'NameField': StringField;
  'ColumnTypeField': SmartField<SampleCustomColumnType>;
  'WidthField': NumberField;
  'HiddenColumnsTab': TabItem;
  'HiddenColumnsField': ListBox<Column<any>>;
};
