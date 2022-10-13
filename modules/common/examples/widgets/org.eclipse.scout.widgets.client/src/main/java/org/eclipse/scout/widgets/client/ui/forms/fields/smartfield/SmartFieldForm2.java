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
package org.eclipse.scout.widgets.client.ui.forms.fields.smartfield;

import java.util.Locale;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.smartfield.SmartFieldForm2.MainBox.FormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.smartfield.SmartFieldForm2.MainBox.GroupBox.SmartField;
import org.eclipse.scout.widgets.client.ui.forms.fields.smartfield.SmartFieldForm2.MainBox.SmartFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.smartfield.SmartFieldForm2.MainBox.ValueFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.valuefield.AbstractValueFieldPropertiesBox;

/**
 * Replacement for SmartFieldForm2. This is work in progress and will be moved to the open source widgets app
 * if the properties boxes cover all the features of the fields. Do not introduce messy code used to test
 * bugs, tickets or other test-cases.
 */
@ClassId("a36b2d66-7b61-4a5f-8b07-7c828505f63c")
public class SmartFieldForm2 extends AbstractForm implements IPageForm {

  @Override
  protected String getConfiguredTitle() {
    return "Smart Field (new)";
  }

  @Override
  protected void execInitForm() {
    SmartField field = getSmartField();
    getFieldByClass(SmartFieldPropertiesBox.class).setField(field);
    getFieldByClass(FormFieldPropertiesBox.class).setField(field);
    getFieldByClass(ValueFieldPropertiesBox.class).setField(field);
  }

  protected SmartField getSmartField() {
    return getFieldByClass(SmartField.class);
  }

  @Order(100)
  @ClassId("0e4820f5-1558-4bc7-8f47-48a3ea309a0d")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("d556ea81-6d0a-4ec0-ae8f-a8659f6bf846")
    public class GroupBox extends AbstractGroupBox {

      @ClassId("f308516f-ae39-45c8-b723-cb393e13fff6")
      public class SmartField extends AbstractSmartField<Locale> {

        @Override
        protected String getConfiguredLabel() {
          return "Smart Field";
        }

        @Override
        protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
          return LocaleLookupCall.class;
        }
      }
    }

    @Order(200)
    @ClassId("26514276-3547-4c94-b0a1-fd11f13ab99d")
    public class SmartFieldPropertiesBox extends AbstractSmartFieldPropertiesBox<Locale> {

      @Override
      protected void execInitField() {
        setField(getSmartField());
      }
    }

    @Order(300)
    @ClassId("7b70b52a-5c7d-4595-af2e-b8802c58e68d")
    public class ValueFieldPropertiesBox extends AbstractValueFieldPropertiesBox<Locale> {

      @Override
      protected void execInitField() {
        setField(getSmartField());
      }
    }

    @Order(400)
    @ClassId("0c4496d0-1383-4c89-b302-96f6fbba4898")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getSmartField());
      }
    }
  }

  @Order(200)
  @ClassId("3f92861f-ca3a-430f-9b87-4396e24655cd")
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
