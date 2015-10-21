/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.event.client.contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.event.client.contact.ContactChooserForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.event.client.contact.ContactChooserForm.MainBox.ContactBox;
import org.eclipse.scout.contacts.event.client.contact.ContactChooserForm.MainBox.ContactBox.ContactField;
import org.eclipse.scout.contacts.event.client.contact.ContactChooserForm.MainBox.OkButton;
import org.eclipse.scout.contacts.shared.contact.ContactLookupCall;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

public class ContactChooserForm extends AbstractForm {

  private List<String> m_filteredContacts = new ArrayList<String>();

  public ContactChooserForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ChooseContact");
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ContactBox getContactBox() {
    return getFieldByClass(ContactBox.class);
  }

  public ContactField getContactField() {
    return getFieldByClass(ContactField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(1_000.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(1_000.0)
    public class ContactBox extends AbstractGroupBox {

      @Order(1_000.0)
      public class ContactField extends AbstractSmartField<String> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return ContactLookupCall.class;
        }

        @Override
        protected void execFilterLookupResult(ILookupCall<String> call, List<ILookupRow<String>> result) throws ProcessingException {
          Iterator<ILookupRow<String>> iterator = result.iterator();
          while (iterator.hasNext()) {
            ILookupRow<String> row = iterator.next();
            if (m_filteredContacts.contains(row.getKey())) {
              iterator.remove();
            }
          }
        }
      }
    }

    @Order(100_000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101_000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }

  public void setFilteredContacts(List<String> filteredContacts) {
    m_filteredContacts = filteredContacts;
  }
}
