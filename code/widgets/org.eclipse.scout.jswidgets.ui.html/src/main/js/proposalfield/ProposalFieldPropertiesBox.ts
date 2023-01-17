/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBoxModel, InitModelOf, models, ProposalField} from '@eclipse-scout/core';
import ProposalFieldPropertiesBoxModel from './ProposalFieldPropertiesBoxModel';
import {ProposalFieldPropertiesBoxWidgetMap, SmartFieldPropertiesBox} from '../index';

export class ProposalFieldPropertiesBox extends SmartFieldPropertiesBox {
  declare widgetMap: ProposalFieldPropertiesBoxWidgetMap;
  declare field: ProposalField;

  constructor() {
    super();
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.extend(ProposalFieldPropertiesBoxModel, super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.widget('DisplayStyleField').setVisible(false);
  }

  protected override _setField(field: ProposalField) {
    super._setField(field);
    if (!this.field) {
      return;
    }

    let maxLengthField = this.widget('MaxLengthField');
    maxLengthField.setValue(this.field.maxLength);
    maxLengthField.on('propertyChange:value', event => this.field.setMaxLength(event.newValue));

    let trimTextField = this.widget('TrimTextField');
    trimTextField.setValue(this.field.trimText);
    trimTextField.on('propertyChange:value', event => this.field.setTrimText(event.newValue));
  }
}
