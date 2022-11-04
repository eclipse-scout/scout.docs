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
import {Button, Event, GroupBox, GroupBoxModel, InitModelOf, models, scout, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {DynamicTab, TabBoxAddTabItemBoxWidgetMap, TabItemLookupCall} from '../index';
import TabBoxAddTabItemBoxModel from './TabBoxAddTabItemBoxModel';

export class TabBoxAddTabItemBox extends GroupBox {
  declare widgetMap: TabBoxAddTabItemBoxWidgetMap;

  tabBox: TabBox;
  dynamicTabCounter: number;
  labelField: StringField;
  subLabelField: StringField;
  beforeField: SmartField<TabItem>;
  addTabItemButton: Button;

  constructor() {
    super();
    this.dynamicTabCounter = 0;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(TabBoxAddTabItemBoxModel);
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

    this.labelField = this.widget('AddTabItem.Label');

    this.subLabelField = this.widget('AddTabItem.SubLabel');

    this.beforeField = this.widget('AddTabItem.TabItemSmartField');
    this.beforeField.lookupCall = new TabItemLookupCall(this.tabBox);

    this.addTabItemButton = this.widget('AddTabItem.CreateButton');
    this.addTabItemButton.on('click', this._onAddTabItemButtonClick.bind(this));

    this._updateAddTabLabel();
  }

  protected _onAddTabItemButtonClick(event: Event<Button>) {
    this.addTabItem(this.labelField.value, this.subLabelField.value, this.beforeField.value);
  }

  addTabItem(label?: string, subLabel?: string, beforeTabItem?: TabItem) {
    let tabItems = this.tabBox.tabItems || [],
      beforeIndex,
      tabItem = scout.create(DynamicTab, {
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

  protected _updateAddTabLabel() {
    this.labelField.setValue('DynTab ' + this.dynamicTabCounter);
  }
}
