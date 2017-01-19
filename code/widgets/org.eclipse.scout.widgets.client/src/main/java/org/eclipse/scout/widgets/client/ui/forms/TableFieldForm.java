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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ColumnSet;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableAdapter;
import org.eclipse.scout.rt.client.ui.basic.table.TableEvent;
import org.eclipse.scout.rt.client.ui.basic.table.TableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIconColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractProposalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.ContextColumnField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.DefaultIconIdField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.DeletedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.InsertedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.AutoResizeColumnsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsCheckableField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsEditableField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.MultiSelectField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableHeaderVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableStatusVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.WrapTextField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.SelectedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.LocationColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.TableStatusVisibleMenu;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.UpdatedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;

@Order(5000.0)
public class TableFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public static final String[] LOCATIONS = {"San Francisco, USA", "Bruehl, Germany"};

  public TableFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TableField");
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
      public class DefaultField extends AbstractFileTableField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("TableContextMenus");
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      public class TableField extends AbstractTableField<TableField.Table> {

        private long m_maxId = 0;

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TableField");
        }

        private long getNextId() {
          return ++m_maxId;
        }

        public void addExampleRows() {
          Table table = getTable();
          ITableRow r;

          //First Row:
          r = table.addRow(getTable().createRow());
          table.getIdColumn().setValue(r, getNextId());
          table.getNameColumn().setValue(r, "Eclipsecon USA");
          table.getLocationColumn().setValue(r, LOCATIONS[0]);
          table.getDateColumn().setValue(r, DateUtility.parse("18.03.2014", "dd.MM.yyyy"));
          table.getStartColumn().setValue(r, DateUtility.parse("18.03.2014 09:00", "dd.MM.yyyy HH:mm"));
          table.getEndDateTimeColumn().setValue(r, DateUtility.parse("20.03.2014 17:45", "dd.MM.yyyy HH:mm"));
          table.getIndustryColumn().setValue(r, IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537.ID);
          table.getParticipantsColumn().setValue(r, 680L);
          table.getWebPageColumn().setValue(r, "http://www.eclipsecon.org");
          table.getPhoneColumn().setValue(r, "+41 (0)79 123 45 67");
          table.getTrendColumn().setValue(r, AbstractIcons.LongArrowUp);
          table.getLanguageColumn().setValue(r, new Locale("en", "US"));

          //Second Row:
          r = table.addRow(getTable().createRow());
          table.getIdColumn().setValue(r, getNextId());
          table.getNameColumn().setValue(r, "Javaland");
          table.getLocationColumn().setValue(r, LOCATIONS[1]);
          table.getDateColumn().setValue(r, DateUtility.parse("25.03.2014", "dd.MM.yyyy"));
          table.getStartColumn().setValue(r, DateUtility.parse("18.03.2014 09:00", "dd.MM.yyyy HH:mm"));
          table.getEndDateTimeColumn().setValue(r, DateUtility.parse("20.03.2014 17:45", "dd.MM.yyyy HH:mm"));
          table.getIndustryColumn().setValue(r, IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537.ID);
          table.getParticipantsColumn().setValue(r, 810L);
          table.getWebPageColumn().setValue(r, "http://www.javaland.eu");
          table.getAttendedColumn().setValue(r, true);
          table.getTrendColumn().setValue(r, AbstractIcons.LongArrowDown);
          table.getLanguageColumn().setValue(r, new Locale("de", "DE"));
        }

        private String rowsToKeyString(List<ITableRow> list) {
          if (list == null || list.size() == 0) {
            return "";
          }

          StringBuffer buf = new StringBuffer(Long.toString((Long) list.get(0).getCellValue(0)));
          for (int i = 1; i < list.size(); i++) {
            buf.append(";" + (Long) list.get(i).getCellValue(0));
          }

          return buf.toString();
        }

        public class Table extends AbstractTable {

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
                new PropertyChangeListener() {
                  @Override
                  public void propertyChange(PropertyChangeEvent e) {
                    if (e.getPropertyName().equals(ITable.PROP_CONTEXT_COLUMN)) {
                      if (e.getNewValue() == null) {
                        getContextColumnField().setValue("");
                      }
                      else {
                        getContextColumnField().setValue(Table.this.getContextColumn().getHeaderCell().getText());
                      }
                    }
                  }
                });

            addTableListener(new TableAdapter() {

              @Override
              public void tableChanged(TableEvent e) {
                if (getRootGroupBox() != null) {
                  getSelectedRowsField().setValue(rowsToKeyString(Table.this.getSelectedRows()));
                  getInsertedRowsField().setValue(rowsToKeyString(Table.this.getInsertedRows()));
                  getUpdatedRowsField().setValue(rowsToKeyString(Table.this.getUpdatedRows()));
                  getDeletedRowsField().setValue(rowsToKeyString(Table.this.getDeletedRows()));
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

            @Override
            protected boolean getConfiguredVisible() {
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
          public class LocationColumn extends AbstractProposalColumn<String> {

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

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return LocationLookupCall.class;
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
              cell.setIconId("font:\uE011");//Icons.Phone
            }
          }

          @Order(100)
          public class TrendColumn extends AbstractIconColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Trend");
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

          private void newRow() {
            ColumnSet cols = getColumnSet();
            ITableRow row = new TableRow(cols);

            row.getCellForUpdate(getIdColumn()).setValue(++m_maxId);
            row.getCellForUpdate(getNameColumn()).setValue("New Row");
            row.getCellForUpdate(getTrendColumn()).setValue(AbstractIcons.LongArrowUp);

            addRow(row, true);
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
              newRow();
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
                ModelJobs.schedule(new IRunnable() {
                  @Override
                  public void run() throws Exception {
                    newRow();
                  }
                }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(2, TimeUnit.SECONDS)));

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
              ModelJobs.schedule(new IRunnable() {
                @Override
                public void run() throws Exception {
                  List<ITableRow> rows = getSelectedRows();
                  deleteRows(rows);
                }
              }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                  .withExecutionTrigger(Jobs.newExecutionTrigger()
                      .withStartIn(2, TimeUnit.SECONDS)));
            }
          }

          @Order(30)
          public class ToggleRowEnabledMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ToggleRowEnabled");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() {
              getSelectedRow().setEnabled(!getSelectedRow().isEnabled());
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return HORIZONTAL_ALIGNMENT_RIGHT;
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
            protected int getConfiguredHorizontalAlignment() {
              return HORIZONTAL_ALIGNMENT_RIGHT;
            }
          }

          @Order(50)
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

          @Order(60)
          public class KeyStroke extends AbstractKeyStroke {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected String getConfiguredKeyStroke() {
              return "Ctrl-Shift-Alt-h";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withHeader(getConfiguredKeyStroke() + " activated").show();
            }
          }
        }
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

      @Order(60)
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

      @Order(70)
      public class PropertiesGroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridH() {
          return 6;
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
            getTableField().setEnabled(getValue());
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
            setValue(getTableField().getTable().isHeaderVisible());
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

        @Order(122)
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

        @Order(125)
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

        @Order(130)
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

        @Order(140)
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

        @Order(142)
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

        @Order(145)
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

        @Order(150)
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

    @Order(40)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getTableField().addExampleRows();
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
        rows.add(new LookupRow<String>(location, location));
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
