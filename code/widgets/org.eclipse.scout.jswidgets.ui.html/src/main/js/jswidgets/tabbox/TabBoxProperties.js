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
jswidgets.TabBoxProperties = function() {
  jswidgets.TabBoxProperties.parent.call(this);
  this.tabBox = null;
};
scout.inherits(jswidgets.TabBoxProperties, scout.TabItem);

jswidgets.TabBoxProperties.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxProperties');
};

jswidgets.TabBoxProperties.prototype._init = function(model) {
  jswidgets.TabBoxProperties.parent.prototype._init.call(this, model);
  this._setTabBox(this.tabBox);
};

jswidgets.TabBoxProperties.prototype.setTabBox = function(tabBox) {
  this.setProperty('tabBox', tabBox);
};

jswidgets.TabBoxProperties.prototype._setTabBox = function(tabBox) {
  this._setProperty('tabBox', tabBox);
  if (!this.tabBox) {
    return;
  }

  this.tabBox.on('propertyChange', this._onTabBoxPropertyChange.bind(this));

  this.selectedTabField = this.widget('TabBoxProperties.SelectedTabField');
  this.selectedTabField.lookupCall = new jswidgets.TabItemLookupCall(this.tabBox);
  this.selectedTabField.on('propertyChange', this._onSelectedTabChange.bind(this));

  this.widget('TabBoxProperties.FormFieldPropertiesBox').setField(this.tabBox);
  this.widget('TabBoxProperties.GridDataBox').setField(this.tabBox);

  this._updateSelectedTab();
};

jswidgets.TabBoxProperties.prototype._onSelectedTabChange = function(event) {
  if (event.propertyName === 'value') {
    this.tabBox.setSelectedTab(event.newValue);
  }
};

jswidgets.TabBoxProperties.prototype._onTabBoxPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    this._updateSelectedTab();
  }
};

jswidgets.TabBoxProperties.prototype._updateSelectedTab = function() {
  var selectedTab = this.tabBox.selectedTab;
  this.selectedTabField.setValue(selectedTab);
  this.selectedTabField.setEnabled(this.tabBox.tabItems.length > 0);
};
