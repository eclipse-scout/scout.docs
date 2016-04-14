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
package org.eclipse.scout.widgets.old.client.ui.desktop.pages;

import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm;

public class SearchFormTablePage extends AbstractPageWithTable<SearchFormTablePage.Table> {
  private String m_displayViewId;

  public SearchFormTablePage(String displayViewId, String displayViewName) {
    super();
    m_displayViewId = displayViewId;
    getCellForUpdate().setText(displayViewName);
  }

  public SearchFormTablePage() {
    super();
    getCellForUpdate().setText("Default");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return SearchForm.class;
  }

  @Override
  protected void execInitSearchForm() {
    if (m_displayViewId != null) {
      getSearchFormInternal().setDisplayViewId(m_displayViewId);
    }
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importTableData(new Object[][]{{0L, "Administrator", "RWX"}, {1L, "Manager", "RX"}, {2L, "Employer", "R"}, {3L, "External", ""}});
  }

  @Override
  protected void execPageActivated() {
    setSearchActive(true);
    IMenu firstMenu = CollectionUtility.firstElement(ClientSession.get().getDesktop().getMenus());
    if (firstMenu != null) {
      if (m_displayViewId == ISearchForm.VIEW_ID_NE || m_displayViewId == ISearchForm.VIEW_ID_SE) {
        firstMenu.setSelected(true);
        firstMenu.setToggleAction(true);
        firstMenu.doAction();
      }
      else {
        firstMenu.setSelected(false);
        firstMenu.setToggleAction(false);
        firstMenu.doAction();
      }
    }
  }

  public class Table extends AbstractTable {

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    public PermissionColumn getPermissionColumn() {
      return getColumnSet().getColumnByClass(PermissionColumn.class);
    }

    public RoleColumn getRoleColumn() {
      return getColumnSet().getColumnByClass(RoleColumn.class);
    }

    public RoleNrColumn getRoleNrColumn() {
      return getColumnSet().getColumnByClass(RoleNrColumn.class);
    }

    @Order(10)
    public class RoleNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }

    @Order(20)
    public class RoleColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Role");
      }
    }

    @Order(30)
    public class PermissionColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Permission");
      }
    }
  }
}
