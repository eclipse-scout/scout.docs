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
package org.eclipse.scout.widgets.old.client.ui.forms;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.ScoutFieldStatus;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.ERRORStatusButton;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.CheckboxField;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.FileChooserField;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.IntegerField;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.SequenceBox;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.SequenceBox.SequenceFrom;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.SequenceBox.SequenceTo;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.GroupBox.StringField;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.INFOStatusButton;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm.MainBox.WARNINGStatusButton;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

public class StatusForm extends AbstractForm implements IPageForm {

  private Long statusNr;

  public StatusForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Status");
  }

  @FormData
  public Long getStatusNr() {
    return statusNr;
  }

  @FormData
  public void setStatusNr(Long statusNr) {
    this.statusNr = statusNr;
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public CheckboxField getCheckboxField() {
    return getFieldByClass(CheckboxField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ERRORStatusButton getERRORStatusButton() {
    return getFieldByClass(ERRORStatusButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public INFOStatusButton getINFOStatusButton() {
    return getFieldByClass(INFOStatusButton.class);
  }

  public IntegerField getIntegerField() {
    return getFieldByClass(IntegerField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SequenceBox getSequenceBox() {
    return getFieldByClass(SequenceBox.class);
  }

  public SequenceFrom getSequenceFrom() {
    return getFieldByClass(SequenceFrom.class);
  }

  public SequenceTo getSequenceTo() {
    return getFieldByClass(SequenceTo.class);
  }

  public StringField getStringField() {
    return getFieldByClass(StringField.class);
  }

  public WARNINGStatusButton getWARNINGStatusButton() {
    return getFieldByClass(WARNINGStatusButton.class);
  }

  public FileChooserField getFileChooserField() {
    return getFieldByClass(FileChooserField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class StringField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
        }
      }

      @Order(20)
      public class IntegerField extends AbstractIntegerField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IntegerField");
        }
      }

      @Order(30)
      public class CheckboxField extends AbstractBooleanField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Checkbox");
        }
      }

      @Order(40)
      public class SequenceBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SequenceBox");
        }

        @Order(10)
        public class SequenceFrom extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }
        }

        @Order(20)
        public class SequenceTo extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(50)
      public class FileChooserField extends AbstractFileChooserField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileChooserField");
        }
      }
    }

    @Order(20)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(30)
    public class ERRORStatusButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ERRORStatus");
      }

      @Override
      protected void execClickAction() {
        if (isSelected()) {
          getWARNINGStatusButton().setSelected(false);
          getINFOStatusButton().setSelected(false);
          for (IFormField f : getAllFields()) {
            if (f instanceof IValueField<?>) {
              f.addErrorStatus(new ScoutFieldStatus("Error on " + f.getClass().getSimpleName(), ScoutFieldStatus.ERROR));
            }
          }
        }
        else {
          for (IFormField field : getAllFields()) {
            field.clearErrorStatus();
          }
        }
      }
    }

    @Order(40)
    public class WARNINGStatusButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("WARNINGStatus");
      }

      @Override
      protected void execClickAction() {
        if (isSelected()) {
          getERRORStatusButton().setSelected(false);
          getINFOStatusButton().setSelected(false);
          for (IFormField f : getAllFields()) {
            if (f instanceof IValueField<?>) {
              f.addErrorStatus(new ScoutFieldStatus("Warning on " + f.getClass().getSimpleName(), ScoutFieldStatus.WARNING));
            }
          }
        }
        else {
          for (IFormField field : getForm().getAllFields()) {
            field.clearErrorStatus();
          }
        }
      }
    }

    @Order(50)
    public class INFOStatusButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("INFOStatus");
      }

      @Override
      protected void execClickAction() {
        if (isSelected()) {
          getERRORStatusButton().setSelected(false);
          getWARNINGStatusButton().setSelected(false);
          for (IFormField f : getAllFields()) {
            if (f instanceof IValueField<?>) {
              f.addErrorStatus(new ScoutFieldStatus("Info on " + f.getClass().getSimpleName(), ScoutFieldStatus.INFO));
            }
          }
        }
        else {
          for (IFormField field : getForm().getAllFields()) {
            field.clearErrorStatus();
          }
        }
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
