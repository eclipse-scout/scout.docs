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
import TreeSmartFieldPropertiesBoxModel from './TreeSmartFieldPropertiesBoxModel';
import {models} from '@eclipse-scout/core';
import {SmartFieldPropertiesBox} from '../../index';

export default class TreeSmartFieldPropertiesBox extends SmartFieldPropertiesBox {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.extend(TreeSmartFieldPropertiesBoxModel, super._jsonModel());
  }

  _init(model) {
    super._init(model);

    this._setField(this.field);
    this.widget('DisplayStyleField').setVisible(false);
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
    super._setField(field);
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    let browseAutoExpandAllField = this.widget('BrowseAutoExpandAllField');
    browseAutoExpandAllField.setValue(this.field.browseAutoExpandAll);
    browseAutoExpandAllField.on('propertyChange:value', event => this.field.setBrowseAutoExpandAll(event.newValue));

    let browseLoadIncrementalField = this.widget('BrowseLoadIncrementalField');
    browseLoadIncrementalField.setValue(this.field.browseLoadIncremental);
    browseLoadIncrementalField.on('propertyChange:value', event => this.field.setBrowseLoadIncremental(event.newValue));
  }
}
