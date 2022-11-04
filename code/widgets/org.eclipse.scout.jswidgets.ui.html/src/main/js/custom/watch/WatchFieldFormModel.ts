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
import {FormModel, GroupBox, TabBox, TabItem} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WatchField, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../../index';

export default (): FormModel => ({
  id: 'jswidgets.WatchFieldForm',
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
            id: 'WatchField',
            objectType: WatchField,
            label: 'Watch',
            gridDataHints: {
              h: 5
            }
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
                id: 'FormFieldPropertiesBox',
                objectType: FormFieldPropertiesBox
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

export type WatchFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'WatchField': WatchField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
