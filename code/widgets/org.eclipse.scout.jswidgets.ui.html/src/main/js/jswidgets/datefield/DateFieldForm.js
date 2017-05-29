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
jswidgets.DateFieldForm = function() {
  jswidgets.DateFieldForm.parent.call(this);
};
scout.inherits(jswidgets.DateFieldForm, scout.Form);

jswidgets.DateFieldForm.prototype._init = function(model) {
  jswidgets.DateFieldForm.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.VerticalSmartGroupBoxBodyGrid();
  bodyGrid.validate(this.rootGroupBox);
  bodyGrid = new scout.VerticalSmartGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));
  bodyGrid = new scout.VerticalSmartGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('PropertiesBox'));

  var dateField = this.widget('DateField');
  var hasTimeField = this.widget('HasTimeField');
  hasTimeField.setValue(dateField.hasTime);
  hasTimeField.on('propertyChange', this._onHasTimePropertyChange.bind(this));

  var hasDateField = this.widget('HasDateField');
  hasDateField.setValue(dateField.hasDate);
  hasDateField.on('propertyChange', this._onHasDatePropertyChange.bind(this));

  var formatField = this.widget('FormatField');
  formatField.setValue(dateField.dateFormatPattern);
  formatField.on('propertyChange', this._onFormatPropertyChange.bind(this));

  var autoDateField = this.widget('AutoDateField');
  autoDateField.setValue(dateField.autoDate);
  autoDateField.on('propertyChange', this._onAutoDatePropertyChange.bind(this));

  this.widget('ValueFieldPropertiesBox').setField(dateField);
  this.widget('FormFieldPropertiesBox').setField(dateField);
};

jswidgets.DateFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.DateFieldForm');
};

jswidgets.DateFieldForm.prototype._onHasDatePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setHasDate(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onHasTimePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setHasTime(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setDateFormatPattern(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onAutoDatePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setAutoDate(event.newValue);
  }
};
