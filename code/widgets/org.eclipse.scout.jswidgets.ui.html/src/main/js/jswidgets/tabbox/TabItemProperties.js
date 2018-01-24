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
jswidgets.TabItemProperties = function() {
  jswidgets.TabItemProperties.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.TabItemProperties, scout.TabItem);

jswidgets.TabItemProperties.prototype._init = function(model) {
  jswidgets.TabItemProperties.parent.prototype._init.call(this, model);
  this._setTabBox(this.tabBox);
};

jswidgets.TabItemProperties.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabItemProperties');
};

jswidgets.TabItemProperties.prototype.setTabBox = function(tabBox) {
  this.setProperty('tabBox', tabBox);
};

jswidgets.TabItemProperties.prototype._setTabBox = function(tabBox) {
  this._setProperty('tabBox', tabBox);
  if (!this.tabBox) {
    return;
  }

  this.targetField = this.widget('TabItemProperties.TargetField');
  this.targetField.lookupCall = new jswidgets.TabItemLookupCall(this.tabBox);
  this.targetField.on('propertyChange', this._onTargetTabItemChange.bind(this));

  this.markedField = this.widget('TabItemProperties.MarkedField');
  this.markedField.on('propertyChange', this._onCurrentTabMarkedChanged.bind(this));

  this.setTabItem(this.tabBox.selectedTab);
};

jswidgets.TabItemProperties.prototype.setTabItem = function(tabItem) {
  this.setProperty('tabItem', tabItem);
};

jswidgets.TabItemProperties.prototype._setTabItem = function(tabItem) {
  this._setProperty('tabItem', tabItem);
  this.targetField.setValue(this.tabItem);
  if (!this.tabItem) {
    return;
  }

  this.markedField.setValue(this.tabItem.marked);
  this.widget('TabItemProperties.GroupBoxPropertiesBox').setField(this.tabItem);
  this.widget('TabItemProperties.GridDataBox').setField(this.tabItem);
  this.widget('TabItemProperties.FormFieldPropertiesBox').setField(this.tabItem);
};

jswidgets.TabItemProperties.prototype._onTargetTabItemChange = function(event) {
  if (event.propertyName === 'value') {
    this.setTabItem(event.newValue);
  }
};

jswidgets.TabItemProperties.prototype._onCurrentTabMarkedChanged = function(event) {
  if (event.propertyName === 'value' && this.tabItem) {
    this.tabItem.setMarked(event.newValue);
  }
};
