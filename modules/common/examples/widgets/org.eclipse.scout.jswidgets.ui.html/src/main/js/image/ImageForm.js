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
import ImageFormModel from './ImageFormModel';

export default class ImageForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(ImageFormModel);
  }

  _init(model) {
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
