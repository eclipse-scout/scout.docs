/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models, numbers, RadioButton, RadioButtonGroup} from '@eclipse-scout/core';
import {FormFieldLookupCall, RadioButtonGroupFormWidgetMap} from '../index';
import RadioButtonGroupFormModel from './RadioButtonGroupFormModel';

export class RadioButtonGroupForm extends Form {
  declare widgetMap: RadioButtonGroupFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return models.get(RadioButtonGroupFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let group = this.widget('RadioButtonGroup');
    group.on('propertyChange:selectedButton', event => this.widget('SelectedButtonField').setValue(event.newValue));
    group.on('propertyChange:gridColumnCount', event => this.widget('GridColumnCountField').setValue(event.newValue));

    let fields = group.fields;
    for (let field of fields) {
      // Link owner to form so fields don't get destroyed when a lookup call is set
      field.setOwner(this);
    }
    let lookupCallField = this.widget('LookupCallField');
    lookupCallField.setValue(group.lookupCall);
    lookupCallField.on('propertyChange:value', event => {
      if (event.newValue) {
        group.setFields([]);
      } else {
        group.setFields(fields);
      }
      group.setLookupCall(event.newValue);
    });

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
    // If radio buttons are replaced, update value in targetField
    group.on('propertyChange:fields', event => targetField.setValue(event.newValue[0]));

    let keyStrokeField = this.widget('Button.KeyStrokeField');
    keyStrokeField.setValue((targetField.value as RadioButton<number>).keyStroke);
    keyStrokeField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      button?.setKeyStroke(event.newValue);
    });

    let selectedField = this.widget('Button.SelectedField');
    selectedField.setValue((targetField.value as RadioButton<number>).selected);
    selectedField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      button?.setSelected(event.newValue);
    });

    let wrapTextField = this.widget('Button.WrapTextField');
    wrapTextField.setValue((targetField.value as RadioButton<number>).wrapText);
    wrapTextField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      button?.setWrapText(event.newValue);
    });

    let iconIdField = this.widget('Button.IconIdField');
    iconIdField.setValue((targetField.value as RadioButton<number>).iconId);
    iconIdField.on('propertyChange:value', event => {
      let button = this.widget('Button.TargetField').value as RadioButton<number>;
      button?.setIconId(event.newValue);
    });

    this.widget('Button.PropertiesBox').setEnabled(!!targetField.value);
    this.widget('Button.FormFieldPropertiesBox').setField(targetField.value);
    this.widget('Button.FormFieldPropertiesBox').setEnabled(!!targetField.value);
    this.widget('Button.GridDataBox').setField(targetField.value);
    this.widget('Button.GridDataBox').setEnabled(!!targetField.value);
  }
}
