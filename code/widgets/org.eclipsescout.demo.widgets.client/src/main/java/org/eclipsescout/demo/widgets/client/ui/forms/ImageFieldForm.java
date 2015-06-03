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

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.dnd.FileListTransferObject;
import org.eclipse.scout.commons.dnd.TransferObject;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ResourceBase;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.ImageField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.ImageURLField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.ScrollbarEnabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedCenterField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedRightField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.IconContentField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.SampleContentButton;
import org.eclipsescout.demo.widgets.shared.Icons;

public class ImageFieldForm extends AbstractForm implements IPageForm {

  public ImageFieldForm() throws ProcessingException {
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
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the AlignedCenterField
   */
  public AlignedCenterField getAlignedCenterField() {
    return getFieldByClass(AlignedCenterField.class);
  }

  /**
   * @return the AlignedRightField
   */
  public AlignedRightField getAlignedRightField() {
    return getFieldByClass(AlignedRightField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DefaultField
   */
  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  /**
   * @return the IconContentField
   */
  public IconContentField getIconContentField() {
    return getFieldByClass(IconContentField.class);
  }

  /**
   * @return the ImageField
   */
  public ImageField getImageField() {
    return getFieldByClass(ImageField.class);
  }

  /**
   * @return the ImageURLField
   */
  public ImageURLField getImageURLField() {
    return getFieldByClass(ImageURLField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the SampleContentButton
   */
  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  /**
   * @return the ScrollbarEnabledField
   */
  public ScrollbarEnabledField getScrollbarEnabledField() {
    return getFieldByClass(ScrollbarEnabledField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    public static final String SCOUT_LOGO = "images/eclipse_scout_logo.png";
    public static final String SCOUT_LOGO_FILENAME = "eclipse_scout_logo.png";
    public static final String BIRD = "http://2.bp.blogspot.com/_LDF9z4ZzZHo/TQZI-CUPl2I/AAAAAAAAAfc/--DuSZRxywM/s1600/bird_1008.jpg";
    public static final String BIRD_OFFLINE = "images/bird_1008.jpg";

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
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
        protected TransferObject execDragRequest() throws ProcessingException {
          Object content = getImage();

          if (content instanceof byte[]) {
            File f = IOUtility.createTempFile(getImageId(), (byte[]) content);
            return new FileListTransferObject(f);
          }

          return null;
        }

        @Override
        protected void execDropRequest(TransferObject transferObject) throws ProcessingException {
          clearErrorStatus();

          if (transferObject.isFileList()) {
            List<String> fileName = ((FileListTransferObject) transferObject).getFilenames();

            if (fileName.size() > 0) {
              try {
                // if you want to work with buffered images
                // BufferedImage bi = ImageIO.read(new FileInputStream(fileName[0]));
                // setImage(bi);

                setImage(IOUtility.getContent(new FileInputStream(fileName.get(0))));
                setImageId(IOUtility.getFileName(fileName.get(0)));
              }
              catch (Exception e) {
                e.printStackTrace();
                addErrorStatus(e.getMessage());
              }
            }
          }
        }

        @Override
        protected void execInitField() throws ProcessingException {
          clearErrorStatus();

          try {
            setImage(IOUtility.getContent(ResourceBase.class.getResourceAsStream(SCOUT_LOGO)));
            setImageId(SCOUT_LOGO_FILENAME);
          }
          catch (Exception e) {
            e.printStackTrace();
            addErrorStatus(e.getMessage());
          }
        }
      }

      @Order(20.0)
      public class IconContentField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.House;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedLeft");
        }
      }

      @Order(30.0)
      public class AlignedCenterField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.House;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedCenter");
        }
      }

      @Order(40.0)
      public class AlignedRightField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.House;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedRight");
        }
      }
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class ImageField extends AbstractImageField {

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
          return TEXTS.get("AutoFit");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ImageFieldForm.MainBox.ConfigurationBox.ImageURLField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {

          getImageURLField().clearErrorStatus();

          try {
            URL url = getUrl((String) newMasterValue);
            setImage(IOUtility.getContent(url.openStream()));
          }
          catch (Exception e) {
            e.printStackTrace();
            getImageURLField().addErrorStatus(e.getMessage());
          }
        }
      }

      @Order(20.0)
      public class ScrollbarEnabledField extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollbarEnabled");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ImageFieldForm.MainBox.ConfigurationBox.ImageURLField.class;
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return true;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          try {
            URL url = getUrl((String) newMasterValue);
            setImage(IOUtility.getContent(url.openStream()));
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

      private URL getUrl(String urlString) throws Exception {
        if (urlString.equals(BIRD)) {
          return ResourceBase.class.getResource(BIRD_OFFLINE);
        }

        return new URL((String) urlString);
      }

      @Order(30.0)
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
      }
    }

    @Order(30.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getImageURLField().setValue(BIRD);
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
