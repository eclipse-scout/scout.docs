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
package org.eclipsescout.demo.widgets.client.old.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.colorfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.ui.forms.ColorFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.ColorFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;
import org.eclipsescout.demo.widgets.shared.Icons;

public class ColorFieldForm extends AbstractForm implements IPageForm {

  public ColorFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ColorField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(50.0)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10.0)
      public class ColorField01 extends AbstractColorField {
        @Override
        protected void execInitField() throws ProcessingException {
          setValue("#FF0000");
        }

        @Override
        protected String getConfiguredIconId() {
          return Icons.StarYellow;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Sample Color Picker";
        }
      }

      @Order(20.0)
      public class ColorField02 extends AbstractColorField {

        @Override
        protected String getConfiguredLabel() {
          return "With one custom menu";
        }

        @Order(10)
        public class AMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "custom menu";
          }

          @Override
          protected void execAction() throws ProcessingException {
            MessageBoxes.createOk().withHeader("Menu").withBody(getClass().getSimpleName()).show();
          }
        }
      }

      @Order(30)
      public class SampleStringField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return "a label";
        }
      }
    }

    @Order(60.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
