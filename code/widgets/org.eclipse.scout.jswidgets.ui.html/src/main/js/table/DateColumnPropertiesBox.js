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
    formatField.on('propertyChange', this._onPropertyChange.bind(this));

    let groupFormatField = this.widget('GroupFormatField');
    groupFormatField.setValue(this.column.groupFormat.pattern);
    groupFormatField.on('propertyChange', this._onPropertyChange.bind(this));

    let hasDateField = this.widget('HasDateField');
    hasDateField.setValue(this.column.hasDate);
    hasDateField.on('propertyChange', this._onPropertyChange.bind(this));

    let hasTimeField = this.widget('HasTimeField');
    hasTimeField.setValue(this.column.hasTime);
    hasTimeField.on('propertyChange', this._onPropertyChange.bind(this));
  }

  _onPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'FormatField') {
      this.column.setFormat(event.newValue);
    }
    if (event.propertyName === 'value' && event.source.id === 'GroupFormatField') {
      this.column.setGroupFormat(event.newValue);
    }
    if (event.propertyName === 'value' && event.source.id === 'HasDateField') {
      this.column.hasDate = event.newValue;
      this.column.setFormat();
    }
    if (event.propertyName === 'value' && event.source.id === 'HasTimeField') {
      this.column.hasTime = event.newValue;
      this.column.setFormat();
    }
  }
}
