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
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.widgets.client.services.lookup.FormLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox1;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox1.InnerFormField;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox1.NewInstanceField;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox2.InnerFormStateBox.InnerFormStateButton;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox2.InnerFormStateBox.SetInnerFormButton;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.GroupBox.GroupBox2.WrappedFormFieldManagesFormLifecycleField;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm.MainBox.WrappedFormFieldBox.WrappedFormField;

@ClassId("99d4da8d-201c-48e0-ac75-c4e92d1e6cd5")
public class WrappedFormFieldForm extends AbstractForm implements IPageForm {

  protected final Map<Class<? extends IForm>, IForm> m_formInstances = new HashMap<>();
  protected IForm m_externallyManagedForm = null;
  protected IForm m_currentFormInstance = null;
  protected FormListener m_formOpenCloseListener = e -> {
    if (e.getType() == FormEvent.TYPE_CLOSED || e.getType() == FormEvent.TYPE_LOAD_COMPLETE) {
      getInnerFormStateButton().update();
    }
  };

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
      form = m_formInstances.computeIfAbsent(formClass, k -> createNewFormInstance(formClass));
    }
    return form;
  }

  protected IForm createNewFormInstance(Class<? extends IForm> formClass) {
    try {
      IForm form = formClass.getConstructor().newInstance();
      form.setShowOnStart(false);
      form.setModal(false);
      return form;
    }
    catch (ReflectiveOperationException e) {
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
  @ClassId("a5160514-2efd-412d-9294-e9164cda6f10")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("4d90c1c1-c7b2-4ca4-a63d-87cbd3e85646")
    public class GroupBox extends AbstractGroupBox {
      @Order(10)
      @ClassId("79badfe6-7cdc-4f45-a5b7-3e331aab0958")
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
        @ClassId("2adbbfd0-de15-4dec-9e80-086d8d8bc4e0")
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
        @ClassId("71223d3a-8df4-40ba-b9f3-7544865ea944")
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
      @ClassId("346aea89-f231-457b-be80-4166c70557a5")
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
        @ClassId("a2794560-4102-4b21-88fa-c0a8c7c5042b")
        public class InnerFormStateBox extends AbstractSequenceBox {

          @Order(10)
          @ClassId("2c725370-e12a-446f-9d6c-b434ee6f33ac")
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
                ModelJobs.schedule(() -> getInnerFormField().setValue(null), ModelJobs.newInput(ClientRunContexts.copyCurrent()));
              }
              else {
                setLabel(form.isFormStartable() ? "Start inner form" : "Close inner form");
                setEnabled(true);
              }
            }
          }

          @Order(20)
          @ClassId("372b8cf8-d3f0-4297-89e0-f872f8ffd317")
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
        @ClassId("8878b588-b04d-4d95-8bad-147c22739984")
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
    @ClassId("eb0ee5c3-e407-4330-98ba-b403fdc44f1c")
    public class WrappedFormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return BORDER_DECORATION_EMPTY;
      }

      @Order(10)
      @ClassId("8446d7e0-e246-49f5-ab23-f95f0dcec766")
      public class WrappedFormField extends AbstractWrappedFormField<IForm> {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }
      }
    }

    @Order(40)
    @ClassId("95141574-52f3-4b7a-86d5-fb573bcc4240")
    public class CloseButton extends AbstractCloseButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CloseButton");
      }
    }

    @Order(50)
    @ClassId("df6a413d-68b2-4033-b924-658868557248")
    public class FormActionsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Form Actions";
      }

      @Order(10)
      @ClassId("7e49187d-1a4c-4eed-8a04-6a08837c7084")
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
      @ClassId("d5c6e256-8e47-414f-8e9a-e778534dda68")
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
      @ClassId("24caacbf-2d7a-4ec7-ad31-118d3993d0d3")
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
