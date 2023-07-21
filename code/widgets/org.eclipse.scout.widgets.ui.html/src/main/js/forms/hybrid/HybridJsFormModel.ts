/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormModel, GroupBox, Label, StringField, WidgetField, WrappedFormField} from '@eclipse-scout/core';

export default (): FormModel => ({
  id: 'HybridJsForm',
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
            id: 'HybridDescriptionField',
            objectType: WidgetField,
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              useUiHeight: true
            },
            fieldWidget: {
              id: 'HybridDescriptionLabel',
              objectType: Label,
              htmlEnabled: true
            }
          },
          {
            id: 'HybridActionBox',
            objectType: GroupBox,
            label: 'Hybrid Action',
            gridColumnCount: 1,
            fields: [
              {
                id: 'HybridActionDescriptionField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'HybridActionDescriptionLabel',
                  objectType: Label,
                  value: 'Hybrid actions are actions executed on the UI server triggered by Scout JS. The JS code can wait for a result and then continue with the provided data.'
                }
              },
              {
                id: 'PingField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'PingLabel',
                  objectType: Label,
                  htmlEnabled: true,
                  value: '<span class="app-link" data-ref="ping">Ping</span> the server, wait for the answer and send a DesktopNotification if successful.'
                }
              },
              {
                id: 'SendDesktopNotificationField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'SendDesktopNotificationLabel',
                  objectType: Label,
                  htmlEnabled: true,
                  value: 'Trigger a <span class="app-link" data-ref="sendDesktopNotification">DesktopNotification sent by the server</span>.'
                }
              }
            ]
          },
          {
            id: 'OpenFormBox',
            objectType: GroupBox,
            label: 'Open Form',
            gridColumnCount: 1,
            fields: [
              {
                id: 'OpenFormDescriptionField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'OpenFormDescriptionLabel',
                  objectType: Label,
                  value: 'Open a Scout Classic form from Scout JS. The JS code can wait for specific form events like save or close and work with the data provided by the form.'
                }
              },
              {
                id: 'OpenPersonFormField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'OpenPersonFormLabel',
                  objectType: Label,
                  htmlEnabled: true,
                  value: 'Open a <span class="app-link" data-ref="openPersonForm">PersonForm</span> on the server.'
                }
              },
              {
                id: 'OpenFormBox.PersonDoField',
                objectType: StringField,
                labelVisible: false,
                gridDataHints: {
                  h: 3,
                  weightY: 0
                },
                multilineText: true,
                wrapText: true,
                maxLength: 1048576,
                cssClass: 'json-field'
              }
            ]
          },
          {
            id: 'CreateFormBox',
            objectType: GroupBox,
            label: 'Create Form',
            gridColumnCount: 2,
            fields: [
              {
                id: 'CreateFormDescriptionField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  w: 2,
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'CreateFormDescriptionLabel',
                  objectType: Label,
                  value: 'Create a Scout Classic form from Scout JS but do not show it. The JS code receives the form and controls when and where to show it.'
                }
              },
              {
                id: 'CreatePersonFormField',
                objectType: WidgetField,
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  w: 2,
                  useUiHeight: true
                },
                fieldWidget: {
                  id: 'CreatePersonFormLabel',
                  objectType: Label,
                  htmlEnabled: true,
                  value: 'Create a <span class="app-link" data-ref="createPersonForm">PersonForm</span> on the server and set it as innerForm into a WrappedFormField.'
                }
              },
              {
                id: 'CreateFormBox.PersonDoField',
                objectType: StringField,
                labelVisible: false,
                gridDataHints: {
                  h: 5,
                  weightY: 0
                },
                multilineText: true,
                wrapText: true,
                maxLength: 1048576,
                cssClass: 'json-field'
              },
              {
                id: 'CreatePersonFormWrappedFormField',
                objectType: WrappedFormField,
                labelVisible: false,
                gridDataHints: {
                  h: 5,
                  weightY: 0
                }
              }
            ]
          }
        ]
      }]
  }
});

export type HybridJsFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'HybridDescriptionField': WidgetField;
  'HybridDescriptionLabel': Label;
  'HybridActionBox': GroupBox;
  'HybridActionDescriptionField': WidgetField;
  'HybridActionDescriptionLabel': Label;
  'PingField': WidgetField;
  'PingLabel': Label;
  'SendDesktopNotificationField': WidgetField;
  'SendDesktopNotificationLabel': Label;
  'OpenFormBox': GroupBox;
  'OpenFormDescriptionField': WidgetField;
  'OpenFormDescriptionLabel': Label;
  'OpenPersonFormField': WidgetField;
  'OpenPersonFormLabel': Label;
  'OpenFormBox.PersonDoField': StringField;
  'CreateFormBox': GroupBox;
  'CreateFormDescriptionField': WidgetField;
  'CreateFormDescriptionLabel': Label;
  'CreatePersonFormField': WidgetField;
  'CreatePersonFormLabel': Label;
  'CreateFormBox.PersonDoField': StringField;
  'CreatePersonFormWrappedFormField': WrappedFormField;
};
