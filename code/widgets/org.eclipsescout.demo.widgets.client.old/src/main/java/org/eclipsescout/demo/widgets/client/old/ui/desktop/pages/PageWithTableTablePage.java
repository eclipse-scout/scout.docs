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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
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
import org.eclipse.scout.rt.client.ui.basic.table.columns.INumberColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.INumberColumn.AggregationFunction;
import org.eclipse.scout.rt.client.ui.basic.table.columns.INumberColumn.BackgroundEffect;
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
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithTable");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importTableData(new Object[][]{
        {"String 10", 1, 23, 9768.3, new Date(System.currentTimeMillis()), false, 2},
        {"String 11", 1, 23, 8768.3, new Date(System.currentTimeMillis()), false, 2},
        {"String 12", 1, 23, 7768.3, new Date(System.currentTimeMillis()), false, 2},
        {"String 13", 1, 23, 5768.3, new Date(System.currentTimeMillis()), false, 2},
        {"String 22", 2, 27, 13000.25, new Date(System.currentTimeMillis() + 86400000), true, 1},
        {"String 23", 2, 27, 12000.25, new Date(System.currentTimeMillis() + 46400000), true, 1},
        {"String 24", 2, 27, 11000.25, new Date(System.currentTimeMillis() + 56400000), true, 1},
        {"String 25", 2, 27, 10000.25, new Date(System.currentTimeMillis() + 76400000), true, 1},
        {"String 31", 3, 20, 8131.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 32", 3, 20, 8231.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 33", 3, 20, 8331.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 34", 3, 20, 8431.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 35", 3, 20, 8531.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 36", 3, 20, 8631.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 37", 3, 20, 8731.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 38", 3, 20, 8831.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
        {"String 39", 3, 20, 8931.7, new Date(System.currentTimeMillis() - 216000000), true, 3},
    });
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
      protected void execInitAction() {
        super.execInitAction();
      }

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

    @Order(30.0)
    public class ChangeAggregationFunctionMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.SingleSelection,
            TableMenuType.MultiSelection,
            TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ChangeAggregationFunction");
      }

      @Override
      protected void execAction() {
        List<IColumn<?>> columns = getTable().getColumns();
        List<String> aggrFunctions = new ArrayList<String>();
        aggrFunctions.add(AggregationFunction.SUM);
        aggrFunctions.add(AggregationFunction.AVG);
        aggrFunctions.add(AggregationFunction.MIN);
        aggrFunctions.add(AggregationFunction.MAX);
        int i = 0;
        if (!getSmartColumn().isGroupingActive()) {
          getColumnSet().setGroupingColumn(getSmartColumn(), true);
          getTable().sort();
          return;
        }
        for (IColumn<?> c : columns) {
          if (c instanceof INumberColumn) {
            INumberColumn column = (INumberColumn) c;
            String oldAggr = column.getAggregationFunction();
            for (i = 0; i < aggrFunctions.size(); ++i) {
              if (StringUtility.nvl(oldAggr, "").equals(StringUtility.nvl(aggrFunctions.get(i), ""))) {
                column.setAggregationFunction(aggrFunctions.get((i + 1) % (aggrFunctions.size())));
                break;
              }
              column.setAggregationFunction(aggrFunctions.get(0));
            }
          }
        }
        if (i >= aggrFunctions.size() - 1) {
          getColumnSet().resetSortingAndGrouping();
        }
      }

    }

    @Order(40.0)
    public class ChangeBackgroundEffectMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.SingleSelection,
            TableMenuType.MultiSelection,
            TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ChangeBackgroundEffect");
      }

      @Override
      protected void execAction() {
        List<IColumn<?>> columns = getTable().getColumns();
        List<String> effects = new ArrayList<String>();
        effects.add(null);
        effects.add(BackgroundEffect.COLOR_GRADIENT_1);
        effects.add(BackgroundEffect.COLOR_GRADIENT_2);
        effects.add(BackgroundEffect.BAR_CHART);

        for (IColumn<?> c : columns) {
          if (c instanceof INumberColumn) {
            INumberColumn column = (INumberColumn) c;
            String oldEffect = column.getBackgroundEffect();
            for (int i = 0; i < effects.size(); ++i) {
              if (StringUtility.nvl(oldEffect, "").equals(StringUtility.nvl(effects.get(i), ""))) {
                column.setBackgroundEffect(effects.get((i + 1) % (effects.size())));
                break;
              }
            }

          }
        }
      }

    }

  }

  @Order(10.0)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected void execInitAction() {
      super.execInitAction();
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(
          TableMenuType.SingleSelection,
          TableMenuType.MultiSelection,
          TableMenuType.EmptySpace);
    }

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithTableTablePage.class;
    }
  }
}
