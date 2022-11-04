/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
