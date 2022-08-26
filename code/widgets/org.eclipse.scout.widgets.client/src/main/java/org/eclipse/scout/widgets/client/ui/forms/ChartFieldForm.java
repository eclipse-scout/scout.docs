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
package org.eclipse.scout.widgets.client.ui.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.scout.rt.chart.client.ui.basic.chart.AbstractChart;
import org.eclipse.scout.rt.chart.client.ui.basic.chart.IChart;
import org.eclipse.scout.rt.chart.client.ui.form.fields.chartfield.AbstractChartField;
import org.eclipse.scout.rt.chart.client.ui.form.fields.tile.AbstractChartTile;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.ChartAxisBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.ChartBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.IChartAxisBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.IChartConfig;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.IChartType;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.IChartValueGroupBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.IMonupleChartValueGroupBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.MonupleChartValueGroupBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.NTupleChartValueGroupBean;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.basic.table.menus.OrganizeColumnsMenu;
import org.eclipse.scout.rt.client.ui.desktop.notification.DesktopNotification;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.form.fields.tilefield.AbstractTileField;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.status.IMultiStatus;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.data.colorscheme.IColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.ChartTypeLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.TileColorSchemeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.LeftBox.ChartDataTableField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.LeftBox.ChartDataTableField.Table;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.LeftBox.ChartDataTableField.Table.AbstractInputForm.InputMainBox.InputGroupBox.InputField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.RightBox.FillTableCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.RightBox.NumberOfDatasetsField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.RightBox.RandomCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.RightBox.RandomDataButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartDataBox.RightBox.ValuesProviderField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.CustomChartPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.FormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.LeftBox.FulfillmentStartValuePropertyCheckbox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.LeftBox.TileCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.LeftBox.TransparentCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.LeftBox.XAxisStackedCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.LeftBox.YAxisStackedCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.BottomBox.ChartPropertiesBox.RightBox.ChartTypeField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.TopBox.ChartField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.TopBox.ChartTileBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.TopBox.ChartTileBox.TileGrid;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.TopBox.ChartTileBox.TileGrid.ChartTile;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;
import org.json.JSONArray;
import org.json.JSONObject;

