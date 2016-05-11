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
package org.eclipse.scout.contacts.events.client.person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scout.contacts.events.client.person.PersonChooserForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.events.client.person.PersonChooserForm.MainBox.OkButton;
import org.eclipse.scout.contacts.events.client.person.PersonChooserForm.MainBox.PersonBox;
import org.eclipse.scout.contacts.events.client.person.PersonChooserForm.MainBox.PersonBox.PersonField;
import org.eclipse.scout.contacts.shared.person.PersonLookupCall;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

public class PersonChooserForm extends AbstractForm {

  private List<String> filteredPersons = new ArrayList<>();

  public PersonChooserForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ChoosePerson");
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public PersonBox getPersonBox() {
    return getFieldByClass(PersonBox.class);
  }

  public PersonField getPersonField() {
    return getFieldByClass(PersonField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(1)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(1)
    public class PersonBox extends AbstractGroupBox {

      @Order(1)
      public class PersonField extends AbstractSmartField<String> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return PersonLookupCall.class;
        }

        @Override
        protected void execFilterLookupResult(ILookupCall<String> call, List<ILookupRow<String>> result) {
          Iterator<ILookupRow<String>> iterator = result.iterator();
          while (iterator.hasNext()) {
            ILookupRow<String> row = iterator.next();
            if (filteredPersons.contains(row.getKey())) {
              iterator.remove();
            }
          }
        }
      }
    }

    @Order(100)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }

  public void setFilteredPersons(List<String> filteredPersons) {
    this.filteredPersons = filteredPersons;
  }
}
