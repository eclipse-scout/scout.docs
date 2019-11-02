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
import {scout, StringFieldLayout, ValueField} from '@eclipse-scout/core';

export default class ChartField extends ValueField {

  constructor() {
    super();
    this.chart = null;
    this.chartConfig = {
      type: 'pie',
      options: {
        maintainAspectRatio: false,
        legend: {
          labels: {
            fontSize: 14
          }
        }
      }
    };
  }


  _init(model) {
    this._onInit = true;
    super._init(model);
    this._onInit = false;
    this.chart = scout.create('jswidgets.Chart', {
      parent: this,
      config: this.chartConfig
    });
  }

  _render() {
    this.addContainer(this.$parent, 'chart-field', new StringFieldLayout(this));
    this.addLabel();
    this.addMandatoryIndicator();
    this.chart.render();

    this.addField(this.chart.$container);
    this.addStatus();
  }

  _valueChanged() {
    if (this._onInit) {
      return;
    }
    this.chart.setData(this.value);
  }


  setChartConfig(chartConfig) {
    this.chart.setConfig(chartConfig);
  }
}


