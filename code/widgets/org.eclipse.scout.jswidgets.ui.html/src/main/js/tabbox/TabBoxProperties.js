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
import {models, TabItem} from '@eclipse-scout/core';
import TabBoxPropertiesModel from './TabBoxPropertiesModel';
import {TabItemLookupCall} from '../index';

export default class TabBoxProperties extends TabItem {

  constructor() {
    super();
    this.tabBox = null;
    this.showMenus = true;
  }

  _jsonModel() {
    return models.get(TabBoxPropertiesModel);
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

    this.tabBox.on('propertyChange', this._onTabBoxPropertyChange.bind(this));

    this.selectedTabField = this.widget('TabBoxProperties.SelectedTabField');
    this.selectedTabField.lookupCall = new TabItemLookupCall(this.tabBox);
    this.selectedTabField.on('propertyChange', this._onSelectedTabChange.bind(this));

    this.tabAreaStyleField = this.widget('TabBoxProperties.TabAreaStyleField');
    this.tabAreaStyleField.on('propertyChange', this._onTabAreaStyleChange.bind(this));

    this.showMenusField = this.widget('TabBoxProperties.ShowMenus');
    this.showMenusField.on('propertyChange', this._onShowMenusChange.bind(this));
    this.showMenusField.setValue(this.showMenus);

    this.widget('TabBoxProperties.FormFieldPropertiesBox').setField(this.tabBox);
    this.widget('TabBoxProperties.GridDataBox').setField(this.tabBox);

    this._updateSelectedTab();
  }

  _onSelectedTabChange(event) {
    if (event.propertyName === 'value') {
      this.tabBox.setSelectedTab(event.newValue);
    }
  }

  _onTabAreaStyleChange(event) {
    if (event.propertyName === 'value') {
      this.tabBox.setTabAreaStyle(event.newValue);
    }
  }

  _onTabBoxPropertyChange(event) {
    if (event.propertyName === 'selectedTab') {
      this._updateSelectedTab();
    }
  }

  _onShowMenusChange(event) {
    if (event.propertyName === 'value') {
      this.tabBox.menus.forEach(function(m) {
        m.setVisible(this.showMenusField.value)
      }, this);
    }
  }

  _updateSelectedTab() {
    var selectedTab = this.tabBox.selectedTab;
    this.selectedTabField.setValue(selectedTab);
    this.selectedTabField.setEnabled(this.tabBox.tabItems.length > 0);
  }
}
