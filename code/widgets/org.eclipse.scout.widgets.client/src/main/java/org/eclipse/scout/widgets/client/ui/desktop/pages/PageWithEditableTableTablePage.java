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

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

@ClassId("37e9a239-4f6a-4e74-8651-4c5df1ca8242")
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
  @ClassId("10a53ec2-c850-43d4-9fbc-22cfbf0607e9")
  public class Table extends PageWithTableTablePage.Table {

    @Override
    protected void execInitTable() {
      super.execInitTable();
      for (IColumn column : getColumns()) {
        column.setEditable(true);
      }
    }

    @Order(20)
    @ClassId("45b2fb23-c79d-457f-bed2-2518475e275d")
    public class ToggleMandatoryMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ToggleMandatory");
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
  @ClassId("991ec167-a118-4fb5-acd6-8896278014cd")
  public class ViewSourceOnGitHubMenu extends PageWithTableTablePage.ViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithEditableTableTablePage.class;
    }
  }
}
