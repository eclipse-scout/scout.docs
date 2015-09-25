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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.template.PictureUrlForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.template.PictureUrlForm.MainBox.OkButton;
import org.eclipse.scout.contacts.client.template.PictureUrlForm.MainBox.UrlBox;
import org.eclipse.scout.contacts.client.template.PictureUrlForm.MainBox.UrlBox.PictureUrlField;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;

public class PictureUrlForm extends AbstractForm {

  public PictureUrlForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PictureURL");
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PictureUrlField getPictureUrlField() {
    return getFieldByClass(PictureUrlField.class);
  }

  public UrlBox getUrlBox() {
    return getFieldByClass(UrlBox.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(1000.0)
    public class UrlBox extends AbstractGroupBox {

      @Order(1000.0)
      public class PictureUrlField extends AbstractStringField {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }
      }
    }

    @Order(2000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(3000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
  }
}
