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

import java.math.BigInteger;

import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.shared.TEXTS;

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
  protected Object[][] loadDemoData() {
    // Insert outline column value (some peusdo-random ID)
    Object[][] data = super.loadDemoData();
    Object[][] newData = new Object[data.length][];
    for (int i = 0; i < data.length; i++) {
      Object[] row = data[i];
      Object[] newRow = new Object[row.length + 1];
      newRow[0] = "#" + new BigInteger(30, m_random).toString(32).toUpperCase() + " (" + row[0] + ")";
      for (int j = 0; j < row.length; j++) {
        newRow[j + 1] = row[j];
      }
      newData[i] = newRow;
    }
    return newData;
  }

  @Replace
  public class Table extends PageWithTableTablePage.Table {

    @Order(5)
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
  }
}
