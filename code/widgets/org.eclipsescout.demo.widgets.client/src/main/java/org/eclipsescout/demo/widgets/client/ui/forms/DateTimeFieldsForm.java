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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.nls.NlsLocale;
import org.eclipse.scout.rt.client.context.ClientRunContext;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.RuntimeExceptionTranslator;
import org.eclipse.scout.rt.platform.util.DateFormatProvider;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.ClientSession;
import org.eclipsescout.demo.widgets.client.services.lookup.DateFormatLocaleLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigLocaleField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.DateFieldFormatField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.DateTimeDisplayTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.DateTimeFieldFormatField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.DateTimeInputField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.DisplayTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.GetValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.InputField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.Placeholder1Field;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.TimeDisplayTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.TimeFieldFormatField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.TimeInputField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.TimeUpdateDisplayTextOnModifyField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.UpdateDisplayTextOnModifyField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DateColumnField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DateTimeColumnField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DateTimeDisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DateTimeField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DateTimeMandatoryField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.DisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.LocaleField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.MandatoryField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.PlaceholderField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.TimeColumnField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.TimeDisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.TimeField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ExamplesBox.TimeMandatoryField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.NineteenSeventyButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.NowButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.SampleFormatButton;
import org.eclipsescout.demo.widgets.shared.CustomDateFormatProvider;

public class DateTimeFieldsForm extends AbstractForm implements IPageForm {

  public DateTimeFieldsForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("NumberFields");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public TimeDisplayTextField getTimeDisplayTextField() {
    return getFieldByClass(TimeDisplayTextField.class);
  }

  /**
   * @return the TimeField
   */
  public TimeField getTimeField() {
    return getFieldByClass(TimeField.class);
  }

  /**
   * @return the Placeholder1Field
   */
  public Placeholder1Field getPlaceholder1Field() {
    return getFieldByClass(Placeholder1Field.class);
  }

  /**
   * @return the PlaceholderField
   */
  public PlaceholderField getPlaceholderField() {
    return getFieldByClass(PlaceholderField.class);
  }

  /**
   * @return the SampleFormatButton
   */
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

  /**
   * @return the DateTimeField
   */
  public DateTimeField getDateTimeField() {
    return getFieldByClass(DateTimeField.class);
  }

  /**
   * @return the DefaultField
   */
  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public NowButton getNowButton() {
    return getFieldByClass(NowButton.class);
  }

  /**
   * @return the GetCheckedKeysField
   */
  public GetValueField getGetValueField() {
    return getFieldByClass(GetValueField.class);
  }

  public InputField getInputField() {
    return getFieldByClass(InputField.class);
  }

  /**
   * @return the DateColumnField
   */
  public DateColumnField getDateColumnField() {
    return getFieldByClass(DateColumnField.class);
  }

  public TimeColumnField getTimeColumnField() {
    return getFieldByClass(TimeColumnField.class);
  }

  public TimeDisabledField getTimeDisabledField() {
    return getFieldByClass(TimeDisabledField.class);
  }

  /**
   * @return the LocaleField
   */
  public LocaleField getLocaleField() {
    return getFieldByClass(LocaleField.class);
  }

  /**
   * @return the TimeFieldFormatField
   */
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

  /**
   * @return the DateFieldFormatField
   */
  public DateFieldFormatField getDateFieldFormatField() {
    return getFieldByClass(DateFieldFormatField.class);
  }

  public ConfigLocaleField getConfigLocaleField() {
    return getFieldByClass(ConfigLocaleField.class);
  }

  public DateTimeColumnField getDateTimeColumnField() {
    return getFieldByClass(DateTimeColumnField.class);
  }

  /**
   * @return the DateTimeFieldFormatField
   */
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

  public TimeUpdateDisplayTextOnModifyField getTimeUpdateDisplayTextOnModifyField() {
    return getFieldByClass(TimeUpdateDisplayTextOnModifyField.class);
  }

