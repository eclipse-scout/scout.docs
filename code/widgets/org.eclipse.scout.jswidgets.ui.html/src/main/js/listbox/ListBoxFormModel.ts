/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormModel, GroupBox, ListBox, LookupCall, SmartField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, LocaleLookupCall, LookupCallLookupCall, TablePropertiesBox,
  TablePropertiesBoxWidgetMap, ValueFieldPropertiesBox, ValueFieldPropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.ListBoxForm',
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
            id: 'ListBox',
            objectType: ListBox,
            lookupCall: LocaleLookupCall,
            label: 'List Box',
            gridDataHints: {
              h: 6,
              weightY: 0
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: TabBox,
        cssClass: 'jswidgets-configuration',
        gridDataHints: {
          h: 6,
          weightY: -1
        },
        gridColumnCount: 1,
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: TabItem,
            label: 'Properties',
            fields: [
              {
                id: 'LookupCallField',
                objectType: SmartField,
                label: 'LookupCall',
                lookupCall: LookupCallLookupCall
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
                id: 'PropertiesBox',
                objectType: TablePropertiesBox,
                expandable: true
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

export type ListBoxFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'ListBox': ListBox<any>;
    'ConfigurationBox': TabBox;
    'PropertiesTab': TabItem;
    'LookupCallField': SmartField<LookupCall<any>>;
    'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'PropertiesBox': TablePropertiesBox;
    'GridDataBox': GridDataBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & TablePropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap
  & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
