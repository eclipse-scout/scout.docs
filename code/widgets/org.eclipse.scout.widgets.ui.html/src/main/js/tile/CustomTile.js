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
import {HtmlComponent, Tile} from '@eclipse-scout/core';

export class CustomTile extends Tile {

  constructor() {
    super();
    this.label = null;
  }

  _render() {
    this.$container = this.$parent.appendDiv('custom-tile');
    this.htmlComp = HtmlComponent.install(this.$container, this.session);
  }

  _renderProperties() {
    super._renderProperties();
    this._renderLabel();
  }

  _renderLabel() {
    this.$container.text(this.label);
  }
}
