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
jswidgets.ListBoxForm = function() {
  jswidgets.ListBoxForm.parent.call(this);
};
scout.inherits(jswidgets.ListBoxForm, scout.Form);

jswidgets.ListBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ListBoxForm');
};

jswidgets.ListBoxForm.prototype._init = function(model) {
  jswidgets.ListBoxForm.parent.prototype._init.call(this, model);

  this.listBox = this.widget('ListBox');

  this.lookupCallField = this.widget('LookupCallField');
  this.lookupCallField.setValue(this.listBox.lookupCall);
  this.lookupCallField.on('propertyChange', this._onLookupCallFielChange.bind(this));
  this.listBox.on('propertyChange', this._onListBoxChange.bind(this));

  this.widget('ValueFieldPropertiesBox').setField(this.listBox);
  this.widget('FormFieldPropertiesBox').setField(this.listBox);
  this.widget('GridDataBox').setField(this.listBox);
  this.widget('EventsTab').setField(this.listBox);
};

jswidgets.ListBoxForm.prototype._onListBoxChange = function(event) {
  if (event.propertyName === 'lookupCall') {
    this.lookupCallField.setValue(event.newValue);
  }
};

jswidgets.ListBoxForm.prototype._onLookupCallFielChange = function(event) {
  if (event.propertyName === 'value') {
    this.listBox.setLookupCall(event.newValue);
  }
};
