/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, ButtonAdapterMenu, CheckBoxField, Event, GroupBox, GroupBoxModel, InitModelOf, Menu, models, NumberField, scout, SmartField, StringField} from '@eclipse-scout/core';
import GroupBoxAddMenuBoxModel from './GroupBoxAddMenuBoxModel';
import {GroupBoxAddMenuBoxWidgetMap} from '../index';

export class GroupBoxAddMenuBox extends GroupBox {
  declare widgetMap: GroupBoxAddMenuBoxWidgetMap;

  field: GroupBox;
  labelField: StringField;
  iconIdField: SmartField<string>;
  horizontalAlignmentField: NumberField;
  stackableField: CheckBoxField;
  shrinkableField: CheckBoxField;
  dynamicMenuCounter: number;

  constructor() {
    super();
    this.field = null;
    this.dynamicMenuCounter = 0;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(GroupBoxAddMenuBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setField(this.field);

    this.widget('MenuBarItemType').setValue('Menu');
  }

  setField(field: GroupBox) {
    this.setProperty('field', field);
  }

  protected _setField(field: GroupBox) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    this.labelField = this.widget('LabelField');
    this.iconIdField = this.widget('IconIdField');
    this.horizontalAlignmentField = this.widget('HorizontalAlignmentField');
    this.stackableField = this.widget('StackableField');
    this.shrinkableField = this.widget('ShrinkableField');

    let addFieldButton = this.widget('CreateButton');
    addFieldButton.on('click', this._onAddMenuButtonClick.bind(this));

    this._updateAddMenuBarDefaultValues();
  }

  protected _updateAddMenuBarDefaultValues() {
    this.labelField.setValue('Dynamic Menu ' + this.dynamicMenuCounter);
    this.stackableField.setValue(true);
    this.shrinkableField.setValue(false);
  }

  protected _onAddMenuButtonClick(event: Event<Button>) {
    let label = this.labelField.value || '';
    this.dynamicMenuCounter++;
    let newMenu: Menu | Button = scout.create(scout.nvl(this.widget('MenuBarItemType').value, 'Menu'), {
      parent: this.field,
      id: 'DynMenu ' + this.dynamicMenuCounter,
      label: label,
      text: label,
      iconId: this.iconIdField.value,
      horizontalAlignment: this.horizontalAlignmentField.value,
      gridDataHints: {
        horizontalAlignment: this.horizontalAlignmentField.value
      },
      stackable: this.stackableField.value,
      shrinkable: this.shrinkableField.value
    });

    if (newMenu instanceof Button) {
      newMenu = scout.create(ButtonAdapterMenu,
        ButtonAdapterMenu.adaptButtonProperties(newMenu, {
          parent: this,
          menubar: this.menuBar,
          button: newMenu
        }));
    }

    let newMenuItems = this.field.menuBar.menuItems.slice();
    newMenuItems.splice(newMenuItems.length, 0, newMenu);
    this.field.setMenus(newMenuItems);

    this._updateAddMenuBarDefaultValues();
  }
}
