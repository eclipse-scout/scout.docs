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
};
scout.inherits(jswidgets.TabBoxForm, scout.Form);

jswidgets.TabBoxForm.prototype._init = function(model) {
  jswidgets.TabBoxForm.parent.prototype._init.call(this, model);
  var tabBox = this.widget('TabBox');
  tabBox.on('propertyChange', this._onFieldPropertyChange.bind(this));

  var selectedTabField = this.widget('SelectedTabField');
  selectedTabField.setValue(tabBox.selectedTab.id);
  selectedTabField.on('propertyChange', this._onSelectedTabChange.bind(this));

  var markedField = this.widget('CurrentTab.MarkedField');
  markedField.on('propertyChange', this._onCurrentTabMarkedChanged.bind(this));

  var addTabMenu = this.widget('AddTabMenu');
  addTabMenu.on('action', this._onAddTabMenuAction.bind(this));

  this.widget('ShareMenu').on('action', function(){
    addTabMenu.setEnabled(!addTabMenu.enabled);
  }.bind(this));



  this.widget('FormFieldPropertiesBox').setField(tabBox);
  this.widget('GridDataBox').setField(tabBox);
  this._updateSelectedTab();
};

jswidgets.TabBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxForm');
};

jswidgets.TabBoxForm.prototype._onAddTabMenuAction = function(event) {
  // handle adding tab item here
};

jswidgets.TabBoxForm.prototype._onSelectedTabChange = function(event) {
  if (event.propertyName === 'value') {
    if (event.newValue) {
      this.widget('TabBox').selectTabById(event.newValue);
    } else {
      this.widget('TabBox').setSelectedTab();
    }
  }
};

jswidgets.TabBoxForm.prototype._onCurrentTabMarkedChanged = function(event) {
  if (event.propertyName === 'value') {
    this.widget('TabBox').selectedTab.setMarked(event.newValue);
  }
};


jswidgets.TabBoxForm.prototype._onFieldPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    this._updateSelectedTab();

  }
};

jswidgets.TabBoxForm.prototype._updateSelectedTab = function(){
  var tabBox = this.widget('TabBox');
  this.widget('SelectedTabField').setValue((tabBox.selectedTab) ? (tabBox.selectedTab.id) : (null));
  this.widget('CurrentTab.MarkedField').setValue(tabBox.selectedTab.marked);
  this.widget('CurrentTab.GroupBoxPropertiesBox').setField(tabBox.selectedTab);
  this.widget('CurrentTab.FormFieldPropertiesBox').setField(tabBox.selectedTab);
};
