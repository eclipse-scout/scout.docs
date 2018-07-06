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

jswidgets.DateFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.DateFieldForm');
};

jswidgets.DateFieldForm.prototype._init = function(model) {
  jswidgets.DateFieldForm.parent.prototype._init.call(this, model);

  var dateField = this.widget('DateField');
  var hasTimeField = this.widget('HasTimeField');
  hasTimeField.setValue(dateField.hasTime);
  hasTimeField.on('propertyChange', this._onHasTimePropertyChange.bind(this));

  var hasDateField = this.widget('HasDateField');
  hasDateField.setValue(dateField.hasDate);
  hasDateField.on('propertyChange', this._onHasDatePropertyChange.bind(this));

  var dateFormatField = this.widget('DateFormatField');
  dateFormatField.setValue(dateField.dateFormatPattern);
  dateFormatField.on('propertyChange', this._onDateFormatPropertyChange.bind(this));

  var timeFormatField = this.widget('TimeFormatField');
  timeFormatField.setValue(dateField.timeFormatPattern);
  timeFormatField.on('propertyChange', this._onTimeFormatPropertyChange.bind(this));

  var timePickerResolutionField = this.widget('TimePickerResolutionField');
  timePickerResolutionField.setValue(dateField.timePickerResolution);
  timePickerResolutionField.on('propertyChange', this._onTimePickerResolutionPropertyChange.bind(this));

  var autoDateField = this.widget('AutoDateField');
  autoDateField.setValue(dateField.autoDate);
  autoDateField.on('propertyChange', this._onAutoDatePropertyChange.bind(this));

  var dontAllowCurrentDateField = this.widget('DontAllowCurrentDateField');
  this._dontAllowCurrentDateValidator = function(value) {
    if (scout.dates.isSameDay(value, new Date())) {
      throw 'You are not allowed to select the current date';
    }
    return value;
  };
  dontAllowCurrentDateField.on('propertyChange', this._onDontAllowCurrentDateField.bind(this));

  this.widget('ValueFieldPropertiesBox').setField(dateField);
  this.widget('FormFieldPropertiesBox').setField(dateField);
  this.widget('GridDataBox').setField(dateField);
  this.widget('WidgetActionsBox').setField(dateField);
  this.widget('EventsTab').setField(dateField);
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

jswidgets.DateFieldForm.prototype._onDateFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setDateFormatPattern(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onTimeFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setTimeFormatPattern(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onTimePickerResolutionPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setTimePickerResolution(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onAutoDatePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DateField').setAutoDate(event.newValue);
  }
};

jswidgets.DateFieldForm.prototype._onDontAllowCurrentDateField = function(event) {
  if (event.propertyName === 'value') {
    var dateField = this.widget('DateField');
    if (event.newValue) {
      dateField.addValidator(this._dontAllowCurrentDateValidator);
    } else {
      dateField.removeValidator(this._dontAllowCurrentDateValidator);
    }
  }
};
