/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, Event, FormField, GroupBox, GroupBoxModel, InitModelOf, Menu, models} from '@eclipse-scout/core';
import FormFieldActionsBoxModel from './FormFieldActionsBoxModel';
import {FormFieldActionsBoxWidgetMap, FormFieldMenuLookupCall} from '../index';

export class FormFieldActionsBox extends GroupBox {
  declare widgetMap: FormFieldActionsBoxWidgetMap;

  field: FormField;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(FormFieldActionsBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field: FormField) {
    this.setProperty('field', field);
  }

  protected _setField(field: FormField) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }
    this.widget('InsertMenuButton').on('click', this._onInsertMenuClick.bind(this));
    this.widget('DeleteMenuButton').on('click', this._onDeleteMenuClick.bind(this));
    this.widget('MenuToDeleteField').setLookupCall(new FormFieldMenuLookupCall(this.field));
    this.widget('MarkAsSavedButton').on('click', () => this.field.markAsSaved());
    this.widget('TouchButton').on('click', () => this.field.touch());
    this.field.on('propertyChange:saveNeeded', event => this.widget('SaveNeededField').setValue(event.newValue));
    this.field.on('propertyChange:touched', event => this.widget('TouchedField').setValue(event.newValue));
  }

  protected _onInsertMenuClick(event: Event<Button>) {
    this.field.insertMenu({
      objectType: Menu,
      text: 'Menu ' + (this.field.menus.length + 1)
    });
  }

  protected _onDeleteMenuClick(event: Event<Button>) {
    let menuToDeleteField = this.widget('MenuToDeleteField');
    let menu = menuToDeleteField.value;
    this.field.deleteMenu(menu);
    // Select last entry in the lookup rows
    menuToDeleteField.lookupCall.getAll().then(result => {
      let rows = result.lookupRows;
      let newValue = null;
      if (rows.length > 0) {
        newValue = rows[rows.length - 1].key;
      }
      menuToDeleteField.setValue(newValue);
    });
  }
}
