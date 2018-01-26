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
jswidgets.TabBoxActionDeleteTabItem = function() {
  jswidgets.TabBoxActionDeleteTabItem.parent.call(this);
  this.field = null;
  this.dynamicTabCounter = 0;
};
scout.inherits(jswidgets.TabBoxActionDeleteTabItem, scout.GroupBox);

jswidgets.TabBoxActionDeleteTabItem.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxActionDeleteTabItem');
};

jswidgets.TabBoxActionDeleteTabItem.prototype._init = function(model) {
  jswidgets.TabBoxActionDeleteTabItem.parent.prototype._init.call(this, model);
  this._setTabBox(this.tabBox);
};

jswidgets.TabBoxActionDeleteTabItem.prototype.setTabBox = function(tabBox) {
  this.setProperty('tabBox', tabBox);
};

jswidgets.TabBoxActionDeleteTabItem.prototype._setTabBox = function(tabBox) {
  this._setProperty('tabBox', tabBox);
  if (!this.tabBox) {
    return;
  }

  this.tabItemField = this.widget('DeleteTabItem.TabItem');
  this.tabItemField.lookupCall = new jswidgets.TabItemLookupCall(this.tabBox);
  this.tabItemField.on('propertyChange', this._onTabItemFieldPropertyChange.bind(this));

  this.deleteButton = this.widget('DeleteTabItem.DeleteButton');
  this.deleteButton.on('click', this._onDeleteTabItemButtonClick.bind(this));
};

jswidgets.TabBoxActionDeleteTabItem.prototype._onTabItemFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.deleteButton.setEnabled(!!event.newValue);
  }
};

jswidgets.TabBoxActionDeleteTabItem.prototype._onDeleteTabItemButtonClick = function(event) {
  this.deleteTabItem(this.tabItemField.value);
};

jswidgets.TabBoxActionDeleteTabItem.prototype.deleteTabItem = function(tabItem) {
  if (tabItem) {
    this.tabBox.deleteTabItem(tabItem);
    this.tabItemField.setValue(null);
  }
};
