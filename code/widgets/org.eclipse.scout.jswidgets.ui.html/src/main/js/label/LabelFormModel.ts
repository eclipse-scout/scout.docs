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
import {CheckBoxField, FormModel, GroupBox, Label, StringField, TabBox, TabItem, WidgetField} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.LabelForm',
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
            objectType: WidgetField,
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              useUiHeight: true
            },
            fieldWidget: {
              id: 'Label',
              objectType: Label,
              value: '${textKey:LabelValue}'
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
                    id: 'HtmlEnabledField',
                    objectType: CheckBoxField,
                    label: 'Html Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'ScrollableField',
                    objectType: CheckBoxField,
                    label: 'Scrollable',
                    labelVisible: false
                  },
                  {
                    id: 'ValueField',
                    objectType: StringField,
                    label: 'Value',
                    multilineText: true,
                    gridDataHints: {
                      h: 3,
                      weightY: 0
                    }
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

export type LabelFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'Label': Label;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'HtmlEnabledField': CheckBoxField;
  'ScrollableField': CheckBoxField;
  'ValueField': StringField;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
