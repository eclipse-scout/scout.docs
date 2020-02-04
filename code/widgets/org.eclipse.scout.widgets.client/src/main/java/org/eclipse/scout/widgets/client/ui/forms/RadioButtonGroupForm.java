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

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.GridData;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.IRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.EventTypeLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.FontStyleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.FontNameField;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.FontStyleField;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonGroup;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonGroup.No1Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonGroup.No2Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonGroup.No3Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonGroup.No4Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonGroup.No5Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonValuesBox.ValueButton1Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonValuesBox.ValueButton2Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonValuesBox.ValueButton3Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonValuesBox.ValueButton4Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.RadioButtonValuesBox.ValueButton5Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ConfigurationBox.ValueField;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ExamplesBox.DefaultGroup;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ExamplesBox.DisabledGroup;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.SampleContentButton;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType.ExternalCode;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType.PublicCode;

@ClassId("6bb3651a-87ea-4529-8096-4b8c2c8c4920")
public class RadioButtonGroupForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("RadioButtonGroup");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public No1Button getNo1Button() {
    return getFieldByClass(No1Button.class);
  }

  public No2Button getNo2Button() {
    return getFieldByClass(No2Button.class);
  }

  public No3Button getNo3Button() {
    return getFieldByClass(No3Button.class);
  }

  public No4Button getNo4Button() {
    return getFieldByClass(No4Button.class);
  }

  public No5Button getNo5Button() {
    return getFieldByClass(No5Button.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultGroup getDefaultGroup() {
    return getFieldByClass(DefaultGroup.class);
  }

  public DisabledGroup getDisabledGroup() {
    return getFieldByClass(DisabledGroup.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ValueField getValueField() {
    return getFieldByClass(ValueField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public RadioButtonGroup getRadioButtonGroup() {
    return getFieldByClass(RadioButtonGroup.class);
  }

  public FontNameField getFontNameField() {
    return getFieldByClass(FontNameField.class);
  }

  public FontStyleField getFontStyleField() {
    return getFieldByClass(FontStyleField.class);
  }

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  public ValueButton1Field getValueButton1Field() {
    return getFieldByClass(ValueButton1Field.class);
  }

  public ValueButton2Field getValueButton2Field() {
    return getFieldByClass(ValueButton2Field.class);
  }

  public ValueButton3Field getValueButton3Field() {
    return getFieldByClass(ValueButton3Field.class);
  }

  public ValueButton4Field getValueButton4Field() {
    return getFieldByClass(ValueButton4Field.class);
  }

  public ValueButton5Field getValueButton5Field() {
    return getFieldByClass(ValueButton5Field.class);
  }

  @Order(10)
  @ClassId("b2f5de0c-ef89-4667-93ce-8a917853566e")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 2;
    }

    @Order(10)
    @ClassId("f67b2cc8-5417-4e72-898f-48a20bc36801")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("57e0e909-b9d6-4795-b32c-b74029c69c5e")
      public class DefaultGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return EventTypeCodeType.class;
        }

        @Override
        protected void execInitField() {
          setValue(ExternalCode.ID);
        }
      }

      @Order(20)
      @ClassId("d910bccd-0ba0-4de9-9403-d08cf540f49b")
      public class DisabledGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return EventTypeLookupCall.class;
        }

        @Override
        protected void execInitField() {
          getButtonFor(PublicCode.ID).setSelected(true);
          for (IRadioButton<Long> button : getButtons()) {
            button.setEnabled(false);
          }
        }
      }

      @Order(30)
      @ClassId("30161d61-f03f-4393-9097-644c41553d71")
      public class StyledGroupBox extends AbstractRadioButtonGroup<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Order(10)
        @ClassId("00db2ed0-0fa1-4417-b75a-ee05fc65cdd4")
        public class RotateLeftButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredIconId() {
            return Icons.RotateLeftBold;
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return -1L;
          }
        }

        @Order(20)
        @ClassId("9638ced8-be48-4ff8-91e2-5bc1891ef0b4")
        public class RotateRightButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredIconId() {
            return Icons.RotateRightBold;
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return -2L;
          }
        }

        @Order(30)
        @ClassId("53b46292-d389-40f4-8b9e-2e2eba02b2b4")
        public class AlarmClockButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredIconId() {
            return Icons.Alarmclock;
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return -3L;
          }
        }
      }
    }

    @Order(20)
    @ClassId("feaa321b-c363-4c31-8f4f-8b5492d828f5")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(10)
      @ClassId("e2544ec4-e7af-4650-bc82-bcd532006fe3")
      public class RadioButtonGroup extends AbstractRadioButtonGroup<String> {

        private boolean m_showMessageBox = true;

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RadioButtonGroup");
        }

        @Override
        protected void execInitField() {
          getNo1Button().setSelected(true);
        }

        @Order(10)
        @ClassId("83bfb9fa-2e36-439b-8c88-a85ca475480f")
        public class No1Button extends AbstractRadioButton<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Button 1";
          }

          @Override
          protected String getConfiguredKeyStroke() {
            return "ctrl-1";
          }
        }

        @Order(20)
        @ClassId("c894a80b-9a5a-44f9-92ef-320631558da0")
        public class No2Button extends AbstractRadioButton<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Button 2";
          }

          @Override
          protected String getConfiguredKeyStroke() {
            return "ctrl-2";
          }
        }

        @Order(30)
        @ClassId("bf182307-7795-40bf-864c-9199bd9e50e4")
        public class No3Button extends AbstractRadioButton<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Button 3";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "This radio button displays a message box when selected. This can be turned off using the menu on the right.";
          }

          @Override
          protected void execClickAction() {
            if (m_showMessageBox) {
              MessageBoxes.createOk().withHeader(TEXTS.get("RadioButtonSelected", getLabel())).withBody(TEXTS.get("RadioButtonExecClickAction")).show();
            }
          }
        }

        @Order(40)
        @ClassId("8b5c4ce6-ba88-4025-8ba5-2ff78a02250d")
        public class No4Button extends AbstractRadioButton<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Button 4";
          }
        }

        @Order(50)
        @ClassId("045e57ef-bae5-4e0c-a18c-bf6b2012850b")
        public class No5Button extends AbstractRadioButton<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Button 5";
          }
        }

        @Order(10)
        @ClassId("87ca8e03-a549-47ea-9f21-7407a4eaa342")
        public class DeselectMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Clear selection";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(
                ValueFieldMenuType.Null,
                ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execAction() {
            RadioButtonGroup.this.setValue(null);
          }
        }

        @Order(20)
        @ClassId("972d6b40-b6d8-480e-9e10-bb732d5e4113")
        public class PreventMessageBoxMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Don't show message box on button 3";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(
                ValueFieldMenuType.Null,
                ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execAction() {
            m_showMessageBox = !m_showMessageBox;
            setText(m_showMessageBox ? "Don't show message box on button 3" : "Show message box on button 3");
          }
        }
      }

      @Order(20)
      @ClassId("536a353d-51b4-4a57-b106-1260171c6ae9")
      public class RadioButtonValuesBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return "Radio button values";
        }

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected void execInitField() {
          getValueButton1Field().setValue("01");
          getValueButton2Field().setValue("02");
          getValueButton3Field().setValue("03");
          getValueButton4Field().setValue("04");
          getValueButton5Field().setValue("05");
        }

        @Order(10)
        @ClassId("e2303553-80e1-4007-bf3a-7c0cb0ab45e9")
        public class ValueButton1Field extends AbstractStringField {

          @Override
          protected String getConfiguredTooltipText() {
            return "Value for button 1";
          }

          @Override
          protected void execChangedValue() {
            getNo1Button().setRadioValue(getValue());
            if (getNo1Button().isSelected()) {
              getRadioButtonGroup().selectKey(getValue());
            }
          }
        }

        @Order(20)
        @ClassId("45aa0fc2-dabb-4e82-9621-188f3a8452aa")
        public class ValueButton2Field extends AbstractStringField {

          @Override
          protected String getConfiguredTooltipText() {
            return "Value for button 2";
          }

          @Override
          protected void execChangedValue() {
            getNo2Button().setRadioValue(getValue());
            if (getNo2Button().isSelected()) {
              getRadioButtonGroup().selectKey(getValue());
            }
          }
        }

        @Order(30)
        @ClassId("f044497b-b1da-4544-9f2e-63eae08bae00")
        public class ValueButton3Field extends AbstractStringField {

          @Override
          protected String getConfiguredTooltipText() {
            return "Value for button 3";
          }

          @Override
          protected void execChangedValue() {
            getNo3Button().setRadioValue(getValue());
            if (getNo3Button().isSelected()) {
              getRadioButtonGroup().selectKey(getValue());
            }
          }
        }

        @Order(40)
        @ClassId("f4bb6716-330c-4dd4-9b8a-03f1fca9cc66")
        public class ValueButton4Field extends AbstractStringField {

          @Override
          protected String getConfiguredTooltipText() {
            return "Value for button 4";
          }

          @Override
          protected void execChangedValue() {
            getNo4Button().setRadioValue(getValue());
            if (getNo4Button().isSelected()) {
              getRadioButtonGroup().selectKey(getValue());
            }
          }
        }

        @Order(50)
        @ClassId("29e62507-b4e1-45fe-9cb2-90dc248a510b")
        public class ValueButton5Field extends AbstractStringField {

          @Override
          protected String getConfiguredTooltipText() {
            return "Value for button 5";
          }

          @Override
          protected void execChangedValue() {
            getNo5Button().setRadioValue(getValue());
            if (getNo5Button().isSelected()) {
              getRadioButtonGroup().selectKey(getValue());
            }
          }
        }
      }

      @Order(30)
      @ClassId("6d022879-122b-4c32-8797-564f6f9fe8b3")
      public class ValueField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Selected value";
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return RadioButtonGroup.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          setValue(getRadioButtonGroup().getValue());
        }
      }

      @Order(40)
      @ClassId("3294640a-9fbf-4fe6-8fa4-7a1651373a16")
      public class RadioButtonGroupHeightField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Group Height";
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execInitField() {
          setValue(1);
        }

        @Override
        protected Integer execValidateValue(Integer rawValue) {
          if (rawValue == null || rawValue < 1) {
            return 1;
          }
          return super.execValidateValue(rawValue);
        }

        @Override
        protected void execChangedValue() {
          GridData gdh = getRadioButtonGroup().getGridDataHints();
          gdh.h = getValue();
          getRadioButtonGroup().setGridDataHints(gdh);
          getConfigurationBox().rebuildFieldGrid();
          getRadioButtonGroup().rebuildFieldGrid();
        }
      }

      @Order(50)
      @ClassId("ba8d7c65-1413-4b36-a2da-a10621c95bcc")
      public class LabelField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Label");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execInitField() {
          setValue(getRadioButtonGroup().getButtons().get(0).getLabel());
        }

        @Override
        protected void execChangedValue() {
          getRadioButtonGroup().getButtons().get(0).setLabel(getValue());
        }
      }

      @Order(60)
      @ClassId("4a7c4797-086a-415d-9408-1ed9d2a44488")
      public class IconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IconId");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(getRadioButtonGroup().getButtons().get(0).getIconId());
        }

        @Override
        protected void execChangedValue() {
          getRadioButtonGroup().getButtons().get(0).setIconId(getValue());
        }
      }

      @Order(70)
      @ClassId("fcd46305-68ad-4f9f-8400-a667aadf4ace")
      public class FontNameField extends AbstractStringField {

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

      @Order(80)
      @ClassId("7fcac7de-f0a4-40df-8a7d-e938bb9bdff7")
      public class FontStyleField extends AbstractSmartField<Integer> {

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
        getRadioButtonGroup().getButtons().get(0).setFont(fs);
      }

      @Order(90)
      @ClassId("cca80d4d-635c-44af-bf2c-b9df045dabf5")
      public class EnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected void execChangedValue() {
          getRadioButtonGroup().getButtons().get(0).setEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getRadioButtonGroup().getButtons().get(0).isEnabled());
        }
      }
    }

    @Order(30)
    @ClassId("768a5694-8966-45f2-809d-b7fc1e4ef025")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(40)
    @ClassId("99e4266e-4933-4cad-be58-4bb0dc0873e4")
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getValueButton1Field().setValue("One");
        getValueButton2Field().setValue("#");
        getValueButton3Field().setValue("42");
        getValueButton4Field().setValue("BTN_4");
        getValueButton5Field().setValue("BTN_5");
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
