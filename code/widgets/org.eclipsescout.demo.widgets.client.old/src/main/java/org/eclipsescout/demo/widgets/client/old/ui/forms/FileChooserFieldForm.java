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
package org.eclipsescout.demo.widgets.client.old.ui.forms;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.resource.BinaryResource;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.FileChooserFieldBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.FileChooserFieldBox.ChooseAnImageField;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.FileDialogBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.FileDialogBox.UploadMultipleFilesButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.FileDialogBox.UploadSingleFileButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.ServerLogBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FileUploadBox.ServerLogBox.ServerLogField;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FolderContentsBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FolderContentsBox.ContentField;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm.MainBox.TabBox.FolderContentsBox.SelectAFolderField;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;

public class FileChooserFieldForm extends AbstractForm implements IPageForm {

  public FileChooserFieldForm() throws ProcessingException {
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
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public ChooseAnImageField getChooseAnImageField() {
    return getFieldByClass(ChooseAnImageField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ContentField getContentField() {
    return getFieldByClass(ContentField.class);
  }

  public FileChooserFieldBox getFileChooserFieldBox() {
    return getFieldByClass(FileChooserFieldBox.class);
  }

  public FileDialogBox getFileDialogBox() {
    return getFieldByClass(FileDialogBox.class);
  }

  public FolderContentsBox getFolderContentsBox() {
    return getFieldByClass(FolderContentsBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public FileUploadBox getFileUploadBox() {
    return getFieldByClass(FileUploadBox.class);
  }

  public SelectAFolderField getSelectAFolderField() {
    return getFieldByClass(SelectAFolderField.class);
  }

  public ServerLogBox getServerLogBox() {
    return getFieldByClass(ServerLogBox.class);
  }

  public ServerLogField getServerLogField() {
    return getFieldByClass(ServerLogField.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  public UploadMultipleFilesButton getUploadMultipleFilesButton() {
    return getFieldByClass(UploadMultipleFilesButton.class);
  }

  public UploadSingleFileButton getUploadSingleFileButton() {
    return getFieldByClass(UploadSingleFileButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(20.0)
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      public class FileUploadBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileUpload");
        }

        @Order(10.0)
        public class FileChooserFieldBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FileChooserField");
          }

          @Order(20.0)
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
            protected void execChangedValue() throws ProcessingException {
              File f = getValueAsFile();
              getServerLogField().addLine("received " + (f == null ? null : f.getName()));
            }
          }
        }

        @Order(30.0)
        public class FileDialogBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FileDialogBox");
          }

          @Order(40.0)
          public class UploadSingleFileButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("UploadSingleFile");
            }

            @Override
            protected void execClickAction() throws ProcessingException {
              FileChooser fc = new FileChooser(false);
              List<BinaryResource> files = fc.startChooser();
              for (BinaryResource file : files) {
                getServerLogField().addLine("received " + file.getFilename());
              }
            }
          }

          @Order(50.0)
          public class UploadMultipleFilesButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("UploadMultipleFiles");
            }

            @Override
            protected void execClickAction() throws ProcessingException {
              FileChooser fc = new FileChooser(true);
              List<BinaryResource> files = fc.startChooser();
              for (BinaryResource file : files) {
                getServerLogField().addLine("received " + file.getFilename());
              }
            }
          }
        }

        @Order(60.0)
        public class ServerLogBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ServerLog");
          }

          @Order(1000.0)
          public class ServerLogField extends AbstractTableField<ServerLogField.Table> {

            @Override
            protected int getConfiguredGridH() {
              return 3;
            }

            public void addLine(String text) throws ProcessingException {
              ITableRow row = getTable().addRow(getTable().createRow());
              getTable().getTimeColumn().setValue(row, new Date());
              getTable().getActionColumn().setValue(row, text);
              getTable().selectLastRow();
              getTable().scrollToSelection();
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Order(70.0)
            public class Table extends AbstractTable {

              public TimeColumn getTimeColumn() {
                return getColumnSet().getColumnByClass(FileChooserFieldForm.MainBox.TabBox.FileUploadBox.ServerLogBox.ServerLogField.Table.TimeColumn.class);
              }

              public ActionColumn getActionColumn() {
                return getColumnSet().getColumnByClass(ActionColumn.class);
              }

              @Order(0.0)
              public class TimeColumn extends AbstractTimeColumn {

                @Override
                protected String getConfiguredHeaderText() {
                  return TEXTS.get("Time");
                }

                @Override
                protected int getConfiguredWidth() {
                  return 120;
                }
              }

              @Order(80.0)
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

              @Order(90.0)
              public class ClearMenu extends AbstractMenu {

                @Override
                protected String getConfiguredText() {
                  return TEXTS.get("Clear");
                }

                @Override
                protected void execAction() throws ProcessingException {
                  getTable().deleteAllRows();
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection, ValueFieldMenuType.NotNull, TableMenuType.MultiSelection, TableMenuType.EmptySpace);
                }
              }
            }
          }
        }
      }

      @Order(100.0)
      public class FolderContentsBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredVisible() {
          return UserAgentUtility.isRichClient();
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FolderContents");
        }

        @Order(110.0)
        public class SelectAFolderField extends AbstractFileChooserField {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SelectAFolder");
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            try {
              String folderName = getValue();
              getContentField().getTable().deleteAllRows();
              if (!StringUtility.isNullOrEmpty(folderName)) {
                File folder = new File(folderName);
                for (File f : folder.listFiles()) {
                  String fileName = f.getName();
                  String filePath = f.getPath();
                  getContentField().getTable().addRowByArray(new Object[]{f, fileName, filePath});
                }
              }
            }
            catch (NullPointerException e) {
              MessageBox.showOkMessage("Folder not found", null, "Can't find folder " + getValue());
            }
          }
        }

        @Order(120.0)
        public class ContentField extends AbstractTableField<ContentField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 7;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Order(130.0)
          public class Table extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            public FileColumn getFileColumn() {
              return getColumnSet().getColumnByClass(FileColumn.class);
            }

            public FileNameColumn getFileNameColumn() {
              return getColumnSet().getColumnByClass(FileNameColumn.class);
            }

            public PathColumn getPathColumn() {
              return getColumnSet().getColumnByClass(PathColumn.class);
            }

            @Order(10.0)
            public class FileColumn extends AbstractColumn<File> {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(140.0)
            public class FileNameColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("FileName");
              }
            }

            @Order(150.0)
            public class PathColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Path");
              }
            }

            @Order(170.0)
            public class UpOneLevelMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("UpOneLevel");
              }

              @Override
              protected void execAction() throws ProcessingException {
                if (getSelectAFolderField().getValue() != null) {
                  getSelectAFolderField().setValue(new File(getSelectAFolderField().getValue()).getParent());
                }
              }
            }
          }
        }
      }
    }

    @Order(180.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
