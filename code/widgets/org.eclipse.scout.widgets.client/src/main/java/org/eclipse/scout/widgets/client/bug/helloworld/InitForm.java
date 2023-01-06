/*
 * Copyright (c) 2023 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.bug.helloworld;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.bug.helloworld.InitForm.MainBox.CancelButton;
import org.eclipse.scout.widgets.client.bug.helloworld.InitForm.MainBox.OkButton;
import org.eclipse.scout.widgets.client.bug.helloworld.InitForm.MainBox.SomeButton;

@ClassId("de056b24-5587-43d8-9f11-31f887a317fa")
public class InitForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    // TODO [Florian] verify translation
    return TEXTS.get("Init");
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SomeButton getSomeButton() {
    return getFieldByClass(SomeButton.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(1000)
  @ClassId("2601f0de-1b4f-40c1-a1e9-52325628cd1c")
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    @ClassId("1e9552e5-cd5b-40b0-9f04-50f88ce1c0d3")
    public class SomeButton extends AbstractButton {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("MyNlsKey");
      }

      @Override
      protected void execClickAction() {
        HelloWorldForm f = new HelloWorldForm();
        f.start();
      }
    }

    @Order(100000)
    @ClassId("0af603c5-2ef7-4292-a6dd-4097b2dd25da")
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000)
    @ClassId("c4ebca3b-fed9-4aae-b478-cd497d617240")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }

    @Override
    protected void execStore() {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }

    @Override
    protected void execStore() {
    }
  }
}
