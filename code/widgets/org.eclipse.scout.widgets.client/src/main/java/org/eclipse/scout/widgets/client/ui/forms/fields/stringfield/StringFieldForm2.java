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
package org.eclipse.scout.widgets.client.ui.forms.fields.stringfield;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.fields.basicfield.AbstractBasicFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.stringfield.StringFieldForm2.MainBox.BasicFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.stringfield.StringFieldForm2.MainBox.FormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.stringfield.StringFieldForm2.MainBox.GroupBox.StringField;
import org.eclipse.scout.widgets.client.ui.forms.fields.stringfield.StringFieldForm2.MainBox.ValueFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.valuefield.AbstractValueFieldPropertiesBox;

/**
 * Replacement for StringFieldForm. This is work in progress and will be moved to the open source widgets app
 * if the properties boxes cover all the features of the fields. Do not introduce messy code used to test
 * bugs, tickets or other test-cases.
 */
@ClassId("b5e653b7-fa81-4eca-af66-a1261e972be2")
public class StringFieldForm2 extends AbstractForm implements IPageForm {

  @Override
  protected String getConfiguredTitle() {
    return "String Field (new)";
  }

  @Override
  protected void execInitForm() {
    AbstractStringField field = getStringField();
    getFieldByClass(FormFieldPropertiesBox.class).setField(field);
    getFieldByClass(ValueFieldPropertiesBox.class).setField(field);
    getFieldByClass(BasicFieldPropertiesBox.class).setField(field);
  }

  protected StringField getStringField() {
    return getFieldByClass(StringField.class);
  }

  @Order(100)
  @ClassId("81857d66-aef2-4258-a9d7-2599c48cd97e")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("b23bbcea-c4eb-4633-bc66-97ec23e50278")
    public class GroupBox extends AbstractGroupBox {

      @ClassId("21ab52e4-8c7e-44ec-9058-cb9a40a559eb")
      public class StringField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "String Field";
        }
      }
    }

    @Order(200)
    @ClassId("c341b1d1-02ce-4856-807f-f2adec0bd54d")
    public class StringFieldPropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "String Field Properties";
      }

      @Override
      protected boolean getConfiguredExpandable() {
        return true;
      }
    }

    @Order(300)
    @ClassId("2f7a6ca3-676a-40e5-b3e0-f786a4b615d4")
    public class BasicFieldPropertiesBox extends AbstractBasicFieldPropertiesBox<String> {

      @Override
      protected void execInitField() {
        setField(getStringField());
      }
    }

    @Order(400)
    @ClassId("26db0821-2b1b-41b0-8021-159e0d9a432c")
    public class ValueFieldPropertiesBox extends AbstractValueFieldPropertiesBox<String> {

      @Override
      protected void execInitField() {
        setField(getStringField());
      }
    }

    @ClassId("bbebdf5d-4744-4735-ad77-c87ed081b0fe")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getStringField());
      }
    }
  }

  @Order(200)
  @ClassId("0ede6335-0f24-4336-bdae-3a4fdac244d6")
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
