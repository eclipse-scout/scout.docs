/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {DateColumn, GroupBox, GroupBoxModel, InitModelOf, models} from '@eclipse-scout/core';
import DateColumnPropertiesBoxModel from './DateColumnPropertiesBoxModel';
import {DateColumnPropertiesBoxWidgetMap} from '../index';

export class DateColumnPropertiesBox extends GroupBox {
  declare widgetMap: DateColumnPropertiesBoxWidgetMap;

  column: DateColumn;

  constructor() {
    super();
    this.column = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(DateColumnPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column: DateColumn) {
    this.setProperty('column', column);
  }

  protected _setColumn(column: DateColumn) {
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
      this.column.setFormat(null);
    });

    let hasTimeField = this.widget('HasTimeField');
    hasTimeField.setValue(this.column.hasTime);
    hasTimeField.on('propertyChange:value', event => {
      this.column.hasTime = event.newValue;
      this.column.setFormat(null);
    });
  }
}
