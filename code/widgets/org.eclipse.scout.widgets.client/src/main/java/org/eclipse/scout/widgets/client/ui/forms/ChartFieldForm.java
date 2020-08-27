/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.configuration.AbstractFormFieldConfigurationBox;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.AbstractChartField;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartConfigDo;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartDataDo;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartDatasetDo;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.IChartField;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ButtonBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ButtonBox.RandomDataButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ButtonBox.ReloadConfigButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ChartTypeField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ColorSetField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.DataLengthField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.DataSeriesField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.FormFieldConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.LinkButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.PlaceholderField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ExamplesBox.ChartField;

@ClassId("81bb570f-72ed-4b4c-be41-77ae5e9dd554")
public class ChartFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public static final String CONFIG_LINE = "/chart/lineConfig.json";
  public static final String CONFIG_LINE_STACKED = "/chart/lineStackedConfig.json";
  public static final String CONFIG_PIE = "/chart/pieConfig.json";
  public static final String CONFIG_DOUGHNUT = "/chart/doughnutConfig.json";

  protected static final int DEFAULT_DATA_SERIES = 1;
  protected static final int DEFAULT_DATA_LENGTH = 6;

  protected static final String[] DEFAULT_LABELS = {"January", "February", "March", "April", "May", "June",
    "Juli", "August", "September", "October", "November", "December"};

  protected static final String[] COLOR_SET_SPEEDO = {
      "dc2938",
      "fe9915",
      "24b499",
      "075163"};

  protected static final String[] COLOR_SET_CHART_JS = {
      "ff6384",
      "36a2eb",
      "ffce56",
      "4bc0c0",
      "9966ff",
      "ff9f40"};

  protected static final String[] COLOR_SET_ECLIPSE_SCOUT = {
      "006C86",
      "0083A1",
      "1AA8C0",
      "1DBBD6",
      "12CBF7",
      "A1E9FF",
      "1CA088",
      "24B499",
      "38CCAA",
      "4FE3C1",
      "80ECD4",
      "A6F7E6",
      "012345"};

  protected int m_previousDataLength = DEFAULT_DATA_LENGTH;
  protected int m_previousDataSeries = DEFAULT_DATA_SERIES;

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ChartField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public DataSeriesField getDataSeriesField() {
    return getFieldByClass(DataSeriesField.class);
  }

  public ChartTypeField getChartTypeField() {
    return getFieldByClass(ChartTypeField.class);
  }

  public ReloadConfigButton getReloadConfigButton() {
    return getFieldByClass(ReloadConfigButton.class);
  }

  public DataLengthField getDataLengthField() {
    return getFieldByClass(DataLengthField.class);
  }

  public ButtonBox getMySequenceBox() {
    return getFieldByClass(ButtonBox.class);
  }

  public ColorSetField getColorSetField() {
    return getFieldByClass(ColorSetField.class);
  }

  public LinkButton getLinkButton() {
    return getFieldByClass(LinkButton.class);
  }

  public PlaceholderField getPlaceholderField() {
    return getFieldByClass(PlaceholderField.class);
  }

  public RandomDataButton getRandomDataButton() {
    return getFieldByClass(RandomDataButton.class);
  }

  public FormFieldConfigurationBox getFormFieldConfigurationBox() {
    return getFieldByClass(FormFieldConfigurationBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ChartField getChartField() {
    return getFieldByClass(ChartField.class);
  }

  protected void updateChartData() {
    updateChartData(false);
  }

  protected void updateChartData(boolean forceNewDataset) {
    int dataSeries = ObjectUtility.nvl(getDataSeriesField().getValue(), DEFAULT_DATA_SERIES);
    int dataLength = ObjectUtility.nvl(getDataLengthField().getValue(), DEFAULT_DATA_LENGTH);

    boolean updateDatasets;
    if (forceNewDataset) {
      updateDatasets = false;
    }
    else {
      updateDatasets = m_previousDataSeries <= dataSeries && m_previousDataLength <= dataLength;
    }

    getChartField().setChartData(randomChartData(dataSeries, dataLength)
        .withUpdateDatasets(updateDatasets));
    m_previousDataSeries = dataSeries;
    m_previousDataLength = dataLength;
  }

  protected String[] fillStringArray(String[] defaults, int requestedLength, Function<Integer, String> valueProvider) {
    List<String> values;
    if (requestedLength > defaults.length) {
      values = new ArrayList<>(requestedLength);
      values.addAll(Arrays.asList(defaults));
      int valuesToAdd = requestedLength - defaults.length;
      for (int i = 0; i < valuesToAdd; i++) {
        values.add(valueProvider.apply(i));
      }
    }
    else {
      values = Arrays.asList(Arrays.copyOfRange(defaults, 0, requestedLength));
    }
    return values.toArray(new String[0]);
  }

  protected String[] repeatValue(String value, int requestedLength) {
    List<String> values = new ArrayList<>(requestedLength);
    for (int i = 0; i < requestedLength; i++) {
      values.add(value);
    }
    return values.toArray(new String[0]);
  }

  protected ChartDataDo randomChartData(int dataSeries, int dataLength) {
    String[] labels = fillStringArray(DEFAULT_LABELS, dataLength, i -> "Month #" + i);

    String chartType = getChartTypeField().getValue();
    boolean lineChart = ObjectUtility.isOneOf(chartType, CONFIG_LINE, CONFIG_LINE_STACKED);
    int requestedLength = lineChart ? dataSeries : dataLength;

    String[] defaultBackgroundColors = createColors(0.4f);
    String[] defaultBorderColors = createColors(1f);
    String[] backgroundColors = fillStringArray(defaultBackgroundColors, requestedLength,
        i -> defaultBackgroundColors[i % defaultBackgroundColors.length]);
    String[] borderColors = fillStringArray(defaultBorderColors, requestedLength,
        i -> defaultBorderColors[i % defaultBorderColors.length]);

    // data sets per series
    List<ChartDatasetDo> datasets = new ArrayList<>();
    for (int i = 0; i < dataSeries; i++) {
      int[] data = new int[dataLength];
      for (int j = 0; j < dataLength; j++) {
        data[j] = (int) (Math.random() * 9.0 + 1);
      }

      ChartDatasetDo chartDataset = BEANS.get(ChartDatasetDo.class)
          .withLabel("Series #" + (i + 1))
          .withData(data)
          .withBorderWidth(1);

      // Type specific dataset configuration
      if (chartType == CONFIG_LINE) {
        chartDataset.put("lineTension", 0.0);
        chartDataset.put("fill", false);
      }

      if (lineChart) {
        chartDataset.put("backgroundColor", backgroundColors[i]);
        chartDataset.put("borderColor", borderColors[i]);
      }
      else {
        chartDataset
            .withBackgroundColor(backgroundColors)
            .withBorderColor(borderColors);
      }

      datasets.add(chartDataset);
    }

    return BEANS.get(ChartDataDo.class)
        .withLabels(labels)
        .withDatasets(datasets);
  }

  protected String[] createColors(float opacity) {
    String[] colorSet = getColorSet();
    List<String> colors = new ArrayList<>(colorSet.length);
    for (int i = 0; i < colorSet.length; i++) {
      colors.add(toRgba(colorSet[i], opacity));
    }
    return colors.toArray(new String[0]);
  }

  protected String[] getColorSet() {
    Integer colorSet = getColorSetField().getValue();
    if (colorSet == null) {
      return COLOR_SET_ECLIPSE_SCOUT;
    }
    switch (colorSet) {
      case 2:
        return COLOR_SET_SPEEDO;
      case 3:
        return COLOR_SET_CHART_JS;
      default:
        return COLOR_SET_ECLIPSE_SCOUT;
    }
  }

  @Order(10)
  @ClassId("2a4eab55-903d-4f3b-bb96-d48a4aa0fc2b")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("a1138d26-ac89-427c-a96c-f77318b63844")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridW() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("0dc78e65-ea77-4e36-bd35-42d256b93fd1")
      public class ChartField extends AbstractChartField {

        @Override
        protected ChartConfigDo getConfiguredChartConfig() {
          return IChartField.readChartConfig(ChartFieldForm.class.getResourceAsStream(CONFIG_PIE));
        }

        @Override
        protected void execInitField() {
          super.execInitField();
          setChartData(randomChartData(DEFAULT_DATA_SERIES, DEFAULT_DATA_LENGTH));
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }
      }
    }

    @Order(30)
    @ClassId("f0937e48-0328-4c7f-a076-40a70602e392")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(1200)
      @ClassId("f8ce887d-a591-4937-9868-9b8f5a030db2")
      public class ChartTypeField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ChartType");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return ChartTypeLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(CONFIG_PIE);
        }

        @Override
        protected void execChangedValue() {
          ChartConfigDo config = IChartField.readChartConfig(ChartFieldForm.class.getResourceAsStream(getValue()));
          getChartField().setChartConfig(config);
          updateChartData(true);
        }
      }

      @Order(1350)
      @ClassId("4a056a87-05ac-45b9-bcf1-86be854ca4e3")
      public class ColorSetField extends AbstractSmartField<Integer> {
        @Override
        protected String getConfiguredLabel() {
          return "Color Set";
        }

        @Override
        protected void execInitField() {
          setValue(1);
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return ColorSetLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          updateChartData();
        }
      }

      @Order(1500)
      @ClassId("b371aab8-5179-4dad-8851-81a1e7f51289")
      public class DataSeriesField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DataSeriesCount");
        }

        @Override
        protected void execInitField() {
          setValue(DEFAULT_DATA_SERIES);
        }

        @Override
        protected Integer getConfiguredMinValue() {
          return 1;
        }

        @Override
        protected Integer getConfiguredMaxValue() {
          return 5;
        }

        @Override
        protected void execChangedValue() {
          updateChartData();
        }
      }

      @Order(1750)
      @ClassId("4a134cd6-611f-43ae-85d5-5309cabd1deb")
      public class DataLengthField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return "Data length";
        }

        @Override
        protected void execInitField() {
          setValue(DEFAULT_DATA_LENGTH);
        }

        @Override
        protected Integer getConfiguredMinValue() {
          return 1;
        }

        @Override
        protected Integer getConfiguredMaxValue() {
          return 250;
        }

        @Override
        protected void execChangedValue() {
          updateChartData();
        }
      }

      @Order(1800)
      @ClassId("301c6fdf-2b93-4686-8c82-c5b444e5d08c")
      public class ButtonBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(100)
        @ClassId("5188d289-562c-4388-9490-3e1d4f1a5231")
        public class RandomDataButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("RandomData");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            updateChartData();
          }
        }

        @Order(200)
        @ClassId("9de0291c-f5d2-4a0d-af54-7ed9ea4149e8")
        public class ReloadConfigButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return "Reload Config";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            String configFile = getChartTypeField().getValue();
            try (InputStream is = ChartFieldForm.class.getResourceAsStream(configFile)) {
              String config = IOUtility.readString(is, StandardCharsets.UTF_8.name());
              getChartField().setChartConfig(config);
            }
            catch (IOException e) {
              throw new ProcessingException("Failed to load config file", e);
            }
          }
        }
      }

      @Order(1900)
      @ClassId("c10f26f0-bb4c-49d4-a0ad-3c743c521538")
      public class PlaceholderField extends AbstractPlaceholderField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }
      }

      @Order(1950)
      @ClassId("47dd0094-67d7-4d72-8543-247afb878f35")
      public class LinkButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Powered by Chart.js";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return IButton.DISPLAY_STYLE_LINK;
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          getDesktop().openUri("https://www.chartjs.org/", OpenUriAction.NEW_WINDOW);
        }
      }

      @Order(2000)
      @ClassId("db8f1b66-18fe-4f37-8e22-75e0482fcb9c")
      public class FormFieldConfigurationBox extends AbstractFormFieldConfigurationBox {

        @Override
        protected IFormField getTargetField() {
          return getChartField();
        }
      }
    }

    @Order(40)
    @ClassId("ccb84308-013a-4bf7-8d2d-6a21de8ec526")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ClassId("b8e867ed-2602-45f2-8634-51d7babb5d1e")
  public static class ChartTypeLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      List<ILookupRow<String>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(CONFIG_LINE, "Line"));
      rows.add(new LookupRow<>(CONFIG_LINE_STACKED, "Line (Stacked)"));
      rows.add(new LookupRow<>(CONFIG_PIE, "Pie"));
      rows.add(new LookupRow<>(CONFIG_DOUGHNUT, "Doughnut"));
      return rows;
    }
  }

  @ClassId("6d12cd9a-1dd3-455f-9a13-4900a29b33ec")
  public static class ColorSetLookupCall extends LocalLookupCall<Integer> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
      List<ILookupRow<Integer>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(1, "Eclipse Scout"));
      rows.add(new LookupRow<>(2, "Speedo"));
      rows.add(new LookupRow<>(3, "Chart.js"));
      return rows;
    }
  }

  protected static String toRgba(String hexColor, float opacity) {
    String r = hexColor.substring(0, 2);
    String g = hexColor.substring(2, 4);
    String b = hexColor.substring(4, 6);
    return "rgba("
        + Integer.valueOf(r, 16) + ","
        + Integer.valueOf(g, 16) + ","
        + Integer.valueOf(b, 16) + ","
        + opacity + ")";
  }

}
