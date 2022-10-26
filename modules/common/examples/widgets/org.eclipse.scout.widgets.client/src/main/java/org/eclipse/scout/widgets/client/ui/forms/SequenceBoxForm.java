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
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
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

@ClassId("c422cb04-4e77-4743-ad5d-391a4aeaae17")
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
  @ClassId("3b05c8c8-8090-4cd3-96d3-19d97672d2d9")
  public class MainBox extends AbstractGroupBox {

    @Order(20)
    @ClassId("0941da3b-9dff-4981-9a4d-a370ea9fac0c")
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
      @ClassId("158e30ca-7d51-498c-b52d-4dce8fb83e30")
      public class FromToBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FromToUsage");
        }

        @Order(10)
        @ClassId("b1407b52-0278-4ef3-a189-c5a0f079f225")
        public class DefaultBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Order(10)
          @ClassId("2a6291cf-b3e4-49ca-8236-a884cab348f2")
          public class FromField extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          @ClassId("08a374d7-bd67-4976-8f59-aba9a69dec3c")
          public class ToField extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(20)
        @ClassId("c12965b1-76c6-4e32-baed-4384f4a73fac")
        public class MandatoryBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mandatory");
          }

          @Order(10)
          @ClassId("45044636-ca2a-4725-9f2e-24542e676e0a")
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
          @ClassId("04e66ae1-3d98-48a0-9fc7-ba22829bc2f1")
          public class MandatoryTo extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30)
        @ClassId("1d5b8458-6eb0-4d74-b17c-24d21469211f")
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
          @ClassId("6ddc01d1-4f20-4078-86dc-1a356fc4bf2b")
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
          @ClassId("cf8e6e57-e0f4-4403-833d-bb88c56c6bb8")
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
      @ClassId("9333187b-d5da-4ac9-a38d-edc709a549da")
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
        @ClassId("2b92115b-bbb0-428d-bced-ee95cb322a3f")
        public class SearchBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(10)
          @ClassId("1344890f-bf5e-43f5-989d-5524f75bcc08")
          public class IconField extends AbstractImageField {

            @Override
            protected String getConfiguredImageId() {
              return Icons.Eye;
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredGridUseUiWidth() {
              return true;
            }

            @Override
            protected double getConfiguredGridWeightX() {
              return 0;
            }
          }

          @Order(20)
          @ClassId("043725af-4cb6-444b-b0c9-37aa971a703b")
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
          @ClassId("a377288b-40c9-456d-9be9-4b803a3fffd5")
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
          @ClassId("e89ec99b-26ea-405b-835d-7e5e4398bc72")
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
          @ClassId("fb7e0678-0132-4d83-ae0d-d29bf9e1bd62")
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
      @ClassId("66c91611-ff8c-4541-aec5-eca4c194a53a")
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

      @Order(40)
      @ClassId("7d59a390-ff5f-45dc-816e-4df58d9fd6e4")
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
        @ClassId("f1477722-b300-455b-a2dd-86a42ad3efbe")
        public class CompanyField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Company");
          }

          @Override
          protected String execValidateValue(String rawValue) {
            if ("error".equals(rawValue)) {
              throw new VetoException("Value is not allowed");
            }
            return rawValue;
          }
        }

        @Order(20)
        @ClassId("f9e2d90f-eba7-4e53-b9da-e3f9db57f6fa")
        public class EmployeesField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Employees");
          }

          @Order(10)
          @ClassId("2227276e-eee2-4aff-a259-38cff6202962")
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
        @ClassId("d0448113-519f-4aa3-86cf-2fd03e85c099")
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
        @ClassId("49c90805-b507-436d-93bd-2548ffca2ff0")
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
    @ClassId("09b741ad-ff2a-4fd2-97b0-6e9367c5181f")
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
      @ClassId("1930270f-da62-4097-b7c2-bdc1190d4d95")
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
      @ClassId("0a4b1113-ea7b-46d2-9cce-ef857fe3e51d")
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
      @ClassId("f0b2b26a-d653-43f2-9452-4095f530d9d9")
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
      @ClassId("8dd59ecc-09b8-4db1-aa31-0cc63922e1a5")
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
      @ClassId("c7cbc8f0-b42f-4753-a4e3-604293fee893")
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
    @ClassId("f6679327-bae6-466a-8044-ab1f0221ff97")
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
      @ClassId("8824e983-1e0f-4b92-a824-f6dc358868e7")
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
        @ClassId("757a40de-c3d0-42ff-b91e-891bc7aba91e")
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
        @ClassId("8c2d5c43-8d49-46c0-ae54-fee7456c69e1")
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
        @ClassId("60e924bc-7c33-4ea9-ae32-3110b0b11192")
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
        @ClassId("d8d81c0f-bb3b-4551-85bd-0ee8b1a43463")
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
        @ClassId("900f38de-5dcb-4984-b604-cd6a690de691")
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
      @ClassId("8f697c95-68c3-4de4-8cc2-e4d1cc800a1e")
      public class WidgetSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        @ClassId("6d98aad9-22ac-44de-84cf-707fde48eb58")
        public class WidgetStringField1 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF1";
          }
        }

        @Order(20)
        @ClassId("855cbc89-846c-4f95-8485-bf4aadea6198")
        public class WidgetStringField2 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF2";
          }
        }

        @Order(30)
        @ClassId("40818aa1-385a-41da-b9b6-73da040e576d")
        public class WidgetBooleanField1 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF1";
          }
        }

        @Order(40)
        @ClassId("00c20504-5b7a-41de-a578-b47c4e6aac37")
        public class WidgetBooleanField2 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF2";
          }
        }

        @Order(50)
        @ClassId("565f2cbd-21ff-4ddc-95a4-e34e00c1f809")
        public class WidgetStringField3 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF3";
          }
        }
      }
    }

    @Order(30)
    @ClassId("9a086347-0734-4c93-9e00-d41ad49374bc")
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
      @ClassId("84035654-b7a3-4661-b184-8cbf83add771")
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
        @ClassId("b0ccf4cc-454c-460b-ae88-d6ee45b5b0a5")
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
        @ClassId("255880c9-22d7-4620-8d68-ab9ef602b74f")
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
        @ClassId("665e4d4c-48da-4ca4-b3aa-f3ee096005b1")
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
        @ClassId("58aefc80-0d87-4f0c-99c9-8bb0d2dfc2c4")
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
        @ClassId("0e60f357-4b6a-465a-9b2b-03647935e418")
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
      @ClassId("67939dbc-54c5-42b9-aec0-9b122a722339")
      public class WidgetSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        @ClassId("a0cd9cd8-0611-46d2-9e2b-b1d6e909af86")
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
        @ClassId("be0a49c9-effc-4426-b34a-f3bca192a2c8")
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
        @ClassId("0c928a8b-26a8-45f7-b79a-ce621bdb2ef3")
        public class WidgetBooleanField1 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF1";
          }
        }

        @Order(40)
        @ClassId("3060d124-384d-43ce-b167-717616161842")
        public class WidgetBooleanField2 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF2";
          }
        }

        @Order(50)
        @ClassId("9e76ada7-a48f-4037-af9d-1966c39d95fe")
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
    @ClassId("9a9a4710-ca55-4019-b35e-edc1a7f6b93d")
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
      @ClassId("345f6c44-ea65-4ea6-a98a-c904d54644d6")
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
        @ClassId("ba3ffcd4-ca3a-49e9-8925-d64431ad75cd")
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
        @ClassId("91d2835b-79d2-46dd-aca8-4342d0232e69")
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
        @ClassId("ce2f2cb9-fb3c-4871-8be7-d44e94863bdd")
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
        @ClassId("632e4ce2-e6c4-4a2a-915f-06e82ea5e67d")
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
        @ClassId("03616763-ca29-4adf-98c3-999177af0752")
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
      @ClassId("2647370d-3214-4acd-af7e-dac310f50ce5")
      public class WidgetSequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        @ClassId("45c61983-4d6e-4fec-a132-a3c354b20976")
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
        @ClassId("496237f0-4deb-4af1-9dbd-184594c27b27")
        public class WidgetStringField2 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF2";
          }
        }

        @Order(30)
        @ClassId("6dc77bb3-3a33-4f32-a209-584d30e97660")
        public class WidgetBooleanField1 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF1";
          }
        }

        @Order(40)
        @ClassId("de55c4c1-256a-4410-957f-3b1720c864a7")
        public class WidgetBooleanField2 extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "BF2";
          }
        }

        @Order(50)
        @ClassId("f2a4ea4f-158d-4ca7-9497-cc1c5f01926b")
        public class WidgetStringField3 extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "SF3";
          }
        }
      }
    }

    @Order(30)
    @ClassId("8170687b-8c6b-471a-bee8-cc1a7d1d80cd")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ClassId("0b6e3d5b-cc88-4dfa-b575-bdba83b81ff1")
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

  @ClassId("3da5d9bb-b783-4c7f-a1a3-938f02ed6abf")
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
