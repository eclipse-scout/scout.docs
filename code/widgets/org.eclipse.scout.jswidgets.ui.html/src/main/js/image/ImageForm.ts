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
import ImageFormModel from './ImageFormModel';
import {ImageFormWidgetMap} from '../index';

export class ImageForm extends Form {
  declare widgetMap: ImageFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(ImageFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let image = this.widget('Image');

    let autoFitField = this.widget('AutoFitField');
    autoFitField.setValue(image.autoFit);
    autoFitField.on('propertyChange:value', event => image.setAutoFit(event.newValue));

    let imageUrlField = this.widget('ImageUrlField');
    imageUrlField.setValue(image.imageUrl);
    imageUrlField.on('propertyChange:value', event => image.setImageUrl(event.newValue));

    this.widget('GridDataBox').setField(this.widget('WidgetField'));
    this.widget('WidgetActionsBox').setField(image);
    this.widget('EventsTab').setField(image);
  }
}
