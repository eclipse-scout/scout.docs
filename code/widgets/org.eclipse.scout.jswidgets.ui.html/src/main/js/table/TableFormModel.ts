/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AggregateTableControl, BooleanColumn, Column, DateColumn, FormModel, GroupBox, IconColumn, Menu, NumberColumn, SmartColumn, SmartField, TabItem, Table, TableField} from '@eclipse-scout/core';
import {
  ColumnPropertiesBox, ColumnPropertiesBoxWidgetMap, ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox,
  GridDataBoxWidgetMap, LocaleLookupCall, TablePropertiesBox, TablePropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.TableForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        fields: [
          {
            id: 'TableField',
            objectType: TableField,
            labelVisible: false,
            gridDataHints: {
              h: 10
            },
            table: {
              id: 'Table',
              objectType: Table,
              columns: [
                {
                  id: 'StringColumn',
                  objectType: Column,
                  text: 'String Column',
                  width: 120
                },
                {
                  id: 'DateColumn',
                  objectType: DateColumn,
                  text: 'Date Column',
                  width: 120
                },
                {
                  id: 'NumberColumn',
                  objectType: NumberColumn,
                  text: 'Number Column',
                  width: 120
                },
                {
                  id: 'SmartColumn',
                  objectType: SmartColumn,
                  text: 'Smart Column',
                  lookupCall: LocaleLookupCall,
                  width: 120
                },
                {
                  id: 'BooleanColumn',
                  objectType: BooleanColumn,
                  text: 'Boolean Column',
                  width: 130
                },
                {
                  id: 'IconColumn',
                  objectType: IconColumn,
                  text: 'Icon Column',
                  width: 110
                },
                {
                  id: 'HtmlColumn',
                  objectType: Column,
                  text: 'Html Column',
                  htmlEnabled: true,
                  width: 110
                }
              ],
              tableControls: [
                {
                  id: 'AggregateTableControl',
                  objectType: AggregateTableControl
                }
              ],
              menus: [
                {
                  id: 'AddRowMenu',
                  objectType: Menu,
                  text: '${textKey:AddRow}',
                  keyStroke: 'insert'
                },
                {
                  id: 'MoveMenu',
                  objectType: Menu,
                  text: '${textKey:Move}',
                  menuTypes: [
                    Table.MenuType.SingleSelection
                  ],
                  childActions: [
                    {
                      id: 'MoveToTopMenu',
                      objectType: Menu,
                      text: '${textKey:MoveToTop}',
                      menuTypes: [
                        Table.MenuType.SingleSelection
                      ]
                    },
                    {
                      id: 'MoveUpMenu',
                      objectType: Menu,
                      text: '${textKey:MoveUp}',
                      menuTypes: [
                        Table.MenuType.SingleSelection
                      ]
                    },
                    {
                      id: 'MoveDownMenu',
                      objectType: Menu,
                      text: '${textKey:MoveDown}',
                      menuTypes: [
                        Table.MenuType.SingleSelection
                      ]
                    },
                    {
                      id: 'MoveToBottomMenu',
                      objectType: Menu,
                      text: '${textKey:MoveToBottom}',
                      menuTypes: [
                        Table.MenuType.SingleSelection
                      ]
                    }
                  ]
                },
                {
                  id: 'DeleteRowMenu',
                  objectType: Menu,
                  text: '${textKey:DeleteRow}',
                  menuTypes: [
                    Table.MenuType.SingleSelection,
                    Table.MenuType.MultiSelection
                  ],
                  keyStroke: 'delete'
                },
                {
                  id: 'ToggleRowEnabledMenu',
                  objectType: Menu,
                  text: '${textKey:ToggleRowEnabled}',
                  inheritAccessibility: false,
                  menuTypes: [
                    Table.MenuType.SingleSelection,
                    Table.MenuType.MultiSelection
                  ]
                }
              ]
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: ConfigurationBox,
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: TabItem,
            label: 'Properties',
            fields: [
              {
                id: 'PropertiesBox',
                objectType: TablePropertiesBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false
              },
              {
                id: 'FormFieldPropertiesBox',
                objectType: FormFieldPropertiesBox,
                expanded: false
              },
              {
                id: 'GridDataBox',
                objectType: GridDataBox,
                label: 'Grid Data Hints',
                expanded: false
              }
            ]
          },
          {
            id: 'ColumnProperties',
            objectType: TabItem,
            label: 'Column Properties',
            fields: [
              {
                id: 'Column.TargetField',
                objectType: SmartField,
                label: 'Target'
              },
              {
                id: 'Column.PropertiesBox',
                objectType: ColumnPropertiesBox,
                labelVisible: true
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: TabItem,
            label: 'Actions',
            fields: [
              {
                id: 'FormFieldActionsBox',
                objectType: FormFieldActionsBox
              },
              {
                id: 'WidgetActionsBox',
                objectType: WidgetActionsBox
              }
            ]
          },
          {
            id: 'EventsTab',
            objectType: EventsTab
          }
        ]
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type TableFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'TableField': TableField;
  'Table': TableFieldTable;
  'ConfigurationBox': ConfigurationBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': TablePropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ColumnProperties': TabItem;
  'Column.TargetField': SmartField<any>;
  'Column.PropertiesBox': ColumnPropertiesBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & TableFieldTableWidgetMap & TablePropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & ColumnPropertiesBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;

export class TableFieldTable extends Table {
  declare widgetMap: TableFieldTableWidgetMap;
  declare columnMap: TableFieldTableColumnMap;
}

export type TableFieldTableWidgetMap = {
  'AggregateTableControl': AggregateTableControl;
  'AddRowMenu': Menu;
  'MoveMenu': Menu;
  'MoveToTopMenu': Menu;
  'MoveUpMenu': Menu;
  'MoveDownMenu': Menu;
  'MoveToBottomMenu': Menu;
  'DeleteRowMenu': Menu;
  'ToggleRowEnabledMenu': Menu;
};

export type TableFieldTableColumnMap = {
  'StringColumn': Column;
  'DateColumn': DateColumn;
  'NumberColumn': NumberColumn;
  'SmartColumn': SmartColumn<any>;
  'BooleanColumn': BooleanColumn;
  'IconColumn': IconColumn;
  'HtmlColumn': Column;
};
