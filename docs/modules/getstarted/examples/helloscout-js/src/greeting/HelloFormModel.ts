import {Button, Form, FormModel, GroupBox, StringField} from '@eclipse-scout/core';

export default (): FormModel => ({
  objectType: Form,
  displayHint: Form.DisplayHint.VIEW,
  modal: false,
  rootGroupBox: {
    objectType: GroupBox,
    borderDecoration: GroupBox.BorderDecoration.EMPTY,
    fields: [
      {
        id: 'NameField',
        objectType: StringField,
        label: 'Name'
      },
      {
        id: 'GreetButton',
        objectType: Button,
        label: 'Say Hello',
        keyStroke: 'enter',
        processButton: false
      }
    ]
  }
})

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type HelloFormWidgetMap = {
  'NameField': StringField;
  'GreetButton': Button;
};
