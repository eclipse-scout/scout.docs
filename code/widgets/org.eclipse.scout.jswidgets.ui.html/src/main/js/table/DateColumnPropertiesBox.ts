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
import DateColumnPropertiesBoxModel from './DateColumnPropertiesBoxModel';

export default class DateColumnPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.column = null;
  }

  _jsonModel() {
    return models.get(DateColumnPropertiesBoxModel);
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

    let formatField = this.widget('FormatField');
    formatField.setValue(this.column.format.pattern);
    formatField.on('propertyChange:value', event => this.column.setFormat(event.newValue));

    let groupFormatField = this.widget('GroupFormatField');
    groupFormatField.setValue(this.column.groupFormat.pattern);
    groupFormatField.on('propertyChange:value', event => this.column.setGroupFormat(event.newValue));

    let hasDateField = this.widget('HasDateField');
    hasDateField.setValue(this.column.hasDate);
    hasDateField.on('propertyChange:value', event => {
      this.column.hasDate = event.newValue;
      this.column.setFormat();
    });

    let hasTimeField = this.widget('HasTimeField');
    hasTimeField.setValue(this.column.hasTime);
    hasTimeField.on('propertyChange:value', event => {
      this.column.hasTime = event.newValue;
      this.column.setFormat();
    });
  }
}
