/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, FormModel, GroupBox, SmartField, StatusSeverity, StringField, TabItem} from '@eclipse-scout/core';
import {ConfigurationBox, EventsTab, EventsTabWidgetMap, StatusSeverityLookupCall, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.TooltipForm',
  displayHint: 'view',
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
            id: 'OpenTooltipButton',
            objectType: Button,
            label: '${textKey:OpenTooltip}',
            cssClass: 'open-form-button',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0,
              verticalAlignment: 0,
              fillVertical: false,
              fillHorizontal: false,
              weightY: 1
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
                    id: 'AutoRemoveField',
                    objectType: CheckBoxField,
                    label: 'Auto Remove',
                    labelVisible: false
                  },
                  {
                    id: 'HtmlEnabledField',
                    objectType: CheckBoxField,
                    label: 'Html Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'TextField',
                    objectType: StringField,
                    label: 'Text',
                    value: '${textKey:TooltipText}'
                  },
                  {
                    id: 'SeverityField',
                    objectType: SmartField,
                    lookupCall: StatusSeverityLookupCall,
                    label: 'Severity'
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

export type TooltipFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'OpenTooltipButton': Button;
  'ConfigurationBox': ConfigurationBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'AutoRemoveField': CheckBoxField;
  'HtmlEnabledField': CheckBoxField;
  'TextField': StringField;
  'SeverityField': SmartField<StatusSeverity>;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
