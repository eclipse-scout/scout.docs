/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';
import FileChooserButtonFormModel from './FileChooserButtonFormModel';
import {FileChooserButtonFormWidgetMap} from '../index';

export class FileChooserButtonForm extends Form {
  declare widgetMap: FileChooserButtonFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(FileChooserButtonFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
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
