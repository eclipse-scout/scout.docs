/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.awt.image.BufferedImage;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.colorpickerfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.extension.client.ui.form.fields.button.AbstractExtensibleRadioButton;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.services.FontIconProviderService;
import org.eclipsescout.demo.widgets.client.services.IFontIconProviderService;
import org.eclipsescout.demo.widgets.client.services.lookup.FontIconLookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.FontLookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.BackgroundGroup;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.BackgroundGroup.CircleButton;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.BackgroundGroup.EmptyButton;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.BackgroundGroup.SquareButton;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.ColorField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.FontField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.FontIconField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.IconNameField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.ResultField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ConfigurationBox.SizeField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ExamplesBox.FontImageField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ExamplesBox.FontRadioButtonGroup;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ExamplesBox.FontSmartField;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ExamplesBox.IconButton;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.ExamplesBox.RadioButtonGroup.FemaleButton;
import org.eclipsescout.demo.widgets.client.ui.forms.IconsForm.MainBox.SampleContentButton;

/**
 * @author mzi
 */
public class IconsForm extends AbstractForm implements IPageForm {

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public IconsForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Icons");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  public AbstractCloseButton getCloseButton() throws ProcessingException {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the BackgroundGroup
   */
  public BackgroundGroup getBackgroundGroup() {
    return getFieldByClass(BackgroundGroup.class);
  }

  /**
   * @return the CircleButton
   */
  public CircleButton getCircleButton() {
    return getFieldByClass(CircleButton.class);
  }

  /**
   * @return the ColorField
   */
  public ColorField getColorField() {
    return getFieldByClass(ColorField.class);
  }

  /**
   * @return the ConfigurationBox
   */
  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  /**
   * @return the ExamplesBox
   */
  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  /**
   * @return the EmptyButton
   */
  public EmptyButton getEmptyButton() {
    return getFieldByClass(EmptyButton.class);
  }

  /**
   * @return the FontIconField
   */
  public FontIconField getFontIconField() {
    return getFieldByClass(FontIconField.class);
  }

  /**
   * @return the FemaleButton
   */
  public FemaleButton getFemaleButton() {
    return getFieldByClass(FemaleButton.class);
  }

  /**
   * @return the FontField
   */
  public FontField getFontField() {
    return getFieldByClass(FontField.class);
  }

  /**
   * @return the FontSmartField
   */
  public FontSmartField getFontSmartField() {
    return getFieldByClass(FontSmartField.class);
  }

  /**
   * @return the GenderGroup
   */
  public FontRadioButtonGroup getGenderGroup() {
    return getFieldByClass(FontRadioButtonGroup.class);
  }

  /**
   * @return the IconButton
   */
  public IconButton getIconButton() {
    return getFieldByClass(IconButton.class);
  }

  /**
   * @return the IconNameField
   */
  public IconNameField getIconNameField() {
    return getFieldByClass(IconNameField.class);
  }

  /**
   * @return the FontImageField
   */
  public FontImageField getFontImageField() {
    return getFieldByClass(FontImageField.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the ResultField
   */
  public ResultField getResultField() {
    return getFieldByClass(ResultField.class);
  }

  /**
   * @return the SampleContentButton
   */
  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  /**
   * @return the SizeField
   */
  public SizeField getSizeField() {
    return getFieldByClass(SizeField.class);
  }

  /**
   * @return the SquareButton
   */
  public SquareButton getSquareButton() {
    return getFieldByClass(SquareButton.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(1000.0)
      public class ImageField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected String getConfiguredImageId() {
          return AbstractIcons.Gears;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Image");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return getConfiguredImageId();
        }
      }

      @Order(2000.0)
      public class RadioButtonGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RadioButton");
        }

        @Order(1000.0)
        public class FemaleButton extends AbstractExtensibleRadioButton<Long> {

          @Override
          protected String getConfiguredIconId() {
            return AbstractIcons.StatusInfo;
          }

          @Override
          protected String getConfiguredTooltipText() {
            return getConfiguredIconId();
          }
        }

        @Order(2000.0)
        public class MaleButton extends AbstractExtensibleRadioButton<Long> {

          @Override
          protected String getConfiguredIconId() {
            return AbstractIcons.StatusWarning;
          }

          @Override
          protected String getConfiguredTooltipText() {
            return getConfiguredIconId();
          }
        }

        @Order(3000.0)
        public class ChildButton extends AbstractExtensibleRadioButton<Long> {

          @Override
          protected String getConfiguredIconId() {
            return AbstractIcons.StatusError;
          }

          @Override
          protected String getConfiguredTooltipText() {
            return getConfiguredIconId();
          }
        }
      }

      @Order(3000.0)
      public class SmartField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SmartField");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }
      }

