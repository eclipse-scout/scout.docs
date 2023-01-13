/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';
import $ from 'jquery';
import LifecycleFormModel from './LifecycleFormModel';
import {LifecycleFormWidgetMap} from '../index';

export class LifecycleForm extends Form {
  declare widgetMap: LifecycleFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(LifecycleFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('HasCloseButtonField').on('propertyChange:value', event => this.widget('CloseMenu').setVisible(event.newValue));

    let askIfNeedSaveField = this.widget('AskIfNeedSaveField');
    askIfNeedSaveField.setValue(this.askIfNeedSave);
    askIfNeedSaveField.on('propertyChange:value', event => this.setAskIfNeedSave(event.newValue));
  }

  override importData() {
    this.widget('NameField').setValue(this.data.name);
    this.widget('BirthdayField').setValue(this.data.birthday);
  }

  override exportData(): LifecycleFormData {
    return {
      name: this.widget('NameField').value,
      birthday: this.widget('BirthdayField').value
    };
  }

  protected override _save(data: LifecycleFormData) {
    if (!this.widget('ExceptionField').value) {
      return super._save(data);
    }
    // Simulate a failing asynchronous save operation (e.g. an ajax call)
    return $.resolvedPromise().then(() => {
      throw new Error('Saving failed');
    });
  }
}

export type LifecycleFormData = {
  name?: string;
  birthday?: Date;
};
