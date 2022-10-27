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
import CarouselFormModel from './CarouselFormModel';
import {Form, models} from '@eclipse-scout/core';

export default class CarouselForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(CarouselFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    let carousel = this.widget('Carousel');
    let statusEnabledField = this.widget('StatusEnabledField');
    statusEnabledField.setValue(carousel.statusEnabled);
    statusEnabledField.on('propertyChange:value', event => this.widget('Carousel').setStatusEnabled(event.newValue));

    let carouselField = this.widget('CarouselField');
    this.widget('FormFieldPropertiesBox').setField(carouselField);
    this.widget('GridDataBox').setField(carouselField);
    this.widget('WidgetActionsBox').setField(carouselField);
    this.widget('FormFieldActionsBox').setField(carouselField);
    this.widget('EventsTab').setField(carousel);
  }
}
