import {Form, models} from '@eclipse-scout/core';
import MiniFormModel from './MiniFormModel';

export default class MiniForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(MiniFormModel);
  }

  _init(model) {
    super._init(model);
    this.widget('CloseButton').on('click', () => this.close());
  }
}
