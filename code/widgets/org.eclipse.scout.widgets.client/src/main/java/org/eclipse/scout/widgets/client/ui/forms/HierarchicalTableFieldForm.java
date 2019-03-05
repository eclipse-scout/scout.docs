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
package org.eclipse.scout.widgets.client.ui.forms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ColumnSet;
import org.eclipse.scout.rt.client.ui.basic.table.HierarchicalStyle;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIconColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.platform.util.TuningUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.ContextColumnField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.DefaultIconIdField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.DeletedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.InsertedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.AutoResizeColumnsField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsCheckableField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsColumnFixedWidthField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsColumnMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsEditableField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.MultiSelectField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableHeaderVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableStatusVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.WrapTextField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.SelectedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ConfigurationBox.UpdatedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox.TableField;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox.TableField.Table;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox.TableField.Table.CustomColumn;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox.TableField.Table.LocationColumn;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox.TableField.Table.TableStatusVisibleMenu;
import org.eclipse.scout.widgets.client.ui.forms.HierarchicalTableFieldForm.MainBox.ExamplesBox.TableField.Table.TrendColumn;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537;

@Order(5010.0)
public class HierarchicalTableFieldForm extends AbstractForm implements IPageForm {

  static final String[] LOCATIONS = {"San Francisco, USA", "Bruehl, Germany"};

