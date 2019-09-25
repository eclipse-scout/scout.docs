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
jswidgets.GroupBoxDeleteFieldBox = function() {
  jswidgets.GroupBoxDeleteFieldBox.parent.call(this);
  this.field = null;
  this.dynamicFieldCounter = 0;
};
scout.inherits(jswidgets.GroupBoxDeleteFieldBox, scout.GroupBox);

jswidgets.GroupBoxDeleteFieldBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxDeleteFieldBox');
};

jswidgets.GroupBoxDeleteFieldBox.prototype._init = function(model) {
  jswidgets.GroupBoxDeleteFieldBox.parent.prototype._init.call(this, model);
  this._setField(this.field);
};

jswidgets.GroupBoxDeleteFieldBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.GroupBoxDeleteFieldBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  this.targetField = this.widget('ToDeleteField');
  this.targetField.setLookupCall(new jswidgets.FormFieldLookupCall(this.field));
  this.targetField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));

  this.deleteFieldButton = this.widget('DeleteButton');
  this.deleteFieldButton.on('click', this._onDeleteFormFieldButtonClick.bind(this));
};

jswidgets.GroupBoxDeleteFieldBox.prototype._onTargetFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.deleteFieldButton.setEnabled(!!event.newValue);
  }
};

jswidgets.GroupBoxDeleteFieldBox.prototype._onDeleteFormFieldButtonClick = function(event) {
  this.field.deleteField(this.targetField.value);
  this.targetField.setValue(null);
  // Validate layout immediately to prevent flickering
  this.field.validateLayoutTree();
};

jswidgets.GroupBoxDeleteFieldBox.prototype.setTargetField = function(field) {
  this.targetField.setValue(field);
};
