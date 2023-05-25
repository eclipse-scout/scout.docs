/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, FormField, FormModel, GroupBox, SmartField, StringField} from '@eclipse-scout/core';
import {RestExceptionLookupCall} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.RestForm',
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
            id: 'RequestBox',
            objectType: GroupBox,
            label: 'Requests',
            gridColumnCount: 4,
            fields: [
              {
                id: 'GetButton',
                objectType: Button,
                label: 'Do a GET request',
                processButton: false,
                gridDataHints: {
                  useUiWidth: true,
                  weightX: 0
                }
              },
              {
                id: 'PostButton',
                objectType: Button,
                label: 'Do a POST request',
                processButton: false,
                gridDataHints: {
                  useUiWidth: true,
                  weightX: 0
                }
              },
              {
                id: 'PutButton',
                objectType: Button,
                label: 'Do a PUT request',
                processButton: false,
                gridDataHints: {
                  useUiWidth: true,
                  weightX: 0
                }
              },
              {
                id: 'DeleteButton',
                objectType: Button,
                label: 'Do a DELETE request',
                processButton: false,
                gridDataHints: {
                  useUiWidth: true,
                  weightX: 0
                }
              }
            ]
          },
          {
            id: 'ErrorBox',
            objectType: GroupBox,
            label: 'Error Requests',
            gridColumnCount: 4,
            fields: [
              {
                id: 'ExceptionTypeField',
                objectType: SmartField,
                label: 'Rest Exception',
                lookupCall: RestExceptionLookupCall,
                value: 1000
              }, {
                id: 'FailButton',
                objectType: Button,
                label: 'Do a failing request',
                processButton: false
              }
            ]
          },
          {
            id: 'WebsocketButton',
            objectType: Button,
            label: 'Init Websocket',
            processButton: false
          },
          {
            id: 'LogField',
            objectType: StringField,
            label: 'Log',
            labelPosition: FormField.LabelPosition.TOP,
            enabled: false,
            multilineText: true,
            gridDataHints: {
              w: 3,
              h: 3
            }
          }
        ]
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type RestFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'RequestBox': GroupBox;
  'GetButton': Button;
  'PostButton': Button;
  'PutButton': Button;
  'DeleteButton': Button;
  'ErrorBox': GroupBox;
  'ExceptionTypeField': SmartField<any>;
  'FailButton': Button;
  'WebsocketButton': Button;
  'LogField': StringField;
};
