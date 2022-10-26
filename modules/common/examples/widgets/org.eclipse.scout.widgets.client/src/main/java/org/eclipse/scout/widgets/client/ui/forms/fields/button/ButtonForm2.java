/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.button;

import org.eclipse.scout.rt.client.ui.desktop.notification.DesktopNotification;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.fields.button.ButtonForm2.MainBox.FormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.button.ButtonForm2.MainBox.GroupBox.Button;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;

/**
 * Replacement for ButtonForm. This is work in progress and will be moved to the open source widgets app
 * if the properties boxes cover all the features of the fields. Do not introduce messy code used to test
 * bugs, tickets or other test-cases.
 */
@ClassId("0f94beb3-c651-4377-8889-b116c78173ee")
public class ButtonForm2 extends AbstractForm implements IPageForm {

  @Override
  protected String getConfiguredTitle() {
    return "Button (new)";
  }

  @Override
  protected void execInitForm() {
    Button button = getButton();
    getFieldByClass(FormFieldPropertiesBox.class).setField(button);
  }

  protected Button getButton() {
    return getFieldByClass(Button.class);
  }

  @Order(100)
  @ClassId("bdfe00dd-9347-4279-9003-f70d752f2c32")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("2a36ed0b-b478-4bed-8e63-9149d21fc684")
    public class GroupBox extends AbstractGroupBox {

      @ClassId("554d4e8b-acff-4019-80ed-8d5529e5d67a")
      public class Button extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return "Button";
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected void execClickAction() {
          getDesktop().addNotification(new DesktopNotification(TEXTS.get("ButtonClickMessage")));
        }
      }
    }

    @Order(200)
    @ClassId("f2cf90d5-3ab5-430b-bdde-d45593508ea5")
    public class ButtonPropertiesBox extends AbstractButtonPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getButton());
      }
    }


    @ClassId("909942c9-3ab5-4962-a531-19ff0fc0b1d0")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getButton());
      }
    }
  }

  @Order(200)
  @ClassId("7bed1a43-6d12-44ce-8f4c-054d6c6d2d67")
  public class CloseButton extends AbstractCloseButton {

  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

}
