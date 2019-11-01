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
import TreeSmartFieldPropertiesBoxModel from './TreeSmartFieldPropertiesBoxModel';
import {models} from '@eclipse-scout/core';
import {SmartFieldPropertiesBox} from '../../index';

export default class TreeSmartFieldPropertiesBox extends SmartFieldPropertiesBox {

  constructor() {
    super();
    this.field = null;
  }


  _jsonModel() {
    return models.extend(TreeSmartFieldPropertiesBoxModel(this), super._jsonModel());
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

    var browseAutoExpandAllField = this.widget('BrowseAutoExpandAllField');
    browseAutoExpandAllField.setValue(this.field.browseAutoExpandAll);
    browseAutoExpandAllField.on('propertyChange', this._onBrowseAutoExpandAllPropertyChange.bind(this));

    var browseLoadIncrementalField = this.widget('BrowseLoadIncrementalField');
    browseLoadIncrementalField.setValue(this.field.browseLoadIncremental);
    browseLoadIncrementalField.on('propertyChange', this._onBrowseLoadIncrementalPropertyChange.bind(this));
  }

  _onBrowseAutoExpandAllPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.field.setBrowseAutoExpandAll(event.newValue);
    }
  }

  _onBrowseLoadIncrementalPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.field.setBrowseLoadIncremental(event.newValue);
    }
  }
}
