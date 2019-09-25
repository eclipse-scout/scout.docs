/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
jswidgets.TabItemLookupCall = function(tabBox) {
  jswidgets.TabItemLookupCall.parent.call(this);
  this._tabBoxPropertyChangeHandler = this._onTabBoxPropertyChange.bind(this);
  this._tabItemPropertyChangeHandler = this._onTabItemPropertyChange.bind(this);
  this.data = [];
  this.setTabBox(tabBox);
};
scout.inherits(jswidgets.TabItemLookupCall, scout.StaticLookupCall);

jswidgets.TabItemLookupCall.prototype._data = function() {
  return this.data;
};

jswidgets.TabItemLookupCall.prototype.setTabBox = function(tabBox) {
  if (this.tabBox) {
    this.tabBox.off('propertyChange', this._tabBoxPropertyChangeHandler);
    this.tabBox.tabItems.forEach(function(tabItem) {
      tabItem.off('propertyChange', this._tabItemPropertyChangeHandler);
    }, this);
  }
  this.tabBox = tabBox;
  this.tabBox.on('propertyChange', this._tabBoxPropertyChangeHandler);
  this.tabBox.tabItems.forEach(function(tabItem) {
    tabItem.on('propertyChange', this._tabItemPropertyChangeHandler);
  }, this);
  this._rebuildData();
};

jswidgets.TabItemLookupCall.prototype._rebuildData = function() {
  this.data = this.tabBox.tabItems.map(function(tabItem) {
    return [tabItem, tabItem.label];
  });
};

jswidgets.TabItemLookupCall.prototype._onTabBoxPropertyChange = function(event) {
  if (event.propertyName === 'tabItems') {
    this._rebuildData();
  }
};
jswidgets.TabItemLookupCall.prototype._onTabItemPropertyChange = function(event) {
  if (event.propertyName === 'label') {
    this._rebuildData();
  }
};
