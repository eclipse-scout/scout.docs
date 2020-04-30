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
import LabelFieldFormModel from './LabelFieldFormModel';

export default class LabelFieldForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(LabelFieldFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    let labelField = this.widget('LabelField');

    let wrapTextField = this.widget('WrapTextField');
    wrapTextField.setValue(labelField.wrapText);
    wrapTextField.on('propertyChange', this._onWrapTextPropertyChange.bind(this));

    let htmlEnabledField = this.widget('HtmlEnabledField');
    htmlEnabledField.setValue(labelField.htmlEnabled);
    htmlEnabledField.on('propertyChange', this._onHtmlEnabledPropertyChange.bind(this));

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(labelField);
    this.widget('FormFieldPropertiesBox').setField(labelField);
    this.widget('GridDataBox').setField(labelField);
    this.widget('WidgetActionsBox').setField(labelField);
    this.widget('FormFieldActionsBox').setField(labelField);
    this.widget('EventsTab').setField(labelField);
  }

  _onWrapTextPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('LabelField').setWrapText(event.newValue);
    }
  }

  _onHtmlEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.widget('LabelField').setHtmlEnabled(event.newValue);
    }
  }
}
