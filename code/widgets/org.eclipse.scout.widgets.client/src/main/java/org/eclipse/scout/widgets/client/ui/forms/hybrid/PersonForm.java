/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.hybrid;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.dataobject.mapping.AbstractDoEntityMapper;
import org.eclipse.scout.rt.dataobject.mapping.DoEntityMappings;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.PersonForm.MainBox.CancelButton;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.PersonForm.MainBox.GroupBox.ActiveField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.PersonForm.MainBox.GroupBox.DateOfBirthField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.PersonForm.MainBox.GroupBox.FirstNameField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.PersonForm.MainBox.GroupBox.NameField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.PersonForm.MainBox.OkButton;

@ClassId("77aa7383-7c57-4a45-84c5-1a98531204c5")
public class PersonForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return "Person";
  }

  public void exportData(PersonDo personDo) {
    BEANS.get(PersonFormMapper.class).toDo(this, personDo);
  }

  public void importData(PersonDo personDo) {
    BEANS.get(PersonFormMapper.class).fromDo(personDo, this);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public DateOfBirthField getDateOfBirthField() {
    return getFieldByClass(DateOfBirthField.class);
  }

  public ActiveField getActiveField() {
    return getFieldByClass(ActiveField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  @Order(10)
  @ClassId("f8325347-2ba9-4fe1-8798-2e5dc37d9715")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("c7fbc360-e916-44f8-99dc-9b26a0ea81c5")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("8123b120-a7b3-4b19-8372-38b03952e45c")
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "Name";
        }
      }

      @Order(20)
      @ClassId("be4a35ef-029e-411a-9c7a-d770b368c809")
      public class FirstNameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return "First Name";
        }
      }

      @Order(30)
      @ClassId("fd0d57d6-8aac-40ca-a1ed-fbe59c2761b5")
      public class DateOfBirthField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return "Date of Birth";
        }
      }

      @Order(40)
      @ClassId("36994f1c-8fcb-4ee1-91cf-78f31b9dcabd")
      public class ActiveField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Active";
        }
      }
    }

    @Order(1000)
    @ClassId("1cc5b3e4-a7d3-442b-9637-8106d61cb799")
    public class OkButton extends AbstractOkButton {
    }

    @Order(2000)
    @ClassId("60684101-4aa7-4f12-af8e-b8893d6d0af9")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  @ApplicationScoped
  public static class PersonFormMapper extends AbstractDoEntityMapper<PersonDo, PersonForm> {
    @Override
    protected void initMappings(DoEntityMappings<PersonDo, PersonForm> mappings) {
      mappings
          .withHolder(PersonDo::name, PersonForm::getNameField)
          .withHolder(PersonDo::firstName, PersonForm::getFirstNameField)
          .withHolder(PersonDo::dateOfBirth, PersonForm::getDateOfBirthField)
          .withHolder(PersonDo::active, PersonForm::getActiveField);
    }
  }
}
