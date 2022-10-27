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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class TabItemLookupCall extends StaticLookupCall {

  constructor(tabBox) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setTabBox(tabBox);
  }

  _data() {
    return this.data;
  }

  setTabBox(tabBox) {
    if (this.tabBox) {
      this.tabBox.off('propertyChange:tabItems', this._rebuildDataHandler);
      this.tabBox.tabItems.forEach(function(tabItem) {
        tabItem.off('propertyChange:label', this._rebuildDataHandler);
      }, this);
    }
    this.tabBox = tabBox;
    this.tabBox.on('propertyChange:tabItems', this._rebuildDataHandler);
    this.tabBox.tabItems.forEach(function(tabItem) {
      tabItem.on('propertyChange:label', this._rebuildDataHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.tabBox.tabItems.map(tabItem => {
      return [tabItem, tabItem.label];
    });
  }
}
