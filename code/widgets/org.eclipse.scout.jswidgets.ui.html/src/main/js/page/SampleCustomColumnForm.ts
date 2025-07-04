/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, Column, Form, FormModel, InitModelOf, numbers, scout, StaticLookupCall, strings, UuidPool} from '@eclipse-scout/core';
import {SampleCustomColumnDo, SampleCustomColumnFormModel, SampleCustomColumnFormWidgetMap, SampleCustomColumnType} from '../index';
import model from './SampleCustomColumnFormModel';

export class SampleCustomColumnForm extends Form implements SampleCustomColumnFormModel {
  declare model: SampleCustomColumnFormModel;
  declare widgetMap: SampleCustomColumnFormWidgetMap;
  declare data: SampleCustomColumnDo;

  hiddenColumns: Column[];

  constructor() {
    super();
    this.data = null; // override default {}
  }

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    if (arrays.hasElements(this.hiddenColumns)) {
      this.widget('HiddenColumnsField').setLookupCall(scout.create(StaticLookupCall<Column<any>>, {
        session: this.session,
        data: this.hiddenColumns.map(column => [column, column.text])
      }));
    }

    this.widget('TabBox').on('propertyChange:selectedTab', event => {
      this.widget('HiddenColumnsField').setMandatory(event.newValue === this.widget('HiddenColumnsTab'));
    });

    // Allow save without changing anything
    this.widget('OkMenu').on('action', event => {
      this.touch();
    });
  }

  override importData() {
    if (this.data) {
      this.setTitle('Edit column');
      this.widget('NameField').setValue(this.data.name);
      this.widget('ColumnTypeField').setValue(this.data.columnType);
      this.widget('WidthField').setValue(this.data.width);
    } else {
      this.setTitle('Add column');
      this.widget('NameField').setValue(this._computeRandomName());
      this.widget('ColumnTypeField').setValue('string');
    }

    if (this.data || arrays.empty(this.hiddenColumns)) {
      this.widget('TabBox').header.setVisible(false);
      this.widget('HiddenColumnsTab').setVisible(false);
    }
  }

  override exportData(): any {
    if (this.widget('TabBox').selectedTab === this.widget('HiddenColumnsTab')) {
      this.hiddenColumns = this.widget('HiddenColumnsField').value;
      return null;
    }
    return scout.create(SampleCustomColumnDo, {
      columnId: this.data?.columnId || UuidPool.take(this.session),
      name: this.widget('NameField').value || undefined,
      columnType: this.widget('ColumnTypeField').value,
      width: this.widget('WidthField').value ?? undefined
    });
  }

  protected _computeRandomName(): string {
    let words = [this._computeRandomWord(3 + numbers.randomInt(6))];
    if (Math.random() > 0.2) {
      words.push(this._computeRandomWord(3 + numbers.randomInt(2)));
    }
    if (Math.random() > 0.5) {
      words.push(this._computeRandomWord(5 + numbers.randomInt(2)));
    }
    return words.map(word => strings.toUpperCaseFirstLetter(word)).join(' ');
  }

  protected _computeRandomWord(length: number): string {
    const vowels = ['a', 'e', 'i', 'o', 'u'];
    const consonants = ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'];
    const bigrams = ['th', 'he', 'in', 'er', 'an', 're', 'nd', 'at', 'on', 'en', 'nt', 'ha', 'es', 'st', 'to', 'ed', 'it', 'ou', 'ea', 'hi', 'is', 'or', 'ti', 'as', 'te', 'et', 'ng', 'of'];

    let word = '';
    let vowel = Math.random() > 0.5;
    for (let i = 0; i < length; i++) {
      if (vowel) {
        word += arrays.randomElement(vowels);
      } else {
        if (Math.random() < 0.2 && i < length - 1) {
          word += arrays.randomElement(bigrams);
          i++;
        } else {
          word += arrays.randomElement(consonants);
        }
      }
      vowel = !vowel;
    }
    return word;
  }
}

export class SampleCustomColumnTypeLookupCall
  extends StaticLookupCall<SampleCustomColumnType> {

  protected override _data(): any[] {
    return [
      ['string', 'String'],
      ['number', 'Number'],
      ['boolean', 'Boolean'],
      ['date', 'Date']
    ];
  }
}
