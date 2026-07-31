/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableEvent;
import org.eclipse.scout.rt.client.ui.basic.table.TableListener;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table.ArrayIndexColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table.ChildrenColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table.NameColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table.ParentColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table.RowIndexColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableInspectorForm.MainBox.GroupBox.InspectorTableField.Table.StatusColumn;

@ClassId("1dcfd08f-b3c0-45aa-8777-180a5c51c54b")
public class TableInspectorForm extends AbstractForm {

  protected final ITable m_inspectedTable;
  protected final Function<ITableRow, String> m_nameProvider;
  protected final TableListener m_tableListener = this::onTableEvent;

  protected IFuture<Void> m_scheduledTableUpdate = null;

  public TableInspectorForm(ITable inspectedTable) {
    this(inspectedTable, null);
  }

  public TableInspectorForm(ITable inspectedTable, Function<ITableRow, String> nameProvider) {
    m_inspectedTable = Assertions.assertNotNull(inspectedTable);
    m_nameProvider = ObjectUtility.nvl(nameProvider, row -> m_inspectedTable.getColumnSet().getFirstVisibleColumn().getDisplayText(row));
    m_inspectedTable.addTableListener(m_tableListener,
        TableEvent.TYPE_ROWS_INSERTED,
        TableEvent.TYPE_ROWS_UPDATED,
        TableEvent.TYPE_ROWS_DELETED,
        TableEvent.TYPE_ALL_ROWS_DELETED,
        TableEvent.TYPE_ROW_ORDER_CHANGED
    );
    updateTableInfo();
  }

  @Override
  protected void execDisposeForm() {
    m_inspectedTable.removeTableListener(m_tableListener);
  }

  public ITable getInspectedTable() {
    return m_inspectedTable;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Table Inspector";
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_DIALOG;
  }

  @Override
  protected boolean getConfiguredCacheBounds() {
    return true;
  }

  @Override
  protected int getConfiguredModalityHint() {
    return MODALITY_HINT_MODELESS;
  }

