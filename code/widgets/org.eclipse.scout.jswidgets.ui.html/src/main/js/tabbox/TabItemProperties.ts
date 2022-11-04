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
import {CheckBoxField, FormField, InitModelOf, models, SmartField, TabBox, TabItem, TabItemModel} from '@eclipse-scout/core';
import TabItemPropertiesModel from './TabItemPropertiesModel';
import {TabItemLookupCall, TabItemPropertiesWidgetMap} from '../index';

export class TabItemProperties extends TabItem {
  declare widgetMap: TabItemPropertiesWidgetMap;

  tabBox: TabBox;
  tabItem: TabItem;
  field: FormField;
  targetField: SmartField<TabItem>;
  markedField: CheckBoxField;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): TabItemModel {
    return models.get(TabItemPropertiesModel);
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

  setTabItem(tabItem: TabItem) {
    this.setProperty('tabItem', tabItem);
  }

  protected _setTabItem(tabItem: TabItem) {
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
