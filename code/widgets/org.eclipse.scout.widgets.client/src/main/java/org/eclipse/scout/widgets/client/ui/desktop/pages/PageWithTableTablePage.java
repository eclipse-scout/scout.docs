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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTileTableHeader;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
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
import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithTableTablePage.Table;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm;

@ClassId("b06a2ee2-f395-4d73-888a-1fc5e7821edf")
public class PageWithTableTablePage extends AbstractPageWithTable<Table> {

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
        {"String 10", "String 11", m_random.nextLong(), m_random.nextInt(), 9768.3, new Date(System.currentTimeMillis()), false, 2L, 1.0, 0.01, 1.0},
        {"String 11", "String 31", m_random.nextLong(), m_random.nextInt(), 8768.3, new Date(System.currentTimeMillis()), false, 2L, 10.0, 0.10, 1.120},
        {"String 12", "String 0", m_random.nextLong(), m_random.nextInt(), 7768.3, new Date(System.currentTimeMillis()), false, 2L, 100.0, 1.0, 1.123},
        {"String 13", "String 1", m_random.nextLong(), m_random.nextInt(), 5768.3, new Date(System.currentTimeMillis()), false, 2L, 0.1, 0.001, 1.150},
        {"String 22", "String 1000", m_random.nextLong(), m_random.nextInt(), 13000.25, new Date(System.currentTimeMillis() + 86400000), true, 1L, -1.0, -0.01, 1.151},
        {"String 23", "String .txt", m_random.nextLong(), m_random.nextInt(), 12000.25, new Date(System.currentTimeMillis() + 46400000), true, 1L, -10.0, -0.1, 1.5},
        {"String 24", "String 1 mit 10", m_random.nextLong(), m_random.nextInt(), 11000.25, new Date(System.currentTimeMillis() + 56400000), true, 1L, -100.0, -1.0, 1.51},
        {"String 25", "String 1 mit 3", m_random.nextLong(), m_random.nextInt(), 10000.25, new Date(System.currentTimeMillis() + 76400000), true, 1L, -0.1, -0.001, 1.511},
        {"String 31", "String 1 mit 1020", m_random.nextLong(), m_random.nextInt(), 8131.7, new Date(System.currentTimeMillis() - 216000000), true, 3L, 1.2, 0.012, 1.0},
        {"String 32", "String 1.txt", m_random.nextLong(), m_random.nextInt(), 8231.7, new Date(System.currentTimeMillis() - 216000000), true, 3L, 1.02, 0.0102, -1.0},
        {"String 33", "String 2.txt", m_random.nextLong(), m_random.nextInt(), 8331.7, new Date(System.currentTimeMillis() - 216000000), true, 3L, 1.002, 0.01002, -1.120},
        {"String 34", "String 19.txt", m_random.nextLong(), m_random.nextInt(), 8431.7, new Date(System.currentTimeMillis() - 216000000), true, 3L, -1.2, -0.012, -1.123},
        {"String 35", "String 200.txt", m_random.nextLong(), m_random.nextInt(), 8531.7, new Date(System.currentTimeMillis() - 216000000), true, 3L, -1.02, -0.0102, -1.150},
        {"String 36", "String 1", m_random.nextLong(), m_random.nextInt(), 8631.7, new Date(System.currentTimeMillis() - 216000000), true, 3L, -1.002, -0.01002, -1.151},
        {"String 37", "String 2", m_random.nextLong(), m_random.nextInt(), 0.005, new Date(System.currentTimeMillis() - 216000000), true, 3L, 1.0, 0.01, -1.5},
        {"String 38", "String 19 ", m_random.nextLong(), m_random.nextInt(), 0.006, new Date(System.currentTimeMillis() - 216000000), true, 3L, 1.0, 0.01, -1.51},
        {"String 39", "String 200", m_random.nextLong(), m_random.nextInt(), 0.006, new Date(System.currentTimeMillis() - 216000000), true, 3L, 1.0, 0.01, -1.511},
    };
  }

  @ClassId("df2fe368-10af-458b-9fdc-b9db9782d546")
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

    @Override
    protected ITile execCreateTile(ITableRow row) {
      return new AbstractHtmlTile() {
        @Override
        public String classId() {
          return UUID.randomUUID().toString();
        }

        @Override
        protected String getConfiguredContent() {
          return HTML.fragment(
              HTML.br(), HTML.bold("String Column: " + row.getCellValue(getStringColumn().getColumnIndex())),
              HTML.br(), HTML.bold("Number Column: " + row.getCellValue(getLongColumn().getColumnIndex())),
              HTML.br(), HTML.bold("Boolean Column: " + row.getCellValue(getBooleanColumn().getColumnIndex())),
              HTML.br(), HTML.bold("String Column: " + row.getCellValue(getStringColumn().getColumnIndex()))).toHtml();
        }
      };
    }

    @Order(10)
    @ClassId("e10f123b-b327-4050-b890-7f5f0f644e19")
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
    @ClassId("6fc968dc-b377-443a-a363-23b0e2a2ebd3")
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
    @ClassId("dd085263-8818-477e-a8ef-15d6bc290117")
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
    @ClassId("8ffedfd0-12b2-4734-a2c3-8b8006a65eaf")
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
    @ClassId("8ef39a96-7851-4e54-9b95-6d09bd63d345")
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
    @ClassId("09a39973-25d1-45d8-bf99-a75e7e7c99bb")
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
    @ClassId("ea738743-46e8-4edd-b24e-6934d5fac41a")
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
    @ClassId("79f59bf7-cccd-4fdf-a3d5-182050cba8c4")
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
    @ClassId("c4d66563-13b6-44a0-8ea1-c8935868bbfb")
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
    @ClassId("6b1e22c7-00ab-4f14-bb5a-f2d856ecbdc4")
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
    @ClassId("7f19882a-7d93-4dbd-b5f1-3d787588608a")
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

    @ClassId("e67e965b-ba87-4364-8faa-e502a43b5753")
    public class TileTableHeaderBox extends AbstractTileTableHeader {
    }

    @Order(10)
    @ClassId("532d29f7-6b40-4e7d-93ea-14f94e8509f5")
    public class AddRowMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AddRow");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected void execAction() {
        addRow(createNewRow());
      }
    }

    protected ITableRow createNewRow() {
      ITableRow row = getTable().createRow();
      getStringColumn().setValue(row, "String 1" + getRows().size());
      getAlphanumericSortingStringColumn().setValue(row, "String 1" + getRows().size());
      getLongColumn().setValue(row, m_random.nextLong());
      getIntegerColumn().setValue(row, m_random.nextInt());
      getBigDecimalColumn().setValue(row, new BigDecimal(m_random.nextInt() / 10));
      getDateColumn().setValue(row, new Date());
      getSmartColumn().setValue(row, 2L);
      getPercentColumn().setValue(row, new BigDecimal(10));
      getMultiplierColumn().setValue(row, new BigDecimal("0.01"));
      getRoundingModeColumn().setValue(row, new BigDecimal(1));
      return row;
    }

    @Order(20)
    @ClassId("eed213ac-ee8f-48cb-8e41-eecf44aa925b")
    public class DeleteRowMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteRow");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
      }

      @Override
      protected void execAction() {
        deleteRows(getSelectedRows());
      }
    }

    @Order(30)
    @ClassId("5bfc103b-899d-4cf0-83ad-1e27db34e77e")
    public class FormMenu extends AbstractFormMenu<BooleanFieldForm> {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected Class<BooleanFieldForm> getConfiguredForm() {
        return BooleanFieldForm.class;
      }

      @Override
      protected String getConfiguredText() {
        return "Form-Menu";
      }
    }

    @Order(50)
    @ClassId("24d3b8c1-d956-46d3-b271-2c5ff53df89a")
    public class HierarchicalMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Hierarchical Menu";
      }

      @Order(10)
      @ClassId("9d88f3f6-4cc4-45be-82f5-a59d4692615b")
      public class SubSingleMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "SubSingle";
        }
      }

      @Order(20)
      @ClassId("5dc7666c-630b-4b37-be9c-765036c8a0dc")
      public class SubMultiMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "SubMulti";
        }
      }

      @Order(30)
      @ClassId("87efb878-277e-4481-9afa-6d597c3edec3")
      public class SubEmptySpaceMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredText() {
          return "SubEmpty";
        }
      }

      @Order(40)
      @ClassId("7f16dec2-bd23-4532-903a-e9ea9aa12ea8")
      public class IntermediateMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Intermediate Menu";
        }

        @Order(10)
        @ClassId("fed345fc-1f08-4ff8-81c1-bbe2185f00b7")
        public class SubSubSingleMenu extends AbstractMenu {

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection);
          }

          @Override
          protected String getConfiguredText() {
            return "SubSubSingle";
          }
        }

        @Order(20)
        @ClassId("fe43e369-7f4d-4d5b-bfe2-b654054aba6a")
        public class SubSubMultiMenu extends AbstractMenu {

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection);
          }

          @Override
          protected String getConfiguredText() {
            return "SubSubMulti";
          }
        }

        @Order(30)
        @ClassId("57c2ccb3-91a0-4d7a-94f6-f842877345ec")
        public class SubSubEmptySpaceMenu extends AbstractMenu {

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
          }

          @Override
          protected String getConfiguredText() {
            return "SubSubEmpty";
          }
        }
      }
    }

    @Order(60)
    @ClassId("0560bd7f-d1db-4140-b348-faa3a7bb3048")
    public class MoreMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("More");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Order(30)
      @ClassId("1df57970-bab8-467e-8de5-c3675693b7b6")
      public class ChangeAggregationFunctionMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ChangeAggregationFunction");
        }

        @Override
        protected void execAction() {
          List<IColumn<?>> columns = getTable().getColumns();
          List<String> aggrFunctions = new ArrayList<>();
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
                if (StringUtility.emptyIfNull(oldAggr).equals(StringUtility.emptyIfNull(aggrFunctions.get(i)))) {
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
      @ClassId("4b131b0a-8c22-44ec-90bc-6cbcc5e3dfca")
      public class ChangeBackgroundEffectMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ChangeBackgroundEffect");
        }

        @Override
        protected void execAction() {
          List<IColumn<?>> columns = getTable().getColumns();
          List<String> effects = new ArrayList<>();
          effects.add(null);
          effects.add(BackgroundEffect.COLOR_GRADIENT_1);
          effects.add(BackgroundEffect.COLOR_GRADIENT_2);
          effects.add(BackgroundEffect.BAR_CHART);

          for (IColumn<?> c : columns) {
            if (c instanceof INumberColumn) {
              INumberColumn column = (INumberColumn) c;
              String oldEffect = column.getBackgroundEffect();
              for (int i = 0; i < effects.size(); ++i) {
                if (StringUtility.emptyIfNull(oldEffect).equals(StringUtility.emptyIfNull(effects.get(i)))) {
                  column.setBackgroundEffect(effects.get((i + 1) % (effects.size())));
                  break;
                }
              }
            }
          }
        }
      }
    }
  }

  @Order(10)
  @ClassId("ef9d8969-3c23-4474-968e-4d97cc64ba7d")
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
    }

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithTableTablePage.class;
    }
  }

  @Order(15)
  @ClassId("27fe26af-fa2c-43b7-bdc8-068fb2327fc7")
  public class HierarchicalPageMenu extends AbstractMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
    }

    @Override
    protected String getConfiguredText() {
      return "HierarchicalPage Menu";
    }

    @Order(10)
    @ClassId("123ebf2a-a992-4a2f-8c54-c3277b28121f")
    public class SubSingleMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubSingle";
      }
    }

    @Order(20)
    @ClassId("63786caa-e075-46df-9ba2-9a9bf9eef704")
    public class SubMultiMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TreeMenuType.MultiSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubMulti";
      }
    }

    @Order(30)
    @ClassId("9602c55a-0b0f-4abf-917b-2e2175fd09ae")
    public class SubEmptySpaceMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubEmpty";
      }
    }

    @Order(40)
    @ClassId("0f63e1b5-c190-4f3b-a144-efabff6dc2d5")
    public class IntermediateMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Intermediate Menu";
      }

      @Order(10)
      @ClassId("4dac2afe-a0e5-45ef-ae2f-1094e22967c2")
      public class SubSubSingleMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "TreeSubSubSingle";
        }
      }

      @Order(20)
      @ClassId("9ce67a6d-bf7e-4db1-82f5-5bfa3c0bc595")
      public class SubSubMultiMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TreeMenuType.MultiSelection);
        }

        @Override
        protected String getConfiguredText() {
          return "TreeSubSubMulti";
        }
      }

      @Order(30)
      @ClassId("2595641e-645b-41ea-911b-db13a646f92c")
      public class SubSubEmptySpaceMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredText() {
          return "TreeSubSubEmpty";
        }
      }
    }
  }
}
