import {Form, MessageBoxes} from '@eclipse-scout/core';
import HelloFormModel from './HelloFormModel';

export class HelloForm extends Form {

  _jsonModel() {
    return HelloFormModel();
  }

  _init(model) {
    super._init(model);
    this.widget('GreetButton').on('click', event => {
      let name = this.widget('NameField').value || 'stranger';
      MessageBoxes.openOk(this.session.desktop, `Hello ${name}!`);
    });
  }
}
