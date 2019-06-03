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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.configuration.AbstractFormFieldConfigurationBox;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.AbstractChartField;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.ChartTypeField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.DataSeriesCountField;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.FormFieldConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ConfigurationBox.RandomDataButton;
import org.eclipse.scout.widgets.client.ui.forms.ChartFieldForm.MainBox.ExamplesBox.ChartField;

public class ChartFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public static final String CONFIG_LINE = "/chart/lineConfig.json";
  public static final String CONFIG_PIE = "/chart/pieConfig.json";
  public static final String CONFIG_DOUGHNUT = "/chart/doughnutConfig.json";
  private int m_dataSeriesCount = 1;

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

  public DataSeriesCountField getDataSeriesCountField() {
    return getFieldByClass(DataSeriesCountField.class);
  }

  public ChartTypeField getChartTypeField() {
    return getFieldByClass(ChartTypeField.class);
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

  private int[][] randomChartData(int serieCount, int dataLenght) {
    int[][] data = new int[serieCount][dataLenght];
    for (int serie = 0; serie < serieCount; serie++) {
      for (int di = 0; di < dataLenght; di++) {
        data[serie][di] = (int) (Math.random() * 9.0 + 1);
      }
    }
    return data;
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
          setValue(randomChartData(m_dataSeriesCount, 6));
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

      @Order(1000)
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
          getChartField().setValue(randomChartData(m_dataSeriesCount, 6));
        }
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
        }
      }

      @Order(1500)
      @ClassId("b371aab8-5179-4dad-8851-81a1e7f51289")
      public class DataSeriesCountField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DataSeriesCount");
        }

        @Override
        protected void execInitField() {
          setValue(m_dataSeriesCount);
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
          m_dataSeriesCount = getValue();
          getChartField().setValue(randomChartData(m_dataSeriesCount, 6));
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
