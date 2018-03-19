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
jswidgets.ValueFieldPropertiesBox = function() {
  jswidgets.ValueFieldPropertiesBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.ValueFieldPropertiesBox, scout.GroupBox);

jswidgets.ValueFieldPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ValueFieldPropertiesBox');
};

jswidgets.ValueFieldPropertiesBox.prototype._init = function(model) {
  jswidgets.ValueFieldPropertiesBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
  this.widget('ValueField').setTrimText(false);
  this.widget('DisplayTextField').setTrimText(false);
};

jswidgets.ValueFieldPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.ValueFieldPropertiesBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }
  this.field.on('propertyChange', this._onFieldPropertyChange.bind(this));

  var valueField = this.widget('ValueField');
  valueField.setValue(this.field.value);
  if (valueField.enabled) {
    valueField.on('propertyChange', this._onPropertyChange.bind(this));
  }

  var displayTextField = this.widget('DisplayTextField');
  displayTextField.setValue(this.field.displayText);
  if (displayTextField.enabled) {
    displayTextField.on('propertyChange', this._onPropertyChange.bind(this));
  }

  var clearableField = this.widget('ClearableField');
  clearableField.setValue(this.field.clearable);
  clearableField.on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.ValueFieldPropertiesBox.prototype._onFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('ValueField').setValue(event.newValue);
  } else if (event.propertyName === 'displayText') {
    this.widget('DisplayTextField').setValue(event.newValue);
  }
};

jswidgets.ValueFieldPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'ValueField') {
    this.field.setValue(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'DisplayTextField') {
    this.field.setDisplayText(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'ClearableField') {
    this.field.setClearable(event.newValue);
  }
};
