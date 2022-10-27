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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class FormFieldLookupCall extends StaticLookupCall {

  constructor(compositeField) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setCompositeField(compositeField);
  }

  _data() {
    return this.data;
  }

  setCompositeField(compositeField) {
    if (this.compositeField) {
      this.compositeField.off('propertyChange:fields', this._rebuildDataHandler);
      this.compositeField.fields.forEach(function(formField) {
        formField.off('propertyChange:label', this._rebuildDataHandler);
      }, this);
    }
    this.compositeField = compositeField;
    this.compositeField.on('propertyChange:fields', this._rebuildDataHandler);
    this.compositeField.fields.forEach(function(formField) {
      formField.on('propertyChange:label', this._rebuildDataHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.compositeField.fields.map(formField => {
      return [formField, formField.label];
    });
  }
}
