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

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.dnd.ResourceListTransferObject;
import org.eclipse.scout.rt.client.ui.dnd.TransferObject;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.IImageField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.Image1Field;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.Image2Field;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.ImageIdField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.ImageURLField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignmentTitleField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.SampleContentButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(7000.0)
@ClassId("0fde5f3a-513a-45ce-9957-cb0c2cdb21fd")
public class ImageFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final Logger LOG = LoggerFactory.getLogger(ImageFieldForm.class);

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ImageField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public Image1Field getImage1Field() {
    return getFieldByClass(Image1Field.class);
  }

  public Image2Field getImage2Field() {
    return getFieldByClass(Image2Field.class);
  }

  public ImageURLField getImageURLField() {
    return getFieldByClass(ImageURLField.class);
  }

  public ImageIdField getImageIdField() {
    return getFieldByClass(ImageIdField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public AlignmentTitleField getAlignmentTitleField() {
    return getFieldByClass(AlignmentTitleField.class);
  }

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  @Order(10)
  @ClassId("f6ee8c17-1166-470c-8f92-4252ddbc4dc5")
  public class MainBox extends AbstractGroupBox {

    public static final String SCOUT_LOGO = "images/eclipse_scout_logo.png";
    public static final String SCOUT_LOGO_FILENAME = "eclipse_scout_logo.png";
    public static final String BIRD = "http://2.bp.blogspot.com/_LDF9z4ZzZHo/TQZI-CUPl2I/AAAAAAAAAfc/--DuSZRxywM/s1600/bird_1008.jpg";
    public static final String BIRD_OFFLINE = "images/bird_1008.jpg";

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("0b717ac1-4d0b-43e9-8b8f-f8ba9e3eef96")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("40c78cb8-034c-46b5-a6fc-a60f973e0033")
      public class DefaultField extends AbstractImageField {

        @Override
        protected int getConfiguredDropType() {
          return TYPE_FILE_TRANSFER;
        }

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected int getConfiguredGridW() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("ImageDragDropSupport");
        }

        @Override
        protected void execDropRequest(TransferObject transferObject) {
          clearErrorStatus();

          if (transferObject instanceof ResourceListTransferObject) {
            List<BinaryResource> resources = ((ResourceListTransferObject) transferObject).getResources();

            if (!resources.isEmpty()) {
              BinaryResource resource = CollectionUtility.firstElement(resources);
              // if you want to work with buffered images
              // BufferedImage bi = ImageIO.read(new FileInputStream(fileName[0]));
              // setImage(bi);
              setImage(resource.getContent());
              setImageId(resource.getFilename());
            }
          }
        }

        @Override
        protected void execInitField() {
          clearErrorStatus();

          try (InputStream in = ResourceBase.class.getResourceAsStream(SCOUT_LOGO)) {
            setImage(IOUtility.readBytes(in));
            setImageId(SCOUT_LOGO_FILENAME);
          }
          catch (Exception e) {
            e.printStackTrace();
            addErrorStatus(e.getMessage());
          }
        }
      }

      @Order(20)
      @ClassId("7e8b10d2-799c-488c-ae63-d352eec5d62f")
      @FormData(sdkCommand = SdkCommand.IGNORE)
      public class AlignmentTitleField extends AbstractLabelField {

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
          setValue(TEXTS.get("AlignmentTitle"));
        }

        @Override
        protected int getConfiguredGridW() {
          return 3;
        }
      }

      @Order(100)
      @ClassId("20919b94-de17-4e07-b843-798590c23e2c")
      public class IconContentFieldTopLeft extends AbstractAlignedImageField {
        public IconContentFieldTopLeft() {
          super(-1, -1);
        }
      }

      @Order(110)
      @ClassId("e2eeca37-9bb6-49d6-82fd-787e0a5eb9ed")
      public class IconContentFieldMiddleLeft extends AbstractAlignedImageField {
        public IconContentFieldMiddleLeft() {
          super(0, -1);
        }
      }

      @Order(120)
      @ClassId("c0074d4f-a849-4cdf-9306-0a3d414057fb")
      public class IconContentFieldBottomLeft extends AbstractAlignedImageField {
        public IconContentFieldBottomLeft() {
          super(1, -1);
        }
      }

      @Order(200)
      @ClassId("aba02980-8e1c-46d6-8f91-f3c7bb07b445")
      public class IconContentFieldTopCenter extends AbstractAlignedImageField {
        public IconContentFieldTopCenter() {
          super(-1, 0);
        }
      }

      @Order(210)
      @ClassId("66decb40-2edf-4d4a-a7bb-1fe01722a1dd")
      public class IconContentFieldMiddleCenter extends AbstractAlignedImageField {
        public IconContentFieldMiddleCenter() {
          super(0, 0);
        }
      }

      @Order(220)
      @ClassId("42ec888a-1f99-4711-93f4-c01636eec591")
      public class IconContentFieldBottomCenter extends AbstractAlignedImageField {
        public IconContentFieldBottomCenter() {
          super(1, 0);
        }
      }

      @Order(300)
      @ClassId("8ca4b731-015d-4db0-8ff8-6521f61081ab")
      public class IconContentFieldTopRight extends AbstractAlignedImageField {
        public IconContentFieldTopRight() {
          super(-1, 1);
        }
      }

      @Order(310)
      @ClassId("cac64b35-d0a7-4699-b343-a28e672f05fb")
      public class IconContentFieldMiddleRight extends AbstractAlignedImageField {
        public IconContentFieldMiddleRight() {
          super(0, 1);
        }
      }

      @Order(320)
      @ClassId("c21627f3-0f05-4ccd-8871-18eb2ec61ccf")
      public class IconContentFieldBottomRight extends AbstractAlignedImageField {
        public IconContentFieldBottomRight() {
          super(1, 1);
        }
      }
    }

    @Order(20)
    @ClassId("81229c47-583b-4bda-ab5f-d35fb3f2aa1a")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("b052fcb0-0d55-4154-af0f-8c1afdf0bcd2")
      public class Image1Field extends AbstractImageField {

        @Override
        protected boolean getConfiguredAutoFit() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return "View 1";
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "Image alignment: horizontally and vertically centered.";
        }
      }

      @Order(15)
      @ClassId("39b4788f-817b-4e03-a211-91f201b14c3e")
      public class Image1ScrollBarEnabledCheckbox extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollbarEnabled");
        }

        @Override
        protected void execChangedValue() {
          getImage1Field().setScrollBarEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage1Field().isScrollBarEnabled());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(16)
      @ClassId("867ccc24-8a25-476a-b640-21ed740316bb")
      public class Image1AutoFitCheckbox extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoFit");
        }

        @Override
        protected void execChangedValue() {
          getImage1Field().setAutoFit(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage1Field().isAutoFit());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(20)
      @ClassId("07d4987d-9830-4a44-bceb-db877d67084a")
      public class Image2Field extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return "View 2";
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "Image alignment: bottom right";
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return true;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected int getConfiguredVerticalAlignment() {
          return 1;
        }
      }

      @Order(25)
      @ClassId("faeca908-4a51-434f-8863-e875c17a0b80")
      public class Image2ScrollbarEnabledFieldCheckbox extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollbarEnabled");
        }

        @Override
        protected void execChangedValue() {
          getImage2Field().setScrollBarEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage2Field().isScrollBarEnabled());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(26)
      @ClassId("db830613-5367-49c6-a16e-64c08b21e583")
      public class Image2AutoFitCheckbox extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoFit");
        }

        @Override
        protected void execChangedValue() {
          getImage2Field().setAutoFit(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage2Field().isAutoFit());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(30)
      @ClassId("9f2052e0-2ea3-40c4-b508-65ef881c0323")
      public class ImageURLField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ImageURL");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getImageIdField().clearErrorStatus();
          getImageIdField().setValueChangeTriggerEnabled(false);
          try {
            getImageIdField().setValue(null);
          }
          finally {
            getImageIdField().setValueChangeTriggerEnabled(true);
          }
          setImageFromUrl(getImage1Field(), getValue());
          setImageFromUrl(getImage2Field(), getValue());
        }

        protected void setImageFromUrl(IImageField field, String urlString) {
          getImageURLField().clearErrorStatus();
          byte[] img = null;
          try {
            if (urlString != null) {
              URL url;
              if (urlString.equals(BIRD)) {
                url = ResourceBase.class.getResource(BIRD_OFFLINE);
              }
              else {
                url = new URL(urlString);
              }
              img = IOUtility.readFromUrl(url);
            }
          }
          catch (Exception e) {
            String errorMsg = "Error while loading image from URL " + urlString;
            LOG.warn(errorMsg, e);
            getImageURLField().addErrorStatus(errorMsg);
          }
          field.setImageId(null);
          field.setImage(img);
        }
      }

      @Order(40)
      @ClassId("dc2b867e-fa27-4221-a8c1-a84de3dc6737")
      public class ImageIdField extends AbstractSmartField<String> {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return "ImageId";
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return IconIdLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getImageURLField().clearErrorStatus();
          getImageURLField().setValueChangeTriggerEnabled(false);
          try {
            getImageURLField().clearErrorStatus();
            getImageURLField().setValue(null);
          }
          finally {
            getImageURLField().setValueChangeTriggerEnabled(true);
          }
          getImage1Field().setImage(null);
          getImage1Field().setImageId(getValue());
          getImage2Field().setImage(null);
          getImage2Field().setImageId(getValue());
        }
      }
    }

    @Order(30)
    @ClassId("665ca770-0c69-48d6-97fc-fd442edbfda6")
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getImageURLField().setValue(BIRD);
      }
    }

    @Order(40)
    @ClassId("56d326d5-a1b6-4b04-9367-4ad4e15c0e37")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
