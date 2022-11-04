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
import {Button, CheckBoxField, FormModel, GroupBox, NativeNotificationVisibility, NumberField, SmartField, StatusSeverity, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.DesktopNotificationForm',
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
            id: 'Button',
            objectType: Button,
            label: 'Show Notification',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
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
                    id: 'MessageField',
                    objectType: StringField,
                    label: 'Message'
                  },
                  {
                    id: 'StatusSeverityField',
                    objectType: SmartField,
                    lookupCall: 'jswidgets.StatusSeverityLookupCall',
                    label: 'Severity'
                  },
                  {
                    id: 'DurationField',
                    objectType: NumberField,
                    label: 'Duration',
                    tooltipText: '${textKey:DurationTooltip}'
                  },
                  {
                    id: 'IconField',
                    objectType: SmartField,
                    lookupCall: 'jswidgets.IconIdLookupCall',
                    label: 'Icon'
                  },
                  {
                    id: 'ClosableField',
                    objectType: CheckBoxField,
                    label: 'Closable',
                    labelVisible: false
                  },
                  {
                    id: 'LoadingField',
                    objectType: CheckBoxField,
                    label: 'Loading',
                    labelVisible: false
                  },
                  {
                    id: 'HtmlEnabledField',
                    objectType: CheckBoxField,
                    label: 'HTML Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'NativeOnlyField',
                    objectType: CheckBoxField,
                    label: 'Native Only',
                    labelVisible: false,
                    tooltipText: '${textKey:NativeOnlyTooltip}'
                  },
                  {
                    id: 'NativeNotificationTitleField',
                    objectType: StringField,
                    label: 'Native Notification Title',
                    labelWidthInPixel: 170
                  },
                  {
                    id: 'NativeNotificationMessageField',
                    objectType: StringField,
                    label: 'Native Notification Message',
                    labelWidthInPixel: 170
                  },
                  {
                    id: 'NativeNotificationIconIdField',
                    objectType: SmartField,
                    lookupCall: 'jswidgets.ImageLookupCall',
                    label: 'Native Notification Icon Id',
                    labelWidthInPixel: 170
                  },
                  {
                    id: 'NativeNotificationVisibilityField',
                    objectType: SmartField,
                    lookupCall: 'jswidgets.NativeNotificationVisibilityLookupCall',
                    label: 'Native Notification Visibility',
                    labelWidthInPixel: 170,
                    tooltipText: '${textKey:NativeNotificationVisibilityTooltip}'
                  },
                  {
                    id: 'DelayField',
                    objectType: NumberField,
                    label: 'Delay',
                    labelWidthInPixel: 170,
                    tooltipText: '${textKey:DelayTooltip}'
                  }
                ]
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

export type DesktopNotificationFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'Button': Button;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'MessageField': StringField;
  'StatusSeverityField': SmartField<StatusSeverity>;
  'DurationField': NumberField;
  'IconField': SmartField<string>;
  'ClosableField': CheckBoxField;
  'LoadingField': CheckBoxField;
  'HtmlEnabledField': CheckBoxField;
  'NativeOnlyField': CheckBoxField;
  'NativeNotificationTitleField': StringField;
  'NativeNotificationMessageField': StringField;
  'NativeNotificationIconIdField': SmartField<string>;
  'NativeNotificationVisibilityField': SmartField<NativeNotificationVisibility>;
  'DelayField': NumberField;
  'EventsTab': EventsTab;
} & EventsTabWidgetMap;
