/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Mode, ModeSelector, StaticLookupCall} from '@eclipse-scout/core';

export class ModeLookupCall extends StaticLookupCall<Mode> {
  modeSelector: ModeSelector;
  protected _rebuildDataHandler: () => void;

  constructor(modeSelector: ModeSelector) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setModeSelector(modeSelector);
  }

  protected override _data(): any[] {
    return this.data;
  }

  setModeSelector(modeSelector: ModeSelector) {
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

  protected _rebuildData() {
    this.data = this.modeSelector.modes.map(mode => {
      return [mode, mode.text];
    });
  }
}
