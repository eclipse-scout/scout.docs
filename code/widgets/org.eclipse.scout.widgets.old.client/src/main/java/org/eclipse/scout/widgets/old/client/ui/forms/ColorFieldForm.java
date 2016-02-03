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
package org.eclipse.scout.widgets.old.client.ui.forms;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.colorfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.ColorFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.ColorFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

public class ColorFieldForm extends AbstractForm implements IPageForm {

  public ColorFieldForm() {
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
  public void startPageForm() {
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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(50)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      public class ColorField01 extends AbstractColorField {
        @Override
        protected void execInitField() {
          setValue("#FF0000");
        }

        @Override
        protected String getConfiguredLabel() {
          return "Sample Color Picker";
        }
      }

      @Order(20)
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
          protected void execAction() {
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

    @Order(60)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
