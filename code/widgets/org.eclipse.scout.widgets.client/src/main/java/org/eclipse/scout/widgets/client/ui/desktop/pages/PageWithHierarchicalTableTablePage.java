/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.form.fields.AbstractFormFieldMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.HierarchicalStyle;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.IDoEntity;
import org.eclipse.scout.rt.dataobject.TypeName;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithHierarchicalTableTablePage.P_SearchForm.MainBox.TabBox.OptionsBox.IncludeOrphanRowField;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithHierarchicalTableTablePage.Table;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("79db5f99-3f8d-4e62-a03c-d7305e5d7055")
public class PageWithHierarchicalTableTablePage extends AbstractPageWithTable<Table> {

  public PageWithHierarchicalTableTablePage() {
    super();
  }

  public PageWithHierarchicalTableTablePage(String name) {
    super();
    getCellForUpdate().setText(name);
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Page with table (hierarchical)";
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return P_SearchForm.class;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    boolean includeOrphanRow = false;
    if (filter.getData() instanceof P_SearchData) {
      includeOrphanRow = ((P_SearchData) filter.getData()).isIncludeOrphanRow();
    }
    importTableData(loadDemoData(includeOrphanRow));
  }

  @Override
  protected void execReloadPage(String reloadReason) {
    boolean allRowsExpanded = getTable().getRows().stream().allMatch(row -> row.isExpanded());
    super.execReloadPage(reloadReason);
    if (allRowsExpanded) {
      getTable().expandAll(null);
    }
  }

  @SuppressWarnings("SpellCheckingInspection")
  protected Object[][] loadDemoData(boolean includeOrphanRow) {
    Object[][] data = new Object[][]{
        {"10", null, "Xensil Unperamete"},
        {"11", "10", "Lotyra Nubreminsi"},
        {"12", "10", "Pughth Zoyaripula"},
        {"20", null, "Luxico Atapholitt"},
        {"21", "20", "Toasti Joolittina"}
    };
    if (includeOrphanRow) {
      List<Object[]> dataList = new ArrayList<>(Arrays.asList(data));
      dataList.add(new Object[]{"31", "30", "Bysind Geadraceas"}); // <-- parent key 30 does not exist
      data = dataList.toArray(new Object[dataList.size()][]);
    }
    return data;
  }

  @ClassId("3efa3ebc-f2e9-43d0-b371-83bc358ee0dc")
  public class Table extends AbstractTable {

    public KeyColumn getKeyColumn() {
      return getColumnSet().getColumnByClass(KeyColumn.class);
    }

    public ParentKeyColumn getParentKeyColumn() {
      return getColumnSet().getColumnByClass(ParentKeyColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Order(10)
    @ClassId("8b40a4a6-bae3-4ad1-b14a-4e8f4789a719")
    public class KeyColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return "Key";
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }

      @Override
      protected int getConfiguredSortIndex() {
        return 0;
      }
    }

    @Order(20)
    @ClassId("fcbdf86a-a68f-48cd-9ef7-f6412d33e382")
    public class ParentKeyColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return "Parent Key";
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }

