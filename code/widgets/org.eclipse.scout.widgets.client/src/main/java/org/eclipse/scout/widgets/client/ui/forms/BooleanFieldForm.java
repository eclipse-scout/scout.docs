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

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.FontStyleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ConfigurationBox.CheckboxField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ConfigurationBox.FontNameField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ConfigurationBox.FontStyleField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ConfigurationBox.ReportedBox.ReportedDisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ConfigurationBox.ReportedBox.ReportedValueField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ExamplesBox.DefaultTriStateField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ExamplesBox.DisabledTriStateField;

@ClassId("2e0290a8-dd4d-4493-b586-25f202fd2922")
public class BooleanFieldForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "BooleanField (CheckBox)";
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DefaultTriStateField getDefaultTriStateField() {
    return getFieldByClass(DefaultTriStateField.class);
  }

  public CheckboxField getCheckboxField() {
    return getFieldByClass(CheckboxField.class);
  }

  public FontNameField getFontNameField() {
    return getFieldByClass(FontNameField.class);
  }

  public FontStyleField getFontStyleField() {
    return getFieldByClass(FontStyleField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public DisabledTriStateField getDisabledTriStateField() {
    return getFieldByClass(DisabledTriStateField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ReportedValueField getReportedValueField() {
    return getFieldByClass(ReportedValueField.class);
  }

  public ReportedDisplayTextField getReportedDisplayTextField() {
    return getFieldByClass(ReportedDisplayTextField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10)
  @ClassId("2e768c81-54a3-4191-8481-6a16caf14051")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 2;
    }

    @Order(10)
    @ClassId("c81d67f2-6a19-4107-847a-e4ec51454701")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("fe4d422f-64d1-46b7-8ff7-a9871149ad3b")
      public class DefaultField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return "ctrl-b";
        }

        @Override
        protected void execChangedValue() {
          getDisabledField().setValue(this.getValue());
        }
      }

      @Order(20)
      @ClassId("116a2b16-88ee-4a35-8794-b38120e8cf33")
      public class DisabledField extends AbstractBooleanField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }
      }

      @Order(30)
      @ClassId("381b970a-5c70-4451-ba25-89fc9e463bf4")
      public class DefaultTriStateField extends AbstractBooleanField {

        @Override
        protected boolean getConfiguredTriStateEnabled() {
          return true;
        }

        @Override
        protected String getConfiguredLabel() {
          return "TriState";
        }

        @Override
        protected void execChangedValue() {
          getDisabledTriStateField().setValue(this.getValue());
        }
      }

      @Order(40)
      @ClassId("01bbf305-7fd1-44d2-b761-28e2655a6b75")
      public class DisabledTriStateField extends AbstractBooleanField {

        @Override
        protected boolean getConfiguredTriStateEnabled() {
          return true;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "TriState " + TEXTS.get("Disabled");
        }
      }
    }

    @Order(30)
    @ClassId("2e04b0e0-b07c-4554-9d2d-9c68c0496bae")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return true;
      }

      @Order(10)
      @ClassId("945ae326-1982-40af-8b8e-741639dd9114")
      public class CheckboxField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Checkbox");
        }

        @Override
        protected void execInitField() {
          // Update slave fields
          fireValueChanged();
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "Hi, I am a tooltip!";
        }

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return false;
        }
      }

      @Order(15)
      @ClassId("63c9a3e9-715a-4040-957b-d2e690058f12")
      public class ReportedBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("4afad359-8dd8-4433-a5cd-c07f1c245190")
        public class ReportedValueField extends AbstractStringField {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Value");
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return CheckboxField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            setValue("" + getCheckboxField().getValue());
          }
        }

        @Order(20)
        @ClassId("bb0bedad-d403-4e1c-9598-af790d3a9e55")
        public class ReportedDisplayTextField extends AbstractStringField {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayText");
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return CheckboxField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            setValue(getCheckboxField().getDisplayText());
          }
        }
      }

      @Order(20)
      @ClassId("6d18a10e-68bf-43a6-b306-fbbe860f0e09")
      public class TriStateEnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "TriState enabled";
        }

        @Override
        protected void execChangedValue() {
          getCheckboxField().setTriStateEnabled(isChecked());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setChecked(getCheckboxField().isTriStateEnabled());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(30)
      @ClassId("bb7b6f1d-7e64-47a5-a91f-a77d8ce60205")
      public class FontNameField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Font");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          updateFontAndColors();
        }
      }

      @Order(40)
      @ClassId("b9a2a2a4-1d19-416a-b2f2-55bab8688318")
      public class FontStyleField extends AbstractSmartField<Integer> {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FontStyle");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<Integer>>) FontStyleLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          updateFontAndColors();
        }
      }

      private void updateFontAndColors() {
        String name = StringUtility.emptyIfNull(getFontNameField().getValue());
        int style = NumberUtility.nvl(getFontStyleField().getValue(), 0);
        FontSpec fs = new FontSpec(name, style, 0);

        getCheckboxField().setFont(fs);
      }

      @Order(50)
      @ClassId("bc3dd00a-5406-4bfb-bf48-691efd12ef03")
      public class ButtonsBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Order(10)
        @ClassId("acd92365-3ba5-4019-9166-0683d3d3c5c0")
        public class RequestFocusButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Request focus";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getCheckboxField().requestFocus();
          }
        }
      }
    }

    @Order(50)
    @ClassId("893a06db-c7bd-40c8-9455-bc5d03c02d10")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