@ClassId("370c6a0a-3949-4b2c-95af-3079e4e4289f")
public class ChartFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final BigDecimal BIG_DECIMAL_100 = BigDecimal.valueOf(100, 0);

  private static final Integer DEFAULT_NUMBER_OF_VENN_CIRCLES = 3;
  private static final Integer DEFAULT_NUMBER_OF_DATASETS = 6;

  private static final int VALUE_PROVIDER_RANDOM = 1;
  private static final int VALUE_PROVIDER_RANDOM_POSITIVE = 2;
  private static final int VALUE_PROVIDER_ALL_0 = 3;
  private static final int VALUE_PROVIDER_ALL_1 = 4;
  private static final int VALUE_PROVIDER_ALL_50000 = 5;
  private static final int VALUE_PROVIDER_RANDOM_500000 = 6;

  private Integer m_numberOfVennCircles = DEFAULT_NUMBER_OF_VENN_CIRCLES;
  private Integer m_numberOfDatasets = DEFAULT_NUMBER_OF_DATASETS;
  private boolean m_normalized = true;

  @Override
  protected String getConfiguredTitle() {
    return "Chart Field";
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  public void startPageForm() {
    startInternal(new TestHandler());
  }

  public IChart getChart() {
    return getTileCheckBox().getValue() ? getTileChart() : getFieldChart();
  }

  public MainBox getMainBox() {
    return (MainBox) getRootGroupBox();
  }


  public ChartField getChartField() {
    return getFieldByClass(ChartField.class);
  }

  public IChart getFieldChart() {
    return getChartField().getChart();
  }

  public ChartTileBox getChartTileBox() {
    return getFieldByClass(ChartTileBox.class);
  }

  public ChartTile getChartTile() {
    return getChartTileBox().getTileGrid().getTileByClass(ChartTile.class);
  }

  public AbstractChartField getTileChartField() {
    return getChartTile().getTileWidget();
  }

  public IChart getTileChart() {
    return getChartTile().getChart();
  }

  public ChartPropertiesBox getChartPropertiesBox() {
    return getFieldByClass(ChartPropertiesBox.class);
  }

  public XAxisStackedCheckBox getXAxisStackedCheckBox() {
    return getFieldByClass(XAxisStackedCheckBox.class);
  }

  public YAxisStackedCheckBox getYAxisStackedCheckBox() {
    return getFieldByClass(YAxisStackedCheckBox.class);
  }

  public TransparentCheckBox getTransparentCheckBox() {
    return getFieldByClass(TransparentCheckBox.class);
  }

  public FulfillmentStartValuePropertyCheckbox getFulfillmentStartValuePropertyCheckbox() {
    return getFieldByClass(FulfillmentStartValuePropertyCheckbox.class);
  }

  public TileCheckBox getTileCheckBox() {
    return getFieldByClass(TileCheckBox.class);
  }

  public ChartTypeField getChartTypeField() {
    return getFieldByClass(ChartTypeField.class);
  }

  public CustomChartPropertiesBox getCustomChartPropertiesBox() {
    return getFieldByClass(CustomChartPropertiesBox.class);
  }

  public FormFieldPropertiesBox getFormFieldPropertiesBox() {
    return getFieldByClass(FormFieldPropertiesBox.class);
  }

  public ChartDataTableField getChartDataTableField() {
    return getFieldByClass(ChartDataTableField.class);
  }

  public RandomCheckBox getRandomCheckBox() {
    return getFieldByClass(RandomCheckBox.class);
  }

  public FillTableCheckBox getFillTableCheckBox() {
    return getFieldByClass(FillTableCheckBox.class);
  }

  public RandomDataButton getRandomDataButton() {
    return getFieldByClass(RandomDataButton.class);
  }

  public ValuesProviderField getValuesProviderField() {
    return getFieldByClass(ValuesProviderField.class);
  }

  public NumberOfDatasetsField getNumberOfDatasetsField() {
    return getFieldByClass(NumberOfDatasetsField.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  private boolean validateTableDimensions(Object[][] chartData, int minRows, int maxRows, String errorRows, int minCols, int maxCols, String errorCols) {
    if (chartData == null) {
      getChartDataTableField().addErrorStatus("No chart data.");
      return false;
    }
    // rows
    if (chartData.length < minRows || (maxRows > 0 && chartData.length > maxRows)) {
      getChartDataTableField().addErrorStatus(errorRows);
      return false;
    }
    // cols
    if (chartData[0].length < minCols || (maxCols > 0 && chartData[0].length > maxCols)) {
      getChartDataTableField().addErrorStatus(errorCols);
      return false;
    }
    return true;
  }

  private boolean validateTableTypes(Object[][] chartData) {
    if (chartData == null || chartData.length == 0 || chartData[0].length == 0) {
      getChartDataTableField().addErrorStatus("No chart data.");
      return false;
    }
    if (Stream.of(Arrays.copyOfRange(chartData[0], 1, chartData[0].length))
        .anyMatch(dataLabel -> !(dataLabel instanceof String))) {
      getChartDataTableField().addErrorStatus("Invalid data labels.");
      return false;
    }
    if (Stream.of(Arrays.copyOfRange(chartData, 1, chartData.length))
        .map(array -> array[0])
        .anyMatch(datasetLabel -> !(datasetLabel instanceof String))) {
      getChartDataTableField().addErrorStatus("Invalid dataset labels.");
      return false;
    }
    if (Stream.of(Arrays.copyOfRange(chartData, 1, chartData.length))
        .flatMap(array -> Stream.of(Arrays.copyOfRange(array, 1, array.length)))
        .anyMatch(value -> !(value instanceof BigDecimal))) {
      getChartDataTableField().addErrorStatus("Invalid values.");
      return false;
    }
    return true;
  }

  private Object[][] getBarLineChartData(boolean comboBarLine) {
    if (getRandomCheckBox().getValue()) {
      return getBarLineChartDataRandom(comboBarLine);
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getBarLineChartDataRandom(boolean comboBarLine) {
    int numGroups = IChartType.BAR_HORIZONTAL.equals(getChart().getConfig().getType()) ? 4 : 6;
    Object[][] chartData = new Object[m_numberOfDatasets + 1][numGroups + 1];

    for (int i = 1; i < chartData[0].length; i++) {
      chartData[0][i] = "Test " + i;
    }

    for (int i = 1; i < chartData.length; i++) {
      String suffix = "";
      if (comboBarLine) {
        suffix = "|" + (NumberUtility.randomInt(2) == 1 ? IChartType.LINE : IChartType.BAR);
      }
      chartData[i][0] = "Dataset " + i + suffix;
    }

    for (int i = 1; i < chartData.length; i++) {
      for (int j = 1; j < chartData[i].length; j++) {
        chartData[i][j] = chartValue();
      }
    }
    return chartData;
  }

  private ChartBean getBarLineChartBean(Object[][] chartData, boolean comboBarLine) {
    if (!validateBarLineTable(chartData)) {
      return null;
    }
    ChartBean bean = new ChartBean();
    List<IChartAxisBean> axis = new ArrayList<>();

    for (int i = 1; i < chartData[0].length; i++) {
      axis.add(new ChartAxisBean(i, (String) chartData[0][i]));
    }

    bean.getData().getAxes().add(axis);

    for (int i = 1; i < chartData.length; i++) {
      MonupleChartValueGroupBean chartValue = new MonupleChartValueGroupBean();
      for (int j = 1; j < chartData[i].length; j++) {
        chartValue.getValues().add((BigDecimal) chartData[i][j]);
      }
      String groupName = (String) chartData[i][0];
      if (comboBarLine) {
        String type = IChartType.BAR;
        String[] split = groupName.split("\\|");
        if (split.length == 2) {
          groupName = split[0];
          if (ObjectUtility.isOneOf(split[1], IChartType.BAR, IChartType.LINE)) {
            type = split[1];
          }
        }
        chartValue.setType(type);
      }
      chartValue.setGroupName(groupName);
      chartValue.setColorHexValue(randomHexColor());
      bean.getData().getChartValueGroups().add(chartValue);
    }

    return bean;
  }

  private boolean validateBarLineTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    if (!validateTableDimensions(chartData,
        2, -1, "No dataset.",
        2, -1, "No data.")) {
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  private Object[][] getPieDoughnutChartData() {
    ValuesProviderField valuesProviderField = getValuesProviderField();
    Integer oldValueProvider = valuesProviderField.getValue();
    valuesProviderField.setValueChangeTriggerEnabled(false);
    valuesProviderField.setValue(VALUE_PROVIDER_RANDOM_POSITIVE);
    Object[][] chartData = getBarLineChartData(false);
    valuesProviderField.setValue(oldValueProvider);
    valuesProviderField.setValueChangeTriggerEnabled(true);
    return chartData;
  }

  private ChartBean getPieDoughnutChartBean(Object[][] chartData) {
    return getBarLineChartBean(chartData, false);
  }

  private Object[][] getBubbleChartData() {
    if (getRandomCheckBox().getValue()) {
      return getBubbleChartDataRandom();
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getBubbleChartDataRandom() {
    int numGroups = 6;
    Object[][] chartData = new Object[m_numberOfDatasets + 1][numGroups * 3 + 1];

    for (int i = 1; i < chartData[0].length; i = i + 3) {
      chartData[0][i] = "Test " + i + " (x)";
      chartData[0][i + 1] = "Test " + i + " (y)";
      chartData[0][i + 2] = "Test " + i + " (z)";
    }

    for (int i = 1; i < chartData.length; i++) {
      chartData[i][0] = "Dataset " + i;
    }

    for (int i = 1; i < chartData.length; i++) {
      for (int j = 1; j < chartData[i].length; j = j + 3) {
        chartData[i][j] = chartValue();
        chartData[i][j + 1] = chartValue();
        chartData[i][j + 2] = new BigDecimal(NumberUtility.randomInt(100));
      }
    }

    return chartData;
  }

  private ChartBean getBubbleChartBean(Object[][] chartData) {
    if (!validateBubbleTable(chartData)) {
      return null;
    }
    ChartBean bean = new ChartBean();

    for (int i = 1; i < chartData.length; i++) {
      NTupleChartValueGroupBean chartValue = new NTupleChartValueGroupBean(3, "x", "y", "z");
      for (int j = 1; j < chartData[i].length; j = j + 3) {
        chartValue.add((BigDecimal) chartData[i][j], (BigDecimal) chartData[i][j + 1], (BigDecimal) chartData[i][j + 2]);
      }
      chartValue.setGroupName((String) chartData[i][0]);
      chartValue.setColorHexValue(randomHexColor());
      bean.getData().getChartValueGroups().add(chartValue);
    }

    return bean;
  }

  private boolean validateBubbleTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    if (!validateTableDimensions(chartData,
        2, -1, "No dataset.",
        2, -1, "No data.")) {
      return false;
    }
    if (chartData[0].length % 3 != 1) {
      getChartDataTableField().addErrorStatus("A bubble chart needs a multiple of three values. These are interpreted as x, y and z");
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  private Object[][] getScatterChartData() {
    if (getRandomCheckBox().getValue()) {
      return getScatterChartDataRandom();
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getScatterChartDataRandom() {
    int numGroups = 6;
    Object[][] chartData = new Object[m_numberOfDatasets + 1][numGroups * 2 + 1];

    for (int i = 1; i < chartData[0].length; i = i + 2) {
      chartData[0][i] = "Test " + i + " (x)";
      chartData[0][i + 1] = "Test " + i + " (y)";
    }

    for (int i = 1; i < chartData.length; i++) {
      chartData[i][0] = "Dataset " + i;
    }

    for (int i = 1; i < chartData.length; i++) {
      for (int j = 1; j < chartData[i].length; j = j + 2) {
        chartData[i][j] = chartValue();
        chartData[i][j + 1] = chartValue();
      }
    }

    return chartData;
  }

  private ChartBean getScatterChartBean(Object[][] chartData) {
    if (!validateScatterTable(chartData)) {
      return null;
    }
    ChartBean bean = new ChartBean();

    for (int i = 1; i < chartData.length; i++) {
      NTupleChartValueGroupBean chartValue = new NTupleChartValueGroupBean(2, "x", "y");
      for (int j = 1; j < chartData[i].length; j = j + 2) {
        chartValue.add((BigDecimal) chartData[i][j], (BigDecimal) chartData[i][j + 1]);
      }
      chartValue.setGroupName((String) chartData[i][0]);
      chartValue.setColorHexValue(randomHexColor());
      bean.getData().getChartValueGroups().add(chartValue);
    }

    return bean;
  }

  private boolean validateScatterTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    if (!validateTableDimensions(chartData,
        2, -1, "No dataset.",
        2, -1, "No data.")) {
      return false;
    }
    if (chartData[0].length % 2 != 1) {
      getChartDataTableField().addErrorStatus("A scatter chart needs a multiple of two values. These are interpreted as x and y");
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  private Object[][] getFulfillmentChartData() {
    if (getRandomCheckBox().getValue()) {
      return getFulfillmentChartDataRandom();
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getFulfillmentChartDataRandom() {
    Object[][] chartData = new Object[2][2];
    chartData[0][1] = "Actual";
    chartData[1][0] = "Dataset 1";
    chartData[1][1] = BigDecimal.valueOf(NumberUtility.randomInt(100));

    return chartData;
  }

  private ChartBean getFulfillmentChartBean(Object[][] chartData) {
    if (!validateFulfillmentTable(chartData)) {
      return null;
    }
    int total = 100;

    ChartBean bean = new ChartBean();

    // Actual value
    MonupleChartValueGroupBean actualChartValue = new MonupleChartValueGroupBean();
    actualChartValue.setGroupName("Actual");
    actualChartValue.setColorHexValue(randomHexColor());
    actualChartValue.getValues().add((BigDecimal) chartData[1][1]);
    bean.getData().getChartValueGroups().add(actualChartValue);

    // Total value
    MonupleChartValueGroupBean totalChartValue = new MonupleChartValueGroupBean();
    totalChartValue.setGroupName("Total");
    totalChartValue.setColorHexValue(randomHexColor());
    totalChartValue.getValues().add(BigDecimal.valueOf(total));
    bean.getData().getChartValueGroups().add(totalChartValue);

    calculatePropFulfillmentStartValue(bean);
    return bean;
  }

  protected void calculatePropFulfillmentStartValue(ChartBean bean) {
    IChart chart = getChart();
    if (BooleanUtility.nvl(getFulfillmentStartValuePropertyCheckbox().getValue())) {
      // Old value needed to calculate animation start of fulfillment chart
      BigDecimal actualOldValue = BigDecimal.ZERO;
      for (IChartValueGroupBean valueGroup : chart.getData().getChartValueGroups()) {
        if (valueGroup instanceof IMonupleChartValueGroupBean) {
          IMonupleChartValueGroupBean monupleValueGroup = (IMonupleChartValueGroupBean) valueGroup;
          if ("Actual".equals(valueGroup.getGroupName()) && !monupleValueGroup.getValues().isEmpty()) {
            actualOldValue = monupleValueGroup.getValues().get(0);
          }
        }
      }
      bean.getConfig().withProperty(IChartConfig.FULFILLMENT_START_VALUE, actualOldValue);
    }
    else {
      IChartConfig config = chart.getConfig();
      config.removeProperty(IChartConfig.FULFILLMENT_START_VALUE);
      chart.setConfig(config);
    }
  }

  private boolean validateFulfillmentTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    String dimensionError = "A fulfillment chart needs exactly one value.";
    if (!validateTableDimensions(chartData,
        2, 2, dimensionError,
        2, 2, dimensionError)) {
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  private Object[][] getSpeedoChartData() {
    if (getRandomCheckBox().getValue()) {
      return getSpeedoChartDataRandom();
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getSpeedoChartDataRandom() {
    int from = NumberUtility.randomInt(1000000);
    int to = from + NumberUtility.randomInt(1000000);

    BigDecimal min = new BigDecimal(from);
    BigDecimal max = new BigDecimal(to);
    BigDecimal value = new BigDecimal(from + NumberUtility.randomInt(to - from));

    Object[][] chartData = new Object[2][4];
    chartData[0][1] = "Min";
    chartData[0][2] = "Value";
    chartData[0][3] = "Max";
    chartData[1][0] = "Dataset 1";
    chartData[1][1] = min;
    chartData[1][2] = value;
    chartData[1][3] = max;

    return chartData;
  }

  private ChartBean getSpeedoChartBean(Object[][] chartData) {
    if (!validateSpeedoTable(chartData)) {
      return null;
    }

    ChartBean bean = new ChartBean();
    MonupleChartValueGroupBean chartValue = new MonupleChartValueGroupBean();
    chartValue.getValues().add((BigDecimal) chartData[1][1]);
    chartValue.getValues().add((BigDecimal) chartData[1][2]);
    chartValue.getValues().add((BigDecimal) chartData[1][3]);
    chartValue.setGroupName((String) chartData[1][0]);
    chartValue.setColorHexValue(randomHexColor());
    bean.getData().getChartValueGroups().add(chartValue);

    return bean;
  }

  private boolean validateSpeedoTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    String dimensionError = "A speedo chart needs exactly one dataset with three values (min, value, max).";
    if (!validateTableDimensions(chartData,
        2, 2, dimensionError,
        4, 4, dimensionError)) {
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    BigDecimal min = (BigDecimal) chartData[1][1];
    BigDecimal value = (BigDecimal) chartData[1][2];
    BigDecimal max = (BigDecimal) chartData[1][3];
    if (value.compareTo(min) < 0 || max.compareTo(value) < 0) {
      getChartDataTableField().addErrorStatus("The values min, value, max for a speedo chart need to fulfill min <= value <= max.");
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  private Object[][] getSalesfunnelChartData() {
    if (getRandomCheckBox().getValue()) {
      return getSalesfunnelChartDataRandom();
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getSalesfunnelChartDataRandom() {
    Object[][] chartData = new Object[m_numberOfDatasets + 1][3];
    chartData[0][1] = "Test 1";
    chartData[0][2] = "Test 2";
    for (int i = 1; i < chartData.length; i++) {
      chartData[i][0] = "Dataset " + i;
      chartData[i][1] = new BigDecimal(NumberUtility.randomInt(10000));
      chartData[i][2] = new BigDecimal(NumberUtility.randomInt(10000));
    }
    return chartData;
  }

  private ChartBean getSalesfunnelChartBean(Object[][] chartData) {
    if (!validateSalesfunnelTable(chartData)) {
      return null;
    }
    ChartBean bean = new ChartBean();
    boolean twoValues = chartData[0].length == 3;
    for (int i = 1; i < chartData.length; i++) {
      MonupleChartValueGroupBean chartValue = new MonupleChartValueGroupBean();
      chartValue.setGroupName((String) chartData[i][0]);
      chartValue.getValues().add((BigDecimal) chartData[i][1]);
      if (twoValues) {
        chartValue.getValues().add((BigDecimal) chartData[i][2]);
      }
      chartValue.setColorHexValue(randomHexColor());
      List<IChartAxisBean> valueDescription = new ArrayList<>();
      valueDescription.add(new ChartAxisBean(i, chartData[0][1] + " " + i));
      if (twoValues) {
        valueDescription.add(new ChartAxisBean(i, chartData[0][2] + " " + i));
      }
      bean.getData().getAxes().add(valueDescription);
      bean.getData().getChartValueGroups().add(chartValue);
    }

    bean.getConfig().withProperty(IChartConfig.SALESFUNNEL_CALC_CONVERSION_RATE, true);
    bean.getConfig().withProperty(IChartConfig.SALESFUNNEL_NORMALIZED, m_normalized);
    return bean;
  }

  private boolean validateSalesfunnelTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    if (!validateTableDimensions(chartData,
        2, -1, "No dataset.",
        2, 3, "A salesfunnel chart needs one or two values in each dataset.")) {
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  private Object[][] getVennChartData() {
    if (getRandomCheckBox().getValue()) {
      return getVennChartDataRandom();
    }
    return getChartDataTableField().getChartData();
  }

  private Object[][] getVennChartDataRandom() {
    Object[][] chartData = null;
    if (m_numberOfVennCircles == 1) {
      chartData = new Object[2][2];
      chartData[0] = new Object[]{null, "Set A"};
    }
    else if (m_numberOfVennCircles == 2) {
      chartData = new Object[2][4];
      chartData[0] = new Object[]{null, "Set A", "Set B", "A-B"};
    }
    else if (m_numberOfVennCircles == 3) {
      chartData = new Object[2][8];
      chartData[0] = new Object[]{null, "Set A", "Set B", "Set C", "A-B", "A-C", "B-C", "A-B-C"};
    }
    else {
      throw new IllegalStateException();
    }
    chartData[1][0] = "Dataset 1";
    for (int i = 1; i < chartData[1].length; i++) {
      chartData[1][i] = BigDecimal.valueOf(Math.max(NumberUtility.randomInt(130) - 30, 0));
    }
    return chartData;
  }

  private ChartBean getVennChartBean(Object[][] chartData) {
    if (!validateVennTable(chartData)) {
      return null;
    }
    ChartBean bean = new ChartBean();

    int chartDataLength = chartData[0].length;
    if (chartDataLength == 2) {
      bean.getConfig().withProperty(IChartConfig.VENN_NUMBER_OF_CIRCLES, 1);
    }
    else if (chartDataLength == 4) {
      bean.getConfig().withProperty(IChartConfig.VENN_NUMBER_OF_CIRCLES, 2);
    }
    else if (chartDataLength == 8) {
      bean.getConfig().withProperty(IChartConfig.VENN_NUMBER_OF_CIRCLES, 3);
    }

    for (int i = 1; i < chartDataLength; i++) {
      MonupleChartValueGroupBean valueGroupBean = new MonupleChartValueGroupBean();
      valueGroupBean.setGroupName((String) chartData[0][i]);
      valueGroupBean.setColorHexValue(randomHexColor());
      valueGroupBean.getValues().add((BigDecimal) chartData[1][i]);
      bean.getData().getChartValueGroups().add(valueGroupBean);
    }

    return bean;
  }

  private boolean validateVennTable(Object[][] chartData) {
    getChartDataTableField().clearErrorStatus();
    String dimensionError = "A venn chart needs exactly one dataset with 1 (Set A), 3 (Set A, Set B, A-B) or 7 (Set A, Set B, Set C, A-B, A-C, B-C, A-B-C) values.";
    if (!validateTableDimensions(chartData,
        2, 2, dimensionError,
        2, 8, dimensionError)) {
      return false;
    }
    if (chartData[0].length != 2 && chartData[0].length != 4 && chartData[0].length != 8) {
      getChartDataTableField().addErrorStatus(dimensionError);
      return false;
    }
    if (!validateTableTypes(chartData)) {
      return false;
    }
    getChartDataTableField().clearErrorStatus();
    return true;
  }

  protected BigDecimal chartValue() {
    switch (getValuesProviderField().getValue()) {
      case VALUE_PROVIDER_RANDOM:
        return new BigDecimal(NumberUtility.randomInt());
      case VALUE_PROVIDER_RANDOM_POSITIVE:
        return new BigDecimal(NumberUtility.randomInt(Integer.MAX_VALUE));
      case VALUE_PROVIDER_RANDOM_500000:
        int range = 1000000;
        return new BigDecimal(range / 2 - NumberUtility.randomInt(range));
      case VALUE_PROVIDER_ALL_0:
        return BigDecimal.ZERO;
      case VALUE_PROVIDER_ALL_1:
        return BigDecimal.ONE;
      case VALUE_PROVIDER_ALL_50000:
        return new BigDecimal(50000);
      default:
        throw new IllegalStateException();
    }
  }

  protected String randomHexColor() {
    int colorInt = NumberUtility.randomInt(16777215); // FFFFFF in decimal
    String color = "#" + Integer.toHexString(0x1000000 | colorInt).substring(1).toUpperCase();
    return color;
  }

  protected String randomWord() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < NumberUtility.randomInt(8) + 5; i++) {
      char tmp = (char) (NumberUtility.randomInt('z' - 'a') + 'a');
      sb.append(i == 0 ? StringUtility.uppercase(tmp + "") : tmp);
    }
    return sb.toString();
  }

  public void renewData() {
    if (isFormLoading()) {
      return; // not yet, see execPostLoad in handler
    }
    IChart chart = getChart();
    Object[][] chartData = null;
    ChartBean bean = null;
    switch (chart.getConfig().getType()) {
      case IChartType.FULFILLMENT:
        chartData = getFulfillmentChartData();
        bean = getFulfillmentChartBean(chartData);
        break;
      case IChartType.LINE:
      case IChartType.BAR:
      case IChartType.BAR_HORIZONTAL:
        chartData = getBarLineChartData(false);
        bean = getBarLineChartBean(chartData, false);
        break;
      case IChartType.COMBO_BAR_LINE:
        chartData = getBarLineChartData(true);
        bean = getBarLineChartBean(chartData, true);
        break;
      case IChartType.BUBBLE:
        chartData = getBubbleChartData();
        bean = getBubbleChartBean(chartData);
        break;
      case IChartType.SCATTER:
        chartData = getScatterChartData();
        bean = getScatterChartBean(chartData);
        break;
      case IChartType.SPEEDO:
        chartData = getSpeedoChartData();
        bean = getSpeedoChartBean(chartData);
        break;
      case IChartType.SALESFUNNEL:
        chartData = getSalesfunnelChartData();
        bean = getSalesfunnelChartBean(chartData);
        break;
      case IChartType.VENN:
        chartData = getVennChartData();
        bean = getVennChartBean(chartData);
        break;
      case IChartType.PIE:
      case IChartType.DOUGHNUT:
      case IChartType.POLAR_AREA:
      case IChartType.RADAR:
      default:
        chartData = getPieDoughnutChartData();
        bean = getPieDoughnutChartBean(chartData);
        break;
    }
    if (bean != null) {
      chart.setData(bean.getData());
      chart.extendConfig(bean.getConfig(), true);
      if (getRandomCheckBox().getValue() && getFillTableCheckBox().getValue()) {
        getChartDataTableField().setChartData(chartData);
      }
    }
  }

  private BigDecimal getMaxValue() {
    switch (getChart().getConfig().getType()) {
      case IChartType.FULFILLMENT:
      case IChartType.VENN:
        return BIG_DECIMAL_100;
      case IChartType.LINE:
      case IChartType.BAR:
      case IChartType.BAR_HORIZONTAL:
      case IChartType.COMBO_BAR_LINE:
      case IChartType.BUBBLE:
      case IChartType.SCATTER:
      case IChartType.SPEEDO:
      case IChartType.SALESFUNNEL:
      case IChartType.PIE:
      case IChartType.DOUGHNUT:
      case IChartType.POLAR_AREA:
      case IChartType.RADAR:
      default:
        return null;
    }
  }

  protected BigDecimal getMinValue() {
    switch (getChart().getConfig().getType()) {
      case IChartType.FULFILLMENT:
      case IChartType.SPEEDO:
      case IChartType.PIE:
      case IChartType.DOUGHNUT:
      case IChartType.POLAR_AREA:
      case IChartType.RADAR:
      case IChartType.VENN:
        return BigDecimal.ZERO;
      case IChartType.LINE:
      case IChartType.BAR:
      case IChartType.BAR_HORIZONTAL:
      case IChartType.COMBO_BAR_LINE:
      case IChartType.SALESFUNNEL:
      case IChartType.BUBBLE:
      case IChartType.SCATTER:
      default:
        return null;
    }
  }

  protected IColorScheme getColorScheme() {
    return getChart().getConfig().getColorScheme();
  }

  protected void setColorScheme(IColorScheme colorScheme) {
    IChartConfig config = getChart().getConfig();
    config.withColorScheme(colorScheme);
    getChart().setConfig(config);
  }

  protected void chartValueClick(BigDecimal xIndex, BigDecimal yIndex, Integer datasetIndex) {
    IHtmlContent message = HTML.fragment(
        HTML.h4("VALUE_CLICK event"),
        HTML.ul(
            HTML.li("xIndex: " + xIndex),
            HTML.li("yIndex: " + yIndex),
            HTML.li("datasetIndex: " + datasetIndex)));
    getDesktop().addNotification(new DesktopNotification(new Status(
        message.toHtml(), IStatus.INFO),
        TimeUnit.SECONDS.toMillis(2),
        true,
        true,
        null));
  }

  @Order(10)
  @ClassId("22cf5f11-d63a-4df2-8393-e592fb8411c3")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void execInitField() {
      super.execInitField();
      setStatusVisible(false);
    }

    @Order(10)
    @ClassId("a5bec8e6-0fd9-481b-b05c-62cc8a68b2af")
    public class TopBox extends AbstractGroupBox {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredBorderVisible() {
        return true;
      }

      @Override
      protected double getConfiguredGridWeightY() {
        return 0;
      }

      @Order(10)
      @ClassId("57c5849b-0071-49da-8fee-846e1a19a48b")
      public class ChartField extends AbstractChartField<IChart> {

        @Override
        protected int getConfiguredGridH() {
          return 10;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Order(10)
        @ClassId("4dea71db-c92d-4aea-9a5e-32f98ebcf616")
        public class Chart extends AbstractChart {

          @Override
          protected IChartConfig getConfiguredConfig() {
            return super.getConfiguredConfig()
                .withLineFill(false);
          }

          @Override
          protected void execValueClick(BigDecimal xIndex, BigDecimal yIndex, Integer datasetIndex) {
            chartValueClick(xIndex, yIndex, datasetIndex);
          }
        }
      }

      @Order(20)
      @ClassId("cd9ccf95-8f2b-47e1-8439-619b50d65a6f")
      public class ChartTileBox extends AbstractTileField<TileGrid> {

        @Override
        protected int getConfiguredGridH() {
          return 10;
        }

        @Override
        protected boolean getConfiguredVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          super.execInitField();
          getTileChart().addPropertyChangeListener(IChart.PROP_CONFIG, evt -> getChartTile().setColorScheme(getChart().getConfig().getColorScheme()));
        }

        @Order(10)
        @ClassId("c2cce503-463f-4d07-a6b7-8d581c99ea41")
        public class TileGrid extends AbstractTileGrid<ChartTile> {

          @Override
          protected boolean getConfiguredScrollable() {
            return false;
          }

          @Order(10)
          @ClassId("15f61f3c-801d-4871-89eb-9d69304bc4ab")
          public class ChartTile extends AbstractChartTile {

            @Override
            protected Boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected Boolean getConfiguredAutoColor() {
              return true;
            }

            @Override
            protected int getConfiguredGridWeightY() {
              return 1;
            }

            @Override
            protected void initTileWidgetConfig() {
              super.initTileWidgetConfig();
              IChartConfig config = getTileWidget().getChart().getConfig();
              config.withLineFill(false);
              getTileWidget().getChart().setConfig(config);
            }

            @Override
            protected void execValueClick(BigDecimal xIndex, BigDecimal yIndex, Integer datasetIndex) {
              chartValueClick(xIndex, yIndex, datasetIndex);
            }
          }
        }
      }
    }

    @Order(20)
    @ClassId("214ab49c-e24b-451a-b183-6e45fc7ed00f")
    public class BottomBox extends AbstractTabBox {

      @Order(10)
      @ClassId("eba111fd-25f7-4c58-b6cf-ed809d43e488")
      public class ChartPropertiesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Properties";
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 4;
        }

        @Order(10)
        @ClassId("f0d190d3-1427-4d14-bfeb-2a1cfd9acaf9")
        public class LeftBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          @ClassId("f4dd98dd-2071-418b-bebc-1a3807c44c20")
          public class AutoColorCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Auto color";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isAutoColor());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withAutoColor(getValue());
              getChart().setConfig(config);
            }
          }

          @Order(20)
          @ClassId("ea83afd7-1cd5-4004-8ff7-bf753444691e")
          public class ClickableCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Clickable";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isClickable());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withClickable(getValue());
              getChart().setConfig(config);
            }
          }

          @Order(30)
          @ClassId("4b98131b-c3ed-4694-b757-979c0936f685")
          public class CheckableCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Checkable";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isCheckable());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withCheckable(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.PIE, IChartType.DOUGHNUT, IChartType.POLAR_AREA, IChartType.RADAR, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.LINE, IChartType.COMBO_BAR_LINE,
                  IChartType.BUBBLE, IChartType.SCATTER));
            }
          }

          @Order(50)
          @ClassId("c3aac8ab-1b26-4c61-800e-6e7e23f5ad96")
          public class AnimatedCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Animated";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isAnimated());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withAnimated(getValue());
              getChart().setConfig(config);
            }
          }

          @Order(60)
          @ClassId("2f95cf2c-4d11-48cb-9ac7-b137b85468f9")
          public class LegendVisibleBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Legend visible";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isLegendDisplay());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withLegendDisplay(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(!ObjectUtility.isOneOf(newMasterValue, IChartType.SALESFUNNEL, IChartType.FULFILLMENT,
                  IChartType.SPEEDO, IChartType.VENN));
            }
          }

          @Order(70)
          @ClassId("92e92f90-0c63-4776-873e-b79e9bdb36b8")
          public class LegendClickableCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Legend Clickable";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Datasets can be shown and hidden using the legend";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isLegendClickable());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withLegendClickable(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.PIE, IChartType.DOUGHNUT, IChartType.POLAR_AREA, IChartType.RADAR, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.LINE, IChartType.COMBO_BAR_LINE,
                  IChartType.BUBBLE, IChartType.SCATTER));
            }
          }

          @Order(80)
          @ClassId("b30ec3b2-e1b7-4e61-9948-219032728e52")
          public class TooltipsEnabledBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Tooltips enabled";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isTooltipsEnabled());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withTooltipsEnabled(getValue());
              getChart().setConfig(config);
            }
          }

          @Order(90)
          @ClassId("07c3d778-c42c-4305-8a03-ee497636c0dd")
          public class DatalabelsVisibleCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Datalabels visible";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isDatalabelsDisplay());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withDatalabelsDisplay(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.PIE, IChartType.DOUGHNUT, IChartType.POLAR_AREA, IChartType.RADAR, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.LINE, IChartType.COMBO_BAR_LINE,
                  IChartType.BUBBLE));
            }
          }

          @Order(100)
          @ClassId("9f827d77-7f7d-49a6-b4f7-7890f4cbb3e7")
          public class XAxisStackedCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "x-Axis stacked";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isXAxisStacked());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withXAxisStacked(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.LINE, IChartType.COMBO_BAR_LINE));
            }
          }

          @Order(110)
          @ClassId("afd7b118-56e2-4fed-b91c-02c72bff52fc")
          public class YAxisStackedCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "y-Axis stacked";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isYAxisStacked());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withYAxisStacked(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.LINE, IChartType.COMBO_BAR_LINE));
            }
          }

          @Order(120)
          @ClassId("78e33fca-9006-40d9-b774-78e62b4cc643")
          public class FillCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Fill";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isLineFill());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withLineFill(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.LINE, IChartType.COMBO_BAR_LINE, IChartType.RADAR));
            }
          }

          @Order(130)
          @ClassId("a13d8d01-8611-4987-8444-326fd7cca935")
          public class TransparentCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Transparent";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().isTransparent());
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withTransparent(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.COMBO_BAR_LINE));
            }
          }

          @Order(140)
          @ClassId("a85aac12-f707-4a6b-a224-30be928fe1e9")
          public class AccordingToValuesCheckbox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Normalized";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(m_normalized);
            }

            @Override
            protected void execChangedValue() {
              m_normalized = getValue();
              IChartConfig chartConfig = getChart().getConfig();
              chartConfig.withProperty(IChartConfig.SALESFUNNEL_NORMALIZED, m_normalized);
              getChart().setConfig(chartConfig);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.SALESFUNNEL));
            }
          }

          @Order(150)
          @ClassId("0f8e0c0c-6802-4b11-b796-2a5aafb976db")
          public class FulfillmentStartValuePropertyCheckbox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Use start value property";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "If set animation starts from the current value. If not set, the animation returns to 0 and starts from 0.";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.FULFILLMENT));
            }
          }

          @Order(160)
          @ClassId("01c0528b-67fc-437a-9f69-86106aff21e0")
          public class TileCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Show inside tile";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              super.execChangedValue();
              AbstractChartField oldVisibleField = getValue() ? getChartField() : getTileChartField();
              AbstractChartField newVisibleField = getValue() ? getTileChartField() : getChartField();

              newVisibleField.setEnabled(oldVisibleField.isEnabled());
              newVisibleField.setVisible(oldVisibleField.isVisible());
              newVisibleField.setLabelVisible(oldVisibleField.isLabelVisible());
              newVisibleField.setLabelUseUiWidth(oldVisibleField.isLabelUseUiWidth());
              newVisibleField.setStatusVisible(oldVisibleField.isStatusVisible());
              newVisibleField.setMandatory(oldVisibleField.isMandatory());
              newVisibleField.setLoading(oldVisibleField.isLoading());
              newVisibleField.setDisabledStyle(oldVisibleField.getDisabledStyle());
              newVisibleField.setLabel(oldVisibleField.getLabel());
              newVisibleField.setLabelPosition(oldVisibleField.getLabelPosition());
              newVisibleField.setLabelWidthInPixel(oldVisibleField.getLabelWidthInPixel());
              newVisibleField.setTooltipText(oldVisibleField.getTooltipText());
              IMultiStatus errorStatus = oldVisibleField.getErrorStatus();
              if (errorStatus != null) {
                newVisibleField.setErrorStatus(errorStatus);
              }
              else {
                newVisibleField.clearErrorStatus();
              }

              getFormFieldPropertiesBox().setField(newVisibleField);

              oldVisibleField.setVisible(false);
              getChartTileBox().setVisible(getValue());

              newVisibleField.getChart().setData(oldVisibleField.getChart().getData());
              newVisibleField.getChart().setConfig(oldVisibleField.getChart().getConfig());
            }
          }
        }

        @Order(20)
        @ClassId("83557b2c-5e01-4e53-8527-4f5d131c2ba5")
        public class RightBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          @ClassId("edc4c52e-2b7e-492f-84f1-3cf283e893cb")
          public class ChartTypeField extends AbstractSmartField<String> {

            @Override
            protected String getConfiguredLabel() {
              return "Chart Type";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return ChartTypeLookupCall.class;
            }

            @Override
            protected void execInitField() {
              setValue(IChartType.PIE);
              fireValueChanged();
            }

            @Override
            protected void execChangedValue() {
              IChart chart = getChart();
              String chartType = StringUtility.emptyIfNull(getValue());
              switch (chartType) {
                case IChartType.PIE:
                case IChartType.LINE:
                case IChartType.BAR:
                case IChartType.BAR_HORIZONTAL:
                case IChartType.COMBO_BAR_LINE:
                case IChartType.FULFILLMENT:
                case IChartType.SPEEDO:
                case IChartType.SALESFUNNEL:
                case IChartType.VENN:
                case IChartType.DOUGHNUT:
                case IChartType.POLAR_AREA:
                case IChartType.RADAR:
                case IChartType.BUBBLE:
                case IChartType.SCATTER:
                  IChartConfig config = chart.getConfig();
                  config.withType(chartType);
                  if (!getXAxisStackedCheckBox().isEnabled() && !getYAxisStackedCheckBox().isEnabled()) {
                    config.removeScales();
                  }
                  else {
                    if (getXAxisStackedCheckBox().isEnabled()) {
                      config.withXAxisStacked(getXAxisStackedCheckBox().getValue());
                    }
                    else {
                      config.removeXAxisStacked();
                    }
                    if (getYAxisStackedCheckBox().isEnabled()) {
                      config.withYAxisStacked(getYAxisStackedCheckBox().getValue());
                    }
                    else {
                      config.removeYAxisStacked();
                    }
                  }
                  if (getTransparentCheckBox().isEnabled()) {
                    config.withTransparent(getTransparentCheckBox().getValue());
                  }
                  else {
                    config.removeTransparent();
                  }
                  chart.setConfig(config);
                  break;
                default:
                  throw new VetoException("Unknown chart type: " + chartType);
              }
              renewData();
            }
          }

          @Order(20)
          @ClassId("b899297b-0bc4-4c85-8c41-0e82cbe8dc04")
          public class ColorSchemeField extends AbstractSmartField<IColorScheme> {
            @Override
            protected String getConfiguredLabel() {
              return "Color Scheme";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected Class<? extends ILookupCall<IColorScheme>> getConfiguredLookupCall() {
              return TileColorSchemeLookupCall.class;
            }

            @Override
            protected void execInitField() {
              setValue(getColorScheme());
            }

            @Override
            protected void execChangedValue() {
              setColorScheme(getValue());
            }
          }

          @Order(30)
          @ClassId("1981e4fa-3218-4489-a301-789dc23be0b7")
          public class TensionField extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return "Tension";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return -1;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected BigDecimal getConfiguredMinValue() {
              return BigDecimal.ZERO;
            }

            @Override
            protected BigDecimal getConfiguredMaxValue() {
              return BigDecimal.ONE;
            }

            @Override
            protected boolean getConfiguredUpdateDisplayTextOnModify() {
              return true;
            }

            @Override
            protected void execInitField() {
              setValue(ObjectUtility.nvl(getChart().getConfig().getLineTension(), BigDecimal.ZERO));
            }

            @Override
            protected BigDecimal execValidateValue(BigDecimal rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return super.execValidateValue(rawValue);
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withLineTension(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.LINE, IChartType.COMBO_BAR_LINE, IChartType.RADAR));
            }
          }

          @Order(40)
          @ClassId("e534c5d0-4f06-4ee1-a9ec-40c58257d33f")
          public class GreenAreaPositionField extends AbstractSmartField<String> {
            @Override
            protected String getConfiguredLabel() {
              return "Green area position";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return P_SpeedoGreenAreaPositionLookupCall.class;
            }

            @Override
            protected void execInitField() {
              setValue(IChartConfig.CENTER);
              fireValueChanged();
            }

            @Override
            protected String execValidateValue(String rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return rawValue;
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withProperty(IChartConfig.SPEEDO_CHART_GREEN_AREA_POSITION, getValue());
              getChart().setConfig(config);
            }

            @Override
            protected String getConfiguredDisplayStyle() {
              return DISPLAY_STYLE_DROPDOWN;
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(newMasterValue == IChartType.SPEEDO);
            }
          }

          @Order(50)
          @ClassId("bff8adea-ba65-4e2a-a515-fac68b34b764")
          public class SizeOfLargestBubbleField extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return "Size of largest bubble";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return -1;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected Integer getConfiguredMinValue() {
              return 0;
            }

            @Override
            protected boolean getConfiguredUpdateDisplayTextOnModify() {
              return true;
            }

            @Override
            protected void execInitField() {
              Object configSizeOfLargestBubble = getChart().getConfig().getProperty(IChartConfig.BUBBLE_SIZE_OF_LARGEST_BUBBLE);
              if (configSizeOfLargestBubble instanceof Number) {
                setValue(NumberUtility.toInteger((Number) configSizeOfLargestBubble));
              }
              else {
                setValue(25);
                IChartConfig config = getChart().getConfig();
                config.withProperty(IChartConfig.BUBBLE_SIZE_OF_LARGEST_BUBBLE, 25);
                getChart().setConfig(config);
              }
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return super.execValidateValue(rawValue);
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withProperty(IChartConfig.BUBBLE_SIZE_OF_LARGEST_BUBBLE, getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.BUBBLE));
            }
          }

          @Order(60)
          @ClassId("95c9e74d-7da1-4ee2-8be5-d0c5f5d1d499")
          public class MinBubbleSizeField extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return "Min bubble size";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return -1;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected Integer getConfiguredMinValue() {
              return 0;
            }

            @Override
            protected boolean getConfiguredUpdateDisplayTextOnModify() {
              return true;
            }

            @Override
            protected void execInitField() {
              Object configMinBubbleSize = getChart().getConfig().getProperty(IChartConfig.BUBBLE_MIN_BUBBLE_SIZE);
              if (configMinBubbleSize instanceof Number) {
                setValue(NumberUtility.toInteger((Number) configMinBubbleSize));
              }
              else {
                setValue(0);
                IChartConfig config = getChart().getConfig();
                config.withProperty(IChartConfig.BUBBLE_MIN_BUBBLE_SIZE, 0);
                getChart().setConfig(config);
              }
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return super.execValidateValue(rawValue);
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withProperty(IChartConfig.BUBBLE_MIN_BUBBLE_SIZE, getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.BUBBLE));
            }
          }

          @Order(70)
          @ClassId("b2e922ba-df16-4c5f-83b2-126bb1a30157")
          public class LegendPositionField extends AbstractSmartField<String> {

            @Override
            protected String getConfiguredLabel() {
              return "Legend position";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return LegendPositionLookupCall.class;
            }

            @Override
            protected void execInitField() {
              setValue(IChartConfig.RIGHT);
              fireValueChanged();
            }

            @Override
            protected String execValidateValue(String rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return rawValue;
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withLegendPosition(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(!ObjectUtility.isOneOf(newMasterValue, IChartType.SALESFUNNEL, IChartType.FULFILLMENT,
                  IChartType.SPEEDO, IChartType.VENN));
            }
          }
        }

        @Order(30)
        @ClassId("dca5f636-6657-4c58-9714-59196e24f3c1")
        public class CustomChartPropertiesBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredLabel() {
            return "Custom Chart Properties";
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Override
          protected int getConfiguredGridW() {
            return 4;
          }

          @Override
          protected boolean getConfiguredExpandable() {
            return true;
          }

          @Override
          protected boolean getConfiguredExpanded() {
            return false;
          }

          @Order(10)
          @ClassId("413d89f5-7e7b-48f0-be66-db4735519cd5")
          public class InfoField extends AbstractLabelField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(HTML.fragment(
                  "For detailed information about possible properties see ",
                  HTML.link("https://www.chartjs.org/", "chart.js"),
                  ". The custom chart properties need to be a JSON object.").toHtml());
            }

            @Override
            protected boolean getConfiguredHtmlEnabled() {
              return true;
            }

            @Override
            protected boolean getConfiguredWrapText() {
              return true;
            }

            @Override
            protected boolean getConfiguredGridUseUiHeight() {
              return true;
            }
          }

          @Order(20)
          @ClassId("ad85583e-efae-494d-89cd-7d69f953011f")
          public class CustomChartPropertiesField extends AbstractStringField {

            private IChartConfig m_oldCustomConfig = BEANS.get(IChartConfig.class);

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected int getConfiguredGridH() {
              return 6;
            }

            @Override
            protected boolean getConfiguredMultilineText() {
              return true;
            }

            @Override
            protected boolean getConfiguredWrapText() {
              return true;
            }

            @Override
            protected int getConfiguredMaxLength() {
              return 1024 * 1024;
            }

            @Override
            protected boolean computeSpellCheckEnabled() {
              return false;
            }

            @Override
            protected String getConfiguredCssClass() {
              return "json-field";
            }

            @Override
            protected String execValidateValue(String rawValue) {
              String result = super.execValidateValue(rawValue);
              if (StringUtility.hasText(result)) {
                JSONObject json = new JSONObject(result);
                result = json.toString(2);
              }
              return result;
            }

            @Override
            protected void execChangedValue() {
              super.execChangedValue();

              IChartConfig customConfig = BEANS.get(IChartConfig.class);
              if (StringUtility.hasText(getValue())) {
                JSONObject json = new JSONObject(getValue());
                handleJSONObject(customConfig, json);
              }

              IChartConfig chartConfig = getChart().getConfig();

              // handle removed custom properties
              m_oldCustomConfig.getPropertiesFlat().keySet().stream()
                  .filter(property -> customConfig.getProperty(property) == null)
                  .forEach(chartConfig::removeProperty);

              chartConfig.addProperties(customConfig, true);
              getChart().setConfig(chartConfig);

              m_oldCustomConfig = customConfig;
            }

            private void handleJSONObject(IChartConfig config, JSONObject json) {
              handleJSONObject(config, json, null);
            }

            /**
             * Adds the custom properties from the given JSONObject the the given IChartConfig recursively.
             */
            private void handleJSONObject(IChartConfig config, JSONObject json, String parentPath) {
              if (config == null || json == null) {
                return;
              }

              json.keySet().forEach(key -> {
                String path = StringUtility.join(".", parentPath, key);
                Object obj = json.get(key);
                if (obj instanceof JSONObject) {
                  handleJSONObject(config, (JSONObject) obj, path);
                }
                else if (obj instanceof JSONArray) {
                  handleJSONArray(config, (JSONArray) obj, path);
                }
                else if (obj != null && isCustomProperty(path)) {
                  // only properties that are not already set by CheckBoxes, SmartFields, etc.
                  config.withProperty(path, obj);
                }
              });
            }

            private void handleJSONArray(IChartConfig config, JSONArray json, String parentPath) {
              if (config == null || json == null || !StringUtility.hasText(parentPath)) {
                return;
              }

              IntStream.range(0, json.length()).forEach(index -> {
                String path = parentPath + "[" + index + "]";
                Object obj = json.get(index);
                if (obj instanceof JSONObject) {
                  handleJSONObject(config, (JSONObject) obj, path);
                }
              });
            }

            private boolean isCustomProperty(String property) {
              return getChart().getConfig().getProperty(property) == null || m_oldCustomConfig.getProperty(property) != null;
            }
          }
        }

        @Order(40)
        @ClassId("628f44b5-4c0a-4487-8ad8-abacb477352d")
        public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

          @Override
          protected void execInitField() {
            setField(getChartField());
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected int getConfiguredGridW() {
            return 4;
          }

          @Override
          protected boolean getConfiguredExpanded() {
            return false;
          }
        }
      }

      @Order(20)
      @ClassId("7a920c42-8ba8-49f2-93e2-962e3efb0541")
      public class ChartDataBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Data";
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 3;
        }

        @Order(10)
        @ClassId("ea67f8a2-bde4-4a87-81b8-a6a9e0c22c4b")
        public class LeftBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected double getConfiguredGridWeightY() {
            return 1;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          @ClassId("f064f5da-0e3b-486a-92fd-0dfff8c86ce1")
          public class ChartDataTableField extends AbstractTableField<Table> {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              if (IChartType.COMBO_BAR_LINE.equals(newMasterValue)) {
                setTooltipText("Add '|line' to a dataset label if the dataset should be displayed as line.");
              }
              else {
                setTooltipText(null);
              }
            }

            @Override
            protected void execInitField() {
              // Hide organize column menu
              OrganizeColumnsMenu organizeColumnsMenu = getTable().getMenuByClass(OrganizeColumnsMenu.class);
              if (organizeColumnsMenu != null) {
                organizeColumnsMenu.setVisibleGranted(false);
              }
            }

            protected Object[][] getChartData() {
              return getTable().getChartData();
            }

            protected void setChartData(Object[][] chartData) {
              getTable().setChartData(chartData);
            }

            @ClassId("647d2881-7d01-466f-b7e8-fc5b77f8f129")
            public class Table extends AbstractTable {

              private List<String> m_dataLabels = CollectionUtility.emptyArrayList();

              protected Object[] getDataLabels() {
                Object[] result = new Object[m_dataLabels.size() + 1];
                System.arraycopy(m_dataLabels.toArray(), 0, result, 1, m_dataLabels.size());
                return result;
              }

              @Override
              protected void injectColumnsInternal(OrderedCollection<IColumn<?>> columnList) {
                if (m_dataLabels != null) {
                  m_dataLabels.forEach(label -> columnList.addLast(new AbstractDataColumn() {
                    @Override
                    protected String getConfiguredHeaderText() {
                      return label;
                    }
                  }));
                }
                super.injectColumnsInternal(columnList);
              }

              protected void addColumn(String label) {
                m_dataLabels.add(label);
                updateColumns();
              }

              protected void removeColumn() {
                if (!CollectionUtility.isEmpty(m_dataLabels)) {
                  m_dataLabels.remove(m_dataLabels.size() - 1);
                }
                updateColumns();
              }

              protected void updateColumns() {
                Object[][] tableData = getTableData();
                resetColumnConfiguration();
                replaceRowsByMatrix(tableData);
                getRemoveDataMenu().setEnabled(m_dataLabels.size() > 0);
                renewData();
              }

              protected Object[][] getChartData() {
                Object[][] tableData = getTableData();
                Object[][] result = new Object[tableData.length + 1][];
                result[0] = getDataLabels();
                System.arraycopy(tableData, 0, result, 1, tableData.length);
                return result;
              }

              protected void setChartData(Object[][] chartData) {
                if (chartData.length == 0 || chartData[0].length == 0) {
                  return;
                }
                if (chartData[0].length > 0) {
                  m_dataLabels = Stream.of(Arrays.copyOfRange(chartData[0], 1, chartData[0].length))
                      .map(label -> (String) label)
                      .collect(Collectors.toList());
                }
                if (chartData.length == 1) {
                  return;
                }
                Object[][] tableData = Arrays.copyOfRange(chartData, 1, chartData.length);
                resetColumnConfiguration();
                replaceRowsByMatrix(tableData);
                getRemoveDataMenu().setEnabled(m_dataLabels.size() > 0);
              }

              @Override
              protected boolean getConfiguredSortEnabled() {
                return false;
              }

              @Override
              protected boolean getConfiguredHeaderMenusEnabled() {
                return false;
              }

              public DatasetLabelColumn getDatasetLabelColumn() {
                return getColumnSet().getColumnByClass(DatasetLabelColumn.class);
              }

              public RemoveDataMenu getRemoveDataMenu() {
                return getMenuByClass(RemoveDataMenu.class);
              }

              @Order(-1)
              @ClassId("3f6a19a5-4a3c-45c7-a10d-86757688127a")
              public class DatasetLabelColumn extends AbstractStringColumn {
                @Override
                protected boolean getConfiguredEditable() {
                  return true;
                }

                @Override
                protected int getConfiguredWidth() {
                  return 250;
                }

                @Override
                protected boolean getConfiguredFixedPosition() {
                  return true;
                }

                @Override
                protected boolean getConfiguredFixedWidth() {
                  return true;
                }

                @Override
                public String validateValue(ITableRow row, String rawValue) {
                  if (StringUtility.isNullOrEmpty(rawValue)) {
                    return "Dataset " + ((row.getRowIndex() == -1 ? getRowCount() : row.getRowIndex()) + 1);
                  }
                  return super.validateValue(row, rawValue);
                }

                @Override
                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                  super.execCompleteEdit(row, editingField);
                  renewData();
                }
              }

              @ClassId("09caace2-59d0-43c6-91d7-0d89591261ab")
              public abstract class AbstractDataColumn extends AbstractBigDecimalColumn {

                @Override
                protected boolean getConfiguredEditable() {
                  return true;
                }

                @Override
                protected int getConfiguredWidth() {
                  return 120;
                }

                @Override
                protected boolean getConfiguredFixedPosition() {
                  return true;
                }

                @Override
                protected boolean getConfiguredFixedWidth() {
                  return true;
                }

                @Override
                public BigDecimal getMaxValue() {
                  return ChartFieldForm.this.getMaxValue();
                }

                @Override
                public BigDecimal getMinValue() {
                  return ChartFieldForm.this.getMinValue();
                }

                @Override
                public BigDecimal validateValue(ITableRow row, BigDecimal rawValue) {
                  if (rawValue == null) {
                    return BigDecimal.ZERO;
                  }
                  return super.validateValue(row, rawValue);
                }

                @Override
                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                  super.execCompleteEdit(row, editingField);
                  renewData();
                }
              }

              @Order(100)
              @ClassId("27f76b4a-2729-4705-9ea0-56ef576fe998")
              public class AddDatasetMenu extends AbstractMenu {

                @Override
                protected String getConfiguredText() {
                  return "Add dataset";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.<IMenuType> hashSet(
                      TableMenuType.EmptySpace,
                      TableMenuType.SingleSelection,
                      TableMenuType.MultiSelection);
                }

                @Override
                protected void execAction() {
                  super.execAction();
                  addRow();
                  renewData();
                }
              }

              @Order(110)
              @ClassId("e4172607-12d2-4cef-b8fc-ef93fce5842a")
              public class RemoveDatasetMenu extends AbstractMenu {

                @Override
                protected String getConfiguredText() {
                  return "Remove dataset";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.<IMenuType> hashSet(
                      TableMenuType.SingleSelection,
                      TableMenuType.MultiSelection);
                }

                @Override
                protected void execAction() {
                  super.execAction();
                  deleteRows(getSelectedRows());
                  renewData();
                }
              }

              @Order(120)
              @ClassId("f93f1d44-4602-44e4-baf6-3e1d66491072")
              public class AddDataMenu extends AbstractMenu {

                @Override
                protected String getConfiguredIconId() {
                  return AbstractIcons.Plus;
                }

                @Override
                protected String getConfiguredTooltipText() {
                  return "Add data";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.<IMenuType> hashSet(
                      TableMenuType.Header);
                }

                @Override
                protected void execAction() {
                  AbstractInputForm form = new AbstractInputForm() {
                  };
                  form.start();
                  form.waitFor();
                  if (form.isFormStored()) {
                    addColumn(form.getInput());
                  }
                }
              }

              @ClassId("26a1f001-ad54-4a37-a7ee-e75c677d9f86")
              abstract class AbstractInputForm extends AbstractForm {

                @Override
                protected String getConfiguredTitle() {
                  return "Data label";
                }

                public String getInput() {
                  return getFieldByClass(InputField.class).getValue();
                }

                @Order(10)
                @ClassId("59a01a83-1ccc-4af6-be7f-9cf12c7ddae8")
                public class InputMainBox extends AbstractGroupBox {

                  @Order(10)
                  @ClassId("f7119abc-9587-445f-a01a-70ddf7f23132")
                  public class InputGroupBox extends AbstractGroupBox {

                    @Order(10)
                    @ClassId("dae2316e-0a18-4f67-afae-d97ab4af24d3")
                    public class InputField extends AbstractStringField {

                      @Override
                      protected String getConfiguredLabel() {
                        return "Data label";
                      }

                      @Override
                      protected boolean getConfiguredLabelVisible() {
                        return false;
                      }

                      @Override
                      protected boolean getConfiguredMandatory() {
                        return true;
                      }
                    }
                  }

                  @Order(10)
                  @ClassId("734f80d2-8477-4556-9b5e-9dfb2d29cb29")
                  public class OkButton extends AbstractOkButton {
                  }

                  @Order(20)
                  @ClassId("e21d0a1b-78f2-40fa-86db-007fa5d08a14")
                  public class CancelButton extends AbstractCancelButton {
                  }
                }

              }

              @Order(150)
              @ClassId("9348656d-4069-4214-ba3b-c6e3f40e23fc")
              public class RemoveDataMenu extends AbstractMenu {

                @Override
                protected String getConfiguredIconId() {
                  return AbstractIcons.Minus;
                }

                @Override
                protected String getConfiguredTooltipText() {
                  return "Remove data";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.<IMenuType> hashSet(
                      TableMenuType.Header);
                }

                @Override
                protected void execAction() {
                  removeColumn();
                }

                @Override
                protected boolean getConfiguredEnabled() {
                  return false;
                }
              }
            }
          }
        }

        @Order(20)
        @ClassId("c14fd88e-ee36-4a3b-ba38-dc44cb50dcfc")
        public class RightBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 4;
          }

          @Override
          protected double getConfiguredGridWeightY() {
            return 1;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          @ClassId("c0a219eb-1edd-48ff-abe8-2099e8467be5")
          public class RandomCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Random";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected int getConfiguredGridW() {
              return 1;
            }

            @Override
            protected void execInitField() {
              setValue(true);
            }

            @Override
            protected void execChangedValue() {
              getChartDataTableField().setEnabled(!getValue());
              getRandomDataButton().setEnabled(getValue());
              getChartTypeField().fireValueChanged();
              if (getValue()) {
                getChartDataTableField().clearErrorStatus();
              }
              else {
                getValuesProviderField().setEnabled(false);
                getNumberOfDatasetsField().setEnabled(false);
              }
            }
          }

          @Order(20)
          @ClassId("91630418-f3a4-43ac-ace1-22047cf88ff0")
          public class FillTableCheckBox extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Fill Table";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected int getConfiguredGridW() {
              return 1;
            }

            @Override
            protected void execInitField() {
              setValue(true);
            }

            @Override
            protected void execChangedValue() {
              if (getValue()) {
                renewData();
              }
            }
          }

          @Order(30)
          @ClassId("2b74b6dd-28a0-4e12-b72d-231b99715688")
          public class RandomDataButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Generate random data";
            }

            @Override
            protected String getConfiguredKeyStroke() {
              return "enter";
            }

            @Override
            protected int getConfiguredGridW() {
              return 2;
            }

            @Override
            protected void execClickAction() {
              renewData();
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }
          }

          @Order(40)
          @ClassId("208a0b37-cbb0-4a69-a8cf-69291137438d")
          public class ValuesProviderField extends AbstractSmartField<Integer> {

            @Override
            protected String getConfiguredLabel() {
              return "Values";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected int getConfiguredGridW() {
              return 4;
            }

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
              return ValuesProviderLookupCall.class;
            }

            @Override
            protected void execInitField() {
              setValue(VALUE_PROVIDER_RANDOM);
              fireValueChanged();
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return rawValue;
            }

            @Override
            protected void execChangedValue() {
              renewData();
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.LINE, IChartType.BAR, IChartType.BAR_HORIZONTAL, IChartType.COMBO_BAR_LINE,
                  IChartType.BUBBLE, IChartType.SCATTER));
            }
          }

          @Order(50)
          @ClassId("fa2185e2-8947-41f9-bce8-860b4a737c61")
          public class NumberOfDatasetsField extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return "Number of datasets";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected int getConfiguredGridW() {
              return 4;
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return -1;
            }

            @Override
            protected boolean getConfiguredUpdateDisplayTextOnModify() {
              return true;
            }

            @Override
            protected void execInitField() {
              if (ObjectUtility.equals(getChartTypeField().getValue(), IChartType.VENN)) {
                setValue(m_numberOfVennCircles);
              }
              else {
                setValue(m_numberOfDatasets);
              }
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              if (rawValue == null) {
                if (ObjectUtility.equals(getChartTypeField().getValue(), IChartType.VENN)) {
                  return DEFAULT_NUMBER_OF_VENN_CIRCLES;
                }
                return DEFAULT_NUMBER_OF_DATASETS;
              }
              return super.execValidateValue(rawValue);
            }

            @Override
            protected void execChangedValue() {
              if (ObjectUtility.equals(getChartTypeField().getValue(), IChartType.VENN)) {
                m_numberOfVennCircles = getValue();
              }
              else {
                m_numberOfDatasets = getValue();
              }
              renewData();
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setFieldChanging(true);
              try {
                if (ObjectUtility.isOneOf(newMasterValue,
                    IChartType.PIE,
                    IChartType.BAR,
                    IChartType.BAR_HORIZONTAL,
                    IChartType.LINE,
                    IChartType.COMBO_BAR_LINE,
                    IChartType.SALESFUNNEL,
                    IChartType.DOUGHNUT,
                    IChartType.POLAR_AREA,
                    IChartType.RADAR,
                    IChartType.BUBBLE,
                    IChartType.SCATTER)) {
                  setEnabled(true);
                  setMinValue(0);
                  setMaxValue(null);
                  setValue(m_numberOfDatasets);
                  getRandomNumberOfDatasetsMenu().setEnabled(true);
                }
                else if (ObjectUtility.equals(newMasterValue, IChartType.VENN)) {
                  setEnabled(true);
                  setMinValue(1);
                  setMaxValue(3);
                  setValue(m_numberOfVennCircles);
                  getRandomNumberOfDatasetsMenu().setEnabled(false);
                }
                else {
                  setEnabled(false);
                  getRandomNumberOfDatasetsMenu().setEnabled(false);
                }
              }
              finally {
                setFieldChanging(false);
              }
            }

            public RandomNumberOfDatasetsMenu getRandomNumberOfDatasetsMenu() {
              return getMenuByClass(RandomNumberOfDatasetsMenu.class);
            }

            @Order(50)
            @ClassId("7fd3f10d-9a00-439d-ba35-2069ade4bb15")
            public class RandomNumberOfDatasetsMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Set random number";
              }

              @Override
              protected void execAction() {
                NumberOfDatasetsField field = getNumberOfDatasetsField();
                Integer randomValue = field.getValue();
                while (randomValue == null || ObjectUtility.equals(randomValue, field.getValue())) {
                  randomValue = Math.max(NumberUtility.randomInt(DEFAULT_NUMBER_OF_DATASETS * 2), 1);
                }
                field.setValue(randomValue);
              }
            }
          }

          @Order(60)
          @ClassId("6e5b5b43-d75a-4b3b-a8a5-e72d4a050784")
          public class MaxSegmentsField extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return "Max. segments";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected int getConfiguredGridW() {
              return 4;
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return -1;
            }

            @Override
            protected Integer getConfiguredMinValue() {
              return 1;
            }

            @Override
            protected boolean getConfiguredUpdateDisplayTextOnModify() {
              return true;
            }

            @Override
            protected void execInitField() {
              setValue(getChart().getConfig().getMaxSegments());
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              if (rawValue == null) {
                return getInitValue();
              }
              return super.execValidateValue(rawValue);
            }

            @Override
            protected void execChangedValue() {
              IChartConfig config = getChart().getConfig();
              config.withMaxSegments(getValue());
              getChart().setConfig(config);
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return ChartTypeField.class;
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              setEnabled(ObjectUtility.isOneOf(newMasterValue, IChartType.PIE, IChartType.DOUGHNUT, IChartType.POLAR_AREA, IChartType.RADAR));
            }
          }
        }
      }
    }

    @Order(200)
    @ClassId("606dbde9-aa8d-46dd-bd15-9dcd81c87622")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class TestHandler extends AbstractFormHandler {

    @Override
    protected void execPostLoad() {
      renewData();
    }
  }

  @ClassId("003ef2c0-4546-43b7-bba6-cf0e9a77322b")
  public static class LegendPositionLookupCall extends LocalLookupCall<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      List<LookupRow<String>> result = new ArrayList<>();
      result.add(new LookupRow<>(IChartConfig.RIGHT, "Right"));
      result.add(new LookupRow<>(IChartConfig.LEFT, "Left"));
      result.add(new LookupRow<>(IChartConfig.TOP, "Top"));
      result.add(new LookupRow<>(IChartConfig.BOTTOM, "Bottom"));
      return result;
    }
  }

  @ClassId("1c2d546c-6a86-47dd-b4e1-64941f9959b0")
  public static class ValuesProviderLookupCall extends LocalLookupCall<Integer> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
      List<LookupRow<Integer>> result = new ArrayList<>();
      result.add(new LookupRow<>(VALUE_PROVIDER_RANDOM, "Random"));
      result.add(new LookupRow<>(VALUE_PROVIDER_RANDOM_POSITIVE, "Random (only positive)"));
      result.add(new LookupRow<>(VALUE_PROVIDER_ALL_0, "All 0"));
      result.add(new LookupRow<>(VALUE_PROVIDER_ALL_1, "All 1"));
      result.add(new LookupRow<>(VALUE_PROVIDER_ALL_50000, "All 50'000"));
      result.add(new LookupRow<>(VALUE_PROVIDER_RANDOM_500000, "Random -500'000 to 500'000"));
      return result;
    }
  }

  @ClassId("31f82076-fe0b-43a7-a940-5a28b7abbb16")
  public static class P_SpeedoGreenAreaPositionLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      List<ILookupRow<String>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(IChartConfig.LEFT, "Left"));
      rows.add(new LookupRow<>(IChartConfig.CENTER, "Center"));
      rows.add(new LookupRow<>(IChartConfig.RIGHT, "Right"));
      return rows;
    }
  }
}
