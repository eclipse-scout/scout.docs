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

import org.eclipse.scout.rt.client.ui.IDisplayParent;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.widgets.client.bug.helloworld.HelloWorldForm.MainBox.TopBox;
import org.eclipse.scout.widgets.client.bug.helloworld.HelloWorldForm.MainBox.TopBox.MessageField;
import org.eclipse.scout.widgets.client.bug.work.InvisibleOutline;

/**
 * <h3>{@link HelloWorldForm}</h3>
 *
 * @author Florian
 */
@ClassId("413fdd5a-e09e-419f-8d40-bd23eea56752")
public class HelloWorldForm extends AbstractForm {

  public HelloWorldForm() {
    setHandler(new ViewHandler());
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected IDisplayParent getConfiguredDisplayParent() {
    return getDesktop().findOutline(InvisibleOutline.class);
  }

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.World;
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public TopBox getTopBox() {
    return getFieldByClass(TopBox.class);
  }

  public MessageField getMessageField() {
    return getFieldByClass(MessageField.class);
  }

  @Order(1000)
  @ClassId("6d6ad0e0-e20a-4d3f-bb51-d4473a0a2a86")
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    @ClassId("9ec7f1ad-c694-4f3c-bab2-7de8cf289003")
    public class TopBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("MessageFromServer");
      }

      @Order(1000)
      @ClassId("0abfff73-0179-4ad0-9699-2c7c8b58163f")
      public class MessageField extends AbstractStringField {
        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Message");
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }
    }
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }
  }
}
