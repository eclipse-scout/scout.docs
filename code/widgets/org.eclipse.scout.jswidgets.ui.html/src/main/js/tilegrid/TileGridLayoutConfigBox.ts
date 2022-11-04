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
import TileGridLayoutConfigBoxModel from './TileGridLayoutConfigBoxModel';
import {models} from '@eclipse-scout/core';
import {LogicalGridLayoutConfigBox} from '../index';

export default class TileGridLayoutConfigBox extends LogicalGridLayoutConfigBox {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.extend(TileGridLayoutConfigBoxModel, super._jsonModel());
  }

  _init(model) {
    super._init(model);

    this.widget('MaxWidthField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
  }

  initLayoutDefaults() {
    super.initLayoutDefaults();
    this.widget('MaxWidthField').setValue(this.getBodyLayout().maxWidth);
  }

  _fillLayoutConfigByEvent(layoutConfig, event) {
    super._fillLayoutConfigByEvent(layoutConfig, event);
    if (event.source.id === 'MaxWidthField') {
      layoutConfig.maxWidth = event.newValue;
    }
  }
}
