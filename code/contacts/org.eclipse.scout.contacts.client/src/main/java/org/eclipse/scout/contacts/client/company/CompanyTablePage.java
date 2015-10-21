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

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.shared.company.CompanyTablePageData;
import org.eclipse.scout.contacts.shared.company.ICompanyService;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@PageData(CompanyTablePageData.class)
public class CompanyTablePage extends AbstractPageWithTable<CompanyTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) throws ProcessingException {
    CompanyDetailsNodePage childPage = new CompanyDetailsNodePage();
    childPage.setCompanyId(getTable().getCompanyIdColumn().getValue(row));
    return childPage;
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    importPageData(BEANS.get(ICompanyService.class).getCompanyTableData(filter));
  }

  @Order(1000.0)
  public class Table extends AbstractTable {

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    public HomepageColumn getHomepageColumn() {
      return getColumnSet().getColumnByClass(HomepageColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Company;
    }

    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    public CompanyIdColumn getCompanyIdColumn() {
      return getColumnSet().getColumnByClass(CompanyIdColumn.class);
    }

    @Order(1000.0)
    public class CompanyIdColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(2000.0)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(3000.0)
    public class CityColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("City");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(4000.0)
    public class CountryColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }
    }

    @Order(5000.0)
    public class HomepageColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Homepage");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(1000.0)
    public class EditMenu extends AbstractMenu {

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-e";
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected void execAction() throws ProcessingException {
        CompanyForm form = new CompanyForm();
        form.setCompanyId(getCompanyIdColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(2000.0)
    public class NewMenu extends AbstractMenu {

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-n";
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Override
      protected void execAction() throws ProcessingException {
        CompanyForm form = new CompanyForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.Star;
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return CompanySearchForm.class;
  }
}
