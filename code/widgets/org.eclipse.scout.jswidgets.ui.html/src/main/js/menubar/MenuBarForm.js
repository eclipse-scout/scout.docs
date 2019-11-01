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
import {Form, MessageBoxes, Menu, EllipsisMenu, models, scout} from '@eclipse-scout/core';
import MenuBarFormModel from './MenuBarFormModel';

export default class MenuBarForm extends Form {

  constructor() {
    super();
    this.currentMenu;
  }


  _jsonModel() {
    return models.get(MenuBarFormModel);
  }

  _init(model) {
    super._init(model);

    var selectedMenuItemField = this.widget('SelectedMenuField');
    selectedMenuItemField.on('propertyChange', this._onSelectedMenuFieldPropertyChange.bind(this));

    var shrinkableField = this.widget('ShrinkableField');
    shrinkableField.on('propertyChange', this._onShrinkableFieldPropertyChange.bind(this));

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
      if (menu instanceof Menu) {
        menu.on('action', this._onMenuAction.bind(this));
        menu.on('propertyChange', this._onMenuPropertyChange.bind(this));
      }
    }.bind(this));

    this._fillSelectedMenuField();
    this._updateSelectedMenu();
  }

  _onReplaceChildActionsClick(event, menu) {
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
  }

  _onMenuAction(event) {
    if (event.source.isToggleAction()) {
      // Don't show message box if it is a toggle action
      return;
    }
    MessageBoxes.createOk(this)
      .withBody("Menu with label '" + event.source.text + "' has been activated.")
      .buildAndOpen();
  }

  _onMenuPropertyChange(event) {
    if (event.propertyName === 'text') {
      this._fillSelectedMenuField();
    }
  }

  _onShrinkableFieldPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'ShrinkableField') {
      this.currentMenu.setShrinkable(event.newValue);
    }
  }

  _onStackableFieldPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'StackableField') {
      this.currentMenu.setStackable(event.newValue);
    }
  }

  _onSelectedMenuFieldPropertyChange(event) {
    if (event.propertyName === 'value') {
      this._updateSelectedMenu();
    }
  }

  /**
   * Collects every menu of the group box and updates lookup call of the SelectedMenuField
   */
  _fillSelectedMenuField() {
    var selectedMenuItemField = this.widget('SelectedMenuField');
    var detailBox = this.widget('DetailBox');
    var menus = [];
    detailBox.visitChildren(function(menu) {
      if (menu instanceof Menu && !(menu instanceof EllipsisMenu)) {
        menus.push(menu);
      }
    }.bind(this));
    selectedMenuItemField.lookupCall.data = [];
    menus.forEach(function(menu) {
      selectedMenuItemField.lookupCall.data.push([menu.id, scout.nvl(menu.text, menu.id)]);
    });
  }

  _updateSelectedMenu() {
    var selectedMenuItemField = this.widget('SelectedMenuField');
    var formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
    var menu = selectedMenuItemField.value ? this.widget(selectedMenuItemField.value) : null;
    if (!menu) {
      return;
    }
    this.widget('ActionPropertiesBox').setField(menu);
    this.widget('EventsTab').setField(menu);
    this.widget('WidgetActionsBox').setField(menu);
    formFieldPropertiesBox.setVisible(!!menu.field);
    this.currentMenu = menu;
    this.widget('ShrinkableField').setValue(this.currentMenu.shrinkable);
    this.widget('StackableField').setValue(this.currentMenu.stackable);
    if (menu.field) {
      // form field widget
      formFieldPropertiesBox.setField(menu.field);
    }
  }
}
