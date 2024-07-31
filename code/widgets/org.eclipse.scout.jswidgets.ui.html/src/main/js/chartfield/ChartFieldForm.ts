/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {
  App, arrays, CancelMenu, CheckBoxField, colorSchemes, Column, DesktopNotification, Form, FormModel, GridData, GroupBox, InitModelOf, Menu, models, ModeSelectorField, NumberColumn, objects, OkMenu, scout, Status, StringField, strings,
  TableField
} from '@eclipse-scout/core';
import {Chart, ChartColorMode, ChartConfig, ChartData, ChartField, ChartPosition, ChartValueClickEvent, ChartValueGroup} from '@eclipse-scout/chart';
import ChartFieldFormModel from './ChartFieldFormModel';
import {ChartDataTableFieldTable, ChartFieldFormWidgetMap, EventsTab, FormFieldPropertiesBox, GridDataBox, ValuesProviderLookupCall, WidgetActionsBox} from '../index';

export class ChartFieldForm extends Form {
  declare widgetMap: ChartFieldFormWidgetMap;

  chart: Chart;
  fieldChart: Chart;
  tileChart: Chart;
  chartConfig: Partial<ChartConfig>;
  customChartConfig: Partial<ChartConfig>;
  chartField: ChartField;
  tileChartField: ChartField;
  autoColorCheckBox: CheckBoxField;
  colorModeSelectorField: ModeSelectorField<ChartColorMode>;
  formFieldPropertiesBox: FormFieldPropertiesBox;
  gridDataBox: GridDataBox;
  widgetActionsBox: WidgetActionsBox;
  eventsTab: EventsTab;
  numberOfVennCircles: number;
  numberOfDatasets: number;
  valuesProvider: number;
  random: boolean;
  fillTable: boolean;
  dataLabels: string[];
  chartDataTableField: TableField;
  chartDataTable: ChartDataTableFieldTable;
  datasetLabelColumn: Column;
  removeDataMenu: Menu;
  fulfillmentStartValuePropertyCheckbox: CheckBoxField;

  constructor() {
    super();
    this.chart = null;
    this.fieldChart = null;
    this.tileChart = null;

    this.chartConfig = {};
    this.customChartConfig = {};

    this.chartField = null;
    this.tileChartField = null;

    this.autoColorCheckBox = null;
    this.colorModeSelectorField = null;

    this.formFieldPropertiesBox = null;
    this.gridDataBox = null;
    this.widgetActionsBox = null;
    this.eventsTab = null;

    this.numberOfVennCircles = ChartFieldForm.DEFAULT_NUMBER_OF_VENN_CIRCLES;
    this.numberOfDatasets = ChartFieldForm.DEFAULT_NUMBER_OF_DATASETS;

    this.valuesProvider = -1;
    this.random = true;
    this.fillTable = true;
    this.dataLabels = [];

    this.chartDataTableField = null;
    this.chartDataTable = null;
    this.datasetLabelColumn = null;
    this.removeDataMenu = null;

    this.fulfillmentStartValuePropertyCheckbox = null;
  }

  static DEFAULT_NUMBER_OF_VENN_CIRCLES = 3;
  static DEFAULT_NUMBER_OF_DATASETS = 6;

  static MAX_INTEGER = 2147483647; // 2^32 - 1
  static MIN_INTEGER = -2147483647; // - (2^32 - 1)

