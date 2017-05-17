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

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.rootGroupBox);
  bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));
  bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('PropertiesBox'));

  var dateField = this.widget('DateField');
  var formatField = this.widget('FormatField');
  formatField.setValue(dateField.dateFormatPattern);
  formatField.on('propertyChange', this._onFormatPropertyChange.bind(this));
  this.widget('ValueFieldPropertiesBox').setField(dateField);
  this.widget('FormFieldPropertiesBox').setField(dateField);
};

jswidgets.DateFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.DateFieldForm');
};

jswidgets.DateFieldForm.prototype._onFormatPropertyChange = function(event) {
  if (event.name === 'value') {
    this.widget('DateField').setDateFormatPattern(event.newValue);
  }
};
