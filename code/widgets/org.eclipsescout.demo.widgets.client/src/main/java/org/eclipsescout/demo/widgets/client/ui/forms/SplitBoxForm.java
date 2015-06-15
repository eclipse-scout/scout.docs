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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox.FromField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox.PreviewField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.ModifiedField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.NameField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.ReadOnlyField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.SizeField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox.FileTableField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.FieldVisibilityBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.FieldVisibilityBox.Placeholder1Field;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileTableField;

public class SplitBoxForm extends AbstractForm implements IPageForm {

  public SplitBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SplitBox");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the DetailsBox
   */
  public DetailsBox getBottomBox() {
    return getFieldByClass(DetailsBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DetailsBox
   */
  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  /**
   * @return the FieldVisibilityBox
   */
  public FieldVisibilityBox getFieldVisibilityBox() {
    return getFieldByClass(FieldVisibilityBox.class);
  }

  /**
   * @return the FileTableField
   */
  public FileTableField getFileTableField() {
    return getFieldByClass(FileTableField.class);
  }

  /**
   * @return the FilesBox
   */
  public FilesBox getFilesBox() {
    return getFieldByClass(FilesBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the ModifiedField
   */
  public ModifiedField getModifiedField() {
    return getFieldByClass(ModifiedField.class);
  }

  /**
   * @return the NameField
   */
  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  /**
   * @return the Placeholder1Field
   */
  public Placeholder1Field getPlaceholder1Field() {
    return getFieldByClass(Placeholder1Field.class);
  }

  /**
   * @return the PreviewField
   */
  public PreviewField getPreviewField() {
    return getFieldByClass(PreviewField.class);
  }

  /**
   * @return the PreviewBox
   */
  public PreviewBox getPreviewBox() {
    return getFieldByClass(PreviewBox.class);
  }

  /**
   * @return the ReadOnlyField
   */
  public ReadOnlyField getReadOnlyField() {
    return getFieldByClass(ReadOnlyField.class);
  }

  /**
   * @return the SizeField
   */
  public SizeField getSizeField() {
    return getFieldByClass(SizeField.class);
  }

  /**
   * @return the ExamplesBox
   */
  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  /**
   * @return the SplitHorizontalField
   */
  public SplitHorizontalField getSplitHorizontalField() {
    return getFieldByClass(SplitHorizontalField.class);
  }

  /**
   * @return the SplitVerticalField
   */
  public SplitVerticalField getSplitVerticalField() {
    return getFieldByClass(SplitVerticalField.class);
  }

  /**
   * @return the undefined
   */
  public FromField getfromField() {
    return getFieldByClass(FromField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(20.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class SplitVerticalField extends AbstractSplitBox {

        @Order(10.0)
        public class SplitHorizontalField extends AbstractSplitBox {

          @Override
          protected boolean getConfiguredSplitHorizontal() {
            return false;
          }

          @Override
          protected double getConfiguredSplitterPosition() {
            return 0.7;
          }

          @Order(10.0)
          public class FilesBox extends AbstractGroupBox {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Files");
            }

            @Order(10.0)
            public class FileTableField extends AbstractFileTableField {

              @Override
              protected int getConfiguredGridH() {
                return 5;
              }

              @Override
              protected boolean getConfiguredLabelVisible() {
                return false;
              }

              @Override
              protected void execInitField() throws ProcessingException {
                super.execInitField();

                for (IColumn c : getTable().getColumns()) {
                  if (c instanceof AbstractFileTableField.Table.DateModifiedColumn) {
                    c.setVisible(false);
                  }
                }
              }

              @Override
              protected void execFileRowClick(File file) throws ProcessingException {
                reloadPreview(file);
                reloadDetails(file);
              }

              private void reloadPreview(File file) throws ProcessingException {
                if (isImage(file)) {
                  getPreviewField().setImage(getFileContent(file));
                }
                else {
                  getPreviewField().setImage(null);
                }
              }

              private byte[] getFileContent(File file) throws ProcessingException {
                try {
                  return IOUtility.getContent(new FileInputStream(file));
                }
                catch (FileNotFoundException e) {
                  throw new ProcessingException("File not found:", e);
                }
              }

              private boolean isImage(File file) {
                if (file == null) {
                  return false;
                }
                String ext = IOUtility.getFileExtension(file.getName()).toLowerCase();
                return CompareUtility.isOneOf(ext, "jpg", "jpeg", "png");
              }

              private void reloadDetails(File file) {
                if (file == null) {
                  getNameField().resetValue();
                  getSizeField().resetValue();
                  getModifiedField().resetValue();
                  getReadOnlyField().resetValue();
                }
                else {
                  getNameField().setValue(file.getName());
                  getSizeField().setValue(file.length());
                  getModifiedField().setValue(new Date(file.lastModified()));
                  getReadOnlyField().setValue(!file.canWrite());
                }
              }
            }
          }

          @Order(20.0)
          public class DetailsBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
              return 1;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Details");
            }

            @Override
            protected void execInitField() throws ProcessingException {
              getNameField().setEnabled(false);
              getSizeField().setEnabled(false);
              getModifiedField().setEnabled(false);
              getReadOnlyField().setEnabled(false);
            }

            @Order(10.0)
            public class NameField extends AbstractStringField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Name");
              }
            }

            @Order(20.0)
            public class SizeField extends AbstractLongField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("SizeInBytes");
              }
            }

            @Order(40.0)
            public class ModifiedField extends AbstractDateTimeField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Modified");
              }
            }

            @Order(50.0)
            public class ReadOnlyField extends AbstractCheckBox {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("ReadOnly");
              }
            }
          }
        }

        @Order(30.0)
        public class PreviewBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredBackgroundColor() {
            return "D6D6D6";
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredHorizontalAlignment() {
            return 0;
          }

          @Order(10.0)
          public class PreviewField extends AbstractImageField {

            @Override
            protected boolean getConfiguredAutoFit() {
              return true;
            }

            @Override
            protected String getConfiguredBackgroundColor() {
              return "D6D6D6";
            }

            @Override
            protected int getConfiguredGridH() {
              return 8;
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }
          }
        }
      }
    }

    @Order(40.0)
    public class FieldVisibilityBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected double getConfiguredGridWeightY() {
        return 0.0;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SplitterVisibility");
      }

      @Order(10.0)
      public class VisiblePreviewField extends AbstractCheckBox {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisiblePreview");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getPreviewBox().setVisible(getValue());
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getPreviewBox().isVisible());
        }
      }

      @Order(20.0)
      public class VisibleDetailsField extends AbstractCheckBox {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleDetails");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getDetailsBox().setVisible(getValue());
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(getDetailsBox().isVisible());
        }
      }

      @Order(40.0)
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
