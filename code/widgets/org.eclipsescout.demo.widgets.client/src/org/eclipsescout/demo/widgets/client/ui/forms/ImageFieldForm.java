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

import java.net.URL;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ImageBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ImageBox.ImageField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ImageBox.ImageURLField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ImageBox.ScoutLogoField;

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

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ImageBox getImageBox() {
    return getFieldByClass(ImageBox.class);
  }

  public ImageField getImageField() {
    return getFieldByClass(ImageField.class);
  }

  public ImageURLField getImageURLField() {
    return getFieldByClass(ImageURLField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ScoutLogoField getScoutLogoField() {
    return getFieldByClass(ScoutLogoField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ImageBox extends AbstractGroupBox {

      @Order(10.0)
      public class ScoutLogoField extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 8;
        }

        @Override
        protected int getConfiguredGridW() {
          return 4;
        }

        @Override
        protected String getConfiguredImageId() {
          return "scout_logo.jpg";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScoutLogo");
        }
      }

      @Order(20.0)
      public class ImageURLField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ImageURL");
        }
      }

      @Order(30.0)
      public class ImageField extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 8;
        }

        @Override
        protected int getConfiguredGridW() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("YourImage");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ImageURLField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          clearErrorStatus();

          try {
            URL url = new URL((String) newMasterValue);
            setImage(IOUtility.getContent(url.openStream()));
            setAutoFit(true);
          }
          catch (Exception e) {
            setErrorStatus(e.getMessage());
          }
        }
      }
    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
