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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class TabItemLookupCall extends StaticLookupCall {

  constructor(tabBox) {
    super();
    this._tabBoxPropertyChangeHandler = this._onTabBoxPropertyChange.bind(this);
    this._tabItemPropertyChangeHandler = this._onTabItemPropertyChange.bind(this);
    this.data = [];
    this.setTabBox(tabBox);
  }


  _data() {
    return this.data;
  }

  setTabBox(tabBox) {
    if (this.tabBox) {
      this.tabBox.off('propertyChange', this._tabBoxPropertyChangeHandler);
      this.tabBox.tabItems.forEach(function(tabItem) {
        tabItem.off('propertyChange', this._tabItemPropertyChangeHandler);
      }, this);
    }
    this.tabBox = tabBox;
    this.tabBox.on('propertyChange', this._tabBoxPropertyChangeHandler);
    this.tabBox.tabItems.forEach(function(tabItem) {
      tabItem.on('propertyChange', this._tabItemPropertyChangeHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.tabBox.tabItems.map(function(tabItem) {
      return [tabItem, tabItem.label];
    });
  }

  _onTabBoxPropertyChange(event) {
    if (event.propertyName === 'tabItems') {
      this._rebuildData();
    }
  }

  _onTabItemPropertyChange(event) {
    if (event.propertyName === 'label') {
      this._rebuildData();
    }
  }
}
