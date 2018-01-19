/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.TabBoxForm = function() {
  jswidgets.TabBoxForm.parent.call(this);
  this.dynamicTabCounter = 0;
};
scout.inherits(jswidgets.TabBoxForm, scout.Form);

jswidgets.TabBoxForm.prototype._init = function(model) {
  jswidgets.TabBoxForm.parent.prototype._init.call(this, model);
  var tabBox = this.widget('TabBox');
  tabBox.on('propertyChange', this._onFieldPropertyChange.bind(this));

  var addTabMenu = this.widget('AddTabMenu');
  addTabMenu.on('action', this._onAddTabMenuAction.bind(this));

  var selectedTabField = this.widget('SelectedTabField');
  selectedTabField.lookupCall = new jswidgets.TabItemLookupCall(tabBox);
  selectedTabField.on('propertyChange', this._onSelectedTabChange.bind(this));

  var markedField = this.widget('CurrentTab.MarkedField');
  markedField.on('propertyChange', this._onCurrentTabMarkedChanged.bind(this));

  var deleteMenu = this.widget('CurrentTab.DeleteMenu');
  deleteMenu.on('action', this._onDeleteTabMenuAction.bind(this));

  this.widget('ShareMenu').on('action', function() {
    addTabMenu.setEnabled(!addTabMenu.enabled);
  }.bind(this));

  this.widget('FormFieldPropertiesBox').setField(tabBox);
  this.widget('GridDataBox').setField(tabBox);

  // add tab item tab
  var beforeField = this.widget('AddTabItemTab.TabItemSmartField');
  beforeField.lookupCall = new jswidgets.TabItemLookupCall(tabBox);

  var addTabItemButton = this.widget('AddTabItemTab.CreateButton');
  addTabItemButton.on('click', this._onAddTabItemButtonClick.bind(this));

  // remove tab
  var tabItemField = this.widget('DeleteTabItemTab.TabItem');
  tabItemField.lookupCall = new jswidgets.TabItemLookupCall(tabBox);

  var deleteTabItemButton = this.widget('DeleteTabItemTab.DeleteButton');
  deleteTabItemButton.on('click', this._onDeleteTabItemButtonClick.bind(this));

  this._updateSelectedTab();
  this._updateAddTabLabel();
};

jswidgets.TabBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxForm');
};

jswidgets.TabBoxForm.prototype._updateAddTabLabel = function() {
  this.widget('AddTabItemTab.Label').setValue('DynTab ' + this.dynamicTabCounter);
};

jswidgets.TabBoxForm.prototype._onSelectedTabChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('TabBox').setSelectedTab(event.newValue);
  }
};

jswidgets.TabBoxForm.prototype._onCurrentTabMarkedChanged = function(event) {
  if (event.propertyName === 'value') {
    this.widget('TabBox').selectedTab.setMarked(event.newValue);
  }
};

jswidgets.TabBoxForm.prototype._onAddTabItemButtonClick = function(event) {
  var tabBox = this.widget('TabBox'),
    tabItems = tabBox.tabItems || [],
    beforeSmartField = this.widget('AddTabItemTab.TabItemSmartField'),
    beforeIndex,
    tabModel = scout.models.getModel('jswidgets.DynamicTab');
  tabModel.id = 'dynTab' + this.dynamicTabCounter++;
  tabModel.parent = tabBox;
  tabModel.label = this.widget('AddTabItemTab.Label').value || 'DynTab ' + this.dynamicTabCounter;
  tabModel.subLabel = this.widget('AddTabItemTab.SubLabel').value;
  if (beforeSmartField.value) {
    beforeIndex = tabItems.indexOf(beforeSmartField.value);
  }
  tabBox.insertTabItem(scout.create('TabItem', tabModel), beforeIndex);
  this._updateAddTabLabel();
};

jswidgets.TabBoxForm.prototype._onDeleteTabItemButtonClick = function(event) {
  var tabBox = this.widget('TabBox'),
    tabItemField = this.widget('DeleteTabItemTab.TabItem'),
    tabItem = tabItemField.value;
  if (tabItem) {
    tabBox.deleteTabItem(tabItem);
    tabItemField.setValue(null);
  }
};

jswidgets.TabBoxForm.prototype._onAddTabMenuAction = function() {
  var tabBox = this.widget('TabBox'),
    tabItems = tabBox.tabItems || [],
    tabModel = scout.models.getModel('jswidgets.DynamicTab');

  tabModel.id = 'dynTab' + this.dynamicTabCounter++;
  tabModel.parent = tabBox;
  tabItems = tabItems.slice();
  tabItems.push(scout.create('TabItem', tabModel));
  tabBox.setTabItems(tabItems);
};

jswidgets.TabBoxForm.prototype._onDeleteTabMenuAction = function() {
  var tabBox = this.widget('TabBox'),
    selectedTab = tabBox.selectedTab;
  tabBox.deleteTabItem(selectedTab);
};

jswidgets.TabBoxForm.prototype._onFieldPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    this._updateSelectedTab();
  }
};

jswidgets.TabBoxForm.prototype._updateSelectedTab = function() {
  var tabBox = this.widget('TabBox'),
    selectedTab = tabBox.selectedTab,
    selectedTabField = this.widget('SelectedTabField');
  selectedTabField.setValue(selectedTab);
  selectedTabField.setEnabled(tabBox.tabItems.length > 0);
  this.widget('CurrentTabItemProperties').setVisible(!!selectedTab);
  this.widget('CurrentTab.MarkedField').setValue(selectedTab ? tabBox.selectedTab.marked : false);
  this.widget('CurrentTab.GroupBoxPropertiesBox').setField(selectedTab);
  this.widget('CurrentTab.FormFieldPropertiesBox').setField(selectedTab);
};
