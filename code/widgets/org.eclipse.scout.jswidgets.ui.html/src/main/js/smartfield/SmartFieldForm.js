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
import {Form, MessageBoxes, models} from '@eclipse-scout/core';
import SmartFieldFormModel from './SmartFieldFormModel';

export default class SmartFieldForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(SmartFieldFormModel);
  }

  _init(model) {
    super._init(model);

    this.smartField = this.widget('SmartField');

    var newLanguageMenu = this.widget('NewLanguageMenu');
    newLanguageMenu.on('action', this._onNewLanguageMenuAction.bind(this));

    this.widget('SmartFieldPropertiesBox').setField(this.smartField);
    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(this.smartField);
    this.widget('FormFieldPropertiesBox').setField(this.smartField);
    this.widget('GridDataBox').setField(this.smartField);
    this.widget('WidgetActionsBox').setField(this.smartField);
    this.widget('EventsTab').setField(this.smartField);
  }

  _onNewLanguageMenuAction(event) {
    return MessageBoxes.createOk(this)
      .withHeader(this.session.text('ThanksForClickingMe'))
      .withBody(this.session.text('NewLanguageMessage'))
      .buildAndOpen();
  }
}
