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
widgets.WidgetsOutline = function() {
  widgets.WidgetsOutline.parent.call(this);
};
scout.inherits(widgets.WidgetsOutline, scout.Outline);

widgets.WidgetsOutline.prototype._createOutlineOverview = function() {
  return scout.create('widgets.WidgetsTileOutlineOverview', {
    parent: this,
    outline: this
  });
};
