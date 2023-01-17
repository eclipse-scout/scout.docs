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
import FileChooserFieldFormModel from './FileChooserFieldFormModel';
import {FileChooserFieldFormWidgetMap} from '../index';

export class FileChooserFieldForm extends Form {
  declare widgetMap: FileChooserFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(FileChooserFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let fileChooserField = this.widget('FileChooserField');

    let acceptTypesField = this.widget('AcceptTypesField');
    acceptTypesField.setValue(fileChooserField.acceptTypes);
    acceptTypesField.on('propertyChange:value', event => this.widget('FileChooserField').setAcceptTypes(event.newValue));

    let maximumUploadSizeField = this.widget('MaximumUploadSizeField');
    maximumUploadSizeField.setValue(fileChooserField.maximumUploadSize);
    maximumUploadSizeField.on('propertyChange:value', event => this.widget('FileChooserField').setMaximumUploadSize(event.newValue));

    this.widget('ValueFieldPropertiesBox').setField(fileChooserField);
    this.widget('FormFieldPropertiesBox').setField(fileChooserField);
    this.widget('GridDataBox').setField(fileChooserField);
    this.widget('WidgetActionsBox').setField(fileChooserField);
    this.widget('FormFieldActionsBox').setField(fileChooserField);
    this.widget('EventsTab').setField(fileChooserField);
  }
}
