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
import {GroupBox, models} from '@eclipse-scout/core';
import SmartColumnPropertiesBoxModel from './SmartColumnPropertiesBoxModel';

export default class SmartColumnPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.column = null;
  }

  _jsonModel() {
    return models.get(SmartColumnPropertiesBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column) {
    this.setProperty('column', column);
  }

  _setColumn(column) {
    this._setProperty('column', column);
    if (!this.column) {
      return;
    }

    var browseMaxRowCountField = this.widget('BrowseMaxRowCountField');
    browseMaxRowCountField.setValue(this.column.browseMaxRowCount);
    browseMaxRowCountField.on('propertyChange', this._onPropertyChange.bind(this));
  }

  _onPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'BrowseMaxRowCountField') {
      this.column.setBrowseMaxRowCount(event.newValue);
    }
  }
}
