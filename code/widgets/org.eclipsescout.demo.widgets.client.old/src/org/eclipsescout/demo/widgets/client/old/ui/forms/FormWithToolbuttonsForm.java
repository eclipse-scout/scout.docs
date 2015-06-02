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
import org.eclipse.scout.rt.client.ui.action.tool.AbstractToolButton;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FormWithToolbuttonsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FormWithToolbuttonsForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FormWithToolbuttonsForm.MainBox.GroupBox.Field01;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;
import org.eclipsescout.demo.widgets.shared.Icons;

public class FormWithToolbuttonsForm extends AbstractForm implements IPageForm {

  public FormWithToolbuttonsForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FormWithToolbuttons");
  }

  @Override
  protected String getConfiguredSubTitle() {
    return null;//"A subtitle";
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public Field01 getDefaultField() {
    return getFieldByClass(Field01.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class Field01 extends AbstractStringField {

        @Override
        protected void execInitField() throws ProcessingException {
          Field02 f = getFieldByClass(Field02.class);
          System.out.println("field " + f);
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Field 01";
        }
      }

      @Order(20.0)
      public class Field02 extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Field 02";
        }
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  @Order(200)
  public class Toolbutton01 extends AbstractToolButton {
    @Override
    protected String getConfiguredText() {
      return "Toolbutton 1";
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.StarRed;
    }
  }

  @Order(200)
  public class Toolbutton02 extends AbstractToolButton {
    @Override
    protected String getConfiguredText() {
      return "Toolbutton 2";
    }

    @Override
    protected boolean getConfiguredToggleAction() {
      return true;
    }

    @Override
    protected void execInitAction() throws ProcessingException {
      setSelected(getToolButtonByClass(Toolbutton01.class).isEnabled());
    }

    @Override
    protected void execAction() throws ProcessingException {
      getToolButtonByClass(Toolbutton01.class).setEnabled(Toolbutton02.this.isSelected());
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.StarYellow;
    }
  }

  public class PageFormHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      getDefaultField().setValue(TEXTS.get("Lorem"));
    }
  }
}
