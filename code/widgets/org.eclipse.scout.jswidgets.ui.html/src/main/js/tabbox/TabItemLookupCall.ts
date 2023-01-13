/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, TabBox, TabItem} from '@eclipse-scout/core';

export class TabItemLookupCall extends StaticLookupCall<TabItem> {
  tabBox: TabBox;
  protected _rebuildDataHandler: () => void;

  constructor(tabBox: TabBox) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setTabBox(tabBox);
  }

  protected override _data(): any[] {
    return this.data;
  }

  setTabBox(tabBox: TabBox) {
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

  protected _rebuildData() {
    this.data = this.tabBox.tabItems.map(tabItem => {
      return [tabItem, tabItem.label];
    });
  }
}
