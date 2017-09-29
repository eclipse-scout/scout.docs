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
import java.util.Date;
import java.util.List;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.ISequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.CompanySearchBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.CompanySearchBox.CompanyField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.CompanySearchBox.CompanySearchButton;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.CompanySearchBox.EmployeesField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.CompanySearchBox.IndustryField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox.FromField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox.ToField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DisabledBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DisabledBox.DisabledFrom;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DisabledBox.DisabledTo;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.MandatoryBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.MandatoryBox.MandatoryFrom;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.MandatoryBox.MandatoryTo;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox.SearchBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox.SearchBox.FirstNameField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox.SearchBox.FormResetButton;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox.SearchBox.IconField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox.SearchBox.LastNameField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.HorizontalFieldGroupBox.SearchBox.SearchButton;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.Placeholder1Field;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.FieldVisibilityBox;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.FieldVisibilityBox.VisibleCompanyField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.FieldVisibilityBox.VisibleEmployeeField;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.FieldVisibilityBox.VisibleIndustryField;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;

public class SequenceBoxForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SequenceBox");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultBox getDefaultBox() {
    return getFieldByClass(DefaultBox.class);
  }

  public DisabledBox getDisabledBox() {
    return getFieldByClass(DisabledBox.class);
  }

  public DisabledFrom getDisabledFrom() {
    return getFieldByClass(DisabledFrom.class);
  }

  public DisabledTo getDisabledTo() {
    return getFieldByClass(DisabledTo.class);
  }

  public EmployeesField getEmployeesField() {
    return getFieldByClass(EmployeesField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public FieldVisibilityBox getFieldVisibilityBox() {
    return getFieldByClass(FieldVisibilityBox.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public FormResetButton getFormResetButton() {
    return getFieldByClass(FormResetButton.class);
  }

  public FromField getFromField() {
    return getFieldByClass(FromField.class);
  }

  public FromToBox getFromToBox() {
    return getFieldByClass(FromToBox.class);
  }

  public IconField getIconField() {
    return getFieldByClass(IconField.class);
  }

  public IndustryField getIndustryField() {
    return getFieldByClass(IndustryField.class);
  }

  public LastNameField getLastNameField() {
    return getFieldByClass(LastNameField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public CompanyField getCompanyField() {
    return getFieldByClass(CompanyField.class);
  }

  public CompanySearchBox getCompanySearchBox() {
    return getFieldByClass(CompanySearchBox.class);
  }

  public CompanySearchButton getCompanySearchButton() {
    return getFieldByClass(CompanySearchButton.class);
  }

  public MandatoryBox getMandatoryBox() {
    return getFieldByClass(MandatoryBox.class);
  }

  public MandatoryFrom getMandatoryFrom() {
    return getFieldByClass(MandatoryFrom.class);
  }

  public MandatoryTo getMandatoryTo() {
    return getFieldByClass(MandatoryTo.class);
  }

  public HorizontalFieldGroupBox getHorizontalFieldGroupBox() {
    return getFieldByClass(HorizontalFieldGroupBox.class);
  }

  public Placeholder1Field getPlaceholder1Field() {
    return getFieldByClass(Placeholder1Field.class);
  }

  public SearchBox getSearchBox() {
    return getFieldByClass(SearchBox.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public ToField getToField() {
    return getFieldByClass(ToField.class);
  }

  public VisibleCompanyField getVisibleCompanyField() {
    return getFieldByClass(VisibleCompanyField.class);
  }

  public VisibleEmployeeField getVisibleEmployeeField() {
    return getFieldByClass(VisibleEmployeeField.class);
  }

  public VisibleIndustryField getVisibleIndustryField() {
    return getFieldByClass(VisibleIndustryField.class);
  }

  public FromField getfromField() {
    return getFieldByClass(FromField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(20)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class FromToBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FromToUsage");
        }

        @Order(10)
        public class DefaultBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Order(10)
          public class FromField extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          public class ToField extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(20)
        public class MandatoryBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mandatory");
          }

          @Order(10)
          public class MandatoryFrom extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }

            @Override
            protected boolean getConfiguredMandatory() {
              return true;
            }
          }

          @Order(20)
          public class MandatoryTo extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30)
        public class DisabledBox extends AbstractSequenceBox {

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
            Date now = new Date();
            Date nineteen70 = new Date();
            nineteen70.setTime(0);

            getDisabledFrom().setValue(nineteen70);
            getDisabledTo().setValue(now);
          }

          @Order(10)
          public class DisabledFrom extends AbstractDateField {

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected boolean getConfiguredHasTime() {
              return true;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          public class DisabledTo extends AbstractDateField {

            @Override
            protected boolean getConfiguredEnabled() {
              return false;
            }

            @Override
            protected boolean getConfiguredHasTime() {
              return true;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }
      }

      @Order(20)
      public class HorizontalFieldGroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HorizontalFieldGroupUsage");
        }

        @Order(10)
        public class SearchBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          // TODO [7.0] bsh: BUG, this property seems to be pretty broken
          @Override
          protected boolean getConfiguredEqualColumnWidths() {
            return false;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(10)
          public class IconField extends AbstractImageField {

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return 1;
            }

            @Override
            protected String getConfiguredImageId() {
              return Icons.Eye;
            }

            @Override
            protected int getConfiguredWidthInPixel() {
              return 150;
            }
          }

          @Order(20)
          public class FirstNameField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("FirstName");
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }
          }

          @Order(30)
          public class LastNameField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("LastName");
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }
          }

          @Order(40)
          public class SearchButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Search");
            }

            @Override
            protected void execClickAction() {
              String name = StringUtility.concatenateTokens(getFirstNameField().getValue(), " ", getLastNameField().getValue());

              if (!name.isEmpty()) {
                MessageBoxes.create()
                    .withHeader(TEXTS.get("SearchPerson", name))
                    .withAutoCloseMillis(2500)
                    .show(MessageBox.YES_OPTION);
              }
            }
          }

          @Order(50)
          public class FormResetButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("FormReset");
            }

            @Override
            protected void execClickAction() {
              getFirstNameField().setValue(null);
              getLastNameField().setValue(null);
            }
          }
        }

      }

      @Order(30)
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

      @Order(40)
      public class CompanySearchBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Order(10)
        public class CompanyField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Company");
          }

          @Override
          protected String execValidateValue(String rawValue) {
            if (rawValue != null && "error".equals(rawValue)) {
              throw new VetoException("Value is not allowed");
            }
            return rawValue;
          }
        }

        @Order(20)
        public class EmployeesField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Employees");
          }

          @Order(10)
          public class MenuField extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("NotNullMenu");
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withHeader("click!").show();
            }
          }
        }

        @Order(30)
        public class IndustryField extends AbstractSmartField<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return IndustryICBCodeType.class;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Industry");
          }
        }

        @Order(40)
        public class CompanySearchButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Retrieve");
          }

          @Override
          protected boolean getConfiguredVisible() {
            return false;
          }
        }
      }
    }

    @Order(20)
    public class FieldVisibilityBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("FieldVisibility");
      }

      @Order(40)
      public class VisibleCompanyField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleCompany");
        }

        @Override
        protected void execChangedValue() {
          getCompanyField().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getCompanyField().isVisible());
        }
      }

      @Order(50)
      public class VisibleEmployeeField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleEmployee");
        }

        @Override
        protected void execChangedValue() {
          getEmployeesField().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getEmployeesField().isVisible());
        }
      }

      @Order(60)
      public class VisibleIndustryField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleIndustry0");
        }

        @Override
        protected void execChangedValue() {
          getIndustryField().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getIndustryField().isVisible());
        }
      }

      @Order(70)
      public class TooltipEmployeeField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TooltipEmployee");
        }

        @Override
        protected void execChangedValue() {
          String tooltip = null;
          if (getValue()) {
            tooltip = "This is a tooltip";
          }
          getEmployeesField().setTooltipText(tooltip);
        }

        @Override
        protected void execInitField() {
          setValue(getEmployeesField().getTooltipText() != null);
        }
      }

      @Order(80)
      public class StatusVisibleIndustryField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StatusVisibleIndustry");
        }

        @Override
        protected void execChangedValue() {
          getIndustryField().setStatusVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getIndustryField().isStatusVisible());
        }
      }
    }

    @Order(30)
    public class Widget1GroupBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "SequenceBox with default widget label position";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      public class WidgetControlSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Override
        protected String execCreateLabelSuffix() {
          return null;
        }

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Change a widget";
        }

        @Order(10)
        public class WidgetSmartField extends AbstractSmartField<IFormField> {

          @Override
          protected String getConfiguredLabel() {
            return "Widget";
          }

          @Override
          protected void initConfig() {
            super.initConfig();
            setLookupCall(new WidgetLookupCall(WidgetSequenceBox.class));
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20)
        public class VisibleSmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Visible";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(true);
          }
        }

        @Order(30)
        public class MandatorySmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Mandatory";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(40)
        public class ErrorStatusSmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Error Status";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(50)
        public class ApplyButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Apply";
          }

          @Override
          protected void execClickAction() {
            boolean visible = BooleanUtility.nvl(getFieldByClass(VisibleSmartField.class).getValue());
            boolean mandatory = BooleanUtility.nvl(getFieldByClass(MandatorySmartField.class).getValue());
            boolean errorStatus = BooleanUtility.nvl(getFieldByClass(ErrorStatusSmartField.class).getValue());

            IFormField field = getFieldByClass(WidgetSmartField.class).getValue();
            field.setVisible(visible);
            field.setMandatory(mandatory);
            if (errorStatus) {
              field.addErrorStatus("error");
            }
            else {
              field.clearErrorStatus();
            }
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return WidgetSmartField.class;
          }

          @Override
          protected boolean getConfiguredMasterRequired() {
            return true;
          }
        }
      }

      @Order(20)
      public class WidgetSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        public class WidgetStringField1 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF1";
          }
        }

        @Order(20)
        public class WidgetStringField2 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF2";
          }
        }

        @Order(30)
        public class WidgetBooleanField1 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF1";
          }
        }

        @Order(40)
        public class WidgetBooleanField2 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF2";
          }
        }

        @Order(50)
        public class WidgetStringField3 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF3";
          }
        }
      }
    }

    @Order(30)
    public class Widget2GroupBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "SequenceBox with 'on-field' widget label position";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      public class WidgetControlSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Override
        protected String execCreateLabelSuffix() {
          return null;
        }

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Change a widget";
        }

        @Order(10)
        public class WidgetSmartField extends AbstractSmartField<IFormField> {

          @Override
          protected String getConfiguredLabel() {
            return "Widget";
          }

          @Override
          protected void initConfig() {
            super.initConfig();
            setLookupCall(new WidgetLookupCall(WidgetSequenceBox.class));
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20)
        public class VisibleSmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Visible";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(true);
          }
        }

        @Order(30)
        public class MandatorySmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Mandatory";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(40)
        public class ErrorStatusSmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Error Status";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(50)
        public class ApplyButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Apply";
          }

          @Override
          protected void execClickAction() {
            boolean visible = BooleanUtility.nvl(getFieldByClass(VisibleSmartField.class).getValue());
            boolean mandatory = BooleanUtility.nvl(getFieldByClass(MandatorySmartField.class).getValue());
            boolean errorStatus = BooleanUtility.nvl(getFieldByClass(ErrorStatusSmartField.class).getValue());

            IFormField field = getFieldByClass(WidgetSmartField.class).getValue();
            field.setVisible(visible);
            field.setMandatory(mandatory);
            if (errorStatus) {
              field.addErrorStatus("error");
            }
            else {
              field.clearErrorStatus();
            }
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return WidgetSmartField.class;
          }

          @Override
          protected boolean getConfiguredMasterRequired() {
            return true;
          }
        }
      }

      @Order(20)
      public class WidgetSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        public class WidgetStringField1 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF1";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20)
        public class WidgetStringField2 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF2";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(30)
        public class WidgetBooleanField1 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF1";
          }
        }

        @Order(40)
        public class WidgetBooleanField2 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF2";
          }
        }

        @Order(50)
        public class WidgetStringField3 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF3";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }
      }
    }

    @Order(30)
    public class Widget3GroupBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "SequenceBox with mixed widget label position";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      public class WidgetControlSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Override
        protected String execCreateLabelSuffix() {
          return null;
        }

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Change a widget";
        }

        @Order(10)
        public class WidgetSmartField extends AbstractSmartField<IFormField> {

          @Override
          protected String getConfiguredLabel() {
            return "Widget";
          }

          @Override
          protected void initConfig() {
            super.initConfig();
            setLookupCall(new WidgetLookupCall(WidgetSequenceBox.class));
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20)
        public class VisibleSmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Visible";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(true);
          }
        }

        @Order(30)
        public class MandatorySmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Mandatory";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(40)
        public class ErrorStatusSmartField extends AbstractSmartField<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return "Error Status";
          }

          @Override
          protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
            return YesNoLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(50)
        public class ApplyButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Apply";
          }

          @Override
          protected void execClickAction() {
            boolean visible = BooleanUtility.nvl(getFieldByClass(VisibleSmartField.class).getValue());
            boolean mandatory = BooleanUtility.nvl(getFieldByClass(MandatorySmartField.class).getValue());
            boolean errorStatus = BooleanUtility.nvl(getFieldByClass(ErrorStatusSmartField.class).getValue());

            IFormField field = getFieldByClass(WidgetSmartField.class).getValue();
            field.setVisible(visible);
            field.setMandatory(mandatory);
            if (errorStatus) {
              field.addErrorStatus("error");
            }
            else {
              field.clearErrorStatus();
            }
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return WidgetSmartField.class;
          }

          @Override
          protected boolean getConfiguredMasterRequired() {
            return true;
          }
        }
      }

      @Order(20)
      public class WidgetSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        public class WidgetStringField1 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF1";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20)
        public class WidgetStringField2 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF2";
          }
        }

        @Order(30)
        public class WidgetBooleanField1 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF1";
          }
        }

        @Order(40)
        public class WidgetBooleanField2 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF2";
          }
        }

        @Order(50)
        public class WidgetStringField3 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF3";
          }
        }
      }
    }

    @Order(30)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  public static class YesNoLookupCall extends LocalLookupCall<Boolean> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<Boolean>> execCreateLookupRows() {
      List<ILookupRow<Boolean>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(Boolean.TRUE, "Yes"));
      rows.add(new LookupRow<>(Boolean.FALSE, "No"));
      return rows;
    }
  }

  public class WidgetLookupCall extends LocalLookupCall<IFormField> {

    private static final long serialVersionUID = 1L;
    private final Class<? extends ISequenceBox> m_sequenceBox;

    public WidgetLookupCall(Class<? extends ISequenceBox> sequenceBox) {
      m_sequenceBox = sequenceBox;
    }

    @Override
    protected List<? extends ILookupRow<IFormField>> execCreateLookupRows() {
      List<ILookupRow<IFormField>> rows = new ArrayList<>();

      ISequenceBox sequenceBox = getFieldByClass(m_sequenceBox);
      for (IFormField field : sequenceBox.getFields()) {
        rows.add(new LookupRow<>(field, field.getLabel()));
      }

      rows.add(new LookupRow<>(sequenceBox, sequenceBox.getLabel()));

      return rows;
    }
  }
}
