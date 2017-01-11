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

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;

public class PageWithEditableTableTablePage extends PageWithTableTablePage {

  public PageWithEditableTableTablePage() {
    super();
  }

  public PageWithEditableTableTablePage(String name) {
    super(name);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithTable") + " (editable)";
  }

  @Replace
  public class Table extends PageWithTableTablePage.Table {

    @Override
    protected void execInitTable() {
      super.execInitTable();
      for (IColumn column : getColumns()) {
        column.setEditable(true);
      }
    }

    @Order(20)
    public class ToggleMandatoryMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.SingleSelection,
            TableMenuType.MultiSelection,
            TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Mandatory");
      }

      @Override
      protected void execAction() {
        List<IColumn<?>> columns = getTable().getColumns();
        for (IColumn<?> c : columns) {
          c.setMandatory(!c.isMandatory());
        }
      }
    }
  }

  @Order(10)
  @Replace
  public class ViewSourceOnGitHubMenu extends PageWithTableTablePage.ViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithEditableTableTablePage.class;
    }
  }
}
