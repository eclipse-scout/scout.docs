/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@ClassId("fcac7a9a-0566-4ce4-b03a-d84f7a9a6006")
@FormData(value = PersonSearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class PersonSearchForm extends AbstractSearchForm {

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
  @ClassId("d1aec0bf-d226-40b7-bebe-8e306d75f51e")
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    @ClassId("0a6c97bf-e6dc-4ba1-99aa-44de574c787d")
    public class TabBox extends AbstractTabBox {

      @Order(1000)
      @ClassId("af57b563-9364-43d6-a96c-b42c134326ce")
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SearchCriteria");
        }

        @Order(1000)
        @ClassId("d2b9010c-d3d0-4180-adc2-207226620eee")
        public class FirstNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FirstName");
          }
        }

        @Order(2000)
        @ClassId("ae755faf-72b7-4b8a-9b63-52de1e64e8a2")
        public class LastNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LastName");
          }
        }

        @Order(3125)
        @ClassId("99b367ce-c0fc-4055-8e3c-b6bda55af129")
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
        @ClassId("c782f468-8ff9-4a91-a23e-b5e06be5bc3e")
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
    @ClassId("7808b9dc-3f41-4ece-b4f0-6e084172dcac")
    public class ResetButton extends AbstractResetButton {
    }

    @Order(9000)
    @ClassId("1fdf54a5-bc8d-4ed3-a20f-d167e068332d")
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
