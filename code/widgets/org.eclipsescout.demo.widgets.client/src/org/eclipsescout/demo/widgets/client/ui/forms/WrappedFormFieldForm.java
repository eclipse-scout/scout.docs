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
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.wrappedform.AbstractWrappedFormField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.FormLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.InnerFormsField;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox.WrappedFormField;

public class WrappedFormFieldForm extends AbstractForm implements IPageForm {

  public WrappedFormFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("WrappedFormField");
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

  public InnerFormsField getInnerFormsField() {
    return getFieldByClass(InnerFormsField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public WrappedFormField getWrappedFormField() {
    return getFieldByClass(WrappedFormField.class);
  }

  public WrappedFormFieldBox getWrappedFormFieldBox() {
    return getFieldByClass(WrappedFormFieldBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class InnerFormsField extends AbstractSmartField<AbstractForm> {

        @Override
        protected int getConfiguredGridW() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InnerForms");
        }

        @Override
        protected Class<? extends LookupCall> getConfiguredLookupCall() {
          return FormLookupCall.class;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getWrappedFormField().setInnerForm(getValue());
        }
      }
    }

    @Order(30.0)
    public class WrappedFormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return "empty";
      }

      @Order(10.0)
      public class WrappedFormField extends AbstractWrappedFormField<AbstractForm> {

        @Override
        protected int getConfiguredGridW() {
          return 3;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setInnerForm(new ImageFieldForm());
        }
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CloseButton");
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
