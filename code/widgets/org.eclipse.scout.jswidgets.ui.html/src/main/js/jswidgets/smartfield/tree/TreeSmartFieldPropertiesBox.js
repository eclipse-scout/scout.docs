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
jswidgets.TreeSmartFieldPropertiesBox = function() {
  jswidgets.TreeSmartFieldPropertiesBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.TreeSmartFieldPropertiesBox, jswidgets.SmartFieldPropertiesBox);

jswidgets.TreeSmartFieldPropertiesBox.prototype._jsonModel = function() {
  return scout.models.extend('jswidgets.TreeSmartFieldPropertiesBox', jswidgets.TreeSmartFieldPropertiesBox.parent.prototype._jsonModel.call(this));
};

jswidgets.TreeSmartFieldPropertiesBox.prototype._init = function(model) {
  jswidgets.TreeSmartFieldPropertiesBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
  this.widget('DisplayStyleField').setVisible(false);
};

jswidgets.TreeSmartFieldPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.TreeSmartFieldPropertiesBox.prototype._setField = function(field) {
  jswidgets.TreeSmartFieldPropertiesBox.parent.prototype._setField.call(this, field);
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  var browseAutoExpandAllField = this.widget('BrowseAutoExpandAllField');
  browseAutoExpandAllField.setValue(this.field.browseAutoExpandAll);
  browseAutoExpandAllField.on('propertyChange', this._onBrowseAutoExpandAllPropertyChange.bind(this));

  var browseLoadIncrementalField = this.widget('BrowseLoadIncrementalField');
  browseLoadIncrementalField.setValue(this.field.browseLoadIncremental);
  browseLoadIncrementalField.on('propertyChange', this._onBrowseLoadIncrementalPropertyChange.bind(this));
};

jswidgets.TreeSmartFieldPropertiesBox.prototype._onBrowseAutoExpandAllPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setBrowseAutoExpandAll(event.newValue);
  }
};

jswidgets.TreeSmartFieldPropertiesBox.prototype._onBrowseLoadIncrementalPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setBrowseLoadIncremental(event.newValue);
  }
};
