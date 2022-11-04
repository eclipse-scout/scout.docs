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
import {Form, FormModel, InitModelOf, models, SmartFieldMultiline} from '@eclipse-scout/core';
import MultilineSmartFieldFormModel from './MultilineSmartFieldFormModel';
import {MultilineSmartFieldFormWidgetMap} from '../../index';

export class MultilineSmartFieldForm extends Form {
  declare widgetMap: MultilineSmartFieldFormWidgetMap;

  smartField: SmartFieldMultiline<number>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(MultilineSmartFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.smartField = this.widget('MultilineSmartField');

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
