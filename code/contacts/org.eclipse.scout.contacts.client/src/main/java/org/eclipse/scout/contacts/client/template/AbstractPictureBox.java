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
package org.eclipse.scout.contacts.client.template;

import java.net.URL;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.contact.ContactForm;
import org.eclipse.scout.contacts.shared.Icons;
import org.eclipse.scout.contacts.shared.template.AbstractPictureBoxData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;

@FormData(value = AbstractPictureBoxData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractPictureBox extends AbstractGroupBox {

  @Override
  protected boolean getConfiguredBorderVisible() {
    return false;
  }

  @Override
  protected int getConfiguredGridColumnCount() {
    return 1;
  }

  @Override
  protected int getConfiguredGridH() {
    return 5;
  }

  @Override
  protected int getConfiguredGridW() {
    return 1;
  }

  public PictureField getPictureField() {
    return getFieldByClass(PictureField.class);
  }

  public PictureUrlField getPictureUrlField() {
    return getFieldByClass(PictureUrlField.class);
  }

  @Order(1000.0)
  public class PictureField extends AbstractImageField {

    @Override
    protected boolean getConfiguredAutoFit() {
      return true;
    }

    @Override
    protected int getConfiguredGridH() {
      return 5;
    }

    @Override
    protected String getConfiguredImageId() {
      return Icons.User;
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected Class<? extends IValueField> getConfiguredMasterField() {
      // TODO [dwi] not so nice to reference ContactForm --> so this PictureBox cannot be reused
      return ContactForm.MainBox.GeneralBox.PictureBox.PictureUrlField.class;
    }

    @Override
    protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
      try {
        URL url = new URL((String) newMasterValue);
        setImage(IOUtility.getContent(url.openStream()));
        setAutoFit(true);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Order(1000.0)
    public class EditURLMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditURL");
      }

      @Override
      protected void execAction() throws ProcessingException {
        PictureUrlForm form = new PictureUrlForm();
        String url = getPictureUrlField().getValue();

        if (StringUtility.hasText(url)) {
          form.getPictureUrlField().setValue(url);
        }

        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          getPictureUrlField().setValue(form.getPictureUrlField().getValue());
        }
      }
    }
  }

  @Order(2000.0)
  public class PictureUrlField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("PictureURL");
    }

    @Override
    protected boolean getConfiguredVisible() {
      return false;
    }
  }

  // FIXME: workaround as image context menu does not yet work
  @Order(3000.0)
  public class EditUrlButton extends AbstractLinkButton {

    @Override
    protected int getConfiguredHorizontalAlignment() {
      return 1;
    }

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("EditURL");
    }

    @Override
    protected boolean getConfiguredProcessButton() {
      return false;
    }

    @Override
    protected void execClickAction() throws ProcessingException {
      PictureUrlForm form = new PictureUrlForm();
      String url = getPictureUrlField().getValue();

      if (StringUtility.hasText(url)) {
        form.getPictureUrlField().setValue(url);
      }

      form.startModify();
      form.waitFor();
      if (form.isFormStored()) {
        getPictureUrlField().setValue(form.getPictureUrlField().getValue());
      }
    }
  }

}
