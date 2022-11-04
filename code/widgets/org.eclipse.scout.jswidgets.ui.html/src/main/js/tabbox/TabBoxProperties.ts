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
import {CheckBoxField, InitModelOf, models, SmartField, TabAreaStyle, TabBox, TabItem, TabItemModel} from '@eclipse-scout/core';
import TabBoxPropertiesModel from './TabBoxPropertiesModel';
import {TabBoxPropertiesWidgetMap, TabItemLookupCall} from '../index';

export class TabBoxProperties extends TabItem {
  declare widgetMap: TabBoxPropertiesWidgetMap;

  tabBox: TabBox;
  showMenus: boolean;
  selectedTabField: SmartField<TabItem>;
  tabAreaStyleField: SmartField<TabAreaStyle>;
  showMenusField: CheckBoxField;

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
    this.selectedTabField.lookupCall = new TabItemLookupCall(this.tabBox);
    this.selectedTabField.on('propertyChange:value', event => this.tabBox.setSelectedTab(event.newValue));

    this.tabAreaStyleField = this.widget('TabBoxProperties.TabAreaStyleField');
    this.tabAreaStyleField.on('propertyChange:value', event => this.tabBox.setTabAreaStyle(event.newValue));

    this.showMenusField = this.widget('TabBoxProperties.ShowMenus');
    this.showMenusField.on('propertyChange:value', event => this.tabBox.menus.forEach(menu => {
      menu.setVisible(this.showMenusField.value);
    }));
    this.showMenusField.setValue(this.showMenus);

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