  private AtomicLong m_nextRowId = new AtomicLong();

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("HierarchicalTableField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public AutoResizeColumnsField getAutoResizeColumnsField() {
    return getFieldByClass(AutoResizeColumnsField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public TableHeaderVisibleField getTableHeaderVisibleField() {
    return getFieldByClass(TableHeaderVisibleField.class);
  }

  public InsertedRowsField getInsertedRowsField() {
    return getFieldByClass(InsertedRowsField.class);
  }

  public IsEditableField getIsEditableField() {
    return getFieldByClass(IsEditableField.class);
  }

  public IsCheckableField getIsCheckableField() {
    return getFieldByClass(IsCheckableField.class);
  }

  public IsColumnMandatoryField getIsColumnMandatoryField() {
    return getFieldByClass(IsColumnMandatoryField.class);
  }

  public IsColumnFixedWidthField getIsColumnFixedWidthField() {
    return getFieldByClass(IsColumnFixedWidthField.class);
  }

  public WrapTextField getWrapText() {
    return getFieldByClass(WrapTextField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MultiSelectField getMultiSelectField() {
    return getFieldByClass(MultiSelectField.class);
  }

  public PropertiesGroupBox getPropertiesGroupBox() {
    return getFieldByClass(PropertiesGroupBox.class);
  }

  public SelectedRowsField getSelectedRowsField() {
    return getFieldByClass(SelectedRowsField.class);
  }

  public TableField getTableField() {
    return getFieldByClass(TableField.class);
  }

  public TableStatusVisibleField getTableStatusVisibleField() {
    return getFieldByClass(TableStatusVisibleField.class);
  }

  public UpdatedRowsField getUpdatedRowsField() {
    return getFieldByClass(UpdatedRowsField.class);
  }

  public DefaultIconIdField getDefaultIconIdField() {
    return getFieldByClass(DefaultIconIdField.class);
  }

  public DeletedRowsField getDeletedRowsField() {
    return getFieldByClass(DeletedRowsField.class);
  }

  public ContextColumnField getContextColumnField() {
    return getFieldByClass(ContextColumnField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class TableField extends AbstractTableField<Table> {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          getTable().replaceRows(createInitialRows());
          getTable().expandAll(null);
        }

        private List<ITableRow> createInitialRows() {
          List<ITableRow> rows = new ArrayList<>();
          ITableRow javaRow = createRow(null, "Java", null, null, null, null, null);
          rows.add(javaRow);

          ITableRow eclipseRow = createRow(null, "Eclipse", null, null, null, null, null);
          rows.add(eclipseRow);
          ITableRow usaRow = createRow(eclipseRow, "USA", null, null, null, null, null);
          rows.add(usaRow);
          rows.add(createRow(usaRow, "EclipseCon", "San Francisco", DateUtility.parse("18.03.2014", "dd.MM.yyyy"), DateUtility.parse("18.03.2014 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("20.03.2014 17:45", "dd.MM.yyyy HH:mm"),
              ICB9537.ID));

          ITableRow jsRow = createRow(null, "JavaScript", null, null, null, null, null);
          rows.add(jsRow);
          return rows;
        }

        private List<ITableRow> createRows(int rowCount, int maxLevel) {
          Table table = getTable();
          List<ITableRow> result = new ArrayList<>();
          List<ITableRow> potentialParents = new ArrayList<>();
          Map<ITableRow, ITableRow> childToParent = new HashMap<>();

          for (int i = 0; i < rowCount; i++) {
            ITableRow parent = null;
            String name = "";
            if (potentialParents.size() > 0 && Math.random() > 0.2) {
              parent = potentialParents.get((int) (Math.random() * potentialParents.size()));
              name = table.getNameColumn().getValue(parent);
            }
            else {
              name = "Row";
            }
            name = name + "_" + i;

            ITableRow row = createRow(parent, name, null, Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalTime.now().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalTime.now().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()), null);
            result.add(row);

            if (parent != null) {
              childToParent.put(row, parent);
            }
            int rowLevel = getRowLevel(row, childToParent);
            if (rowLevel < maxLevel) {
              potentialParents.add(row);
            }
          }
          return result;

        }

        private int getRowLevel(ITableRow row, Map<ITableRow, ITableRow> childToParent) {
          ITableRow parent = childToParent.get(row);
          if (parent != null) {
            return 1 + getRowLevel(parent, childToParent);
          }
          return 0;
        }

        private ITableRow createRow(ITableRow parentRow, String name, String location, Date date, Date startTime, Date endDateTime, Long industery) {
          Table table = getTable();
          ITableRow row = table.createRow();
          table.getIdColumn().setValue(row, m_nextRowId.getAndIncrement());
          table.getParentIdColumn().setValue(row, Optional.ofNullable(parentRow).map(r -> table.getIdColumn().getValue(parentRow)).orElse(null));
          table.getNameColumn().setValue(row, name);
          table.getLocationColumn().setValue(row, location);
          table.getDateColumn().setValue(row, date);
          table.getStartColumn().setValue(row, startTime);
          table.getEndDateTimeColumn().setValue(row, endDateTime);
          table.getIndustryColumn().setValue(row, industery);
          return row;
        }

        private void newRow() {
          newRowWithParent(null);
        }

        private void newRowWithParent(ITableRow parent) {
          Table table = getTable();
          ColumnSet cols = table.getColumnSet();
          ITableRow row = new TableRow(cols);

          row.getCellForUpdate(table.getIdColumn()).setValue(m_nextRowId.getAndIncrement());
          row.getCellForUpdate(table.getParentIdColumn()).setValue(Optional.ofNullable(table.getIdColumn().getValue(parent)).orElse(null));
          row.getCellForUpdate(table.getNameColumn()).setValue("New Row");
          row.getCellForUpdate(table.getTrendColumn()).setValue(AbstractIcons.LongArrowUp);
          ExampleBean bean = new ExampleBean();
          bean.setHeader("header property");
          row.getCellForUpdate(table.getCustomColumn()).setValue(bean);

          table.addRow(row, true);
        }

        @Order(1000)
        public class Table extends AbstractTable {

          public CustomColumn getCustomColumn() {
            return getColumnSet().getColumnByClass(CustomColumn.class);
          }

          public ParentIdColumn getParentIdColumn() {
            return getColumnSet().getColumnByClass(ParentIdColumn.class);
          }

          public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
          }

          public LocationColumn getLocationColumn() {
            return getColumnSet().getColumnByClass(LocationColumn.class);
          }

          public IndustryColumn getIndustryColumn() {
            return getColumnSet().getColumnByClass(IndustryColumn.class);
          }

          public ParticipantsColumn getParticipantsColumn() {
            return getColumnSet().getColumnByClass(ParticipantsColumn.class);
          }

          public WebPageColumn getWebPageColumn() {
            return getColumnSet().getColumnByClass(WebPageColumn.class);
          }

          public PhoneColumn getPhoneColumn() {
            return getColumnSet().getColumnByClass(PhoneColumn.class);
          }

          public TrendColumn getTrendColumn() {
            return getColumnSet().getColumnByClass(TrendColumn.class);
          }

          public StartTimeColumn getStartColumn() {
            return getColumnSet().getColumnByClass(StartTimeColumn.class);
          }

          public EndDateTimeColumn getEndDateTimeColumn() {
            return getColumnSet().getColumnByClass(EndDateTimeColumn.class);
          }

          public LanguageColumn getLanguageColumn() {
            return getColumnSet().getColumnByClass(LanguageColumn.class);
          }

          public DateColumn getDateColumn() {
            return getColumnSet().getColumnByClass(DateColumn.class);
          }

          public IdColumn getIdColumn() {
            return getColumnSet().getColumnByClass(IdColumn.class);
          }

          public AttendedColumn getAttendedColumn() {
            return getColumnSet().getColumnByClass(AttendedColumn.class);
          }

          public MixedStateColumn getMixedStateColumn() {
            return getColumnSet().getColumnByClass(MixedStateColumn.class);
          }

          @Override
          protected void execDecorateRow(ITableRow row) {
            Long participants = getParticipantsColumn().getValue(row);
            if (participants != null && participants > 800) {
              row.setIconId("font:\uE001");
            }
          }

          @Override
          protected void execInitTable() {
            addPropertyChangeListener(
                e -> {
                  if (e.getPropertyName().equals(ITable.PROP_CONTEXT_COLUMN)) {
                    if (e.getNewValue() == null) {
                      getContextColumnField().setValue("");
                      // Is Column Mandatory Field
                      getIsColumnMandatoryField().setEnabled(false);
                      getIsColumnMandatoryField().setValue(false);
                      // Is Column Width Fixed
                      getIsColumnFixedWidthField().setEnabled(false);
                      getIsColumnFixedWidthField().setValue(false);
                    }
                    else {
                      IColumn<?> contextColumn = Table.this.getContextColumn();
                      getContextColumnField().setValue(contextColumn.getHeaderCell().getText());
                      // Is Column Mandatory Field
                      getIsColumnMandatoryField().setEnabled(true);
                      getIsColumnMandatoryField().setValue(contextColumn.isMandatory());
                      // Is Column Width Fixed
                      getIsColumnFixedWidthField().setEnabled(true);
                      getIsColumnFixedWidthField().setValue(contextColumn.isFixedWidth());
                    }
                  }
                });

          }

          @Order(10)
          public class IdColumn extends AbstractLongColumn {

            @Override
            protected boolean getConfiguredDisplayable() {
              return false;
            }

            @Override
            protected boolean getConfiguredPrimaryKey() {
              return true;
            }

          }

          @Order(15)
          @ClassId("f900298c-4770-4983-a42e-bd0385f16846")
          public class ParentIdColumn extends AbstractLongColumn {

            @Override
            protected boolean getConfiguredParentKey() {
              return true;
            }

            @Override
            protected boolean getConfiguredDisplayable() {
              return false;
            }
          }

          @Order(20)
          public class NameColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected boolean getConfiguredMandatory() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Name");
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

          }

          @Order(30)
          public class LocationColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Location");
            }

            @Override
            protected int getConfiguredWidth() {
              return 150;
            }

          }

          @Order(40)
          public class DateColumn extends AbstractDateColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Date");
            }

            @Override
            protected int getConfiguredWidth() {
              return 110;
            }
          }

          @Order(45)
          public class StartTimeColumn extends AbstractTimeColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("StartTime");
            }

