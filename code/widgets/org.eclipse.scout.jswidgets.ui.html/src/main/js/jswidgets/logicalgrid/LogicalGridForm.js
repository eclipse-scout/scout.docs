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
jswidgets.LogicalGridForm = function() {
  jswidgets.LogicalGridForm.parent.call(this);
  this._fieldFocusHandler = this._onFieldFocus.bind(this);
  this._fieldRenderHandler = this._onFieldRender.bind(this);
};
scout.inherits(jswidgets.LogicalGridForm, scout.Form);

jswidgets.LogicalGridForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.LogicalGridForm');
};

jswidgets.LogicalGridForm.prototype._init = function(model) {
  jswidgets.LogicalGridForm.parent.prototype._init.call(this, model);

  var groupBox = this.widget('DetailBox');
  groupBox.on('propertyChange', this._onGroupBoxPropertyChange.bind(this));
  this._initFields(groupBox.fields);

  var targetField = this.widget('TargetField');
  targetField.lookupCall = new jswidgets.FormFieldLookupCall(groupBox);
  targetField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));
  this.widget('StringField1').on('propertyChange', this._onFieldPropertyChange.bind(this));
  this.widget('StringField2').on('propertyChange', this._onFieldPropertyChange.bind(this));
  this.widget('StringField3').on('propertyChange', this._onFieldPropertyChange.bind(this));
  this.widget('StringField4').on('propertyChange', this._onFieldPropertyChange.bind(this));

  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(groupBox.logicalGrid ? groupBox.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onLogicalGridChange.bind(this));

  this.widget('GridDataBox').setEnabled(!!targetField.value);

  this.widget('Actions.AddFieldBox').setField(groupBox);
  this.widget('Actions.AddFieldBox').beforeField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));
  this.widget('Actions.DeleteFieldBox').setField(groupBox);
  this.widget('Actions.DeleteFieldBox').targetField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));
};

jswidgets.LogicalGridForm.prototype._initFields = function(fields) {
  fields.forEach(function(field) {
    field.off('render', this._fieldRenderHandler);
    field.on('render', this._fieldRenderHandler);
  }, this);
};

jswidgets.LogicalGridForm.prototype._onFieldRender = function(event) {
  event.source.$field.off('focus', this._fieldFocusHandler);
  event.source.$field.on('focus', this._fieldFocusHandler);
};

jswidgets.LogicalGridForm.prototype._onGroupBoxPropertyChange = function(event) {
  if (event.propertyName === 'fields') {
    this._initFields(event.source.fields);
  }
};

jswidgets.LogicalGridForm.prototype._onTargetFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var oldField = event.oldValue;
    var newField = event.newValue;
    this.widget('GridDataBox').setField(newField);
    this.widget('GridDataBox').setEnabled(!!newField);
    this.widget('CalculatedGridDataBox').setField(newField);
    if (oldField) {
      oldField.removeCssClass('field-highlighted');
    }
    if (newField) {
      newField.addCssClass('field-highlighted');
    }
  }
};

jswidgets.LogicalGridForm.prototype._onFieldPropertyChange = function(event) {
  if (event.propertyName === 'gridData' && event.source === this.widget('TargetField')) {
    var gridDataBox = this.widget('CalculatedGridDataBox');
    gridDataBox.reloadGridData();
  }
};

jswidgets.LogicalGridForm.prototype._onFieldFocus = function(event) {
  var field = scout.widget(event.currentTarget);
  this.widget('TargetField').setValue(field);
  this.widget('Actions.AddFieldBox').setTargetField(field);
  this.widget('Actions.DeleteFieldBox').setTargetField(field);
};

jswidgets.LogicalGridForm.prototype._onLogicalGridChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setLogicalGrid(event.newValue);
  }
};
