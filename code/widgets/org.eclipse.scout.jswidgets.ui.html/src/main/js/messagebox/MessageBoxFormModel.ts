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
import {Button, CheckBoxField, FormModel, GroupBox, SmartField, StatusSeverity, StringField} from '@eclipse-scout/core';

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
            lookupCall: 'jswidgets.StatusSeverityLookupCall',
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
            lookupCall: 'jswidgets.IconIdLookupCall',
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
