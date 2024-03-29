/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';
import CheckBoxFieldFormModel from './CheckBoxFieldFormModel';
import {CheckBoxFieldFormWidgetMap} from '../index';

export class CheckBoxFieldForm extends Form {
  declare widgetMap: CheckBoxFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(CheckBoxFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let field = this.widget('CheckBoxField');

    let triStateEnabledField = this.widget('TriStateEnabledField');
    triStateEnabledField.setValue(field.triStateEnabled);
    triStateEnabledField.on('propertyChange:value', event => this.widget('CheckBoxField').setTriStateEnabled(event.newValue));

    let wrapTextEnabledField = this.widget('WrapTextField');
    wrapTextEnabledField.setValue(field.wrapText);
    wrapTextEnabledField.on('propertyChange:value', event => this.widget('CheckBoxField').setWrapText(event.newValue));

    let keyStrokeField = this.widget('KeyStrokeField');
    keyStrokeField.setValue(field.keyStroke);
    keyStrokeField.on('propertyChange:value', event => this.widget('CheckBoxField').setKeyStroke(event.newValue));

    this.widget('ValueFieldPropertiesBox').setField(field);
    this.widget('FormFieldPropertiesBox').setField(field);
    this.widget('GridDataBox').setField(field);
    this.widget('WidgetActionsBox').setField(field);
    this.widget('FormFieldActionsBox').setField(field);
    this.widget('EventsTab').setField(field);
  }
}
