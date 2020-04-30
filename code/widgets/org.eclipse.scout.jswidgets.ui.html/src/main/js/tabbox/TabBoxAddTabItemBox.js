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
import {GroupBox, models, scout} from '@eclipse-scout/core';
import {TabItemLookupCall} from '../index';
import TabBoxAddTabItemBoxModel from './TabBoxAddTabItemBoxModel';

export default class TabBoxAddTabItemBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.dynamicTabCounter = 0;
  }

  _jsonModel() {
    return models.get(TabBoxAddTabItemBoxModel);
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

    this.labelField = this.widget('AddTabItem.Label');

    this.subLabelField = this.widget('AddTabItem.SubLabel');

    this.beforeField = this.widget('AddTabItem.TabItemSmartField');
    this.beforeField.lookupCall = new TabItemLookupCall(this.tabBox);

    this.addTabItemButton = this.widget('AddTabItem.CreateButton');
    this.addTabItemButton.on('click', this._onAddTabItemButtonClick.bind(this));

    this._updateAddTabLabel();
  }

  _onAddTabItemButtonClick(event) {
    this.addTabItem(this.labelField.value, this.subLabelField.value, this.beforeField.value);
  }

  addTabItem(label, subLabel, beforeTabItem) {
    let tabItems = this.tabBox.tabItems || [],
      beforeIndex,
      tabItem = scout.create('jswidgets.DynamicTab', {
        parent: this.tabBox,
        label: label || 'DynTab ' + this.dynamicTabCounter,
        subLabel: subLabel
      });
    if (beforeTabItem) {
      beforeIndex = tabItems.indexOf(beforeTabItem);
    }

    this.tabBox.insertTabItem(tabItem, beforeIndex);
    this.dynamicTabCounter++;
    this._updateAddTabLabel();
  }

  _updateAddTabLabel() {
    this.labelField.setValue('DynTab ' + this.dynamicTabCounter);
  }
}
