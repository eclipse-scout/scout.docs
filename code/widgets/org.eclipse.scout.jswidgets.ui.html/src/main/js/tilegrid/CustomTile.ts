/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
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
  declare self: CustomTile;

  label: string;
  $label: JQuery;

  constructor() {
    super();
    this.label = null;
    this.displayStyle = Tile.DisplayStyle.PLAIN;
  }

  protected override _render() {
    this.$container = this.$parent.appendDiv('custom-tile');
    this.$label = this.$container.appendDiv('label');
    this.htmlComp = HtmlComponent.install(this.$container, this.session);
  }

  protected override _renderProperties() {
    super._renderProperties();
    this._renderLabel();
  }

  setLabel(label: string) {
    this.setProperty('label', label);
  }

  protected _renderLabel() {
    this.$label.text(this.label);
  }
}

export interface CustomTileModel extends TileModel {
  label?: string;
}
