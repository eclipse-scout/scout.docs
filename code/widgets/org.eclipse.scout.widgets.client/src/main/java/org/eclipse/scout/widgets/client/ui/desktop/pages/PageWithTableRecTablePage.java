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

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("321ea8cf-836e-4879-bba1-9c32c25b3e20")
public class PageWithTableRecTablePage extends PageWithTableTablePage {

  public PageWithTableRecTablePage() {
    super();
  }

  public PageWithTableRecTablePage(String name) {
    super(name);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithTable") + " (recursive)";
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return false;
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new PageWithTableRecTablePage();
  }

  @Override
  @SuppressWarnings({"ManualArrayCopy", "pmd:AvoidArrayLoops"})
  protected Object[][] loadDemoData() {
    // Insert outline column value (some pseudo-random ID)
    Object[][] data = super.loadDemoData();
    Object[][] newData = new Object[data.length][];
    for (int i = 0; i < data.length; i++) {
      Object[] row = data[i];
      Object[] newRow = new Object[row.length + 1];
      newRow[0] = createOutlineSummary((String)row[0]);
      for (int j = 0; j < row.length; j++) {
        newRow[j + 1] = row[j];
      }
      newData[i] = newRow;
    }
    return newData;
  }

  protected String createOutlineSummary(String str) {
    return "#" + new BigInteger(30, m_random).toString(32).toUpperCase() + " (" + str + ")";
  }

  @Override
  protected List<IMenu> execComputeParentTablePageMenus(IPageWithTable<?> parentTablePage) {
    return Collections.emptyList();
  }

  @Replace
  @ClassId("eb9eb561-4765-4fb7-bc4a-05e4a4446352")
  public class TableEx extends PageWithTableTablePage.Table {

    public OutlineColumn getOutlineColumn() {
      return getColumnSet().getColumnByClass(OutlineColumn.class);
    }

    @Order(5)
    @ClassId("0c6b078f-30bc-45b4-a120-b2ec13e425e3")
    public class OutlineColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredSummary() {
        return true;
      }
    }

    @Override
    protected ITableRow createNewRow() {
      ITableRow row = super.createNewRow();
      getOutlineColumn().setValue(row, createOutlineSummary(getStringColumn().getValue(row)));
      return row;
    }
  }
}
