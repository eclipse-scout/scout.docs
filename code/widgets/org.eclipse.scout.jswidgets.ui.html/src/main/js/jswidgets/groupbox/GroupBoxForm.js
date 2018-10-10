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
jswidgets.GroupBoxForm = function() {
  jswidgets.GroupBoxForm.parent.call(this);
  this._fieldFocusHandler = this._onFieldFocus.bind(this);
  this._fieldRenderHandler = this._onFieldRender.bind(this);
};
scout.inherits(jswidgets.GroupBoxForm, scout.Form);

jswidgets.GroupBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxForm');
};

jswidgets.GroupBoxForm.prototype._init = function(model) {
  jswidgets.GroupBoxForm.parent.prototype._init.call(this, model);

  var menu1 = this.widget('Menu1');
  menu1.on('action', this._onMenuAction.bind(this));

  var configurationBox = this.widget('ConfigurationBox');
  configurationBox.on('propertyChange', this._onConfigurationBoxPropertyChange.bind(this));

  var groupBox = this.widget('DetailBox');
  groupBox.on('propertyChange', this._onGroupBoxPropertyChange.bind(this));
  this._initFields(groupBox.fields);

  // GroupBox Properties tab
  this.widget('GroupBoxPropertiesBox').setField(groupBox);
  this.widget('Actions.AddFieldBox').setField(groupBox);
  this.widget('Actions.DeleteFieldBox').setField(groupBox);
  this.widget('FormFieldPropertiesBox').setField(groupBox);

  var bodyLayoutConfigBox = this.widget('BodyLayoutConfigBox');
  bodyLayoutConfigBox.getLayoutConfig = function() {
    return this.field.bodyLayoutConfig;
  };
  bodyLayoutConfigBox.setLayoutConfig = function(layoutConfig) {
    this.field.setBodyLayoutConfig(layoutConfig);
  };
  bodyLayoutConfigBox.setField(groupBox);
  this.widget('GridDataBox').setField(groupBox);
  this.widget('WidgetActionsBox').setField(groupBox);
  this.widget('EventsTab').setField(groupBox);

  // Field tab
  var targetField = this.widget('Field.TargetField');
  targetField.setLookupCall(new jswidgets.FormFieldLookupCall(groupBox));
  targetField.setValue(groupBox.fields[0]);
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

  this._onTargetPropertyChange({
    propertyName: 'value',
    newValue: targetField.value
  });
};

jswidgets.GroupBoxForm.prototype._initFields = function(fields) {
  fields.forEach(function(field) {
    field.off('render', this._fieldRenderHandler);
    field.on('render', this._fieldRenderHandler);
  }, this);
};

jswidgets.GroupBoxForm.prototype._onMenuAction = function(event) {
  scout.MessageBoxes.createOk(this)
    .withBody("Menu with label '" + event.source.text + "' has been activated.")
    .buildAndOpen();
};

jswidgets.GroupBoxForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var oldField = event.oldValue;
    var newField = event.newValue;

    var fieldPropertiesBox = this.widget('Field.FormFieldPropertiesBox');
    fieldPropertiesBox.setField(newField);
    fieldPropertiesBox.setEnabled(!!newField);

    var fieldGridDataBox = this.widget('Field.GridDataBox');
    fieldGridDataBox.setField(newField);
    fieldGridDataBox.setEnabled(!!newField);
    this._updateHighlightedField(newField, oldField);
  }
};

jswidgets.GroupBoxForm.prototype._updateHighlightedField = function(newTargetField, oldTargetField) {
  var configurationBox = this.widget('ConfigurationBox');
  if (oldTargetField) {
    oldTargetField.removeCssClass('field-highlighted');
  }
  if (!newTargetField) {
    return;
  }

  // Only highlight field if field properties or actions tab is selected
  if (scout.isOneOf(configurationBox.selectedTab.id, 'FieldPropertiesTab', 'ActionsTab')) {
    newTargetField.addCssClass('field-highlighted');
  } else {
    newTargetField.removeCssClass('field-highlighted');
  }
};

jswidgets.GroupBoxForm.prototype._onFieldRender = function(event) {
  event.source.$field.off('focus', this._fieldFocusHandler);
  event.source.$field.on('focus', this._fieldFocusHandler);
};

jswidgets.GroupBoxForm.prototype._onFieldFocus = function(event) {
  var field = scout.widget(event.currentTarget);
  this.widget('Field.TargetField').setValue(field);
  this.widget('Actions.AddFieldBox').setTargetField(field);
  this.widget('Actions.DeleteFieldBox').setTargetField(field);
};

jswidgets.GroupBoxForm.prototype._onConfigurationBoxPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    var targetField = this.widget('Field.TargetField').value;
    if (!targetField) {
      return;
    }
    this._updateHighlightedField(targetField);
  }
};

jswidgets.GroupBoxForm.prototype._onGroupBoxPropertyChange = function(event) {
  if (event.propertyName === 'fields') {
    this._initFields(event.source.fields);
  }
};
