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
package org.eclipse.scout.contacts.client.contact;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.ResetButton;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.SearchButton;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.TabBox;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.TabBox.FieldBox;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.TabBox.FieldBox.CompanyField;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.TabBox.FieldBox.FirstNameField;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.TabBox.FieldBox.LastNameField;
import org.eclipse.scout.contacts.client.contact.ContactsSearchForm.MainBox.TabBox.FieldBox.Location;
import org.eclipse.scout.contacts.client.template.AbstractLocationBox;
import org.eclipse.scout.contacts.shared.company.CompanyLookupCall;
import org.eclipse.scout.contacts.shared.contact.ContactsSearchFormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ContactsSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ContactsSearchForm extends AbstractSearchForm {

  public ContactsSearchForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Contacts");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) throws ProcessingException {
    super.execResetSearchFilter(searchFilter);
    ContactsSearchFormData formData = new ContactsSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @Override
  public void start() throws ProcessingException {
    startInternal(new SearchHandler());
  }

  public CompanyField getCompanyField() {
    return getFieldByClass(CompanyField.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public LastNameField getLastNameField() {
    return getFieldByClass(LastNameField.class);
  }

  public Location getLocationBox() {
    return getFieldByClass(Location.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class TabBox extends AbstractTabBox {

      @Order(1000.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(1000.0)
        public class FirstNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FirstName");
          }
        }

        @Order(2000.0)
        public class LastNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LastName");
          }
        }

        @Order(3125.0)
        public class Location extends AbstractLocationBox {
        }

        @Order(3750.0)
        public class CompanyField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Company");
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return CompanyLookupCall.class;
          }
        }
      }
    }

    @Order(8000.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(9000.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
