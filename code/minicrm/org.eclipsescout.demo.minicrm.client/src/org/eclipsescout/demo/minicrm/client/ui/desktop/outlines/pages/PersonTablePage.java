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
package org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.PersonTablePage.Table;
import org.eclipsescout.demo.minicrm.client.ui.desktop.outlines.pages.searchform.PersonSearchForm;
import org.eclipsescout.demo.minicrm.shared.Icons;
import org.eclipsescout.demo.minicrm.shared.services.IStandardOutlineService;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.outlines.pages.PersonTablePageData;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.outlines.pages.searchform.PersonSearchFormData;

@PageData(PersonTablePageData.class)
public class PersonTablePage extends AbstractPageWithTable<Table> {

  private Long m_companyNr;

  @Override
  protected String getConfiguredIconId() {
    return Icons.User;
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return PersonSearchForm.class;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  @Override
  protected void execInitSearchForm() throws ProcessingException {
    ((PersonSearchForm) getSearchFormInternal()).setCompanyNr(m_companyNr);
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    PersonSearchFormData formData = (PersonSearchFormData) filter.getFormData();
    if (formData == null) {
      formData = new PersonSearchFormData();
    }

    PersonTablePageData pageData = SERVICES.getService(IStandardOutlineService.class).getPersonTableData(formData);
    importPageData(pageData);
  }

  @FormData
  public Long getCompanyNr() {
    return m_companyNr;
  }

  @FormData
  public void setCompanyNr(Long companyNr) {
    m_companyNr = companyNr;
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.User;
    }

    public FirstNameColumn getFirstNameColumn() {
      return getColumnSet().getColumnByClass(FirstNameColumn.class);
    }

    public LastNameColumn getLastNameColumn() {
      return getColumnSet().getColumnByClass(LastNameColumn.class);
    }

    public PersonNrColumn getPersonNrColumn() {
      return getColumnSet().getColumnByClass(PersonNrColumn.class);
    }

    @Order(10.0)
    public class PersonNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(20.0)
    public class LastNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("LastName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(30.0)
    public class FirstNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }
  }
}
