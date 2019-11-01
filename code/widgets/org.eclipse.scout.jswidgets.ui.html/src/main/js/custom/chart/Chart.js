/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Widget} from '@eclipse-scout/core';
import {Chart as ChartJs} from 'chart.js';
import * as $ from 'jquery';

export default class Chart extends Widget {

  constructor() {
    super();
    this.chart = null;
    this.chartConfig = null;

    // default config
    this.config = {
      type: Chart.Type.PIE,
      options: {
        elements: {
          arc: {
            borderWidth: 1
          }
        },
        layout: {
          padding: {
            left: 20,
            right: 20,
            top: 20,
            bottom: 20
          }
        }
      }
    };
  }


  static Type = {
    PIE: 'pie',
    LINE: 'line',
    DOUGHNUT: 'doughnut',
    POLAR_AREA: 'polarArea'
  };

  _init(model) {
    super._init(model);
    this.config = $.extend(true, this.config, model.config);
  }

  _render() {
    this.$container = this.$parent.appendElement('<canvas>', 'chart');
    this._renderConfig();
    this._renderData();
  }

  setData(data) {
    this.setProperty('data', data);
  }

  _renderData() {
    if (!this.data) {
      return;
    }
    $.extend(true, this.config.data, this.data);
    this.chart.update();
  }

  setConfig(config) {
    this.setProperty('config', config);
  }


  _renderConfig() {
    if (this.chart) {
      this.chart.destroy();
    }
    this.chart = new ChartJs(this.$container[0], this.config); // jshint ignore:line
  }
}
