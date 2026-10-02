/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormField, InitModelOf, Menu, StaticLookupCall, StaticLookupCallModel} from '@eclipse-scout/core';

export class FormFieldMenuLookupCall extends StaticLookupCall<Menu> {
  declare model: FormFieldMenuLookupCall;
  formField: FormField;
  protected _rebuildDataHandler: () => void;

  constructor(formField: FormField) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.setFormField(this.formField);
  }

  setFormField(formField: FormField) {
    if (this.formField) {
      this.formField.off('propertyChange:menus', this._rebuildDataHandler);
      this.formField.menus.forEach(menu => menu.off('propertyChange:text', this._rebuildDataHandler));
    }
    this.formField = formField;
    this.formField.on('propertyChange:menus', this._rebuildDataHandler);
    this.formField.menus.forEach(menu => menu.on('propertyChange:text', this._rebuildDataHandler));
    this._rebuildData();
  }

  protected _rebuildData() {
    this.data = this.formField.menus.map(menu => {
      return [menu, menu.text];
    });
  }
}

export interface FormFieldMenuLookupCallModel extends StaticLookupCallModel<Menu> {
  formField?: FormField;
}
