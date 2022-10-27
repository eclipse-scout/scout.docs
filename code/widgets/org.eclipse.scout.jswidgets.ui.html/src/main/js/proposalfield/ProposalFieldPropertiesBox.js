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
import {models} from '@eclipse-scout/core';
import ProposalFieldPropertiesBoxModel from './ProposalFieldPropertiesBoxModel';
import {SmartFieldPropertiesBox} from '../index';

export default class ProposalFieldPropertiesBox extends SmartFieldPropertiesBox {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.extend(ProposalFieldPropertiesBoxModel, super._jsonModel());
  }

  _init(model) {
    super._init(model);
    this.widget('DisplayStyleField').setVisible(false);
  }

  _setField(field) {
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
