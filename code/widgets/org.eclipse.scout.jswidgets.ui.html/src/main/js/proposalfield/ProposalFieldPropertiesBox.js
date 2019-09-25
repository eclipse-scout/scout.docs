/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
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
  this.field = null;
}


_jsonModel() {
  return models.extend(ProposalFieldPropertiesBoxModel(this), super._jsonModel());
}

_init(model) {
  super._init( model);

  this._setField(this.field);
  this.widget('DisplayStyleField').setVisible(false);
}

setField(field) {
  this.setProperty('field', field);
}

_setField(field) {
  super._setField( field);
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  var maxLengthField = this.widget('MaxLengthField');
  maxLengthField.setValue(this.field.maxLength);
  maxLengthField.on('propertyChange', this._onMaxLengthPropertyChange.bind(this));

  var trimTextField = this.widget('TrimTextField');
  trimTextField.setValue(this.field.trimText);
  trimTextField.on('propertyChange', this._onTrimTextPropertyChange.bind(this));
}

_onSmartFieldChange(event) {
  if (event.propertyName === 'lookupCall') {
    this.widget('LookupCallField').setValue(event.newValue);
  }
}

_onLookupCallPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setLookupCall(event.newValue);
  }
}

_onMaxLengthPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setMaxLength(event.newValue);
  }
}

_onTrimTextPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setTrimText(event.newValue);
  }
}
}
