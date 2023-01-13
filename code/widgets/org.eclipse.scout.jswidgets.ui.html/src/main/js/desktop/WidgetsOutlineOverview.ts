/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {HtmlComponent, PageTileGrid, scout, TileOutlineOverview} from '@eclipse-scout/core';
import {App} from '../index';

export class WidgetsOutlineOverview extends TileOutlineOverview {

  $description: JQuery;

  constructor() {
    super();
  }

  protected override _render() {
    super._render();
    this.$container.addClass('widgets-outline-overview');

    let title = this.session.text('AppWelcome');
    this.$title.text(title);

    let description = this.session.text('AppDescription', App.get().scoutVersion);
    this.$description = this.$content.appendDiv('widget-tile-outline-desc').html(description);
    this.$description.addClass('prevent-initial-focus');
    HtmlComponent.install(this.$description, this.session);
  }

  protected override _createPageTileGrid(): PageTileGrid {
    return scout.create(PageTileGrid, {
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

  protected override _updateTitle(animated = true) {
    // NOP
  }
}
