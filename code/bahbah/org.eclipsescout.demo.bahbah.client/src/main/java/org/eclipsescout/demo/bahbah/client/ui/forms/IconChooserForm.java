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
package org.eclipsescout.demo.bahbah.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.bahbah.client.ui.forms.IconChooserForm.MainBox.CancelButton;
import org.eclipsescout.demo.bahbah.client.ui.forms.IconChooserForm.MainBox.IconField;
import org.eclipsescout.demo.bahbah.client.ui.forms.IconChooserForm.MainBox.OkButton;
import org.eclipsescout.demo.bahbah.shared.services.process.IIconProcessService;

public class IconChooserForm extends AbstractForm {

  public IconChooserForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("IconChangeTitle");
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public IconField getIconField() {
    return getFieldByClass(IconField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class IconField extends AbstractFileChooserField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Icon");
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }
    }

    @Order(20)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      BEANS.get(IIconProcessService.class).saveIcon(getIconField().getValue().getContent());
    }
  }
}
