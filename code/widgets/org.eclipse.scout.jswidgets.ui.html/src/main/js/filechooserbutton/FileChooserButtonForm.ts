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

    let fileChooserButton = this.widget('FileChooserButton');

    let acceptTypesField = this.widget('AcceptTypesField');
    acceptTypesField.setValue(fileChooserButton.acceptTypes);
    acceptTypesField.on('propertyChange:value', event => this.widget('FileChooserButton').setAcceptTypes(event.newValue));

    let maximumUploadSizeField = this.widget('MaximumUploadSizeField');
    maximumUploadSizeField.setValue(fileChooserButton.maximumUploadSize);
    maximumUploadSizeField.on('propertyChange:value', event => this.widget('FileChooserButton').setMaximumUploadSize(event.newValue));

    let iconIdField = this.widget('IconIdField');
    iconIdField.setValue(fileChooserButton.iconId);
    iconIdField.on('propertyChange:value', event => this.widget('FileChooserButton').setIconId(event.newValue));

    this.widget('ValueFieldPropertiesBox').setField(fileChooserButton);
    this.widget('FormFieldPropertiesBox').setField(fileChooserButton);
    this.widget('GridDataBox').setField(fileChooserButton);
    this.widget('WidgetActionsBox').setField(fileChooserButton);
    this.widget('FormFieldActionsBox').setField(fileChooserButton);
    this.widget('EventsTab').setField(fileChooserButton);
  }
}
