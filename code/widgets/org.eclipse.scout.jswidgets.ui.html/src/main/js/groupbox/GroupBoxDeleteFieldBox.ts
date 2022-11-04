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
import {GroupBox, models} from '@eclipse-scout/core';
import {FormFieldLookupCall} from '../index';
import GroupBoxDeleteFieldBoxModel from './GroupBoxDeleteFieldBoxModel';

export default class GroupBoxDeleteFieldBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.dynamicFieldCounter = 0;
  }

  _jsonModel() {
    return models.get(GroupBoxDeleteFieldBoxModel);
  }

  _init(model) {
    super._init(model);
    this._setField(this.field);
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    this.targetField = this.widget('ToDeleteField');
    this.targetField.setLookupCall(new FormFieldLookupCall(this.field));
    this.targetField.on('propertyChange:value', event => this.deleteFieldButton.setEnabled(!!event.newValue));

    this.deleteFieldButton = this.widget('DeleteButton');
    this.deleteFieldButton.on('click', this._onDeleteFormFieldButtonClick.bind(this));
  }

  _onDeleteFormFieldButtonClick(event) {
    this.field.deleteField(this.targetField.value);
    this.targetField.setValue(null);
  }

  setTargetField(field) {
    this.targetField.setValue(field);
  }
}
