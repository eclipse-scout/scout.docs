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
jswidgets.ProposalFieldPropertiesBox = function() {
  jswidgets.ProposalFieldPropertiesBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.ProposalFieldPropertiesBox, jswidgets.SmartFieldPropertiesBox);

jswidgets.ProposalFieldPropertiesBox.prototype._jsonModel = function() {
  return scout.models.extend('jswidgets.ProposalFieldPropertiesBox', jswidgets.ProposalFieldPropertiesBox.parent.prototype._jsonModel.call(this));
};

jswidgets.ProposalFieldPropertiesBox.prototype._init = function(model) {
  jswidgets.ProposalFieldPropertiesBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
  this.widget('DisplayStyleField').setVisible(false);
};

jswidgets.ProposalFieldPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.ProposalFieldPropertiesBox.prototype._setField = function(field) {
  jswidgets.ProposalFieldPropertiesBox.parent.prototype._setField.call(this, field);
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  var lookupCallField = this.widget('LookupCallField');
  lookupCallField.setValue(this.field.lookupCall);
  lookupCallField.on('propertyChange', this._onLookupCallPropertyChange.bind(this));
  this.field.on('propertyChange', this._onSmartFieldChange.bind(this));

  var maxLengthField = this.widget('MaxLengthField');
  maxLengthField.setValue(this.field.maxLength);
  maxLengthField.on('propertyChange', this._onMaxLengthPropertyChange.bind(this));

  var trimTextField = this.widget('TrimTextField');
  trimTextField.setValue(this.field.trimText);
  trimTextField.on('propertyChange', this._onTrimTextPropertyChange.bind(this));
};

jswidgets.ProposalFieldPropertiesBox.prototype._onSmartFieldChange = function(event) {
  if (event.propertyName === 'lookupCall') {
    this.widget('LookupCallField').setValue(event.newValue);
  }
};

jswidgets.ProposalFieldPropertiesBox.prototype._onLookupCallPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setLookupCall(event.newValue);
  }
};

jswidgets.ProposalFieldPropertiesBox.prototype._onMaxLengthPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setMaxLength(event.newValue);
  }
};

jswidgets.ProposalFieldPropertiesBox.prototype._onTrimTextPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setTrimText(event.newValue);
  }
};
