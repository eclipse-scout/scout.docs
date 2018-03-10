/*******************************************************************************
 * Copyright (c) 2014-2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
scout.WidgetsOutlineOverview = function() {
  scout.WidgetsOutlineOverview.parent.call(this);
};
scout.inherits(scout.WidgetsOutlineOverview, scout.TileOutlineOverview);

scout.WidgetsOutlineOverview.prototype._render = function() {
  scout.WidgetsOutlineOverview.parent.prototype._render.call(this);
  this.$container.addClass('widgets-outline-overview');

  var title = this.session.text('AppWelcome');
  this.$title.text(title);

  var description = this.session.text('AppDescription', scout.app.scoutVersion);
  this.$description = this.$content.appendDiv('widget-tile-outline-desc').html(description);
};

scout.WidgetsOutlineOverview.prototype._createPageTileGrid = function() {
  return scout.create('PageTileGrid', {
    parent: this,
    outline: this.outline,
    layoutConfig: {
      columnWidth: 150,
      rowHeight: 100
    }
  });
};
