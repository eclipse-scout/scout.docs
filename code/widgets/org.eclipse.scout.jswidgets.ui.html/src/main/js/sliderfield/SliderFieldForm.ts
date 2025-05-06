/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf} from '@eclipse-scout/core';
import {SliderFieldFormWidgetMap} from '../index';
import model from './SliderFieldFormModel';

export class SliderFieldForm extends Form {
  declare widgetMap: SliderFieldFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let sliderField = this.widget('SliderField');

    // -----

    let stepField = this.widget('StepField');
    stepField.setValue(sliderField.step);
    stepField.on('propertyChange:value', event => sliderField.setStep(event.newValue));

    let sliderTabbableField = this.widget('SliderTabbableField');
    sliderTabbableField.setValue(sliderField.tabbable);
    sliderTabbableField.on('propertyChange:value', event => {
      sliderField.setTabbable(event.newValue);
      updateFocusButtons();
    });
    let sliderFieldFocusButton = this.widget('SliderFieldFocusButton');
    sliderFieldFocusButton.on('click', event => sliderField.focus());
    let sliderFocusButton = this.widget('SliderFocusButton');
    sliderFocusButton.on('click', event => sliderField.slider.focus());
    const updateFocusButtons = () => {
      sliderFieldFocusButton.setVisible(sliderTabbableField.value);
      sliderFocusButton.setVisible(sliderTabbableField.value);
    };
    updateFocusButtons();

    let valueEditableField = this.widget('ValueEditableField');
    valueEditableField.setValue(sliderField.valueEditable);
    valueEditableField.on('propertyChange:value', event => sliderField.setValueEditable(event.newValue));

    // -----

    let minValueField = this.widget('MinValueField');
    minValueField.on('propertyChange:value', event => sliderField.setMinValue(event.newValue));
    minValueField.setValue(sliderField.minValue);

    let maxValueField = this.widget('MaxValueField');
    maxValueField.on('propertyChange:value', event => sliderField.setMaxValue(event.newValue));
    maxValueField.setValue(sliderField.maxValue);

    let fractionDigitsField = this.widget('FractionDigitsField');
    fractionDigitsField.on('propertyChange:value', event => sliderField.setFractionDigits(event.newValue));
    fractionDigitsField.setValue(sliderField.fractionDigits);

    const updateFormat = () => {
      sliderField.setDecimalFormat({
        pattern: formatField.value,
        multiplier: multiplierField.value
      });
    };

    let formatField = this.widget('FormatField');
    formatField.setValue(sliderField.decimalFormat.pattern);
    formatField.on('propertyChange:value', event => updateFormat());

    let multiplierField = this.widget('MultiplierField');
    multiplierField.setValue(sliderField.decimalFormat.multiplier);
    multiplierField.on('propertyChange:value', event => updateFormat());

    // -----

    this.widget('ValueFieldPropertiesBox').setField(sliderField);
    this.widget('FormFieldPropertiesBox').setField(sliderField);
    this.widget('GridDataBox').setField(sliderField);
    this.widget('FormFieldActionsBox').setField(sliderField);
    this.widget('WidgetActionsBox').setField(sliderField);
    this.widget('EventsTab').setField(sliderField);
  }
}
