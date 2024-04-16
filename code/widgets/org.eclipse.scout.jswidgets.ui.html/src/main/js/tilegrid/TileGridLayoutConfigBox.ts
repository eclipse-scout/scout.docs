/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import TileGridLayoutConfigBoxModel from './TileGridLayoutConfigBoxModel';
import {GroupBoxModel, InitModelOf, models, TileGridLayoutConfig, TileGridLayoutConfigModel} from '@eclipse-scout/core';
import {LogicalGridLayoutConfigBox, TileGridLayoutConfigBoxWidgetMap} from '../index';

export class TileGridLayoutConfigBox extends LogicalGridLayoutConfigBox {
  declare widgetMap: TileGridLayoutConfigBoxWidgetMap;

  protected override _jsonModel(): GroupBoxModel {
    return models.extend(TileGridLayoutConfigBoxModel, super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('MaxWidthField').on('propertyChange:value', event => this._updateLayoutConfig(event));
  }

  override initLayoutDefaults() {
    super.initLayoutDefaults();
    this.widget('MaxWidthField').setValue(this.getLayoutConfig().maxWidth);
  }

  override getLayoutConfig(): TileGridLayoutConfig {
    return super.getLayoutConfig() as TileGridLayoutConfig;
  }

  protected override _readLayoutConfig(): InitModelOf<TileGridLayoutConfigModel> {
    return {
      ...super._readLayoutConfig(),
      maxWidth: this.widget('MaxWidthField').value
    };
  }
}
