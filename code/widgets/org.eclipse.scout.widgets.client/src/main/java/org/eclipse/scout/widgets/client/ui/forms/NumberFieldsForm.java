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
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Locale;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigintegerfield.AbstractBigIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.numberfield.AbstractNumberField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.NumberFormatProvider;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.services.lookup.NumberFormatLocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.ConfigLocaleField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.FormatField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.GroupingField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MaximumValueField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationBottomBox.MinimumValueField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.BigIntegerInputField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.InputField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.LongInputField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntDisabledField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntStyledField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntegerColumnField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.BigIntegerField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.IntegerColumnField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.IntegerField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.LongColumnField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.LongDisabledField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.LongField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.LongMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.LongStyledField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.MandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.ExamplesBox.StyledField;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.HighestValueButton;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm.MainBox.SmallestValueButton;

public class NumberFieldsForm extends AbstractForm implements IPageForm {

  public NumberFieldsForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("NumberFields");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public FormatField getFormatField() {
    return getFieldByClass(FormatField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
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

  public IntegerColumnField getIntegerColumnField() {
    return getFieldByClass(IntegerColumnField.class);
  }

  public IntegerField getIntegerField() {
    return getFieldByClass(IntegerField.class);
  }

  public LongColumnField getLongColumnField() {
    return getFieldByClass(LongColumnField.class);
  }

  public LongDisabledField getLongDisabledField() {
    return getFieldByClass(LongDisabledField.class);
  }

  public LongField getLongField() {
    return getFieldByClass(LongField.class);
  }

  public LongInputField getLongInputField() {
    return getFieldByClass(LongInputField.class);
  }

  public LongMandatoryField getLongMandatoryField() {
    return getFieldByClass(LongMandatoryField.class);
  }

  public LongStyledField getLongStyledField() {
    return getFieldByClass(LongStyledField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public BigIntDisabledField getBigIntDisabledField() {
    return getFieldByClass(BigIntDisabledField.class);
  }

  public BigIntMandatoryField getBigIntMandatoryField() {
    return getFieldByClass(BigIntMandatoryField.class);
  }

  public BigIntStyledField getBigIntStyledField() {
    return getFieldByClass(BigIntStyledField.class);
  }

  public BigIntegerColumnField getBigIntegerColumnField() {
    return getFieldByClass(BigIntegerColumnField.class);
  }

  public BigIntegerField getBigIntegerField() {
    return getFieldByClass(BigIntegerField.class);
  }

  public BigIntegerInputField getBigIntegerInputField() {
    return getFieldByClass(BigIntegerInputField.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public MaximumValueField getMaximumValueField() {
    return getFieldByClass(MaximumValueField.class);
  }

  public MinimumValueField getMinimumValueField() {
    return getFieldByClass(MinimumValueField.class);
  }

  public ConfigLocaleField getConfigLocaleField() {
    return getFieldByClass(ConfigLocaleField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public SmallestValueButton getSmallestValueButton() {
    return getFieldByClass(SmallestValueButton.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public StyledField getStyledField() {
    return getFieldByClass(StyledField.class);
  }

  public void changeLocale(AbstractNumberField field, final Locale locale) {
    DecimalFormat oldFormat = field.getFormat();
    DecimalFormat newFormat = BEANS.get(NumberFormatProvider.class).getNumberInstance(locale);

    newFormat.setParseBigDecimal(oldFormat.isParseBigDecimal());
    newFormat.setMinimumFractionDigits(oldFormat.getMinimumFractionDigits());
    newFormat.setMaximumFractionDigits(oldFormat.getMaximumFractionDigits());
    newFormat.setMaximumIntegerDigits(oldFormat.getMaximumIntegerDigits());
    newFormat.setRoundingMode(oldFormat.getRoundingMode());
    newFormat.setGroupingUsed(oldFormat.isGroupingUsed());
    field.setFormat(newFormat);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Order(10)
      public class IntegerColumnField extends AbstractLabelField {

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
          setValue(TEXTS.get("IntegerField"));
        }
      }

      @Order(20)
      public class IntegerField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(30)
      public class MandatoryField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(40)
      public class DisabledField extends AbstractIntegerField {

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
          setValue(5);
        }
      }

      @Order(50)
      public class StyledField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected void execChangedValue() {
          if (NumberUtility.nvl(getValue(), 0) < 0) {
            setForegroundColor("FF0000");
          }
          else {
            setForegroundColor("0000FF");
          }
        }

        @Override
        protected void execInitField() {
          setValue(-3);
          interceptChangedValue();
        }
      }

      @Order(60)
      public class LongColumnField extends AbstractLabelField {
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
          setValue(TEXTS.get("LongField"));
        }
      }

      @Order(70)
      public class LongField extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(80)
      public class LongMandatoryField extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(90)
      public class LongDisabledField extends AbstractLongField {

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
          setValue(5L);
        }
      }

      @Order(100)
      public class LongStyledField extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected void execChangedValue() {
          if (getValue() < 0) {
            setForegroundColor("FF0000");
          }
          else {
            setForegroundColor("0000FF");
          }
        }

        @Override
        protected void execInitField() {
          setValue(-3L);
          interceptChangedValue();
        }
      }

      @Order(110)
      public class BigIntegerColumnField extends AbstractLabelField {
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
          setValue(TEXTS.get("BigIntegerField"));
        }
      }

      @Order(120)
      public class BigIntegerField extends AbstractBigIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BigIntegerField");
        }
      }

      @Order(130)
      public class BigIntMandatoryField extends AbstractBigIntegerField {

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
      public class BigIntDisabledField extends AbstractBigIntegerField {

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
          setValue(BigInteger.valueOf(5));
        }
      }

      @Order(150)
      public class BigIntStyledField extends AbstractBigIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected void execChangedValue() {
          if (getValue().signum() < 0) {
            setForegroundColor("FF0000");
          }
          else {
            setForegroundColor("0000FF");
          }
        }

        @Override
        protected void execInitField() {
          setValue(BigInteger.valueOf(-3));
          interceptChangedValue();
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
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 3;
        }

        @Order(10)
        public class InputField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IntegerFieldInput");
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
            return NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.InputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            if (newMasterValue != null) {
              setValue(((Integer) newMasterValue).toString());
            }
            else {
              setValue(null);
            }
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

        @Order(70)
        public class LongInputField extends AbstractLongField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LongFieldInput");
          }
        }

