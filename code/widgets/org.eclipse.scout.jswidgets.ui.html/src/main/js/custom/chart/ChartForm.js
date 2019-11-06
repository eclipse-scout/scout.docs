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
import {Form, models} from '@eclipse-scout/core';
import ChartFormModel from './ChartFormModel';
import {Chart} from '../../index';

export default class ChartForm extends Form {

  constructor() {
    super();
    this.chartField = null;
    this._chartType = Chart.Type.PIE;
    this._dataSeriesCount = 1;
    this._dataArrayLength = 5;
  }

  static CHART_COLORS_LINE = ['rgba(41, 128, 185, .5)', 'rgba(23, 165, 137, .5)', 'rgba(243, 156, 18, .5)', 'rgba(241, 196, 15, .5)'];

  static CHART_COLORS_DONAT = [
    ['#ebf5fb', '#d6eaf8', '#aed6f1', '#85c1e9', '#5dade2', '#3498db', '#2e86c1', '#2874a6', '#21618c', '#1b4f72'],
    ['#e8f6f3', '#d0ece7', '#a2d9ce', '#73c6b6', '#45b39d', '#16a085', '#138d75', '#117a65', '#0e6655', '#0b5345']
  ];

  _jsonModel() {
    return models.get(ChartFormModel);

  }

  _init(model) {
    super._init(model);
    this.chartField = this.widget('ChartFormField');
    this.chartField.setChartConfig(this._pieChartConfig(false));
    this.chartField.setValue(this._randomPieData());

    this.widget('RandomDataMenu').on('action', this._onRandomDataAction.bind(this));

    var chartTypeField = this.widget('ChartTypeField');
    chartTypeField.setValue(this._chartType);
    chartTypeField.on('propertyChange', this._onChartTypePropertyChange.bind(this));

    var dataSeriesCountField = this.widget('DataSeriesCountField');
    dataSeriesCountField.setValue(this._dataSeriesCount);
    dataSeriesCountField.on('propertyChange', this._onDataSeriesCountPropertyChange.bind(this));

    var dataArrayLengthField = this.widget('DataArrayLengthField');
    dataArrayLengthField.setValue(this._dataArrayLength);
    dataArrayLengthField.on('propertyChange', this._onDataArrayLengthPropertyChange.bind(this));

    this.widget('FormFieldPropertiesBox').setField(this.chartField);
    this.widget('GridDataBox').setField(this.chartField);
    this.widget('WidgetActionsBox').setField(this.chartField);
    this.widget('EventsTab').setField(this.chartField);

  }

  _onSelectedTabChange(event) {
    if (event.propertyName === 'value') {
      if (event.newValue) {
        this.widget('TabBox').selectTabById(event.newValue);
      } else {
        this.widget('TabBox').setSelectedTab();
      }
    }
  }

  _onFieldPropertyChange(event) {
    if (event.propertyName === 'selectedTab') {
      this.widget('SelectedTabField').setValue((event.newValue) ? (event.newValue.id) : (null));
    }
  }

  _onChartTypePropertyChange(event) {
    if (event.propertyName === 'value') {
      this._chartType = event.newValue;
      this.chartField.setChartConfig(this._pieChartConfig(false));
      this._updateDataSeries();
    }
  }

  _onDataSeriesCountPropertyChange(event) {
    if (event.propertyName === 'value') {
      this._dataSeriesCount = event.newValue;
      this._updateDataSeries();
    }
  }

  _onDataArrayLengthPropertyChange(event) {
    if (event.propertyName === 'value') {
      this._dataArrayLength = event.newValue;
      this.chartField.setChartConfig(this._pieChartConfig(false));
      this._updateDataSeries();
    }
  }

  _onRandomDataAction() {
    this._updateDataSeries();
  }

  _updateDataSeries() {
    this.chartField.setValue(this._randomPieData());
  }

  _pieChartConfig(withDataSeries) {
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
  }

  _randomPieData() {
    var data = [];
    var datasets = [];
    var names = [];
    var i;

    // labels
    for (i = 0; i < this._dataArrayLength; i++) {
      names.push('serie: ' + i);
    }

    // datasets
    for (i = 0; i < this._dataSeriesCount; i++) {
      datasets.push(this._randomDataset(i));
    }

    return {
      labels: names,
      datasets: datasets
    };
  }

  _randomDataset(colorIndex) {
    var dataset = {
      label: 'dataset ' + colorIndex,
      backgroundColor: ChartForm.CHART_COLORS_DONAT[colorIndex].slice(0, this._dataArrayLength),
      data: []
    };
    if (this._chartType === Chart.Type.LINE || this._chartType === Chart.Type.POLAR_AREA) {
      dataset.backgroundColor = ChartForm.CHART_COLORS_LINE[colorIndex];
    }
    for (var i = 0; i < this._dataArrayLength; i++) {
      dataset.data.push(Math.floor(Math.random() * 8) + 2);
    }
    return dataset;
  }
}
