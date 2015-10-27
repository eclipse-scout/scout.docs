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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Date;

import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.resource.BinaryResource;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.ISplitBox;
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
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.SizeField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox.FileTableField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.FieldVisibilityBox;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileTableField;

public class SplitBoxForm extends AbstractForm implements IPageForm {

  public SplitBoxForm() {
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
  public void startPageForm() {
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
            return 0.6;
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
              protected void execInitField() {
                super.execInitField();

                for (IColumn c : getTable().getColumns()) {
                  if (c instanceof AbstractFileTableField.Table.DateModifiedColumn) {
                    c.setVisible(false);
                  }
                }
              }

              @Override
              protected void execResourceRowClick(BinaryResource resource) {
                reloadPreview(resource);
                reloadDetails(resource);
              }

              private void reloadPreview(BinaryResource resource) {
                if (isImage(resource)) {
                  getPreviewField().setImage(resource.getContent());
                }
                else {
                  getPreviewField().setImage(null);
                }
              }

              private boolean isImage(BinaryResource resource) {
                if (resource == null) {
                  return false;
                }
                String ext = IOUtility.getFileExtension(resource.getFilename()).toLowerCase();
                return CompareUtility.isOneOf(ext, "jpg", "jpeg", "png");
              }

              private void reloadDetails(BinaryResource resource) {
                if (resource == null) {
                  getNameField().resetValue();
                  getSizeField().resetValue();
                  getModifiedField().resetValue();
                }
                else {
                  getNameField().setValue(resource.getFilename());
                  getSizeField().setValue(resource.getContent() == null ? 0L : resource.getContent().length);
                  getModifiedField().setValue(new Date(resource.getLastModified()));
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
            protected void execInitField() {
              getNameField().setEnabled(false);
              getSizeField().setEnabled(false);
              getModifiedField().setEnabled(false);
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
    public class SplitVisibleEnabledField extends AbstractSplitBox {

      @Order(10.0)
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
          protected void execChangedValue() {
            getPreviewBox().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
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
          protected void execChangedValue() {
            getDetailsBox().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getDetailsBox().isVisible());
          }
        }
      }

      @Order(20.0)
      public class FieldEnabledBox extends AbstractGroupBox {

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
          return TEXTS.get("SplitterEnabled");
        }

        @Order(10.0)
        public class VisiblePreviewField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("EnabledHorizontal");
          }

          @Override
          protected void execChangedValue() {
            getSplitHorizontalField().setSplitterEnabled(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getSplitHorizontalField().isSplitterEnabled());
          }
        }

        @Order(20.0)
        public class VisibleDetailsField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("EnabledVertical");
          }

          @Override
          protected void execChangedValue() {
            getSplitVerticalField().setSplitterEnabled(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getSplitVerticalField().isSplitterEnabled());
          }
        }

        @Order(30.0)
        public class SplitterPositionBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return "Splitter position";
          }

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(10.0)
          public class SplitterPositionVField extends P_AbstractSplitterPositionField {

            @Override
            protected String getConfiguredLabel() {
              return "V";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Splitter position of vertical splitter";
            }

            @Override
            protected ISplitBox getSplitBox() {
              return getSplitVerticalField();
            }
          }

          @Order(20.0)
          public class SplitterPositionHField extends P_AbstractSplitterPositionField {

            @Override
            protected String getConfiguredLabel() {
              return "H";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Splitter position of horizontal splitter";
            }

            @Override
            protected ISplitBox getSplitBox() {
              return getSplitHorizontalField();
            }
          }
        }
      }
    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  protected abstract class P_AbstractSplitterPositionField extends AbstractBigDecimalField {

    protected abstract ISplitBox getSplitBox();

    @Override
    protected BigDecimal getConfiguredMinValue() {
      return BigDecimal.ZERO;
    }

    @Override
    protected BigDecimal getConfiguredMaxValue() {
      return BigDecimal.ONE;
    }

    @Override
    protected void execChangedValue() {
      if (getValue() != null) {
        getSplitBox().setSplitterPosition(getValue().doubleValue());
      }
    }

    @Override
    protected void execInitField() {
      // set initial value
      setValueWithoutValueChangeTriggers(getSplitBox().getSplitterPosition());
      // add listener
      getSplitBox().addPropertyChangeListener(ISplitBox.PROP_SPLITTER_POSITION, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          setValueWithoutValueChangeTriggers((double) evt.getNewValue());
        }
      });
    }

    protected void setValueWithoutValueChangeTriggers(double value) {
      setValueChangeTriggerEnabled(false);
      try {
        setValue(BigDecimal.valueOf(value));
      }
      finally {
        setValueChangeTriggerEnabled(true);
      }
    }
  }
}
