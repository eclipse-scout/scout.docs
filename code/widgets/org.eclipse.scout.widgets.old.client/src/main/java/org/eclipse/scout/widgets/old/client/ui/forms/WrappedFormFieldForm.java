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

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.wrappedform.AbstractWrappedFormField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.FormLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.StaticFormLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.InnerFormsField;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.StaticInnerFormsField;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox.WrappedFormField;

public class WrappedFormFieldForm extends AbstractForm implements IPageForm {

  public WrappedFormFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("WrappedFormField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public InnerFormsField getInnerFormsField() {
    return getFieldByClass(InnerFormsField.class);
  }

  public StaticInnerFormsField getStaticInnerFormsField() {
    return getFieldByClass(StaticInnerFormsField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public WrappedFormField getWrappedFormField() {
    return getFieldByClass(WrappedFormField.class);
  }

  public WrappedFormFieldBox getWrappedFormFieldBox() {
    return getFieldByClass(WrappedFormFieldBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class InnerFormsField extends AbstractSmartField<Class<? extends IPageForm>> {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InnerForms") + " (dynamic)";
        }

        @Override
        protected Class<? extends ILookupCall<Class<? extends IPageForm>>> getConfiguredLookupCall() {
          return FormLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(ImageFieldForm.class);
          fireValueChanged(); // because events are not fired by default in execInitField()
        }

        @Override
        protected void execChangedValue() {
          // Clear other field
          getStaticInnerFormsField().setValueChangeTriggerEnabled(false);
          try {
            getStaticInnerFormsField().setValue(null);
          }
          finally {
            getStaticInnerFormsField().setValueChangeTriggerEnabled(true);
          }

          // Set inner form
          Class<? extends IPageForm> value = getValue();
          if (value == null) {
            getWrappedFormField().setInnerForm(null);
          }
          else {
            IPageForm form;
            try {
              form = value.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e) {
              throw new ProcessingException("Error while creating instance of " + value.getName());
            }
            getWrappedFormField().setInnerForm(form);
          }
        }
      }

      @Order(20)
      public class StaticInnerFormsField extends AbstractSmartField<IPageForm> {

        private StaticFormLookupCall m_lookupCall = null;

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InnerForms") + " (static)";
        }

        @Override
        protected void execInitField() {
          m_lookupCall = new StaticFormLookupCall();
          setLookupCall(m_lookupCall);
        }

        @Override
        protected void execChangedValue() {
          // Clear other field
          getInnerFormsField().setValueChangeTriggerEnabled(false);
          try {
            getInnerFormsField().setValue(null);
          }
          finally {
            getInnerFormsField().setValueChangeTriggerEnabled(true);
          }

          // Set inner form
          IPageForm form = getValue();
          if (form == null) {
            getWrappedFormField().setInnerForm(null);
          }
          else {
            getWrappedFormField().setInnerForm(form);
          }
        }
      }
    }

    @Order(30)
    public class WrappedFormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return BORDER_DECORATION_EMPTY;
      }

      @Order(10)
      public class WrappedFormField extends AbstractWrappedFormField<IPageForm> {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        public void setInnerForm(IPageForm form) {
          super.setInnerForm(form, false);
        }

        @Override
        protected void execInitField() {
          getInnerForm().start();
        }
      }
    }

    @Order(40)
    public class CloseButton extends AbstractCloseButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CloseButton");
      }
    }

    @Order(50)
    public class FormActionsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Form Actions";
      }

      @Order(10)
      public class IsEmptyMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "execIsEmpty";
        }

        @Override
        protected void execAction() {
          WrappedFormFieldForm.this.checkSaveNeeded();
          MessageBoxes.createOk()
              .withBody("isEmpty() = " + WrappedFormFieldForm.this.isEmpty())
              .show();
        }
      }

      @Order(20)
      public class IsSaveNeededMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "execIsSaveNeeded";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk()
              .withBody("isSaveNeeded() = " + WrappedFormFieldForm.this.isSaveNeeded())
              .show();
        }
      }

      @Order(30)
      public class MarkSavedMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "execMarkSaved";
        }

        @Override
        protected void execAction() {
          WrappedFormFieldForm.this.markSaved();
          MessageBoxes.createOk()
              .withBody("markSaved() = void")
              .show();
        }
      }

      @Order(40)
      public class ToggleStartCloseMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "wrapped form: doClose";
        }

        @Override
        protected void execAction() {
          if (getWrappedFormField().getInnerForm() == null || getWrappedFormField().getInnerForm().isFormStarted()) {
            getWrappedFormField().getInnerForm().doClose();
            setText("wrapped form: start");
          }
          else {
            getWrappedFormField().getInnerForm().start();
            setText("wrapped form: doClose");
          }
        }
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
