/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models, SmartField} from '@eclipse-scout/core';
import TreeSmartFieldFormModel from './TreeSmartFieldFormModel';
import {TreeSmartFieldFormWidgetMap} from '../../index';

export class TreeSmartFieldForm extends Form {
  declare widgetMap: TreeSmartFieldFormWidgetMap;

  smartField: SmartField<string>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(TreeSmartFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.smartField = this.widget('TreeSmartField');

    this.widget('TreeSmartFieldPropertiesBox').setField(this.smartField);
    this.widget('LookupCallField').setVisible(false);
    this.widget('ValueFieldPropertiesBox').setField(this.smartField);
    this.widget('FormFieldPropertiesBox').setField(this.smartField);
    this.widget('GridDataBox').setField(this.smartField);
    this.widget('WidgetActionsBox').setField(this.smartField);
    this.widget('FormFieldActionsBox').setField(this.smartField);
    this.widget('EventsTab').setField(this.smartField);
  }
}