  @Override
  protected boolean getConfiguredClosable() {
    return true;
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  public InspectorTableField getInspectorTableField() {
    return getFieldByClass(InspectorTableField.class);
  }

  protected void onTableEvent(TableEvent event) {
    if (m_scheduledTableUpdate != null) {
      return;
    }
    m_scheduledTableUpdate = ModelJobs.schedule(() -> {
      m_scheduledTableUpdate = null;
      this.updateTableInfo();
    }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
  }

  public void updateTableInfo() {
    Table inspectorTable = getInspectorTableField().getTable();
    ArrayIndexColumn arrayIndexColumn = inspectorTable.getArrayIndexColumn();
    NameColumn nameColumn = inspectorTable.getNameColumn();
    RowIndexColumn rowIndexColumn = inspectorTable.getRowIndexColumn();
    ParentColumn parentColumn = inspectorTable.getParentColumn();
    ChildrenColumn childrenColumn = inspectorTable.getChildrenColumn();
    StatusColumn statusColumn = inspectorTable.getStatusColumn();

    inspectorTable.setTableChanging(true);
    try {
      List<ITableRow> inspectorRows = new ArrayList<>();
      for (int i = 0; i < m_inspectedTable.getRowCount(); i++) {
        ITableRow row = m_inspectedTable.getRow(i);
        ITableRow inspectorRow = inspectorTable.createRow();
        inspectorRows.add(inspectorRow);
        inspectorRow.getCellForUpdate(arrayIndexColumn).setValue(i);
        inspectorRow.getCellForUpdate(nameColumn).setValue(m_nameProvider.apply(row));
        inspectorRow.getCellForUpdate(rowIndexColumn).setValue("#" + row.getRowIndex());
        inspectorRow.getCellForUpdate(rowIndexColumn).toggleCssClass("invalid-value", row.getRowIndex() != i);
        if (row.getParentRow() != null) {
          inspectorRow.getCellForUpdate(parentColumn).setValue("#" + row.getParentRow().getRowIndex());
        }
        if (CollectionUtility.hasElements(row.getChildRows())) {
          inspectorRow.getCellForUpdate(childrenColumn).setValue(row.getChildRows().stream()
              .map(c -> "#" + c.getRowIndex())
              .collect(Collectors.joining(", "))
          );
        }
        String status = switch (row.getStatus()) {
          case ITableRow.STATUS_DELETED -> "DELETED";
          case ITableRow.STATUS_INSERTED -> "INSERTED";
          case ITableRow.STATUS_NON_CHANGED -> "NON_CHANGED";
          case ITableRow.STATUS_UPDATED -> "UPDATED";
          default -> null;
        };
        inspectorRow.getCellForUpdate(statusColumn).setValue(status);
      }
      inspectorTable.deleteAllRows();
      inspectorTable.addRows(inspectorRows);
    }
    finally {
      inspectorTable.setTableChanging(false);
    }
  }

  @ClassId("b85b7691-78bf-4ce8-ad38-a94d9253e4b3")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredWidthInPixel() {
      return 800;
    }

    @Override
    protected int getConfiguredHeightInPixel() {
      return 400;
    }

    @Order(10)
    @ClassId("3c97146c-fef1-42a9-ade4-79b624b07bff")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("5aaf2715-72cf-47c4-9a59-cfc7dcb91900")
      public class InspectorTableField extends AbstractTableField<Table> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @ClassId("fb31c6e5-13d2-4037-91f7-b180fba7d4e4")
        public class Table extends AbstractTable {

          @Override
          protected boolean getConfiguredSortEnabled() {
            return false;
          }

          @Override
          protected boolean getConfiguredTextFilterEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredCssClass() {
            return "table-inspector-table";
          }

          public ArrayIndexColumn getArrayIndexColumn() {
            return getColumnSet().getColumnByClass(ArrayIndexColumn.class);
          }

          public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
          }

          public RowIndexColumn getRowIndexColumn() {
            return getColumnSet().getColumnByClass(RowIndexColumn.class);
          }

          public ParentColumn getParentColumn() {
            return getColumnSet().getColumnByClass(ParentColumn.class);
          }

          public ChildrenColumn getChildrenColumn() {
            return getColumnSet().getColumnByClass(ChildrenColumn.class);
          }

          public StatusColumn getStatusColumn() {
            return getColumnSet().getColumnByClass(StatusColumn.class);
          }

          @Order(10)
          @ClassId("c457184e-e1db-4e4a-bc28-7df613f0a659")
          public class ArrayIndexColumn extends AbstractIntegerColumn {

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "Array Index";
            }

            @Override
            protected int getConfiguredWidth() {
              return 50;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
              cell.setText("[" + getValue(row) + "]");
            }
          }

          @Order(20)
          @ClassId("76002bef-c5dc-4ee8-a0aa-1a467767933e")
          public class NameColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Name";
            }

            @Override
            protected int getConfiguredWidth() {
              return 220;
            }
          }

          @Order(30)
          @ClassId("b8bb8709-e53b-4c2c-97c5-05bacee50938")
          public class RowIndexColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Row Index";
            }

            @Override
            protected int getConfiguredWidth() {
              return 90;
            }
          }

          @Order(40)
          @ClassId("ef216820-ad56-4157-8892-489287fd8260")
          public class ParentColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Parent";
            }

            @Override
            protected int getConfiguredWidth() {
              return 70;
            }
          }

          @Order(50)
          @ClassId("21dc766b-551e-46bb-a604-c7b25e793acb")
          public class ChildrenColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Children";
            }

            @Override
            protected int getConfiguredWidth() {
              return 150;
            }
          }

          @Order(60)
          @ClassId("5c338717-d0b7-42ed-9442-247d4227b17e")
          public class StatusColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Status";
            }

            @Override
            protected int getConfiguredWidth() {
              return 150;
            }
          }
        }
      }
    }
  }
}
