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

import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractAlphanumericSortingStringColumn;
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
import org.eclipse.scout.rt.client.ui.form.AbstractFormMenu;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.CheckboxFieldForm;

public class PageWithTableTablePage extends AbstractPageWithTable<PageWithTableTablePage.Table> {

  protected final Random m_random = new Random();

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
    importTableData(loadDemoData());
  }

  protected Object[][] loadDemoData() {
    return new Object[][]{
        {"String 10", "String 11", m_random.nextLong(), m_random.nextInt(), 9768.3, new Date(System.currentTimeMillis()), false, 2, 1.0, 0.01, 1.0},
        {"String 11", "String 31", m_random.nextLong(), m_random.nextInt(), 8768.3, new Date(System.currentTimeMillis()), false, 2, 10.0, 0.10, 1.120},
        {"String 12", "String 0", m_random.nextLong(), m_random.nextInt(), 7768.3, new Date(System.currentTimeMillis()), false, 2, 100.0, 1.0, 1.123},
        {"String 13", "String 1", m_random.nextLong(), m_random.nextInt(), 5768.3, new Date(System.currentTimeMillis()), false, 2, 0.1, 0.001, 1.150},
        {"String 22", "String 1000", m_random.nextLong(), m_random.nextInt(), 13000.25, new Date(System.currentTimeMillis() + 86400000), true, 1, -1.0, -0.01, 1.151},
        {"String 23", "String .txt", m_random.nextLong(), m_random.nextInt(), 12000.25, new Date(System.currentTimeMillis() + 46400000), true, 1, -10.0, -0.1, 1.5},
        {"String 24", "String 1 mit 10", m_random.nextLong(), m_random.nextInt(), 11000.25, new Date(System.currentTimeMillis() + 56400000), true, 1, -100.0, -1.0, 1.51},
        {"String 25", "String 1 mit 3", m_random.nextLong(), m_random.nextInt(), 10000.25, new Date(System.currentTimeMillis() + 76400000), true, 1, -0.1, -0.001, 1.511},
        {"String 31", "String 1 mit 1020", m_random.nextLong(), m_random.nextInt(), 8131.7, new Date(System.currentTimeMillis() - 216000000), true, 3, 1.2, 0.012, 1.0},
        {"String 32", "String 1.txt", m_random.nextLong(), m_random.nextInt(), 8231.7, new Date(System.currentTimeMillis() - 216000000), true, 3, 1.02, 0.0102, -1.0},
        {"String 33", "String 2.txt", m_random.nextLong(), m_random.nextInt(), 8331.7, new Date(System.currentTimeMillis() - 216000000), true, 3, 1.002, 0.01002, -1.120},
        {"String 34", "String 19.txt", m_random.nextLong(), m_random.nextInt(), 8431.7, new Date(System.currentTimeMillis() - 216000000), true, 3, -1.2, -0.012, -1.123},
        {"String 35", "String 200.txt", m_random.nextLong(), m_random.nextInt(), 8531.7, new Date(System.currentTimeMillis() - 216000000), true, 3, -1.02, -0.0102, -1.150},
        {"String 36", "String 1", m_random.nextLong(), m_random.nextInt(), 8631.7, new Date(System.currentTimeMillis() - 216000000), true, 3, -1.002, -0.01002, -1.151},
        {"String 37", "String 2", m_random.nextLong(), m_random.nextInt(), 0.005, new Date(System.currentTimeMillis() - 216000000), true, 3, 1.0, 0.01, -1.5},
        {"String 38", "String 19 ", m_random.nextLong(), m_random.nextInt(), 0.006, new Date(System.currentTimeMillis() - 216000000), true, 3, 1.0, 0.01, -1.51},
        {"String 39", "String 200", m_random.nextLong(), m_random.nextInt(), 0.006, new Date(System.currentTimeMillis() - 216000000), true, 3, 1.0, 0.01, -1.511},
    };
  }

  public class Table extends AbstractTable {

    public SmartColumn getSmartColumn() {
      return getColumnSet().getColumnByClass(SmartColumn.class);
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

    public AlphanumericSortingStringColumn getAlphanumericSortingStringColumn() {
      return getColumnSet().getColumnByClass(AlphanumericSortingStringColumn.class);
    }

    public PercentColumn getPercentColumn() {
      return getColumnSet().getColumnByClass(PercentColumn.class);
    }

    public MultiplierColumn getMultiplierColumn() {
      return getColumnSet().getColumnByClass(MultiplierColumn.class);
    }

    public RoundingModeColumn getRoundingModeColumn() {
      return getColumnSet().getColumnByClass(RoundingModeColumn.class);
    }

    @Order(10)
    public class StringColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("StringColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(15)
    public class AlphanumericSortingStringColumn extends AbstractAlphanumericSortingStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("StringColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(20)
    public class LongColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("LongColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(30)
    public class IntegerColumn extends AbstractIntegerColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("IntegerColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 130;
      }
    }

    @Order(40)
    public class BigDecimalColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("BigDecimalColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(50)
    public class DateColumn extends AbstractDateColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("DateColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(60)
    public class BooleanColumn extends AbstractBooleanColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("BooleanColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(70)
    public class SmartColumn extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("SmartColumn");
      }

      @Override
      protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
        return CompanyTypeLookupCall.class;
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(80)
    public class PercentColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PercentColumn");
      }

      @Override
      protected boolean getConfiguredPercent() {
        return true;
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(90)
    public class MultiplierColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("MultiplierColumn");
      }

      @Override
      protected int getConfiguredMultiplier() {
        return -100;
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(100)
    public class RoundingModeColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("RoundingModeColumn");
      }

      @Override
      protected RoundingMode getConfiguredRoundingMode() {
        return RoundingMode.CEILING;
      }

      @Override
      protected int getConfiguredWidth() {
        return 80;
      }
    }

    @Order(30)
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

    @Order(40)
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

    @Order(50)
    public class FormMenu extends AbstractFormMenu<CheckboxFieldForm> {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.SingleSelection,
            TableMenuType.MultiSelection,
            TableMenuType.EmptySpace);
      }

      @Override
      protected Class<CheckboxFieldForm> getConfiguredForm() {
        return CheckboxFieldForm.class;
      }

      @Override
      protected String getConfiguredText() {
        return "Form-Menu";
      }
    }

    @Order(20)
    public class TableSingleMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.SingleSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "Single";
      }
    }

    @Order(60)
    public class HierarchicalMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Hierarchical Menu";
      }

      @Order(10)
      public class SubSingleMenu extends AbstractMenu {
        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(
              TableMenuType.SingleSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "SubSingle";
        }
      }

      @Order(20)
      public class SubMultiMenu extends AbstractMenu {
        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(
              TableMenuType.MultiSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "SubMulti";
        }
      }

      @Order(30)
      public class SubEmptySpaceMenu extends AbstractMenu {
        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(
              TableMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredText() {
          return "SubEmpty";
        }
      }

      @Order(40)
      public class IntermediateMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Intermediate Menu";
        }

        @Order(10)
        public class SubSubSingleMenu extends AbstractMenu {
          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(
                TableMenuType.SingleSelection);
          }

          @Override
          protected String getConfiguredText() {
            return "SubSubSingle";
          }
        }

        @Order(20)
        public class SubSubMultiMenu extends AbstractMenu {
          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(
                TableMenuType.MultiSelection);
          }

          @Override
          protected String getConfiguredText() {
            return "SubSubMulti";
          }
        }

        @Order(30)
        public class SubSubEmptySpaceMenu extends AbstractMenu {
          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(
                TableMenuType.EmptySpace);
          }

          @Override
          protected String getConfiguredText() {
            return "SubSubEmpty";
          }
        }
      }
    }
  }

  @Order(10)
  public class HierarchicalPageMenu extends AbstractMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(
          TreeMenuType.SingleSelection);
    }

    @Override
    protected String getConfiguredText() {
      return "HierarchicalPage Menu";
    }

    @Order(10)
    public class SubSingleMenu extends AbstractMenu {
      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TreeMenuType.SingleSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubSingle";
      }

    }

    @Order(20)
    public class SubMultiMenu extends AbstractMenu {
      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TreeMenuType.MultiSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubMulti";
      }

    }

    @Order(30)
    public class SubEmptySpaceMenu extends AbstractMenu {
      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(
            TreeMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubEmpty";
      }

    }

    @Order(40)
    public class IntermediateMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Intermediate Menu";
      }

      @Order(10)
      public class SubSubSingleMenu extends AbstractMenu {
        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(
              TreeMenuType.SingleSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "TreeSubSubSingle";
        }

      }

      @Order(20)
      public class SubSubMultiMenu extends AbstractMenu {
        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(
              TreeMenuType.MultiSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "TreeSubSubMulti";
        }

      }

      @Order(30)
      public class SubSubEmptySpaceMenu extends AbstractMenu {
        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(
              TreeMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredText() {
          return "TreeSubSubEmpty";
        }
      }
    }
  }

  @Order(10)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(
          TreeMenuType.EmptySpace,
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
