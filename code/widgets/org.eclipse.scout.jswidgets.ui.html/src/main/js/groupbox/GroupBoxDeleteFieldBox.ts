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
import {Button, Event, FormField, GroupBox, GroupBoxModel, InitModelOf, models, SmartField} from '@eclipse-scout/core';
import {FormFieldLookupCall, GroupBoxDeleteFieldBoxWidgetMap} from '../index';
import GroupBoxDeleteFieldBoxModel from './GroupBoxDeleteFieldBoxModel';

export class GroupBoxDeleteFieldBox extends GroupBox {
  declare widgetMap: GroupBoxDeleteFieldBoxWidgetMap;

  field: GroupBox;
  targetField: SmartField<FormField>;
  deleteFieldButton: Button;
  dynamicFieldCounter: number;

  constructor() {
    super();
    this.field = null;
    this.dynamicFieldCounter = 0;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(GroupBoxDeleteFieldBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setField(this.field);
  }

  setField(field: GroupBox) {
    this.setProperty('field', field);
  }

  protected _setField(field: GroupBox) {
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

  protected _onDeleteFormFieldButtonClick(event: Event<Button>) {
    this.field.deleteField(this.targetField.value);
    this.targetField.setValue(null);
  }

  setTargetField(field: FormField) {
    this.targetField.setValue(field);
  }
}
