/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, Event, GroupBox, GroupBoxModel, InitModelOf, models, SmartField, TabBox, TabItem} from '@eclipse-scout/core';
import TabBoxDeleteTabItemBoxModel from './TabBoxDeleteTabItemBoxModel';
import {TabBoxDeleteTabItemBoxWidgetMap, TabItemLookupCall} from '../index';

export class TabBoxDeleteTabItemBox extends GroupBox {
  declare widgetMap: TabBoxDeleteTabItemBoxWidgetMap;

  tabBox: TabBox;
  dynamicTabCounter: number;
  tabItemField: SmartField<TabItem>;
  deleteButton: Button;

  constructor() {
    super();
    this.dynamicTabCounter = 0;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(TabBoxDeleteTabItemBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setTabBox(this.tabBox);
  }

  setTabBox(tabBox: TabBox) {
    this.setProperty('tabBox', tabBox);
  }

  protected _setTabBox(tabBox: TabBox) {
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

  protected _onDeleteTabItemButtonClick(event: Event<Button>) {
    this.deleteTabItem(this.tabItemField.value);
  }

  deleteTabItem(tabItem: TabItem) {
    if (tabItem) {
      this.tabBox.deleteTabItem(tabItem);
      this.tabItemField.setValue(null);
    }
  }
}
