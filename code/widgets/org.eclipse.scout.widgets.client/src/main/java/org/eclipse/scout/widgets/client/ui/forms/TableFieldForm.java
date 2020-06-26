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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTileTableHeader;
import org.eclipse.scout.rt.client.ui.basic.table.CheckableStyle;
import org.eclipse.scout.rt.client.ui.basic.table.ColumnSet;
import org.eclipse.scout.rt.client.ui.basic.table.GroupingStyle;
import org.eclipse.scout.rt.client.ui.basic.table.HeaderCell;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableEvent;
import org.eclipse.scout.rt.client.ui.basic.table.TableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
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
import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.ContextColumnField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.DefaultIconIdField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.DeletedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.InsertedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.AutoResizeColumnsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsCheckableField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsColumnFixedWidthField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsColumnMandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsEditableField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.MultiSelectField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableHeaderVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableStatusVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TileModeField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.WrapTextField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.SelectedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.CustomColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.LocationColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.TableStatusVisibleMenu;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.TrendColumn;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.UpdatedRowsField;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TableFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537;

@Order(5000.0)
@ClassId("ade82b46-9eca-44c2-8dd2-b90389f7bb67")
public class TableFieldForm extends AbstractForm implements IPageForm {

  public static final String[] LOCATIONS = {"San Francisco, USA", "Bruehl, Germany"};

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

  public TileModeField getTileModeField() {
    return getFieldByClass(TileModeField.class);
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
  @ClassId("10650cd0-7e98-4a41-b05a-e2d5b2d955d4")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("8c29e2ae-eea9-4163-af41-8752d9f9e753")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("cbf13840-fc34-4ecb-8c9a-e3888c557dd5")
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
    @ClassId("194591df-39d6-475f-a7d4-82b72c483e90")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("aebd5c7d-a9fa-40b8-8427-edde84fcce89")
      public class TableField extends AbstractTableField<Table> {

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
          table.getIndustryColumn().setValue(r, ICB9537.ID);
          table.getParticipantsColumn().setValue(r, 680L);
          table.getWebPageColumn().setValue(r, "http://www.eclipsecon.org");
          table.getAttendedColumn().setValue(r, null);
          table.getMixedStateColumn().setValue(r, null);
          table.getPhoneColumn().setValue(r, "+41 (0)79 123 45 67");
          table.getImageColumn().setValue(r, "large");
          table.getTrendColumn().setValue(r, AbstractIcons.LongArrowUpBold);
          table.getLanguageColumn().setValue(r, new Locale("en", "US"));
          table.getHtmlEnabledColumn().setValue(r, HTML.appLink("link", "App Link").toHtml());

          //Second Row:
          r = table.addRow(getTable().createRow());
          table.getIdColumn().setValue(r, getNextId());
          table.getNameColumn().setValue(r, "Javaland");
          table.getLocationColumn().setValue(r, LOCATIONS[1]);
          table.getDateColumn().setValue(r, DateUtility.parse("25.03.2014", "dd.MM.yyyy"));
          table.getStartColumn().setValue(r, DateUtility.parse("18.03.2014 09:00", "dd.MM.yyyy HH:mm"));
          table.getEndDateTimeColumn().setValue(r, DateUtility.parse("20.03.2014 17:45", "dd.MM.yyyy HH:mm"));
          table.getIndustryColumn().setValue(r, ICB9537.ID);
          table.getParticipantsColumn().setValue(r, 810L);
          table.getWebPageColumn().setValue(r, "http://www.javaland.eu");
          table.getAttendedColumn().setValue(r, true);
          table.getMixedStateColumn().setValue(r, true);
          table.getPhoneColumn().setValue(r, null);
          table.getImageColumn().setValue(r, "small");
          table.getTrendColumn().setValue(r, AbstractIcons.LongArrowDownBold);
          table.getLanguageColumn().setValue(r, new Locale("de", "DE"));
          table.getHtmlEnabledColumn().setValue(r, HTML.appLink("link", "App Link").toHtml());
        }

        private String rowsToKeyString(List<ITableRow> list) {
          if (list == null || list.isEmpty()) {
            return "";
          }

          StringBuilder buf = new StringBuilder(Long.toString((Long) list.get(0).getCellValue(0)));
          for (int i = 1; i < list.size(); i++) {
            buf.append(";").append(list.get(i).getCellValue(0));
          }

          return buf.toString();
        }

        @ClassId("bffc9432-ffdf-4b6b-9644-069aaf62e0fb")
        public class Table extends AbstractTable {

          public CustomColumn getCustomColumn() {
            return getColumnSet().getColumnByClass(CustomColumn.class);
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

          public ImageColumn getImageColumn() {
            return getColumnSet().getColumnByClass(ImageColumn.class);
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

          public HtmlEnabledColumn getHtmlEnabledColumn() {
            return getColumnSet().getColumnByClass(HtmlEnabledColumn.class);
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
          protected void execAppLinkAction(String ref) {
            MessageBoxes.createOk().withBody("Link of row " + getNameColumn().getSelectedValue() + " has been clicked.").show();
          }

          @Override
          protected void execInitTable() {
            //noinspection DuplicatedCode
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

            addTableListener(e -> {
              if (getRootGroupBox() != null) {
                getSelectedRowsField().setValue(rowsToKeyString(Table.this.getSelectedRows()));
                getInsertedRowsField().setValue(rowsToKeyString(Table.this.getInsertedRows()));
                getUpdatedRowsField().setValue(rowsToKeyString(Table.this.getUpdatedRows()));
                getDeletedRowsField().setValue(rowsToKeyString(Table.this.getDeletedRows()));
              }
            });
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
                    HTML.br(), HTML.bold("Name: " + row.getCellValue(getNameColumn().getColumnIndex())),
                    HTML.br(), HTML.bold("Location: " + row.getCellValue(getLocationColumn().getColumnIndex())),
                    HTML.br(), HTML.bold("Date: " + row.getCellValue(getDateColumn().getColumnIndex())),
                    HTML.br(), HTML.bold("Industry: " + row.getCellValue(getIndustryColumn().getColumnIndex()))).toHtml();
              }
            };
          }

          @Order(10)
          @ClassId("d4f34a6e-048a-4879-bafb-51d1b7f8ba0d")
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
          @ClassId("70915c92-acd5-4b58-8b9a-2164eb4a810f")
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
          @ClassId("bfbcf2c7-9532-40f5-8b25-93078ed6f41f")
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
          @ClassId("70c106fb-5391-4abb-a8d1-54b5661d319e")
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
          @ClassId("8948051f-fbea-4047-be7c-70590882d2d0")
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
          @ClassId("a2217ed3-7e94-4110-b3aa-2416a7eac95c")
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
          @ClassId("3772d988-6cb7-41fa-bac9-df4491a17ed0")
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
          @ClassId("98d5e29f-de27-4b59-8204-632cabdd1400")
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
              return LocaleLookupCall.class;
            }

          }

