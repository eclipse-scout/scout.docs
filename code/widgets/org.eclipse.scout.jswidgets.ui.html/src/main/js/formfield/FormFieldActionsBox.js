/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {GroupBox, models} from '@eclipse-scout/core';
import FormFieldActionsBoxModel from './FormFieldActionsBoxModel';
import {FormFieldMenuLookupCall} from '../index';

export default class FormFieldActionsBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.get(FormFieldActionsBoxModel);
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
    var insertMenuButton = this.widget('InsertMenuButton');
    insertMenuButton.on('click', this._onInsertMenuClick.bind(this));

    var deleteMenuButton = this.widget('DeleteMenuButton');
    deleteMenuButton.on('click', this._onDeleteMenuClick.bind(this));

    var menuToDeleteField = this.widget('MenuToDeleteField');
    menuToDeleteField.setLookupCall(new FormFieldMenuLookupCall(this.field));
  }

  _onInsertMenuClick(event) {
    this.field.insertMenu({
      objectType: 'Menu',
      text: 'Menu ' + (this.field.menus.length + 1)
    });
  }

  _onDeleteMenuClick(event) {
    var menuToDeleteField = this.widget('MenuToDeleteField');
    var menu = menuToDeleteField.value;
    this.field.deleteMenu(menu);
    // Select last entry in the lookup rows
    menuToDeleteField.lookupCall.getAll().then(result => {
      var rows = result.lookupRows;
      var newValue = null;
      if (rows.length > 0) {
        newValue = rows[rows.length - 1].key;
      }
      menuToDeleteField.setValue(newValue);
    });
  }
}
