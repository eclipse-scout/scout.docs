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
jswidgets.FileChooserButtonForm = function() {
  jswidgets.FileChooserButtonForm.parent.call(this);
};
scout.inherits(jswidgets.FileChooserButtonForm, scout.Form);

jswidgets.FileChooserButtonForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FileChooserButtonForm');
};

jswidgets.FileChooserButtonForm.prototype._init = function(model) {
  jswidgets.FileChooserButtonForm.parent.prototype._init.call(this, model);

  var fileChooserButton = this.widget('FileChooserButton');

  var acceptTypesField = this.widget('AcceptTypesField');
  acceptTypesField.setValue(fileChooserButton.acceptTypes);
  acceptTypesField.on('propertyChange', this._onAcceptTypesPropertyChange.bind(this));

  var maximumUploadSizeField = this.widget('MaximumUploadSizeField');
  maximumUploadSizeField.setValue(fileChooserButton.maximumUploadSize);
  maximumUploadSizeField.on('propertyChange', this._onMaximumUploadSizePropertyChange.bind(this));

  var iconIdField = this.widget('IconIdField');
  iconIdField.setValue(fileChooserButton.iconId);
  iconIdField.on('propertyChange', this._onIconIdPropertyChange.bind(this));

  this.widget('ValueFieldPropertiesBox').setField(fileChooserButton);
  this.widget('FormFieldPropertiesBox').setField(fileChooserButton);
  this.widget('GridDataBox').setField(fileChooserButton);
  this.widget('EventsTab').setField(fileChooserButton);
};

jswidgets.FileChooserButtonForm.prototype._onAcceptTypesPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('FileChooserButton').setAcceptTypes(event.newValue);
  }
};

jswidgets.FileChooserButtonForm.prototype._onMaximumUploadSizePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('FileChooserButton').setMaximumUploadSize(event.newValue);
  }
};

jswidgets.FileChooserButtonForm.prototype._onIconIdPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('FileChooserButton').setIconId(event.newValue);
  }
};
