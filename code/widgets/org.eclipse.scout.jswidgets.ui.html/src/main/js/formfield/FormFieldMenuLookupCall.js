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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class FormFieldMenuLookupCall extends StaticLookupCall {

  constructor(formField) {
    super();
    this._formFieldPropertyChangeHandler = this._onFormFieldPropertyChange.bind(this);
    this._menuPropertyChangeHandler = this._onMenuPropertyChange.bind(this);
    this.data = [];
    this.setFormField(formField);
  }

  _data() {
    return this.data;
  }

  setFormField(formField) {
    if (this.formField) {
      this.formField.off('propertyChange', this._formFieldPropertyChangeHandler);
      this.formField.menus.forEach(function(menu) {
        menu.off('propertyChange', this._menuPropertyChangeHandler);
      }, this);
    }
    this.formField = formField;
    this.formField.on('propertyChange', this._formFieldPropertyChangeHandler);
    this.formField.menus.forEach(function(menu) {
      menu.on('propertyChange', this._menuPropertyChangeHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.formField.menus.map(menu => {
      return [menu, menu.text];
    });
  }

  _onFormFieldPropertyChange(event) {
    if (event.propertyName === 'menus') {
      this._rebuildData();
    }
  }

  _onMenuPropertyChange(event) {
    if (event.propertyName === 'text') {
      this._rebuildData();
    }
  }
}
