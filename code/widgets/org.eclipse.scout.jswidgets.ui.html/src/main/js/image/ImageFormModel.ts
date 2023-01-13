/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, FormModel, GroupBox, Image, ProposalField, TabBox, TabItem, WidgetField} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, GridDataBox, GridDataBoxWidgetMap, ImageLookupCall, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.ImageForm',
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
            id: 'WidgetField',
            objectType: WidgetField,
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              h: 4,
              weightY: 0
            },
            fieldWidget: {
              id: 'Image',
              objectType: Image,
              imageUrl: 'img/eclipse_scout_logo.png'
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
                id: 'PropertiesBox',
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'AutoFitField',
                    objectType: CheckBoxField,
                    label: 'Auto Fit',
                    labelVisible: false
                  },
                  {
                    id: 'ImageUrlField',
                    objectType: ProposalField,
                    label: 'Image Url',
                    lookupCall: ImageLookupCall
                  }
                ]
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

export type ImageFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'WidgetField': WidgetField;
  'Image': Image;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'AutoFitField': CheckBoxField;
  'ImageUrlField': ProposalField;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & GridDataBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
