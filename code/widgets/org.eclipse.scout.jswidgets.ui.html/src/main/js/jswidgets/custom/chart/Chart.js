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
jswidgets.Chart = function() {
  jswidgets.Chart.parent.call(this);
  this.chart = null;
  this.chartConfig = null;

  // default config
  this.config = {
    type: jswidgets.Chart.Type.PIE,
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
};
scout.inherits(jswidgets.Chart, scout.Widget);

jswidgets.Chart.Type = {
  PIE: 'pie',
  LINE: 'line',
  DOUGHNUT: 'doughnut',
  POLAR_AREA: 'polarArea'
};

jswidgets.Chart.prototype._init = function(model) {
  jswidgets.Chart.parent.prototype._init.call(this, model);
  this.config = $.extend(true, this.config, model.config);
};

jswidgets.Chart.prototype._render = function() {
  this.$container = this.$parent.appendElement('<canvas>', 'chart');
  this._renderConfig();
  this._renderData();
};

jswidgets.Chart.prototype.setData = function(data) {
  this.setProperty('data', data);
};

jswidgets.Chart.prototype._renderData = function() {
  if (!this.data) {
    return;
  }
  $.extend(true, this.config.data, this.data);
  this.chart.update();
};

jswidgets.Chart.prototype.setConfig = function(config) {
  this.setProperty('config', config);
};


jswidgets.Chart.prototype._renderConfig = function() {
  if (this.chart) {
    this.chart.destroy();
  }
  this.chart = new Chart(this.$container[0], this.config); // jshint ignore:line
};
