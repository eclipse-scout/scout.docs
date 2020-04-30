/*
 * Copyright (c) 2014-2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {App, scout, TileOutlineOverview} from '@eclipse-scout/core';

export default class WidgetsOutlineOverview extends TileOutlineOverview {

  constructor() {
    super();
  }

  _render() {
    super._render();
    this.$container.addClass('widgets-outline-overview');

    let title = this.session.text('AppWelcome');
    this.$title.text(title);

    let description = this.session.text('AppDescription', App.get().scoutVersion);
    this.$description = this.$content.appendDiv('widget-tile-outline-desc').html(description);
    this.$description.addClass('prevent-initial-focus');
  }

  _createPageTileGrid() {
    return scout.create('PageTileGrid', {
      parent: this,
      outline: this.outline,
      layoutConfig: {
        hgap: 12,
        vgap: 12,
        columnWidth: 150,
        rowHeight: 90
      }
    });
  }
}
