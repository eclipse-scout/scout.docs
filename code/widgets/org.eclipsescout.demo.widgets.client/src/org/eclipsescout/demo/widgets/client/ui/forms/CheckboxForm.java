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
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.ValidationFailedStatus;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.GroupBox.CheckboxField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.GroupBox.SequenceBox;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.GroupBox.SequenceBox.EnabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.GroupBox.SequenceBox.HasToBeCheckedField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm.MainBox.GroupBox.SequenceBox.MandatoryField;

public class CheckboxForm extends AbstractForm implements IPageForm {

  public CheckboxForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Checkbox");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public CheckboxField getCheckboxField() {
    return getFieldByClass(CheckboxField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public EnabledField getEnabledField() {
    return getFieldByClass(EnabledField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public HasToBeCheckedField getHasToBeCheckedField() {
    return getFieldByClass(HasToBeCheckedField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public SequenceBox getSequenceBox() {
    return getFieldByClass(SequenceBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class SequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return 0;
        }

        @Order(10.0)
        public class EnabledField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Enabled");
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getCheckboxField().setEnabled(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            this.setValue(true);
          }
        }

        @Order(20.0)
        public class MandatoryField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mandatory");
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getCheckboxField().setMandatory(getValue());
          }
        }

        @Order(30.0)
        public class HasToBeCheckedField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("HasToBeChecked");
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            if (getValue()) {
              if (!getCheckboxField().getValue()) {
                getCheckboxField().setErrorStatus(new ValidationFailedStatus("Field has to be checked"));
              }
            }
            else {
              getCheckboxField().clearErrorStatus();
            }
          }
        }
      }

      @Order(20.0)
      public class CheckboxField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Checkbox");
        }

        @Override
        protected Boolean execValidateValue(Boolean rawValue) throws ProcessingException {
          if (getHasToBeCheckedField().getValue() && rawValue != null && !rawValue) {
            throw new VetoException("Field has to be checked");
          }
          return rawValue;
        }
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
