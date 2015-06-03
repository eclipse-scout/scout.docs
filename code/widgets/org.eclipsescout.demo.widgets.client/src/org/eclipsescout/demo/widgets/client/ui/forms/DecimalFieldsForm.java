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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.eclipse.scout.commons.NumberUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.BigDecimalInputField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.FormatField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.FractionDigitsField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.GetValue0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.GroupingField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.InputField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.MaxFractionDigitsField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.MaximumValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.MinFractionDigitsField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.MinimumValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.MultiplierField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.PercentField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.Place1Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.Place2Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.Place3Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.Place4Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.Place5Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalDisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalMandatoryField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalStyledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.DisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.DoubleColumnField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.DoubleField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.MandatoryField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.StyledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.HighestValueButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.PiButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.SampleFormatButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm.MainBox.SmallestValueButton;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntegerColumnField;

@SuppressWarnings("deprecation")
public class DecimalFieldsForm extends AbstractForm implements IPageForm {

  private static final BigDecimal FOUR = new BigDecimal(4);

  public DecimalFieldsForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("DecimalFields");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public BigDecimalField getBigDecimalField() {
    return getFieldByClass(BigDecimalField.class);
  }

  public BigDecimalInputField getBigDecimalInputField() {
    return getFieldByClass(BigDecimalInputField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public DoubleField getDoubleField() {
    return getFieldByClass(DoubleField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  /**
   * @return the FormatField
   */
  public FormatField getFormatField() {
    return getFieldByClass(FormatField.class);
  }

  public FractionDigitsField getFractionDigitsField() {
    return getFieldByClass(FractionDigitsField.class);
  }

  public GetValue0Field getGetValue0Field() {
    return getFieldByClass(GetValue0Field.class);
  }

  public GroupingField getGroupingField() {
    return getFieldByClass(GroupingField.class);
  }

  public HighestValueButton getHighestValueButton() {
    return getFieldByClass(HighestValueButton.class);
  }

  public InputField getInputField() {
    return getFieldByClass(InputField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public MaxFractionDigitsField getMaxFractionDigitsField() {
    return getFieldByClass(MaxFractionDigitsField.class);
  }

  public MaximumValueField getMaximumValueField() {
    return getFieldByClass(MaximumValueField.class);
  }

  public MinFractionDigitsField getMinFractionDigitsField() {
    return getFieldByClass(MinFractionDigitsField.class);
  }

  public MinimumValueField getMinimumValueField() {
    return getFieldByClass(MinimumValueField.class);
  }

  public MultiplierField getMultiplierField() {
    return getFieldByClass(MultiplierField.class);
  }

  public PercentField getPercentField() {
    return getFieldByClass(PercentField.class);
  }

  public PiButton getPiButton() {
    return getFieldByClass(PiButton.class);
  }

  public Place1Field getPlace1Field() {
    return getFieldByClass(Place1Field.class);
  }

  public Place2Field getPlace2Field() {
    return getFieldByClass(Place2Field.class);
  }

  public Place3Field getPlace3Field() {
    return getFieldByClass(Place3Field.class);
  }

  public Place4Field getPlace4Field() {
    return getFieldByClass(Place4Field.class);
  }

  public Place5Field getPlace5Field() {
    return getFieldByClass(Place5Field.class);
  }

  /**
   * @return the SampleFormatButton
   */
  public SampleFormatButton getSampleFormatButton() {
    return getFieldByClass(SampleFormatButton.class);
  }

  public SmallestValueButton getSmallestValueButton() {
    return getFieldByClass(SmallestValueButton.class);
  }

  public StyledField getStyledField() {
    return getFieldByClass(StyledField.class);
  }

  public BigDecimalDisabledField getBigIntDisabledField() {
    return getFieldByClass(BigDecimalDisabledField.class);
  }

  public BigDecimalMandatoryField getBigIntMandatoryField() {
    return getFieldByClass(BigDecimalMandatoryField.class);
  }

  public BigDecimalStyledField getBigIntStyledField() {
    return getFieldByClass(BigDecimalStyledField.class);
  }

  public BigIntegerColumnField getBigIntegerColumnField() {
    return getFieldByClass(BigIntegerColumnField.class);
  }

  public DoubleColumnField getIntegerColumnField() {
    return getFieldByClass(DoubleColumnField.class);
  }

  public DoubleField getIntegerField() {
    return getFieldByClass(DoubleField.class);
  }

  public ConfigurationBox getSpecialCasesBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Order(10.0)
      public class DoubleColumnField extends AbstractLabelField {

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("DoubleField"));
        }
      }

      @Order(20.0)
      public class DoubleField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(30.0)
      public class MandatoryField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(40.0)
      public class DisabledField extends AbstractDoubleField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(Math.E);
        }
      }

      @Order(50.0)
      public class StyledField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          if (getValue() < 0) {
            setForegroundColor("FF0000");
          }
          else {
            setForegroundColor(null);
          }
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(-3.1415);
          setForegroundColor("FF0000");
        }
      }

