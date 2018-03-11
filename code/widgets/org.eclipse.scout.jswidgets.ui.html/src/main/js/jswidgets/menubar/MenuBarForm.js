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

jswidgets.MenuBarForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.MenuBarForm');
};

jswidgets.MenuBarForm.prototype._init = function(model) {
  jswidgets.MenuBarForm.parent.prototype._init.call(this, model);

  var selectedMenuItemField = this.widget('SelectedMenuField');
  selectedMenuItemField.on('propertyChange', this._onSelectedMenuFieldPropertyChange.bind(this));

  var stackableField = this.widget('StackableField');
  stackableField.on('propertyChange', this._onStackableFieldPropertyChange.bind(this));


  this.replaceMenu = this.widget('ReplaceMenu');
  this.replaceMenu.on('action', function(event) {
    this._onReplaceChildActionsClick(event, this.hierarchicalMenu);
  }.bind(this));

  var menu1 = this.widget('Menu1');
  menu1.on('action', this._onMenuAction.bind(this));
  menu1.on('propertyChange', this._onMenuPropertyChange.bind(this));

  var menu2 = this.widget('Menu2');
  menu2.on('action', this._onMenuAction.bind(this));
  menu2.on('propertyChange', this._onMenuPropertyChange.bind(this));

  // Add event handlers to the hierarchical menu and its sub menus
  this.hierarchicalMenu = this.widget('HierarchicalMenu');
  this.hierarchicalMenu.on('action', this._onMenuAction.bind(this));
  this.hierarchicalMenu.on('propertyChange', this._onMenuPropertyChange.bind(this));
  this.hierarchicalMenu.visitChildren(function(menu) {
    if (menu instanceof scout.Menu) {
      menu.on('action', this._onMenuAction.bind(this));
      menu.on('propertyChange', this._onMenuPropertyChange.bind(this));
    }
  }.bind(this));

  this._fillSelectedMenuField();
  this._updateSelectedMenu();
};

jswidgets.MenuBarForm.prototype._onReplaceChildActionsClick = function(event, menu) {
  var i = 1,
    menuCount = Math.floor(Math.random() * 10) + i,
    newMenus = [];
  menu = menu || this.currentMenu;
  if (menu) {
    if (menu === this.hierarchicalMenu) {
      newMenus.push(this.replaceMenu);
    }
    for (; i < menuCount; i++) {
      newMenus.push(scout.create('Menu', {
        parent: this,
        text: 'DynMenu ' + i,
        enabled: true
      }));
    }
    menu.setChildActions(newMenus);
  }
};

jswidgets.MenuBarForm.prototype._onMenuAction = function(event) {
  scout.MessageBoxes.createOk(this)
    .withBody("Menu with label '" + event.source.text + "' has been activated.")
    .buildAndOpen();
};

jswidgets.MenuBarForm.prototype._onMenuPropertyChange = function(event) {
  if (event.propertyName === 'text') {
    this._fillSelectedMenuField();
  }
};

jswidgets.MenuBarForm.prototype._onStackableFieldPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'StackableField') {
    this.currentMenu.setStackable(event.newValue);
  }
};

jswidgets.MenuBarForm.prototype._onSelectedMenuFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this._updateSelectedMenu();
  }
};

/**
 * Collects every menu of the group box and updates lookup call of the SelectedMenuField
 */
jswidgets.MenuBarForm.prototype._fillSelectedMenuField = function() {
  var selectedMenuItemField = this.widget('SelectedMenuField');
  var detailBox = this.widget('DetailBox');
  var menus = [];
  detailBox.visitChildren(function(menu) {
    if (menu instanceof scout.Menu && !(menu instanceof scout.EllipsisMenu)) {
      menus.push(menu);
    }
  }.bind(this));
  selectedMenuItemField.lookupCall.data = [];
  menus.forEach(function(menu) {
    selectedMenuItemField.lookupCall.data.push([menu.id, scout.nvl(menu.text, menu.id)]);
  });
};

jswidgets.MenuBarForm.prototype._updateSelectedMenu = function() {
  var selectedMenuItemField = this.widget('SelectedMenuField');
  var formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
  var menuWidget = selectedMenuItemField.value ? this.widget(selectedMenuItemField.value) : null;
  if (!menuWidget) {
    return;
  }
  this.widget('ActionPropertiesBox').setField(menuWidget);
  formFieldPropertiesBox.setVisible(!!menuWidget.field);
  this.currentMenu = menuWidget;
  this.widget('StackableField').setValue(this.currentMenu.stackable);
  if (menuWidget.field) {
    // form field widget
    formFieldPropertiesBox.setField(menuWidget.field);
  }
};
