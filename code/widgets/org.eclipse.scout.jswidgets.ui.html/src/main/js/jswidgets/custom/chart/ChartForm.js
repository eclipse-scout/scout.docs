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
jswidgets.ChartForm = function() {
  jswidgets.ChartForm.parent.call(this);
  this.chartField = null;
  this._dataSeriesCount = 5;
  this._chartType = jswidgets.Chart.type.PIE;
};
scout.inherits(jswidgets.ChartForm, scout.Form);



jswidgets.ChartForm.CHART_COLORS = [
  '#ebf5fb',
  '#d6eaf8',
  '#aed6f1',
  '#85c1e9',
  '#5dade2',
  '#3498db',
  '#2e86c1',
  '#2874a6',
  '#21618c',
  '#1b4f72'
];

jswidgets.ChartForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ChartForm');

};

jswidgets.ChartForm.prototype._init = function(model) {
  jswidgets.ChartForm.parent.prototype._init.call(this, model);
  this.chartField= this.widget('ChartFormField');
  this.chartField.setChartConfig(this._pieChartConfig(false));
  this.chartField.setValue(this._randomPieData());

  this.widget('RandomDataMenu').on('action', this._onRandomDataAction.bind(this));

  var dataSeriesField = this.widget('DataSeriesField');
  dataSeriesField.setValue(this._dataSeriesCount);
  dataSeriesField.on('propertyChange', this._onDataSeriesPropertyChange.bind(this));

  var chartTypeField = this.widget('ChartTypeField');
  chartTypeField.setValue(this._chartType);
  chartTypeField.on('propertyChange', this._onChartTypePropertyChange.bind(this));

  this.widget('FormFieldPropertiesBox').setField(this.chartField);
  this.widget('GridDataBox').setField(this.chartField);
  this.widget('WidgetActionsBox').setField(this.chartField);
  this.widget('EventsTab').setField(this.chartField);

};

jswidgets.ChartForm.prototype._onSelectedTabChange = function(event) {
  if (event.propertyName === 'value') {
    if (event.newValue) {
      this.widget('TabBox').selectTabById(event.newValue);
    } else {
      this.widget('TabBox').setSelectedTab();
    }
  }
};

jswidgets.ChartForm.prototype._onFieldPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    this.widget('SelectedTabField').setValue((event.newValue) ? (event.newValue.id) : (null));
  }
};

jswidgets.ChartForm.prototype._onDataSeriesPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this._dataSeriesCount = event.newValue;
    this.chartField.setChartConfig(this._pieChartConfig(false));
    this._updateDataSeries();
  }
};

jswidgets.ChartForm.prototype._onChartTypePropertyChange = function(event){
  if (event.propertyName === 'value') {
    this._chartType = event.newValue;
    this.chartField.setChartConfig(this._pieChartConfig(false));
    this._updateDataSeries();
  }
};

jswidgets.ChartForm.prototype._onRandomDataAction = function() {
  this._updateDataSeries();
};

jswidgets.ChartForm.prototype._updateDataSeries = function() {
  this.chartField.setValue(this._randomPieData());
};

jswidgets.ChartForm.prototype._pieChartConfig = function(withDataSeries) {
  var chartConfig = {
    type: this._chartType,
    options: {
      maintainAspectRatio: false,
      legend: {
        labels: {
          fontSize: 14
        }
      }
    }
  };
  if (withDataSeries) {
    chartConfig.data = this._randomPieData();
  }
  return chartConfig;
};

jswidgets.ChartForm.prototype._randomPieData = function() {
  var data = [];
  var names = [];
  for (var i = 0; i < this._dataSeriesCount; i++) {
    data.push(Math.floor(Math.random() * 8) + 2);
    names.push('serie: ' + i);
  }

  return {
    labels: names,
    datasets: [{
      backgroundColor: jswidgets.ChartForm.CHART_COLORS.slice(0, this._dataSeriesCount),
      data: data
    }]
  };
};
