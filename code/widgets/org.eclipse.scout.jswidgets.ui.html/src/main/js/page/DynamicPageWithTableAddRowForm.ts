/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, numbers} from '@eclipse-scout/core';
import {DynamicPageWithTableAddRowFormWidgetMap, PageTypeLookupCall} from '../index';
import model from './DynamicPageWithTableAddRowFormModel';

export class DynamicPageWithTableAddRowForm extends Form {
  declare widgetMap: DynamicPageWithTableAddRowFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    // Allow save without changing anything
    this.widget('OkMenu').on('action', event => {
      this.touch();
    });

    // Show inactive codes
    this.widget('PageTypeField').on('prepareLookupCall', event => {
      let lookupCall = event.lookupCall as PageTypeLookupCall;
      lookupCall.includeInactive = true;
    });

    this.widget('NameField').setValue(numbers.randomId());
    this.widget('PageTypeField').setValue('PageWithNodes');
  }
}
