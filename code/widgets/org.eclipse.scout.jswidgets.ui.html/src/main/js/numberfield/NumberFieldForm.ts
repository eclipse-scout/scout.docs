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
import NumberFieldFormModel from './NumberFieldFormModel';
import {Form, models} from '@eclipse-scout/core';

export default class NumberFieldForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(NumberFieldFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    let numberField = this.widget('NumberField');
    let formatField = this.widget('FormatField');
    formatField.setValue(numberField.decimalFormat.pattern);
    formatField.on('propertyChange:value', event => this._updateFormat());

    let multiplierField = this.widget('MultiplierField');
    multiplierField.setValue(numberField.decimalFormat.multiplier);
    multiplierField.on('propertyChange:value', event => this._updateFormat());

    let minValueField = this.widget('MinValueField');
    minValueField.on('propertyChange:value', event => this.widget('NumberField').setMinValue(event.newValue));
    minValueField.setValue(numberField.minValue);

    let maxValueField = this.widget('MaxValueField');
    maxValueField.on('propertyChange:value', event => this.widget('NumberField').setMaxValue(event.newValue));
    maxValueField.setValue(numberField.maxValue);

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(numberField);
    this.widget('FormFieldPropertiesBox').setField(numberField);
    this.widget('GridDataBox').setField(numberField);
    this.widget('WidgetActionsBox').setField(numberField);
    this.widget('FormFieldActionsBox').setField(numberField);
    this.widget('EventsTab').setField(numberField);
  }

  _updateFormat() {
    let multiplierField = this.widget('MultiplierField');
    let formatField = this.widget('FormatField');
    this.widget('NumberField').setDecimalFormat({
      pattern: formatField.value,
      multiplier: multiplierField.value
    });
    multiplierField.setValue(this.widget('NumberField').decimalFormat.multiplier);
  }
}
