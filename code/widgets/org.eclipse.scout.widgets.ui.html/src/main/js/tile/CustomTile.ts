/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {HtmlComponent, Tile, TileModel} from '@eclipse-scout/core';

export class CustomTile extends Tile implements CustomTileModel {
  declare model: CustomTileModel;
  label: string;

  constructor() {
    super();
    this.label = null;
  }

  protected override _render() {
    this.$container = this.$parent.appendDiv('custom-tile');
    this.htmlComp = HtmlComponent.install(this.$container, this.session);
  }

  protected override _renderProperties() {
    super._renderProperties();
    this._renderLabel();
  }

  protected _renderLabel() {
    this.$container.text(this.label);
  }
}

export interface CustomTileModel extends TileModel {
  label?: string;
}
