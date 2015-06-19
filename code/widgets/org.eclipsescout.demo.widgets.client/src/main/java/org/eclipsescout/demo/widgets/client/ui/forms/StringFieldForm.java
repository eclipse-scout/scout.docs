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

import org.eclipse.scout.commons.NumberUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.FontStyleLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.BackgroundColorField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.CharCountBox;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.CharCountBox.CountWhileTypingField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.CharCountBox.NumCharsField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.DisplayTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.FontNameField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.FontSizeField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.FontStyleField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.ForegroundColorField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.InputMaskedField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.MaxLengthField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.Placeholder1Field;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.PlaceholderField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.StringField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.StringInputField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.TextInputField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.UpdateDisplayTextOnModifyField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.UpperCaseField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.ValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ConfigurationBox.WrapTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.DisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.LabelCenterField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.LabelLeftField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.LabelRightField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.MandatoryField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.OnFieldLabelField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.ExamplesBox.StyledField;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.LoremIpsumButton;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm.MainBox.SampleFormatButton;

public class StringFieldForm extends AbstractForm implements IPageForm {

  public StringFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("StringField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
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

  /**
   * @return the FontField
   */
  public FontNameField getFontNameField() {
    return getFieldByClass(FontNameField.class);
  }

  /**
   * @return the FontSizeField
   */
  public FontSizeField getFontSizeField() {
    return getFieldByClass(FontSizeField.class);
  }

  /**
   * @return the FontStyleField
   */
  public FontStyleField getFontStyleField() {
    return getFieldByClass(FontStyleField.class);
  }

  /**
   * @return the ForegroundColorField
   */
  public ForegroundColorField getForegroundColorField() {
    return getFieldByClass(ForegroundColorField.class);
  }

  public NumCharsField getNumCharsField() {
    return getFieldByClass(NumCharsField.class);
  }

  /**
   * @return the OnFieldLabelField
   */
  public OnFieldLabelField getOnFieldLabelField() {
    return getFieldByClass(OnFieldLabelField.class);
  }

  public InputMaskedField getInputMaskedField() {
    return getFieldByClass(InputMaskedField.class);
  }

  /**
   * @return the LabelCenterField
   */
  public LabelCenterField getLabelCenterField() {
    return getFieldByClass(LabelCenterField.class);
  }

  /**
   * @return the LabelLeftField
   */
  public LabelLeftField getLabelLeftField() {
    return getFieldByClass(LabelLeftField.class);
  }

  /**
   * @return the LabelRightField
   */
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

  /**
   * @return the MaxLengthField
   */
  public MaxLengthField getMaxLengthField() {
    return getFieldByClass(MaxLengthField.class);
  }

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

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class DefaultField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "&Default";
        }
      }

      @Order(20.0)
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
        protected String execValidateValue(String rawValue) throws ProcessingException {
          if (StringUtility.isNullOrEmpty(rawValue)) {
            throw new VetoException("Field content must not be empty");
          }
          return rawValue;
        }
      }

      @Order(25.0)
      public class InsertText1Button extends AbstractButton {
        @Override
        protected void execInitField() throws ProcessingException {
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
        protected void execClickAction() throws ProcessingException {
          getMandatoryField().insertText(getDefaultField().getValue());
        }
      }

      @Order(30.0)
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
        protected void execInitField() throws ProcessingException {
          setValue("Text in disabled Field");
        }
      }

      @Order(40.0)
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

      @Order(50.0)
      public class OnFieldLabelField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("OnFieldLabel");
        }

        @Override
        protected int getConfiguredLabelHorizontalAlignment() {
          return -1;
        }

        @Override
        protected int getConfiguredLabelPosition() {
          return LABEL_POSITION_ON_FIELD;
        }
      }

      @Order(60.0)
      public class LabelLeftField extends AbstractStringField {

        @Override
        protected int getConfiguredLabelHorizontalAlignment() {
          return -1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LabelLeft");
        }
      }

      @Order(70.0)
      public class LabelCenterField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LabelCenter");
        }

        @Override
        protected int getConfiguredLabelHorizontalAlignment() {
          return 0;
        }
      }

      @Order(80.0)
      public class LabelRightField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LabelRight");
        }

        @Override
        protected int getConfiguredLabelHorizontalAlignment() {
          return 1;
        }
      }
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(60.0)
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
        protected boolean getConfiguredMultilineText() {
          return true;
        }

      }

      @Order(65.0)
      public class InsertText2Button extends AbstractButton {
        @Override
        protected void execInitField() throws ProcessingException {
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
        protected void execClickAction() throws ProcessingException {
          getTextInputField().insertText(getDefaultField().getValue());
        }
      }

      private void updateFontAndColors() {
        String name = StringUtility.nvl(getFontNameField().getValue(), "");
        int style = NumberUtility.nvl(getFontStyleField().getValue(), 0);
        int size = NumberUtility.nvl(getFontSizeField().getValue(), 0);
        FontSpec fs = new FontSpec(name, style, size);

        getTextInputField().setFont(fs);

        String foreground = StringUtility.nvl(getForegroundColorField().getValue(), "000000");
        String background = StringUtility.nvl(getBackgroundColorField().getValue(), "ffffff");

        getTextInputField().setForegroundColor(foreground);
        getTextInputField().setBackgroundColor(background);
      }

      @Order(61.0)
      public class CharCountBox extends AbstractSequenceBox {

        @Order(1000.0)
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

        @Order(2000.0)
        public class CountWhileTypingField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CountWhileTyping");
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTextInputField().isUpdateDisplayTextOnModify());
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTextInputField().setUpdateDisplayTextOnModify(isChecked());
          }
        }
      }

      @Order(70.0)
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
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }
      }

      @Order(80.0)
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
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }
      }

      @Order(90.0)
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
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }
      }

      @Order(100.0)
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
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }

        @Override
        protected String execValidateValue(String rawValue) throws ProcessingException {
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

      @Order(110.0)
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
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }

        @Override
        protected String execValidateValue(String rawValue) throws ProcessingException {
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

      @Order(120.0)
      public class WrapTextField extends AbstractCheckBox {

        // TODO: [BUG] does not react on getConfiguredLabelFont, bug???

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("WrapText");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getTextInputField().setWrapText(getValue());
        }
      }

      @Order(130.0)
      public class StringInputField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
        }
      }

      @Order(140.0)
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

      @Order(150.0)
      public class InputMaskedField extends AbstractStringField {

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InputMasked");
        }
      }

      @Order(160.0)
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
        protected void execChangedValue() throws ProcessingException {
          int maxlen = NumberUtility.nvl(getValue(), 4000);
          getStringInputField().setMaxLength(maxlen);
          getUpperCaseField().setMaxLength(maxlen);
          getInputMaskedField().setMaxLength(maxlen);
        }
      }

      @Order(170.0)
      public class PlaceholderField extends AbstractPlaceholderField {

        @Override
        protected int getConfiguredGridH() {
          return 1;
        }
      }

      @Order(2000.0)
      public class StringField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
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

      @Order(3000.0)
      public class ValueField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Value");
        }
      }

      @Order(4000.0)
      public class DisplayTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DisplayText");
        }
      }

      @Order(5000.0)
      public class UpdateDisplayTextOnModifyField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpdateDisplayTextOnModify");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getStringField().setUpdateDisplayTextOnModify(getValue());
        }
      }

      @Order(6000.0)
      public class Placeholder1Field extends AbstractPlaceholderField {
        @Override
        protected int getConfiguredGridH() {
          return 3;
        }
      }
    }

    @Order(30.0)
    public class LoremIpsumButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getTextInputField().setValue(TEXTS.get("LoremIpsum") + "\n" + TEXTS.get("Lorem"));
        getStringInputField().setValue(TEXTS.get("HelloWorld"));
        getUpperCaseField().setValue(TEXTS.get("HelloWorld"));
        getInputMaskedField().setValue(TEXTS.get("HelloWorld"));
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(50.0)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
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
