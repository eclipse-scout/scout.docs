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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.GridData;
import org.eclipse.scout.rt.client.ui.form.fields.ICompositeField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.IBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm.MainBox.CheckFieldBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm.MainBox.LinkButtonBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm.MainBox.PushButtonBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm.MainBox.RadioButtonBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm.MainBox.ToggleButtonBox;

public class ButtonsForm extends AbstractForm implements IPageForm {

  public ButtonsForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredCacheBounds() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Buttons");
  }

  @Override
  protected void execInitForm() throws ProcessingException {
    decorateButtonList(getFieldByClass(PushButtonBox.class), "Push", IButton.DISPLAY_STYLE_DEFAULT);
    decorateButtonList(getFieldByClass(ToggleButtonBox.class), "Toggle", IButton.DISPLAY_STYLE_TOGGLE);
    decorateButtonList(getFieldByClass(LinkButtonBox.class), "Link", IButton.DISPLAY_STYLE_LINK);
    decorateButtonList(getFieldByClass(RadioButtonBox.class), "Radio", IButton.DISPLAY_STYLE_RADIO);
    decorateCheckBoxList(getFieldByClass(CheckFieldBox.class), "Check");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public AbstractCloseButton getCloseButton() throws ProcessingException {
    return getFieldByClass(CloseButton.class);
  }

  private void decorateButtonList(ICompositeField box, String label, int displayStyle) {
    int index = 0;
    for (IFormField f : box.getFields()) {
      if (f instanceof IButton) {
        IButton b = (IButton) f;
        b.setDisplayStyleInternal(displayStyle);
        switch (index) {
          case 0: {
            b.setLabel("&" + label);
            break;
          }
          case 1: {
            b.setLabel(label + " selected");
            b.setSelected(true);
            break;
          }
          case 2: {
            b.setLabel(label + " disabled");
            b.setEnabled(false);
            break;
          }
          case 3: {
            b.setLabel(label + " disabled & selected");
            b.setSelected(true);
            b.setEnabled(false);
            break;
          }
        }
        index++;
      }
    }
  }

  private void decorateCheckBoxList(ICompositeField box, String label) {
    int index = 0;
    for (IFormField f : box.getFields()) {
      if (f instanceof IBooleanField) {
        IBooleanField b = (IBooleanField) f;
        b.setLabelVisible(false);
        GridData g = b.getGridData();
        g.useUiWidth = true;
        b.setGridDataInternal(g);
        switch (index) {
          case 0: {
            b.setLabel(label);
            break;
          }
          case 1: {
            b.setLabel(label + " selected");
            b.setChecked(true);
            break;
          }
          case 2: {
            b.setLabel(label + " disabled");
            b.setEnabled(false);
            break;
          }
          case 3: {
            b.setLabel(label + " disabled & selected");
            b.setChecked(true);
            b.setEnabled(false);
            break;
          }
        }
        index++;
      }
    }
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class PushButtonBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class PushButton1 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
        }

        @Order(10)
        public class TestMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "TestMenu";
          }
        }
      }

      @Order(20)
      public class PushButton2 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(30)
      public class PushButton3 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(40)
      public class PushButton4 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(20)
    public class ToggleButtonBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class ToggleButton1 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(20)
      public class ToggleButton2 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(30)
      public class ToggleButton3 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(40)
      public class ToggleButton4 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(30)
    public class LinkButtonBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class LinkButton1 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(20)
      public class LinkButton2 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(30)
      public class LinkButton3 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(40)
      public class LinkButton4 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(40)
    public class RadioButtonBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class RadioButton1 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(20)
      public class RadioButton2 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(30)
      public class RadioButton3 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(40)
      public class RadioButton4 extends AbstractButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(50)
    public class CheckFieldBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class CheckField1 extends AbstractCheckBox {
      }

      @Order(20)
      public class CheckField2 extends AbstractCheckBox {
      }

      @Order(30)
      public class CheckField3 extends AbstractCheckBox {
      }

      @Order(40)
      public class CheckField4 extends AbstractCheckBox {
      }
    }

    @Order(100)
    public class Special1Box extends AbstractGroupBox {

      @Order(40)
      public class VisibilityButton extends AbstractButton {

        @Override
        public int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DEFAULT;
        }

        @Override
        public String getConfiguredLabel() {
          return "Set 'Button with menus' invisible/visible (10sec)";
        }

        @Override
        public void execClickAction() throws ProcessingException {
          try {
            Thread.sleep(10000L);
          }
          catch (InterruptedException e) {
            e.printStackTrace();
          }

          ButtonWithMenu buttonWithMenu = getFieldByClass(ButtonWithMenu.class);
          buttonWithMenu.setView(!buttonWithMenu.isEnabled(), !buttonWithMenu.isVisible(), false);
        }
      }

      @Order(50)
      public class ButtonWithMenu extends AbstractButton {

        @Override
        public boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Button with menu";
        }

        @Override
        public boolean getConfiguredVisible() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          requestPopup();
        }

        @Order(10)
        public class Menu1 extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Menu1";
          }
        }

        @Order(20)
        public class Menu2 extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Menu2";
          }
        }

        @Order(30)
        public class Menu3 extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Menu3";
          }
        }
      }
    }

    @Order(200)
    public class CloseButton extends AbstractCloseButton {

    }

    @Order(10)
    public class KeyStroke extends AbstractKeyStroke {
      @Override
      protected String getConfiguredKeyStroke() {
        return "shift-f";
      }

      @Override
      protected void execAction() throws ProcessingException {
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
    }
  }
}
