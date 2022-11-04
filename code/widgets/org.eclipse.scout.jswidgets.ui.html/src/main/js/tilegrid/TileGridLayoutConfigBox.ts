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
import {FormField, GroupBoxModel, InitModelOf, models, PropertyChangeEvent, TileGridLayout, TileGridLayoutConfig} from '@eclipse-scout/core';
import {LogicalGridLayoutConfigBox, TileGridLayoutConfigBoxWidgetMap} from '../index';

export class TileGridLayoutConfigBox extends LogicalGridLayoutConfigBox {
  declare widgetMap: TileGridLayoutConfigBoxWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.extend(TileGridLayoutConfigBoxModel, super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('MaxWidthField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
  }

  override initLayoutDefaults() {
    super.initLayoutDefaults();
    this.widget('MaxWidthField').setValue(this.getBodyLayout().maxWidth);
  }

  override getBodyLayout(): TileGridLayout {
    return super.getBodyLayout() as TileGridLayout;
  }

  override getLayoutConfig(): TileGridLayoutConfig {
    return super.getLayoutConfig() as TileGridLayoutConfig;
  }

  protected override _fillLayoutConfigByEvent(layoutConfig: TileGridLayoutConfig, event: PropertyChangeEvent<any, FormField>) {
    super._fillLayoutConfigByEvent(layoutConfig, event);
    if (event.source.id === 'MaxWidthField') {
      layoutConfig.maxWidth = event.newValue;
    }
  }
}
