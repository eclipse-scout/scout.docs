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
package org.eclipsescout.demo.widgets.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipsescout.demo.widgets.client.ClientSession;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm;

public class SearchFormTablePage extends AbstractPageWithTable<SearchFormTablePage.Table> {

  private String m_displayViewId;

  public SearchFormTablePage() {
    super();
    getCellForUpdate().setText("Default");
  }

  public SearchFormTablePage(String displayViewId, String displayViewName) {
    super();
    m_displayViewId = displayViewId;
    getCellForUpdate().setText(displayViewName);
  }

  @Override
  protected String getConfiguredIconId() {
    return org.eclipse.scout.rt.shared.AbstractIcons.SmartFieldBrowse;
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
  protected void execInitSearchForm() throws ProcessingException {
    if (m_displayViewId != null) {
      getSearchFormInternal().setDisplayViewId(m_displayViewId);
    }
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return new Object[][]{{0L, "Administrator", "RWX"}, {1L, "Manager", "RX"}, {2L, "Employer", "R"}, {3L, "External", ""}};
  }

  @Override
  protected void execPageActivated() throws ProcessingException {
    setSearchActive(true);
    if (m_displayViewId == ISearchForm.VIEW_ID_NE || m_displayViewId == ISearchForm.VIEW_ID_SE) {
      ClientSession.get().getDesktop().getToolButtons()[0].setSelected(true);
      ClientSession.get().getDesktop().getToolButtons()[0].setToggleAction(true);
      ClientSession.get().getDesktop().getToolButtons()[0].doAction();
    }
    else {
      ClientSession.get().getDesktop().getToolButtons()[0].setSelected(false);
      ClientSession.get().getDesktop().getToolButtons()[0].setToggleAction(false);
      ClientSession.get().getDesktop().getToolButtons()[0].doAction();
    }
  }

  @Order(10.0)
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

    @Order(10.0)
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

    @Order(20.0)
    public class RoleColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Role");
      }
    }

    @Order(30.0)
    public class PermissionColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Permission");
      }
    }
  }
}
