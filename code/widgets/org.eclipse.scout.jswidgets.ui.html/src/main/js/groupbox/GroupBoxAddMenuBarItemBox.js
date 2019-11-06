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
import {Button, ButtonAdapterMenu, GridData, GroupBox, models, scout} from '@eclipse-scout/core';
import GroupBoxAddMenuBarItemBoxModel from './GroupBoxAddMenuBarItemBoxModel';

export default class GroupBoxAddMenuBarItemBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.dynamicMenuBarItemCounter = 0;
  }

  _jsonModel() {
    return models.get(GroupBoxAddMenuBarItemBoxModel);
  }

  _init(model) {
    super._init(model);
    this._setField(this.field);

    var menuBarItemType = this.widget('MenuBarItemType');
    menuBarItemType.setValue('Button');
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    this.labelField = this.widget('LabelField');
    this.iconIdField = this.widget('IconIdField');
    this.horizontalAlignmentField = this.widget('HorizontalAlignmentField');
    this.stackableField = this.widget('StackableField');
    this.shrinkableField = this.widget('ShrinkableField');

    var addFieldButton = this.widget('CreateButton');
    addFieldButton.on('click', this._onAddMenuBarItemButtonClick.bind(this));

    this._updateAddMenuBarDefaultValues();
  }

  _updateAddMenuBarDefaultValues() {
    this.labelField.setValue('Dynamic Menubar Item ' + this.dynamicMenuBarItemCounter);
    this.stackableField.setValue(true);
    this.shrinkableField.setValue(false);
  }

  _onAddMenuBarItemButtonClick(event) {
    var label = this.labelField.value || 'Dynamic Menubar Item ' + this.dynamicMenuBarItemCounter;
    var gridData = new GridData();
    gridData.horizontalAlignment = this.horizontalAlignmentField.value;
    this.dynamicMenuBarItemCounter++;
    var newMenuBarItem = scout.create(scout.nvl(this.widget('MenuBarItemType').value, 'Button'), {
      parent: this.field,
      id: 'DynMenuBarItem ' + this.dynamicMenuBarItemCounter,
      label: label,
      text: label,
      iconId: this.iconIdField.value,
      horizontalAlignment: this.horizontalAlignmentField.value,
      gridData: gridData,
      stackable: this.stackableField.value,
      shrinkable: this.shrinkableField.value
    });

    if (newMenuBarItem instanceof Button) {
      newMenuBarItem = scout.create('ButtonAdapterMenu',
        ButtonAdapterMenu.adaptButtonProperties(newMenuBarItem, {
          parent: this,
          menubar: this.menuBar,
          button: newMenuBarItem
        }));
    }

    var newMenuItems = this.field.menuBar.menuItems.slice();
    newMenuItems.splice(newMenuItems.length, 0, newMenuBarItem);
    this.field._setMenus(newMenuItems);

    this._updateAddMenuBarDefaultValues();
    // Validate layout immediately to prevent flickering
    this.field.validateLayoutTree();
  }
}
