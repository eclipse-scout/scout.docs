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
import {Form, FormModel, InitModelOf, models, SmartField} from '@eclipse-scout/core';
import TableSmartFieldFormModel from './TableSmartFieldFormModel';
import {TableSmartFieldFormWidgetMap} from '../../index';

export class TableSmartFieldForm extends Form {
  declare widgetMap: TableSmartFieldFormWidgetMap;

  smartField: SmartField<string>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(TableSmartFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.smartField = this.widget('TableSmartField');

    this.widget('SmartFieldPropertiesBox').setField(this.smartField);
    this.widget('LookupCallField').setVisible(false);
    this.widget('ValueFieldPropertiesBox').setField(this.smartField);
    this.widget('FormFieldPropertiesBox').setField(this.smartField);
    this.widget('GridDataBox').setField(this.smartField);
    this.widget('WidgetActionsBox').setField(this.smartField);
    this.widget('FormFieldActionsBox').setField(this.smartField);
    this.widget('EventsTab').setField(this.smartField);
  }
}
