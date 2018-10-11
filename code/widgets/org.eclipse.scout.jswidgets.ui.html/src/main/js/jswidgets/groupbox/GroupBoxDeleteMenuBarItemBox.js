/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.GroupBoxDeleteMenuBarItemBox = function() {
  jswidgets.GroupBoxDeleteMenuBarItemBox.parent.call(this);
  this.field = null;
  this.dynamicMenuBarItemCounter = 0;
};
scout.inherits(jswidgets.GroupBoxDeleteMenuBarItemBox, scout.GroupBox);

jswidgets.GroupBoxDeleteMenuBarItemBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxDeleteMenuBarItemBox');
};

jswidgets.GroupBoxDeleteMenuBarItemBox.prototype._init = function(model) {
  jswidgets.GroupBoxDeleteMenuBarItemBox.parent.prototype._init.call(this, model);
  this._setField(this.field);
};

jswidgets.GroupBoxDeleteMenuBarItemBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.GroupBoxDeleteMenuBarItemBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  this.targetField = this.widget('ToDeleteMenuBarItem');
  this.targetField.setLookupCall(new jswidgets.MenuBarItemLookupCall(this.field));
  this.targetField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));

  this.deleteFieldButton = this.widget('DeleteButton');
  this.deleteFieldButton.on('click', this._onDeleteMenuBarItemButtonClick.bind(this));
};

jswidgets.GroupBoxDeleteMenuBarItemBox.prototype._onTargetFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.deleteFieldButton.setEnabled(!!event.newValue);
  }
};

jswidgets.GroupBoxDeleteMenuBarItemBox.prototype._onDeleteMenuBarItemButtonClick = function() {
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
};
