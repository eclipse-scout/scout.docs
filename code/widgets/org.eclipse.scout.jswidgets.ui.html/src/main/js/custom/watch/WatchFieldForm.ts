/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
