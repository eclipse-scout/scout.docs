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
import {EllipsisMenu, Form, Menu, MenuBar, models, scout, Status} from '@eclipse-scout/core';
import MenuBarFormModel from './MenuBarFormModel';

export default class MenuBarForm extends Form {

  constructor() {
    super();
    this.currentMenu = null;
  }

  _jsonModel() {
    return models.get(MenuBarFormModel);
  }

  _init(model) {
    super._init(model);

    let selectedMenuField = this.widget('SelectedMenuField');
    selectedMenuField.on('propertyChange:value', event => this._updateSelectedMenu());

    let actionTargetField = this.widget('ActionTargetField');
    actionTargetField.on('propertyChange:value', event => this._updateActionTarget());

    let shrinkableField = this.widget('ShrinkableField');
    shrinkableField.on('propertyChange:value', event => this.currentMenu.setShrinkable(event.newValue));

    let stackableField = this.widget('StackableField');
    stackableField.on('propertyChange:value', event => this.currentMenu.setStackable(event.newValue));

    let subMenuVisibilityField = this.widget('SubMenuVisibilityField');
    subMenuVisibilityField.on('propertyChange:value', event => this.currentMenu.setSubMenuVisibility(event.newValue));

    this.replaceMenu = this.widget('ReplaceMenu');
    this.replaceMenu.on('action', event => {
      this._onReplaceChildActionsClick(event, this.hierarchicalMenu);
    });

    this.hierarchicalMenu = this.widget('HierarchicalMenu');

    let formMenu = this.widget('FormMenu');
    formMenu.on('propertyChange:selected', event => {
      if (event.newValue && !formMenu.form) {
        formMenu.setForm(scout.create('jswidgets.MiniForm', {
          parent: formMenu
        }));
      }
    });

    // To make key stroke work on the combo menu, delegate the action calls to a child
    let comboMenu = this.widget('ComboMenu');
    comboMenu.on('action', event => comboMenu.childActions[0] && comboMenu.childActions[0].doAction());

    let detailBox = this.widget('DetailBox');
    let menus = detailBox.menus;

    menus.forEach(menu => {
      menu.on('action', this._onMenuAction.bind(this));
      menu.on('propertyChange:text', event => this._fillSelectedMenuField());
      menu.visitChildMenus(menu => {
        menu.on('action', this._onMenuAction.bind(this));
        menu.on('propertyChange:text', event => this._fillSelectedMenuField());
      });
    });

    this.widget('Actions.AddGroupBoxMenuBox').setField(detailBox);
    this.widget('Actions.DeleteGroupBoxMenuBox').setField(detailBox);
    this._fillSelectedMenuField();
    selectedMenuField.setValue(detailBox.menus[0]);
    actionTargetField.setValue(detailBox.menus[0]);
  }

  _onReplaceChildActionsClick(event, menu) {
    let i = 1,
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
    scout.create('DesktopNotification', {
      parent: this,
      status: {
        severity: Status.Severity.OK,
        message: this.session.text('MenuClickMessage', event.source.text)
      }
    }).show();
  }

  /**
   * Collects every menu of the group box and updates lookup call of the SelectedMenuField
   */
  _fillSelectedMenuField() {
    let selectedMenuField = this.widget('SelectedMenuField');
    let detailBox = this.widget('DetailBox');
    let menus = [];
    detailBox.visitChildren(menu => {
      if (menu instanceof Menu && !(menu instanceof EllipsisMenu)) {
        menus.push(menu);
      }
    });
    selectedMenuField.lookupCall.data = [];
    menus.forEach(menu => {
      selectedMenuField.lookupCall.data.push([menu, scout.nvl(menu.text, menu.id)]);
    });
    let actionTargetField = this.widget('ActionTargetField');
    actionTargetField.lookupCall.data = [];
    actionTargetField.lookupCall.data.push([this.widget('DetailBox').menuBar, 'Menu Bar']);
    menus.forEach(menu => {
      actionTargetField.lookupCall.data.push([menu, scout.nvl(menu.text, menu.id)]);
    });
  }

  _updateSelectedMenu() {
    let selectedMenuField = this.widget('SelectedMenuField');
    let formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
    let menu = selectedMenuField.value;
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
    this.widget('SubMenuVisibilityField').setValue(this.currentMenu.subMenuVisibility);
    if (menu.field) {
      // form field widget
      formFieldPropertiesBox.setField(menu.field);
    }
  }

  _updateActionTarget() {
    let menu = this.widget('ActionTargetField').value;
    this.widget('Actions.MenuActionsBox').setMenu(menu instanceof Menu ? menu : null);
    this.widget('Actions.MenuActionsBox').setVisible(menu instanceof Menu);
    this.widget('Actions.AddGroupBoxMenuBox').setVisible(menu instanceof MenuBar);
    this.widget('Actions.DeleteGroupBoxMenuBox').setVisible(menu instanceof MenuBar);
  }
}
