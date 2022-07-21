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
import {GroupBox, models} from '@eclipse-scout/core';
import {FormFieldLookupCall} from '../index';
import GroupBoxAddFieldBoxModel from './GroupBoxAddFieldBoxModel';

export default class GroupBoxAddFieldBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.dynamicFieldCounter = 0;
  }

  _jsonModel() {
    return models.get(GroupBoxAddFieldBoxModel);
  }

  _init(model) {
    super._init(model);
    this._setField(this.field);

    let fieldType = this.widget('LabelType');
    fieldType.setValue('StringField');
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    this.beforeField = this.widget('BeforeField');
    this.beforeField.setLookupCall(new FormFieldLookupCall(this.field));

    this.labelField = this.widget('LabelField');

    let addFieldButton = this.widget('CreateButton');
    addFieldButton.on('click', this._onAddFormFieldButtonClick.bind(this));

    this._updateAddFieldLabel();
  }

  _updateAddFieldLabel() {
    this.labelField.setValue('Dynamic Field ' + this.dynamicFieldCounter);
  }

  _onAddFormFieldButtonClick(event) {
    let siblings = this.field.fields || [],
      beforeField = this.beforeField.value;

    this.dynamicFieldCounter++;
    let newField = scout.create(scout.nvl(this.widget('LabelType').value, 'StringField'), {
      parent: this.field,
      id: 'DynField ' + this.dynamicFieldCounter,
      label: this.labelField.value || 'Dynamic Field ' + this.dynamicFieldCounter
    });

    if (beforeField) {
      this.field.insertFieldBefore(newField, beforeField);
    } else {
      this.field.insertField(newField);
    }
    this._updateAddFieldLabel();
  }

  setTargetField(field) {
    this.beforeField.setValue(field);
  }
}
