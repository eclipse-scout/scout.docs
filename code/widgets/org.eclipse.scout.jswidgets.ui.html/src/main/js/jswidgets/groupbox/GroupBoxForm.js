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
};
scout.inherits(jswidgets.GroupBoxForm, scout.Form);

jswidgets.GroupBoxForm.prototype._init = function(model) {
  jswidgets.GroupBoxForm.parent.prototype._init.call(this, model);

  var groupBox = this.widget('DetailBox');

  var toggleVisibilityField = this.widget('ToggleVisibilityField');
  toggleVisibilityField.on('propertyChange', this._onToggleVisibilityChange.bind(this));

  var toggleVisibilityButton = this.widget('ToggleVisibilityButton');
  toggleVisibilityButton.setEnabled(!!toggleVisibilityField.value);
  toggleVisibilityButton.on('click', this._onToggleVisibilityButtonClick.bind(this));

  this.widget('GroupBoxPropertiesBox').setField(groupBox);
  this.widget('FormFieldPropertiesBox').setField(groupBox);
  this.widget('BodyLayoutConfigBox').getLayoutConfig = function() {
    return this.field.bodyLayoutConfig;
  };
  this.widget('BodyLayoutConfigBox').updateLayoutConfig = function(layoutConfig) {
    this.field.setBodyLayoutConfig(layoutConfig);
  };
  this.widget('BodyLayoutConfigBox').setField(groupBox);
  this.widget('GridDataBox').setField(groupBox);
};

jswidgets.GroupBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxForm');
};

jswidgets.GroupBoxForm.prototype._onToggleVisibilityChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('ToggleVisibilityButton').setEnabled(!!event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onToggleVisibilityButtonClick = function(event) {
  var field = this.widget(this.widget('ToggleVisibilityField').value);
  field.setVisible(!field.visible);
};
