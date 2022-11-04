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
import {Button, Event, FormField, GroupBox, GroupBoxModel, InitModelOf, models, scout, SmartField, StringField} from '@eclipse-scout/core';
import {FormFieldLookupCall, GroupBoxAddFieldBoxWidgetMap} from '../index';
import GroupBoxAddFieldBoxModel from './GroupBoxAddFieldBoxModel';

export class GroupBoxAddFieldBox extends GroupBox {
  declare widgetMap: GroupBoxAddFieldBoxWidgetMap;

  field: GroupBox;
  beforeField: SmartField<FormField>;
  labelField: StringField;
  dynamicFieldCounter: number;

  constructor() {
    super();
    this.field = null;
    this.dynamicFieldCounter = 0;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(GroupBoxAddFieldBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setField(this.field);

    let fieldType = this.widget('LabelType');
    fieldType.setValue('StringField');
  }

  setField(field: GroupBox) {
    this.setProperty('field', field);
  }

  protected _setField(field: GroupBox) {
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

  protected _updateAddFieldLabel() {
    this.labelField.setValue('Dynamic Field ' + this.dynamicFieldCounter);
  }

  protected _onAddFormFieldButtonClick(event: Event<Button>) {
    let beforeField = this.beforeField.value;

    this.dynamicFieldCounter++;
    let newField: FormField = scout.create(scout.nvl(this.widget('LabelType').value, 'StringField'), {
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

  setTargetField(field: FormField) {
    this.beforeField.setValue(field);
  }
}
