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
jswidgets.TabBoxActionAddTabItem = function() {
  jswidgets.TabBoxActionAddTabItem.parent.call(this);
  this.field = null;
  this.dynamicTabCounter = 0;
};
scout.inherits(jswidgets.TabBoxActionAddTabItem, scout.GroupBox);

jswidgets.TabBoxActionAddTabItem.prototype._init = function(model) {
  jswidgets.TabBoxActionAddTabItem.parent.prototype._init.call(this, model);
  this._setTabBox(this.tabBox);
};

jswidgets.TabBoxActionAddTabItem.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxActionAddTabItem');
};

jswidgets.TabBoxActionAddTabItem.prototype.setTabBox = function(tabBox) {
  this.setProperty('tabBox', tabBox);
};

jswidgets.TabBoxActionAddTabItem.prototype._setTabBox = function(tabBox) {
  this._setProperty('tabBox', tabBox);
  if (!this.tabBox) {
    return;
  }

  this.labelField = this.widget('AddTabItem.Label');

  this.subLabelField = this.widget('AddTabItem.SubLabel');

  this.beforeField = this.widget('AddTabItem.TabItemSmartField');
  this.beforeField.lookupCall = new jswidgets.TabItemLookupCall(this.tabBox);

  this.addTabItemButton = this.widget('AddTabItem.CreateButton');
  this.addTabItemButton.on('click', this._onAddTabItemButtonClick.bind(this));

  this._updateAddTabLabel();
};

jswidgets.TabBoxActionAddTabItem.prototype._onAddTabItemButtonClick = function(event) {
  this.addTabItem(this.labelField.value, this.subLabelField.value, this.beforeField.value);
};

jswidgets.TabBoxActionAddTabItem.prototype.addTabItem = function(label, subLabel, beforeTabItem) {
  var tabItems = this.tabBox.tabItems || [],
    beforeIndex,
    tabItem = scout.create('jswidgets.DynamicTab', {
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
};

jswidgets.TabBoxActionAddTabItem.prototype._updateAddTabLabel = function() {
  this.labelField.setValue('DynTab ' + this.dynamicTabCounter);
};
