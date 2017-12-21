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
jswidgets.MenuBarForm = function() {
  jswidgets.MenuBarForm.parent.call(this);
  this.currentMenu;
};
scout.inherits(jswidgets.MenuBarForm, scout.Form);

jswidgets.MenuBarForm.prototype._init = function(model) {
  jswidgets.MenuBarForm.parent.prototype._init.call(this, model);

  var menuBarField = this.widget('MenuBarField');

  var tabBox = this.widget('PropertiesTabBox');
  tabBox.on('propertyChange', this._onPropertiesTabBoxPropertyChange.bind(this));

  var menuItemMenu = this.widget('MenuItem.MenuItemMenu');
  menuItemMenu.field.on('propertyChange', this._onSelectedMenuItemChange.bind(this));

  var stackableField = this.widget('MenuItem.StackableField');
  stackableField.on('propertyChange', this._onPropertyChange.bind(this));

  this.widget('FormFieldPropertiesBox').setField(menuBarField);
  this.widget('GridDataBox').setField(menuBarField);

  this._updatePropertyTabBoxSelectedTab();
  this._updateSelectedMenu();
};

jswidgets.MenuBarForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.MenuBarForm');
};

jswidgets.MenuBarForm.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'MenuItem.StackableField') {
    this.currentMenu.setStackable(event.newValue);
  }
};

jswidgets.MenuBarForm.prototype._onPropertiesTabBoxPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    this._updatePropertyTabBoxSelectedTab();
  }
};

jswidgets.MenuBarForm.prototype._updatePropertyTabBoxSelectedTab = function() {
  var tabBox = this.widget('PropertiesTabBox');
  this.widget('MenuItem.MenuItemMenu').setVisible(tabBox.selectedTab && tabBox.selectedTab.id === 'MenuItem.PropertyTabBox');
};

jswidgets.MenuBarForm.prototype._onSelectedMenuItemChange = function(event) {
  if (event.propertyName === 'value') {
    this._updateSelectedMenu();
  }
};

jswidgets.MenuBarForm.prototype._updateSelectedMenu = function() {
  var menuItemMenu = this.widget('MenuItem.MenuItemMenu'),
    formFieldPropertiesBox = this.widget('MenuItem.FormFieldPropertiesBox'),
    menuWidget = (menuItemMenu.field) ? (this.widget(menuItemMenu.field.value)) : null;
  this.widget('MenuItem.ActionPropertiesBox').setField(menuWidget);
  formFieldPropertiesBox.setVisible(!!menuWidget.field);
  this.currentMenu = menuWidget;
  this.widget('MenuItem.StackableField').setValue(this.currentMenu.stackable);
  if (menuWidget.field) {
    // form field widget
    formFieldPropertiesBox.setField(menuWidget.field);
  }

};
