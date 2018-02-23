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
jswidgets.TabBoxDeleteTabItemBox = function() {
  jswidgets.TabBoxDeleteTabItemBox.parent.call(this);
  this.field = null;
  this.dynamicTabCounter = 0;
};
scout.inherits(jswidgets.TabBoxDeleteTabItemBox, scout.GroupBox);

jswidgets.TabBoxDeleteTabItemBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxDeleteTabItemBox');
};

jswidgets.TabBoxDeleteTabItemBox.prototype._init = function(model) {
  jswidgets.TabBoxDeleteTabItemBox.parent.prototype._init.call(this, model);
  this._setTabBox(this.tabBox);
};

jswidgets.TabBoxDeleteTabItemBox.prototype.setTabBox = function(tabBox) {
  this.setProperty('tabBox', tabBox);
};

jswidgets.TabBoxDeleteTabItemBox.prototype._setTabBox = function(tabBox) {
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

jswidgets.TabBoxDeleteTabItemBox.prototype._onTabItemFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.deleteButton.setEnabled(!!event.newValue);
  }
};

jswidgets.TabBoxDeleteTabItemBox.prototype._onDeleteTabItemButtonClick = function(event) {
  this.deleteTabItem(this.tabItemField.value);
};

jswidgets.TabBoxDeleteTabItemBox.prototype.deleteTabItem = function(tabItem) {
  if (tabItem) {
    this.tabBox.deleteTabItem(tabItem);
    this.tabItemField.setValue(null);
  }
};
