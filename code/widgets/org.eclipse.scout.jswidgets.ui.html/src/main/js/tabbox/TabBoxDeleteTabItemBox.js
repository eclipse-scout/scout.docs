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
import {GroupBox, models} from '@eclipse-scout/core';
import TabBoxDeleteTabItemBoxModel from './TabBoxDeleteTabItemBoxModel';
import {TabItemLookupCall} from '../index';

export default class TabBoxDeleteTabItemBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.dynamicTabCounter = 0;
  }

  _jsonModel() {
    return models.get(TabBoxDeleteTabItemBoxModel);
  }

  _init(model) {
    super._init(model);
    this._setTabBox(this.tabBox);
  }

  setTabBox(tabBox) {
    this.setProperty('tabBox', tabBox);
  }

  _setTabBox(tabBox) {
    this._setProperty('tabBox', tabBox);
    if (!this.tabBox) {
      return;
    }

    this.tabItemField = this.widget('DeleteTabItem.TabItem');
    this.tabItemField.lookupCall = new TabItemLookupCall(this.tabBox);
    this.tabItemField.on('propertyChange:value', event => this.deleteButton.setEnabled(!!event.newValue));

    this.deleteButton = this.widget('DeleteTabItem.DeleteButton');
    this.deleteButton.on('click', this._onDeleteTabItemButtonClick.bind(this));
  }

  _onDeleteTabItemButtonClick(event) {
    this.deleteTabItem(this.tabItemField.value);
  }

  deleteTabItem(tabItem) {
    if (tabItem) {
      this.tabBox.deleteTabItem(tabItem);
      this.tabItemField.setValue(null);
    }
  }
}
