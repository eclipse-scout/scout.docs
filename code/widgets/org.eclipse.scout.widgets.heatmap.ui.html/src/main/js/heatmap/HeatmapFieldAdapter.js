/*******************************************************************************
 * Copyright (c) 2014-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
scout.HeatmapFieldAdapter = function() {
  scout.HeatmapFieldAdapter.parent.call(this);
};
scout.inherits(scout.HeatmapFieldAdapter, scout.FormFieldAdapter);

scout.HeatmapFieldAdapter.prototype._onWidgetEvent = function(event) {
  if (event.type === 'viewParameterChange') {
    this._onWidgetViewParameterChange(event);
  } else if (event.type === 'click') {
    this._onWidgetClick(event);
  } else {
    scout.HeatmapFieldAdapter.parent.prototype._onWidgetEvent.call(this, event);
  }
};

scout.HeatmapFieldAdapter.prototype._onWidgetViewParameterChange = function(event) {
  this._send('viewParameterChanged', {
    center: event.center,
    zoomFactor: event.zoomFactor
  });
};

scout.HeatmapFieldAdapter.prototype._onWidgetClick = function(event) {
  this._send('clicked', {
    point: event.point
  });
};

scout.HeatmapFieldAdapter.prototype.onModelAction = function(event) {
  if (event.type === 'heatPointsAdded') {
    this._onHeatPointsAdded(event);
  } else {
    scout.HeatmapFieldAdapter.parent.prototype.onModelAction.call(this, event);
  }
};

scout.HeatmapFieldAdapter.prototype._onHeatPointsAdded = function(event) {
  event.points.forEach(function(point) {
    this.widget.addHeatPoint(point);
  }, this);
};
