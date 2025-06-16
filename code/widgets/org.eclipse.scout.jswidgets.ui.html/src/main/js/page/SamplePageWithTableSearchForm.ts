/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {BaseDoEntity, Form, FormModel, scout, typeName} from '@eclipse-scout/core';
import {SamplePageWithTableSearchFormWidgetMap} from '../index';
import model from './SamplePageWithTableSearchFormModel';

export class SamplePageWithTableSearchForm extends Form {
  declare widgetMap: SamplePageWithTableSearchFormWidgetMap;
  declare data: SamplePageWithTableRestrictionDo;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return model();
  }

  override exportData(): SamplePageWithTableRestrictionDo {
    return scout.create(SamplePageWithTableRestrictionDo, {
      stringField: this.widget('StringField').value
    });
  }

  override importData() {
    this.widget('StringField').setValue(this.data?.stringField);
  }
}

@typeName('jswidgets.SamplePageWithTableRestriction')
export class SamplePageWithTableRestrictionDo extends BaseDoEntity {
  stringField: string;
}
