/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, FormTableControl, InitModelOf, models, Table} from '@eclipse-scout/core';
import SamplePageWithTableSearchFormModel from './SamplePageWithTableSearchFormModel';
import {SamplePageWithTableSearchFormWidgetMap} from '../index';

export class SamplePageWithTableSearchForm extends Form {
  declare widgetMap: SamplePageWithTableSearchFormWidgetMap;

  constructor() {
    super();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._initListeners();
  }

  protected override _jsonModel(): FormModel {
    return models.get(SamplePageWithTableSearchFormModel);
  }

  protected _initListeners() {
    let parentTable = (this.parent as FormTableControl).table;
    this.widget('SearchButton').on('action', parentTable.reload.bind(parentTable, Table.ReloadReason.SEARCH));
  }

  override exportData(): any {
    return {
      stringField: this.widget('StringField').value
    };
  }
}
