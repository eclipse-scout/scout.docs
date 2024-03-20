import {Form, FormModel, InitModelOf, MessageBoxes} from '@eclipse-scout/core';
import HelloFormModel, {HelloFormWidgetMap} from './HelloFormModel';

export class HelloForm extends Form {

  declare widgetMap: HelloFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return HelloFormModel();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.widget('GreetButton').on('click', () => {
      let name = this.widget('NameField').value || 'stranger';
      MessageBoxes.openOk(this.session.desktop, `Hello ${name}!`);
    });
  }
}
