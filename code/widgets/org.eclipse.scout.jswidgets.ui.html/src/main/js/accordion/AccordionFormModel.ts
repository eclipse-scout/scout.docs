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
import {Accordion, AccordionField, CheckBoxField, FormModel, Group, GroupBox, GroupCollapseStyle, ImageField, LabelField, Menu, SmartField, TabItem} from '@eclipse-scout/core';
import {
  CollapseStyleLookupCall, ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WidgetActionsBox,
  WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.AccordionForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 2,
        menus: [
          {
            id: 'InsertMenu',
            objectType: Menu,
            text: 'Insert group',
            keyStroke: 'insert'
          },
          {
            id: 'DeleteFirstMenu',
            objectType: Menu,
            text: 'Delete first group',
            keyStroke: 'delete'
          },
          {
            id: 'CollapseExpandFirstMenu',
            objectType: Menu,
            text: 'Collapse/Expand first group'
          },
          {
            id: 'CollapseAllMenu',
            objectType: Menu,
            text: 'Collapse all groups'
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
              h: 4,
              w: 2
            },
            accordion: {
              id: 'Accordion',
              objectType: Accordion,
              scrollable: true,
              groups: [
                {
                  objectType: Group,
                  title: 'Group with Text',
                  body: {
                    objectType: LabelField,
                    labelVisible: false,
                    wrapText: true,
                    value: '${textKey:AccordionTextGroup}'
                  }
                },
                {
                  objectType: Group,
                  title: 'Group with an Image',
                  body: {
                    objectType: ImageField,
                    labelVisible: false,
                    statusVisible: false,
                    imageUrl: 'img/eclipse_scout_logo.png'
                  }
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
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
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
                    id: 'CollapseStyleField',
                    objectType: SmartField,
                    label: 'Collapse Style',
                    lookupCall: CollapseStyleLookupCall
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

export type AccordionFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'InsertMenu': Menu;
  'DeleteFirstMenu': Menu;
  'CollapseExpandFirstMenu': Menu;
  'CollapseAllMenu': Menu;
  'SortMenu': Menu;
  'SortAscMenu': Menu;
  'SortDescMenu': Menu;
  'AccordionField': AccordionField;
  'Accordion': Accordion;
  'ConfigurationBox': ConfigurationBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'ExclusiveExpandField': CheckBoxField;
  'ScrollableField': CheckBoxField;
  'CollapseStyleField': SmartField<GroupCollapseStyle>;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
