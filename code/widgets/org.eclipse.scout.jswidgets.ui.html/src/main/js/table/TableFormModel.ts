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
import {AggregateTableControl, BooleanColumn, Column, DateColumn, FormModel, GroupBox, IconColumn, Menu, NumberColumn, SmartColumn, SmartField, TabItem, Table, TableField} from '@eclipse-scout/core';
import {
  ColumnPropertiesBox,
  ColumnPropertiesBoxWidgetMap,
  ConfigurationBox,
  EventsTab,
  EventsTabWidgetMap,
  FormFieldActionsBox,
  FormFieldActionsBoxWidgetMap,
  FormFieldPropertiesBox,
  FormFieldPropertiesBoxWidgetMap,
  GridDataBox,
  GridDataBoxWidgetMap,
  LocaleLookupCall,
  TablePropertiesBox,
  TablePropertiesBoxWidgetMap,
  WidgetActionsBox,
  WidgetActionsBoxWidgetMap
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
                  menuTypes: [
                    'Table.EmptySpace'
                  ],
                  keyStroke: 'insert'
                },
                {
                  id: 'MoveMenu',
                  objectType: Menu,
                  text: '${textKey:Move}',
                  menuTypes: [
                    'Table.SingleSelection'
                  ],
                  childActions: [
                    {
                      id: 'MoveToTopMenu',
                      objectType: Menu,
                      text: '${textKey:MoveToTop}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    },
                    {
                      id: 'MoveUpMenu',
                      objectType: Menu,
                      text: '${textKey:MoveUp}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    },
                    {
                      id: 'MoveDownMenu',
                      objectType: Menu,
                      text: '${textKey:MoveDown}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    },
                    {
                      id: 'MoveToBottomMenu',
                      objectType: Menu,
                      text: '${textKey:MoveToBottom}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    }
                  ]
                },
                {
                  id: 'DeleteRowMenu',
                  objectType: Menu,
                  text: '${textKey:DeleteRow}',
                  menuTypes: [
                    'Table.SingleSelection',
                    'Table.MultiSelection'
                  ],
                  keyStroke: 'delete'
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

export type TableFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'TableField': TableField;
    'Table': TableFieldTable;
    'AggregateTableControl': AggregateTableControl;
    'AddRowMenu': Menu;
    'MoveMenu': Menu;
    'MoveToTopMenu': Menu;
    'MoveUpMenu': Menu;
    'MoveDownMenu': Menu;
    'MoveToBottomMenu': Menu;
    'DeleteRowMenu': Menu;
    'ConfigurationBox': ConfigurationBox;
    'PropertiesTab': TabItem;
    'PropertiesBox': TablePropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'ColumnProperties': TabItem;
    'Column.TargetField': SmartField<Column>;
    'Column.PropertiesBox': ColumnPropertiesBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & TablePropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & ColumnPropertiesBoxWidgetMap & FormFieldActionsBoxWidgetMap
  & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;

export type TableFieldTableColumnMap = {
  'StringColumn': Column;
  'DateColumn': DateColumn;
  'NumberColumn': NumberColumn;
  'SmartColumn': SmartColumn<string>;
  'BooleanColumn': BooleanColumn;
  'IconColumn': IconColumn;
  'HtmlColumn': Column;
};

export class TableFieldTable extends Table {
  declare columnMap: TableFieldTableColumnMap;
}
