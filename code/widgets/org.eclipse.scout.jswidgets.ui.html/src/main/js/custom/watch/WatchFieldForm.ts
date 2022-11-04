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
import {Form, InitModelOf, models} from '@eclipse-scout/core';
import WatchFieldFormModel from './WatchFieldFormModel';
import {WatchFieldFormWidgetMap} from '../../index';

export class WatchFieldForm extends Form {
  declare widgetMap: WatchFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel() {
    return models.get(WatchFieldFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    let watchField = this.widget('WatchField');

    this.widget('FormFieldPropertiesBox').setField(watchField);
    this.widget('GridDataBox').setField(watchField);
    this.widget('WidgetActionsBox').setField(watchField);
    this.widget('EventsTab').setField(watchField);
  }
}
