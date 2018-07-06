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
jswidgets.RadioButtonGroupForm = function() {
  jswidgets.RadioButtonGroupForm.parent.call(this);
};
scout.inherits(jswidgets.RadioButtonGroupForm, scout.Form);

jswidgets.RadioButtonGroupForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.RadioButtonGroupForm');
};

jswidgets.RadioButtonGroupForm.prototype._init = function(model) {
  jswidgets.RadioButtonGroupForm.parent.prototype._init.call(this, model);

  var group = this.widget('RadioButtonGroup');
  group.on('propertyChange', this._onRadioButtonGroupPropertyChange.bind(this));

  var selectedButtonField = this.widget('SelectedButtonField');
  selectedButtonField.setLookupCall(new jswidgets.FormFieldLookupCall(group));
  selectedButtonField.setValue(group.selectedButton);
  selectedButtonField.on('propertyChange', this._onSelectedButtonPropertyChange.bind(this));

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(group.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onGridColumnCountPropertyChange.bind(this));

  this.widget('ValueField').setEnabled(true);
  this.widget('ValueFieldPropertiesBox').parseValue = function(newValue) {
    if (!isNaN(newValue)) {
      newValue = scout.numbers.ensure(newValue);
    }
    return newValue;
  };
  this.widget('ValueFieldPropertiesBox').setField(group);

  this.widget('FormFieldPropertiesBox').setField(group);
  this.widget('GridDataBox').setField(group);
  this.widget('LayoutConfigBox').setField(group);
  this.widget('WidgetActionsBox').setField(group);
  this.widget('EventsTab').setField(group);

  // Button tab
  var targetField = this.widget('Button.TargetField');
  targetField.setLookupCall(new jswidgets.FormFieldLookupCall(group));
  targetField.setValue(group.radioButtons[0]);
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

  var keyStrokeField = this.widget('Button.KeyStrokeField');
  keyStrokeField.setValue(targetField.value.keyStroke);
  keyStrokeField.on('propertyChange', this._onKeyStrokePropertyChange.bind(this));

  var selectedField = this.widget('Button.SelectedField');
  selectedField.setValue(targetField.value.selected);
  selectedField.on('propertyChange', this._onSelectedPropertyChange.bind(this));

  this.widget('Button.PropertiesBox').setEnabled(!!targetField.value);
  this.widget('Button.FormFieldPropertiesBox').setField(targetField.value);
  this.widget('Button.FormFieldPropertiesBox').setEnabled(!!targetField.value);
  this.widget('Button.GridDataBox').setField(targetField.value);
  this.widget('Button.GridDataBox').setEnabled(!!targetField.value);
};

jswidgets.RadioButtonGroupForm.prototype._onRadioButtonGroupPropertyChange = function(event) {
  if (event.propertyName === 'selectedButton') {
    this.widget('SelectedButtonField').setValue(event.newValue);
  } else if (event.propertyName === 'gridColumnCount') {
    this.widget('GridColumnCountField').setValue(event.newValue);
  }
};

jswidgets.RadioButtonGroupForm.prototype._onSelectedButtonPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('RadioButtonGroup').selectButton(event.newValue);
  }
};

jswidgets.RadioButtonGroupForm.prototype._onGridColumnCountPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var newVal = event.newValue;
    if (newVal > 0 || newVal === scout.RadioButtonGroup.DEFAULT_GRID_COLUMN_COUNT) {
      this.widget('RadioButtonGroup').setGridColumnCount(newVal);
    }
  }
};

jswidgets.RadioButtonGroupForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var button = event.newValue;
    if (button) {
      this.widget('Button.KeyStrokeField').setValue(button.keyStroke);
      this.widget('Button.SelectedField').setValue(button.selected);
    }
    this.widget('Button.PropertiesBox').setEnabled(!!button);
    this.widget('Button.FormFieldPropertiesBox').setField(button);
    this.widget('Button.FormFieldPropertiesBox').setEnabled(!!button);
    this.widget('Button.GridDataBox').setField(button);
    this.widget('Button.GridDataBox').setEnabled(!!button);
  }
};

jswidgets.RadioButtonGroupForm.prototype._onKeyStrokePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var button = this.widget('Button.TargetField').value;
    if (button) {
      button.setKeyStroke(event.newValue);
    }
  }
};

jswidgets.RadioButtonGroupForm.prototype._onSelectedPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var button = this.widget('Button.TargetField').value;
    if (button) {
      button.setSelected(event.newValue);
    }
  }
};
