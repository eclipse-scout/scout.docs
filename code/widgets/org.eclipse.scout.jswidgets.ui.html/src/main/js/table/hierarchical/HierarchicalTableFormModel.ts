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
import {AggregateTableControl, BooleanColumn, Column, FormModel, GroupBox, Menu, SmartField, TabItem, Table, TableField} from '@eclipse-scout/core';
import {
  ColumnPropertiesBox, ColumnPropertiesBoxWidgetMap, ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox,
  GridDataBoxWidgetMap, HierarchicalTablePropertiesBox, HierarchicalTablePropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../../index';

export default (): FormModel => ({
  id: 'jswidgets.HierarchicalTableForm',
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
                  text: '${textKey:Name}',
                  id: 'Name',
                  width: 200,
                  objectType: Column
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
                  objectType: Column
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
                  menuTypes: ['Table.EmptySpace'],
                  childActions: [
                    {
                      id: 'RemoveAll',
                      objectType: Menu,
                      text: 'Remove all rows',
                      menuTypes: ['Table.EmptySpace']
                    },
                    {
                      id: 'InsertFew',
                      objectType: Menu,
                      text: 'Insert few',
                      menuTypes: ['Table.EmptySpace']
                    },
                    {
                      id: 'InsertMany',
                      objectType: Menu,
                      text: 'Insert many',
                      menuTypes: ['Table.EmptySpace']
                    }
                  ]
                },
                {
                  id: 'AddRowMenu',
                  objectType: Menu,
                  text: '${textKey:AddRow}',
                  menuTypes: ['Table.EmptySpace'],
                  keyStroke: 'insert'
                },
                {
                  id: 'DeleteRowMenu',
                  objectType: Menu,
                  text: '${textKey:DeleteRow}',
                  menuTypes: ['Table.SingleSelection', 'Table.MultiSelection'],
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
                objectType: SmartField,
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

export type HierarchicalTableFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'TableField': TableField;
    'Table': HierarchicalTableFieldTable;
    'AggregateTableControl': AggregateTableControl;
    'ContentMenu': Menu;
    'RemoveAll': Menu;
    'InsertFew': Menu;
    'InsertMany': Menu;
    'AddRowMenu': Menu;
    'DeleteRowMenu': Menu;
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
  }
  & HierarchicalTablePropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & ColumnPropertiesBoxWidgetMap & FormFieldActionsBoxWidgetMap
  & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;

export type HierarchicalTableFieldTableColumnMap = {
  'Name': Column;
  'Detail': Column;
  'Date': Column;
  'Active': BooleanColumn;
};

export class HierarchicalTableFieldTable extends Table {
  declare columnMap: HierarchicalTableFieldTableColumnMap;
}
