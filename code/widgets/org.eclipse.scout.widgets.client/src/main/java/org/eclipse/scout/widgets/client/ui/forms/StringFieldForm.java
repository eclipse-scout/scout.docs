/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.FontStyleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.BackgroundColorField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.CharCountBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.CharCountBox.CountWhileTypingField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.CharCountBox.NumCharsField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.DisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.FontNameField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.FontSizeField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.FontStyleField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.ForegroundColorField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.HasActionSequenceBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.HasActionSequenceBox.HasActionField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.HasActionSequenceBox.ToggleHasActionButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedButtonSequenceBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedButtonSequenceBox.InputMaskedInsertTextButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedButtonSequenceBox.InputMaskedSetValueButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedButtonSequenceBox.InputMaskedSourceField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedDisplayTextField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedMaxLengthField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedOptionSequenceBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedOptionSequenceBox.InputMaskedFlagField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedOptionSequenceBox.InputMaskedUpdateDisplayTextOnModifyField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedBox.InputMaskedValueField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.MaxLengthField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.RefreshButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.SelectButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.SelectionEndField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.SelectionStartField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.TargetFieldRadioButtonGroup;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.TargetFieldRadioButtonGroup.StringFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.SelectionBox.TargetFieldRadioButtonGroup.TextInputFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.StringField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.StringInputField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.TextInputField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.UpdateDisplayTextOnModifyField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.UpperCaseField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.ValueField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.WrapTextField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.LabelCenterField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.LabelLeftField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.LabelRightField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.MandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.OnFieldLabelField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.StyledField;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.LoremIpsumButton;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm.MainBox.SampleFormatButton;