  protected override _jsonModel(): FormModel {
    return models.get(ChartFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.rootGroupBox.visitFields(field => field.setStatusVisible(false));
    this.chartField = this.widget('ChartField');
    this.fieldChart = this.chartField.chart;
    this.chart = this.fieldChart;
    this.chartConfig = this.chart.config;

    let chartTileBox = this.widget('ChartTileBox');
    let chartTile = this.widget('ChartTile');
    this.tileChartField = this.widget('TileChartField');
    this.tileChart = this.tileChartField.chart;
    this.tileChart.on('valueClick', this._onChartValueClick.bind(this));

    this.fieldChart.on('propertyChange:config', event => {
      let colorScheme = ((event.newValue || {}).options || {}).colorScheme;
      if (colorScheme && typeof colorScheme === 'object') {
        colorScheme = $.extend(true, {}, colorScheme);
      }
      chartTile.setColorScheme(colorScheme);
    });
    this.fieldChart.on('valueClick', this._onChartValueClick.bind(this));

    this.autoColorCheckBox = this.widget('AutoColorCheckBox');
    this.autoColorCheckBox.setValue(this._getChartConfig().options?.autoColor);
    this.autoColorCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          autoColor: event.newValue
        }
      });
      this._setChartConfig(config);
      this.colorModeSelectorField.modeSelector.findModeByRef(ChartColorMode.AUTO).setText(event.newValue ? 'Auto' : 'Element');
      if (!event.newValue) {
        this._renewData();
      }
    });

    this.colorModeSelectorField = this.widget('ColorModeSelectorField');
    this.colorModeSelectorField.modeSelector.selectModeByRef(ChartColorMode.AUTO);
    this.colorModeSelectorField.setValue(ChartColorMode.AUTO);
    this.colorModeSelectorField.on('propertyChange:value', event => {
      const config = $.extend(true, {}, this._getChartConfig(), {
        options: {
          colorMode: event.newValue
        }
      });
      this._setChartConfig(config);
      if (!this.autoColorCheckBox.value) {
        this._renewData();
      }
    });

    let clickableCheckBox = this.widget('ClickableCheckBox');
    clickableCheckBox.setValue((this._getChartConfig().options || {}).clickable);
    clickableCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          clickable: event.newValue
        }
      });
      this._setChartConfig(config);
    });

    let checkableCheckBox = this.widget('CheckableCheckBox');
    checkableCheckBox.setValue((this._getChartConfig().options || {}).checkable);
    checkableCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          checkable: event.newValue
        }
      });
      this._setChartConfig(config);
    });

    let animatedCheckBox = this.widget('AnimatedCheckBox');
    animatedCheckBox.setValue(((this._getChartConfig().options || {}).animation || {}).duration > 0);
    animatedCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          animation: {
            duration: event.newValue ? Chart.DEFAULT_ANIMATION_DURATION : 0
          }
        }
      });
      this._setChartConfig(config);
    });

    let legendVisibleBox = this.widget('LegendVisibleBox');
    legendVisibleBox.setValue((((this._getChartConfig().options || {}).plugins || {}).legend || {}).display);
    legendVisibleBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          plugins: {
            legend: {
              display: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let legendClickableCheckBox = this.widget('LegendClickableCheckBox');
    legendClickableCheckBox.setValue((((this._getChartConfig().options || {}).plugins || {}).legend || {}).clickable);
    legendClickableCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          plugins: {
            legend: {
              clickable: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    const legendPointsVisibleCheckBox = this.widget('LegendPointsVisibleCheckBox');
    legendPointsVisibleCheckBox.setValue(this._getChartConfig().options?.plugins?.legend?.pointsVisible);
    legendPointsVisibleCheckBox.on('propertyChange:value', event => {
      const config = $.extend(true, {}, this._getChartConfig(), {
        options: {
          plugins: {
            legend: {
              pointsVisible: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let tooltipsEnabledBox = this.widget('TooltipsEnabledBox');
    tooltipsEnabledBox.setValue((((this._getChartConfig().options || {}).plugins || {}).tooltip || {}).enabled as boolean);
    tooltipsEnabledBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          plugins: {
            tooltip: {
              enabled: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let datalabelsVisibleCheckBox = this.widget('DatalabelsVisibleCheckBox');
    datalabelsVisibleCheckBox.setValue((((this._getChartConfig().options || {}).plugins || {}).datalabels || {}).display as boolean);
    datalabelsVisibleCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          plugins: {
            datalabels: {
              display: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let xAxisStackedCheckBox = this.widget('XAxisStackedCheckBox');
    xAxisStackedCheckBox.setValue((((this._getChartConfig().options || {}).scales || {}).x || {}).stacked as boolean);
    xAxisStackedCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          scales: {
            x: {
              stacked: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let yAxisStackedCheckBox = this.widget('YAxisStackedCheckBox');
    yAxisStackedCheckBox.setValue((((this._getChartConfig().options || {}).scales || {}).y || {}).stacked as boolean);
    yAxisStackedCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          scales: {
            y: {
              stacked: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let fillCheckBox = this.widget('FillCheckBox');
    fillCheckBox.setValue((((this._getChartConfig().options || {}).elements || {}).line || {}).fill as boolean);
    fillCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          elements: {
            line: {
              fill: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });

    let transparentCheckBox = this.widget('TransparentCheckBox');
    transparentCheckBox.setValue((this._getChartConfig().options || {}).transparent);
    transparentCheckBox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          transparent: event.newValue
        }
      });
      this._setChartConfig(config);
    });

    let accordingToValuesCheckbox = this.widget('AccordingToValuesCheckbox');
    accordingToValuesCheckbox.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          salesfunnel: {
            normalized: event.newValue
          }
        }
      });
      this._setChartConfig(config);
    });
    accordingToValuesCheckbox.setValue(((this._getChartConfig().options || {}).salesfunnel || {}).normalized || false);

    this.fulfillmentStartValuePropertyCheckbox = this.widget('FulfillmentStartValuePropertyCheckbox');

    let tileCheckBox = this.widget('TileCheckBox');
    tileCheckBox.on('propertyChange:value', event => {
      let oldVisibleField = event.newValue ? this.chartField : this.tileChartField;
      let newVisibleField = event.newValue ? this.tileChartField : this.chartField;
      let oldVisibleBox = event.newValue ? this.chartField : chartTileBox;
      let newVisibleBox = event.newValue ? chartTileBox : this.chartField;

      newVisibleField.setEnabled(oldVisibleField.enabled);
      newVisibleField.setVisible(oldVisibleField.visible);
      newVisibleField.setLabelVisible(oldVisibleField.labelVisible);
      newVisibleField.setStatusVisible(oldVisibleField.statusVisible);
      newVisibleField.setMandatory(oldVisibleField.mandatory);
      newVisibleField.setLoading(oldVisibleField.loading);
      newVisibleField.setLabelHtmlEnabled(oldVisibleField.labelHtmlEnabled);
      newVisibleField.setInheritAccessibility(oldVisibleField.inheritAccessibility);
      newVisibleField.setFieldStyle(oldVisibleField.fieldStyle);
      newVisibleField.setDisabledStyle(oldVisibleField.disabledStyle);
      newVisibleField.setLabel(oldVisibleField.label);
      newVisibleField.setLabelPosition(oldVisibleField.labelPosition);
      newVisibleField.setLabelWidthInPixel(oldVisibleField.labelWidthInPixel);
      newVisibleField.setTooltipText(oldVisibleField.tooltipText);
      newVisibleField.setTooltipAnchor(oldVisibleField.tooltipAnchor);
      let errorStatus = oldVisibleField.errorStatus;
      if (errorStatus) {
        newVisibleField.setErrorStatus(errorStatus);
      } else {
        newVisibleField.clearErrorStatus();
      }
      newVisibleField.setStatusPosition(oldVisibleField.statusPosition);
      newVisibleBox.setGridDataHints(new GridData(oldVisibleBox.gridDataHints));

      this.formFieldPropertiesBox.setField(newVisibleField);
      this.gridDataBox.setField(newVisibleBox);
      this.widgetActionsBox.setField(newVisibleField);

      oldVisibleField.setVisible(false);
      chartTileBox.setVisible(event.newValue);

      let oldChart = event.newValue ? this.fieldChart : this.tileChart;
      let newChart = event.newValue ? this.tileChart : this.fieldChart;

      newChart.setData(oldChart.data);

      let chartConfig = this._getChartConfig();
      chartConfig = $.extend(true, {}, chartConfig, {
        options: {
          colorScheme: {
            tile: event.newValue
          }
        }
      });
      this._setChartConfig(chartConfig);
      newChart.setConfig(this._getCombinedConfig());

      this.eventsTab.setField(newChart);

      this.chart = newChart;
    });

    let colorSchemeField = this.widget('ColorSchemeField');
    colorSchemeField.on('propertyChange:value', event => {
      let config = this._getChartConfig(),
        colorScheme = colorSchemes.ensureColorScheme(event.newValue, tileCheckBox.value);
      config = $.extend(true, {}, config, {
        options: {
          colorScheme: colorScheme
        }
      });
      this._setChartConfig(config);

      chartTile.setColorScheme(colorScheme);
    });
    colorSchemeField.setValue(colorSchemes.ColorSchemeId.DEFAULT);

    let tensionField = this.widget('TensionField');
    tensionField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        tensionField.setValue(0);
        return;
      }
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          elements: {
            line: {
              tension: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });
    tensionField.setValue((((this._getChartConfig().options || {}).elements || {}).line || {}).tension as number || 0);

    let greenAreaPositionField = this.widget('GreenAreaPositionField');
    greenAreaPositionField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        greenAreaPositionField.setValue(Chart.Position.CENTER);
        return;
      }
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          speedo: {
            greenAreaPosition: event.newValue
          }
        }
      });
      this._setChartConfig(config);
    });
    greenAreaPositionField.setValue(((this._getChartConfig().options || {}).speedo || {}).greenAreaPosition || Chart.Position.CENTER);

    let sizeOfLargestBubbleField = this.widget('SizeOfLargestBubbleField');
    sizeOfLargestBubbleField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        sizeOfLargestBubbleField.setValue(25);
        return;
      }
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          bubble: {
            sizeOfLargestBubble: event.newValue
          }
        }
      });
      this._setChartConfig(config);
    });
    sizeOfLargestBubbleField.setValue(((this._getChartConfig().options || {}).bubble || {}).sizeOfLargestBubble || 25);

    let minBubbleSizeField = this.widget('MinBubbleSizeField');
    minBubbleSizeField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        minBubbleSizeField.setValue(0);
        return;
      }
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          bubble: {
            minBubbleSize: event.newValue
          }
        }
      });
      this._setChartConfig(config);
    });
    minBubbleSizeField.setValue(((this._getChartConfig().options || {}).bubble || {}).minBubbleSize || 0);

    let legendPositionField = this.widget('LegendPositionField');
    legendPositionField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        legendPositionField.setValue(Chart.Position.RIGHT);
        return;
      }
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, {
        options: {
          plugins: {
            legend: {
              position: event.newValue
            }
          }
        }
      });
      this._setChartConfig(config);
    });
    legendPositionField.setValue((((this._getChartConfig().options || {}).plugins || {}).legend || {}).position as ChartPosition || Chart.Position.RIGHT);

    let customChartPropertiesField = this.widget('CustomChartPropertiesField');
    customChartPropertiesField.addValidator(value => {
      let parsedValue;
      // Parse input
      if (strings.hasText(value)) {
        try {
          parsedValue = JSON.parse(value);
        } catch (err) {
          throw '' + err; // JSON syntax error
        }
      }
      // Format parsed value
      let formatted = '';
      if (parsedValue) {
        formatted = JSON.stringify(parsedValue, null, 2);
      }
      if (!formatted || objects.isPlainObject(parsedValue)) {
        return formatted;
      }
      throw 'Expected JSON type: Object {}';
    });
    customChartPropertiesField.on('propertyChange:value', event => {
      if (strings.empty(event.newValue)) {
        this._setCustomChartConfig({});
        return;
      }
      this._setCustomChartConfig(JSON.parse(event.newValue));
    });

    let insertExampleChartPropertiesMenu = this.widget('InsertExampleChartPropertiesMenu');
    insertExampleChartPropertiesMenu.on('action', event => {
      customChartPropertiesField.setValue(JSON.stringify({options: {}}));
    });

    this.formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
    this.formFieldPropertiesBox.setField(this.chartField);
    this.gridDataBox = this.widget('GridDataBox');
    this.gridDataBox.setField(this.chartField);

    this.chartDataTableField = this.widget('ChartDataTableField');
    this.chartDataTable = this.widget('ChartDataTableField.Table');
    this.datasetLabelColumn = this.chartDataTable.columnById('DatasetLabelColumn');

    this.chartDataTable.on('completeCellEdit', event => {
      if (objects.isNullOrUndefined(event.field.value)) {
        let column = event.column,
          row = event.row,
          value: number | string = 0;
        if (column === this.datasetLabelColumn) {
          let rowIndex = this.chartDataTable.rows.indexOf(row);
          value = 'Dataset ' + (rowIndex + 1);
        }
        event.field.setValue(value);
      }
    });
    this.chartDataTable.on('rowsUpdated', event => this._renewData());

    let addDatasetMenu = this.widget('AddDatasetMenu');
    addDatasetMenu.on('action', event => {
      let table = this.chartDataTable;
      table.insertRow({cells: (['Dataset ' + (table.rows.length + 1)] as ChartDataRowRaw).concat(arrays.init(table.columns.length - 1, 0))});
      this._renewData();
    });

    let removeDatasetMenu = this.widget('RemoveDatasetMenu');
    removeDatasetMenu.on('action', event => {
      let table = this.chartDataTable;
      table.deleteRows(table.selectedRows);
      this._renewData();
    });

    let addDataMenu = this.widget('AddDataMenu');
    addDataMenu.on('action', event => {
      let inputForm = scout.create(Form, {
        parent: this,
        title: 'Data label',
        rootGroupBox: {
          objectType: GroupBox,
          borderDecoration: 'empty',
          menus: [
            {
              id: 'OkMenu',
              objectType: OkMenu
            },
            {
              id: 'CancelMenu',
              objectType: CancelMenu
            }
          ],
          fields: [{
            id: 'InputField',
            objectType: StringField,
            label: 'Data label',
            labelVisible: false,
            mandatory: true
          }]
        }
      });
      inputForm.open()
        .catch((...args) => App.get().errorHandler.handle(...args));
      inputForm.whenSave()
        .then(() => this._addColumn(inputForm.widget('InputField', StringField).value))
        .catch((...args) => App.get().errorHandler.handle(...args));
    });

    this.removeDataMenu = this.widget('RemoveDataMenu');
    this.removeDataMenu.on('action', event => {
      this._removeColumn();
    });

    let fillTableCheckBox = this.widget('FillTableCheckBox');
    fillTableCheckBox.on('propertyChange:value', event => {
      this.fillTable = event.newValue;

      if (this.fillTable) {
        this._renewData();
      }
    });
    fillTableCheckBox.setValue(this.fillTable);

    let randomDataButton = this.widget('RandomDataButton');
    randomDataButton.on('click', event => this._renewData());

    let valuesProviderField = this.widget('ValuesProviderField');
    valuesProviderField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        valuesProviderField.setValue(ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM);
        return;
      }
      this.valuesProvider = event.newValue;
      this._renewData();
    });
    valuesProviderField.setValue(ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM);

    let numberOfDatasetsField = this.widget('NumberOfDatasetsField');
    numberOfDatasetsField.setValue(this._getChartConfig().type === Chart.Type.VENN ? this.numberOfVennCircles : this.numberOfDatasets);
    numberOfDatasetsField.on('propertyChange:value', event => {
      if (objects.isNullOrUndefined(event.newValue)) {
        numberOfDatasetsField.setValue(this._getChartConfig().type === Chart.Type.VENN ? this.numberOfVennCircles : this.numberOfDatasets);
        return;
      }
      if (this._getChartConfig().type === Chart.Type.VENN) {
        this.numberOfVennCircles = event.newValue;
      } else {
        this.numberOfDatasets = event.newValue;
      }
      this._renewData();
    });

    let randomNumberOfDatasetsMenu = this.widget('RandomNumberOfDatasetsMenu');
    randomNumberOfDatasetsMenu.on('action', event => {
      numberOfDatasetsField.setValue(Math.max(Math.floor(Math.random() * ChartFieldForm.DEFAULT_NUMBER_OF_DATASETS * 2), 1));
    });

    let maxSegmentsField = this.widget('MaxSegmentsField');
    maxSegmentsField.on('propertyChange:value', event => {
      let config = this._getChartConfig();
      if (objects.isNullOrUndefined(event.newValue)) {
        maxSegmentsField.setValue((config.options || {}).maxSegments || 1);
        return;
      }

      config = $.extend(true, {}, config, {
        options: {
          maxSegments: event.newValue
        }
      });
      this._setChartConfig(config);
    });
    maxSegmentsField.setValue((this._getChartConfig().options || {}).maxSegments || 1);

    this.widgetActionsBox = this.widget('WidgetActionsBox');
    this.widgetActionsBox.setField(this.chartField);
    this.eventsTab = this.widget('EventsTab');
    this.eventsTab.setField(this.chart);

    let chartTypeField = this.widget('ChartTypeField');
    chartTypeField.on('propertyChange:value', event => {
      let config = this._getChartConfig(),
        type = event.newValue;

      checkableCheckBox.setEnabled(!scout.isOneOf(type, Chart.Type.SALESFUNNEL, Chart.Type.FULFILLMENT, Chart.Type.SPEEDO, Chart.Type.VENN));
      legendVisibleBox.setEnabled(!scout.isOneOf(type, Chart.Type.SALESFUNNEL, Chart.Type.FULFILLMENT, Chart.Type.SPEEDO, Chart.Type.VENN));
      legendClickableCheckBox.setEnabled(!scout.isOneOf(type, Chart.Type.SALESFUNNEL, Chart.Type.FULFILLMENT, Chart.Type.SPEEDO, Chart.Type.VENN));
      legendPointsVisibleCheckBox.setEnabled(!scout.isOneOf(type, Chart.Type.SALESFUNNEL, Chart.Type.FULFILLMENT, Chart.Type.SPEEDO, Chart.Type.VENN));
      datalabelsVisibleCheckBox.setEnabled(!scout.isOneOf(type, Chart.Type.SALESFUNNEL, Chart.Type.FULFILLMENT, Chart.Type.SPEEDO, Chart.Type.VENN));
      xAxisStackedCheckBox.setEnabled(scout.isOneOf(type, Chart.Type.BAR, Chart.Type.BAR_HORIZONTAL, Chart.Type.LINE, Chart.Type.COMBO_BAR_LINE));
      yAxisStackedCheckBox.setEnabled(scout.isOneOf(type, Chart.Type.BAR, Chart.Type.BAR_HORIZONTAL, Chart.Type.LINE, Chart.Type.COMBO_BAR_LINE));
      fillCheckBox.setEnabled(scout.isOneOf(type, Chart.Type.LINE, Chart.Type.COMBO_BAR_LINE, Chart.Type.RADAR));
      transparentCheckBox.setEnabled(scout.isOneOf(type, Chart.Type.BAR, Chart.Type.BAR_HORIZONTAL, Chart.Type.COMBO_BAR_LINE));
      accordingToValuesCheckbox.setEnabled(scout.isOneOf(type, Chart.Type.SALESFUNNEL));
      this.fulfillmentStartValuePropertyCheckbox.setEnabled(scout.isOneOf(type, Chart.Type.FULFILLMENT));
      tensionField.setEnabled(scout.isOneOf(type, Chart.Type.LINE, Chart.Type.COMBO_BAR_LINE, Chart.Type.RADAR));
      greenAreaPositionField.setEnabled(scout.isOneOf(type, Chart.Type.SPEEDO));
      sizeOfLargestBubbleField.setEnabled(scout.isOneOf(type, Chart.Type.BUBBLE));
      minBubbleSizeField.setEnabled(scout.isOneOf(type, Chart.Type.BUBBLE));
      legendPositionField.setEnabled(!scout.isOneOf(type, Chart.Type.SALESFUNNEL, Chart.Type.FULFILLMENT, Chart.Type.SPEEDO, Chart.Type.VENN));
      this.chartDataTableField.setTooltipText(scout.isOneOf(type, Chart.Type.COMBO_BAR_LINE) ? 'Add \'|line\' to a dataset label if the dataset should be displayed as line.' : null);
      valuesProviderField.setEnabled(scout.isOneOf(type, Chart.Type.LINE, Chart.Type.BAR, Chart.Type.BAR_HORIZONTAL, Chart.Type.COMBO_BAR_LINE, Chart.Type.BUBBLE, Chart.Type.SCATTER));
      maxSegmentsField.setEnabled(scout.isOneOf(type, Chart.Type.PIE, Chart.Type.DOUGHNUT, Chart.Type.POLAR_AREA, Chart.Type.RADAR));

      config = $.extend(true, {}, config, {
        type: type
      });

      config = $.extend(true, {}, config, {
        options: {
          scales: {
            x: {},
            y: {}
          }
        }
      });

      if (!xAxisStackedCheckBox.enabled && !yAxisStackedCheckBox.enabled) {
        config.options.scales = {};
      } else {
        config.options.scales.x.stacked = xAxisStackedCheckBox.enabled && !objects.isNullOrUndefined(xAxisStackedCheckBox.value) ? xAxisStackedCheckBox.value : false;
        config.options.scales.y.stacked = yAxisStackedCheckBox.enabled && !objects.isNullOrUndefined(yAxisStackedCheckBox.value) ? yAxisStackedCheckBox.value : false;
      }

      config.options.transparent = transparentCheckBox.enabled ? transparentCheckBox.value : null;

      this._setChartConfig(config);

      this.chartDataTable.columns
        .filter(column => column !== this.datasetLabelColumn)
        .forEach((column: NumberColumn) => {
          column.maxValue = this._getMaxValue();
          column.minValue = this._getMinValue();
        });

      this._renewData();

      if (scout.isOneOf(type, Chart.Type.PIE,
        Chart.Type.BAR,
        Chart.Type.BAR_HORIZONTAL,
        Chart.Type.LINE,
        Chart.Type.COMBO_BAR_LINE,
        Chart.Type.SALESFUNNEL,
        Chart.Type.DOUGHNUT,
        Chart.Type.POLAR_AREA,
        Chart.Type.RADAR,
        Chart.Type.BUBBLE,
        Chart.Type.SCATTER)) {
        numberOfDatasetsField.setEnabled(this.random);
        numberOfDatasetsField.setMinValue(0);
        numberOfDatasetsField.setMaxValue(null);
        numberOfDatasetsField.setValue(this.numberOfDatasets);
        randomNumberOfDatasetsMenu.setEnabled(true);
      } else if (type === Chart.Type.VENN) {
        numberOfDatasetsField.setEnabled(this.random);
        numberOfDatasetsField.setMinValue(1);
        numberOfDatasetsField.setMaxValue(3);
        numberOfDatasetsField.setValue(this.numberOfVennCircles);
        randomNumberOfDatasetsMenu.setEnabled(false);
      } else {
        numberOfDatasetsField.setEnabled(false);
        randomNumberOfDatasetsMenu.setEnabled(false);
      }
    });
    chartTypeField.setValue(this._getChartConfig().type);

    let randomCheckBox = this.widget('RandomCheckBox');
    randomCheckBox.on('propertyChange:value', event => {
      this.random = event.newValue;

      this.chartDataTableField.setEnabled(!this.random);
      randomDataButton.setEnabled(this.random);
      chartTypeField.triggerPropertyChange('value', chartTypeField.value, chartTypeField.value);
      if (this.random) {
        this.chartDataTableField.clearErrorStatus();
      } else {
        valuesProviderField.setEnabled(false);
        numberOfDatasetsField.setEnabled(false);
      }
    });
    randomCheckBox.setValue(this.random);
  }

  protected _onChartValueClick(event: ChartValueClickEvent) {
    let clickedItem = event.data;
    this.session.desktop.addNotification(scout.create(DesktopNotification, {
      parent: this,
      duration: 2000,
      closable: true,
      htmlEnabled: true,
      status: Status.info('<h4>VALUE_CLICK event</h4><ul>'
        + (!objects.isNullOrUndefined(clickedItem.xIndex) ? '<li>xIndex: ' + clickedItem.xIndex + '</li>' : '')
        + (!objects.isNullOrUndefined(clickedItem.yIndex) ? '<li>yIndex: ' + clickedItem.yIndex + '</li>' : '')
        + (!objects.isNullOrUndefined(clickedItem.datasetIndex) ? '<li>datasetIndex: ' + clickedItem.datasetIndex + '</li>' : '')
        + (!objects.isNullOrUndefined(clickedItem.dataIndex) ? '<li>dataIndex: ' + clickedItem.dataIndex + '</li>' : '')
        + '</ul>')
    }));
  }

  protected _renewData() {
    if (this.loading) {
      return;
    }

    let chartData, chartBean;

    switch (this._getChartConfig().type) {
      case Chart.Type.FULFILLMENT:
        chartData = this._getFulfillmentChartData();
        chartBean = this._getFulfillmentChartBean(chartData);
        break;
      case Chart.Type.LINE:
      case Chart.Type.BAR:
      case Chart.Type.BAR_HORIZONTAL:
        chartData = this._getBarLineChartData(false);
        chartBean = this._getBarLineChartBean(chartData, false);
        break;
      case Chart.Type.COMBO_BAR_LINE:
        chartData = this._getBarLineChartData(true);
        chartBean = this._getBarLineChartBean(chartData, true);
        break;
      case Chart.Type.BUBBLE:
        chartData = this._getBubbleChartData();
        chartBean = this._getBubbleChartBean(chartData);
        break;
      case Chart.Type.SCATTER:
        chartData = this._getScatterChartData();
        chartBean = this._getScatterChartBean(chartData);
        break;
      case Chart.Type.SPEEDO:
        chartData = this._getSpeedoChartData();
        chartBean = this._getSpeedoChartBean(chartData);
        break;
      case Chart.Type.SALESFUNNEL:
        chartData = this._getSalesfunnelChartData();
        chartBean = this._getSalesfunnelChartBean(chartData);
        break;
      case Chart.Type.VENN:
        chartData = this._getVennChartData();
        chartBean = this._getVennChartBean(chartData);
        break;
      case Chart.Type.PIE:
      case Chart.Type.DOUGHNUT:
      case Chart.Type.POLAR_AREA:
      case Chart.Type.RADAR:
      default:
        chartData = this._getPieDoughnutChartData();
        chartBean = this._getPieDoughnutChartBean(chartData);
        break;
    }

    if (chartBean) {
      this.chart.setData(chartBean.data);
      let config = this._getChartConfig();
      config = $.extend(true, {}, config, chartBean.config);
      this._setChartConfig(config);
      if (this.random && this.fillTable) {
        this._setTableChartData(chartData);
      }
    }
  }

  protected _updateColorHexValue(valueGroups: ChartValueGroup[]) {
    valueGroups = arrays.ensure(valueGroups).filter(valueGroup => !!valueGroup);
    if (!valueGroups || !valueGroups.length) {
      return;
    }

    switch (this.colorModeSelectorField.value) {
      case ChartColorMode.DATASET:
        valueGroups.forEach(valueGroup => {
          valueGroup.colorHexValue = this._randomHexColor();
        });
        break;
      case ChartColorMode.DATA: {
        const maxValueGroupSize = Math.max(...valueGroups.map(valueGroup => arrays.length(valueGroup.values as any[])));
        const colorHexValues = arrays.init(maxValueGroupSize, null).map(v => this._randomHexColor());
        valueGroups.forEach(valueGroup => {
          valueGroup.colorHexValue = colorHexValues;
        });
        break;
      }
      case ChartColorMode.AUTO:
      default:
        valueGroups.forEach(valueGroup => {
          valueGroup.colorHexValue = arrays.init(arrays.length(valueGroup.values as any[]), null).map(v => this._randomHexColor());
        });
        break;
    }
  }

  protected _getFulfillmentChartData(): ChartDataRaw {
    if (this.random) {
      return this._getFulfillmentChartDataRandom();
    }
    return this._getTableChartData();
  }

  protected _getFulfillmentChartDataRandom(): ChartDataRaw {
    return [
      [null, 'Actual'],
      ['Dataset 1', Math.floor(Math.random() * 100)]
    ];
  }

  protected _getFulfillmentChartBean(chartData: ChartDataRaw): ChartBean {
    if (!this._validateFulfillmentTable(chartData)) {
      return;
    }

    let chartValueGroups = [];

    // Actual value
    chartValueGroups.push({
      groupName: 'Actual',
      colorHexValue: this._randomHexColor(),
      values: [chartData[1][1]]
    });

    // Total value
    chartValueGroups.push({
      groupName: 'Total',
      colorHexValue: this._randomHexColor(),
      values: [100]
    });

    return {
      data: {
        axes: [],
        chartValueGroups: chartValueGroups
      },
      config: {
        options: {
          fulfillment: {
            startValue: this._calculatePropFulfillmentStartValue()
          }
        }
      }
    };
  }

  protected _calculatePropFulfillmentStartValue(): number {
    let startValue = 0;
    if (this.fulfillmentStartValuePropertyCheckbox.value) {
      this.chart.data.chartValueGroups.forEach(chartValueGroup => {
        if (chartValueGroup.groupName === 'Actual') {
          startValue = (chartValueGroup.values as number[])[0];
        }
      });
    }
    return startValue;
  }

  protected _validateFulfillmentTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    let dimensionError = 'A fulfillment chart needs exactly one value.';
    if (!this._validateTableDimensions(chartData,
      2, 2, dimensionError,
      2, 2, dimensionError)) {
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getBarLineChartData(comboBarLine: boolean): ChartDataRaw {
    if (this.random) {
      return this._getBarLineChartDataRandom(comboBarLine);
    }
    return this._getTableChartData();
  }

  protected _getBarLineChartDataRandom(comboBarLine: boolean): ChartDataRaw {
    let numGroups = this._getChartConfig().type === Chart.Type.BAR_HORIZONTAL ? 4 : 6,
      chartData = arrays.init(this.numberOfDatasets + 1, null);

    for (let i = 0; i < chartData.length; i++) {
      chartData[i] = arrays.init(numGroups + 1, null);
    }

    for (let i = 1; i < chartData[0].length; i++) {
      chartData[0][i] = 'Test ' + i;
    }

    for (let i = 1; i < chartData.length; i++) {
      let suffix = '';
      if (comboBarLine) {
        suffix = '|' + (Math.floor(Math.random() * 2) === 1 ? Chart.Type.LINE : Chart.Type.BAR);
      }
      chartData[i][0] = 'Dataset ' + i + suffix;
    }

    for (let i = 1; i < chartData.length; i++) {
      for (let j = 1; j < chartData[i].length; j++) {
        chartData[i][j] = this._chartValue();
      }
    }

    return chartData;
  }

  protected _getBarLineChartBean(chartData: ChartDataRaw, comboBarLine: boolean): ChartBean {
    if (!this._validateBarLineTable(chartData)) {
      return;
    }

    let axis = [];

    for (let i = 1; i < chartData[0].length; i++) {
      axis.push({label: chartData[0][i]});
    }

    let chartValueGroups = [];

    for (let i = 1; i < chartData.length; i++) {
      let chartValueGroup = {values: []} as ChartValueGroup;
      for (let j = 1; j < chartData[i].length; j++) {
        (chartValueGroup.values as number[]).push(chartData[i][j] as number);
      }

      let groupName = chartData[i][0] as string;
      if (comboBarLine) {
        let type: string = Chart.Type.BAR,
          split = groupName.split('|');
        if (split.length === 2) {
          groupName = split[0];
          if (scout.isOneOf(split[1], Chart.Type.BAR, Chart.Type.LINE)) {
            type = split[1];
          }
        }
        chartValueGroup.type = type;
      }
      chartValueGroup.groupName = groupName;
      chartValueGroups.push(chartValueGroup);
    }
    this._updateColorHexValue(chartValueGroups);

    return {
      data: {
        axes: [axis],
        chartValueGroups: chartValueGroups
      },
      config: {}
    };
  }

  protected _validateBarLineTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    if (!this._validateTableDimensions(chartData,
      2, -1, 'No dataset.',
      2, -1, 'No data.')) {
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getBubbleChartData(): ChartDataRaw {
    if (this.random) {
      return this._getBubbleChartDataRandom();
    }
    return this._getTableChartData();
  }

  protected _getBubbleChartDataRandom(): ChartDataRaw {
    let numGroups = 6,
      chartData = arrays.init(this.numberOfDatasets + 1, null);

    for (let i = 0; i < chartData.length; i++) {
      chartData[i] = arrays.init(numGroups * 3 + 1, null);
    }

    for (let i = 1; i < chartData[0].length; i = i + 3) {
      chartData[0][i] = 'Test ' + i + ' (x)';
      chartData[0][i + 1] = 'Test ' + i + ' (y)';
      chartData[0][i + 2] = 'Test ' + i + ' (z)';
    }

    for (let i = 1; i < chartData.length; i++) {
      chartData[i][0] = 'Dataset ' + i;
    }

    for (let i = 1; i < chartData.length; i++) {
      for (let j = 1; j < chartData[i].length; j = j + 3) {
        chartData[i][j] = this._chartValue();
        chartData[i][j + 1] = this._chartValue();
        chartData[i][j + 2] = Math.floor(Math.random() * 100);
      }
    }

    return chartData;
  }

  protected _getBubbleChartBean(chartData: ChartDataRaw): ChartBean {
    if (!this._validateBubbleTable(chartData)) {
      return;
    }

    let chartValueGroups = [];

    for (let i = 1; i < chartData.length; i++) {
      let chartValueGroup = {values: []} as ChartValueGroup;
      for (let j = 1; j < chartData[i].length; j = j + 3) {
        let nTuple = {} as Record<string, number>;
        nTuple.x = chartData[i][j] as number;
        nTuple.y = chartData[i][j + 1] as number;
        nTuple.z = chartData[i][j + 2] as number;
        (chartValueGroup.values as Record<string, number>[]).push(nTuple);
      }

      chartValueGroup.groupName = chartData[i][0] as string;
      chartValueGroups.push(chartValueGroup);
    }
    this._updateColorHexValue(chartValueGroups);

    return {
      data: {
        axes: [],
        chartValueGroups: chartValueGroups
      },
      config: {}
    };
  }

  protected _validateBubbleTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    if (!this._validateTableDimensions(chartData,
      2, -1, 'No dataset.',
      2, -1, 'No data.')) {
      return false;
    }
    if (chartData[0].length % 3 !== 1) {
      this.chartDataTableField.addErrorStatus('A bubble chart needs a multiple of three values. These are interpreted as x, y and z');
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getScatterChartData(): ChartDataRaw {
    if (this.random) {
      return this._getScatterChartDataRandom();
    }
    return this._getTableChartData();
  }

  protected _getScatterChartDataRandom(): ChartDataRaw {
    let numGroups = 6,
      chartData = arrays.init(this.numberOfDatasets + 1, null);

    for (let i = 0; i < chartData.length; i++) {
      chartData[i] = arrays.init(numGroups * 2 + 1, null);
    }

    for (let i = 1; i < chartData[0].length; i = i + 2) {
      chartData[0][i] = 'Test ' + i + ' (x)';
      chartData[0][i + 1] = 'Test ' + i + ' (y)';
    }

    for (let i = 1; i < chartData.length; i++) {
      chartData[i][0] = 'Dataset ' + i;
    }

    for (let i = 1; i < chartData.length; i++) {
      for (let j = 1; j < chartData[i].length; j = j + 2) {
        chartData[i][j] = this._chartValue();
        chartData[i][j + 1] = this._chartValue();
      }
    }

    return chartData;
  }

  protected _getScatterChartBean(chartData: ChartDataRaw): ChartBean {
    if (!this._validateScatterTable(chartData)) {
      return;
    }

    let chartValueGroups = [];

    for (let i = 1; i < chartData.length; i++) {
      let chartValueGroup = {values: []} as ChartValueGroup;
      for (let j = 1; j < chartData[i].length; j = j + 2) {
        let nTuple = {} as Record<string, number>;
        nTuple.x = chartData[i][j] as number;
        nTuple.y = chartData[i][j + 1] as number;
        (chartValueGroup.values as Record<string, number>[]).push(nTuple);
      }

      chartValueGroup.groupName = chartData[i][0] as string;
      chartValueGroups.push(chartValueGroup);
    }
    this._updateColorHexValue(chartValueGroups);

    return {
      data: {
        axes: [],
        chartValueGroups: chartValueGroups
      },
      config: {}
    };
  }

  protected _validateScatterTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    if (!this._validateTableDimensions(chartData,
      2, -1, 'No dataset.',
      2, -1, 'No data.')) {
      return false;
    }
    if (chartData[0].length % 2 !== 1) {
      this.chartDataTableField.addErrorStatus('A scatter chart needs a multiple of two values. These are interpreted as x and y');
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getSpeedoChartData(): ChartDataRaw {
    if (this.random) {
      return this._getSpeedoChartDataRandom();
    }
    return this._getTableChartData();
  }

  protected _getSpeedoChartDataRandom(): ChartDataRaw {
    let min = Math.floor(Math.random() * 1000000),
      max = min + Math.floor(Math.random() * 1000000),
      value = min + Math.floor(Math.random() * (max - min));
    return [
      [null, 'Min', 'Value', 'Max'],
      ['Dataset 1', min, value, max]
    ];
  }

  protected _getSpeedoChartBean(chartData: ChartDataRaw): ChartBean {
    if (!this._validateSpeedoTable(chartData)) {
      return;
    }

    return {
      data: {
        axes: [],
        chartValueGroups: [{
          groupName: chartData[1][0] as string,
          colorHexValue: this._randomHexColor(),
          values: [chartData[1][1] as number, chartData[1][2] as number, chartData[1][3] as number]
        }]
      },
      config: {}
    };
  }

  protected _validateSpeedoTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    let dimensionError = 'A speedo chart needs exactly one dataset with three values (min, value, max).';
    if (!this._validateTableDimensions(chartData,
      2, 2, dimensionError,
      4, 4, dimensionError)) {
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    let min = chartData[1][1],
      value = chartData[1][2],
      max = chartData[1][3];
    if (value < min || max < value) {
      this.chartDataTableField.addErrorStatus('The values min, value, max for a speedo chart need to fulfill min <= value <= max.');
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getSalesfunnelChartData(): ChartDataRaw {
    if (this.random) {
      return this._getSalesfunnelChartDataRandom();
    }
    return this._getTableChartData();
  }

  protected _getSalesfunnelChartDataRandom(): ChartDataRaw {
    let chartData = arrays.init(this.numberOfDatasets + 1, null);

    for (let i = 0; i < chartData.length; i++) {
      chartData[i] = arrays.init(3, null);
    }

    chartData[0][1] = 'Test 1';
    chartData[0][2] = 'Test 2';

    for (let i = 1; i < chartData.length; i++) {
      chartData[i][0] = 'Dataset ' + i;
      chartData[i][1] = Math.floor(Math.random() * 10000);
      chartData[i][2] = Math.floor(Math.random() * 10000);
    }

    return chartData;
  }

  protected _getSalesfunnelChartBean(chartData: ChartDataRaw): ChartBean {
    if (!this._validateSalesfunnelTable(chartData)) {
      return;
    }

    let twoValues = chartData[0].length === 3,
      chartValueGroups = [],
      axes = [];

    for (let i = 1; i < chartData.length; i++) {
      let chartValueGroup = {values: []} as ChartValueGroup;
      chartValueGroup.groupName = chartData[i][0] as string;
      (chartValueGroup.values as number[]).push(chartData[i][1] as number);
      if (twoValues) {
        (chartValueGroup.values as number[]).push(chartData[i][2] as number);
      }
      chartValueGroup.colorHexValue = this._randomHexColor();
      chartValueGroups.push(chartValueGroup);
      let axis = [];
      axis.push({label: chartData[0][1] + ' ' + i});
      if (twoValues) {
        axis.push({label: chartData[0][2] + ' ' + i});
      }
      axes.push(axis);
    }

    return {
      data: {
        axes: axes,
        chartValueGroups: chartValueGroups
      },
      config: {
        options: {
          salesfunnel: {
            calcConversionRate: true
          }
        }
      }
    };
  }

  protected _validateSalesfunnelTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    if (!this._validateTableDimensions(chartData,
      2, -1, 'No dataset.',
      2, 3, 'A salesfunnel chart needs one or two values in each dataset.')) {
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getVennChartData(): ChartDataRaw {
    if (this.random) {
      return this._getVennChartDataRandom();
    }
    return this._getTableChartData();
  }

  protected _getVennChartDataRandom(): ChartDataRaw {
    let chartData = arrays.init(2, null);

    if (this.numberOfVennCircles === 1) {
      chartData[0] = [null, 'Set A'];
      chartData[1] = arrays.init(2, null);
    } else if (this.numberOfVennCircles === 2) {
      chartData[0] = [null, 'Set A', 'Set B', 'A-B'];
      chartData[1] = arrays.init(4, null);
    } else if (this.numberOfVennCircles === 3) {
      chartData[0] = [null, 'Set A', 'Set B', 'Set C', 'A-B', 'A-C', 'B-C', 'A-B-C'];
      chartData[1] = arrays.init(8, null);
    }

    chartData[1][0] = 'Dataset 1';

    for (let i = 1; i < chartData[1].length; i++) {
      chartData[1][i] = Math.max(Math.floor(Math.random() * 130) - 30, 0);
    }

    return chartData;
  }

  protected _getVennChartBean(chartData: ChartDataRaw): ChartBean {
    if (!this._validateVennTable(chartData)) {
      return;
    }

    let chartValueGroups = [];

    for (let i = 1; i < chartData[0].length; i++) {
      chartValueGroups.push({
        groupName: chartData[0][i],
        colorHexValue: this._randomHexColor(),
        values: [chartData[1][i]]
      });
    }

    return {
      data: {
        axes: [],
        chartValueGroups: chartValueGroups
      },
      config: {
        options: {
          venn: {
            numberOfCircles: Math.log2(chartData[0].length) as 1 | 2 | 3
          }
        }
      }
    };
  }

  protected _validateVennTable(chartData: ChartDataRaw): boolean {
    this.chartDataTableField.clearErrorStatus();
    let dimensionError = 'A venn chart needs exactly one dataset with 1 (Set A), 3 (Set A, Set B, A-B) or 7 (Set A, Set B, Set C, A-B, A-C, B-C, A-B-C) values.';
    if (!this._validateTableDimensions(chartData,
      2, 2, dimensionError,
      2, 8, dimensionError)) {
      return false;
    }
    if (chartData[0].length !== 2 && chartData[0].length !== 4 && chartData[0].length !== 8) {
      this.chartDataTableField.addErrorStatus(dimensionError);
      return false;
    }
    if (!this._validateTableTypes(chartData)) {
      return false;
    }
    this.chartDataTableField.clearErrorStatus();
    return true;
  }

  protected _getPieDoughnutChartData(): ChartDataRaw {
    let oldValuesProvider = this.valuesProvider;
    this.valuesProvider = ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM_POSITIVE;
    let chartData = this._getBarLineChartData(false);
    this.valuesProvider = oldValuesProvider;
    return chartData;
  }

  protected _getPieDoughnutChartBean(chartData: ChartDataRaw): ChartBean {
    return this._getBarLineChartBean(chartData, false);
  }

  protected _chartValue(): number {
    switch (this.valuesProvider) {
      case ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM:
        return Math.floor(Math.random() * (ChartFieldForm.MAX_INTEGER - ChartFieldForm.MIN_INTEGER) + ChartFieldForm.MIN_INTEGER);
      case ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM_POSITIVE:
        return Math.floor(Math.random() * ChartFieldForm.MAX_INTEGER);
      case ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM_500000:
        return 500000 - Math.floor(Math.random() * 1000000);
      case ValuesProviderLookupCall.Type.VALUE_PROVIDER_ALL_0:
        return 0;
      case ValuesProviderLookupCall.Type.VALUE_PROVIDER_ALL_1:
        return 1;
      case ValuesProviderLookupCall.Type.VALUE_PROVIDER_ALL_50000:
        return 50000;
      default:
        return 42;
    }
  }

  protected _randomHexColor(): string {
    return '#' + Math.floor(Math.random() * 16777215).toString(16);
  }

  protected _validateTableDimensions(chartData: ChartDataRaw, minRows: number, maxRows: number, errorRows: string, minCols: number, maxCols: number, errorCols: string): boolean {
    if (objects.isNullOrUndefined(chartData)) {
      this.chartDataTableField.addErrorStatus('No chart data.');
      return false;
    }
    // rows
    if (chartData.length < minRows || (maxRows > 0 && chartData.length > maxRows)) {
      this.chartDataTableField.addErrorStatus(errorRows);
      return false;
    }
    // cols
    if (chartData[0].length < minCols || (maxCols > 0 && chartData[0].length > maxCols)) {
      this.chartDataTableField.addErrorStatus(errorCols);
      return false;
    }
    return true;
  }

  protected _validateTableTypes(chartData: ChartDataRaw): boolean {
    if (objects.isNullOrUndefined(chartData) || chartData.length === 0 || chartData[0].length === 0) {
      this.chartDataTableField.addErrorStatus('No chart data.');
      return false;
    }
    if (chartData[0].slice(1)
      .some(elem => typeof elem !== 'string')) {
      this.chartDataTableField.addErrorStatus('Invalid data labels.');
      return false;
    }
    if (chartData.slice(1)
      .map(arr => arr[0])
      .some(elem => typeof elem !== 'string')) {
      this.chartDataTableField.addErrorStatus('Invalid dataset labels.');
      return false;
    }
    if (arrays.flatMap(chartData.slice(1), arr => arr.slice(1))
      .some(elem => typeof elem !== 'number')) {
      this.chartDataTableField.addErrorStatus('Invalid values.');
      return false;
    }
    return true;
  }

  protected _getTableChartData(): ChartDataRaw {
    return [[null, ...this.dataLabels], ...this._getTableData()];
  }

  protected _getTableData(): ChartDataRaw {
    return this.chartDataTable.rows.map(row => row.cells.map(cell => cell.value));
  }

  protected _setTableChartData(chartData: ChartDataRaw) {
    if (!chartData || !chartData.length || !chartData[0].length) {
      return;
    }

    this.dataLabels = chartData[0].splice(1) as string[];

    this.chartDataTable.deleteAllRows();
    this._updateColumnStructure();

    this._setTableData(chartData.splice(1));

    this.removeDataMenu.setEnabled(this.dataLabels.length !== 0);
  }

  protected _setTableData(tableData: ChartDataRaw) {
    this.chartDataTable.deleteAllRows();
    this.chartDataTable.insertRows(tableData.map(row => ({
      cells: row
    })));
  }

  protected _addColumn(label: string) {
    this.dataLabels.push(label);
    this._updateColumns(arr => arr.push(0));
  }

  protected _removeColumn() {
    this.dataLabels.pop();
    this._updateColumns(arr => arr.pop());
  }

  protected _updateColumns(modifyRow: (arr: ChartDataRowRaw) => void) {
    let tableData = this._getTableData();

    this.chartDataTable.deleteAllRows();
    this._updateColumnStructure();

    if (modifyRow) {
      tableData.forEach(row => modifyRow(row));
    }

    this._setTableData(tableData);

    this.removeDataMenu.setEnabled(this.dataLabels.length !== 0);
    this._renewData();
  }

  protected _updateColumnStructure() {
    let columns = [this.datasetLabelColumn, ...this.dataLabels.map(label => scout.create(NumberColumn, {
      session: this.session,
      editable: true,
      width: 120,
      fixedPosition: true,
      text: label,
      maxValue: this._getMaxValue(),
      minValue: this._getMinValue()
    }))];

    this.chartDataTable.updateColumnStructure(columns);
  }

  protected _getMaxValue(): number {
    switch (this._getChartConfig().type) {
      case Chart.Type.FULFILLMENT:
      case Chart.Type.VENN:
        return 100;
      case Chart.Type.LINE:
      case Chart.Type.BAR:
      case Chart.Type.BAR_HORIZONTAL:
      case Chart.Type.COMBO_BAR_LINE:
      case Chart.Type.BUBBLE:
      case Chart.Type.SCATTER:
      case Chart.Type.SPEEDO:
      case Chart.Type.SALESFUNNEL:
      case Chart.Type.PIE:
      case Chart.Type.DOUGHNUT:
      case Chart.Type.POLAR_AREA:
      case Chart.Type.RADAR:
      default:
        return null;
    }
  }

  protected _getMinValue(): number {
    switch (this._getChartConfig().type) {
      case Chart.Type.FULFILLMENT:
      case Chart.Type.SPEEDO:
      case Chart.Type.PIE:
      case Chart.Type.DOUGHNUT:
      case Chart.Type.POLAR_AREA:
      case Chart.Type.RADAR:
      case Chart.Type.VENN:
        return 0;
      case Chart.Type.LINE:
      case Chart.Type.BAR:
      case Chart.Type.BAR_HORIZONTAL:
      case Chart.Type.COMBO_BAR_LINE:
      case Chart.Type.SALESFUNNEL:
      case Chart.Type.BUBBLE:
      case Chart.Type.SCATTER:
      default:
        return null;
    }
  }

  protected _getChartConfig(): ChartConfig {
    return this.chartConfig as ChartConfig;
  }

  protected _getCombinedConfig(): ChartConfig {
    return $.extend(true, {}, this.customChartConfig, this._getChartConfig());
  }

  protected _setChartConfig(config: ChartConfig) {
    this.chartConfig = config || {};
    this.chart.setConfig(this._getCombinedConfig());
  }

  protected _setCustomChartConfig(config: Partial<ChartConfig>) {
    this.customChartConfig = config || {};
    this.chart.setConfig(this._getCombinedConfig());
  }
}

type ChartBean = {
  data: ChartData;
  config: Partial<ChartConfig>;
};

type ChartDataRowRaw = (number | string)[];
type ChartDataRaw = ChartDataRowRaw[];
