widgets.ChartField = function() {
  widgets.ChartField.parent.call(this);
  this.chart = null;
  this.chartData = null;
  this.chartConfig = null;
};
scout.inherits(widgets.ChartField, scout.FormField);

widgets.ChartField.prototype._init = function(model) {
  widgets.ChartField.parent.prototype._init.call(this, model);
  this._setChartConfig(this.chartConfig);
};

widgets.ChartField.prototype._render = function() {
  this.addContainer(this.$parent, 'chart-field');
  this.addLabel();
  this.addMandatoryIndicator();

  // The additional DIV is required because Chart.js has its own resize handler which
  // checks the parent element in the DOM to find its dimensions. And since we use absolute
  // positioning in the FormField layout, Chart.js would calculate a wrong size for the
  // canvas element.
  this.addFieldContainer(this.$parent.makeDiv());
  this.addField(this.$fieldContainer.appendElement('<canvas>', 'chart'));

  this.addStatus();
};

widgets.ChartField.prototype._renderProperties = function() {
  widgets.ChartField.parent.prototype._renderProperties.call(this);
  this._renderChartConfig();
  this._renderChartData();
};

widgets.ChartField.prototype._remove = function() {
  widgets.ChartField.parent.prototype._remove.call(this);
  if (this.chart) {
    this.chart.destroy();
    this.chart = null;
  }
};

widgets.ChartField.prototype._renderChartData = function() {
  if (!this.chartData) {
    return;
  }

  if (this.chartData.updateDatasets) {
    $.extend(true, this.chartConfig.data.datasets, this.chartData.datasets);
  } else {
    this.chartConfig.data.datasets = this.chartData.datasets;
  }

  this.chartConfig.data.labels = this.chartData.labels;
  this.chart.update();
};

widgets.ChartField.prototype.setChartConfig = function(chartConfig) {
  this.setProperty('chartConfig', chartConfig);
};

widgets.ChartField.prototype._setChartConfig = function(chartConfig) {
  if (typeof chartConfig === 'string') {
    chartConfig = JSON.parse(chartConfig);
  }
  this._setProperty('chartConfig', chartConfig);
};

widgets.ChartField.prototype._renderChartConfig = function() {
  if (this.chart) {
    this.chart.destroy();
  }
  this.chart = new Chart(this.$field[0], this.chartConfig); // jshint ignore:line
  this._renderChartData();
};
