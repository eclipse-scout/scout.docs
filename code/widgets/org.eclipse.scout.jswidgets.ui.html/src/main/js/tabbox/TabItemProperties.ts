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
import {models, TabItem} from '@eclipse-scout/core';
import TabItemPropertiesModel from './TabItemPropertiesModel';
import {TabItemLookupCall} from '../index';

export default class TabItemProperties extends TabItem {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.get(TabItemPropertiesModel);
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

    this.targetField = this.widget('TabItemProperties.TargetField');
    this.targetField.lookupCall = new TabItemLookupCall(this.tabBox);
    this.targetField.on('propertyChange:value', event => this.setTabItem(event.newValue));

    this.markedField = this.widget('TabItemProperties.MarkedField');
    this.markedField.on('propertyChange:value', event => {
      if (this.tabItem) {
        this.tabItem.setMarked(event.newValue);
      }
    });

    this.setTabItem(this.tabBox.selectedTab);
  }

  setTabItem(tabItem) {
    this.setProperty('tabItem', tabItem);
  }

  _setTabItem(tabItem) {
    this._setProperty('tabItem', tabItem);
    this.targetField.setValue(this.tabItem);
    if (!this.tabItem) {
      return;
    }

    this.markedField.setValue(this.tabItem.marked);
    this.widget('TabItemProperties.GroupBoxPropertiesBox').setField(this.tabItem);
    this.widget('TabItemProperties.GridDataBox').setField(this.tabItem);
    this.widget('TabItemProperties.FormFieldPropertiesBox').setField(this.tabItem);
  }
}
