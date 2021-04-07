import { GroupBox } from '@eclipse-scout/core';

export default () => ({
  objectType: 'Form',
  displayHint: 'view',
  modal: false,
  rootGroupBox: {
    objectType: 'GroupBox',
    borderDecoration: GroupBox.BorderDecoration.EMPTY,
    fields: [
      {
        id: 'NameField',
        objectType: 'StringField',
        label: 'Name'
      },
      {
        id: 'GreetButton',
        objectType: 'Button',
        label: 'Say Hello',
        keyStroke: 'enter',
        processButton: false
      }
    ]
  }
})
