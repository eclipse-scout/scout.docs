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

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox.FromField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox.PreviewField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox.FileTableField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.FieldVisibilityBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.FieldVisibilityBox.Placeholder1Field;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileDetailsBox;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileDetailsBox.ModifiedField;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileDetailsBox.NameField;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileDetailsBox.ReadOnlyField;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileDetailsBox.SizeField;
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
   * @return the ExamplesBox
   */
  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
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
   * @deprecated Use {@link #getDetailsBox()#getModifiedField()}
   */
  @Deprecated
  public ModifiedField getModifiedField() {
    return getDetailsBox().getModifiedField();
  }

  /**
   * @return the NameField
   * @deprecated Use {@link #getDetailsBox()#getNameField()}
   */
  @Deprecated
  public NameField getNameField() {
    return getDetailsBox().getNameField();
  }

  /**
   * @return the Placeholder1Field
   */
  public Placeholder1Field getPlaceholder1Field() {
    return getFieldByClass(Placeholder1Field.class);
  }

  /**
   * @return the PreviewBox
   */
  public PreviewBox getPreviewBox() {
    return getFieldByClass(PreviewBox.class);
  }

  /**
   * @return the PreviewField
   */
  public PreviewField getPreviewField() {
    return getFieldByClass(PreviewField.class);
  }

  /**
   * @return the ReadOnlyField
   * @deprecated Use {@link #getDetailsBox()#getReadOnlyField()}
   */
  @Deprecated
  public ReadOnlyField getReadOnlyField() {
    return getDetailsBox().getReadOnlyField();
  }

  /**
   * @return the SizeField
   * @deprecated Use {@link #getDetailsBox()#getSizeField()}
   */
  @Deprecated
  public SizeField getSizeField() {
    return getDetailsBox().getSizeField();
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
   * @return the DetailsBox
   */
  public DetailsBox getBottomBox() {
    return getFieldByClass(DetailsBox.class);
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
                getTable().addSampleRow();
                getTable().getDateModifiedColumn().setVisible(false);
                getTable().getReadOnlyColumn().setVisible(false);
              }

              @Override
              protected void execFileRowClick(File file) {
                reloadPreview(file);
                reloadDetails(file);
              }

              private void reloadPreview(File file) {
                if (isImage(file)) {
                  try {
                    getPreviewField().setImage(getFileContent(file));
                  }
                  catch (ProcessingException e) {
                    e.printStackTrace();
                  }
                }
                else {
                  getPreviewField().setImage(null);
                }
              }

              private void reloadDetails(File file) {
                DetailsBox d = getDetailsBox();
                d.getNameField().setValue(file.getName());
                d.getSizeField().setValue(file.length());
                d.getModifiedField().setValue(new Date(file.lastModified()));
                d.getReadOnlyField().setValue(!file.canWrite());
              }
            }
          }

          private boolean isImage(File file) {
            String ext = IOUtility.getFileExtension(file.getName()).toLowerCase();

            if (ext.equals("jpg")) return true;
            if (ext.equals("jpeg")) return true;
            if (ext.equals("png")) return true;

            return false;
          }

          private byte[] getFileContent(File file) throws ProcessingException {
            try {
              return IOUtility.getContent(new FileInputStream(file));
            }
            catch (FileNotFoundException e) {
              throw new ProcessingException("File not found:", e);
            }
          }

          @Order(20.0)
          public class DetailsBox extends AbstractFileDetailsBox {

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
