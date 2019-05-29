/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.ChartField = function() {
  jswidgets.ChartField.parent.call(this);
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
};

scout.inherits(jswidgets.ChartField, scout.ValueField);


jswidgets.ChartField.prototype._init = function(model) {
  this._onInit = true;
  jswidgets.ChartField.parent.prototype._init.call(this, model);
  this._onInit = false;
  this.chart = scout.create('jswidgets.Chart', {
    parent: this,
    config: this.chartConfig
  });
};

jswidgets.ChartField.prototype._render = function() {
  this.addContainer(this.$parent, 'string-field', new scout.StringFieldLayout(this));
  this.addLabel();
  this.addMandatoryIndicator();
  this.chart.render();

  this.addField(this.chart.$container);
  this.addStatus();
};

jswidgets.ChartField.prototype._valueChanged = function() {
  if(this._onInit){
    return;
  }
  this.chart.setData(this.value);
};


jswidgets.ChartField.prototype.setChartConfig = function(chartConfig){
  this.chart.setConfig(chartConfig);
};


