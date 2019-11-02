import {Form, models, Table} from '@eclipse-scout/core';
import SamplePageWithTableSearchFormModel from './SamplePageWithTableSearchFormModel';

export default class SamplePageWithTableSearchForm extends Form {

  constructor() {
    super();
  }


  _init(model) {
    super._init(model);
    this._initListeners();
  }

  _jsonModel() {
    return models.get(SamplePageWithTableSearchFormModel);
  }

  _initListeners() {
    var parentTable = this.parent.table;
    this.widget('SearchButton').on('action', parentTable.reload.bind(parentTable), Table.ReloadReason.SEARCH);
  }

  exportData() {
    return {
      stringField: this.widget('StringField').value
    };
  }
}
