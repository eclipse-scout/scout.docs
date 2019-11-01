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
import {Form, models} from '@eclipse-scout/core';
import {ModeLookupCall} from '../index';
import ModeSelectorFormModel from './ModeSelectorFormModel';

export default class ModeSelectorForm extends Form {

  constructor() {
    super();
  }


  _jsonModel() {
    return models.get(ModeSelectorFormModel);
  }

  _init(model) {
    super._init(model);

    var modeSelectorField = this.widget('ModeSelectorField');
    var modeSelector = this.widget('ModeSelector');

    var mode1 = this.widget('Mode1');
    mode1.on('propertyChange', this._onModeChange.bind(this, mode1));

    var mode2 = this.widget('Mode2');
    mode2.on('propertyChange', this._onModeChange.bind(this, mode2));

    var mode3 = this.widget('Mode3');
    mode3.on('propertyChange', this._onModeChange.bind(this, mode3));

    var targetField = this.widget('TargetField');
    targetField.setLookupCall(new ModeLookupCall(modeSelector));
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
  }


  _onTargetPropertyChange(event) {
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
  }

  _onTextPropertyChange(event) {
    if (event.propertyName === 'value') {
      var mode = this.widget('TargetField').value;
      if (mode) {
        mode.setText(event.newValue);
      }
    }
  }

  _onIconIdPropertyChange(event) {
    if (event.propertyName === 'value') {
      var mode = this.widget('TargetField').value;
      if (mode) {
        mode.setIconId(event.newValue);
      }
    }
  }

  _onSelectedPropertyChange(event) {
    if (event.propertyName === 'value') {
      var mode = this.widget('TargetField').value;
      if (mode) {
        mode.setSelected(event.newValue);
      }
    }
  }

  _onEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      var mode = this.widget('TargetField').value;
      if (mode) {
        mode.setEnabled(event.newValue);
      }
    }
  }

  _onVisiblePropertyChange(event) {
    if (event.propertyName === 'value') {
      var mode = this.widget('TargetField').value;
      if (mode) {
        mode.setVisible(event.newValue);
      }
    }
  }

  _onModeChange(mode, event) {
    if (event.propertyName === 'selected') {
      var targetMode = this.widget('TargetField').value;
      if (mode === targetMode) {
        this.widget('SelectedField').setValue(mode.selected);
      }
    }
  }
}

