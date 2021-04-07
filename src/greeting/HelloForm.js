import { Form, models, MessageBoxes } from '@eclipse-scout/core';
import HelloFormModel from './HelloFormModel';

export class HelloForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(HelloFormModel)
  }

  _init(model) {
    super._init(model);
    this.widget('GreetButton').on('click', event => {
      let name = this.widget('NameField').value || 'stranger';
      MessageBoxes.openOk(this.session.desktop, `Hello ${name}!`);
    });
  }
}
