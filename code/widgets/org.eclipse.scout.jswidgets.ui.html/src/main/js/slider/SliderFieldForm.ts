/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormModel, InitModelOf, models, SliderField} from '@eclipse-scout/core';
import {NumberFieldForm, SliderFieldFormWidgetMap} from '../index';
import SliderFieldFormModel from './SliderFieldFormModel';

export class SliderFieldForm extends NumberFieldForm {

  declare widgetMap: SliderFieldFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return models.extend(SliderFieldFormModel, super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let numberField = this.widget('NumberField') as SliderField;

    let stepField = this.widget('StepField');
    stepField.setValue(numberField.step);
    stepField.on('propertyChange:value', event => numberField.setStep(event.newValue));

    let valueEditableField = this.widget('ValueEditableField');
    valueEditableField.setValue(numberField.valueEditable);
    valueEditableField.on('propertyChange:value', event => numberField.setValueEditable(event.newValue));

    let focusButton = this.widget('FocusButton');
    focusButton.setVisible(numberField.sliderTabbable);
    focusButton.on('click', event => numberField.slider.focus());

    let sliderTabbableField = this.widget('SliderTabbableField');
    sliderTabbableField.setValue(numberField.sliderTabbable);
    sliderTabbableField.on('propertyChange:value', event => {
      numberField.setSliderTabbable(event.newValue);
      focusButton.setVisible(event.newValue);
    });

    this.widget('CalculatorField').setVisible(false);
  }
}
