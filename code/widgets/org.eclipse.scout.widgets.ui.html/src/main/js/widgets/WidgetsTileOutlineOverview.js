/*
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
widgets.WidgetsTileOutlineOverview = function() {
  widgets.WidgetsTileOutlineOverview.parent.call(this);
};
scout.inherits(widgets.WidgetsTileOutlineOverview, scout.TileOutlineOverview);

widgets.WidgetsTileOutlineOverview.prototype._render = function() {
  widgets.WidgetsTileOutlineOverview.parent.prototype._render.call(this);
  this.$container.addClass('widgets-outline-overview');

  this.$title.text(this.session.text('ApplicationTitle'));
  this.$description = this.$content.appendDiv('widgets-outline-desc').html(this.outline.description);
  this.$description.addClass('prevent-initial-focus');
};

widgets.WidgetsTileOutlineOverview.prototype._createPageTileGrid = function() {
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
};
