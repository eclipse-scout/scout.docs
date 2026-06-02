/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {InitModelOf, models, SmartField, TabBox, TabItem, TabItemModel} from '@eclipse-scout/core';
import TabBoxPropertiesModel from './TabBoxPropertiesModel';
import {TabBoxPropertiesWidgetMap, TabItemLookupCall} from '../index';

export class TabBoxProperties extends TabItem {
  declare widgetMap: TabBoxPropertiesWidgetMap;

  tabBox: TabBox;
  showMenus: boolean;
  selectedTabField: SmartField<TabItem>;

  constructor() {
    super();
    this.tabBox = null;
    this.showMenus = true;
  }

  protected override _jsonModel(): TabItemModel {
    return models.get(TabBoxPropertiesModel);
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

    this.tabBox.on('propertyChange:selectedTab', event => this._updateSelectedTab());

    this.selectedTabField = this.widget('TabBoxProperties.SelectedTabField');
    this.selectedTabField.setLookupCall(new TabItemLookupCall(this.tabBox));
    this.selectedTabField.on('propertyChange:value', event => this.tabBox.setSelectedTab(event.newValue));

    let markStrategy = this.widget('TabBoxProperties.MarkStrategy');
    markStrategy.setValue(this.tabBox.markStrategy);
    markStrategy.on('propertyChange:value', event => this.tabBox.setMarkStrategy(event.newValue));

    let tabAreaStyleField = this.widget('TabBoxProperties.TabAreaStyleField');
    tabAreaStyleField.setValue(this.tabBox.tabAreaStyle);
    tabAreaStyleField.on('propertyChange:value', event => this.tabBox.setTabAreaStyle(event.newValue));

    let showMenusField = this.widget('TabBoxProperties.ShowMenus');
    showMenusField.on('propertyChange:value', event => this.tabBox.menus.forEach(menu => {
      menu.setVisible(showMenusField.value);
    }));
    showMenusField.setValue(this.showMenus);

    this.widget('TabBoxProperties.FormFieldPropertiesBox').setField(this.tabBox);
    this.widget('TabBoxProperties.GridDataBox').setField(this.tabBox);

    this._updateSelectedTab();
  }

  protected _updateSelectedTab() {
    let selectedTab = this.tabBox.selectedTab;
    this.selectedTabField.setValue(selectedTab);
    this.selectedTabField.setEnabled(this.tabBox.tabItems.length > 0);
  }
}
