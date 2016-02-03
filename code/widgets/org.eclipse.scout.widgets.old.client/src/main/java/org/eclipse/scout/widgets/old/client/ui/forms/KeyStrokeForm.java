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

import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.KeyStrokeForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.KeyStrokeForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.old.client.ui.forms.KeyStrokeForm.MainBox.GroupBox.ChangeValueWithCtrlshiftcField;
import org.eclipse.scout.widgets.old.client.ui.forms.KeyStrokeForm.MainBox.GroupBox.CtrlshiftdForFocusField;
import org.eclipse.scout.widgets.old.client.ui.forms.KeyStrokeForm.MainBox.GroupBox.CtrlshiftsForFocusField;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

public class KeyStrokeForm extends AbstractForm implements IPageForm {

  public KeyStrokeForm() {
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
  public void startPageForm() {
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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class CtrlshiftsForFocusField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CtrlshiftsForFocus");
        }
      }

      @Order(15)
      public class CtrlshiftdForFocusField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CtrlshiftdForFocus");
        }
      }

      @Order(20)
      public class ChangeValueWithCtrlshiftcField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ChangeValueWithCtrlshiftc");
        }
      }
    }

    @Order(20)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(10)
    public class CtrlshiftcKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "control-shift-c";
      }

      @Override
      protected void execAction() {
        getChangeValueWithCtrlshiftcField().setValue(!getChangeValueWithCtrlshiftcField().getValue());
      }
    }

    @Order(20)
    public class CtrlshiftdKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "control-shift-d";
      }

      @Override
      protected void execAction() {
        getCtrlshiftdForFocusField().requestFocus();
      }
    }

    @Order(30)
    public class CtrlshiftsKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "control-shift-s";
      }

      @Override
      protected void execAction() {
        getCtrlshiftsForFocusField().requestFocus();
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }
  }
}
