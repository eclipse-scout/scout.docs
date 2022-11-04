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
import {Button, GroupBox, GroupBoxModel, InitModelOf, Menu, models, SmartField} from '@eclipse-scout/core';
import {FormFieldMenuLookupCall, GroupBoxDeleteMenuBoxWidgetMap} from '../index';
import GroupBoxDeleteMenuBoxModel from './GroupBoxDeleteMenuBoxModel';

export class GroupBoxDeleteMenuBox extends GroupBox {
  declare widgetMap: GroupBoxDeleteMenuBoxWidgetMap;

  field: GroupBox;
  targetField: SmartField<Menu>;
  deleteFieldButton: Button;
  dynamicMenuCounter: number;

  constructor() {
    super();
    this.field = null;
    this.dynamicMenuCounter = 0;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(GroupBoxDeleteMenuBoxModel);
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

    this.targetField = this.widget('MenuToDeleteField');
    this.targetField.setLookupCall(new FormFieldMenuLookupCall(this.field));
    this.targetField.on('propertyChange:value', event => this.deleteFieldButton.setEnabled(!!event.newValue));

    this.deleteFieldButton = this.widget('DeleteButton');
    this.deleteFieldButton.on('click', this._onDeleteMenuButtonClick.bind(this));
  }

  protected _onDeleteMenuButtonClick() {
    let newMenuItems = this.field.menuBar.menuItems.slice(),
      index = this.field.menuBar.menuItems.indexOf(this.targetField.value);

    if (index < 0) {
      return;
    }

    newMenuItems.splice(index, 1);
    this.field.setMenus(newMenuItems);

    this.targetField.setValue(null);
  }
}
