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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import org.eclipse.scout.rt.client.context.ClientRunContext;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.util.date.DateFormatProvider;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.ConfigLocaleField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.DateFieldFormatField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.DisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.GetValueField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.InputField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn2Box.TimeDisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn2Box.TimeFieldFormatField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn2Box.TimeInputField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn3Box.DateTimeDisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn3Box.DateTimeFieldFormatField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn3Box.DateTimeInputField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn1Box.DateColumnField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn1Box.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn1Box.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn1Box.LocaleField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn1Box.MandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn2Box.TimeColumnField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn2Box.TimeDisabledField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn2Box.TimeField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn2Box.TimeMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn3Box.DateTimeColumnField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn3Box.DateTimeDisabledField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn3Box.DateTimeField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.ExampleColumn3Box.DateTimeMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.NineteenSeventyButton;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.NowButton;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.SampleFormatButton;
import org.eclipse.scout.widgets.shared.CustomDateFormatProvider;

public class DateTimeFieldsForm extends AbstractForm implements IPageForm {

  public DateTimeFieldsForm() {
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

  public TimeDisplayTextField getTimeDisplayTextField() {
    return getFieldByClass(TimeDisplayTextField.class);
  }

  public TimeField getTimeField() {
    return getFieldByClass(TimeField.class);
  }

  public SampleFormatButton getSampleFormatButton() {
    return getFieldByClass(SampleFormatButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DateTimeDisplayTextField getDateTimeDisplayTextField() {
    return getFieldByClass(DateTimeDisplayTextField.class);
  }

  public DateTimeField getDateTimeField() {
    return getFieldByClass(DateTimeField.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public NowButton getNowButton() {
    return getFieldByClass(NowButton.class);
  }

  public GetValueField getGetValueField() {
    return getFieldByClass(GetValueField.class);
  }

  public InputField getInputField() {
    return getFieldByClass(InputField.class);
  }

  public DateColumnField getDateColumnField() {
    return getFieldByClass(DateColumnField.class);
  }

  public TimeColumnField getTimeColumnField() {
    return getFieldByClass(TimeColumnField.class);
  }

  public TimeDisabledField getTimeDisabledField() {
    return getFieldByClass(TimeDisabledField.class);
  }

  public LocaleField getLocaleField() {
    return getFieldByClass(LocaleField.class);
  }

  public TimeFieldFormatField getTimeFieldFormatField() {
    return getFieldByClass(TimeFieldFormatField.class);
  }

  public TimeInputField getTimeInputField() {
    return getFieldByClass(TimeInputField.class);
  }

  public TimeMandatoryField getTimeMandatoryField() {
    return getFieldByClass(TimeMandatoryField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public DateTimeDisabledField getDateTimeDisabledField() {
    return getFieldByClass(DateTimeDisabledField.class);
  }

  public DateTimeMandatoryField getDateTimeMandatoryField() {
    return getFieldByClass(DateTimeMandatoryField.class);
  }

  public DateFieldFormatField getDateFieldFormatField() {
    return getFieldByClass(DateFieldFormatField.class);
  }

  public ConfigLocaleField getConfigLocaleField() {
    return getFieldByClass(ConfigLocaleField.class);
  }

  public DateTimeColumnField getDateTimeColumnField() {
    return getFieldByClass(DateTimeColumnField.class);
  }

  public DateTimeFieldFormatField getDateTimeFieldFormatField() {
    return getFieldByClass(DateTimeFieldFormatField.class);
  }

  public DateTimeInputField getDateTimeInputField() {
    return getFieldByClass(DateTimeInputField.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public DisplayTextField getDisplayTextField() {
    return getFieldByClass(DisplayTextField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public NineteenSeventyButton getNineteenSeventyButton() {
    return getFieldByClass(NineteenSeventyButton.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class ExampleColumn0Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 100;
        }

        @Override
        protected double getConfiguredGridWeightX() {
          return 0;
        }

        @Order(10)
        public class TitleColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("");
          }
        }

        @Order(20)
        public class TitleDefaultField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Default"));
          }
        }

        @Order(30)
        public class TitleMandatoryField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Mandatory"));
          }
        }

        @Order(40)
        public class TitleDisabledField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Disabled"));
          }
        }

        @Order(50)
        public class TitleLocaleField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Locale"));
          }
        }
      }

      @Order(10)
      public class ExampleColumn1Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
        public class DateColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("DateField"));
          }
        }

        @Order(20)
        public class DefaultField extends AbstractDateField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("DateFieldKeyboardControl");
          }
        }

        @Order(30)
        public class MandatoryField extends AbstractDateField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
        public class DisabledField extends AbstractDateField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            setValue(new Date());
          }
        }

        @Order(50)
        public class LocaleField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Locale");
          }

          @Override
          protected void execInitField() {
            setValue(NlsLocale.get().getDisplayName(NlsLocale.get()));
          }
        }
      }

      @Order(20)
      public class ExampleColumn2Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(60)
        public class TimeColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("TimeField"));
          }
        }

        @Order(70)
        public class TimeField extends AbstractTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }
        }

        @Order(80)
        public class TimeMandatoryField extends AbstractTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
        public class TimeDisabledField extends AbstractTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            setValue(new Date());
          }
        }
      }

      @Order(30)
      public class ExampleColumn3Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(110)
        public class DateTimeColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("DateTimeField"));
          }
        }

        @Order(120)
        public class DateTimeField extends AbstractDateTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }
        }

        @Order(130)
        public class DateTimeMandatoryField extends AbstractDateTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
        public class DateTimeDisabledField extends AbstractDateTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            setValue(new Date());
          }
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      public class ConfigurationColumn0Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 100;
        }

        @Override
        protected double getConfiguredGridWeightX() {
          return 0;
        }

        @Order(10)
        public class TitleColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("");
          }
        }

        @Order(20)
        public class TitleInputField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("Input");
          }
        }

        @Order(30)
        public class TitleValueField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Value"));
          }
        }

        @Order(40)
        public class TitleFormatField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Format"));
          }
        }

        @Order(50)
        public class TitleDisplayTextField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("DisplayText"));
          }
        }

        @Order(60)
        public class TitleLocaleField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Locale"));
          }
        }

        @Order(70)
        public class TitleAutoDateField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("Auto date");
          }
        }
      }

      @Order(20)
      public class ConfigurationColumn1Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(5)
        public class DateColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("DateField"));
          }
        }

        @Order(10)
        public class InputField extends AbstractDateField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateFieldInput");
          }

          @Override
          protected String execFormatValue(final Date value) {
            final ClientRunContext runContext = ClientRunContexts.copyCurrent().withLocale(getConfigLocaleField().getValue());
            return runContext.call(new Callable<String>() {
              @Override
              public String call() throws Exception {
                return InputField.super.execFormatValue(value);
              }
            });
          }

          @Override
          public void setDisplayText(String s) {
            super.setDisplayText(s);
            getDateTimeDisplayTextField().setValue(s);
          }
        }

        @Order(20)
        public class GetValueField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            return DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.InputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            if (newMasterValue != null) {
              setValue(((Date) newMasterValue).toString());
            }
            else {
              setValue(null);
            }
          }
        }

        @Order(30)
        public class DateFieldFormatField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateFieldFormat");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("UseSimpleDateFormatPattern");
          }

          @Override
          protected void execChangedValue() {
            getInputField().setFormat(getValue());
          }
        }

        @Order(37)
        public class DisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }
        }

        @Order(40)
        public class ConfigLocaleField extends AbstractSmartField<Locale> {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            return (Class<? extends ILookupCall<Locale>>) LocaleLookupCall.class;
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("SelectLocale");
          }

          @Override
          protected void execChangedValue() {
            getInputField().setFormat(getDateFieldFormatField().getValue());
            getTimeInputField().setFormat(getTimeFieldFormatField().getValue());
            getDateTimeInputField().setFormat(getDateTimeFieldFormatField().getValue());
          }

          @Override
          protected void execInitField() {
            setValue(ClientSession.get().getLocale());
          }
        }

        @Order(50)
        public class AutoDateDateField extends AbstractDateTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return "Auto date";
          }

          @Override
          protected void execChangedValue() {
            Date autoDate = getValue();
            getForm().getFieldByClass(ConfigurationBox.ConfigurationColumn1Box.InputField.class).setAutoDate(autoDate);
            getForm().getFieldByClass(ConfigurationBox.ConfigurationColumn2Box.TimeInputField.class).setAutoDate(autoDate);
            getForm().getFieldByClass(ConfigurationBox.ConfigurationColumn3Box.DateTimeInputField.class).setAutoDate(autoDate);
          }
        }
      }

      @Order(30)
      public class ConfigurationColumn2Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(5)
        public class TimeColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("TimeField"));
          }
        }

        @Order(50)
        public class TimeInputField extends AbstractTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TimeFieldInput");
          }

          @Override
          protected String execFormatValue(final Date value) {
            final ClientRunContext runContext = ClientRunContexts.copyCurrent().withLocale(getConfigLocaleField().getValue());
            return runContext.call(new Callable<String>() {
              @Override
              public String call() throws Exception {
                return TimeInputField.super.execFormatValue(value);
              }
            });
          }

          @Override
          public void setDisplayText(String s) {
            super.setDisplayText(s);
            getDateTimeDisplayTextField().setValue(s);
          }
        }

        @Order(60)
        public class GetTimeValueField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            return DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn2Box.TimeInputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            if (newMasterValue != null) {
              setValue(((Date) newMasterValue).toString());
            }
            else {
              setValue(null);
            }
          }
        }

        @Order(70)
        public class TimeFieldFormatField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TimeFieldFormat");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("UseSimpleDateFormatPattern");
          }

          @Override
          protected void execChangedValue() {
            getTimeInputField().setFormat(getTimeFieldFormatField().getValue());
          }
        }

        @Order(77)
        public class TimeDisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }
        }
      }

      @Order(40)
      public class ConfigurationColumn3Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(5)
        public class DateTimeColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "BOLD";
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("DateTimeField"));
          }
        }

        @Order(90)
        public class DateTimeInputField extends AbstractDateTimeField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateTimeFieldInput");
          }

          @Override
          protected String execFormatValue(final Date value) {
            final ClientRunContext runContext = ClientRunContexts.copyCurrent().withLocale(getConfigLocaleField().getValue());
            return runContext.call(new Callable<String>() {
              @Override
              public String call() throws Exception {
                return DateTimeInputField.super.execFormatValue(value);
              }
            });
          }

          @Override
          public void setDisplayText(String s) {
            super.setDisplayText(s);
            getDateTimeDisplayTextField().setValue(s);
          }
        }

        @Order(100)
        public class GetDateTimeValueField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

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
            return DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn3Box.DateTimeInputField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            if (newMasterValue != null) {
              setValue(((Date) newMasterValue).toString());
            }
            else {
              setValue(null);
            }
          }
        }

        @Order(110)
        public class DateTimeFieldFormatField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateTimeFieldFormat");
          }

          @Override
          protected String getConfiguredLabelFont() {
            return "ITALIC";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("UseSimpleDateFormatPattern");
          }

          @Override
          protected void execChangedValue() {
            getDateTimeInputField().setFormat(getDateTimeFieldFormatField().getValue());
          }
        }

        @Order(2000)
        public class DateTimeDisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }
        }
      }
    }

    private void updateDateTimeFields(Date d) {
      if (d != null) {
        getInputField().setValue(d);
        getTimeInputField().setValue(d);
        getDateTimeInputField().setValue(d);
        getDefaultField().setValue(d);
        getMandatoryField().setValue(d);
        getTimeField().setValue(d);
        getTimeMandatoryField().setValue(d);
        getDateTimeField().setValue(d);
        getDateTimeMandatoryField().setValue(d);
      }

      Locale oldLocale = NlsLocale.get();
      try {
        NlsLocale.set(getConfigLocaleField().getValue());
        getInputField().setFormat(getDateFieldFormatField().getValue());
        getTimeInputField().setFormat(getTimeFieldFormatField().getValue());
        getDateTimeInputField().setFormat(getDateTimeFieldFormatField().getValue());
      }
      finally {
        NlsLocale.set(oldLocale);
      }
    }

    @Order(30)
    public class NowButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Now");
      }

      @Override
      protected void execClickAction() {
        updateDateTimeFields(new Date());
      }
    }

    @Order(40)
    public class NineteenSeventyButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("NineteenSeventy");
      }

      @Override
      protected void execClickAction() {
        Date d = new Date();
        d.setTime(0);
        updateDateTimeFields(d);
      }
    }

    @Order(50)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() {
        getDateFieldFormatField().setValue("EEEE, dd. MMMM yyyy");
        getTimeFieldFormatField().setValue("HH:mm:ss Z");
        getDateTimeFieldFormatField().setValue("MM/dd/yy h:mm a");
      }
    }

    @Order(60)
    public class ShortFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShortFormat");
      }

      @Override
      protected void execClickAction() {
        DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(DateFormat.SHORT, getConfigLocaleField().getValue());
        if (df instanceof SimpleDateFormat) {
          getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
        }
      }
    }

    @Order(70)
    public class MediumFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("MediumFormat");
      }

      @Override
      protected void execClickAction() {
        DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(DateFormat.MEDIUM, getConfigLocaleField().getValue());
        if (df instanceof SimpleDateFormat) {
          getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
        }
      }
    }

    @Order(80)
    public class CustomMediumFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CustomMediumFormat");
      }

      @Override
      protected void execClickAction() {
        DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(CustomDateFormatProvider.CUSTOM_MEDIUM, getConfigLocaleField().getValue());
        if (df instanceof SimpleDateFormat) {
          getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
        }
      }
    }

    @Order(170)
    public class CloseButton extends AbstractCloseButton {

      @Override
      protected void execClickAction() {

      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
