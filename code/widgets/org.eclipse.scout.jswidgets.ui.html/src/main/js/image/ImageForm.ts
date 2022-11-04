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
