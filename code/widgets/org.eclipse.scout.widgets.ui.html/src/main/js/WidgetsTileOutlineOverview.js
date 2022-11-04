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
import {HtmlComponent, scout, TileOutlineOverview} from '@eclipse-scout/core';

export class WidgetsTileOutlineOverview extends TileOutlineOverview {

  constructor() {
    super();
  }

  _render() {
    super._render();
    this.$container.addClass('widgets-outline-overview');

    this.$title.text(this.session.text('ApplicationTitle'));
    this.$description = this.$content.appendDiv('widgets-outline-desc').html(this.outline.description);
    this.$description.addClass('prevent-initial-focus');
    HtmlComponent.install(this.$description, this.session);
  }

  _createPageTileGrid() {
    let page;
    let nodes;
    if (this.outline.compact) {
      page = this.outline.compactRootNode();
      if (page) {
        nodes = page.childNodes;
      }
    }
    return scout.create('PageTileGrid', {
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

  _updateTitle() {
    // NOP
  }
}
