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
import {Form, FormModel, InitModelOf, models, numbers, RadioButton, RadioButtonGroup} from '@eclipse-scout/core';
import {FormFieldLookupCall, RadioButtonGroupFormWidgetMap} from '../index';
import RadioButtonGroupFormModel from './RadioButtonGroupFormModel';

export class RadioButtonGroupForm extends Form {
  declare widgetMap: RadioButtonGroupFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(RadioButtonGroupFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let group = this.widget('RadioButtonGroup');
    group.on('propertyChange:selectedButton', event => this.widget('SelectedButtonField').setValue(event.newValue));
    group.on('propertyChange:gridColumnCount', event => this.widget('GridColumnCountField').setValue(event.newValue));

    let selectedButtonField = this.widget('SelectedButtonField');
    selectedButtonField.setLookupCall(new FormFieldLookupCall(group));
    selectedButtonField.setValue(group.selectedButton);
    selectedButtonField.on('propertyChange:value', event => this.widget('RadioButtonGroup').selectButton(event.newValue as RadioButton<number>));

    let gridColumnCountField = this.widget('GridColumnCountField');
    gridColumnCountField.setValue(group.gridColumnCount);
    gridColumnCountField.on('propertyChange:value', event => {
      let newVal = event.newValue;
      if (newVal > 0 || newVal === RadioButtonGroup.DEFAULT_GRID_COLUMN_COUNT) {
        this.widget('RadioButtonGroup').setGridColumnCount(newVal);
      }
    });

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').parseValue = (newValue: string | number) => {
      if (!isNaN(newValue as number)) {
        newValue = numbers.ensure(newValue);
      }
      return newValue;
    };
    this.widget('ValueFieldPropertiesBox').setField(group);

    this.widget('FormFieldPropertiesBox').setField(group);
    this.widget('GridDataBox').setField(group);
    this.widget('LayoutConfigBox').setField(group);
    this.widget('WidgetActionsBox').setField(group);
    this.widget('FormFieldActionsBox').setField(group);
    this.widget('EventsTab').setField(group);

    // Button tab
    let targetField = this.widget('Button.TargetField');
    targetField.setLookupCall(new FormFieldLookupCall(group));
    targetField.setValue(group.radioButtons[0]);
    targetField.on('propertyChange:value', event => {
      let button = event.newValue as RadioButton<number>;
      if (button) {
        this.widget('Button.KeyStrokeField').setValue(button.keyStroke);
        this.widget('Button.SelectedField').setValue(button.selected);
        this.widget('Button.WrapTextField').setValue(button.wrapText);
      }
      this.widget('Button.PropertiesBox').setEnabled(!!button);
      this.widget('Button.FormFieldPropertiesBox').setField(button);
      this.widget('Button.FormFieldPropertiesBox').setEnabled(!!button);
      this.widget('Button.GridDataBox').setField(button);
      this.widget('Button.GridDataBox').setEnabled(!!button);
    });

    let keyStrokeField = this.widget('Button.KeyStrokeField');
    keyStrokeField.setValue((targetField.value as RadioButton<number>).keyStroke);
    keyStrokeField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      if (button) {
        button.setKeyStroke(event.newValue);
      }
    });

    let selectedField = this.widget('Button.SelectedField');
    selectedField.setValue((targetField.value as RadioButton<number>).selected);
    selectedField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      if (button) {
        button.setSelected(event.newValue);
      }
    });

    let wrapTextField = this.widget('Button.WrapTextField');
    wrapTextField.setValue((targetField.value as RadioButton<number>).wrapText);
    wrapTextField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      if (button) {
        button.setWrapText(event.newValue);
      }
    });

    this.widget('Button.PropertiesBox').setEnabled(!!targetField.value);
    this.widget('Button.FormFieldPropertiesBox').setField(targetField.value);
    this.widget('Button.FormFieldPropertiesBox').setEnabled(!!targetField.value);
    this.widget('Button.GridDataBox').setField(targetField.value);
    this.widget('Button.GridDataBox').setEnabled(!!targetField.value);
  }
}
