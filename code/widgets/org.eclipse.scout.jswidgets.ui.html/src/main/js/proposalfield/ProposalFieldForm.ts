/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
