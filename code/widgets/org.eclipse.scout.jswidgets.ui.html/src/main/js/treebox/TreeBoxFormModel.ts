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
import {FormModel, GroupBox, LookupCall, SmartField, TabBox, TabItem, TreeBox} from '@eclipse-scout/core';
import {
  EventsTab,
  EventsTabWidgetMap,
  FormFieldActionsBox,
  FormFieldActionsBoxWidgetMap,
  FormFieldPropertiesBox,
  FormFieldPropertiesBoxWidgetMap,
  GridDataBox,
  GridDataBoxWidgetMap,
  LookupCallLookupCall,
  TreePropertiesBox,
  TreePropertiesBoxWidgetMap,
  ValueFieldPropertiesBox,
  ValueFieldPropertiesBoxWidgetMap,
  WidgetActionsBox,
  WidgetActionsBoxWidgetMap,
  WorldLookupCall
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.TreeBoxForm',
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
            id: 'TreeBox',
            objectType: TreeBox,
            lookupCall: WorldLookupCall,
            label: 'Tree Box',
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
                id: 'PropertiesBox',
                objectType: TreePropertiesBox,
                labelVisible: false,
                borderVisible: false
              },
              {
                id: 'ValueFieldPropertiesBox',
                objectType: ValueFieldPropertiesBox,
                expanded: false
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

export type TreeBoxFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'TreeBox': TreeBox<any>;
    'ConfigurationBox': TabBox;
    'PropertiesTab': TabItem;
    'LookupCallField': SmartField<LookupCall<any>>;
    'PropertiesBox': TreePropertiesBox;
    'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & TreePropertiesBoxWidgetMap & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
