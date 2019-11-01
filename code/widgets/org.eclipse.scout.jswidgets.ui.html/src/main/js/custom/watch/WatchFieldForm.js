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
import WatchFieldFormModel from './WatchFieldFormModel';

export default class WatchFieldForm extends Form {

  constructor() {
    super();
  }


  _jsonModel() {
    return models.get(WatchFieldFormModel);
  }

  _init(model) {
    super._init(model);
    var watchField = this.widget('WatchField');

    this.widget('FormFieldPropertiesBox').setField(watchField);
    this.widget('GridDataBox').setField(watchField);
    this.widget('WidgetActionsBox').setField(watchField);
    this.widget('EventsTab').setField(watchField);
  }

  _onSelectedTabChange(event) {
    if (event.propertyName === 'value') {
      if (event.newValue) {
        this.widget('TabBox').selectTabById(event.newValue);
      } else {
        this.widget('TabBox').setSelectedTab();
      }
    }
  }

  _onFieldPropertyChange(event) {
    if (event.propertyName === 'selectedTab') {
      this.widget('SelectedTabField').setValue((event.newValue) ? (event.newValue.id) : (null));
    }
  }
}