      @Order(4000.0)
      public class IconButton extends AbstractButton {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected String getConfiguredIconId() {
          return AbstractIcons.WizardNextButton;
        }

        @Override
        protected String getConfiguredTooltipText() {
          return getConfiguredIconId();
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 100;
        }
      }

      @Order(5000.0)
      public class FontImageField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected String getConfiguredImageId() {
          return "font:plane!circle";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Image");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return getConfiguredImageId();
        }
      }

      @Order(6000.0)
      public class FontRadioButtonGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RadioButton");
        }

        @Order(1000.0)
        public class FemaleButton extends AbstractExtensibleRadioButton<Long> {

          @Override
          protected String getConfiguredIconId() {
            return "font:female!16";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return getConfiguredIconId();
          }
        }

        @Order(2000.0)
        public class MaleButton extends AbstractExtensibleRadioButton<Long> {

          @Override
          protected String getConfiguredIconId() {
            return "font:male!16";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return getConfiguredIconId();
          }
        }

        @Order(3000.0)
        public class ChildButton extends AbstractExtensibleRadioButton<Long> {

          @Override
          protected String getConfiguredIconId() {
            return "font:child!16";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return getConfiguredIconId();
          }
        }
      }

      @Order(7000.0)
      public class FontSmartField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SmartField");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return FontIconLookupCall.class;
        }
      }

      @Order(8000.0)
      public class FontIconButton extends AbstractButton {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected String getConfiguredIconId() {
          if (UserAgentUtility.isWebClient()) {
            return "font:arrow-right!16!#ffffff";
          }
          else {
            return "font:arrow-right!16";
          }
        }

        @Override
        protected String getConfiguredTooltipText() {
          return getConfiguredIconId();
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 100;
        }
      }

    }

    @Order(2000.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(1000.0)
      public class ResultField extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Result");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return IconsForm.MainBox.ConfigurationBox.IconNameField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          String iconName = (String) newMasterValue;
          BufferedImage image = SERVICES.getService(IFontIconProviderService.class).getBufferedImageIcon(iconName);

          setImage(image);
          setImageId(iconName);
        }
      }

      @Order(2000.0)
      public class IconNameField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IconName");
        }

        protected void updateValue() {
          String iconId = getFontIconField().getValue();
          String font = getFontField().getValue();
          String color = getColorField().getValue();
          Integer size = getSizeField().getValue();
          String background = getBackgroundGroup().getValue();

          if (StringUtility.isNullOrEmpty(iconId)) {
            return;
          }

          StringBuffer sb = new StringBuffer(iconId);

          if (StringUtility.hasText(color)) {
            sb.append(FontIconProviderService.PARAMETER_SEPARATOR + color);
          }

          if (size != null) {
            sb.append(FontIconProviderService.PARAMETER_SEPARATOR + size);
          }

          if (StringUtility.hasText(background)) {
            sb.append(FontIconProviderService.PARAMETER_SEPARATOR + background);
          }

          if (StringUtility.hasText(font)) {
            sb.append(FontIconProviderService.FONT_SEPARATOR + font);
          }

          setValue(sb.toString());
        }
      }

      @Order(2500.0)
      public class FontIconField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IconId");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return FontIconLookupCall.class;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getIconNameField().updateValue();
        }
      }

      @Order(3000.0)
      public class FontField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Font");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return FontLookupCall.class;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getIconNameField().updateValue();
        }
      }

      @Order(5000.0)
      public class ColorField extends AbstractColorField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Color");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getIconNameField().updateValue();
        }
      }

      @Order(6000.0)
      public class SizeField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Size");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getIconNameField().updateValue();
        }
      }

      @Order(7000.0)
      public class BackgroundGroup extends AbstractRadioButtonGroup<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Background");
        }

        @Order(-1000.0)
        public class SquareButton extends AbstractExtensibleRadioButton<String> {

          @Override
          public String getRadioValue() {
            return FontIconProviderService.ICON_BACKGROUND_SQUARE;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Square");
          }
        }

        @Order(0.0)
        public class CircleButton extends AbstractExtensibleRadioButton<String> {

          @Override
          public String getRadioValue() {
            return FontIconProviderService.ICON_BACKGROUND_CIRCLE;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Circle");
          }
        }

        @Order(1000.0)
        public class EmptyButton extends AbstractExtensibleRadioButton<String> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Empty");
          }
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getIconNameField().updateValue();
        }
      }

    }

    @Order(3000.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getFontIconField().setValue("font:download");
        getFontField().setValue(FontIconProviderService.ICON_FONT);
        getColorField().setValue("#FAA635");
        getSizeField().setValue(128);
        getBackgroundGroup().setValue(FontIconProviderService.ICON_BACKGROUND_SQUARE);
      }
    }

    @Order(4000.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
