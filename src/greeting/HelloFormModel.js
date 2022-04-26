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
        label: '${textKey:helloworld.Name}'
      },
      {
        id: 'GreetButton',
        objectType: 'Button',
        label: '${textKey:helloworld.SayHello}',
        keyStroke: 'enter',
        processButton: false
      }
    ]
  }
})
