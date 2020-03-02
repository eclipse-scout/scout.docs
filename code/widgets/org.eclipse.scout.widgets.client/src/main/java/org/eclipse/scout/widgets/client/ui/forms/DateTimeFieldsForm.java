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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.client.context.ClientRunContext;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.date.DateFormatProvider;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.ConfigLocaleField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.DateFieldFormatField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.DisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.DontAllowCurrentDateField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.GetValueField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.HasDateField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.HasTimeField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn1Box.InputField;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm.MainBox.ConfigurationBox.ConfigurationColumn2Box.DontAllow1000TimeField;
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
import org.eclipse.scout.widgets.shared.CustomDateFormatProvider;

@ClassId("51fd5a1c-4b2b-4094-a16e-c9865b9bf9ce")
public class DateTimeFieldsForm extends AbstractForm implements IPageForm {

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

  public GetValueField getGetValueField() {
    return getFieldByClass(GetValueField.class);
  }

  public InputField getInputField() {
    return getFieldByClass(InputField.class);
  }

  public DateColumnField getDateColumnField() {
    return getFieldByClass(DateColumnField.class);
  }

  public DontAllowCurrentDateField getDontAllowCurrentDateField() {
    return getFieldByClass(DontAllowCurrentDateField.class);
  }

  public DontAllow1000TimeField getDontAllow1000TimeField() {
    return getFieldByClass(DontAllow1000TimeField.class);
  }

  public HasDateField getHasDateField() {
    return getFieldByClass(HasDateField.class);
  }

  public HasTimeField getHasTimeField() {
    return getFieldByClass(HasTimeField.class);
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

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10)
  @ClassId("b89d82db-0eec-4d12-ad77-8e565d4deae0")
  public class MainBox extends AbstractGroupBox {

    @Order(5)
    @ClassId("2a956217-5956-4071-bbbe-e40c0cd1858d")
    public class SeleniumTestMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return "Selenium";
      }

      @Order(100)
      @ClassId("a6b583ba-abb1-47f6-8ba2-9d815e7a080f")
      public class ToggleSearchRequiredMenu extends AbstractToggleMenu {

        @Override
        protected String getConfiguredText() {
          return "Add error status";
        }

