import {FormField} from '@eclipse-scout/core';

export default function() {
  return {
  id: 'jswidgets.RestForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 3,
        fields: [
          {
            id: 'GetButton',
            objectType: 'Button',
            label: 'Do a GET request',
            processButton: false,
            gridDataHints: {
              useUiWidth: true,
              weightX: 0
            }
          },
          {
            id: 'PostButton',
            objectType: 'Button',
            label: 'Do a POST request',
            processButton: false,
            gridDataHints: {
              useUiWidth: true,
              weightX: 0
            }
          },
          {
            id: 'PutButton',
            objectType: 'Button',
            label: 'Do a PUT request',
            processButton: false,
            gridDataHints: {
              useUiWidth: true,
              weightX: 0
            }
          },
          {
            id: 'DeleteButton',
            objectType: 'Button',
            label: 'Do a DELETE request',
            processButton: false,
            gridDataHints: {
              useUiWidth: true,
              weightX: 0
            }
          },
          {
            id: 'FailButton',
            objectType: 'Button',
            label: 'Do a failing request',
            processButton: false
          },
          {
            id: 'LogField',
            objectType: 'StringField',
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
};
}
