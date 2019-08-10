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
jswidgets.FormFieldPropertiesBox = function() {
  jswidgets.FormFieldPropertiesBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.FormFieldPropertiesBox, scout.GroupBox);

jswidgets.FormFieldPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FormFieldPropertiesBox');
};

jswidgets.FormFieldPropertiesBox.prototype._init = function(model) {
  jswidgets.FormFieldPropertiesBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
};

jswidgets.FormFieldPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.FormFieldPropertiesBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }
  var enabledField = this.widget('EnabledField');
  enabledField.setValue(this.field.enabled);
  enabledField.on('propertyChange', this._onPropertyChange.bind(this));

  var visibleField = this.widget('VisibleField');
  visibleField.setValue(this.field.visible);
  visibleField.on('propertyChange', this._onPropertyChange.bind(this));

  var labelVisibleField = this.widget('LabelVisibleField');
  labelVisibleField.setValue(this.field.labelVisible);
  labelVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

  var statusVisibleField = this.widget('StatusVisibleField');
  statusVisibleField.setValue(this.field.statusVisible);
  statusVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

  var mandatoryField = this.widget('MandatoryField');
  mandatoryField.setValue(this.field.mandatory);
  mandatoryField.on('propertyChange', this._onPropertyChange.bind(this));

  var loadingField = this.widget('LoadingField');
  loadingField.setValue(this.field.loading);
  loadingField.on('propertyChange', this._onPropertyChange.bind(this));

  var fieldStyleField = this.widget('FieldStyleField');
  fieldStyleField.setValue(this.field.fieldStyle);
  fieldStyleField.on('propertyChange', this._onPropertyChange.bind(this));

  var disabledStyleField = this.widget('DisabledStyleField');
  disabledStyleField.setValue(this.field.disabledStyle);
  disabledStyleField.on('propertyChange', this._onPropertyChange.bind(this));

  var labelField = this.widget('LabelField');
  labelField.setValue(this.field.label);
  labelField.on('propertyChange', this._onPropertyChange.bind(this));

  var labelPositionField = this.widget('LabelPositionField');
  labelPositionField.setValue(this.field.labelPosition);
  labelPositionField.on('propertyChange', this._onPropertyChange.bind(this));

  var labelWidthInPixelField = this.widget('LabelWidthInPixelField');
  labelWidthInPixelField.setValue(this.field.labelWidthInPixel);
  labelWidthInPixelField.on('propertyChange', this._onPropertyChange.bind(this));

  var errorStatusField = this.widget('ErrorStatusField');
  errorStatusField.setValue(this.field.errorStatus ? this.field.errorStatus.severity : null);
  errorStatusField.on('propertyChange', this._onPropertyChange.bind(this));

  var tooltipTextField = this.widget('TooltipTextField');
  tooltipTextField.setValue(this.field.tooltipText);
  tooltipTextField.on('propertyChange', this._onPropertyChange.bind(this));

  var statusPositionField = this.widget('StatusPositionField');
  statusPositionField.setValue(this.field.statusPosition);
  statusPositionField.on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.FormFieldPropertiesBox.prototype._createErrorStatus = function(severity) {
  if (!severity) {
    return null;
  }
  return new scout.Status({
    severity: severity,
    message: this.session.text('FormFieldStatusMessage', scout.objects.keyByValue(scout.Status.Severity, severity))
  });
};

jswidgets.FormFieldPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'EnabledField') {
    this.field.setEnabled(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'VisibleField') {
    this.field.setVisible(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'LabelVisibleField') {
    this.field.setLabelVisible(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'StatusVisibleField') {
    this.field.setStatusVisible(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'MandatoryField') {
    this.field.setMandatory(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'LoadingField') {
    this.field.setLoading(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'FieldStyleField') {
    this.field.setFieldStyle(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'DisabledStyleField') {
    this.field.setDisabledStyle(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'LabelField') {
    this.field.setLabel(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'LabelPositionField') {
    this.field.setLabelPosition(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'LabelWidthInPixelField') {
    if (event.source.lookupRow) {
      this.field.setLabelWidthInPixel(event.source.lookupRow.key);
    } else {
      this.field.setLabelWidthInPixel(scout.numbers.ensure(event.newValue));
    }
  } else if (event.propertyName === 'value' && event.source.id === 'ErrorStatusField') {
    this.field.setErrorStatus(this._createErrorStatus(event.newValue));
  } else if (event.propertyName === 'value' && event.source.id === 'TooltipTextField') {
    this.field.setTooltipText(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'StatusPositionField') {
    this.field.setStatusPosition(event.newValue);
  }
};
