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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.FormatField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.FractionDigitsField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.GroupingField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MaxFractionDigitsField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MaximumValueField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MinFractionDigitsField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MinimumValueField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MultiplierField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.PercentField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.InputField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.Value0Field;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalColumn.BigDecimalDisabledField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalColumn.BigDecimalField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalColumn.BigDecimalMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.ExamplesBox.BigDecimalColumn.BigDecimalStyledField;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.HighestValueButton;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.PiButton;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.SampleFormatButton;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm.MainBox.SmallestValueButton;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntegerColumnField;

public class DecimalFieldsForm extends AbstractForm implements IPageForm {

  public DecimalFieldsForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("DecimalFields");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public BigDecimalField getBigDecimalField() {
    return getFieldByClass(BigDecimalField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public FormatField getFormatField() {
    return getFieldByClass(FormatField.class);
  }

  public FractionDigitsField getFractionDigitsField() {
    return getFieldByClass(FractionDigitsField.class);
  }

  public Value0Field getValue0Field() {
    return getFieldByClass(Value0Field.class);
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

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
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

  public SampleFormatButton getSampleFormatButton() {
    return getFieldByClass(SampleFormatButton.class);
  }

  public SmallestValueButton getSmallestValueButton() {
    return getFieldByClass(SmallestValueButton.class);
  }

  public ConfigurationBox getSpecialCasesBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Order(10)
      public class BigDecimalColumn extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(110)
        public class BigDecimalColumnField extends AbstractLabelField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("EmptyString");
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("BigDecimalField"));
          }
        }

        @Order(120)
        public class BigDecimalField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }
        }

        @Order(130)
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

        @Order(140)
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
          protected void execInitField() {
            setValue(BigDecimal.valueOf(Math.E));
          }
        }

        @Order(150)
        public class BigDecimalStyledField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Styled");
          }

          @Override
          protected void execChangedValue() {
            if (getValue() != null && getValue().signum() < 0) {
              setForegroundColor("FF0000");
            }
            else {
              setForegroundColor(null);
            }
          }

          @Override
          protected void execInitField() {
            setValue(BigDecimal.valueOf(-3.5));
            setForegroundColor("FF0000");
          }
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      public class ConfigurationTopBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        public class InputField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BigDecimalFieldInput");
          }
        }

        @Order(20)
        public class Value0Field extends AbstractStringField {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return "PROP_VALUE";
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return InputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            setValue(newMasterValue == null ? null : newMasterValue.toString());
          }
        }

        @Order(25)
        public class DisplayText0Field extends AbstractStringField {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return "PROP_DISPLAY_TEXT";
          }

          @Override
          protected void execInitField() {
            getInputField().addPropertyChangeListener(PROP_DISPLAY_TEXT, new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                DisplayText0Field.this.setValue((String) evt.getNewValue());
              }
            });
          }
        }
      }

      @Order(20)
      public class ConfigurationBottomBox extends AbstractGroupBox {

        @Order(30)
        public class MinimumValueField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("MinimumValue");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            if (getValue() != null) {
              getInputField().setMinValue(getValue());
            }
            else {
              getInputField().setMinValue(null);
            }

            getInputField().validateContent();
          }
        }

        @Order(40)
        public class MaximumValueField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("MaximumValue");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            if (getValue() != null) {
              getInputField().setMaxValue(getValue());
            }
            else {
              getInputField().setMaxValue(null);
            }

            getInputField().validateContent();
          }
        }

        @Order(50)
        public class GroupingField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Grouping");
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execInitField() {
            setValue(getInputField().isGroupingUsed());
          }

          @Override
          protected void execChangedValue() {
            getInputField().setGroupingUsed(getValue());
          }
        }

        @Order(55)
        public class UpdateDisplayTextOnModify extends AbstractBooleanField {

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("UpdateDisplayTextOnModify");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getInputField().setUpdateDisplayTextOnModify(getValue());
          }
        }

        @Order(60)
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
          protected void execInitField() {
            setValue(getInputField().getMinFractionDigits());
          }

          @Override
          protected void execChangedValue() {
            getInputField().setMinFractionDigits(NumberUtility.nvl(getValue(), 0));
          }
        }

        @Order(70)
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
          protected void execInitField() {
            setValue(getInputField().getMaxFractionDigits());
          }

          @Override
          protected void execChangedValue() {
            getInputField().setMaxFractionDigits(NumberUtility.nvl(getValue(), 2));
          }
        }

        @Order(80)
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
          protected void execInitField() {
            setValue(getInputField().getFractionDigits());
          }

          @Override
          protected void execChangedValue() {
            getInputField().setFractionDigits(NumberUtility.nvl(getValue(), 2));
          }
        }

        @Order(90)
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
          protected void execInitField() {
            setValue(getInputField().getMultiplier());
          }

          @Override
          protected void execChangedValue() {
            getInputField().setMultiplier(NumberUtility.nvl(getValue(), 1));
          }
        }

        @Order(100)
        public class PercentField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Percent");
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getInputField().setPercent(getValue());
          }
        }

        @Order(110)
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
          protected void execChangedValue() {
            DecimalFormat format = new DecimalFormat();

            if (getValue() != null) {
              format = new DecimalFormat(getValue());
            }

            getInputField().setFormat(format);
          }
        }
      }
    }

    @Order(30)
    public class HighestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("HighestValue");
      }

      @Override
      protected void execClickAction() {
        getInputField().setDisplayText("can get as large as you want");
      }
    }

    @Order(40)
    public class SmallestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SmallestValue");
      }

      @Override
      protected void execClickAction() {
        getInputField().setDisplayText("can get as small as you want");
      }
    }

    @Order(50)
    public class PiButton extends AbstractButton {

      /**
       * Gauss-Legendre Algorithm implementation copied from
       * http://java.lykkenborg.no/2005/03/computing-pi-using-bigdecimal.html
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

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Pi");
      }

      @Override
      protected void execClickAction() {
        getInputField().setValue(pi());
      }
    }

    @Order(60)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(70)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() {
        getFormatField().setValue("#,###.0 �C;#,###.0 �C BELOW ZERO");
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
