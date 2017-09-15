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
jswidgets.SmartFieldPropertiesBox = function() {
  jswidgets.SmartFieldPropertiesBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.SmartFieldPropertiesBox, scout.GroupBox);

jswidgets.SmartFieldPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SmartFieldPropertiesBox');
};

jswidgets.SmartFieldPropertiesBox.prototype._init = function(model) {
  jswidgets.SmartFieldPropertiesBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
};

jswidgets.SmartFieldPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.SmartFieldPropertiesBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  var displayStyleField = this.widget('DisplayStyleField');
  displayStyleField.setValue(this.field.displayStyle);
  displayStyleField.on('propertyChange', this._onDisplayStylePropertyChange.bind(this));

  var browseMaxRowCountField = this.widget('BrowseMaxRowCountField');
  browseMaxRowCountField.setValue(this.field.browseMaxRowCount);
  browseMaxRowCountField.on('propertyChange', this._onBrowseMaxRowCountPropertyChange.bind(this));

  var activeFilterEnabledField = this.widget('ActiveFilterEnabledField');
  activeFilterEnabledField.setValue(this.field.activeFilterEnabled);
  activeFilterEnabledField.on('propertyChange', this._onActiveFilterEnabledPropertyChange.bind(this));
};

jswidgets.SmartFieldPropertiesBox.prototype._onDisplayStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.displayStyle = event.newValue;
    this.field.remove();
    // Expects the parent to be a group box
    this.field.parent._renderControls();
    this.field.parent.htmlBody.revalidateLayoutTree();
  }
};

jswidgets.SmartFieldPropertiesBox.prototype._onBrowseMaxRowCountPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setBrowseMaxRowCount(event.newValue);
  }
};

jswidgets.SmartFieldPropertiesBox.prototype._onActiveFilterEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setActiveFilterEnabled(event.newValue);
  }
};
