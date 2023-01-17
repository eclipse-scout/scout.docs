/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import CarouselFormModel from './CarouselFormModel';
import {CarouselFormWidgetMap} from '../index';
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';

export class CarouselForm extends Form {
  declare widgetMap: CarouselFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(CarouselFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
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
