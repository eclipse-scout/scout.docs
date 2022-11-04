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
import {FormFieldMenuLookupCall} from '../index';
import GroupBoxDeleteMenuBoxModel from './GroupBoxDeleteMenuBoxModel';

export default class GroupBoxDeleteMenuBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.dynamicMenuCounter = 0;
  }

  _jsonModel() {
    return models.get(GroupBoxDeleteMenuBoxModel);
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

    this.targetField = this.widget('MenuToDeleteField');
    this.targetField.setLookupCall(new FormFieldMenuLookupCall(this.field));
    this.targetField.on('propertyChange:value', event => this.deleteFieldButton.setEnabled(!!event.newValue));

    this.deleteFieldButton = this.widget('DeleteButton');
    this.deleteFieldButton.on('click', this._onDeleteMenuButtonClick.bind(this));
  }

  _onDeleteMenuButtonClick() {
    let newMenuItems = this.field.menuBar.menuItems.slice(),
      index = this.field.menuBar.menuItems.indexOf(this.targetField.value);

    if (index < 0) {
      return;
    }

    newMenuItems.splice(index, 1);
    this.field._setMenus(newMenuItems);

    this.targetField.setValue(null);
  }
}
