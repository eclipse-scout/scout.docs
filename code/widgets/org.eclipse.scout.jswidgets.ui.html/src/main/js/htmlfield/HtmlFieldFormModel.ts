/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, FormModel, GroupBox, HtmlField, Menu, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, StatesBox, ValueFieldPropertiesBox,
  ValueFieldPropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.HtmlFieldForm',
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
            id: 'HtmlField',
            objectType: HtmlField,
            label: 'Html Field',
            labelVisible: false,
            gridDataHints: {
              h: 3
            }
          },
          {
            id: 'StatesBox',
            objectType: StatesBox,
            fieldInitProperties: ['value']
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: TabBox,
        cssClass: 'jswidgets-configuration',
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
                    id: 'ScrollBarEnabledField',
                    objectType: CheckBoxField,
                    label: 'Scroll Bar Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'ScrollToAnchorField',
                    objectType: StringField,
                    label: 'Scroll To Anchor',
                    tooltipText: '${textKey:HtmlFieldScrollToAnchorTooltip}',
                    menus: [{
                      id: 'InsertAnchorHtmlMenu',
                      objectType: Menu,
                      text: 'Insert HTML with an anchor named "anchor"'
                    }]
                  }
                ]
              },
              {
                id: 'ValueFieldPropertiesBox',
                objectType: ValueFieldPropertiesBox
              },
              {
                id: 'FormFieldPropertiesBox',
                objectType: FormFieldPropertiesBox
              },
              {
                id: 'GridDataBox',
                objectType: GridDataBox,
                label: 'Grid Data Hints'
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

export type HtmlFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'HtmlField': HtmlField;
  'StatesBox': StatesBox;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'SelectableField': CheckBoxField;
  'ScrollBarEnabledField': CheckBoxField;
  'ScrollToAnchorField': StringField;
  'InsertAnchorHtmlMenu': Menu;
  'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
