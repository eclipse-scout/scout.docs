/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Event, FormFieldAdapter} from '@eclipse-scout/core';
import {HeatmapField, HeatmapFieldClickEvent, HeatmapFieldViewParameterChangeEvent} from '../index';

export class HeatmapFieldAdapter extends FormFieldAdapter {
  declare widget: HeatmapField;

  protected override _onWidgetEvent(event: Event<HeatmapField>) {
    if (event.type === 'viewParameterChange') {
      this._onWidgetViewParameterChange(event as HeatmapFieldViewParameterChangeEvent);
    } else if (event.type === 'click') {
      this._onWidgetClick(event as HeatmapFieldClickEvent);
    } else {
      super._onWidgetEvent(event);
    }
  }

  protected _onWidgetViewParameterChange(event: HeatmapFieldViewParameterChangeEvent) {
    this._send('viewParameterChange', {
      center: event.center,
      zoomFactor: event.zoomFactor
    });
  }

  protected _onWidgetClick(event: HeatmapFieldClickEvent) {
    this._send('click', {
      point: event.point
    });
  }

  override onModelAction(event: any) {
    if (event.type === 'heatPointsAdded') {
      this._onHeatPointsAdded(event);
    } else {
      super.onModelAction(event);
    }
  }

  protected _onHeatPointsAdded(event: any) {
    event.points.forEach(point => {
      this.widget.addHeatPoint(point);
    });
  }
}
