/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {DateFormat, dates, Form, FormModel, InitModelOf, models, scout, ValueFieldValidator} from '@eclipse-scout/core';
import DateFieldFormModel from './DateFieldFormModel';
import {DateFieldFormWidgetMap} from '../index';

export class DateFieldForm extends Form {
  declare widgetMap: DateFieldFormWidgetMap;

  protected _dontAllowCurrentDateValidator: ValueFieldValidator<Date>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(DateFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let dateField = this.widget('DateField');
    let hasTimeField = this.widget('HasTimeField');
    hasTimeField.setValue(dateField.hasTime);
    hasTimeField.on('propertyChange:value', event => this.widget('DateField').setHasTime(event.newValue));

    let hasDateField = this.widget('HasDateField');
    hasDateField.setValue(dateField.hasDate);
    hasDateField.on('propertyChange:value', event => this.widget('DateField').setHasDate(event.newValue));

    let dateFormatField = this.widget('DateFormatField');
    dateFormatField.setValue(dateField.dateFormatPattern);
    dateFormatField.on('propertyChange:value', event => this.widget('DateField').setDateFormatPattern(event.newValue));

    let timeFormatField = this.widget('TimeFormatField');
    timeFormatField.setValue(dateField.timeFormatPattern);
    timeFormatField.on('propertyChange:value', event => this.widget('DateField').setTimeFormatPattern(event.newValue));

    let timePickerResolutionField = this.widget('TimePickerResolutionField');
    timePickerResolutionField.setValue(dateField.timePickerResolution);
    timePickerResolutionField.on('propertyChange:value', event => this.widget('DateField').setTimePickerResolution(event.newValue));

    let autoDateField = this.widget('AutoDateField');
    autoDateField.setValue(dateField.autoDate);
    autoDateField.on('propertyChange:value', event => this.widget('DateField').setAutoDate(event.newValue));

    let allowedDatesField = this.widget('AllowedDatesField');
    allowedDatesField.setValue(scout.nvl(dateField.allowedDates, []).map(date => this._dateFormat().format(date)));
    allowedDatesField.on('propertyChange:value', event => this.widget('DateField').setAllowedDates(event.newValue.split(',').map(dateStr => this._dateFormat().parse(dateStr))));
    allowedDatesField.addValidator(value => {
      if (!value) {
        return '';
      }
      return value.split(',').map(text => {
        let date = this._dateFormat().parse(text);
        if (!date) {
          throw `${text} is an invalid date`;
        }
        return this._dateFormat().format(date);
      }).join(',');
    });

    this.widget('AllowedSampleDatesMenu').on('action', event => {
      allowedDatesField.setValue([
        this._dateFormat().format(dates.shift(new Date(), 0, 3, 1)),
        this._dateFormat().format(dates.shift(new Date(), 0, 2, 3)),
        this._dateFormat().format(new Date()),
        this._dateFormat().format(dates.shift(new Date(), 0, 0, -1)),
        this._dateFormat().format(dates.shift(new Date(), 0, -2, -1))
      ].join(','));
    });

    let dontAllowCurrentDateField = this.widget('DontAllowCurrentDateField');
    this._dontAllowCurrentDateValidator = value => {
      if (dates.isSameDay(value, new Date())) {
        throw 'You are not allowed to select the current date';
      }
      return value;
    };
    dontAllowCurrentDateField.on('propertyChange:value', event => {
      let dateField = this.widget('DateField');
      if (event.newValue) {
        dateField.addValidator(this._dontAllowCurrentDateValidator);
      } else {
        dateField.removeValidator(this._dontAllowCurrentDateValidator);
      }
    });

    this.widget('ValueFieldPropertiesBox').setField(dateField);
    this.widget('FormFieldPropertiesBox').setField(dateField);
    this.widget('GridDataBox').setField(dateField);
    this.widget('WidgetActionsBox').setField(dateField);
    this.widget('FormFieldActionsBox').setField(dateField);
    this.widget('EventsTab').setField(dateField);
  }

  protected _dateFormat(): DateFormat {
    let dateField = this.widget('DateField');
    return new DateFormat(this.session.locale, dateField.dateFormatPattern + ' ' + dateField.timeFormatPattern);
  }
}
