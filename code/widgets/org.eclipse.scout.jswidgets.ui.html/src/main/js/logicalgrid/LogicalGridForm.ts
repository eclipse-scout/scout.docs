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
import {Form, models, scout} from '@eclipse-scout/core';
import LogicalGridFormModel from './LogicalGridFormModel';
import {FormFieldLookupCall} from '../index';

export default class LogicalGridForm extends Form {

  constructor() {
    super();
    this._fieldFocusHandler = this._onFieldFocus.bind(this);
    this._fieldRenderHandler = this._onFieldRender.bind(this);
  }

  _jsonModel() {
    return models.get(LogicalGridFormModel);
  }

  _init(model) {
    super._init(model);

    let groupBox = this.widget('DetailBox');
    groupBox.on('propertyChange:fields', event => this._initFields(event.source.fields));
    this._initFields(groupBox.fields);

    let targetField = this.widget('TargetField');
    targetField.lookupCall = new FormFieldLookupCall(groupBox);
    targetField.on('propertyChange:value', this._onTargetFieldValueChange.bind(this));

    let logicalGridField = this.widget('LogicalGridField');
    logicalGridField.setValue(groupBox.logicalGrid ? groupBox.logicalGrid.objectType : null);
    logicalGridField.on('propertyChange:value', event => this.widget('DetailBox').setLogicalGrid(event.newValue));

    this.widget('GridDataBox').setEnabled(!!targetField.value);

    this.widget('Actions.AddFieldBox').setField(groupBox);
    this.widget('Actions.DeleteFieldBox').setField(groupBox);
  }

  _initFields(fields) {
    fields.forEach(function(field) {
      field.off('render', this._fieldRenderHandler);
      field.on('render', this._fieldRenderHandler);
    }, this);
  }

  _onFieldRender(event) {
    event.source.$field.off('focus', this._fieldFocusHandler);
    event.source.$field.on('focus', this._fieldFocusHandler);
  }

  _onTargetFieldValueChange(event) {
    let oldField = event.oldValue;
    let newField = event.newValue;
    this.widget('GridDataBox').setField(newField);
    this.widget('GridDataBox').setEnabled(!!newField);
    this.widget('CalculatedGridDataBox').setField(newField);
    if (oldField) {
      oldField.removeCssClass('field-highlighted');
    }
    if (newField) {
      newField.addCssClass('field-highlighted');
    }
  }

  _onFieldFocus(event) {
    let field = scout.widget(event.currentTarget);
    this.widget('TargetField').setValue(field);
    this.widget('Actions.AddFieldBox').setTargetField(field);
    this.widget('Actions.DeleteFieldBox').setTargetField(field);
  }
}
