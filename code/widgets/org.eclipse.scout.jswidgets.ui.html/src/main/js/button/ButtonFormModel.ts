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
import {Button, ButtonDisplayStyle, CheckBoxField, FormModel, GroupBox, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.ButtonForm',
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
            label: 'Button',
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
                    id: 'DefaultButtonField',
                    objectType: CheckBoxField,
                    label: 'Default Button',
                    labelVisible: false
                  },
                  {
                    id: 'ProcessButtonField',
                    objectType: CheckBoxField,
                    label: 'Process Button',
                    labelVisible: false,
                    tooltipText: '${textKey:ProcessButtonTooltip}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'PreventDoubleClickField',
                    objectType: CheckBoxField,
                    label: 'Prevent Double Click',
                    labelVisible: false
                  },
                  {
                    id: 'SelectedField',
                    objectType: CheckBoxField,
                    label: 'Selected',
                    labelVisible: false
                  },
                  {
                    id: 'DisplayStyleField',
                    objectType: SmartField,
                    lookupCall: 'jswidgets.ButtonDisplayStyleLookupCall',
                    label: 'Display Style'
                  },
                  {
                    id: 'IconIdField',
                    objectType: SmartField,
                    lookupCall: 'jswidgets.IconIdLookupCall',
                    label: 'Icon Id'
                  },
                  {
                    id: 'KeyStrokeField',
                    objectType: StringField,
                    label: 'Key Stroke'
                  }
                ]
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

export type ButtonFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'Button': Button;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'DefaultButtonField': CheckBoxField;
  'ProcessButtonField': CheckBoxField;
  'PreventDoubleClickField': CheckBoxField;
  'SelectedField': CheckBoxField;
  'DisplayStyleField': SmartField<ButtonDisplayStyle>;
  'IconIdField': SmartField<string>;
  'KeyStrokeField': StringField;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