      @Override
      protected boolean getConfiguredParentKey() {
        return true;
      }
    }

    @Order(30)
    @ClassId("7dc64420-7c7b-42fa-829a-6579c44eae95")
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return "Name";
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(10)
    @ClassId("ef6d9d3b-be3e-479f-8c4a-1d22f7e1ae7c")
    public class ExpandAllMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return "Expand all";
      }

      @Override
      protected String getConfiguredIconId() {
        return Icons.ExpandAll;
      }

      @Override
      protected void execAction() {
        getTable().expandAll(null);
      }
    }

    @Order(20)
    @ClassId("b6f0f550-16ea-4bbb-84a0-08429d0cf15f")
    public class CollapseAllMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return "Collapse all";
      }

      @Override
      protected String getConfiguredIconId() {
        return Icons.CollapseAll;
      }

      @Override
      protected void execAction() {
        getTable().collapseAll(null);
      }
    }

    @Order(30)
    @ClassId("e3c14e58-3f4b-4294-8e8a-47777155ed55")
    public class HierarchicalStyleMenu extends AbstractFormFieldMenu {

      @Override
      protected byte getConfiguredHorizontalAlignment() {
        return HORIZONTAL_ALIGNMENT_RIGHT;
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Order(10)
      @ClassId("d70a98aa-e9d3-42f0-845b-93105cd06b9a")
      public class HierarchicalStyleSmartField extends AbstractSmartField<HierarchicalStyle> {

        @Override
        protected String getConfiguredLabel() {
          return "Hierarchical Style";
        }

        @Override
        protected Class<? extends ILookupCall<HierarchicalStyle>> getConfiguredLookupCall() {
          return P_HierarchicalStyleLookupCall.class;
        }

        @Override
        protected boolean getConfiguredLabelUseUiWidth() {
          return true;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected String getConfiguredFieldStyle() {
          return FIELD_STYLE_CLASSIC;
        }

        @Override
        protected String getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DROPDOWN;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 300;
        }

        @Override
        protected void execInitField() {
          setValue(getTable().getHierarchicalStyle());
        }

        @Override
        protected void execChangedValue() {
          getTable().setHierarchicalStyle(getValue());
        }
      }
    }
  }

  @Order(10)
  @ClassId("084ef4a7-b3a9-4fb1-9b21-019d0f4e2f1e")
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
    }

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithHierarchicalTableTablePage.class;
    }
  }

  @ClassId("1b0f1d53-aca8-4571-bd91-d85b2bc61529")
  public static class P_HierarchicalStyleLookupCall extends LocalLookupCall<HierarchicalStyle> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<HierarchicalStyle>> execCreateLookupRows() {
      List<LookupRow<HierarchicalStyle>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(HierarchicalStyle.DEFAULT, "Default"));
      rows.add(new LookupRow<>(HierarchicalStyle.STRUCTURED, "Structured"));
      return rows;
    }
  }

  @ClassId("780ef59c-4cdc-4d94-90d5-d55315cdfd6e")
  public static class P_SearchForm extends AbstractSearchForm {

    @Override
    protected void execResetSearchFilter(SearchFilter searchFilter) {
      super.execResetSearchFilter(searchFilter);
      IDoEntity data = BEANS.get(P_SearchData.class)
          .withIncludeOrphanRow(getIncludeOrphanRowField().isChecked());
      searchFilter.setData(data);
    }

    public IncludeOrphanRowField getIncludeOrphanRowField() {
      return getFieldByClass(IncludeOrphanRowField.class);
    }

    @Order(10)
    @ClassId("47bd8393-70b4-4696-832b-c15a9d9665bb")
    public class MainBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("3f40879f-b816-4ec7-83c3-960f6616c264")
      public class TabBox extends AbstractTabBox {

        @Order(10)
        @ClassId("624dbd55-863b-4be8-bac0-f2670f964573")
        public class OptionsBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredLabel() {
            return "Options";
          }

          @Order(10)
          @ClassId("aa568216-8bab-4b22-b4aa-a5080ee6706c")
          public class IncludeOrphanRowField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Include orphaned row";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "If checked, the loaded data will include a row with an invalid parent key. It should be silently ignored.";
            }
          }
        }
      }

      @Order(30)
      @ClassId("7a7ad952-e1bf-4952-8548-a95979dbe359")
      public class ResetButton extends AbstractResetButton {
      }

      @Order(40)
      @ClassId("458dfe5f-0e9a-464b-9e12-ce66da415fb3")
      public class SearchButton extends AbstractSearchButton {
      }
    }
  }

  @TypeName("scout.PageWithHierarchicalTableSearchData")
  public static class P_SearchData extends DoEntity {

    public DoValue<Boolean> includeOrphanRow() {
      return doValue("includeOrphanRow");
    }

    // -----

    public P_SearchData withIncludeOrphanRow(Boolean includeOrphanRow) {
      includeOrphanRow().set(includeOrphanRow);
      return this;
    }

    public Boolean getIncludeOrphanRow() {
      return includeOrphanRow().get();
    }

    public boolean isIncludeOrphanRow() {
      return nvl(getIncludeOrphanRow());
    }
  }
}
