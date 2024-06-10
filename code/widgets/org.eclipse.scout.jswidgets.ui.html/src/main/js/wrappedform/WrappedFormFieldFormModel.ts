/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, FormModel, GroupBox, SmartField, TabBox, TabItem, WrappedFormField} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap,
  WrappedFormLookupCall
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.WrappedFormFieldForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'WrappedFormFieldBox',
        objectType: GroupBox,
        borderVisible: false,
        fields: [
          {
            id: 'WrappedFormField',
            objectType: WrappedFormField,
            labelVisible: false,
            gridDataHints: {
              h: 15,
              weightY: 0
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: TabBox,
        cssClass: 'jswidgets-configuration',
        selectedTab: 'WrappedFormFieldPropertiesBox',
        tabItems: [{
          id: 'WrappedFormFieldPropertiesBox',
          objectType: TabItem,
          label: 'Properties',
          gridColumnCount: 2,
          fields: [
            {
              id: 'InnerFormField',
              objectType: SmartField,
              label: 'Inner form',
              lookupCall: {
                objectType: WrappedFormLookupCall
              }
            },
            {
              id: 'CloseInnerFormButton',
              objectType: Button,
              label: 'Close inner form',
              displayStyle: Button.DisplayStyle.LINK,
              processButton: false,
              enabled: false
            },
            {
              id: 'InitialFocusEnabledField',
              objectType: CheckBoxField,
              label: 'Initial Focus Enabled',
              labelVisible: false
            },
            {
              id: 'FormFieldPropertiesBox',
              objectType: FormFieldPropertiesBox,
              expanded: false
            },
            {
              id: 'GridDataBox',
              objectType: GridDataBox,
              label: 'Grid Data Hints'
            }
          ]
        }, {
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
        }, {
          id: 'EventsTab',
          objectType: EventsTab
        }]
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type WrappedFormFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'WrappedFormFieldBox': GroupBox;
  'WrappedFormField': WrappedFormField;
  'ConfigurationBox': TabBox;
  'WrappedFormFieldPropertiesBox': TabItem;
  'InitialFocusEnabledField': CheckBoxField;
  'InnerFormField': SmartField<any>;
  'CloseInnerFormButton': Button;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
