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
import ImageFieldFormModel from './ImageFieldFormModel';
import {ImageFieldFormWidgetMap} from '../index';

export class ImageFieldForm extends Form {
  declare widgetMap: ImageFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(ImageFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let imageField = this.widget('ImageField');
    imageField.on('drop', event => imageField.setImageUrl(URL.createObjectURL(event.files[0])));
    imageField.on('fileUpload', event => imageField.setImageUrl(URL.createObjectURL(event.file)));

    let autoFitField = this.widget('AutoFitField');
    autoFitField.setValue(imageField.autoFit);
    autoFitField.on('propertyChange:value', event => imageField.setAutoFit(event.newValue));

    let scrollBarEnabledField = this.widget('ScrollBarEnabledField');
    scrollBarEnabledField.setValue(imageField.scrollBarEnabled);
    scrollBarEnabledField.on('propertyChange:value', event => imageField.setScrollBarEnabled(event.newValue));

    let uploadEnabledField = this.widget('UploadEnabledField');
    uploadEnabledField.setValue(imageField.uploadEnabled);
    uploadEnabledField.on('propertyChange:value', event => imageField.setUploadEnabled(event.newValue));

    let imageUrlField = this.widget('ImageUrlField');
    imageUrlField.setValue(imageField.imageUrl);
    imageUrlField.on('propertyChange:value', event => imageField.setImageUrl(event.newValue));

    this.widget('FormFieldPropertiesBox').setField(imageField);
    this.widget('GridDataBox').setField(imageField);
    this.widget('WidgetActionsBox').setField(imageField);
    this.widget('FormFieldActionsBox').setField(imageField);
    this.widget('EventsTab').setField(imageField);
  }
}
