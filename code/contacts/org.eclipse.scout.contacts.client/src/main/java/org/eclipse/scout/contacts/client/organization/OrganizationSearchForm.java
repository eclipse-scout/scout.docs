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
package org.eclipse.scout.contacts.client.organization;

import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.ResetButton;
import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.SearchButton;
import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.TabBox;
import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.TabBox.FieldBox;
import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.TabBox.FieldBox.HomepageField;
import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.TabBox.FieldBox.Location;
import org.eclipse.scout.contacts.client.organization.OrganizationSearchForm.MainBox.TabBox.FieldBox.NameField;
import org.eclipse.scout.contacts.client.template.AbstractLocationBox;
import org.eclipse.scout.contacts.shared.organization.OrganizationSearchFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@FormData(value = OrganizationSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OrganizationSearchForm extends AbstractSearchForm {

  public OrganizationSearchForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Organization");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) {
    super.execResetSearchFilter(searchFilter);
    OrganizationSearchFormData formData = new OrganizationSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @Override
  public void start() {
    startInternal(new SearchHandler());
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public HomepageField getHomepageField() {
    return getFieldByClass(HomepageField.class);
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
        public class NameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Name");
          }
        }

        @Order(2000)
        public class HomepageField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Homepage");
          }
        }

        @Order(4000)
        public class Location extends AbstractLocationBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Location");
          }
        }
      }
    }

    @Order(2000)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(3000)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
