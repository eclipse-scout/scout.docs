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
package org.eclipse.scout.contacts.client.person;

import org.eclipse.scout.contacts.client.common.AbstractAddressBox;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.ResetButton;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.SearchButton;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.TabBox;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.TabBox.FieldBox;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.TabBox.FieldBox.FirstNameField;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.TabBox.FieldBox.LastNameField;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.TabBox.FieldBox.Location;
import org.eclipse.scout.contacts.client.person.PersonSearchForm.MainBox.TabBox.FieldBox.OrganizationField;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.PersonSearchFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = PersonSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PersonSearchForm extends AbstractSearchForm {

  public PersonSearchForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Persons");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) {
    super.execResetSearchFilter(searchFilter);
    PersonSearchFormData formData = new PersonSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @Override
  public void start() {
    startInternal(new SearchHandler());
  }

  public OrganizationField getOrganizationField() {
    return getFieldByClass(OrganizationField.class);
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

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    public class TabBox extends AbstractTabBox {

      @Order(1000)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SearchCriteria");
        }

        @Order(1000)
        public class FirstNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FirstName");
          }
        }

        @Order(2000)
        public class LastNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LastName");
          }
        }

        @Order(3125)
        public class Location extends AbstractAddressBox {

          @Override
          protected int getConfiguredGridH() {
            return 1;
          }

          @Override
          protected void execInitField() {
            getStreetField().setVisible(false);
            getShowOnMapButton().setVisible(false);
          }

          @Override
          protected void verifyAllFields() {
            // no verification required for search
          }
        }

        @Order(3750)
        public class OrganizationField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Organization");
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return OrganizationLookupCall.class;
          }
        }
      }
    }

    @Order(8000)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(9000)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
