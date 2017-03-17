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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.wrappedform.AbstractWrappedFormField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.services.lookup.FormLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox1;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox1.InnerFormField;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox1.NewInstanceField;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox2.InnerFormStateBox.InnerFormStateButton;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox2.InnerFormStateBox.SetInnerFormButton;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox2.WrappedFormFieldManagesFormLifecycleField;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox.WrappedFormField;

public class WrappedFormFieldForm extends AbstractForm implements IPageForm {

  protected final Map<Class<? extends IForm>, IForm> m_formInstances = new HashMap<>();
  protected IForm m_externallyManagedForm = null;
  protected IForm m_currentFormInstance = null;
  protected FormListener m_formOpenCloseListener = new FormListener() {
    @Override
    public void formChanged(FormEvent e) {
      if (e.getType() == FormEvent.TYPE_CLOSED || e.getType() == FormEvent.TYPE_LOAD_COMPLETE) {
        getInnerFormStateButton().update();
      }
    }
  };

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

  protected IForm getFormInstance(Class<? extends IForm> formClass) {
    IForm form;
    if (getNewInstanceField().isChecked()) {
      form = createNewFormInstance(formClass);
    }
    else {
      form = m_formInstances.get(formClass);
      if (form == null) {
        form = createNewFormInstance(formClass);
        m_formInstances.put(formClass, form);
      }
    }
    return form;
  }

  protected IForm createNewFormInstance(Class<? extends IForm> formClass) {
    try {
      IForm form = formClass.newInstance();
      form.setShowOnStart(false);
      form.setModal(false);
      return form;
    }
    catch (InstantiationException | IllegalAccessException e) {
      throw new ProcessingException("Error while creating instance of {}", formClass);
    }
  }

  protected void setInnerForm() {
    // Remove form listener from previous form
    if (m_currentFormInstance != null) {
      m_currentFormInstance.removeFormListener(m_formOpenCloseListener);
    }
    // If form is not managed by wrapped form field, we manage it ourselves
    if (m_externallyManagedForm != null) {
      m_externallyManagedForm.doClose();
      m_externallyManagedForm = null;
    }

    // Set new value
    Class<? extends IForm> formClass = getInnerFormField().getValue();
    if (formClass == null) {
      // No form
      getWrappedFormField().setInnerForm(null);
      m_currentFormInstance = null;
    }
    else {
      // Get instance for form class provided by lookup call
      IForm form = getFormInstance(formClass);
      if (getWrappedFormFieldManagesFormLifecycleField().isChecked()) {
        getWrappedFormField().setInnerForm(form); // implies "true"
      }
      else {
        // Ensure form is started (because the wrapped form field will _not_ manage it automatically)
        if (form.isFormStartable()) {
          form.start();
        }
        m_externallyManagedForm = form;
        getWrappedFormField().setInnerForm(form, false);
      }
      // Add a form listener, so we can update the form state button when the form is closed
      m_currentFormInstance = form;
      m_currentFormInstance.addFormListener(m_formOpenCloseListener);
    }
    getInnerFormStateButton().update();
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox1 getGroupBox() {
    return getFieldByClass(GroupBox1.class);
  }

  public InnerFormField getInnerFormField() {
    return getFieldByClass(InnerFormField.class);
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

  public WrappedFormFieldManagesFormLifecycleField getWrappedFormFieldManagesFormLifecycleField() {
    return getFieldByClass(WrappedFormFieldManagesFormLifecycleField.class);
  }

  public InnerFormStateButton getInnerFormStateButton() {
    return getFieldByClass(InnerFormStateButton.class);
  }

  public NewInstanceField getNewInstanceField() {
    return getFieldByClass(NewInstanceField.class);
  }

  public SetInnerFormButton getSetInnerFormButton() {
    return getFieldByClass(SetInnerFormButton.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GroupBox extends AbstractGroupBox {
      @Order(10)
      public class GroupBox1 extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        public class InnerFormField extends AbstractSmartField<Class<? extends IPageForm>> {

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected String getConfiguredLabel() {
            return "Inner form";
          }

          @Override
          protected Class<FormLookupCall> getConfiguredLookupCall() {
            return FormLookupCall.class;
          }

          @Override
          protected void execChangedValue() {
            setInnerForm();
            getSetInnerFormButton().update();
          }
        }

        @Order(20)
        public class NewInstanceField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "New instance";
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected void execInitField() {
            setChecked(false);
          }

          @Override
          protected void execChangedValue() {
            getSetInnerFormButton().update();
          }
        }
      }

      @Order(10)
      public class GroupBox2 extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        public class InnerFormStateBox extends AbstractSequenceBox {

          @Order(10)
          public class InnerFormStateButton extends AbstractLinkButton {

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected int getConfiguredGridW() {
              return 1;
            }

            @Override
            protected void execInitField() {
              update();
            }

            @Override
            protected void execClickAction() {
              IForm form = Assertions.assertNotNull(getWrappedFormField().getInnerForm());
              if (form.isFormStartable()) {
                form.start();
              }
              else {
                form.doClose();
              }
              update();
            }

            public void update() {
              IForm form = getWrappedFormField().getInnerForm();
              if (form == null) {
                setLabel("(No inner form set)");
                setEnabled(false);
                ModelJobs.schedule(new IRunnable() {
                  @Override
                  public void run() throws Exception {
                    getInnerFormField().setValue(null);
                  }
                }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
              }
              else {
                setLabel(form.isFormStartable() ? "Start inner form" : "Close inner form");
                setEnabled(true);
              }
            }
          }

          @Order(20)
          public class SetInnerFormButton extends AbstractLinkButton {

            @Override
            protected String getConfiguredLabel() {
              return "Set new inner form";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }

            @Override
            protected int getConfiguredGridW() {
              return 1;
            }

            @Override
            protected void execClickAction() {
              setInnerForm();
            }

            public void update() {
              setVisible(getInnerFormField().getValue() != null && getNewInstanceField().isChecked());
            }
          }
        }

        @Order(20)
        public class WrappedFormFieldManagesFormLifecycleField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "WrappedFormField manages form life-cycle";
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected void execInitField() {
            setChecked(true);
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
      public class WrappedFormField extends AbstractWrappedFormField<IForm> {

        @Override
        protected int getConfiguredGridW() {
          return 2;
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
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