        @Override
        protected void execAlways() {
          if (isActive()) {
            getInputField().addErrorStatus(new DateTimeErrorStatus());
          } else {
            getInputField().removeErrorStatus(DateTimeErrorStatus.class);
          }
        }
      }
    }

    @Order(10)
    @ClassId("abf5111c-e978-4d77-9c34-8698b71eb6ac")
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
      @ClassId("3b629bec-07d5-43a1-8905-d51383b0a152")
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
        @ClassId("5d485944-f802-425e-bc2b-1e30a582f631")
        public class TitleColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("");
          }
        }

        @Order(20)
        @ClassId("f8f3c196-c14a-4f4f-acec-f2532086b026")
        public class TitleDefaultField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Default"));
          }
        }

        @Order(30)
        @ClassId("3013e9fd-ae6a-4bb5-b048-05d9cce6e77c")
        public class TitleMandatoryField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Mandatory"));
          }
        }

        @Order(40)
        @ClassId("df3310b6-4a77-42ff-aedf-7e7df25cecbb")
        public class TitleDisabledField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Disabled"));
          }
        }

        @Order(50)
        @ClassId("3d3bd919-c292-4489-a2a5-c59ffa9caaa7")
        public class TitleLocaleField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Locale"));
          }
        }
      }

      @Order(10)
      @ClassId("44c66e36-d5d0-40cb-b9fe-d08932455c5e")
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
        @ClassId("5cafaf9a-733e-4b88-bb57-ff122cc4d182")
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
        @ClassId("98e2958e-4716-4dff-938b-fa42619940fc")
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
        @ClassId("9cb48c34-6433-4770-a6f4-e712141d4fea")
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
        @ClassId("0d787021-f5bb-4b49-b0f0-ab8ec1974baa")
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
        @ClassId("1d497866-e3e8-4114-bc9d-b7dee9e5cc4b")
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
      @ClassId("5f422501-f37b-4ff5-ad25-67122f98c412")
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
        @ClassId("0f390914-0ba3-4ee1-9e3d-a7130e89d2f8")
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
        @ClassId("b833e50b-f59b-41ba-a175-81a784721623")
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
        @ClassId("462b0b86-50fa-4c29-83ce-c3d2e2255694")
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
        @ClassId("d46891db-e134-4806-9dfa-3881982d5f2a")
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
      @ClassId("ffe5ba6d-d2e9-4c94-8f84-633c39cb1613")
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
        @ClassId("9ccba0a4-6a1b-4ed0-9e00-fe0221f2ad79")
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
        @ClassId("83ede272-3447-48e1-88d5-6a06dd8322c5")
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
        @ClassId("0d720d62-dbb4-4dd3-bcc0-8c1a66d90e12")
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
        @ClassId("963b7452-8948-4e4f-889c-de7f0d7cb647")
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
    @ClassId("3bd76f55-2ad7-4e27-9e00-c7697c1e9bb5")
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
      @ClassId("15bae7a4-ef70-4c3e-8baa-297b06878f18")
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
        @ClassId("f2c2faf2-6296-43bc-b448-1a4c155b3f08")
        public class TitleColumnField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("");
          }
        }

        @Order(20)
        @ClassId("e7141c24-c150-4736-92ac-fb2da601b4c1")
        public class TitleInputField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("Input");
          }
        }

        @Order(30)
        @ClassId("3fd31445-e0a1-4e22-b73e-fa499ea31bbf")
        public class TitleValueField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Value"));
          }
        }

        @Order(40)
        @ClassId("3a6feb60-087d-420d-853d-40df69cce512")
        public class TitleFormatField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Format"));
          }
        }

        @Order(50)
        @ClassId("f10f7254-3be1-4a8a-99af-115217b2f9e6")
        public class TitleDisplayTextField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("DisplayText"));
          }
        }

        @Order(60)
        @ClassId("7f2ccf64-d155-4977-90e1-7f506a460046")
        public class TitleLocaleField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(TEXTS.get("Locale"));
          }
        }

        @Order(70)
        @ClassId("465c5c06-76d1-4302-a54a-b1e5ab17288e")
        public class TitleAutoDateField extends AbstractLabelField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue("Auto date");
          }
        }
      }

      @Order(20)
      @ClassId("0be45e8c-b9be-44fe-8db8-0616b66ba710")
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
        @ClassId("3599b030-4037-4164-9c14-a93599be2161")
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
        @ClassId("89641f54-7d94-46a3-8164-74beee039739")
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
            return runContext.call(() -> InputField.super.execFormatValue(value));
          }

          @Override
          public void setDisplayText(String s) {
            super.setDisplayText(s);
            getDisplayTextField().setValue(s);
          }

          @Override
          protected Date execValidateValue(Date rawValue) {
            Date date = super.execValidateValue(rawValue);
            if (getDontAllowCurrentDateField().getValue() && DateUtility.isSameDay(date, new Date())) {
              throw new VetoException("You are not allowed to select the current day");
            }
            return date;
          }
        }

        @Order(20)
        @ClassId("3c3037e1-e3fe-4eb1-930d-f6914d6b1667")
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
            return InputField.class;
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
        @ClassId("7c448403-31c0-485c-b0ae-83c333a074dc")
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
        @ClassId("6d7aa35b-6ac9-44a8-b0ca-670df30cde64")
        public class DisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }
        }

        @Order(40)
        @ClassId("99fb8342-ee2f-48e2-b965-9d2ee8db8c00")
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
        @ClassId("5a641bfd-c1c7-4d12-b3db-52a774bafa28")
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
            getForm().getFieldByClass(InputField.class).setAutoDate(autoDate);
            getForm().getFieldByClass(TimeInputField.class).setAutoDate(autoDate);
            getForm().getFieldByClass(DateTimeInputField.class).setAutoDate(autoDate);
          }
        }

        @Order(2000)
        @ClassId("f12e34f8-a2d6-4c12-8246-a2db6d4aeec4")
        public class DontAllowCurrentDateField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DontAllowCurrentDate");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            InputField field = getForm().getFieldByClass(InputField.class);
            field.setValue(field.getValue()); // revalidate
          }
        }

        @Order(3000)
        @ClassId("ea115900-a7b4-410b-a14d-2405f74026ec")
        public class HasDateField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("HasDate");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            getInputField().setHasDate(!getInputField().isHasDate());
          }

          @Override
          protected void execInitField() {
            setValue(getInputField().isHasDate());
          }
        }

        @Order(4000)
        @ClassId("86d06171-f93b-4d19-86f1-f3e1fc79cc82")
        public class HasTimeField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("HasTime");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            getInputField().setHasTime(!getInputField().isHasTime());
          }

          @Override
          protected void execInitField() {
            setValue(getInputField().isHasTime());
          }
        }
      }

      @Order(30)
      @ClassId("acfc3fae-9897-4b44-abce-d5600ad506e7")
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
        @ClassId("e892b778-4fa6-473b-a652-53a9a79d60ff")
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
        @ClassId("66086496-dc42-48e6-ae76-0b401fcee493")
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
            return runContext.call(() -> TimeInputField.super.execFormatValue(value));
          }

          @Override
          public void setDisplayText(String s) {
            super.setDisplayText(s);
            getTimeDisplayTextField().setValue(s);
          }

          @Override
          protected Date execValidateValue(Date rawValue) {
            Date date = super.execValidateValue(rawValue);
            if (date == null) {
              return null;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (getDontAllow1000TimeField().getValue() && cal.get(Calendar.HOUR_OF_DAY) == 10 && cal.get(Calendar.MINUTE) == 0) {
              throw new VetoException("You are not allowed to select 10:00");
            }
            return date;
          }
        }

        @Order(60)
        @ClassId("d375eb91-ef76-446f-8022-d43198ab97f8")
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
            return TimeInputField.class;
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
        @ClassId("88cae563-eedb-49f1-8cbe-a2572576d5ee")
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
        @ClassId("a03c4433-68a3-42c0-bd67-fc2eb20ed10f")
        public class TimeDisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }
        }

        @Order(2000)
        @ClassId("b7ed1507-62b8-447f-b23f-3d9883aec82c")
        public class DontAllow1000TimeField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DontAllow1000Time");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            TimeInputField field = getForm().getFieldByClass(TimeInputField.class);
            field.setValue(field.getValue()); // revalidate
          }
        }

      }

      @Order(40)
      @ClassId("582f8706-0f19-4eda-aef3-365878cb2595")
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
        @ClassId("7c95be4f-5cad-4f50-b4bb-d774924f7e3c")
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
        @ClassId("030711c5-cdad-4256-9106-332853ca4d12")
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
            return runContext.call(() -> DateTimeInputField.super.execFormatValue(value));
          }

          @Override
          public void setDisplayText(String s) {
            super.setDisplayText(s);
            getDateTimeDisplayTextField().setValue(s);
          }
        }

        @Order(100)
        @ClassId("4285cb38-26b6-4c2d-9de0-65d252738b35")
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
            return DateTimeInputField.class;
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
        @ClassId("f1aeb702-fcd0-4a8a-8476-da44172c8136")
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
        @ClassId("e7769afc-1759-4070-83bb-374a79b13670")
        public class DateTimeDisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
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

    @Order(10)
    @ClassId("dd71f617-a8b0-46d6-87d1-c9a0353c1a16")
    public class SetDateMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("SetDate");
      }

      @Order(10)
      @ClassId("84217680-3893-4b42-8923-95618af4cefe")
      public class NowMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Now");
        }

        @Override
        protected void execAction() {
          updateDateTimeFields(new Date());
        }
      }

      @Order(20)
      @ClassId("025f9481-87a4-41a5-a28f-252a2095ceb3")
      public class NineteenSeventyMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("NineteenSeventy");
        }

        @Override
        protected void execAction() {
          Date d = new Date();
          d.setTime(0);
          updateDateTimeFields(d);
        }
      }

    }

    @Order(20)
    @ClassId("d3de745e-3066-4141-b186-f523f20cde27")
    public class SetFormatMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("SetFormat");
      }

      @Order(10)
      @ClassId("a75b8f23-2347-4593-889b-8c7532852366")
      public class SampleFormatMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("SampleFormat");
        }

        @Override
        protected void execAction() {
          getDateFieldFormatField().setValue("EEEE, dd. MMMM yyyy");
          getTimeFieldFormatField().setValue("HH:mm:ss Z");
          getDateTimeFieldFormatField().setValue("MM/dd/yy h:mm a");
        }
      }

      @Order(20)
      @ClassId("90cb583c-195d-4f04-a8db-eef97ff5b84e")
      public class ShortFormatMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ShortFormat");
        }

        @Override
        protected void execAction() {
          DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(DateFormat.SHORT, getConfigLocaleField().getValue());
          if (df instanceof SimpleDateFormat) {
            getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
          }
        }
      }

      @Order(30)
      @ClassId("03d519da-517f-4178-bd78-efb3f1136dfa")
      public class MediumFormatMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("MediumFormat");
        }

        @Override
        protected void execAction() {
          DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(DateFormat.MEDIUM, getConfigLocaleField().getValue());
          if (df instanceof SimpleDateFormat) {
            getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
          }
        }
      }

      @Order(40)
      @ClassId("5b74b5f1-d158-4979-8023-371de53ec5f8")
      public class CustomMediumFormatMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("CustomMediumFormat");
        }

        @Override
        protected void execAction() {
          DateFormat df = BEANS.get(DateFormatProvider.class).getDateInstance(CustomDateFormatProvider.CUSTOM_MEDIUM, getConfigLocaleField().getValue());
          if (df instanceof SimpleDateFormat) {
            getDateFieldFormatField().setValue(((SimpleDateFormat) df).toPattern());
          }
        }
      }
    }

    @Order(30)
    @ClassId("1cda221b-dc81-40d9-ac60-38011df06f01")
    public class ToggleAllowedDatesButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("AllowedDates");
      }

      @Override
      protected int getConfiguredDisplayStyle() {
        return IButton.DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("AllowedDatesTooltip");
      }

      @Override
      protected void execSelectionChanged(boolean selection) {
        List<Date> allowedDates = null;
        if (selection) {
          allowedDates = new ArrayList<>();
          Date today = DateUtility.truncDate(new Date());
          allowedDates.add(DateUtility.addTime(today, Calendar.DATE, -1));
          allowedDates.add(DateUtility.addTime(today, Calendar.DATE, 1));
          allowedDates.add(DateUtility.addTime(today, Calendar.MONTH, -1));
          allowedDates.add(DateUtility.addTime(today, Calendar.MONTH, 1));
        }
        getDefaultField().setAllowedDates(allowedDates);
      }
    }

    @Order(170)
    @ClassId("482d73ed-1ecb-4aa1-b574-adec34c1a88d")
    public class CloseButton extends AbstractCloseButton {
      @Override
      protected void execClickAction() {
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  public class DateTimeErrorStatus extends Status {
    public DateTimeErrorStatus() {
      super("Error from Java model");
    }
  }
}
