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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class ModeLookupCall extends StaticLookupCall {

  constructor(modeSelector) {
    super();
    this._modeSelectorPropertyChangeHandler = this._onModeSelectorPropertyChange.bind(this);
    this._modePropertyChangeHandler = this._onModePropertyChange.bind(this);
    this.data = [];
    this.setModeSelector(modeSelector);
  }

  _data() {
    return this.data;
  }

  setModeSelector(modeSelector) {
    if (this.modeSelector) {
      this.modeSelector.off('propertyChange', this._modeSelectorPropertyChangeHandler);
      this.modeSelector.modes.forEach(function(mode) {
        mode.off('propertyChange', this._modePropertyChangeHandler);
      }, this);
    }
    this.modeSelector = modeSelector;
    this.modeSelector.on('propertyChange', this._modeSelectorPropertyChangeHandler);
    this.modeSelector.modes.forEach(function(mode) {
      mode.on('propertyChange', this._modePropertyChangeHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.modeSelector.modes.map(function(mode) {
      return [mode, mode.text];
    });
  }

  _onModeSelectorPropertyChange(event) {
    if (event.propertyName === 'modes') {
      this._rebuildData();
    }
  }

  _onModePropertyChange(event) {
    if (event.propertyName === 'text') {
      this._rebuildData();
    }
  }
}