  public UpdateDisplayTextOnModifyField getUpdateDisplayTextOnModifyField() {
    return getFieldByClass(UpdateDisplayTextOnModifyField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
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

      @Order(10.0)
      public class DateColumnField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("DateField"));
        }
      }

      @Order(20.0)
      public class DefaultField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("DateFieldKeyboardControl");
        }

      }

      @Order(30.0)
      public class MandatoryField extends AbstractDateField {

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
      public class DisabledField extends AbstractDateField {

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
          setValue(new Date());
        }
      }

      @Order(50.0)
      public class LocaleField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Locale");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(ClientSession.get().getLocale().getDisplayName());
        }
      }

      @Order(60.0)
      public class TimeColumnField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("TimeField"));
        }
      }

      @Order(70.0)
      public class TimeField extends AbstractTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

      }

      @Order(80.0)
      public class TimeMandatoryField extends AbstractTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(90.0)
      public class TimeDisabledField extends AbstractTimeField {

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
          setValue(new Date());
        }
      }

      @Order(100.0)
      public class PlaceholderField extends AbstractPlaceholderField {
      }

      @Order(110.0)
      public class DateTimeColumnField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("DateTimeField"));
        }
      }

      @Order(120.0)
      public class DateTimeField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

      }

      @Order(130.0)
      public class DateTimeMandatoryField extends AbstractDateTimeField {

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
      public class DateTimeDisabledField extends AbstractDateTimeField {

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
          setValue(new Date());
        }
      }
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class InputField extends AbstractDateField {

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
          }, BEANS.get(RuntimeExceptionTranslator.class));
        }

        @Override
        protected void execChangedDisplayText() {
          getDisplayTextField().setValue(getDisplayText());
        }
      }

      @Order(20.0)
      public class GetValueField extends AbstractStringField {

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
          return DateTimeFieldsForm.MainBox.ConfigurationBox.InputField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          if (newMasterValue != null) {
            setValue(((Date) newMasterValue).toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(30.0)
      public class DateFieldFormatField extends AbstractStringField {

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
        protected void execChangedValue() throws ProcessingException {
          getInputField().setFormat(getValue());
        }

      }

      @Order(37.0)
      public class DisplayTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DisplayText");
        }
      }

      @Order(38.0)
      public class UpdateDisplayTextOnModifyField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpdateDisplayTextOnModify");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setUpdateDisplayTextOnModify(getValue());
        }
      }

      @Order(40.0)
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
          return (Class<? extends ILookupCall<Locale>>) DateFormatLocaleLookupCall.class;
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("SelectLocale");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getInputField().setFormat(getDateFieldFormatField().getValue());
          getTimeInputField().setFormat(getTimeFieldFormatField().getValue());
          getDateTimeInputField().setFormat(getDateTimeFieldFormatField().getValue());
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(ClientSession.get().getLocale());
        }
      }

      @Order(50.0)
      public class TimeInputField extends AbstractTimeField {

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
          }, BEANS.get(RuntimeExceptionTranslator.class));
        }

        @Override
        protected void execChangedDisplayText() {
          getTimeDisplayTextField().setValue(getDisplayText());
        }
      }

      @Order(60.0)
      public class GetTimeValueField extends AbstractStringField {

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
          return DateTimeFieldsForm.MainBox.ConfigurationBox.TimeInputField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          if (newMasterValue != null) {
            setValue(((Date) newMasterValue).toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(70.0)
      public class TimeFieldFormatField extends AbstractStringField {

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
        protected void execChangedValue() throws ProcessingException {
          getTimeInputField().setFormat(getTimeFieldFormatField().getValue());
        }
      }

      @Order(77.0)
      public class TimeDisplayTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DisplayText");
        }
      }

      @Order(78.0)
      public class TimeUpdateDisplayTextOnModifyField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpdateDisplayTextOnModify");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getTimeInputField().setUpdateDisplayTextOnModify(getValue());
        }
      }

      @Order(80.0)
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

      @Order(90.0)
      public class DateTimeInputField extends AbstractDateTimeField {

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
          }, BEANS.get(RuntimeExceptionTranslator.class));
        }

        @Override
        protected void execChangedDisplayText() {
          getDateTimeDisplayTextField().setValue(getDisplayText());
        }
      }

      @Order(100.0)
      public class GetDateTimeValueField extends AbstractStringField {

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
          return DateTimeFieldsForm.MainBox.ConfigurationBox.DateTimeInputField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          if (newMasterValue != null) {
            setValue(((Date) newMasterValue).toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(110.0)
      public class DateTimeFieldFormatField extends AbstractStringField {

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
        protected void execChangedValue() throws ProcessingException {
          getDateTimeInputField().setFormat(getDateTimeFieldFormatField().getValue());
        }
      }

      @Order(2000.0)
      public class DateTimeDisplayTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DisplayText");
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

      Locale oldLocale = NlsLocale.get(false);
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

    @Order(30.0)
    public class NowButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Now");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        updateDateTimeFields(new Date());
      }
    }

    @Order(40.0)
    public class NineteenSeventyButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("NineteenSeventy");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        Date d = new Date();
        d.setTime(0);
        updateDateTimeFields(d);
      }
    }

    @Order(50.0)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getDateFieldFormatField().setValue("EEEE, dd. MMMM yyyy");
        getTimeFieldFormatField().setValue("HH:mm:ss Z");
        getDateTimeFieldFormatField().setValue("MM/dd/yy h:mm a");
      }
    }

    @Order(60.0)
    public class ShortFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShortFormat");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(DateFormat.SHORT, getConfigLocaleField().getValue());
        if (df instanceof SimpleDateFormat) {
          getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
        }
      }
    }

    @Order(70.0)
    public class MediumFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("MediumFormat");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(DateFormat.MEDIUM, getConfigLocaleField().getValue());
        if (df instanceof SimpleDateFormat) {
          getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
        }
      }
    }

    @Order(80.0)
    public class CustomMediumFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CustomMediumFormat");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(CustomDateFormatProvider.CUSTOM_MEDIUM, getConfigLocaleField().getValue());
        if (df instanceof SimpleDateFormat) {
          getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
        }
      }
    }

    @Order(170.0)
    public class CloseButton extends AbstractCloseButton {

      @Override
      protected void execClickAction() throws ProcessingException {

      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
