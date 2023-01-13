/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormFieldAdapter} from '@eclipse-scout/core';

export class HeatmapFieldAdapter extends FormFieldAdapter {

  constructor() {
    super();
  }

  _onWidgetEvent(event) {
    if (event.type === 'viewParameterChange') {
      this._onWidgetViewParameterChange(event);
    } else if (event.type === 'click') {
      this._onWidgetClick(event);
    } else {
      super._onWidgetEvent(event);
    }
  }

  _onWidgetViewParameterChange(event) {
    this._send('viewParameterChange', {
      center: event.center,
      zoomFactor: event.zoomFactor
    });
  }

  _onWidgetClick(event) {
    this._send('click', {
      point: event.point
    });
  }

  onModelAction(event) {
    if (event.type === 'heatPointsAdded') {
      this._onHeatPointsAdded(event);
    } else {
      super.onModelAction(event);
    }
  }

  _onHeatPointsAdded(event) {
    event.points.forEach(function(point) {
      this.widget.addHeatPoint(point);
    }, this);
  }
}
