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
import {GroupBox, models} from '@eclipse-scout/core';
import NumberColumnPropertiesBoxModel from './NumberColumnPropertiesBoxModel';

export default class NumberColumnPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.column = null;
  }

  _jsonModel() {
    return models.get(NumberColumnPropertiesBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column) {
    this.setProperty('column', column);
  }

  _setColumn(column) {
    this._setProperty('column', column);
    if (!this.column) {
      return;
    }

    let minValueField = this.widget('MinValueField');
    minValueField.setValue(this.column.minValue);
    minValueField.on('propertyChange:value', event => {
      this.column.minValue = event.newValue;
    });

    let maxValueField = this.widget('MaxValueField');
    maxValueField.setValue(this.column.maxValue);
    maxValueField.on('propertyChange:value', event => {
      this.column.maxValue = event.newValue;
    });

    let multiplierField = this.widget('MultiplierField');
    multiplierField.setValue(this.column.decimalFormat.multiplier);
    multiplierField.on('propertyChange:value', event => this.column.setDecimalFormat({
      pattern: this.column.decimalFormat.pattern,
      multiplier: event.newValue
    }));

    let formatField = this.widget('FormatField');
    formatField.setValue(this.column.decimalFormat.pattern);
    formatField.on('propertyChange:value', event => this.column.setDecimalFormat({
      pattern: event.newValue,
      multiplier: this.column.decimalFormat.multiplier
    }));

    let backgroundEffectField = this.widget('BackgroundEffectField');
    backgroundEffectField.setValue(this.column.backgroundEffect);
    backgroundEffectField.on('propertyChange:value', event => this.column.setBackgroundEffect(event.newValue));
  }
}
