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
import FileChooserButtonFormModel from './FileChooserButtonFormModel';

export default class FileChooserButtonForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(FileChooserButtonFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    var fileChooserButton = this.widget('FileChooserButton');

    var acceptTypesField = this.widget('AcceptTypesField');
    acceptTypesField.setValue(fileChooserButton.acceptTypes);
    acceptTypesField.on('propertyChange', this._onAcceptTypesPropertyChange.bind(this));

    var maximumUploadSizeField = this.widget('MaximumUploadSizeField');
    maximumUploadSizeField.setValue(fileChooserButton.maximumUploadSize);
    maximumUploadSizeField.on('propertyChange', this._onMaximumUploadSizePropertyChange.bind(this));

    var iconIdField = this.widget('IconIdField');
    iconIdField.setValue(fileChooserButton.iconId);
    iconIdField.on('propertyChange', this._onIconIdPropertyChange.bind(this));

    this.widget('ValueFieldPropertiesBox').setField(fileChooserButton);
    this.widget('FormFieldPropertiesBox').setField(fileChooserButton);
    this.widget('GridDataBox').setField(fileChooserButton);
    this.widget('WidgetActionsBox').setField(fileChooserButton);
    this.widget('FormFieldActionsBox').setField(fileChooserButton);
    this.widget('EventsTab').setField(fileChooserButton);
  }

  _onAcceptTypesPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('FileChooserButton').setAcceptTypes(event.newValue);
    }
  }

  _onMaximumUploadSizePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('FileChooserButton').setMaximumUploadSize(event.newValue);
    }
  }

  _onIconIdPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('FileChooserButton').setIconId(event.newValue);
    }
  }
}