        @Order(80)
        public class Value1Field extends AbstractStringField {

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
            return NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.LongInputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            if (newMasterValue != null) {
              setValue(((Long) newMasterValue).toString());
            }
            else {
              setValue(null);
            }
          }
        }

        @Order(85)
        public class DisplayText1Field extends AbstractStringField {

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
            getLongInputField().addPropertyChangeListener(PROP_DISPLAY_TEXT, new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                DisplayText1Field.this.setValue((String) evt.getNewValue());
              }
            });
          }
        }

        @Order(120)
        public class BigIntegerInputField extends AbstractBigIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BigIntegerFieldInput");
          }
        }

        @Order(130)
        public class Value3Field extends AbstractStringField {

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
            return NumberFieldsForm.MainBox.ConfigurationBox.ConfigurationTopBox.BigIntegerInputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            if (newMasterValue != null) {
              setValue(((BigInteger) newMasterValue).toString());
            }
            else {
              setValue(null);
            }
          }
        }

        @Order(135)
        public class DisplayText2Field extends AbstractStringField {

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
            getBigIntegerInputField().addPropertyChangeListener(PROP_DISPLAY_TEXT, new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                DisplayText2Field.this.setValue((String) evt.getNewValue());
              }
            });
          }
        }
      }

      @Order(20)
      public class ConfigurationBottomBox extends AbstractGroupBox {

        @Order(30)
        public class MinimumValueField extends AbstractLongField {

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
              getInputField().setMinValue(getValue().intValue());
              getLongInputField().setMinValue(getValue());
              getBigIntegerInputField().setMinValue(BigInteger.valueOf(getValue()));
            }
            else {
              getInputField().setMinValue(null);
              getLongInputField().setMinValue(null);
              getBigIntegerInputField().setMinValue(null);
            }

            getInputField().validateContent();
            getLongInputField().validateContent();
            getBigIntegerInputField().validateContent();
          }
        }

        @Order(40)
        public class MaximumValueField extends AbstractLongField {

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
              getInputField().setMaxValue(getValue().intValue());
              getLongInputField().setMaxValue(getValue());
              getBigIntegerInputField().setMaxValue(BigInteger.valueOf(getValue()));
            }
            else {
              getInputField().setMaxValue(null);
              getLongInputField().setMaxValue(null);
              getBigIntegerInputField().setMaxValue(null);
            }

            getInputField().validateContent();
            getLongInputField().validateContent();
            getBigIntegerInputField().validateContent();
          }
        }

        @Order(50)
        public class GroupingField extends AbstractBooleanField {

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Grouping");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getInputField().setGroupingUsed(getValue());
            getLongInputField().setGroupingUsed(getValue());
            getBigIntegerInputField().setGroupingUsed(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(true);
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
            getLongInputField().setUpdateDisplayTextOnModify(getValue());
            getBigIntegerInputField().setUpdateDisplayTextOnModify(getValue());
          }
        }

        @Order(60)
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
            getLongInputField().setFormat(format);
            getBigIntegerInputField().setFormat(format);
          }
        }

        @Order(65)
        public class ConfigLocaleField extends AbstractSmartField<Locale> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Locale");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
            return (Class<? extends ILookupCall<Locale>>) NumberFormatLocaleLookupCall.class;
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("SelectLocale");
          }

          @Override
          protected void execChangedValue() {
            changeLocale(getInputField(), getValue());
            changeLocale(getLongInputField(), getValue());
            changeLocale(getBigIntegerInputField(), getValue());
          }

          @Override
          protected void execInitField() {
            setValue(ClientSession.get().getLocale());
          }
        }

      }
    }

    @Order(40)
    public class HighestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("HighestValue");
      }

      @Override
      protected void execClickAction() {
        getInputField().setValue(Integer.MAX_VALUE);
        getLongInputField().setValue(Long.MAX_VALUE);
        getBigIntegerInputField().setDisplayText("can get as large as you want");
      }
    }

    @Order(50)
    public class SmallestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SmallestValue");
      }

      @Override
      protected void execClickAction() {
        getInputField().setValue(Integer.MIN_VALUE);
        getLongInputField().setValue(Long.MIN_VALUE);
        getBigIntegerInputField().setDisplayText("can get as small as you want");
      }
    }

    @Order(60)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() {
        getFormatField().setValue("'Prod-No.' 000,0000");
      }
    }

    @Order(70)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
