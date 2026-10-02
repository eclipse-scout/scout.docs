/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormField, GroupBox, InitModelOf, RadioButtonGroup, SequenceBox, StaticLookupCall, StaticLookupCallModel} from '@eclipse-scout/core';

export class FormFieldLookupCall extends StaticLookupCall<FormField> implements FormFieldLookupCallModel {
  declare model: FormFieldLookupCallModel;
  compositeField: GroupBox | SequenceBox | RadioButtonGroup<any>;
  protected _rebuildDataHandler: () => void;

  constructor() {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.setCompositeField(this.compositeField);
  }

  setCompositeField(compositeField: GroupBox | SequenceBox | RadioButtonGroup<any>) {
    if (this.compositeField) {
      this.compositeField.off('propertyChange:fields', this._rebuildDataHandler);
      this.compositeField.fields.forEach(formField => formField.off('propertyChange:label', this._rebuildDataHandler));
    }
    this.compositeField = compositeField;
    this.compositeField.on('propertyChange:fields', this._rebuildDataHandler);
    this.compositeField.fields.forEach(formField => formField.on('propertyChange:label', this._rebuildDataHandler));
    this._rebuildData();
  }

  protected _rebuildData() {
    this.data = this.compositeField.fields.map(formField => {
      return [formField, formField.label];
    });
  }
}

export interface FormFieldLookupCallModel extends StaticLookupCallModel<FormField> {
  compositeField?: GroupBox | SequenceBox | RadioButtonGroup<any>;
}
