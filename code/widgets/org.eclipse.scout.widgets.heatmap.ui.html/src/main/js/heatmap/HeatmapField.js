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
import {Dimension, FormField, graphics, HtmlComponent, ObjectFactory} from '@eclipse-scout/core';
import {HeatmapFieldLayout} from '../index';

import * as L from 'leaflet';

export class HeatmapField extends FormField {

  constructor() {
    super();
  }

  _render() {
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
    // After the initial rendering, this condition is ensured by HeapmapFieldLayout.js.
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

  _renderProperties() {
    super._renderProperties();
    this._renderViewParameter();
    this._renderHeatPointList();
  }

  _remove() {
    super._remove();

    this.heatmap.remove();
    this.heatmap = null;
    this._heatLayer = null;
  }

  _onViewParameterChange() {
    this.trigger('viewParameterChange', {
      center: {
        x: this.heatmap.getCenter().lng,
        y: this.heatmap.getCenter().lat
      },
      zoomFactor: this.heatmap.getZoom()
    });
  }

  _onClick(event) {
    this.trigger('click', {
      point: {
        x: event.latlng.lng,
        y: event.latlng.lat
      }
    });
  }

  _renderViewParameter() {
    this.heatmap.setView([
      this.viewParameter.center.y,
      this.viewParameter.center.x
    ], this.viewParameter.zoomFactor);
  }

  _renderHeatPointList() {
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
    this._heatLayer = L.heatLayer(heatPoints, {
      // TODO [7.0] bsh: make this parameter list configurable from the model!
      // parameters to control the appearance of heat points
      // see leaflet.heat docu for full spec
      radius: 20,
      blur: 30,
      max: 1.0
    });
    this._heatLayer.addTo(this.heatmap);
  }

  addHeatPoint(point) {
    if (this._heatLayer) {
      this._heatLayer.addLatLng([
        point.y,
        point.x,
        point.intensity
      ]);
    }
  }
}
