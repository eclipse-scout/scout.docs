import {FormField} from '@eclipse-scout/core';
import $ from 'jquery';

import {Chart} from 'chart.js';

export default class ChartField extends FormField {

  constructor() {
    super();
    this.chart = null;
    this.chartData = null;
    this.chartConfig = null;
  }


  _init(model) {
    super._init(model);
    this._setChartConfig(this.chartConfig);
  }

  _render() {
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
  }

  _renderProperties() {
    super._renderProperties();
    this._renderChartConfig();
    this._renderChartData();
  }

  _remove() {
    super._remove();
    if (this.chart) {
      this.chart.destroy();
      this.chart = null;
    }
  }

  _renderChartData() {
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
  }

  setChartConfig(chartConfig) {
    this.setProperty('chartConfig', chartConfig);
  }

  _setChartConfig(chartConfig) {
    if (typeof chartConfig === 'string') {
      chartConfig = JSON.parse(chartConfig);
    }
    this._setProperty('chartConfig', chartConfig);
  }

  _renderChartConfig() {
    if (this.chart) {
      this.chart.destroy();
    }
    this.chart = new Chart(this.$field[0], this.chartConfig); // jshint ignore:line
    this._renderChartData();
  }
}
