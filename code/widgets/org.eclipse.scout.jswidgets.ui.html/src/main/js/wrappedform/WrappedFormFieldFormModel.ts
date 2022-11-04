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
import {Button, Form, FormModel, GroupBox, SmartField, TabBox, TabItem, WrappedFormField} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap, WrappedFormLookupCall} from '../index';

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
              id: 'WidgetActionsBox',
              objectType: WidgetActionsBox,
              expandable: false,
              labelVisible: false,
              borderVisible: false
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

export type WrappedFormFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'WrappedFormFieldBox': GroupBox;
  'WrappedFormField': WrappedFormField;
  'ConfigurationBox': TabBox;
  'WrappedFormFieldPropertiesBox': TabItem;
  'InnerFormField': SmartField<Form>;
  'CloseInnerFormButton': Button;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
