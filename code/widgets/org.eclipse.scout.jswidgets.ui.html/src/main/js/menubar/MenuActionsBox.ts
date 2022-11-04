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
import {Button, Event, GroupBox, GroupBoxModel, InitModelOf, Menu, models} from '@eclipse-scout/core';
import MenuActionsBoxModel from './MenuActionsBoxModel';
import {ChildActionsLookupCall, MenuActionsBoxWidgetMap} from '../index';

export class MenuActionsBox extends GroupBox {
  declare widgetMap: MenuActionsBoxWidgetMap;

  menu: Menu;

  constructor() {
    super();
    this.menu = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(MenuActionsBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    let insertMenuButton = this.widget('InsertMenuButton');
    insertMenuButton.on('click', this._onInsertMenuClick.bind(this));

    let deleteMenuButton = this.widget('DeleteMenuButton');
    deleteMenuButton.on('click', this._onDeleteMenuClick.bind(this));

    let menuToDeleteField = this.widget('MenuToDeleteField');
    menuToDeleteField.setLookupCall(new ChildActionsLookupCall(this.menu));
    this._setMenu((this.menu));
  }

  setMenu(menu: Menu) {
    this.setProperty('menu', menu);
  }

  protected _setMenu(menu: Menu) {
    this._setProperty('menu', menu);
    (this.widget('MenuToDeleteField').lookupCall as ChildActionsLookupCall).setAction(this.menu);
  }

  protected _onInsertMenuClick(event: Event<Button>) {
    this.menu.insertChildAction({
      objectType: Menu,
      text: 'Menu ' + (this.menu.childActions.length + 1)
    });
  }

  protected _onDeleteMenuClick(event: Event<Button>) {
    let menuToDeleteField = this.widget('MenuToDeleteField');
    let menu = menuToDeleteField.value;
    this.menu.deleteChildAction(menu);
    // Select last entry in the lookup rows
    menuToDeleteField.lookupCall.getAll().then(result => {
      let rows = result.lookupRows;
      let newValue = null;
      if (rows.length > 0) {
        newValue = rows[rows.length - 1].key;
      }
      menuToDeleteField.setValue(newValue);
    });
  }
}
