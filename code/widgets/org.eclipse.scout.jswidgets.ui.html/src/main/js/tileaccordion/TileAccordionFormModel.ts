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
import {AccordionField, Button, CheckBoxField, FormModel, Group, GroupBox, LabelField, Menu, NumberField, SequenceBox, SmartField, TabItem, TileAccordion, TileGrid} from '@eclipse-scout/core';
import {
  ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, TileGridLayoutConfigBox,
  TileGridLayoutConfigBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.TileAccordionForm',
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
            id: 'GroupsMenu',
            objectType: Menu,
            text: 'Groups',
            childActions: [
              {
                id: 'InsertMenu',
                objectType: Menu,
                text: 'Insert group'
              },
              {
                id: 'DeleteFirstMenu',
                objectType: Menu,
                text: 'Delete first group'
              }
            ]
          },
          {
            id: 'InsertTileIntoGroup0Menu',
            objectType: Menu,
            text: 'Insert tile into group 0',
            keyStroke: 'insert'
          },
          {
            id: 'InsertTileIntoGroup1Menu',
            objectType: Menu,
            text: 'Insert tile into group 1'
          },
          {
            id: 'DeleteSelectedTilesMenu',
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
            id: 'AccordionField',
            objectType: AccordionField,
            labelVisible: false,
            gridDataHints: {
              h: 8
            },
            accordion: {
              id: 'Accordion',
              objectType: TileAccordion,
              scrollable: true,
              textFilterEnabled: true,
              cssClass: 'has-custom-tiles'
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
                    id: 'ExclusiveExpandField',
                    objectType: CheckBoxField,
                    label: 'Exclusive Expand',
                    labelVisible: false,
                    tooltipText: '${textKey:ExclusiveExpandTooltip}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'ScrollableField',
                    objectType: CheckBoxField,
                    label: 'Scrollable',
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
                label: 'Tile Grid Layout Config',
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
                id: 'TileAccordionActionsBox',
                objectType: GroupBox,
                fields: [
                  {
                    id: 'InsertTileField',
                    objectType: SequenceBox,
                    label: 'Insert',
                    fields: [
                      {
                        id: 'InsertTileCountField',
                        objectType: NumberField,
                        labelVisible: false,
                        statusVisible: false,
                        value: 1,
                        gridDataHints: {
                          widthInPixel: 50,
                          weightX: 0
                        }
                      },
                      {
                        id: 'InsertTileTypeField',
                        objectType: SmartField,
                        displayStyle: 'dropdown',
                        label: 'Tile Type',
                        labelVisible: false,
                        statusVisible: false,
                        lookupCall: 'jswidgets.TileTypeLookupCall',
                        value: 'simple'
                      }
                    ]
                  },
                  {
                    id: 'InsertTileIntoField',
                    objectType: SequenceBox,
                    label: 'into',
                    fields: [
                      {
                        id: 'InsertTileTargetField',
                        objectType: SmartField,
                        displayStyle: 'dropdown',
                        labelVisible: false,
                        statusVisible: false
                      },
                      {
                        id: 'InsertTileButton',
                        objectType: Button,
                        label: 'Insert'
                      }
                    ]
                  }
                ]
              },
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

export type TileAccordionFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'GroupsMenu': Menu;
    'InsertMenu': Menu;
    'DeleteFirstMenu': Menu;
    'InsertTileIntoGroup0Menu': Menu;
    'InsertTileIntoGroup1Menu': Menu;
    'DeleteSelectedTilesMenu': Menu;
    'SelectNextMenu': Menu;
    'SelectAllMenu': Menu;
    'SortMenu': Menu;
    'SortAscMenu': Menu;
    'SortDescMenu': Menu;
    'AccordionField': AccordionField;
    'Accordion': TileAccordion;
    'StatusField': LabelField;
    'ConfigurationBox': ConfigurationBox;
    'PropertiesTab': TabItem;
    'PropertiesBox': GroupBox;
    'SelectableField': CheckBoxField;
    'MultiSelectField': CheckBoxField;
    'ExclusiveExpandField': CheckBoxField;
    'ScrollableField': CheckBoxField;
    'WithPlaceholdersField': CheckBoxField;
    'VirtualField': CheckBoxField;
    'TextFilterEnabledField': CheckBoxField;
    'GridColumnCountField': NumberField;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'LayoutConfigBox': TileGridLayoutConfigBox;
    'ActionsTab': TabItem;
    'TileAccordionActionsBox': GroupBox;
    'InsertTileField': SequenceBox;
    'InsertTileCountField': NumberField;
    'InsertTileTypeField': SmartField<'default' | 'simple'>;
    'InsertTileIntoField': SequenceBox;
    'InsertTileTargetField': SmartField<Group<TileGrid>>;
    'InsertTileButton': Button;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & TileGridLayoutConfigBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
