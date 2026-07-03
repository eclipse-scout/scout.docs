/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AggregateTableControl, BooleanColumn, Column, DateColumn, FormModel, GroupBox, Menu, SmartField, TabItem, Table, TableField} from '@eclipse-scout/core';
import {
  ColumnPropertiesBox, ColumnPropertiesBoxWidgetMap, ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox,
  GridDataBoxWidgetMap, HierarchicalTablePropertiesBox, HierarchicalTablePropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../../index';

export default (): FormModel => ({
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
              rowsDraggable: true,
              columns: [
                {
                  text: '${textKey:Name}',
                  id: 'Name',
                  width: 200,
                  objectType: Column,
                  summary: true
                },
                {
                  text: '${textKey:Detail}',
                  id: 'Detail',
                  width: 175,
                  objectType: Column
                },
                {
                  text: 'Date',
                  id: 'Date',
                  width: 200,
                  objectType: DateColumn
                },
                {
                  text: 'Active',
                  id: 'Active',
                  width: 100,
                  objectType: BooleanColumn
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
                  id: 'ContentMenu',
                  objectType: Menu,
                  text: 'Content',
                  childActions: [
                    {
                      id: 'RemoveAll',
                      objectType: Menu,
                      text: 'Remove all rows'
                    },
                    {
                      id: 'InsertFlat',
                      objectType: Menu,
                      text: 'Insert flat list'
                    },
                    {
                      id: 'InsertFew',
                      objectType: Menu,
                      text: 'Insert few'
                    },
                    {
                      id: 'InsertMany',
                      objectType: Menu,
                      text: 'Insert many'
                    }
                  ]
                },
                {
                  id: 'AddRowMenu',
                  objectType: Menu,
                  text: '${textKey:AddRow}',
                  keyStroke: 'insert'
                },
                {
                  id: 'DeleteRowMenu',
                  objectType: Menu,
                  text: '${textKey:DeleteRow}',
                  menuTypes: [Table.MenuType.SingleSelection, Table.MenuType.MultiSelection],
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
                objectType: HierarchicalTablePropertiesBox,
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
                objectType: SmartField<Column>,
                label: 'Target'
              },
              {
                id: 'Column.PropertiesBox',
                objectType: ColumnPropertiesBox,
                labelVisible: false
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

export type DndTableFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'TableField': TableField;
  'Table': TableFieldTable1;
  'ConfigurationBox': ConfigurationBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': HierarchicalTablePropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ColumnProperties': TabItem;
  'Column.TargetField': SmartField<Column>;
  'Column.PropertiesBox': ColumnPropertiesBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & TableFieldTable1WidgetMap & HierarchicalTablePropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & ColumnPropertiesBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;

export class TableFieldTable1 extends Table {
  declare widgetMap: TableFieldTable1WidgetMap;
  declare columnMap: TableFieldTable1ColumnMap;
}

export type TableFieldTable1WidgetMap = {
  'AggregateTableControl': AggregateTableControl;
  'ContentMenu': Menu;
  'RemoveAll': Menu;
  'InsertFlat': Menu;
  'InsertFew': Menu;
  'InsertMany': Menu;
  'AddRowMenu': Menu;
  'DeleteRowMenu': Menu;
};

export type TableFieldTable1ColumnMap = {
  'Name': Column;
  'Detail': Column;
  'Date': DateColumn;
  'Active': BooleanColumn;
};
