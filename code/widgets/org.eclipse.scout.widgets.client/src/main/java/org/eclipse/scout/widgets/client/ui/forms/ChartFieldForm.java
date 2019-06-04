/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.forms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
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
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartDataDo;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartDatasetDo;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ChartTypeField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.DataLengthField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.DataSeriesField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.FormFieldConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.RandomDataButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ReloadConfigButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ExamplesBox.ChartField;

public class ChartFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public static final String CONFIG_LINE = "/chart/lineConfig.json";
  public static final String CONFIG_PIE = "/chart/pieConfig.json";
  public static final String CONFIG_DOUGHNUT = "/chart/doughnutConfig.json";

  protected static final int DEFAULT_DATA_SERIES = 1;
  protected static final int DEFAULT_DATA_LENGTH = 6;

  protected static final String[] DEFAULT_LABELS = {"January", "February", "March", "April", "June", "Juli",
      "August", "September", "October", "November", "December"};

  // Border and Background Colors must have the same length
  protected static final String[] DEFAULT_BORDER_COLORS = {
      "rgba(255, 99, 132, 1)",
      "rgba(54, 162, 235, 1)",
      "rgba(255, 206, 86, 1)",
      "rgba(75, 192, 192, 1)",
      "rgba(153, 102, 255, 1)",
      "rgba(255, 159, 64, 1)"};

  protected static final String[] DEFAULT_BACKGROUND_COLORS = {
      "rgba(255, 99, 132, 0.2)",
      "rgba(54, 162, 235, 0.2)",
      "rgba(255, 206, 86, 0.2)",
      "rgba(75, 192, 192, 0.2)",
      "rgba(153, 102, 255, 0.2)",
      "rgba(255, 159, 64, 0.2)"};

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
    return values.toArray(new String[values.size()]);
  }

  protected String[] repeatValue(String value, int requestedLength) {
    List<String> values = new ArrayList<>(requestedLength);
    for (int i = 0; i < requestedLength; i++) {
      values.add(value);
    }
    return values.toArray(new String[values.size()]);
  }

  protected ChartDataDo randomChartData(int dataSeries, int dataLength) {
    String[] labels = fillStringArray(DEFAULT_LABELS, dataLength, i -> "Month #" + i);

    boolean lineChart = getChartTypeField().getValue() == CONFIG_LINE;
    int requestedLength = lineChart ? dataSeries : dataLength;

    String[] backgroundColors = fillStringArray(DEFAULT_BACKGROUND_COLORS, requestedLength,
        i -> DEFAULT_BACKGROUND_COLORS[i % DEFAULT_BACKGROUND_COLORS.length]);
    String[] borderColors = fillStringArray(DEFAULT_BORDER_COLORS, requestedLength,
        i -> DEFAULT_BORDER_COLORS[i % DEFAULT_BORDER_COLORS.length]);

    // data sets per serie
    List<ChartDatasetDo> datasets = new ArrayList<>();
    for (int i = 0; i < dataSeries; i++) {
      int[] data = new int[dataLength];
      for (int j = 0; j < dataLength; j++) {
        data[j] = (int) (Math.random() * 9.0 + 1);
      }

      String[] datasetBackgroundColors;
      String[] datasetBorderColors;
      if (lineChart) {
        datasetBackgroundColors = repeatValue(backgroundColors[i], dataLength);
        datasetBorderColors = repeatValue(borderColors[i], dataLength);
      }
      else {
        datasetBackgroundColors = backgroundColors;
        datasetBorderColors = borderColors;
      }

      ChartDatasetDo chartDataset = BEANS.get(ChartDatasetDo.class)
          .withLabel("Series #" + (i + 1))
          .withData(data)
          .withBackgroundColor(datasetBackgroundColors)
          .withBorderColor(datasetBorderColors)
          .withBorderWidth(1);
      if (lineChart) {
        chartDataset.put("lineTension", 0.0);
      }
      datasets.add(chartDataset);
    }

    return BEANS.get(ChartDataDo.class)
        .withLabels(labels)
        .withDatasets(datasets);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
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
      public class ChartField extends AbstractChartField {

        @Override
        protected String getConfiguredConfigFile() {
          return CONFIG_PIE;
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
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(1250)
      @ClassId("f8ce887d-a591-4937-9868-9b8f5a030db2")
      public class ChartTypeField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ChatType");
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
          getChartField().setConfigFile(getValue());
          updateChartData(true);
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

      @Order(1900)
      @ClassId("9de0291c-f5d2-4a0d-af54-7ed9ea4149e8")
      public class ReloadConfigButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Reload config";
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
            getChartField().setConfig(config);
          }
          catch (IOException e) {
            throw new ProcessingException("Failed to load config file", e);
          }
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
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  public static class ChartTypeLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      List<ILookupRow<String>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(CONFIG_LINE, "Line"));
      rows.add(new LookupRow<>(CONFIG_PIE, "Pie"));
      rows.add(new LookupRow<>(CONFIG_DOUGHNUT, "Doughnut"));
      return rows;
    }
  }
}
