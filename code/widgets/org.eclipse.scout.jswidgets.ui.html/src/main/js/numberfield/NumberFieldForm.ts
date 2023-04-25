/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import NumberFieldFormModel from './NumberFieldFormModel';
import {NumberFieldFormWidgetMap} from '../index';
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';

export class NumberFieldForm extends Form {
  declare widgetMap: NumberFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(NumberFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
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

    let fractionDigitsField = this.widget('FractionDigitsField');
    fractionDigitsField.on('propertyChange:value', event => this.widget('NumberField').setFractionDigits(event.newValue));
    fractionDigitsField.setValue(numberField.fractionDigits);

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(numberField);
    this.widget('FormFieldPropertiesBox').setField(numberField);
    this.widget('GridDataBox').setField(numberField);
    this.widget('WidgetActionsBox').setField(numberField);
    this.widget('FormFieldActionsBox').setField(numberField);
    this.widget('EventsTab').setField(numberField);
  }

  protected _updateFormat() {
    let multiplierField = this.widget('MultiplierField');
    let formatField = this.widget('FormatField');
    this.widget('NumberField').setDecimalFormat({
      pattern: formatField.value,
      multiplier: multiplierField.value
    });
    multiplierField.setValue(this.widget('NumberField').decimalFormat.multiplier);
  }
}
