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
};
scout.inherits(jswidgets.LogicalGridForm, scout.Form);

jswidgets.LogicalGridForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.LogicalGridForm');
};

jswidgets.LogicalGridForm.prototype._init = function(model) {
  jswidgets.LogicalGridForm.parent.prototype._init.call(this, model);

  var targetField = this.widget('TargetField');
  targetField.on('propertyChange', this._onTargetFieldPropertyChange.bind(this));
  this.widget('StringField1').on('propertyChange', this._onFieldPropertyChange.bind(this));
  this.widget('StringField2').on('propertyChange', this._onFieldPropertyChange.bind(this));
  this.widget('StringField3').on('propertyChange', this._onFieldPropertyChange.bind(this));
  this.widget('StringField4').on('propertyChange', this._onFieldPropertyChange.bind(this));

  var groupBox = this.widget('DetailBox');
  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(groupBox.logicalGrid ? groupBox.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onLogicalGridChange.bind(this));

  this.widget('GridDataBox').setEnabled(!!targetField.value);
};

jswidgets.LogicalGridForm.prototype._render = function() {
  jswidgets.LogicalGridForm.parent.prototype._render.call(this);

  this.widget('StringField1').$field.on('focus', this._onFieldFocus.bind(this));
  this.widget('StringField2').$field.on('focus', this._onFieldFocus.bind(this));
  this.widget('StringField3').$field.on('focus', this._onFieldFocus.bind(this));
  this.widget('StringField4').$field.on('focus', this._onFieldFocus.bind(this));
};

jswidgets.LogicalGridForm.prototype._onTargetFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var oldField = this.widget(event.oldValue);
    var newField = this.widget(event.newValue);
    this.widget('GridDataBox').setField(newField);
    this.widget('GridDataBox').setEnabled(!!newField);
    this.widget('CalculatedGridDataBox').setField(newField);
    if (oldField) {
      oldField.removeCssClass('logical-grid-form-highlight-field');
    }
    if (newField) {
      newField.addCssClass('logical-grid-form-highlight-field');
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
  this.widget('TargetField').setValue(field.id);
};

jswidgets.LogicalGridForm.prototype._onLogicalGridChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setLogicalGrid(event.newValue);
  }
};
