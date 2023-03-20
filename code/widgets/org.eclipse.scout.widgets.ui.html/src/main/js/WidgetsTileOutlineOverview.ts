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
import {WidgetsOutline} from './WidgetsOutline';

export class WidgetsTileOutlineOverview extends TileOutlineOverview {
  declare outline: WidgetsOutline;
  $description: JQuery;

  protected override _render() {
    super._render();
    this.$container.addClass('widgets-outline-overview');

    this.$title.text(this.session.text('ApplicationTitle'));
    this.$description = this.$content.appendDiv('widgets-outline-desc').html(this.outline.description);
    this.$description.addClass('prevent-initial-focus');
    HtmlComponent.install(this.$description, this.session);
  }

  protected override _createPageTileGrid() {
    let page;
    let nodes;
    if (this.outline.compact) {
      page = this.outline.compactRootNode();
      if (page) {
        nodes = page.childNodes;
      }
    }
    return scout.create(PageTileGrid, {
      parent: this,
      outline: this.outline,
      compact: this.outline.compact,
      compactLayoutConfig: {
        hgap: 12,
        vgap: 12,
        columnWidth: 150,
        rowHeight: 90
      },
      page: page,
      nodes: nodes,
      layoutConfig: {
        hgap: 12,
        vgap: 12,
        columnWidth: 150,
        rowHeight: 90
      }
    });
  }

  protected override _updateTitle() {
    // NOP
  }
}
