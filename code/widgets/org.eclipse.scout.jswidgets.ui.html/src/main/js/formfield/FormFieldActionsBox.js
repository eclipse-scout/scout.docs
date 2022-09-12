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
    let insertMenuButton = this.widget('InsertMenuButton');
    insertMenuButton.on('click', this._onInsertMenuClick.bind(this));

    let insertChildMenuButton = this.widget('InsertChildMenuButton');
    insertChildMenuButton.on('click', this._onInsertChildMenuClick.bind(this));

    let deleteMenuButton = this.widget('DeleteMenuButton');
    deleteMenuButton.on('click', this._onDeleteMenuClick.bind(this));

    let selectedMenuField = this.widget('SelectedMenuField');
    selectedMenuField.setLookupCall(new FormFieldMenuLookupCall(this.field));
  }

  _onInsertMenuClick(event) {
    this.field.insertMenu({
      objectType: 'Menu',
      text: 'Menu ' + (this.field.menus.length + 1)
    });
  }

  _onInsertChildMenuClick(event) {
    let selectedMenuField = this.widget('SelectedMenuField');
    let menu = selectedMenuField.value;
    if (!menu) {
      return;
    }
    menu.insertChildAction({
      objectType: 'Menu',
      text: 'Child Menu ' + (menu.childActions.length + 1)
    });
  }

  _onDeleteMenuClick(event) {
    let selectedMenuField = this.widget('SelectedMenuField');
    let menu = selectedMenuField.value;
    this.field.deleteMenu(menu);
    // Select last entry in the lookup rows
    selectedMenuField.lookupCall.getAll().then(result => {
      let rows = result.lookupRows;
      let newValue = null;
      if (rows.length > 0) {
        newValue = rows[rows.length - 1].key;
      }
      selectedMenuField.setValue(newValue);
    });
  }
}
