/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, FormModel, GroupBox, TabBox, TabItem, TagField} from '@eclipse-scout/core';
import {
  EventsTab,
  EventsTabWidgetMap,
  FormFieldActionsBox,
  FormFieldActionsBoxWidgetMap,
  FormFieldPropertiesBox,
  FormFieldPropertiesBoxWidgetMap,
  GridDataBox,
  GridDataBoxWidgetMap,
  TagLookupCall,
  ValueFieldPropertiesBox,
  ValueFieldPropertiesBoxWidgetMap,
  WidgetActionsBox,
  WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.TagFieldForm',
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
            id: 'TagField',
            objectType: TagField,
            lookupCall: TagLookupCall,
            label: 'Tag Field',
            clickable: true
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
                id: 'TagBarProperties',
                objectType: GroupBox,
                label: 'Tag Bar Properties',
                tooltipText: '${textKey:TagBarPropertiesTooltip}',
                fields: [{
                  id: 'EnabledField',
                  objectType: CheckBoxField,
                  label: 'Enabled',
                  labelVisible: false
                }, {
                  id: 'ClickableField',
                  objectType: CheckBoxField,
                  label: 'Clickable',
                  labelVisible: false
                }]
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

export type TagFieldFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'TagField': TagField;
    'ConfigurationBox': TabBox;
    'PropertiesTab': TabItem;
    'TagBarProperties': GroupBox;
    'EnabledField': CheckBoxField;
    'ClickableField': CheckBoxField;
    'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
