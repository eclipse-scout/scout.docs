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
jswidgets.NumberFieldForm = function() {
  jswidgets.NumberFieldForm.parent.call(this);
};
scout.inherits(jswidgets.NumberFieldForm, scout.Form);

jswidgets.NumberFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.NumberFieldForm');
};

jswidgets.NumberFieldForm.prototype._init = function(model) {
  jswidgets.NumberFieldForm.parent.prototype._init.call(this, model);

  var numberField = this.widget('NumberField');
  var formatField = this.widget('FormatField');
  formatField.setValue(numberField.decimalFormat.pattern);
  formatField.on('propertyChange', this._onFormatPropertyChange.bind(this));

  var multiplierField = this.widget('MultiplierField');
  multiplierField.setValue(numberField.decimalFormat.multiplier);
  multiplierField.on('propertyChange', this._onFormatPropertyChange.bind(this));

  var minValueField = this.widget('MinValueField');
  minValueField.on('propertyChange', this._onMinValueFieldPropertyChange.bind(this));
  minValueField.setValue(numberField.minValue);

  var maxValueField = this.widget('MaxValueField');
  maxValueField.on('propertyChange', this._onMaxValueFieldPropertyChange.bind(this));
  maxValueField.setValue(numberField.maxValue);

  this.widget('ValueField').setEnabled(true);
  this.widget('ValueFieldPropertiesBox').setField(numberField);
  this.widget('FormFieldPropertiesBox').setField(numberField);
  this.widget('GridDataBox').setField(numberField);
  this.widget('WidgetActionsBox').setField(numberField);
  this.widget('EventsTab').setField(numberField);
};

jswidgets.NumberFieldForm.prototype._onFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var multiplier = this.widget('MultiplierField');
    var format = this.widget('FormatField');
    this.widget('NumberField').setDecimalFormat({
      pattern: format.value,
      multiplier: multiplier.value
    });
  }
};

jswidgets.NumberFieldForm.prototype._onMinValueFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('NumberField').setMinValue(event.newValue);
  }
};

jswidgets.NumberFieldForm.prototype._onMaxValueFieldPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('NumberField').setMaxValue(event.newValue);
  }
};