@ClassId("4b0b8f36-0f35-4bad-8fc1-017eac80d967")
public class StringFieldForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("StringField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public CountWhileTypingField getAlwaysSyncDisplayTextField() {
    return getFieldByClass(CountWhileTypingField.class);
  }

  public BackgroundColorField getBackgroundColorField() {
    return getFieldByClass(BackgroundColorField.class);
  }

  public ValueField getValueField() {
    return getFieldByClass(ValueField.class);
  }

  public WrapTextField getWrapTextField() {
    return getFieldByClass(WrapTextField.class);
  }

  public CharCountBox getCharCountBox() {
    return getFieldByClass(CharCountBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DisplayTextField getDisplayTextField() {
    return getFieldByClass(DisplayTextField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public FontNameField getFontNameField() {
    return getFieldByClass(FontNameField.class);
  }

  public FontSizeField getFontSizeField() {
    return getFieldByClass(FontSizeField.class);
  }

  public FontStyleField getFontStyleField() {
    return getFieldByClass(FontStyleField.class);
  }

  public ForegroundColorField getForegroundColorField() {
    return getFieldByClass(ForegroundColorField.class);
  }

  public InputMaskedBox getInputMaskedBox() {
    return getFieldByClass(InputMaskedBox.class);
  }

  public InputMaskedMaxLengthField getInputMaskedMaxLengthField() {
    return getFieldByClass(InputMaskedMaxLengthField.class);
  }

  public InputMaskedSourceField getInputMaskedSourceField() {
    return getFieldByClass(InputMaskedSourceField.class);
  }

  public InputMaskedSetValueButton getInputMaskedSetValueButton() {
    return getFieldByClass(InputMaskedSetValueButton.class);
  }

  public InputMaskedInsertTextButton getInputMaskedInsertTextButton() {
    return getFieldByClass(InputMaskedInsertTextButton.class);
  }

  public InputMaskedValueField getInputMaskedValueField() {
    return getFieldByClass(InputMaskedValueField.class);
  }

  public InputMaskedDisplayTextField getInputMaskedDisplayTextField() {
    return getFieldByClass(InputMaskedDisplayTextField.class);
  }

  public InputMaskedUpdateDisplayTextOnModifyField getInputMaskedUpdateDisplayTextOnModifyField() {
    return getFieldByClass(InputMaskedUpdateDisplayTextOnModifyField.class);
  }

  public InputMaskedFlagField getInputMaskedFlagField() {
    return getFieldByClass(InputMaskedFlagField.class);
  }

  public InputMaskedOptionSequenceBox getInputMaskedOptionSequenceBox() {
    return getFieldByClass(InputMaskedOptionSequenceBox.class);
  }

  public InputMaskedButtonSequenceBox getInputMaskedButtonSequenceBox() {
    return getFieldByClass(InputMaskedButtonSequenceBox.class);
  }

  public HasActionSequenceBox getHasActionSequenceBox() {
    return getFieldByClass(HasActionSequenceBox.class);
  }

  public NumCharsField getNumCharsField() {
    return getFieldByClass(NumCharsField.class);
  }

  public OnFieldLabelField getOnFieldLabelField() {
    return getFieldByClass(OnFieldLabelField.class);
  }

  public InputMaskedField getInputMaskedField() {
    return getFieldByClass(InputMaskedField.class);
  }

  public LabelCenterField getLabelCenterField() {
    return getFieldByClass(LabelCenterField.class);
  }

  public LabelLeftField getLabelLeftField() {
    return getFieldByClass(LabelLeftField.class);
  }

  public LabelRightField getLabelRightField() {
    return getFieldByClass(LabelRightField.class);
  }

  public LoremIpsumButton getLoremIpsumButton() {
    return getFieldByClass(LoremIpsumButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public MaxLengthField getMaxLengthField() {
    return getFieldByClass(MaxLengthField.class);
  }

  public SampleFormatButton getSampleFormatButton() {
    return getFieldByClass(SampleFormatButton.class);
  }

  public StringField getStringField() {
    return getFieldByClass(StringField.class);
  }

  public StringInputField getStringInputField() {
    return getFieldByClass(StringInputField.class);
  }

  public StyledField getStyledField() {
    return getFieldByClass(StyledField.class);
  }

  public TextInputField getTextInputField() {
    return getFieldByClass(TextInputField.class);
  }

  public UpdateDisplayTextOnModifyField getUpdateDisplayTextOnModifyField() {
    return getFieldByClass(UpdateDisplayTextOnModifyField.class);
  }

  public UpperCaseField getUpperCaseField() {
    return getFieldByClass(UpperCaseField.class);
  }

  public HasActionField getHasActionField() {
    return getFieldByClass(HasActionField.class);
  }

  public ToggleHasActionButton getToggleHasActionButton() {
    return getFieldByClass(ToggleHasActionButton.class);
  }

  public SelectionBox getSelectionBox() {
    return getFieldByClass(SelectionBox.class);
  }

  public TargetFieldRadioButtonGroup getTargetFieldRadioButtonGroup() {
    return getFieldByClass(TargetFieldRadioButtonGroup.class);
  }

  public TextInputFieldButton getTextInputFieldButton() {
    return getFieldByClass(TextInputFieldButton.class);
  }

  public StringFieldButton getStringFieldButton() {
    return getFieldByClass(StringFieldButton.class);
  }

  public SelectionStartField getSelectionStartField() {
    return getFieldByClass(SelectionStartField.class);
  }

  public SelectionEndField getSelectionEndField() {
    return getFieldByClass(SelectionEndField.class);
  }

  public RefreshButton getRefreshButton() {
    return getFieldByClass(RefreshButton.class);
  }

  public SelectButton getSelectButton() {
    return getFieldByClass(SelectButton.class);
  }

  @Order(10)
  @ClassId("5e596d7d-27a9-4f05-a68b-c9698fb2276c")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("60c55dac-a6aa-4786-888e-88527429aa5c")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("d30af044-5600-418b-bcd0-ac65eae4dc18")
      public class DefaultField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "Default";
        }
      }

      @Order(20)
      @ClassId("e86f5e64-487c-47d4-92ab-bcb6f9a92550")
      public class MandatoryField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          if (StringUtility.isNullOrEmpty(rawValue)) {
            throw new VetoException("Field content must not be empty");
          }
          return rawValue;
        }
      }

      @Order(25)
      @ClassId("aab1dce0-9ba4-4556-ba5f-2bd5f5a72484")
      public class InsertText1Button extends AbstractButton {
        @Override
        protected void execInitField() {
          super.execInitField();
          setLabel(TEXTS.get("CallInsertTextSourceXTargetY", getDefaultField().getLabel(), getMandatoryField().getLabel()));
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          getMandatoryField().insertText(getDefaultField().getValue());
        }
      }

      @Order(30)
      @ClassId("2535ec2b-f551-4823-b73d-05c7a32cd196")
      public class DisabledField extends AbstractStringField {

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
          setValue("Text in disabled Field");
        }
      }

      @Order(40)
      @ClassId("5c0eec14-efda-4e5c-8859-2de528d54b47")
      public class StyledField extends AbstractStringField {

        @Override
        protected String getConfiguredBackgroundColor() {
          return "FDFFAA";
        }

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected String getConfiguredForegroundColor() {
          return "0080C0";
        }

        @Override
        protected String getConfiguredFormat() {
          return "j";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }
      }

      @Order(50)
      @ClassId("635a8b99-86c3-4b74-bc95-26e134a8758d")
      public class OnFieldLabelField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("OnFieldLabel");
        }

        @Override
        protected byte getConfiguredLabelHorizontalAlignment() {
          return LABEL_HORIZONTAL_ALIGNMENT_LEFT;
        }

        @Override
        protected byte getConfiguredLabelPosition() {
          return LABEL_POSITION_ON_FIELD;
        }
      }

      @Order(60)
      @ClassId("3541e817-f848-4d60-be80-33fdb9063cb3")
      public class LabelLeftField extends AbstractStringField {

        @Override
        protected byte getConfiguredLabelHorizontalAlignment() {
          return LABEL_HORIZONTAL_ALIGNMENT_LEFT;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LabelLeft");
        }
      }

      @Order(70)
      @ClassId("91725d63-f697-4e12-9d95-e749c61fb14f")
      public class LabelCenterField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LabelCenter");
        }

        @Override
        protected byte getConfiguredLabelHorizontalAlignment() {
          return LABEL_HORIZONTAL_ALIGNMENT_CENTER;
        }
      }

      @Order(80)
      @ClassId("2b175f14-07be-47a4-8bf8-97e3a39d59ce")
      public class LabelRightField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LabelRight");
        }

        @Override
        protected byte getConfiguredLabelHorizontalAlignment() {
          return LABEL_HORIZONTAL_ALIGNMENT_RIGHT;
        }
      }
    }

    @Order(20)
    @ClassId("1297c75c-60cd-40cc-a101-0490edeef4c7")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(60)
      @ClassId("ae59ef0a-2a62-40b2-8955-6d44f4645fb0")
      public class TextInputField extends AbstractStringField {

        @Override
        protected void execChangedDisplayText() {
          String displayText = getDisplayText();
          int length = displayText != null ? displayText.length() : 0;
          getNumCharsField().setValue(length);
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MultilineText");
        }

        @Override
        protected boolean getConfiguredSelectionTrackingEnabled() {
          return true;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }
      }

      @Order(65)
      @ClassId("587a6f8e-4cbd-4151-9f2f-ea4de832ccdf")
      public class InsertText2Button extends AbstractButton {
        @Override
        protected void execInitField() {
          setLabel(TEXTS.get("CallInsertTextSourceXTargetY", getDefaultField().getLabel(), getTextInputField().getLabel()));
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          getTextInputField().insertText(getDefaultField().getValue());
        }
      }

      private void updateFontAndColors() {
        String name = StringUtility.emptyIfNull(getFontNameField().getValue());
        int style = NumberUtility.nvl(getFontStyleField().getValue(), 0);
        int size = NumberUtility.nvl(getFontSizeField().getValue(), 0);
        FontSpec fs = new FontSpec(name, style, size);

        getTextInputField().setFont(fs);

        String foreground = ObjectUtility.nvl(getForegroundColorField().getValue(), "000000");
        String background = ObjectUtility.nvl(getBackgroundColorField().getValue(), "ffffff");

        getTextInputField().setForegroundColor(foreground);
        getTextInputField().setBackgroundColor(background);
      }

      @Order(61)
      @ClassId("a46ed108-5b5b-49c7-a9a7-c61c1be4c370")
      public class CharCountBox extends AbstractSequenceBox {

        @Order(1000)
        @ClassId("26788917-4f37-45ed-93c8-542ae01c6486")
        public class NumCharsField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("NumChars");
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }
        }

        @Order(2000)
        @ClassId("10dfc44c-31f3-4215-9fcb-b51779f1d627")
        public class CountWhileTypingField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CountWhileTyping");
          }

          @Override
          protected void execInitField() {
            setValue(getTextInputField().isUpdateDisplayTextOnModify());
          }

          @Override
          protected void execChangedValue() {
            getTextInputField().setUpdateDisplayTextOnModify(isChecked());
          }
        }
      }

      @Order(70)
      @ClassId("f0620fda-2ea1-4592-adfc-c6f1ce758c6b")
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
      @ClassId("ea7a910d-efe5-43c3-bb68-ca6b9b826cec")
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

      @Order(90)
      @ClassId("3b49c919-0c97-4a07-8f40-e0c04e4ef6f5")
      public class FontSizeField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FontSize");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Integer getConfiguredMaxValue() {
          return 100;
        }

        @Override
        protected Integer getConfiguredMinValue() {
          return 1;
        }

        @Override
        protected void execChangedValue() {
          updateFontAndColors();
        }
      }

      @Order(100)
      @ClassId("55c84c39-425e-450b-982e-649d041233e3")
      public class ForegroundColorField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ForegroundColor");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          updateFontAndColors();
        }

        @Override
        protected String execValidateValue(String rawValue) {
          clearErrorStatus();

          if (StringUtility.isNullOrEmpty(rawValue)) {
            return rawValue;
          }

          if (!rawValue.matches("[0-9A-Fa-f]{6}")) {
            this.addErrorStatus("\"" + rawValue + "\" " + TEXTS.get("NoColor"));
          }

          return rawValue;
        }
      }

      @Order(110)
      @ClassId("c437e9a1-5c65-4473-b7d1-a8010f47239e")
      public class BackgroundColorField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BackgroundColor");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          updateFontAndColors();
        }

        @Override
        protected String execValidateValue(String rawValue) {
          clearErrorStatus();

          if (StringUtility.isNullOrEmpty(rawValue)) {
            return rawValue;
          }

          if (!rawValue.matches("[0-9A-Fa-f]{6}")) {
            this.addErrorStatus("\"" + rawValue + "\" " + TEXTS.get("NoColor"));
          }

          return rawValue;
        }
      }

      @Order(120)
      @ClassId("d950c669-5c05-41d3-9bf7-5a9aae433a67")
      public class WrapTextField extends AbstractBooleanField {

        // TODO [7.0] bsh: BUG, does not react on getConfiguredLabelFont, bug???

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("WrapText");
        }

        @Override
        protected void execChangedValue() {
          getTextInputField().setWrapText(getValue());
        }
      }

      @Order(125)
      @ClassId("31b952aa-ca39-4a08-919a-f710ceb5ec41")
      public class SpellCheckEnabledField extends AbstractBooleanField {

        // TODO [7.0] bsh: BUG, does not react on getConfiguredLabelFont, bug???

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SpellCheckEnabled");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("SpellCheckEnabledTooltip");
        }

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getTextInputField().setSpellCheckEnabled(getValue());
        }
      }

      @Order(127)
      @ClassId("3d1f8e4d-137f-4f27-9549-1b04b4d6589f")
      public class TrimTextField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return "Trim text";
        }

        @Override
        protected void execInitField() {
          setValue(getTextInputField().isTrimText());
        }

        @Override
        protected void execChangedValue() {
          getTextInputField().setTrimText(getValue());
        }
      }

      @Order(128)
      @ClassId("5deb5bd3-6477-4ce7-8c0d-16d86a742fc7")
      public class EnableTextField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Multiline Text enabled";
        }

        @Override
        protected void execInitField() {
          setValue(true);
        }

        @Override
        protected void execChangedValue() {
          getTextInputField().setEnabled(getValue(), true, true);
        }

      }

      @Order(130)
      @ClassId("63133786-3d12-4c5c-89e4-d05c090f35d1")
      public class StringInputField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
        }
      }

      @Order(140)
      @ClassId("6cee79a0-c6f8-4b1f-b659-512dc0b1aa52")
      public class UpperCaseField extends AbstractStringField {

        @Override
        protected boolean getConfiguredFormatUpper() {
          return true;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpperCase");
        }
      }

      @Order(154)
      @ClassId("5947fc3f-0e73-4620-b7bf-be1714bf59e9")
      public class HasActionSequenceBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
        @ClassId("420b9c0a-82e9-4569-bd65-4f48d8f49e7c")
        public class HasActionField extends AbstractStringField {

          @Override
          protected boolean getConfiguredHasAction() {
            return true;
          }

          @Override
          protected void execAction() {
            super.execAction();
            MessageBoxes.createOk().withHeader(getValue()).show();
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("HasAction");
          }
        }

        @Order(20)
        @ClassId("4a5532c2-8ef1-4059-9176-fffc2b7a11da")
        public class ToggleHasActionButton extends AbstractButton {

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_LINK;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ToggleHasActionProperty");
          }

          @Override
          protected void execClickAction() {
            getHasActionField().setHasAction(!getHasActionField().isHasAction());
          }
        }
      }

      @Order(160)
      @ClassId("6a31d7dc-80d5-4bda-bf76-d4ccbbce26bf")
      public class MaxLengthField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MaxLength");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          int maxlen = NumberUtility.nvl(getValue(), 4000);
          getStringInputField().setMaxLength(maxlen);
          getUpperCaseField().setMaxLength(maxlen);
        }
      }

      @Order(2000)
      @ClassId("cf20bd3d-4454-410e-873d-85415074d8e9")
      public class StringField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
        }

        @Override
        protected boolean getConfiguredSelectionTrackingEnabled() {
          return true;
        }

        @Override
        protected void execChangedValue() {
          getValueField().setValue(getValue());
        }

        @Override
        protected void execChangedDisplayText() {
          getDisplayTextField().setValue(getDisplayText());
          if ("disable".equals(getDisplayText())) {
            getUpdateDisplayTextOnModifyField().setValue(false);
          }
        }
      }

      @Order(3000)
      @ClassId("bd0b3bf0-86b4-4e29-8ef9-f95b686c261b")
      public class ValueField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Value");
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(4000)
      @ClassId("1d050810-1fb5-4ff8-a2ab-db8ed9421a6e")
      public class DisplayTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DisplayText");
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(5000)
      @ClassId("4f88f37e-84b5-4606-a69c-8c8c2d2b6c7c")
      public class UpdateDisplayTextOnModifyField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpdateDisplayTextOnModify");
        }

        @Override
        protected void execChangedValue() {
          getStringField().setUpdateDisplayTextOnModify(getValue());
        }
      }

      @Order(5500)
      @ClassId("79ae8c46-c66b-4af0-83c7-790a9ec23bc7")
      public class SelectionBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Selection");
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        public AbstractStringField getTargetField() {
          if (Boolean.TRUE.equals(getTargetFieldRadioButtonGroup().getValue())) {
            return getTextInputField();
          }
          else {
            return getStringField();
          }
        }

        @Order(10)
        @ClassId("bd2014bd-824b-4c97-b8a4-30ac8848a6ca")
        public class TargetFieldRadioButtonGroup extends AbstractRadioButtonGroup<Boolean> {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Order(10)
          @ClassId("ae91e4f4-49e0-4392-b6f8-be8943439f84")
          public class TextInputFieldButton extends AbstractRadioButton<Boolean> {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              super.execInitField();
              setLabel(getTextInputField().getLabel());
            }

            @Override
            protected Boolean getConfiguredRadioValue() {
              return Boolean.TRUE; // multi line: true
            }
          }

          @Order(20)
          @ClassId("28f937cb-3ffb-4d22-9ee2-942f2262a41b")
          public class StringFieldButton extends AbstractRadioButton<Boolean> {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              super.execInitField();
              setLabel(getStringField().getLabel());
              getTargetFieldRadioButtonGroup().setValue(Boolean.FALSE);
            }

            @Override
            protected Boolean getConfiguredRadioValue() {
              return Boolean.FALSE; // multi line: false
            }
          }
        }

        @Order(20)
        @ClassId("5462e915-00d3-48c4-93fa-56ec3fb1d59f")
        public class SelectionStartField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SelectionStart");
          }
        }

        @Order(30)
        @ClassId("a5faa2c9-9860-4dac-88db-817b971f7258")
        public class SelectionEndField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SelectionEnd");
          }
        }

        @Order(40)
        @ClassId("28abdffa-ba3c-420f-9b06-d651341dd3ef")
        public class RefreshButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Refresh");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getSelectionStartField().setValue(getTargetField().getSelectionStart());
            getSelectionEndField().setValue(getTargetField().getSelectionEnd());
          }
        }

        @Order(50)
        @ClassId("c6cf1946-2897-4f6a-b54c-0a9b43a40653")
        public class SelectButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Select");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getTargetField().requestFocus(); // Otherwise selection won't be visible
            getTargetField().select(NumberUtility.nvl(getSelectionStartField().getValue(), 0), NumberUtility.nvl(getSelectionEndField().getValue(), 0));
          }
        }
      }

      @Order(5600)
      @ClassId("2094c44b-94d4-4df9-9efb-eaf4c8d4c986")
      public class InputMaskedBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return "Input masked";
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
        protected int getConfiguredGridH() {
          return 8;
        }

        @Order(10)
        @ClassId("a6c204e1-7cbd-46c4-86c6-4d9f5941aaba")
        public class InputMaskedField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return "Input masked";
          }

          @Override
          protected boolean getConfiguredInputMasked() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            getInputMaskedValueField().setValue(getValue());
          }

          @Override
          protected void execChangedDisplayText() {
            getInputMaskedDisplayTextField().setValue(getDisplayText());
          }
        }

        @Order(1000)
        @ClassId("1ea01cd5-6597-4b56-b9e5-54f838ac3bb4")
        public class InputMaskedOptionSequenceBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(10)
          @ClassId("3a428d96-b17b-4ad2-b829-d56c60cf37c5")
          public class InputMaskedFlagField extends AbstractBooleanField {
            @Override
            protected String getConfiguredLabel() {
              return "Toggle masked";
            }

            @Override
            protected boolean getConfiguredGridUseUiWidth() {
              return true;
            }

            @Override
            protected void execInitField() {
              setValue(true);
            }

            @Override
            protected void execChangedValue() {
              getInputMaskedField().setInputMasked(BooleanUtility.nvl(getValue()));
            }
          }

          @Order(20)
          @ClassId("b1f8723c-85b1-42fe-9c91-952c097e03da")
          public class InputMaskedUpdateDisplayTextOnModifyField extends AbstractBooleanField {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("UpdateDisplayTextOnModify");
            }

            @Override
            protected boolean getConfiguredGridUseUiWidth() {
              return true;
            }

            @Override
            protected void execChangedValue() {
              getInputMaskedField().setUpdateDisplayTextOnModify(getValue());
            }
          }
        }

        @Order(2000)
        @ClassId("3922f893-4464-4322-bca1-7af17f38e6f5")
        public class InputMaskedMaxLengthField extends AbstractIntegerField {
          @Override
          protected String getConfiguredLabel() {
            return "Max length";
          }

          @Override
          protected Integer getConfiguredMinValue() {
            return 0;
          }

          @Override
          protected Integer getConfiguredMaxValue() {
            return 4000;
          }

          @Override
          protected void execChangedValue() {
            int maxlen = NumberUtility.nvl(getValue(), 4000);
            getInputMaskedField().setMaxLength(maxlen);
          }
        }

        @Order(2500)
        @ClassId("d326817d-5237-409d-934e-3870cb0fb4a5")
        public class InputMaskedButtonSequenceBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(3000)
          @ClassId("65e6c35e-4a4e-435f-b6dd-eb6d709b989a")
          public class InputMaskedSourceField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() {
              return "Source";
            }
          }

          @Order(4000)
          @ClassId("534d02eb-b861-4b69-9907-900a415b70ce")
          public class InputMaskedSetValueButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Set value";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected int getConfiguredDisplayStyle() {
              return DISPLAY_STYLE_LINK;
            }

            @Override
            protected void execClickAction() {
              getInputMaskedField().setValue(getInputMaskedSourceField().getValue());
            }
          }

          @Order(5000)
          @ClassId("1f18741c-74f4-46d4-9af5-fa553b4044fb")
          public class InputMaskedInsertTextButton extends AbstractButton {
            @Override
            protected String getConfiguredLabel() {
              return "Insert text";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected int getConfiguredDisplayStyle() {
              return DISPLAY_STYLE_LINK;
            }

            @Override
            protected void execClickAction() {
              getInputMaskedField().insertText(getInputMaskedSourceField().getValue());
            }
          }
        }

        @Order(6000)
        @ClassId("2f9c3276-9f87-4e98-b892-49f77d1ea85c")
        public class InputMaskedValueField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return "Value";
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }
        }

        @Order(7000)
        @ClassId("bf1d46fc-587c-4ac4-8085-ebc37f0319ed")
        public class InputMaskedDisplayTextField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return "Display text";
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }
        }
      }
    }

    @Order(30)
    @ClassId("e8807bf6-7fd0-4031-9111-81b276bbfaa0")
    public class LoremIpsumButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getTextInputField().setValue(TEXTS.get("LoremIpsum") + "\n" + TEXTS.get("Lorem"));
        getStringInputField().setValue(TEXTS.get("HelloWorld"));
        getUpperCaseField().setValue(TEXTS.get("HelloWorld"));
        getInputMaskedField().setValue(TEXTS.get("HelloWorld"));
      }
    }

    @Order(40)
    @ClassId("e7eb0d63-5359-45cc-b158-fac346d49c89")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(50)
    @ClassId("6006dda1-6e6f-49a3-bc19-ec2629353a10")
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() {
        getFontNameField().setValue("Courier");
        getFontSizeField().setValue(14);
        getForegroundColorField().setValue("000088");
        getBackgroundColorField().setValue("DDDDFF");
        getWrapTextField().setValue(true);

        getMaxLengthField().setValue(7);
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
