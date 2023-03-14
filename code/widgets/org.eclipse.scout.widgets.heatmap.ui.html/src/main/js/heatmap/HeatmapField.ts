/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Dimension, FormField, graphics, HtmlComponent, ObjectFactory} from '@eclipse-scout/core';
import {HeatmapFieldEventMap, HeatmapFieldLayout, HeatmapFieldModel} from '../index';
import * as L from 'leaflet';
import {LeafletMouseEvent} from 'leaflet';

export class HeatmapField extends FormField implements HeatmapFieldModel {
  declare model: HeatmapFieldModel;
  declare eventMap: HeatmapFieldEventMap;
  declare self: HeatmapField;

  heatPointList: HeatPoint[];
  viewParameter: HeatmapViewParameter;
  heatmap: L.Map;
  _heatLayer: any;

  protected override _render() {
    this.addContainer(this.$parent, 'heatmap-field');
    this.addLabel();
    this.addStatus();

    let heatmapId = ObjectFactory.get().createUniqueId();
    let $field = this.$container
      .makeDiv('heatmap')
      .attr('id', heatmapId);
    this.addField($field);

    // Before (!) installing the layout, set the initial size to 1x1. The size of $field must not
    // get smaller than that, because Leaflet.js throws an error when the drawing canvas has size 0.
    // After the initial rendering, this condition is ensured by HeatmapFieldLayout.js.
    graphics.setSize($field, new Dimension(1, 1));
    let fieldHtmlComp = HtmlComponent.install($field, this.session);
    fieldHtmlComp.setLayout(new HeatmapFieldLayout(this));

    this.heatmap = L.map(heatmapId, {
      trackResize: false
    });
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.heatmap);

    this.heatmap.on('zoomend', this._onViewParameterChange.bind(this));
    this.heatmap.on('moveend', this._onViewParameterChange.bind(this));
    this.heatmap.on('click', this._onClick.bind(this));
    this.heatmap.on('contextmenu', this._onClick.bind(this));
  }

  protected override _renderProperties() {
    super._renderProperties();
    this._renderViewParameter();
    this._renderHeatPointList();
  }

  protected override _remove() {
    super._remove();

    this.heatmap.remove();
    this.heatmap = null;
    this._heatLayer = null;
  }

  protected _onViewParameterChange() {
    this.trigger('viewParameterChange', {
      center: {
        x: this.heatmap.getCenter().lng,
        y: this.heatmap.getCenter().lat
      },
      zoomFactor: this.heatmap.getZoom()
    });
  }

  protected _onClick(event: LeafletMouseEvent) {
    this.trigger('click', {
      point: {
        x: event.latlng.lng,
        y: event.latlng.lat
      }
    });
  }

  protected _renderViewParameter() {
    this.heatmap.setView([
      this.viewParameter.center.y,
      this.viewParameter.center.x
    ], this.viewParameter.zoomFactor);
  }

  protected _renderHeatPointList() {
    if (this._heatLayer) {
      this.heatmap.removeLayer(this._heatLayer);
    }
    let heatPoints = [];
    if (this.heatPointList) {
      this.heatPointList.forEach(point => {
        heatPoints.push([
          point.y,
          point.x,
          point.intensity
        ]);
      });
    }
    // @ts-expect-error no types available for leaflet.heat plugin
    this._heatLayer = L.heatLayer(heatPoints, {
      // parameters to control the appearance of heat points
      // see leaflet.heat docs for full spec
      radius: 20,
      blur: 30,
      max: 1.0
    });
    this._heatLayer.addTo(this.heatmap);
  }

  addHeatPoint(point: HeatPoint) {
    if (this._heatLayer) {
      this._heatLayer.addLatLng([
        point.y,
        point.x,
        point.intensity
      ]);
    }
  }
}

export interface MapPoint {
  x: number;
  y: number;
}

export interface HeatPoint extends MapPoint {
  intensity: number;
}

export interface HeatmapViewParameter {
  center: MapPoint;
  zoomFactor: number;
}
