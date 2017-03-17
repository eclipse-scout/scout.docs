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

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.ScoutTexts;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserBox.UploadButtonsBox.UploadMultipleFilesButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserBox.UploadButtonsBox.UploadSingleFileButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserFieldBox.ChooseAnImageField;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserFieldBox.PropertiesGroupBox.MaximumUploadSizeField;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.ServerLogBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.ServerLogBox.ServerLogField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractStatusButton;

@Order(8050)
public class FileChooserFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public FileChooserFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FileChooserField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public ChooseAnImageField getChooseAnImageField() {
    return getFieldByClass(ChooseAnImageField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public FileChooserFieldBox getFileChooserFieldBox() {
    return getFieldByClass(FileChooserFieldBox.class);
  }

  public FileChooserBox getFileChooserBox() {
    return getFieldByClass(FileChooserBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MaximumUploadSizeField getMaximumUploadSizeField() {
    return getFieldByClass(MaximumUploadSizeField.class);
  }

  public ServerLogBox getServerLogBox() {
    return getFieldByClass(ServerLogBox.class);
  }

  public ServerLogField getServerLogField() {
    return getFieldByClass(ServerLogField.class);
  }

  public UploadMultipleFilesButton getUploadMultipleFilesButton() {
    return getFieldByClass(UploadMultipleFilesButton.class);
  }

  public UploadSingleFileButton getUploadSingleFileButton() {
    return getFieldByClass(UploadSingleFileButton.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected boolean getConfiguredBorderVisible() {
        return false;
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      public class FileChooserFieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "File Chooser Field";
        }

        @Order(20)
        public class ChooseAnImageField extends AbstractFileChooserField {

          @Override
          protected List<String> getConfiguredFileExtensions() {
            return CollectionUtility.arrayList("png", "bmp", "jpg", "jpeg", "gif");
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ChooseAnImage");
          }

          @Override
          protected void execChangedValue() {
            getServerLogField().addLine(getValue());
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
            return 2;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          public class EnabledField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Enabled";
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
              getChooseAnImageField().setEnabled(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getChooseAnImageField().isEnabled());
            }
          }

          @Order(20)
          public class MandatoryField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Mandatory";
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
              getChooseAnImageField().setMandatory(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getChooseAnImageField().isMandatory());
            }
          }

          @Order(30)
          public class StatusButton extends AbstractStatusButton {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected IFormField getField() {
              return getChooseAnImageField();
            }

            @Override
            protected String getConfiguredFont() {
              return "ITALIC";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }
          }

          @Order(40)
          @ClassId("8bb43b90-eb17-40c4-ac6a-62de849e1c5c")
          public class MaximumUploadSizeField extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return "Max. upload size";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Maximum file size in bytes that is accepted by the file chooser field.";
            }

            @Override
            protected String getConfiguredLabelFont() {
              return "ITALIC";
            }

            @Override
            protected boolean getConfiguredMandatory() {
              return true;
            }

            @Override
            protected void execInitField() {
              setValue(getChooseAnImageField().getMaximumUploadSize());
            }

            @Override
            protected Long execValidateValue(Long rawValue) {
              if (rawValue == null || rawValue < 0) {
                throw new VetoException(ScoutTexts.get("InvalidValueMessageX", ObjectUtility.toString(rawValue)));
              }
              return super.execValidateValue(rawValue);
            }

            @Override
            protected void execChangedValue() {
              getChooseAnImageField().setMaximumUploadSize(getValue());
            }
          }
        }
      }

      @Order(30)
      public class FileChooserBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "File Chooser";
        }

        @Order(10)
        public class UploadButtonsBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(40)
          public class UploadSingleFileButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("UploadSingleFile");
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected void execClickAction() {
              FileChooser fc = new FileChooser(false);
              List<BinaryResource> files = fc.startChooser();
              for (BinaryResource file : files) {
                getServerLogField().addLine(file);
              }
            }
          }

          @Order(50)
          public class UploadMultipleFilesButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("UploadMultipleFiles");
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected void execClickAction() {
              FileChooser fc = new FileChooser(true);
              List<BinaryResource> files = fc.startChooser();
              for (BinaryResource file : files) {
                getServerLogField().addLine(file);
              }
            }
          }
        }
      }

      @Order(60)
      public class ServerLogBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ServerLog");
        }

        @Order(1000)
        public class ServerLogField extends AbstractTableField<ServerLogField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          public void addLine(BinaryResource file) {
            ITableRow row = getTable().addRow(getTable().createRow());
            getTable().getFileColumn().setValue(row, file);
            getTable().getTimeColumn().setValue(row, new Date());
            getTable().getActionColumn().setValue(row, "received " + (file == null ? "no file" : "file: " + file.getFilename()));
            getTable().selectLastRow();
            getTable().scrollToSelection();
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          public class Table extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            @Override
            protected Class<? extends IMenu> getConfiguredDefaultMenu() {
              return OpenFileMenu.class;
            }

            public FileColumn getFileColumn() {
              return getColumnSet().getColumnByClass(FileColumn.class);
            }

            public TimeColumn getTimeColumn() {
              return getColumnSet().getColumnByClass(TimeColumn.class);
            }

            public ActionColumn getActionColumn() {
              return getColumnSet().getColumnByClass(ActionColumn.class);
            }

            @Order(10)
            public class FileColumn extends AbstractColumn<BinaryResource> {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(20)
            public class TimeColumn extends AbstractTimeColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Time");
              }

              @Override
              protected int getConfiguredWidth() {
                return 120;
              }

              @Override
              protected boolean getConfiguredFixedWidth() {
                return true;
              }
            }

            @Order(80)
            public class ActionColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Action");
              }

              @Override
              protected int getConfiguredWidth() {
                return 120;
              }
            }

            @Order(90)
            public class ClearMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Clear");
              }

              @Override
              protected void execAction() {
                getTable().deleteAllRows();
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection, ValueFieldMenuType.NotNull, TableMenuType.MultiSelection, TableMenuType.EmptySpace);
              }
            }

            @Order(100)
            public class OpenFileMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Download file";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection);
              }

              @Override
              protected void execOwnerValueChanged(Object newOwnerValue) {
                setVisible(getFileColumn().getSelectedValue() != null);
              }

              @Override
              protected void execAction() {
                getDesktop().openUri(getFileColumn().getSelectedValue(), OpenUriAction.DOWNLOAD);
              }
            }
          }
        }
      }
    }

    @Order(180)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(280)
    public class OkButton extends AbstractOkButton {

      @Override
      protected void execInitField() {
        setVisible(getDisplayHint() == DISPLAY_HINT_DIALOG);
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
