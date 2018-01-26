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
jswidgets.GroupBoxAddFieldBox = function() {
  jswidgets.GroupBoxAddFieldBox.parent.call(this);
  this.field = null;
  this.dynamicFieldCounter = 0;
};
scout.inherits(jswidgets.GroupBoxAddFieldBox, scout.GroupBox);

jswidgets.GroupBoxAddFieldBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxAddFieldBox');
};

jswidgets.GroupBoxAddFieldBox.prototype._init = function(model) {
  jswidgets.GroupBoxAddFieldBox.parent.prototype._init.call(this, model);
  this._setField(this.field);
};

jswidgets.GroupBoxAddFieldBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.GroupBoxAddFieldBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  this.beforeField = this.widget('BeforeField');
  this.beforeField.lookupCall = new jswidgets.FormFieldLookupCall(this.field);

  this.labelField = this.widget('LabelField');

  var addFieldButton = this.widget('CreateButton');
  addFieldButton.on('click', this._onAddFormFieldButtonClick.bind(this));

  this._updateAddFieldLabel();
};

jswidgets.GroupBoxAddFieldBox.prototype._updateAddFieldLabel = function() {
  this.labelField.setValue('DynField ' + this.dynamicFieldCounter);
};

jswidgets.GroupBoxAddFieldBox.prototype._onAddFormFieldButtonClick = function(event) {
  var siblings = this.field.fields || [],
    beforeField = this.beforeField.value;

  this.dynamicFieldCounter++;
  var newField = scout.create('StringField', {
    parent: this.field,
    id: 'DynField ' + this.dynamicFieldCounter,
    label: this.labelField.value || 'DynField ' + this.dynamicFieldCounter
  });

  this.field.insertFieldBefore(newField, beforeField);
  this._updateAddFieldLabel();
  // Validate layout immediately to prevent flickering
  this.field.validateLayoutTree();
};
