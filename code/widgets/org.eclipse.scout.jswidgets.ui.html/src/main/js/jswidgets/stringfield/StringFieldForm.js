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
jswidgets.StringFieldForm = function() {
  jswidgets.StringFieldForm.parent.call(this);
};
scout.inherits(jswidgets.StringFieldForm, scout.Form);

jswidgets.StringFieldForm.prototype._init = function(model) {
  jswidgets.StringFieldForm.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.rootGroupBox);
  bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));
  bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('PropertiesBox'));

  var stringField = this.widget('StringField');
  var formatField = this.widget('FormatField');
  formatField.setValue(stringField.format);
  formatField.on('propertyChange', this._onFormatPropertyChange.bind(this));

  var blockFormatField = this.widget('BlockFormatField');
  blockFormatField.setValue(stringField.format);
  blockFormatField.on('propertyChange', this._onBlockFormatPropertyChange.bind(this));

  this.widget('ValueFieldPropertiesBox').setField(stringField);
  this.widget('FormFieldPropertiesBox').setField(stringField);
};

jswidgets.StringFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.StringFieldForm');
};

jswidgets.StringFieldForm.prototype._onFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setFormat(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onBlockFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var stringField = this.widget('StringField');
    if (event.newValue) {
      stringField.setFormatter(jswidgets.StringFieldForm.blockFormatter);
      stringField.setParser(jswidgets.StringFieldForm.blockParser);
    } else {
      stringField.setFormatter(null);
      stringField.setParser(null);
    }
  }
};

jswidgets.StringFieldForm.blockFormatter = function(value, defaultFormatter) {
  var displayText = defaultFormatter(value);
  if (!displayText) {
    return displayText;
  }
  return displayText.match(/.{4}/g).join('-');
};

jswidgets.StringFieldForm.blockParser = function(displayText, defaultParser) {
  if (displayText) {
    return displayText.replace(/-/g, '');
  }
  return defaultParser(displayText);
};
