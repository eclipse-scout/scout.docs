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
jswidgets.ModeSelectorForm = function() {
  jswidgets.ModeSelectorForm.parent.call(this);
};
scout.inherits(jswidgets.ModeSelectorForm, scout.Form);

jswidgets.ModeSelectorForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ModeSelectorForm');
};

jswidgets.ModeSelectorForm.prototype._init = function(model) {
  jswidgets.ModeSelectorForm.parent.prototype._init.call(this, model);

  var modeSelectorField = this.widget('ModeSelectorField');
  var modeSelector = this.widget('ModeSelector');

  var mode1 = this.widget('Mode1');
  mode1.on('propertyChange', this._onModeChange.bind(this, mode1));

  var mode2 = this.widget('Mode2');
  mode2.on('propertyChange', this._onModeChange.bind(this, mode2));

  var mode3 = this.widget('Mode3');
  mode3.on('propertyChange', this._onModeChange.bind(this, mode3));

  var targetField = this.widget('TargetField');
  targetField.setLookupCall(new jswidgets.ModeLookupCall(modeSelector));
  targetField.setValue(modeSelector.modes[0]);
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

  var selectedField = this.widget('SelectedField');
  selectedField.setValue(targetField.value.selected);
  selectedField.on('propertyChange', this._onSelectedPropertyChange.bind(this));

  var enabledField = this.widget('EnabledField');
  enabledField.setValue(targetField.value.enabled);
  enabledField.on('propertyChange', this._onEnabledPropertyChange.bind(this));

  var visibleField = this.widget('VisibleField');
  visibleField.setValue(targetField.value.visible);
  visibleField.on('propertyChange', this._onVisiblePropertyChange.bind(this));

  var textField = this.widget('TextField');
  textField.setValue(targetField.value.text);
  textField.on('propertyChange', this._onTextPropertyChange.bind(this));

  var iconIdField = this.widget('IconIdField');
  iconIdField.setValue(targetField.value.iconId);
  iconIdField.on('propertyChange', this._onIconIdPropertyChange.bind(this));

  this.widget('FormFieldPropertiesBox').setField(modeSelectorField);
  this.widget('GridDataBox').setField(modeSelectorField);
  this.widget('WidgetActionsBox').setField(modeSelectorField);
  this.widget('EventsTab').setField(modeSelectorField);
};


jswidgets.ModeSelectorForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var mode = event.newValue;
    this.widget('SelectedField').setEnabled(!!mode);
    this.widget('EnabledField').setEnabled(!!mode);
    this.widget('VisibleField').setEnabled(!!mode);
    this.widget('TextField').setEnabled(!!mode);
    this.widget('IconIdField').setEnabled(!!mode);
    if (mode) {
      this.widget('SelectedField').setValue(mode.selected);
      this.widget('EnabledField').setValue(mode.enabled);
      this.widget('VisibleField').setValue(mode.visible);
      this.widget('TextField').setValue(mode.text);
      this.widget('IconIdField').setValue(mode.iconId);
    }
  }
};

jswidgets.ModeSelectorForm.prototype._onTextPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var mode = this.widget('TargetField').value;
    if (mode) {
      mode.setText(event.newValue);
    }
  }
};

jswidgets.ModeSelectorForm.prototype._onIconIdPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var mode = this.widget('TargetField').value;
    if (mode) {
      mode.setIconId(event.newValue);
    }
  }
};

jswidgets.ModeSelectorForm.prototype._onSelectedPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var mode = this.widget('TargetField').value;
    if (mode) {
      mode.setSelected(event.newValue);
    }
  }
};

jswidgets.ModeSelectorForm.prototype._onEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var mode = this.widget('TargetField').value;
    if (mode) {
      mode.setEnabled(event.newValue);
    }
  }
};

jswidgets.ModeSelectorForm.prototype._onVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var mode = this.widget('TargetField').value;
    if (mode) {
      mode.setVisible(event.newValue);
    }
  }
};

jswidgets.ModeSelectorForm.prototype._onModeChange = function(mode, event) {
  if (event.propertyName === 'selected') {
    var targetMode = this.widget('TargetField').value;
    if (mode === targetMode) {
      this.widget('SelectedField').setValue(mode.selected);
    }
  }
};

