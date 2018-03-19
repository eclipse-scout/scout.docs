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
jswidgets.LabelFieldForm = function() {
  jswidgets.LabelFieldForm.parent.call(this);
};
scout.inherits(jswidgets.LabelFieldForm, scout.Form);

jswidgets.LabelFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.LabelFieldForm');
};

jswidgets.LabelFieldForm.prototype._init = function(model) {
  jswidgets.LabelFieldForm.parent.prototype._init.call(this, model);

  var labelField = this.widget('LabelField');

  var wrapTextField = this.widget('WrapTextField');
  wrapTextField.setValue(labelField.wrapText);
  wrapTextField.on('propertyChange', this._onWrapTextPropertyChange.bind(this));

  var htmlEnabledField = this.widget('HtmlEnabledField');
  htmlEnabledField.setValue(labelField.htmlEnabled);
  htmlEnabledField.on('propertyChange', this._onHtmlEnabledPropertyChange.bind(this));

  this.widget('ValueField').setEnabled(true);
  this.widget('ValueFieldPropertiesBox').setField(labelField);
  this.widget('FormFieldPropertiesBox').setField(labelField);
  this.widget('GridDataBox').setField(labelField);
  this.widget('EventsTab').setField(labelField);
};

jswidgets.LabelFieldForm.prototype._onWrapTextPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('LabelField').setWrapText(event.newValue);
  }
};

jswidgets.LabelFieldForm.prototype._onHtmlEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('LabelField').setHtmlEnabled(event.newValue);
  }
};