            @Override
            protected int getConfiguredWidth() {
              return 110;
            }
          }

          @Order(48)
          public class EndDateTimeColumn extends AbstractDateTimeColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("EndDate");
            }

            @Override
            protected int getConfiguredWidth() {
              return 140;
            }
          }

          @Order(50)
          public class IndustryColumn extends AbstractSmartColumn<Long> {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected boolean getConfiguredBrowseHierarchy() {
              return true;
            }

            @Override
            protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
              return IndustryICBCodeType.class;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Industry");
            }

            @Override
            protected int getConfiguredWidth() {
              return 80;
            }

          }

          @Order(60)
          public class LanguageColumn extends AbstractSmartColumn<Locale> {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Language");
            }

            @Override
            protected int getConfiguredWidth() {
              return 80;
            }

            @Override
            protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
              return (Class<? extends ILookupCall<Locale>>) LocaleLookupCall.class;
            }

          }

          @Order(70)
          public class ParticipantsColumn extends AbstractLongColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Participants");
            }

            @Override
            protected int getConfiguredWidth() {
              return 100;
            }

            /**
             * Accepts only positive values
             */
            @Override
            protected Long execValidateValue(ITableRow row, Long rawValue) {
              if (NumberUtility.nvl(rawValue, 1) < 0) {
                throw new VetoException(TEXTS.get("NoNegNumber"));
              }
              return rawValue;
            }
          }

          @Order(80)
          public class WebPageColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("WebPage");
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

          }

          @Order(90)
          public class AttendedColumn extends AbstractBooleanColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Attended");
            }

            @Override
            protected boolean getConfiguredVisible() {
              return true;
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

          }

          @Order(92)
          public class MixedStateColumn extends AbstractBooleanColumn {

            @Override
            protected boolean getConfiguredTriStateEnabled() {
              return true;
            }

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("MixedState");
            }

            @Override
            protected boolean getConfiguredVisible() {
              return true;
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }
          }

          @Order(100)
          public class PhoneColumn extends AbstractStringColumn {
            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Phone");
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
              cell.setIconId(Icons.Phone);
            }

            @Override
            protected int getConfiguredWidth() {
              return 150;
            }
          }

          @Order(100)
          public class TrendColumn extends AbstractIconColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Trend");
            }

            @Override
            protected int getConfiguredWidth() {
              return 60;
            }

          }

          @Order(110)
          public class TextAndIconHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Text and Icon";
            }

            @Override
            protected String getConfiguredHeaderIconId() {
              return AbstractIcons.Chart;
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }
          }

          @Order(120)
          public class IconHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderIconId() {
              return AbstractIcons.Chart;
            }

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "Icon only";
            }

          }

          @Order(130)
          public class EmptyHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "Empty Header";
            }
          }

          @Order(135)
          public class DateWithEmptyTextColumn extends AbstractDateColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return "Empty Cell Text";
            }

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "This column shows a custom text if there is no value";
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
              if (cell.getValue() == null) {
                cell.setText("-empty-");
              }
            }
          }

          @Order(137)
          public class CustomColumn extends AbstractColumn<ExampleBean> {
            @Override
            protected String getConfiguredHeaderText() {
              return "Custom";
            }

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "This column uses a pojo as data type with a custom formatting. The value is not sent to client.";
            }

            @Override
            protected int getConfiguredWidth() {
              return 100;
            }

            @Override
            protected String formatValueInternal(ITableRow row, ExampleBean value) {
              if (value == null) {
                return null;
              }
              return value.getHeader();
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
              if (row.getRowIndex() == 1) {
                cell.setBackgroundColor("f99494");
              }
            }

          }

          @Order(140)
          public class LegacyHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Legacy Style";
            }

            @Override
            protected String getConfiguredHeaderBackgroundColor() {
              return "ddfff9";
            }

            @Override
            protected String getConfiguredHeaderForegroundColor() {
              return "ff0000";
            }

            @Override
            protected String getConfiguredHeaderFont() {
              return "italic";
            }

            @Override
            protected int getConfiguredWidth() {
              return 100;
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }
          }

          @Order(150)
          public class MultilineHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return "Multiline\nHeader";
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }
          }

          @Order(10)
          public class NewMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("New");
            }

            @Override
            protected void execAction() {
              newRowWithParent(getTable().getSelectedRow());
            }
          }

          @Order(15)
          public class TableStatusVisibleMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Status";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TableMenuType.EmptySpace);
            }

            @Order(10)
            public class RemoveStatusMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "(No status)";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTable().setTableStatus(null);
              }
            }

            @Order(20)
            public class SetSeverityInfoMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Severity INFO";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTable().setTableStatus(new Status("This is an information about this table.", IStatus.INFO));
              }
            }

            @Order(30)
            public class SetSeverityWarningMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Severity WARNING";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTable().setTableStatus(new Status("This is a warning about this table.", IStatus.WARNING));
              }
            }

            @Order(40)
            public class SetSeverityErrorMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Severity ERROR";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTable().setTableStatus(new Status("An error has occurred on the table.", IStatus.ERROR));
              }
            }
          }

          @Order(15)
          public class MoreMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("More");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
            }

            @Order(-1000)
            @ClassId("11420e6a-61df-4406-8b40-ddd49f9b9a95")
            public class ExpandSelectedRowMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Toggle expansion of selected row";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {

                ITableRow selectedRow = getSelectedRow();
                if (selectedRow != null) {
                  getTable().setRowExpanded(selectedRow, !selectedRow.isExpanded());
                }
              }
            }

            @Order(10)
            public class NewDelayedMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewDelayed");
              }

              @Override
              protected void execAction() {
                ModelJobs.schedule(TableField.this::newRow, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(2, TimeUnit.SECONDS)));

              }
            }

            @Order(12)
            @ClassId("cc32400c-a5d9-4621-8e1d-68869170d4d8")
            public class DeleteAllRowsMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("DeleteRows");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTable().deleteAllRows();
              }
            }

            @Order(13)
            @ClassId("cf7c4860-ad75-48c0-9de8-3fe6c875d00d")
            public class AddManyRowsMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("AddManyRows");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {

                TuningUtility.startTimer();
                List<ITableRow> newRows = createRows(10000, 2);
                TuningUtility.stopTimer("createRows");
                TuningUtility.startTimer();
                getTable().addRows(newRows);
                TuningUtility.stopTimer("replacerows");
              }
            }

            @Order(14)
            @ClassId("c8725035-38d9-494c-9f46-0c83074a5498")
            public class AddInitialRowsMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Add initial rows";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTable().addRows(createInitialRows());
              }
            }

            @Order(15)
            public class ScrollToSelection extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ScrollToSelection");
              }

              @Override
              protected void execAction() {
                scrollToSelection();
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }
            }

            @Order(20)
            public class ToggleHierarchicalStyle extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ToggleHierarchicalStyle");
              }

              @Override
              protected void execAction() {
                HierarchicalStyle style = getHierarchicalStyle();
                switch (style) {
                  case STRUCTURED:
                    style = HierarchicalStyle.DEFAULT;
                    break;

                  default:
                    style = HierarchicalStyle.STRUCTURED;
                    break;
                }
                getTable().setHierarchicalStyle(style);
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }
            }
          }

          @Order(20)
          public class DeleteMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection, TableMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Delete");
            }

            @Override
            protected void execAction() {
              List<ITableRow> rows = getSelectedRows();
              deleteRows(rows);
            }
          }

          @Order(25)
          public class DeleteDelayedMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection, TableMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DeleteDelayed");
            }

            @Override
            protected void execAction() {
              ModelJobs.schedule(() -> {
                List<ITableRow> rows = getSelectedRows();
                deleteRows(rows);
              }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                  .withExecutionTrigger(Jobs.newExecutionTrigger()
                      .withStartIn(2, TimeUnit.SECONDS)));
            }
          }

          @Order(40)
          public class HighlightRow extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Highlight");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() {
              for (ITableRow row : getSelectedRows()) {
                if (row.getCssClass() == null) {
                  row.setCssClass("highlight");
                }
                else {
                  row.setCssClass(null);
                }
              }
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
              return HORIZONTAL_ALIGNMENT_RIGHT;
            }
          }

        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(20)
      public class SelectedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SelectedRows");
        }
      }

      @Order(30)
      public class InsertedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InsertedRows");
        }
      }

      @Order(40)
      public class UpdatedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpdatedRows");
        }
      }

      @Order(50)
      public class DeletedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DeletedRows");
        }
      }

      @Order(55)
      public class DefaultIconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DefaultIconId");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getTableField().getTable().setDefaultIconId(getValue());

          // setting the default icon id for existing rows doesn't work.
          // that's why we are setting the icon on all table rows individually as well
          for (ITableRow row : getTableField().getTable().getRows()) {
            row.setIconId(getValue());
          }
        }
      }

      @Order(60)
      public class ContextColumnField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ContextColumn");
        }
      }

      @Order(70)
      public class PropertiesGroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridH() {
          return 8;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(80)
        public class AutoResizeColumnsField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("AutoResizeColumns");
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("AutoResizeColumnsTooltip");
          }

          @Override
          protected boolean getConfiguredFillHorizontal() {
            return false;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setAutoResizeColumns(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isAutoResizeColumns());
          }

        }

        @Order(85)
        public class AutoOptimizeColumnWidthsField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("AutoOptimizeColumnWidths");
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("AutoOptimizeColumnWidthsTooltip");
          }

          @Override
          protected boolean getConfiguredFillHorizontal() {
            return false;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            for (IColumn<?> column : getTableField().getTable().getColumns()) {
              column.setAutoOptimizeWidth(getValue());
            }
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }

        }

        @Order(90)
        public class IsVisibleField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            getLocationColumn().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getLocationColumn().isVisible());
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          private IColumn<String> getLocationColumn() {
            ITable table = getTableField().getTable();
            return table.getColumnSet().getColumnByClass(LocationColumn.class);
          }
        }

        @Order(95)
        public class IsEnabledField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsEnabled");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().setEnabled(getValue(), true, true);
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().isEnabled());
          }
        }

        @Order(100)
        public class IsEditableField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsEditable");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            for (IColumn<?> column : getTableField().getTable().getColumns()) {
              if (column instanceof TrendColumn || column instanceof CustomColumn) {
                continue;
              }
              column.setEditable(getValue());
            }
          }

          @Override
          protected void execInitField() {
            setValue(true);
          }
        }

        @Order(110)
        public class MultiSelectField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("MultiSelect");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setMultiSelect(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isMultiSelect());
          }
        }

        @Order(120)
        public class IsCheckableField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsCheckable");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setCheckable(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isCheckable());
          }

        }

        @Order(130)
        public class IsColumnMandatoryField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ColumnIsMandatory");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            IColumn<?> contextColumn = getTableField().getTable().getContextColumn();
            if (contextColumn != null) {
              contextColumn.setMandatory(getValue());
            }
          }

          @Override
          protected void execInitField() {
            if (getTableField().getTable().getContextColumn() != null) {
              setEnabled(true);
              setValue(getTableField().getTable().getContextColumn().isMandatory());
            }
            else {
              setEnabled(false);
              setValue(false);
            }
          }

        }

        @Order(135)
        public class IsColumnFixedWidthField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ColumnIsFixedWidth");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            IColumn<?> contextColumn = getTableField().getTable().getContextColumn();
            if (contextColumn != null) {
              contextColumn.setFixedWidth(getValue());
            }
          }

          @Override
          protected void execInitField() {
            if (getTableField().getTable().getContextColumn() != null) {
              setEnabled(true);
              setValue(getTableField().getTable().getContextColumn().isFixedWidth());
            }
            else {
              setEnabled(false);
              setValue(false);
            }
          }
        }

        @Order(140)
        public class IsRowIconVisibleField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("RowIconVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setRowIconVisible(getValue());
            getDefaultIconIdField().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isRowIconVisible());
            getDefaultIconIdField().setVisible(getValue());
          }

        }

        @Order(150)
        public class WrapTextField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("WrapText");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().getNameColumn().setTextWrap(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().getNameColumn().isTextWrap());
          }

        }

        @Order(160)
        public class TableStatusVisibleField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TableStatusVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().isTableStatusVisible());
            getTableField().getTable().getMenuByClass(TableStatusVisibleMenu.class).setVisible(getValue());
          }

          @Override
          protected void execChangedValue() {
            getTableField().setTableStatusVisible(getValue());
            getTableField().getTable().getMenuByClass(TableStatusVisibleMenu.class).setVisible(getValue());
          }
        }

        @Order(170)
        public class TableHeaderVisibleField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TableHeaderVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setHeaderVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isHeaderVisible());
          }
        }

        @Order(180)
        public class TableHeaderEnabledField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Table Header Enabled";
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setHeaderEnabled(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isHeaderEnabled());
          }
        }

        @Order(190)
        public class TableSortEnabledField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsSortingEnabled");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setSortEnabled(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isSortEnabled());
          }
        }

        @Order(200)
        public class TruncatedCellTooltipEnabled extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsTruncatedCellTooltipEnabled");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected boolean getConfiguredTriStateEnabled() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            getTableField().getTable().setTruncatedCellTooltipEnabled(TriState.parse(getValue()));
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isTruncatedCellTooltipEnabled().getBooleanValue());
          }
        }

        @Order(210)
        public class ToggleHorizontalAlignmentField extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ToggleHorizontalAlignment");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execClickAction() {
            for (IColumn column : getTableField().getTable().getColumns()) {
              int newAlignment = column.getHorizontalAlignment() + 1;
              if (newAlignment > 1) {
                newAlignment = -1;
              }
              column.setHorizontalAlignment(newAlignment);
            }
          }
        }
      }
    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  public static class LocationLookupCall extends LocalLookupCall<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      final List<ILookupRow<String>> rows = new ArrayList<>();
      for (String location : LOCATIONS) {
        rows.add(new LookupRow<>(location, location));
      }
      return rows;
    }

    @Override
    public List<? extends ILookupRow<String>> getDataByKey() {
      final List<? extends ILookupRow<String>> rows = super.getDataByKey();
      if (!rows.isEmpty() && getKey() == null) {
        return rows;
      }
      else {
        return Collections.singletonList(new LookupRow<>(getKey(), getKey().toString()));
      }
    }
  }
}
