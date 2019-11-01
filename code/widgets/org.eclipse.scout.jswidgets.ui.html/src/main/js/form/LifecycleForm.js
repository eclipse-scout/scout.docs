/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Form, models} from '@eclipse-scout/core';
import * as $ from 'jquery';
import LifecycleFormModel from './LifecycleFormModel';

export default class LifecycleForm extends Form {

  constructor() {
    super();
  }


  _jsonModel() {
    return models.get(LifecycleFormModel);
  }

  _init(model) {
    super._init(model);

    this.widget('HasCloseButtonField').on('propertyChange', this._onHasCloseButtonPropertyChange.bind(this));

    var askIfNeedSaveField = this.widget('AskIfNeedSaveField');
    askIfNeedSaveField.setValue(this.askIfNeedSave);
    askIfNeedSaveField.on('propertyChange', this._onAskIfNeedSavePropertyChange.bind(this));
  }

  importData() {
    this.widget('NameField').setValue(this.data.name);
    this.widget('BirthdayField').setValue(this.data.birthday);
  }

  exportData() {
    return {
      name: this.widget('NameField').value,
      birthday: this.widget('BirthdayField').value
    };
  }

  _save(data) {
    if (!this.widget('ExceptionField').value) {
      return super._save(data);
    }
    // Simulate a failing asynchronous save operation (e.g. an ajax call)
    return $.resolvedPromise().then(function() {
      throw new Error('Saving failed');
    });
  }

  _onHasCloseButtonPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('CloseMenu').setVisible(event.newValue);
    }
  }

  _onAskIfNeedSavePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.setAskIfNeedSave(event.newValue);
    }
  }
}
