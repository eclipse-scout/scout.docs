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

jswidgets.TabBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TabBoxForm');
};

jswidgets.TabBoxForm.prototype._init = function(model) {
  jswidgets.TabBoxForm.parent.prototype._init.call(this, model);
  var tabBox = this.widget('TabBox');

  this.widget('Properties.TabBox').setTabBox(tabBox);
  this.widget('Properties.TabItem').setTabBox(tabBox);
  this.addTabItemBox = this.widget('ActionBox.AddTabItem');
  this.addTabItemBox.setTabBox(tabBox);
  this.deleteTabItemBox = this.widget('ActionBox.DeleteTabItem');
  this.deleteTabItemBox.setTabBox(tabBox);

  var addTabMenu = this.widget('AddTabMenu');
  addTabMenu.on('action', function() {
    this.addTabItemBox.addTabItem();
  }.bind(this));

  var deleteMenu = this.widget('DeleteTabMenu');
  deleteMenu.on('action', function() {
    this.deleteTabItemBox.deleteTabItem(tabBox.selectedTab);
  }.bind(this));

  this.widget('ShareMenu').on('action', function() {
    addTabMenu.setEnabled(!addTabMenu.enabled);
  }.bind(this));
};
