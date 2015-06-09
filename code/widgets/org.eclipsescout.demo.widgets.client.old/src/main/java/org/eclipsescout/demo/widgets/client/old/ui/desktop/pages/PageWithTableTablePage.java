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
package org.eclipsescout.demo.widgets.client.old.ui.desktop.pages;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipsescout.demo.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;

public class PageWithTableTablePage extends AbstractPageWithTable<PageWithTableTablePage.Table> {

  public PageWithTableTablePage() {
    super();
  }

  public PageWithTableTablePage(String name) {
    super();
    getCellForUpdate().setText(name);
  }

  @Override
  protected String getConfiguredIconId() {
    return org.eclipse.scout.rt.shared.AbstractIcons.TreeNodeOpen;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithTable");
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    importTableData(new Object[][]{{"String 1", 1, 23, 9768.3, new Date(System.currentTimeMillis()), false, 2}, {"String 2", 2, 27, 10000.25, new Date(System.currentTimeMillis() + 86400000), true, 1}, {"String 3", 3, 20, 8031.7, new Date(System.currentTimeMillis() - 216000000), true, 3}});
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    public SmartColumn getSmartColumn() {
      return getColumnSet().getColumnByClass(SmartColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    public BooleanColumn getBooleanColumn() {
      return getColumnSet().getColumnByClass(BooleanColumn.class);
    }

    public DateColumn getDateColumn() {
      return getColumnSet().getColumnByClass(DateColumn.class);
    }

    public BigDecimalColumn getBigDecimalColumn() {
      return getColumnSet().getColumnByClass(BigDecimalColumn.class);
    }

    public IntegerColumn getIntegerColumn() {
      return getColumnSet().getColumnByClass(IntegerColumn.class);
    }

    public LongColumn getLongColumn() {
      return getColumnSet().getColumnByClass(LongColumn.class);
    }

    public StringColumn getStringColumn() {
      return getColumnSet().getColumnByClass(StringColumn.class);
    }

    @Order(10.0)
    public class StringColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("StringColumn");
      }
    }

    @Order(20.0)
    public class LongColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("LongColumn");
      }
    }

    @Order(30.0)
    public class IntegerColumn extends AbstractIntegerColumn {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("IntegerColumn");
      }
    }

    @Order(40.0)
    public class BigDecimalColumn extends AbstractBigDecimalColumn {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("BigDecimalColumn");
      }
    }

    @Order(50.0)
    public class DateColumn extends AbstractDateColumn {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("DateColumn");
      }
    }

    @Order(60.0)
    public class BooleanColumn extends AbstractBooleanColumn {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("BooleanColumn");
      }
    }

    @Order(70.0)
    public class SmartColumn extends AbstractSmartColumn<Long> {

      @Override
      protected boolean getConfiguredEditable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("SmartColumn");
      }

      @Override
      protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
        return CompanyTypeLookupCall.class;
      }
    }

    @Order(20.0)
    public class ToggleMandatoryMenu extends AbstractMenu {

      @Override
      protected void execInitAction() throws ProcessingException {
        super.execInitAction();
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.SingleSelection,
            TableMenuType.MultiSelection,
            TableMenuType.EmptySpace
            );
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Mandatory");
      }

      @Override
      protected void execAction() throws ProcessingException {
        List<IColumn<?>> columns = getTable().getColumns();
        for (IColumn<?> c : columns) {
          c.setMandatory(!c.isMandatory());
        }
      }

    }

  }

  @Order(10.0)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected void execInitAction() throws ProcessingException {
      super.execInitAction();
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(
          TableMenuType.SingleSelection,
          TableMenuType.MultiSelection,
          TableMenuType.EmptySpace
          );
    }

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithTableTablePage.class;
    }
  }
}
