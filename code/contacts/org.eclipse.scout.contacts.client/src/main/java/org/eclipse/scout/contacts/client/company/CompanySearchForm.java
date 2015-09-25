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
package org.eclipse.scout.contacts.client.company;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.ResetButton;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.SearchButton;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.TabBox;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.TabBox.FieldBox;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.TabBox.FieldBox.HomepageField;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.TabBox.FieldBox.Location;
import org.eclipse.scout.contacts.client.company.CompanySearchForm.MainBox.TabBox.FieldBox.NameField;
import org.eclipse.scout.contacts.client.template.AbstractLocationBox;
import org.eclipse.scout.contacts.shared.company.CompanySearchFormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@FormData(value = CompanySearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CompanySearchForm extends AbstractSearchForm {

  public CompanySearchForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) throws ProcessingException {
    super.execResetSearchFilter(searchFilter);
    CompanySearchFormData formData = new CompanySearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @Override
  public void start() throws ProcessingException {
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

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class TabBox extends AbstractTabBox {

      @Order(1000.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SearchCriteria");
        }

        @Order(1000.0)
        public class NameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Name");
          }
        }

        @Order(2000.0)
        public class HomepageField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Homepage");
          }
        }

        @Order(4000.0)
        public class Location extends AbstractLocationBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Location");
          }
        }
      }
    }

    @Order(2000.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(3000.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
