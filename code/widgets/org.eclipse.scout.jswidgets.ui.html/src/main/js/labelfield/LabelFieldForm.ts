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
import LabelFieldFormModel from './LabelFieldFormModel';
import {LabelFieldFormWidgetMap} from '../index';

export class LabelFieldForm extends Form {
  declare widgetMap: LabelFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(LabelFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let labelField = this.widget('LabelField');

    let wrapTextField = this.widget('WrapTextField');
    wrapTextField.setValue(labelField.wrapText);
    wrapTextField.on('propertyChange:value', event => this.widget('LabelField').setWrapText(event.newValue));

    let htmlEnabledField = this.widget('HtmlEnabledField');
    htmlEnabledField.setValue(labelField.htmlEnabled);
    htmlEnabledField.on('propertyChange:value', event => this.widget('LabelField').setHtmlEnabled(event.newValue));

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(labelField);
    this.widget('FormFieldPropertiesBox').setField(labelField);
    this.widget('GridDataBox').setField(labelField);
    this.widget('WidgetActionsBox').setField(labelField);
    this.widget('FormFieldActionsBox').setField(labelField);
    this.widget('EventsTab').setField(labelField);
  }
}
