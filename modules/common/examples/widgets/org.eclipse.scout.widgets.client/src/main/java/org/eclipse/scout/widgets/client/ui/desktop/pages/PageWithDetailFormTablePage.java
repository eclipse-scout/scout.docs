/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithDetailFormTablePage.Table;
import org.eclipse.scout.widgets.client.ui.forms.DetailForm;

@ClassId("d02d5e1d-6e82-4ea7-b712-3cb2c58a0b38")
public class PageWithDetailFormTablePage extends AbstractPageWithTable<Table> {

  public PageWithDetailFormTablePage() {
    super(true, DetailForm.class.getName());
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return DetailForm.class;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithADetailform");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importTableData(new Object[][]{
        {1, "Exxon Mobil Corporation", "XOM", 1},
        {2, "IBM", "IBM", 2},
        {3, "UBS", "UBS", 3},
        {4, "Coca-Cola Company", "KO", 55}});
  }

  @ClassId("2de95ffa-6bb9-492a-b9c5-f1c07c963f78")
  public class Table extends AbstractTable {

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return false;
    }

    @Override
    protected boolean getConfiguredMultiSelect() {
      return false;
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public SymbolColumn getSymbolColumn() {
      return getColumnSet().getColumnByClass(SymbolColumn.class);
    }

    public NumberColumn getNumberColumn() {
      return getColumnSet().getColumnByClass(NumberColumn.class);
    }

    public CompanyNrColumn getCompanyNrColumn() {
      return getColumnSet().getColumnByClass(CompanyNrColumn.class);
    }

    @Order(10)
    @ClassId("d6b4687a-1361-45d6-af25-b1d0dc56dee2")
    public class CompanyNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(20)
    @ClassId("ae5b38af-9e0e-4976-bc79-9d792dfd48ff")
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(30)
    @ClassId("58fc5cff-c637-4c20-8856-a0ee57611884")
    public class SymbolColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Symbol");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(40)
    @ClassId("3ac70f1e-3280-4588-a329-fa57f346e6ae")
    public class NumberColumn extends AbstractIntegerColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("IntegerColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(10)
    @ClassId("3c928934-80c2-44f1-a94e-1db7cc8aeb1c")
    public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected Class<?> provideSourceClass() {
        return PageWithDetailFormTablePage.class;
      }
    }
  }
}
