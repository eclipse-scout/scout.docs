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
import TagFieldFormModel from './TagFieldFormModel';
import {Form, models} from '@eclipse-scout/core';

export default class TagFieldForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(TagFieldFormModel);
  }

  _init(model) {
    super._init(model);

    var tagField = this.widget('TagField');

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').parseValue = function(newValue) {
      if (!newValue) {
        return newValue;
      }
      return newValue.split(',');
    };
    this.widget('ValueFieldPropertiesBox').setField(tagField);
    this.widget('FormFieldPropertiesBox').setField(tagField);
    this.widget('GridDataBox').setField(tagField);
    this.widget('WidgetActionsBox').setField(tagField);
    this.widget('EventsTab').setField(tagField);
  }
}
