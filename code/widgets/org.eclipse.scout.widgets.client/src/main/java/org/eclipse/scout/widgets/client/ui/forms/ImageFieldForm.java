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

import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

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
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
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
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedCenterField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedRightField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.IconContentField;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm.MainBox.SampleContentButton;
import org.eclipse.scout.widgets.shared.Icons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(7000.0)
public class ImageFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final Logger LOG = LoggerFactory.getLogger(ImageFieldForm.class);

  public ImageFieldForm() {
    super();
  }

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

  public AlignedCenterField getAlignedCenterField() {
    return getFieldByClass(AlignedCenterField.class);
  }

  public AlignedRightField getAlignedRightField() {
    return getFieldByClass(AlignedRightField.class);
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

  public IconContentField getIconContentField() {
    return getFieldByClass(IconContentField.class);
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

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  @Order(10)
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
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class DefaultField extends AbstractImageField {

        @Override
        protected int getConfiguredDragType() {
          return TYPE_FILE_TRANSFER;
        }

        @Override
        protected int getConfiguredDropType() {
          return TYPE_FILE_TRANSFER;
        }

        @Override
        protected int getConfiguredGridH() {
          return 4;
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
        protected TransferObject execDragRequest() {
          Object content = getImage();

          if (content instanceof byte[]) {
            BinaryResource resource = new BinaryResource(getImageId(), (byte[]) content);
            return new ResourceListTransferObject(resource);
          }

          return null;
        }

        @Override
        protected void execDropRequest(TransferObject transferObject) {
          clearErrorStatus();

          if (transferObject instanceof ResourceListTransferObject) {
            List<BinaryResource> resources = ((ResourceListTransferObject) transferObject).getResources();

            if (resources.size() > 0) {
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
      public class IconContentField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.EclipseScout;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedLeft");
        }
      }

      @Order(30)
      public class AlignedCenterField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.EclipseScout;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedCenter");
        }
      }

      @Order(40)
      public class AlignedRightField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.EclipseScout;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedRight");
        }
      }
    }

    @Order(20)
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
            URL url = null;
            if (urlString != null) {
              if (urlString.equals(BIRD)) {
                url = ResourceBase.class.getResource(BIRD_OFFLINE);
              }
              else {
                url = new URL(urlString);
              }
            }
            img = IOUtility.readFromUrl(url);
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
        protected void execFilterLookupResult(ILookupCall<String> call, List<ILookupRow<String>> result) {
          for (Iterator<ILookupRow<String>> it = result.iterator(); it.hasNext();) {
            ILookupRow<String> l = it.next();
            if (StringUtility.startsWith(l.getKey(), "font:")) {
              it.remove();
            }
          }
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
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
