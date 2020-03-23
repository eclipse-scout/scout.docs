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

    var numberField = this.widget('NumberField');
    var formatField = this.widget('FormatField');
    formatField.setValue(numberField.decimalFormat.pattern);
    formatField.on('propertyChange', this._onFormatPropertyChange.bind(this));

    var multiplierField = this.widget('MultiplierField');
    multiplierField.setValue(numberField.decimalFormat.multiplier);
    multiplierField.on('propertyChange', this._onFormatPropertyChange.bind(this));

    var minValueField = this.widget('MinValueField');
    minValueField.on('propertyChange', this._onMinValueFieldPropertyChange.bind(this));
    minValueField.setValue(numberField.minValue);

    var maxValueField = this.widget('MaxValueField');
    maxValueField.on('propertyChange', this._onMaxValueFieldPropertyChange.bind(this));
    maxValueField.setValue(numberField.maxValue);

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(numberField);
    this.widget('FormFieldPropertiesBox').setField(numberField);
    this.widget('GridDataBox').setField(numberField);
    this.widget('WidgetActionsBox').setField(numberField);
    this.widget('FormFieldActionsBox').setField(numberField);
    this.widget('EventsTab').setField(numberField);
  }

  _onFormatPropertyChange(event) {
    if (event.propertyName === 'value') {
      var multiplierField = this.widget('MultiplierField');
      var formatField = this.widget('FormatField');
      this.widget('NumberField').setDecimalFormat({
        pattern: formatField.value,
        multiplier: multiplierField.value
      });
      multiplierField.setValue(this.widget('NumberField').decimalFormat.multiplier);
    }
  }

  _onMinValueFieldPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('NumberField').setMinValue(event.newValue);
    }
  }

  _onMaxValueFieldPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('NumberField').setMaxValue(event.newValue);
    }
  }
}
