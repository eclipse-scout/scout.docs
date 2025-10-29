/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

import {
  CheckBoxField, FormModel, GroupBox, Menu, SimpleTabArea, SimpleTabAreaDisplayStyle, SimpleTabAreaPosition, SimpleTabBox, SmartField, StaticLookupCall, Status, StatusSeverity, StringField, TabItem, WidgetField
} from '@eclipse-scout/core';
import {ConfigurationBox, EventsTab, EventsTabWidgetMap, IconIdLookupCall, StatusSeverityLookupCall, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        gridDataHints: {
          weightY: 1
        },
        responsive: false,
        fields: [
          {
            id: 'WidgetField',
            objectType: WidgetField,
            labelVisible: false,
            statusVisible: false,
            scrollable: false,
            gridDataHints: {
              weightY: 0,
              h: 10
            },
            fieldWidget: {
              id: 'SimpleTabBox',
              objectType: SimpleTabBox
            }
          }
        ],
        menus: [
          {
            id: 'AddTabMenu',
            objectType: Menu,
            text: 'Add tab'
          },
          {
            id: 'DeleteTabMenu',
            objectType: Menu,
            text: 'Delete tab'
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
                id: 'SimpleTabAreaPropertiesBox',
                objectType: GroupBox,
                label: 'Tab Area Properties',
                expandable: true,
                fields: [
                  {
                    id: 'DisplayStyleField',
                    objectType: SmartField<SimpleTabAreaDisplayStyle>,
                    label: 'Display Style',
                    lookupCall: {
                      objectType: StaticLookupCall<SimpleTabAreaDisplayStyle>,
                      data: [
                        [SimpleTabArea.DisplayStyle.DEFAULT, 'Default'],
                        [SimpleTabArea.DisplayStyle.SPREAD_EVEN, 'Spread even']
                      ]
                    }
                  },
                  {
                    id: 'PositionField',
                    objectType: SmartField<SimpleTabAreaPosition>,
                    label: 'Position',
                    lookupCall: {
                      objectType: StaticLookupCall<SimpleTabAreaPosition>,
                      data: [
                        ['top', 'Top'],
                        ['right', 'Right'],
                        ['bottom', 'Bottom'],
                        ['left', 'Left']
                      ]
                    }
                  }
                ]
              },
              {
                id: 'SimpleTabPropertiesBox',
                objectType: GroupBox,
                label: 'Tab Properties',
                enabled: false,
                expandable: true,
                fields: [
                  {
                    id: 'TitleField',
                    objectType: StringField,
                    label: 'Title'
                  },
                  {
                    id: 'SubTitleField',
                    objectType: StringField,
                    label: 'Sub Title'
                  },
                  {
                    id: 'IconIdField',
                    objectType: SmartField<string>,
                    lookupCall: IconIdLookupCall,
                    label: 'Icon Id'
                  },
                  {
                    id: 'ClosableField',
                    objectType: CheckBoxField,
                    label: 'Closable'
                  },
                  {
                    id: 'StatusField',
                    objectType: SmartField<StatusSeverity>,
                    lookupCall: StatusSeverityLookupCall,
                    label: 'Status'
                  }
                ]
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: TabItem,
            label: 'Actions',
            fields: [
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

export type SimpleTabBoxFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'WidgetField': WidgetField;
  'SimpleTabBox': SimpleTabBox;
  'AddTabMenu': Menu;
  'DeleteTabMenu': Menu;
  'ConfigurationBox': ConfigurationBox;
  'PropertiesTab': TabItem;
  'SimpleTabAreaPropertiesBox': GroupBox;
  'DisplayStyleField': SmartField<SimpleTabAreaDisplayStyle>;
  'PositionField': SmartField<SimpleTabAreaPosition>;
  'SimpleTabPropertiesBox': GroupBox;
  'TitleField': StringField;
  'SubTitleField': StringField;
  'IconIdField': SmartField<string>;
  'ClosableField': CheckBoxField;
  'StatusField': SmartField<StatusSeverity>;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
