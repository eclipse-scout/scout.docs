/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Form, FormModel, InitModelOf, Mode, models, PropertyChangeEvent} from '@eclipse-scout/core';
import {ModeLookupCall, ModeSelectorFormWidgetMap} from '../index';
import ModeSelectorFormModel from './ModeSelectorFormModel';

export class ModeSelectorForm extends Form {
  declare widgetMap: ModeSelectorFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(ModeSelectorFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let modeSelectorField = this.widget('ModeSelectorField');
    let modeSelector = this.widget('ModeSelector');

    let mode1 = this.widget('Mode1');
    mode1.on('propertyChange:selected', this._onModeChange.bind(this, mode1));

    let mode2 = this.widget('Mode2');
    mode2.on('propertyChange:selected', this._onModeChange.bind(this, mode2));

    let mode3 = this.widget('Mode3');
    mode3.on('propertyChange:selected', this._onModeChange.bind(this, mode3));

    let targetField = this.widget('TargetField');
    targetField.setLookupCall(new ModeLookupCall(modeSelector));
    targetField.setValue(modeSelector.modes[0]);
    targetField.on('propertyChange:value', event => {
      let mode = event.newValue;
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
    });

    let selectedField = this.widget('SelectedField');
    selectedField.setValue(targetField.value.selected);
    selectedField.on('propertyChange:value', event => {
      let mode = this.widget('TargetField').value;
      if (mode) {
        mode.setSelected(event.newValue);
      }
    });

    let enabledField = this.widget('EnabledField');
    enabledField.setValue(targetField.value.enabled);
    enabledField.on('propertyChange:value', event => {
      let mode = this.widget('TargetField').value;
      if (mode) {
        mode.setEnabled(event.newValue);
      }
    });

    let visibleField = this.widget('VisibleField');
    visibleField.setValue(targetField.value.visible);
    visibleField.on('propertyChange:value', event => {
      let mode = this.widget('TargetField').value;
      if (mode) {
        mode.setVisible(event.newValue);
      }
    });

    let textField = this.widget('TextField');
    textField.setValue(targetField.value.text);
    textField.on('propertyChange:value', event => {
      let mode = this.widget('TargetField').value;
      if (mode) {
        mode.setText(event.newValue);
      }
    });

    let iconIdField = this.widget('IconIdField');
    iconIdField.setValue(targetField.value.iconId);
    iconIdField.on('propertyChange:value', event => {
      let mode = this.widget('TargetField').value;
      if (mode) {
        mode.setIconId(event.newValue);
      }
    });

    this.widget('FormFieldPropertiesBox').setField(modeSelectorField);
    this.widget('GridDataBox').setField(modeSelectorField);
    this.widget('WidgetActionsBox').setField(modeSelectorField);
    this.widget('FormFieldActionsBox').setField(modeSelectorField);
    this.widget('EventsTab').setField(modeSelectorField);
  }

  protected _onModeChange(mode: Mode, event: PropertyChangeEvent<boolean, Mode>) {
    let targetMode = this.widget('TargetField').value;
    if (mode === targetMode) {
      this.widget('SelectedField').setValue(mode.selected);
    }
  }
}

