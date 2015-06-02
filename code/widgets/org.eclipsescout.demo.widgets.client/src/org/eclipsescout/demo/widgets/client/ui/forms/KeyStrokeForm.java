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
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.KeyStrokeForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.KeyStrokeForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.KeyStrokeForm.MainBox.GroupBox.ChangeValueWithCtrlshiftcField;
import org.eclipsescout.demo.widgets.client.ui.forms.KeyStrokeForm.MainBox.GroupBox.CtrlshiftdForFocusField;
import org.eclipsescout.demo.widgets.client.ui.forms.KeyStrokeForm.MainBox.GroupBox.CtrlshiftsForFocusField;

public class KeyStrokeForm extends AbstractForm implements IPageForm {

  public KeyStrokeForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("KeyStroke");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public ChangeValueWithCtrlshiftcField getChangeValueWithCtrlshiftcField() {
    return getFieldByClass(ChangeValueWithCtrlshiftcField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public CtrlshiftdForFocusField getCtrlshiftdForFocusField() {
    return getFieldByClass(CtrlshiftdForFocusField.class);
  }

  public CtrlshiftsForFocusField getCtrlshiftsForFocusField() {
    return getFieldByClass(CtrlshiftsForFocusField.class);
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
      public class CtrlshiftsForFocusField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CtrlshiftsForFocus");
        }
      }

      @Order(15.0)
      public class CtrlshiftdForFocusField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CtrlshiftdForFocus");
        }
      }

      @Order(20.0)
      public class ChangeValueWithCtrlshiftcField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ChangeValueWithCtrlshiftc");
        }
      }
    }

    @Order(20.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(10.0)
    public class CtrlshiftcKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "control-shift-c";
      }

      @Override
      protected void execAction() throws ProcessingException {
        getChangeValueWithCtrlshiftcField().setValue(!getChangeValueWithCtrlshiftcField().getValue());
      }
    }

    @Order(20.0)
    public class CtrlshiftdKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "control-shift-d";
      }

      @Override
      protected void execAction() throws ProcessingException {
        getCtrlshiftdForFocusField().requestFocus();
      }
    }

    @Order(30.0)
    public class CtrlshiftsKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "control-shift-s";
      }

      @Override
      protected void execAction() throws ProcessingException {
        getCtrlshiftsForFocusField().requestFocus();
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
    }
  }
}
