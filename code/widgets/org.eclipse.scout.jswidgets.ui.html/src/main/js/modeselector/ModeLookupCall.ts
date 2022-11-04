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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class ModeLookupCall extends StaticLookupCall {

  constructor(modeSelector) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setModeSelector(modeSelector);
  }

  _data() {
    return this.data;
  }

  setModeSelector(modeSelector) {
    if (this.modeSelector) {
      this.modeSelector.off('propertyChange:modes', this._rebuildDataHandler);
      this.modeSelector.modes.forEach(function(mode) {
        mode.off('propertyChange:text', this._rebuildDataHandler);
      }, this);
    }
    this.modeSelector = modeSelector;
    this.modeSelector.on('propertyChange:modes', this._rebuildDataHandler);
    this.modeSelector.modes.forEach(function(mode) {
      mode.on('propertyChange:text', this._rebuildDataHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.modeSelector.modes.map(mode => {
      return [mode, mode.text];
    });
  }
}
