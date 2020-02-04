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
@ClassId("661d5956-e2ed-4c1f-94fb-f1656dd7dfd9")
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
  @ClassId("a60b6f58-fe16-4cd5-869f-283d79e6842f")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("2900411b-27ca-47b0-9039-cd6d9753cf02")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("96ce0861-6a2b-4637-859c-f7a5ab52b42e")
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
        @ClassId("d6ea2d9b-3329-48b6-be8c-62aa2baf9258")
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
          @ClassId("015260b2-ba04-4920-b059-fa0bf419036a")
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
          @ClassId("c6103c38-5042-4907-b1a0-03615d2ff289")
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
          @ClassId("4bd0de11-cdb0-4130-8bd7-008c1cbd05cf")
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
          @ClassId("fe34d701-530c-4294-af63-3e8b412af027")
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
          @ClassId("e5d1cb72-c38e-467a-b1b1-b8251b228f1c")
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
          @ClassId("5769ab4d-e0fa-4134-97c2-d4a7d555e4ef")
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
          @ClassId("7600b717-df38-44fd-8363-dd0df5318099")
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
          @ClassId("965a00e9-3dc0-49fe-a70f-bc9d0e6a6749")
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
          @ClassId("addde7ad-5240-444e-b8bc-6430ada5be29")
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
          @ClassId("4ee90db0-ed18-4547-a5d6-27e82076b0d5")
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
          @ClassId("820147c8-c24b-4344-9ee7-d6613bd7c7f4")
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
          @ClassId("594339a3-2f6f-4e83-bcdb-0652f856a560")
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
          @ClassId("633659b8-7e93-4a95-a8c1-09e5e1307296")
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
          @ClassId("957b781c-80c5-470f-b30c-98651153fd1b")
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
          @ClassId("b5da50b7-3ac6-47d8-858c-7abc9691f306")
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
          @ClassId("9dff11fb-85ec-49ec-90e7-c9fd9ca5e4b4")
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
          @ClassId("907f22c9-ed33-459e-a379-e3272631fbd3")
          public class EmptyHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "Empty Header";
            }
          }

          @Order(135)
          @ClassId("fb556f40-688a-49c5-888f-b6e8ec147d92")
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
          @ClassId("adac4871-fe64-47b3-b97c-967cc3555e25")
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
          @ClassId("d3e65cf4-93f1-45e1-952f-2d85cc3aa234")
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
          @ClassId("71e39327-5a2c-4dd2-84fb-e6a1bc94ecc6")
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
          @ClassId("26399b5c-0cf6-4893-b217-dfcb6e44792a")
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
          @ClassId("9bedc317-87a4-41cd-ac16-8855c7e04dc5")
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
            @ClassId("30127e1f-09aa-4c34-a942-144b781fd331")
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
            @ClassId("8e9ffae2-af6b-4f8e-a67b-81f3916ec346")
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
            @ClassId("d6f2bf26-26c0-4b09-a3de-468a7d320ac6")
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
            @ClassId("1ce6f398-2f6e-45d0-ad07-7d0902a4b984")
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
          @ClassId("bce638bb-73ff-4cd4-a371-09ebc1af9a9b")
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
            @ClassId("fc4084b5-35e9-4473-80f0-3b93b682ab18")
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
            @ClassId("891ee02d-a03b-4570-b248-d913145a8b71")
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
            @ClassId("70d284f2-6fcd-4bb2-be15-b0b61d1bcd53")
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
          @ClassId("821f23d5-2eef-4647-9b12-8fd21d032606")
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
          @ClassId("dbf7c7d0-c75a-4eac-b9f1-3e86d6b6c18d")
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
          @ClassId("3304983c-9cda-4b67-b905-a1a2ff62ccbb")
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
    @ClassId("8f9634e0-afb0-435f-8d41-40015252b082")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(20)
      @ClassId("55b0ff57-f638-446d-864d-176922df8845")
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
      @ClassId("4b7076c9-3389-4cc4-b8c9-cca6b12f3394")
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
      @ClassId("1ad722a6-6b7e-4282-af68-c2300b36d96f")
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
      @ClassId("93fa8899-e730-4fac-b67d-62d58147cc29")
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
      @ClassId("b124784e-8f64-47bc-bd1e-ec17b67ee469")
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
      @ClassId("13748246-620c-4fad-aeb5-8cccc064d3d1")
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
      @ClassId("deec15bb-0df4-4c1a-a755-dd535ea7b8b3")
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
        @ClassId("e6429d0d-6cba-4b21-b1b9-a5fb46076ef8")
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
        @ClassId("4baa9bf2-2abd-4db2-a912-77fab25ca953")
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
        @ClassId("6bfb8099-4348-474f-bf93-cd5a1aac98a5")
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
        @ClassId("088ef80b-b5e0-4d9b-81a6-55d89fd40a66")
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
        @ClassId("170ea18f-3ff8-409b-b7ef-44d009054c87")
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
        @ClassId("d0d0da23-44d9-4af6-9ebe-d29b3cc1c1a8")
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
        @ClassId("df01036c-f50f-4494-bcc9-73f28e074928")
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
        @ClassId("f9bb6014-2e36-4ace-84b9-5bb6ee056edf")
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
        @ClassId("df7d782a-628f-43a1-97dd-a1a9d1ba326c")
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
        @ClassId("60062570-9d93-480c-966b-0653f930d93e")
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
        @ClassId("c92c5855-0f0e-4888-a06d-9b358620b1bc")
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
        @ClassId("b4ef6afd-aaa7-455a-ab23-ff217d6a9643")
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
        @ClassId("43bfd37b-a064-433c-bce1-f66207f3342c")
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
        @ClassId("ca823306-10c7-42e3-b60e-4c0c6d2ae320")
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
        @ClassId("e27f52ab-b557-40a8-b39a-9a1d4e06d2cb")
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
        @ClassId("bafe86c7-41e7-4486-b5e2-7dfb06115652")
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

        @Order(205)
        @ClassId("17be4d04-504b-47ba-8f57-0496d8cf68e7")
        public class ScrollToSelection extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return "Scroll To Selection";
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
            getTableField().getTable().setScrollToSelection(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isScrollToSelection());
          }
        }

        @Order(210)
        @ClassId("42675dee-0c27-4848-8577-693c7494385c")
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
    @ClassId("41c683e1-bc77-48d2-bdd1-fd61a0058385")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ClassId("3f50e5dc-6f07-4312-9ced-7edf0954b999")
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
