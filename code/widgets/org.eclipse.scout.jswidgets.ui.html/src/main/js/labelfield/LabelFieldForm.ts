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
import LabelFieldFormModel from './LabelFieldFormModel';
import {LabelFieldFormWidgetMap} from '../index';

export class LabelFieldForm extends Form {
  declare widgetMap: LabelFieldFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return models.get(LabelFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let labelField = this.widget('LabelField');

    let selectableField = this.widget('SelectableField');
    selectableField.setValue(labelField.selectable);
    selectableField.on('propertyChange:value', event => this.widget('LabelField').setSelectable(event.newValue));

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
