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
jswidgets.FileChooserFieldForm = function() {
  jswidgets.FileChooserFieldForm.parent.call(this);
};
scout.inherits(jswidgets.FileChooserFieldForm, scout.Form);

jswidgets.FileChooserFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FileChooserFieldForm');
};

jswidgets.FileChooserFieldForm.prototype._init = function(model) {
  jswidgets.FileChooserFieldForm.parent.prototype._init.call(this, model);

  var fileChooserField = this.widget('FileChooserField');

  var acceptTypesField = this.widget('AcceptTypesField');
  acceptTypesField.setValue(fileChooserField.acceptTypes);
  acceptTypesField.on('propertyChange', this._onAcceptTypesPropertyChange.bind(this));

  var maximumUploadSizeField = this.widget('MaximumUploadSizeField');
  maximumUploadSizeField.setValue(fileChooserField.maximumUploadSize);
  maximumUploadSizeField.on('propertyChange', this._onMaximumUploadSizePropertyChange.bind(this));

  this.widget('ValueFieldPropertiesBox').setField(fileChooserField);
  this.widget('FormFieldPropertiesBox').setField(fileChooserField);
  this.widget('GridDataBox').setField(fileChooserField);
  this.widget('WidgetActionsBox').setField(fileChooserField);
  this.widget('EventsTab').setField(fileChooserField);
};

jswidgets.FileChooserFieldForm.prototype._onAcceptTypesPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('FileChooserField').setAcceptTypes(event.newValue);
  }
};

jswidgets.FileChooserFieldForm.prototype._onMaximumUploadSizePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('FileChooserField').setMaximumUploadSize(event.newValue);
  }
};
