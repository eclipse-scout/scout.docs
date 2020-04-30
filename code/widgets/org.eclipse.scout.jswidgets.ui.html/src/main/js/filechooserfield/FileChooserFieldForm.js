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
import FileChooserFieldFormModel from './FileChooserFieldFormModel';

export default class FileChooserFieldForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(FileChooserFieldFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    let fileChooserField = this.widget('FileChooserField');

    let acceptTypesField = this.widget('AcceptTypesField');
    acceptTypesField.setValue(fileChooserField.acceptTypes);
    acceptTypesField.on('propertyChange', this._onAcceptTypesPropertyChange.bind(this));

    let maximumUploadSizeField = this.widget('MaximumUploadSizeField');
    maximumUploadSizeField.setValue(fileChooserField.maximumUploadSize);
    maximumUploadSizeField.on('propertyChange', this._onMaximumUploadSizePropertyChange.bind(this));

    this.widget('ValueFieldPropertiesBox').setField(fileChooserField);
    this.widget('FormFieldPropertiesBox').setField(fileChooserField);
    this.widget('GridDataBox').setField(fileChooserField);
    this.widget('WidgetActionsBox').setField(fileChooserField);
    this.widget('FormFieldActionsBox').setField(fileChooserField);
    this.widget('EventsTab').setField(fileChooserField);
  }

  _onAcceptTypesPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('FileChooserField').setAcceptTypes(event.newValue);
    }
  }

  _onMaximumUploadSizePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('FileChooserField').setMaximumUploadSize(event.newValue);
    }
  }
}
