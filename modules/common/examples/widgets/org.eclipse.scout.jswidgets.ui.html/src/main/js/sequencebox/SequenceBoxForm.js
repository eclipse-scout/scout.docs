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
import {Form, models} from '@eclipse-scout/core';
import {FormFieldLookupCall} from '../index';
import SequenceBoxFormModel from './SequenceBoxFormModel';

export default class SequenceBoxForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(SequenceBoxFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    let sequenceBox = this.widget('SequenceBox');

    let formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
    formFieldPropertiesBox.setField(sequenceBox);
    this.widget('GridDataBox').setField(sequenceBox);
    this.widget('LayoutConfigBox').setField(sequenceBox);
    this.widget('WidgetActionsBox').setField(sequenceBox);
    this.widget('FormFieldActionsBox').setField(sequenceBox);
    this.widget('EventsTab').setField(sequenceBox);

    // FieldProperties tab
    let targetField = this.widget('Field.TargetField');
    targetField.setLookupCall(new FormFieldLookupCall(sequenceBox));
    targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));
    targetField.setValue(sequenceBox.fields[0]);

    this._onTargetPropertyChange({
      propertyName: 'value',
      newValue: targetField.value
    });
  }

  _onTargetPropertyChange(event) {
    if (event.propertyName === 'value') {
      let targetField = event.newValue;

      let fieldPropertiesBox = this.widget('Field.FormFieldPropertiesBox');
      fieldPropertiesBox.setField(targetField);
      fieldPropertiesBox.setEnabled(!!targetField);

      let fieldGridDataBox = this.widget('Field.GridDataBox');
      fieldGridDataBox.setField(targetField);
      fieldGridDataBox.setEnabled(!!targetField);
    }
  }
}
