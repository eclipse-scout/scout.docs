/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, FormModel, GroupBox, SmartField, StatusSeverity, StringField} from '@eclipse-scout/core';
import {IconIdLookupCall, StatusSeverityLookupCall} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.MessageBoxForm',
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
            label: 'Open MessageBox',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
            }
          }
        ]
      },
      {
        id: 'PropertiesBox',
        objectType: GroupBox,
        label: 'Properties',
        fields: [
          {
            id: 'SeverityField',
            objectType: SmartField,
            lookupCall: StatusSeverityLookupCall,
            label: 'Severity'
          },
          {
            id: 'HeaderField',
            objectType: StringField,
            label: 'Header'
          },
          {
            id: 'BodyField',
            objectType: StringField,
            label: 'Body',
            multilineText: true,
            gridDataHints: {
              h: 3
            }
          },
          {
            id: 'HtmlField',
            objectType: CheckBoxField,
            label: 'Body is HTML'
          },
          {
            id: 'IconField',
            objectType: SmartField,
            lookupCall: IconIdLookupCall,
            label: 'Icon'
          },
          {
            id: 'YesButtonTextField',
            objectType: StringField,
            label: 'Yes-Button Text'
          },
          {
            id: 'NoButtonTextField',
            objectType: StringField,
            label: 'No-Button Text'
          },
          {
            id: 'CancelButtonTextField',
            objectType: StringField,
            label: 'Cancel-Button Text'
          },
          {
            id: 'ResultField',
            objectType: StringField,
            label: 'Result',
            enabled: false
          }
        ]
      }
    ]
  }
});

export type MessageBoxFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'Button': Button;
  'PropertiesBox': GroupBox;
  'SeverityField': SmartField<StatusSeverity>;
  'HeaderField': StringField;
  'BodyField': StringField;
  'HtmlField': CheckBoxField;
  'IconField': SmartField<string>;
  'YesButtonTextField': StringField;
  'NoButtonTextField': StringField;
  'CancelButtonTextField': StringField;
  'ResultField': StringField;
};
