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
import {Form, FormModel, InitModelOf, models, ProposalField} from '@eclipse-scout/core';
import ProposalFieldFormModel from './ProposalFieldFormModel';
import {ProposalFieldFormWidgetMap} from '../index';

export class ProposalFieldForm extends Form {
  declare widgetMap: ProposalFieldFormWidgetMap;

  proposalField: ProposalField;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(ProposalFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.proposalField = this.widget('ProposalField');

    this.widget('ProposalFieldPropertiesBox').setField(this.proposalField);
    this.widget('ValueFieldPropertiesBox').setField(this.proposalField);
    this.widget('FormFieldPropertiesBox').setField(this.proposalField);
    this.widget('GridDataBox').setField(this.proposalField);
    this.widget('WidgetActionsBox').setField(this.proposalField);
    this.widget('FormFieldActionsBox').setField(this.proposalField);
    this.widget('EventsTab').setField(this.proposalField);
  }
}
