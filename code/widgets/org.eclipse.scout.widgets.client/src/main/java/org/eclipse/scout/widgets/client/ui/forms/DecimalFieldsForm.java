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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.NumberUtility;
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

@ClassId("87f25304-3e24-4e4d-b602-d39d23523acc")
public class DecimalFieldsForm extends AbstractForm implements IPageForm {

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
  @ClassId("b05d388b-d68b-4976-af79-858aeda5d2f7")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("6be75bb8-9761-47e8-a6b8-76454e57f5e9")
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
      @ClassId("576b3799-d4fd-4a48-ada4-a4e2c215fd5f")
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
        @ClassId("eb3899a9-424a-44eb-8821-e3debb4f7cab")
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
        @ClassId("ceae1cc7-6637-4a17-8800-d3916dedbc47")
        public class BigDecimalField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }
        }

        @Order(130)
        @ClassId("e69d48f2-f302-4def-b91c-94e7e9f0a072")
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
        @ClassId("c7ab4d0b-c0b3-4957-a531-b81ad64227cd")
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
        @ClassId("24c04721-045c-41a9-a160-47a2a33529d7")
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
    @ClassId("d19483bd-211a-4e9c-b373-9d8ae7043fcf")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("e9a80355-b26a-476b-b992-3fc0031b2fbc")
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
        @ClassId("aaf8b581-73f8-419a-a056-6697d51d6109")
        public class InputField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BigDecimalFieldInput");
          }
        }

        @Order(20)
        @ClassId("c40f6b23-fcae-4665-9c3a-344cf01b4b9c")
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
        @ClassId("8ef94ef5-252e-4a96-ba7f-7bae382a7801")
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
            getInputField().addPropertyChangeListener(PROP_DISPLAY_TEXT, evt -> DisplayText0Field.this.setValue((String) evt.getNewValue()));
          }
        }
      }

      @Order(20)
      @ClassId("accc3d6f-8e79-4db3-8f56-2913ed056bc5")
      public class ConfigurationBottomBox extends AbstractGroupBox {

        @Order(30)
        @ClassId("87f5ee19-5cd4-489b-8942-288427afea10")
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
        @ClassId("21a1d308-44ff-4c45-aa18-dbbd8f9a3bb0")
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
        @ClassId("aeeaf695-17da-41d6-bc34-17a7193998ae")
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
        @ClassId("fd4ee370-ed1b-42aa-900f-3be8e6ec7af4")
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
        @ClassId("c62f56b1-745c-43ae-9a6d-ac7d0f3b50c8")
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
        @ClassId("b79caadb-18c7-4a2e-9c67-d59c48892d91")
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
        @ClassId("3c6d6a9f-ed78-45eb-9a7b-1fb3b3b36f91")
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
        @ClassId("9397685e-f648-4350-8f7d-e9d8cd0c1748")
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
        @ClassId("0cf7c3ea-7e68-4655-b3eb-d8420f456eaf")
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
        @ClassId("573e15c7-466d-44f3-8f58-53d3544a133f")
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
    @ClassId("7d47a251-3781-4a29-a6e6-921edc55da81")
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
    @ClassId("a3dc5038-5d70-43b5-a9b0-fe6c62d9d72a")
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
    @ClassId("7a7411e1-8911-4cdd-af2a-96887a36583c")
    public class PiButton extends AbstractButton {

      /**
       * Gauss-Legendre Algorithm implementation copied from
       * http://java.lykkenborg.no/2005/03/computing-pi-using-bigdecimal.html
       */
      private BigDecimal pi() {
        BigDecimal two = new BigDecimal(2);
        int scale = NumberUtility.nvl(getFractionDigitsField().getValue(), 20) + 2;
        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.ONE.divide(sqrt(two, scale), scale, RoundingMode.HALF_UP);
        BigDecimal t = new BigDecimal(0.25);
        BigDecimal x = BigDecimal.ONE;
        BigDecimal y;

        while (!a.equals(b)) {
          y = a;
          a = a.add(b).divide(two, scale, RoundingMode.HALF_UP);
          b = sqrt(b.multiply(y), scale);
          t = t.subtract(x.multiply(y.subtract(a).multiply(y.subtract(a))));
          x = x.multiply(two);
        }

        return a.add(b).multiply(a.add(b)).divide(t.multiply(new BigDecimal(4)), scale - 2, RoundingMode.HALF_UP);
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
          x1 = a.divide(x0, scale, RoundingMode.HALF_UP);
          x1 = x1.add(x0);
          x1 = x1.divide(two, scale, RoundingMode.HALF_UP);
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
    @ClassId("5f4b8a16-556f-4c6a-a0f2-6cf25e293bf1")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(70)
    @ClassId("e9666531-34cf-41b4-9088-7563914e4b3c")
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() {
        getFormatField().setValue("#,###.0 °C;#,###.0 °C BELOW ZERO");
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
