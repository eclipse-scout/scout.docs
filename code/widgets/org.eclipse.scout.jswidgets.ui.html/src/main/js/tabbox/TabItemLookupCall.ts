/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {InitModelOf, StaticLookupCall, StaticLookupCallModel, TabBox, TabItem} from '@eclipse-scout/core';

export class TabItemLookupCall extends StaticLookupCall<TabItem> implements TabItemLookupCallModel {
  declare model: TabItemLookupCallModel;
  tabBox: TabBox;
  protected _rebuildDataHandler: () => void;

  constructor() {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.setTabBox(this.tabBox);
  }

  setTabBox(tabBox: TabBox) {
    if (this.tabBox) {
      this.tabBox.off('propertyChange:tabItems', this._rebuildDataHandler);
      this.tabBox.tabItems.forEach(tabItem => tabItem.off('propertyChange:label', this._rebuildDataHandler));
    }
    this.tabBox = tabBox;
    this.tabBox.on('propertyChange:tabItems', this._rebuildDataHandler);
    this.tabBox.tabItems.forEach(tabItem => tabItem.on('propertyChange:label', this._rebuildDataHandler));
    this._rebuildData();
  }

  protected _rebuildData() {
    this.data = this.tabBox.tabItems.map(tabItem => {
      return [tabItem, tabItem.label];
    });
  }
}

export interface TabItemLookupCallModel extends StaticLookupCallModel<TabItem> {
  tabBox?: TabBox;
}
