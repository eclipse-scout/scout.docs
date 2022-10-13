/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
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
import org.eclipse.scout.rt.client.ui.form.fields.filechooserbutton.AbstractFileChooserButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.ReadOnlyProperty;
import org.eclipse.scout.widgets.client.WidgetsHelper;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserBox.UploadButtonsBox.UploadMultipleFilesButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserBox.UploadButtonsBox.UploadSingleFileButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserButtonBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserButtonBox.FileChooserButton;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserFieldBox.ChooseAnImageField;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.FileChooserFieldBox.PropertiesGroupBox.MaximumUploadSizeField;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.ServerLogBox;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.ServerLogBox.ServerLogField;
import org.eclipse.scout.widgets.client.ui.forms.FileChooserFieldForm.MainBox.GroupBox.ServerLogBox.ServerLogField.Table;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractStatusButton;

@Order(8050)
@ClassId("9511705e-5754-4e83-a6b0-4ac8a357fbaa")
public class FileChooserFieldForm extends AbstractForm implements IAdvancedExampleForm {

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

  public FileChooserButtonBox getFileChooserButtonBox() {
    return getFieldByClass(FileChooserButtonBox.class);
  }

  public FileChooserButton getFileChooserButton() {
    return getFieldByClass(FileChooserButton.class);
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
  @ClassId("21932ccf-f1f3-4a11-bb8c-0fe7e07cb3cf")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
      BEANS.get(WidgetsHelper.class).injectReadOnlyMenu(menus);
    }

    @Order(10)
    @ClassId("4c62f7aa-0c63-413e-aed0-92febdc3351a")
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
      @ClassId("23bc5430-20a5-4fb8-8bf8-0cb0d9fe1f44")
      public class FileChooserFieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "File Chooser Field";
        }

        @Order(20)
        @ClassId("007df315-782a-45a7-a0cb-2d9d3b472c08")
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
          protected void execInitField() {
            setEnabled(!CONFIG.getPropertyValue(ReadOnlyProperty.class));
          }

          @Override
          protected void execChangedValue() {
            getServerLogField().addLine(getValue());
          }
        }

        @Order(70)
        @ClassId("fc000a72-179c-4451-8274-9ba4324660e9")
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
          @ClassId("a6660a5f-9ee5-49c3-863a-a1ff29264171")
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
              if (CONFIG.getPropertyValue(ReadOnlyProperty.class)) {
                setValue(false);
                setEnabled(false);
              } else {
                setValue(getChooseAnImageField().isEnabled());
              }
            }
          }

          @Order(20)
          @ClassId("a59c2fe9-620f-4543-a3b8-fd3462e646ef")
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
          @ClassId("0600545a-97b2-41b1-a5ea-eb97d09688a2")
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
                throw new VetoException(TEXTS.get("InvalidValueMessageX", ObjectUtility.toString(rawValue)));
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

      @Order(20)
      @ClassId("573e431b-de60-4476-b638-6868f76da1e7")
      public class FileChooserButtonBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return "File Chooser Button";
        }

        @Order(10)
        @ClassId("bb27c852-ac1c-48ef-8ee0-e9aefec3d6d9")
        public class FileChooserButton extends AbstractFileChooserButton {
          @Override
          protected String getConfiguredLabel() {
            return "Upload";
          }

          @Override
          protected void execInitField() {
            setEnabled(!CONFIG.getPropertyValue(ReadOnlyProperty.class));
          }

          @Override
          protected void execChangedValue() {
            BinaryResource file = getValue();
            if (file != null) {
              getServerLogField().addLine(file);
            }
          }
        }
      }

      @Order(30)
      @ClassId("2000939e-756c-40e4-a964-2a1eb5f08b07")
      public class FileChooserBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "File Chooser";
        }

        @Order(10)
        @ClassId("47144e31-947a-4c6e-8e8d-d897e82ca856")
        public class UploadButtonsBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(40)
          @ClassId("29fa9853-6660-49aa-8233-10ee169d3907")
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
            protected void execInitField() {
              setEnabled(!CONFIG.getPropertyValue(ReadOnlyProperty.class));
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
          @ClassId("014a4685-1640-42db-8a95-1218cb47c21a")
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
            protected void execInitField() {
              setEnabled(!CONFIG.getPropertyValue(ReadOnlyProperty.class));
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
      @ClassId("557f40d7-1c2d-4e3f-86c3-559d9ee0b368")
      public class ServerLogBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ServerLog");
        }

        @Order(1000)
        @ClassId("0bcd89e7-7a46-4dc4-a3ab-c1d74adf08d6")
        public class ServerLogField extends AbstractTableField<Table> {

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

          @ClassId("7ae1aabf-0c87-4b02-803c-b277bb2740d0")
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
            @ClassId("e628767c-fe3b-463c-8cda-786a17e727aa")
            public class FileColumn extends AbstractColumn<BinaryResource> {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(20)
            @ClassId("5983bb13-a536-4e3c-93e3-faa0b606cb0c")
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
            @ClassId("10ac91f0-54f1-4b51-b0c5-4f60c880b38a")
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
            @ClassId("27dcfa78-6e07-49e8-8e0a-fd524b6f4f30")
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
            @ClassId("da1bd459-ec92-4b7e-8276-a3630a8de90c")
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
    @ClassId("2abab35c-c73b-429d-8012-2b4682f77982")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(280)
    @ClassId("50bfd518-2935-4917-bf80-5954102cd868")
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