          @Order(70)
          @ClassId("185fc0b6-d909-4271-afb3-8647478cf364")
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
          @ClassId("fe99af5b-0419-4e18-8f17-9393df1cc3fb")
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
          @ClassId("d65e7f29-0364-4386-a5e0-6974344444e9")
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
          @ClassId("22523dfc-1aa7-43c3-b12b-bf9b07e7ea6c")
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
          @ClassId("705ad496-494e-4852-81ff-b6fad158835a")
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
          @ClassId("b8198666-b8f2-40c3-8219-fd4a33a9135d")
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

          @Order(105)
          @ClassId("ece26c29-1ff8-4c9f-80bb-5dd2a7a644d3")
          public class ImageColumn extends AbstractStringColumn {
            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return "Image";
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
              if ("large".equals(cell.getValue())) {
                cell.setIconId("eclipse16"); // larger icon
              }
              else if ("small".equals(cell.getValue())) {
                cell.setIconId(Icons.EclipseScout);
              }
              else {
                cell.setIconId(null);
              }
            }

            @Override
            protected int getConfiguredWidth() {
              return 150;
            }
          }

          @Order(110)
          @ClassId("8b479321-62d0-4a9b-af4d-a31f4a8c01b9")
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
          @ClassId("e70f80e2-436c-4f80-875e-3f1741aa303c")
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
          @ClassId("16a54f05-8656-4890-af03-0e7ce5c527c6")
          public class EmptyHeaderColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderTooltipText() {
              return "Empty Header";
            }
          }

          @Order(135)
          @ClassId("c6702877-4f14-44da-aab2-74793814165e")
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
          @ClassId("6cdd58cb-34e3-4f6c-8cdb-7ae89dde2b06")
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

          @Order(138)
          @ClassId("3d4efc39-bdc0-4527-8846-375743133f22")
          public class HtmlEnabledColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
              return "Html Enabled";
            }

            @Override
            protected boolean getConfiguredHtmlEnabled() {
              return true;
            }

            @Override
            protected int getConfiguredWidth() {
              return 130;
            }
          }

          @Order(140)
          @ClassId("2a7608fc-1df6-442d-8ef2-dd7f46a5c2ae")
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
          @ClassId("b7a5ca0e-39fa-473c-9d92-9c8849f1a4f1")
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
            row.getCellForUpdate(getTrendColumn()).setValue(AbstractIcons.LongArrowUpBold);
            ExampleBean bean = new ExampleBean();
            bean.setHeader("header property");
            row.getCellForUpdate(getCustomColumn()).setValue(bean);

            addRow(row, true);
          }

          @Order(10)
          @ClassId("4387270a-0be4-459f-83a4-9583190f8871")
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
          @ClassId("a09f638b-4278-44d9-8669-5c4f075fe444")
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
            @ClassId("ac46c70b-0efb-4974-b359-9c5a990fbdb7")
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
            @ClassId("fd06fd59-0de4-4dac-94ee-0ea96c236866")
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
            @ClassId("7a25ecaa-82bc-4aa1-9255-dcf6d056e16e")
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
            @ClassId("af188f37-f842-4d5d-ae81-c9ada7d01384")
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
          @ClassId("fd00e286-e6e3-4e32-88ac-cecda2d8274d")
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
            @ClassId("6d04bccf-436c-4445-8e43-7b9cfd8bb2ca")
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
                ModelJobs.schedule(Table.this::newRow, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(2, TimeUnit.SECONDS)));

              }
            }

            @Order(12)
            @ClassId("021a7381-a8d7-4082-a70b-b460fc635d80")
            public class CompleteCellEditDelayedMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Complete cell-edit delayed";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Starts a timer which calls completeCellEdit() on the table after 5 seconds have passed. " +
                    "Click this menu, then click on an editable cell and wait until the cell is being closed.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                ModelJobs.schedule(Table.this::completeCellEdit, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(5, TimeUnit.SECONDS)));
              }
            }

            @Order(15)
            @ClassId("65e50c24-14d1-4430-9ad6-03179028c9ee")
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
            @ClassId("09b54c41-321e-404c-aba9-543db48d2fd0")
            public class ToggleGroupingStyle extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ToggleGroupingStyle");
              }

              @Override
              protected void execAction() {
                ITable table = getTable();
                GroupingStyle groupingStyle = table.getGroupingStyle();
                if (groupingStyle == GroupingStyle.BOTTOM) {
                  table.setGroupingStyle(GroupingStyle.TOP);
                }
                else {
                  table.setGroupingStyle(GroupingStyle.BOTTOM);
                }
                setTooltipText(TEXTS.get("CurrentGroupingStyle", getTable().getGroupingStyle().name()));
              }

              @Override
              protected String getConfiguredTooltipText() {
                return TEXTS.get("CurrentGroupingStyle", GroupingStyle.BOTTOM.name());
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }
            }

            /**
             * Example how to update a header text dynamically at runtime, outside of the regular Table lifecycle.
             */
            @Order(30)
            @ClassId("48f1cf80-5cc5-4dfa-b494-7c0395c0b428")
            public class UpdateHeaderTextMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("UpdateColumnHeaderText");
              }

              @Override
              protected void execAction() {
                String seconds = DateUtility.format(new Date(), "ss");
                Table table = getTableField().getTable();
                NameColumn column = table.getNameColumn();
                ((HeaderCell) column.getHeaderCell()).setText("Dynamic text " + seconds);
                TableEvent event = new TableEvent(table, TableEvent.TYPE_COLUMN_HEADERS_UPDATED);
                event.setColumns(table.getColumns());
                table.fireTableEventInternal(event);
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }
            }
          }

          @Order(20)
          @ClassId("c50b7e54-6c0f-42fe-9d14-19c202712f9d")
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
          @ClassId("49539b26-6ceb-44cf-9d76-e974c947c054")
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

          @Order(30)
          @ClassId("c8e313dd-c569-491a-8413-8026034d7fbf")
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
            protected byte getConfiguredHorizontalAlignment() {
              return HORIZONTAL_ALIGNMENT_RIGHT;
            }
          }

          @Order(40)
          @ClassId("ef8c48e3-7dfe-4ec8-bcd6-bffc06f6371c")
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

          @Order(50)
          @ClassId("7a25645e-4c8f-4159-aa17-311095029a1b")
          public class HierarchicalMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Hierarchical Menu";
            }

            @Order(10)
            @ClassId("f8dd6088-48c0-4c9c-b303-c37d92cd6b54")
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
            @ClassId("5fca819d-060d-4973-a43a-afcb2cd768f4")
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
            @ClassId("44a4285a-d845-41f2-9ad5-0583a1fbcb5a")
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
            @ClassId("00261499-2b22-4832-8c0a-287959dc6a9f")
            public class IntermediateMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Intermediate Menu";
              }

              @Order(10)
              @ClassId("ec2268e5-0e52-4311-a9c7-f457d733ada4")
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
              @ClassId("ee402813-5cdf-4f5a-b6c0-eb6d0a0bcd53")
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
              @ClassId("1ca381c9-73f9-4bdc-b4c2-84b95b617f45")
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
          @ClassId("6c2ccfbb-21a5-4dc8-9967-d2292d60f1f8")
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

          @Order(100)
          @ClassId("539be567-f70d-4421-a49b-90633e87390f")
          public class TileTableHeader extends AbstractTileTableHeader {

          }
        }
      }

      @Order(20)
      @ClassId("717a9b9a-1778-4eb4-a329-528c9dc0bd75")
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
      @ClassId("70de935a-1fef-410b-9eca-6379d9f1564f")
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
      @ClassId("be7d524c-0712-4ce6-853a-a9e9249bc7fa")
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
      @ClassId("0ace8942-b071-4bef-9bd0-d8281004c2e6")
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
      @ClassId("99b0fb9d-fb00-4ae9-a11e-04de5925e433")
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
      @ClassId("28346db9-2639-4ca6-b0d9-75a1139defae")
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
          return IconIdLookupCall.class;
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

      @Order(65)
      @ClassId("1c1a56ba-ba54-40b9-8004-8b040d0667b2")
      public class CheckableStyleField extends AbstractSmartField<CheckableStyle> {

        @Override
        protected String getConfiguredLabel() {
          return "Checkable Style";
        }

        @Override
        protected String getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DROPDOWN;
        }

        @Override
        protected Class<? extends ILookupCall<CheckableStyle>> getConfiguredLookupCall() {
          return TableCheckableStyleLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getTableField().getTable().setCheckableStyle(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTableField().getTable().getCheckableStyle());
        }
      }

      @Order(70)
      @ClassId("504771b2-5fca-4eeb-9d17-590fa8cc2aab")
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
        @ClassId("2e267e28-ae4c-4fac-a99c-0a13af1f692a")
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
        @ClassId("529abe9b-8131-42c3-ac27-b0d2094299cc")
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
        @ClassId("f228e6ec-768d-43de-bc08-075552509f39")
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
        @ClassId("b2a3ea75-31f4-4852-b384-51de513d72cd")
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
        @ClassId("27cb771a-37ca-49ad-8e32-0c1eb1ab7120")
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
        @ClassId("d834dd14-9ebd-4e32-8d76-a977756d5728")
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

        @Order(115)
        @ClassId("9a9266b1-b828-4213-9fea-c43c3f16739a")
        public class MultilineTextField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Multiline Text";
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
            getTableField().getTable().setMultilineText(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isMultilineText());
          }
        }

        @Order(120)
        @ClassId("009e0fd8-2521-429f-81e0-ac0ea90c8859")
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

        @Order(125)
        @ClassId("60336300-0bb4-47bb-aad1-b325167445d0")
        public class IsCompactField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Compact";
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
            getTableField().getTable().setCompact(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isCompact());
          }
        }

        @Order(130)
        @ClassId("176a4265-9c6c-4164-9ad3-0c721bae3dfc")
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
        @ClassId("60812cde-a167-4c54-99cb-5715a6d80c9d")
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
        @ClassId("94a196c3-c4bd-44c2-8afc-c413eaac82fa")
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
        @ClassId("5b663bbf-5637-4d1a-9cd7-4f343df281b9")
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
        @ClassId("fc848c32-eded-4a86-9096-ef2df9f2913f")
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
        @ClassId("35fc626e-8116-401f-87af-ba545996b07e")
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
        @ClassId("d3bbc294-ebf6-4de8-aa7f-a7cfb3140e06")
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
        @ClassId("74481a5c-fb39-4102-a130-addb942b7600")
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
        @ClassId("cb571b93-e4d7-4bc2-b768-af186e2f12d4")
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
        @ClassId("828664de-34c0-48cf-8463-77855665c10e")
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
        @ClassId("1a9a423a-b3c6-4bf1-96dd-3a8e4a2c3df5")
        public class TileModeField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Tile Mode";
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
            getTableField().getTable().setTileMode(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getTableField().getTable().isTileMode());
          }
        }

        @Order(220)
        @ClassId("ac90d60a-a193-4ec1-9705-6c592484436c")
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
    @ClassId("62ec8650-74d8-4207-94d7-9d5c9d817a7f")
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
    @ClassId("b655fd0c-facd-4f6d-86aa-a871d63264de")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ClassId("6f90ab0e-3f3b-467a-83f2-bbf9396fad93")
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
        return Collections.singletonList(new LookupRow<>(getKey(), getKey()));
      }
    }
  }
}
