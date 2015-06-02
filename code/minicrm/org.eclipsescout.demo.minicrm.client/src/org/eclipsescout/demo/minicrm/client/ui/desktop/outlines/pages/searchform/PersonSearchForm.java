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
package org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform;

import java.math.BigDecimal;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.ResetButton;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.SearchButton;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.TabBox;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.TabBox.FieldBox;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.TabBox.FieldBox.EmployerField;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.TabBox.FieldBox.EmployerTypeField;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.TabBox.FieldBox.FirstNameField;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm.MainBox.TabBox.FieldBox.LastNameField;
import org.eclipsescout.demo.minicrm.shared.services.code.CompanyTypeCodeType;
import org.eclipsescout.demo.minicrm.shared.services.lookup.CompanyLookupCall;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.outlines.pages.searchform.PersonSearchFormData;

@FormData(value = PersonSearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class PersonSearchForm extends AbstractSearchForm {

  private Long m_companyNr;

  public PersonSearchForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) throws ProcessingException {
    super.execResetSearchFilter(searchFilter);
    PersonSearchFormData formData = new PersonSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @FormData
  public Long getCompanyNr() {
    return m_companyNr;
  }

  @FormData
  public void setCompanyNr(Long companyNr) {
    m_companyNr = companyNr;
  }

  @Override
  public void startSearch() throws ProcessingException {
    startInternal(new SearchHandler());
  }

  public EmployerField getEmployerField() {
    return getFieldByClass(EmployerField.class);
  }

  public EmployerTypeField getEmployerTypeField() {
    return getFieldByClass(EmployerTypeField.class);
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

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(10.0)
        public class LastNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LastName");
          }
        }

        @Order(20.0)
        public class FirstNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FirstName");
          }
        }

        @Order(30.0)
        public class EmployerTypeField extends AbstractSmartField<Long> {

          @Override
          protected Class<? extends ICodeType<?>> getConfiguredCodeType() {
            return CompanyTypeCodeType.class;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("EmployerType");
          }
        }

        @Order(40.0)
        public class EmployerField extends AbstractSmartField<BigDecimal> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Employer");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return CompanyLookupCall.class;

          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return EmployerTypeField.class;
          }
        }
      }
    }

    @Order(20.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(30.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      if (m_companyNr != null) {
        getEmployerField().setValue(BigDecimal.valueOf(m_companyNr));
        getEmployerField().setEnabled(false);
        getEmployerTypeField().setVisible(false);
      }
    }
  }
}