      @Order(110.0)
      public class BigDecimalColumnField extends AbstractLabelField {

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("BigDecimalField"));
        }
      }

      @Order(120.0)
      public class BigDecimalField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(130.0)
      public class BigDecimalMandatoryField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(140.0)
      public class BigDecimalDisabledField extends AbstractBigDecimalField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(BigDecimal.valueOf(Math.E));
        }
      }

      @Order(150.0)
      public class BigDecimalStyledField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          if (getValue().signum() < 0) {
            setForegroundColor("FF0000");
          }
          else {
            setForegroundColor(null);
          }
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(BigDecimal.valueOf(-3.5));
          setForegroundColor("FF0000");
        }
      }
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class InputField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DoubleFieldInput");
        }
      }

      @Order(20.0)
      public class GetValue0Field extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("GetValue");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return InputField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          if (newMasterValue != null) {
            setValue(((Double) newMasterValue).toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(30.0)
      public class MinimumValueField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MinimumValue");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          if (getValue() != null) {
            getInputField().setMinValue(getValue());
            getBigDecimalInputField().setMinValue(BigDecimal.valueOf(getValue()));
          }
          else {
            getInputField().setMinValue(null);
            getBigDecimalInputField().setMinValue(null);
          }

          getInputField().validateContent();
          getBigDecimalInputField().validateContent();
        }
      }

      @Order(40.0)
      public class MaximumValueField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MaximumValue");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          if (getValue() != null) {
            getInputField().setMaxValue(getValue());
            getBigDecimalInputField().setMaxValue(BigDecimal.valueOf(getValue()));
          }
          else {
            getInputField().setMaxValue(null);
            getBigDecimalInputField().setMaxValue(null);
          }

          getInputField().validateContent();
          getBigDecimalInputField().validateContent();
        }
      }

      @Order(50.0)
      public class GroupingField extends AbstractCheckBox {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Grouping");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setGroupingUsed(getValue());
          getBigDecimalInputField().setGroupingUsed(getValue());
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getInputField().isGroupingUsed());
        }
      }

      @Order(60.0)
      public class MinFractionDigitsField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MinFractionDigits");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setMinFractionDigits(NumberUtility.nvl(getValue(), 0));
          getBigDecimalInputField().setMinFractionDigits(NumberUtility.nvl(getValue(), 0));
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getInputField().getMinFractionDigits());
        }
      }

      @Order(70.0)
      public class MaxFractionDigitsField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MaxFractionDigits");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setMaxFractionDigits(NumberUtility.nvl(getValue(), 2));
          getBigDecimalInputField().setMaxFractionDigits(NumberUtility.nvl(getValue(), 2));
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getInputField().getMaxFractionDigits());
        }
      }

      @Order(80.0)
      public class FractionDigitsField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FractionDigits");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setFractionDigits(NumberUtility.nvl(getValue(), 2));
          getBigDecimalInputField().setFractionDigits(NumberUtility.nvl(getValue(), 2));
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getInputField().getFractionDigits());
        }
      }

      @Order(90.0)
      public class MultiplierField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Multiplier");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setMultiplier(NumberUtility.nvl(getValue(), 1));
          getBigDecimalInputField().setMultiplier(NumberUtility.nvl(getValue(), 1));
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getInputField().getMultiplier());
        }
      }

      @Order(100.0)
      public class PercentField extends AbstractCheckBox {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Percent");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setPercent(getValue());
          getBigDecimalInputField().setPercent(getValue());
        }
      }

      @Order(110.0)
      public class FormatField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Format");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          DecimalFormat format = new DecimalFormat();

          if (getValue() != null) {
            format = new DecimalFormat(getValue());
          }

          getInputField().setFormat(format);
          getBigDecimalInputField().setFormat(format);
        }
      }

      @Order(120.0)
      public class BigDecimalInputField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BigDecimalFieldInput");
        }
      }

      @Order(130.0)
      public class GetValue1Field extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("GetValue");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return BigDecimalInputField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          if (newMasterValue != null) {
            setValue(((BigDecimal) newMasterValue).toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(140.0)
      public class Place1Field extends AbstractPlaceholderField {
      }

      @Order(150.0)
      public class Place2Field extends AbstractPlaceholderField {
      }

      @Order(160.0)
      public class Place3Field extends AbstractPlaceholderField {
      }

      @Order(170.0)
      public class Place4Field extends AbstractPlaceholderField {
      }

      @Order(180.0)
      public class Place5Field extends AbstractPlaceholderField {
      }

      @Order(190.0)
      public class Place6Field extends AbstractPlaceholderField {
      }

      @Order(200.0)
      public class Place7Field extends AbstractPlaceholderField {
      }

      @Order(210.0)
      public class Place8Field extends AbstractPlaceholderField {
      }
    }

    @Order(30.0)
    public class HighestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("HighestValue");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getInputField().setValue(Double.MAX_VALUE);
        getBigDecimalInputField().setDisplayText("can get as large as you want");
      }
    }

    @Order(40.0)
    public class SmallestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SmallestValue");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getInputField().setValue(Double.MIN_VALUE);
        getBigDecimalInputField().setDisplayText("can get as small as you want");
      }
    }

    @Order(50.0)
    public class PiButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Pi");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getInputField().setValue(Math.PI);
        getBigDecimalInputField().setValue(pi());
      }

      /**
       * Gauss-Legendre Algorithm
       * implementation copied from http://java.lykkenborg.no/2005/03/computing-pi-using-bigdecimal.html
       */
      private BigDecimal pi() {
        BigDecimal two = new BigDecimal(2);
        int scale = NumberUtility.nvl(getFractionDigitsField().getValue(), 20);
        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.ONE.divide(sqrt(two, scale), scale, BigDecimal.ROUND_HALF_UP);
        BigDecimal t = new BigDecimal(0.25);
        BigDecimal x = BigDecimal.ONE;
        BigDecimal y;

        while (!a.equals(b)) {
          y = a;
          a = a.add(b).divide(two, scale, BigDecimal.ROUND_HALF_UP);
          b = sqrt(b.multiply(y), scale);
          t = t.subtract(x.multiply(y.subtract(a).multiply(y.subtract(a))));
          x = x.multiply(two);
        }

        return a.add(b).multiply(a.add(b)).divide(t.multiply(new BigDecimal(4)), scale, BigDecimal.ROUND_HALF_UP);
      }

      /**
       * Babylonian square root method (Newton's method)
       */
      private BigDecimal sqrt(BigDecimal a, int scale) {
        BigDecimal two = new BigDecimal(2);
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(a.doubleValue()));

        while (!x0.equals(x1)) {
          x0 = x1;
          x1 = a.divide(x0, scale, BigDecimal.ROUND_HALF_UP);
          x1 = x1.add(x0);
          x1 = x1.divide(two, scale, BigDecimal.ROUND_HALF_UP);
        }

        return x1;
      }
    }

    @Order(60.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(70.0)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getFormatField().setValue("#,###.0 �C;#,###.0 �C BELOW ZERO");
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
