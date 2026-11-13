/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {InitModelOf, Mode, ModeSelector, StaticLookupCall, StaticLookupCallModel} from '@eclipse-scout/core';

export class ModeLookupCall extends StaticLookupCall<Mode> implements ModeLookupCallModel {
  declare model: ModeLookupCallModel;
  modeSelector: ModeSelector;
  protected _rebuildDataHandler: () => void;

  constructor() {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.setModeSelector(this.modeSelector);
  }

  setModeSelector(modeSelector: ModeSelector) {
    if (this.modeSelector) {
      this.modeSelector.off('propertyChange:modes', this._rebuildDataHandler);
      this.modeSelector.modes.forEach(mode => mode.off('propertyChange:text', this._rebuildDataHandler));
    }
    this.modeSelector = modeSelector;
    this.modeSelector.on('propertyChange:modes', this._rebuildDataHandler);
    this.modeSelector.modes.forEach(mode => mode.on('propertyChange:text', this._rebuildDataHandler));
    this._rebuildData();
  }

  protected _rebuildData() {
    this.data = this.modeSelector.modes.map(mode => {
      return [mode, mode.text];
    });
  }
}

export interface ModeLookupCallModel extends StaticLookupCallModel<Mode> {
  modeSelector?: ModeSelector;
}
