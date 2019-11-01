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
import {MenuBarItemLookupCall} from '../index';
import GroupBoxDeleteMenuBarItemBoxModel from './GroupBoxDeleteMenuBarItemBoxModel';

export default class GroupBoxDeleteMenuBarItemBox extends GroupBox {

constructor() {
  super();
  this.field = null;
  this.dynamicMenuBarItemCounter = 0;
}


_jsonModel() {
  return models.get(GroupBoxDeleteMenuBarItemBoxModel);
}

_init(model) {
  super._init( model);
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

  this.targetField = this.widget('ToDeleteMenuBarItem');
  this.targetField.setLookupCall(new MenuBarItemLookupCall(this.field));
  this.targetField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));

  this.deleteFieldButton = this.widget('DeleteButton');
  this.deleteFieldButton.on('click', this._onDeleteMenuBarItemButtonClick.bind(this));
}

_onTargetFieldPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.deleteFieldButton.setEnabled(!!event.newValue);
  }
}

_onDeleteMenuBarItemButtonClick() {
  var newMenuItems = this.field.menuBar.menuItems.slice(),
    index = this.field.menuBar.menuItems.indexOf(this.targetField.value);

  if (index < 0) {
    return;
  }

  newMenuItems.splice(index, 1);
  this.field._setMenus(newMenuItems);

  this.targetField.setValue(null);
  // Validate layout immediately to prevent flickering
  this.field.validateLayoutTree();
}
}
