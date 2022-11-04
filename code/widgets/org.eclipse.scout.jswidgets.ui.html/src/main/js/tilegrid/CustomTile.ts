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
import {HtmlComponent, Tile, TileModel} from '@eclipse-scout/core';

export class CustomTile extends Tile implements CustomTileModel {
  declare model: CustomTileModel;
  declare self: CustomTile;

  label: string;

  constructor() {
    super();
    this.label = null;
    this.displayStyle = Tile.DisplayStyle.PLAIN;
  }

  protected override _render() {
    this.$container = this.$parent.appendDiv('custom-tile');
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
    this.$container.text(this.label);
  }
}

export interface CustomTileModel extends TileModel {
  label?: string;
}
