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
jswidgets.TreePropertiesBox = function() {
  jswidgets.TreePropertiesBox.parent.call(this);
  this.tree = null;
};
scout.inherits(jswidgets.TreePropertiesBox, scout.GroupBox);

jswidgets.TreePropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TreePropertiesBox');
};

jswidgets.TreePropertiesBox.prototype._init = function(model) {
  jswidgets.TreePropertiesBox.parent.prototype._init.call(this, model);

  this._setTree(this.tree);
};

jswidgets.TreePropertiesBox.prototype.setTree = function(tree) {
  this.setProperty('tree', tree);
};

jswidgets.TreePropertiesBox.prototype._setTree = function(tree) {
  this._setProperty('tree', tree);
  if (!this.tree) {
    return;
  }

  var autoCheckChildrenField = this.widget('AutoCheckChildrenField');
  autoCheckChildrenField.setValue(this.tree.autoResizeColumns);
  autoCheckChildrenField.on('propertyChange', this._onAutoCheckChildrenPropertyChange.bind(this));

  var checkableField = this.widget('CheckableField');
  checkableField.setValue(this.tree.checkable);
  checkableField.on('propertyChange', this._onCheckablePropertyChange.bind(this));

  var multiCheckField = this.widget('MultiCheckField');
  multiCheckField.setValue(this.tree.multiCheck);
  multiCheckField.on('propertyChange', this._onMultiCheckPropertyChange.bind(this));

  var checkableStyleField = this.widget('CheckableStyleField');
  checkableStyleField.setValue(this.tree.checkableStyle);
  checkableStyleField.on('propertyChange', this._onCheckableStylePropertyChange.bind(this));
};

jswidgets.TreePropertiesBox.prototype._onAutoCheckChildrenPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tree.autoCheckChildren = event.newValue;
  }
};

jswidgets.TreePropertiesBox.prototype._onCheckablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tree.setCheckable(event.newValue);
  }
};

jswidgets.TreePropertiesBox.prototype._onMultiCheckPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tree.multiCheck = event.newValue;
  }
};

jswidgets.TreePropertiesBox.prototype._onCheckableStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tree.setCheckableStyle(event.newValue);
  }
};
