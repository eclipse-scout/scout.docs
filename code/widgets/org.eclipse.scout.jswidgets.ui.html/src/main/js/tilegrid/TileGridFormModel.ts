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
import {CheckBoxField, FormModel, GroupBox, LabelField, Menu, NumberField, SmartField, TabItem, TileField, TileGrid} from '@eclipse-scout/core';
import {
  ConfigurationBox, CustomTile, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, TileGridLayoutConfigBox,
  WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';
import {TileGridLayoutConfigBoxWidgetMap} from './TileGridLayoutConfigBoxModel';

export default (): FormModel => ({
  id: 'jswidgets.TileGridForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        menus: [
          {
            id: 'InsertMenu',
            objectType: Menu,
            text: 'Insert tile',
            keyStroke: 'insert'
          },
          {
            id: 'InsertManyMenu',
            objectType: Menu,
            text: 'Insert many tiles'
          },
          {
            id: 'DeleteMenu',
            objectType: Menu,
            text: 'Delete selected tiles',
            keyStroke: 'delete'
          },
          {
            id: 'SelectNextMenu',
            objectType: Menu,
            text: 'Select next tile'
          },
          {
            id: 'SelectAllMenu',
            objectType: Menu,
            text: 'Select all tiles'
          },
          {
            id: 'SortMenu',
            objectType: Menu,
            text: 'Sort',
            childActions: [
              {
                id: 'SortAscMenu',
                objectType: Menu,
                text: 'Ascending'
              },
              {
                id: 'SortDescMenu',
                objectType: Menu,
                text: 'Descending'
              }
            ]
          }
        ],
        fields: [
          {
            id: 'TileField',
            objectType: TileField,
            labelVisible: false,
            gridDataHints: {
              h: 8
            },
            tileGrid: {
              id: 'TileGrid',
              objectType: TileGrid,
              scrollable: true,
              textFilterEnabled: true,
              cssClass: 'has-custom-tiles',
              gridColumnCount: 5,
              layoutConfig: {
                columnWidth: 100,
                rowHeight: 100
              },
              tiles: [
                {
                  objectType: CustomTile,
                  label: 'Tile 1'
                },
                {
                  objectType: CustomTile,
                  label: 'Tile 2'
                },
                {
                  objectType: CustomTile,
                  label: 'Tile 3'
                },
                {
                  objectType: CustomTile,
                  label: 'Tile 4'
                },
                {
                  objectType: CustomTile,
                  label: 'Tile 5'
                }
              ]
            }
          },
          {
            id: 'StatusField',
            objectType: LabelField,
            labelVisible: false,
            gridDataHints: {
              horizontalAlignment: 1
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
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'SelectableField',
                    objectType: CheckBoxField,
                    label: 'Selectable',
                    labelVisible: false
                  },
                  {
                    id: 'MultiSelectField',
                    objectType: CheckBoxField,
                    label: 'Multi Select',
                    labelVisible: false
                  },
                  {
                    id: 'ScrollableField',
                    objectType: CheckBoxField,
                    label: 'Scrollable',
                    labelVisible: false
                  },
                  {
                    id: 'InvertColorsField',
                    objectType: CheckBoxField,
                    label: 'Invert Colors',
                    labelVisible: false
                  },
                  {
                    id: 'WithPlaceholdersField',
                    objectType: CheckBoxField,
                    label: 'With Placeholders',
                    labelVisible: false
                  },
                  {
                    id: 'VirtualField',
                    objectType: CheckBoxField,
                    label: 'Virtual',
                    labelVisible: false,
                    tooltipText: '${textKey:TileGridVirtualTooltip}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'TextFilterEnabledField',
                    objectType: CheckBoxField,
                    label: 'Text Filter Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'GridColumnCountField',
                    objectType: NumberField,
                    label: 'Grid Column Count'
                  },
                  {
                    id: 'LogicalGridField',
                    objectType: SmartField,
                    label: 'Logical Grid',
                    lookupCall: 'jswidgets.LogicalGridLookupCall',
                    tooltipText: '${textKey:LogicalGridWithRefTooltip}'
                  },
                  {
                    id: 'ColorSchemeField',
                    objectType: SmartField,
                    label: 'Color Scheme',
                    lookupCall: 'jswidgets.ColorSchemeLookupCall'
                  },
                  {
                    id: 'TileTypeField',
                    objectType: SmartField,
                    displayStyle: 'dropdown',
                    label: 'Tile Type',
                    tooltipText: '${textKey:TileTypeTooltip}',
                    lookupCall: 'jswidgets.TileTypeLookupCall',
                    value: 'simple'
                  }
                ]
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
              },
              {
                id: 'LayoutConfigBox',
                objectType: TileGridLayoutConfigBox,
                expanded: false
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

export type TileGridFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'InsertMenu': Menu;
    'InsertManyMenu': Menu;
    'DeleteMenu': Menu;
    'SelectNextMenu': Menu;
    'SelectAllMenu': Menu;
    'SortMenu': Menu;
    'SortAscMenu': Menu;
    'SortDescMenu': Menu;
    'TileField': TileField;
    'TileGrid': TileGrid;
    'StatusField': LabelField;
    'ConfigurationBox': ConfigurationBox;
    'PropertiesTab': TabItem;
    'PropertiesBox': GroupBox;
    'SelectableField': CheckBoxField;
    'MultiSelectField': CheckBoxField;
    'ScrollableField': CheckBoxField;
    'InvertColorsField': CheckBoxField;
    'WithPlaceholdersField': CheckBoxField;
    'VirtualField': CheckBoxField;
    'TextFilterEnabledField': CheckBoxField;
    'GridColumnCountField': NumberField;
    'LogicalGridField': SmartField<string>;
    'ColorSchemeField': SmartField<string>;
    'TileTypeField': SmartField<'default' | 'simple'>;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'LayoutConfigBox': TileGridLayoutConfigBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & TileGridLayoutConfigBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
