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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.annotations.ClassId;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.IDisplayParent;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.DisplayHintLookupCall.DisplayHint;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.DisplayParentLookupCall.DisplayParent;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.BlockModelThreadField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.CloseOnChildCloseField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayHintField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayParentField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.ModalityField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpenFormButton;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpeningDelayField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField1GroupbBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField1GroupbBox.Field1Field;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField1GroupbBox.HideField1Button;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField2GroupbBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField2GroupbBox.Field2Field;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField2GroupbBox.HideField2Button;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField3GroupbBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField3GroupbBox.Field3Field;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField3GroupbBox.HideField3Button;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField4GroupbBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField4GroupbBox.Field4Field;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField4GroupbBox.HideField4Button;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.LongRunningOperationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.LongRunningOperationBox.CancellationDurationField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormForm.MainBox.LongRunningOperationBox.StartLongRunningOperationButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("b612310f-59b6-427d-93c9-57b384564a94")
public class FormForm extends AbstractForm implements IPageForm {

  private static final Logger LOG = LoggerFactory.getLogger(FormForm.class);

  public FormForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Form";
  }

  @Override
  protected void execInitForm() {
    getOpeningDelayField().requestFocus();
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public ControllerBox getControllerBox() {
    return getFieldByClass(ControllerBox.class);
  }

  public DisplayHintField getDisplayHintField() {
    return getFieldByClass(DisplayHintField.class);
  }

  public ModalityField getModalityField() {
    return getFieldByClass(ModalityField.class);
  }

  public DisplayParentField getDisplayParentField() {
    return getFieldByClass(DisplayParentField.class);
  }

  public OpeningDelayField getOpeningDelayField() {
    return getFieldByClass(OpeningDelayField.class);
  }

  public BlockModelThreadField getBlockModelThreadField() {
    return getFieldByClass(BlockModelThreadField.class);
  }

  public CloseOnChildCloseField getCloseOnChildCloseField() {
    return getFieldByClass(CloseOnChildCloseField.class);
  }

  public OpenFormButton getOpenFormButton() {
    return getFieldByClass(OpenFormButton.class);
  }

  public FormFieldBox getFormFieldBox() {
    return getFieldByClass(FormFieldBox.class);
  }

  public FormField1GroupbBox getFormField1GroupbBox() {
    return getFieldByClass(FormField1GroupbBox.class);
  }

  public Field1Field getField1Field() {
    return getFieldByClass(Field1Field.class);
  }

  public HideField1Button getHideField1Button() {
    return getFieldByClass(HideField1Button.class);
  }

  public FormField2GroupbBox getFormField2GroupbBox() {
    return getFieldByClass(FormField2GroupbBox.class);
  }

  public Field2Field getField2Field() {
    return getFieldByClass(Field2Field.class);
  }

  public HideField2Button getHideField2Button() {
    return getFieldByClass(HideField2Button.class);
  }

  public FormField3GroupbBox getFormField3GroupbBox() {
    return getFieldByClass(FormField3GroupbBox.class);
  }

  public Field3Field getField3Field() {
    return getFieldByClass(Field3Field.class);
  }

  public HideField3Button getHideField3Button() {
    return getFieldByClass(HideField3Button.class);
  }

  public FormField4GroupbBox getFormField4GroupbBox() {
    return getFieldByClass(FormField4GroupbBox.class);
  }

  public Field4Field getField4Field() {
    return getFieldByClass(Field4Field.class);
  }

  public HideField4Button getHideField4Button() {
    return getFieldByClass(HideField4Button.class);
  }

  public LongRunningOperationBox getLongRunningOperationBox() {
    return getFieldByClass(LongRunningOperationBox.class);
  }

  public CancellationDurationField getCancellationDurationField() {
    return getFieldByClass(CancellationDurationField.class);
  }

  public StartLongRunningOperationButton getStartLongRunningOperationButton() {
    return getFieldByClass(StartLongRunningOperationButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ControllerBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Control how to open new forms";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10.0)
      public class DisplayHintField extends AbstractSmartField<DisplayHint> {

        @Override
        protected String getConfiguredLabel() {
          return "DisplayHint";
        }

        @Override
        protected Class<? extends ILookupCall<DisplayHint>> getConfiguredLookupCall() {
          return DisplayHintLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(DisplayHint.Dialog);
        }
      }

      @Order(20.0)
      public class ModalityField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Modal";
        }
      }

      @Order(30.0)
      public class DisplayParentField extends AbstractSmartField<DisplayParent> {

        @Override
        protected String getConfiguredLabel() {
          return "DisplayParent";
        }

        @Override
        protected Class<? extends ILookupCall<DisplayParent>> getConfiguredLookupCall() {
          return DisplayParentLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(DisplayParent.Desktop);
        }
      }

      @Order(40.0)
      public class OpeningDelayField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Opening delay [s]";
        }

        @Override
        protected void execInitField() {
          setValue(0);
        }
      }

      @Order(50.0)
      public class BlockModelThreadField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Block model thread during open";
        }
      }

      @Order(55.0)
      public class CloseOnChildCloseField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "close this when child closed";
        }
      }

      @Order(60.0)
      public class OpenFormButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return "Open Form";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          int openingDelay = (getOpeningDelayField().getValue() != null ? getOpeningDelayField().getValue() : 0);

          final IRunnable openFormRunnable = new IRunnable() {

            @Override
            public void run() throws Exception {
              DisplayHint displayHint = (getDisplayHintField().getValue() != null ? getDisplayHintField().getValue() : DisplayHint.Dialog);

              switch (displayHint) {
                case Dialog:
                case View: {
                  FormForm form = new FormForm();
                  form.setDisplayHint(displayHint.getValue());
                  DisplayParent displayParent = (getDisplayParentField().getValue() != null ? getDisplayParentField().getValue() : DisplayParent.Auto);
                  if (displayParent != DisplayParent.Auto) {
                    form.setDisplayParent(displayParent.getValue());
                  }
                  form.setModal(getModalityField().isChecked());
                  form.start();
                  if (getCloseOnChildCloseField().getValue()) {
                    form.addFormListener(new FormListener() {

                      @Override
                      public void formChanged(FormEvent e) {
                        if (e.getType() == FormEvent.TYPE_CLOSED) {
                          FormForm.this.doClose();
                        }
                      }
                    });
                  }

                  break;
                }
                case MessageBox: {
                  IMessageBox messageBox = MessageBoxes.createYesNoCancel().withHeader("Message box").withBody("I am a message box");
                  DisplayParent displayParent = (getDisplayParentField().getValue() != null ? getDisplayParentField().getValue() : DisplayParent.Auto);
                  if (displayParent != DisplayParent.Auto) {
                    messageBox.withDisplayParent(displayParent.getValue());
                  }
                  messageBox.show();
                  break;
                }
                case FileChooser: {
                  FileChooser fileChooser = new FileChooser();
                  DisplayParent displayParent = (getDisplayParentField().getValue() != null ? getDisplayParentField().getValue() : DisplayParent.Auto);
                  if (displayParent != DisplayParent.Auto) {
                    fileChooser.setDisplayParent(displayParent.getValue());
                  }
                  fileChooser.startChooser();
                  break;
                }
                default:
                  throw new IllegalArgumentException();
              }
            }
          };

          if (openingDelay == 0) {
            ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
          }
          else {
            if (getBlockModelThreadField().isChecked()) {
              try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(openingDelay));
                ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
              }
              catch (InterruptedException e) {
                throw new ProcessingException("Interrupted", e);
              }
            }
            else {
              Jobs.schedule(new IRunnable() {

                @Override
                public void run() throws Exception {
                  ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
                }
              }, Jobs.newInput()
                  .withRunContext(ClientRunContexts.copyCurrent())
                  .withSchedulingDelay(openingDelay, TimeUnit.SECONDS));
            }
          }
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(20.0)
    public class FormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Form fields";
      }

      @Order(10.0)
      public class FormField1GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10.0)
        public class Field1Field extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 1";
          }
        }

        @Order(20.0)
        public class HideField1Button extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Hide field in 3s";
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_LINK;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            ModelJobs.schedule(new IRunnable() {

              @Override
              public void run() throws Exception {
                getField1Field().setVisible(false);
              }
            }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                .withSchedulingDelay(3, TimeUnit.SECONDS));
          }
        }
      }

      @Order(20.0)
      public class FormField2GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10.0)
        public class Field2Field extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 2";
          }
        }

        @Order(20.0)
        public class HideField2Button extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Hide field in 3s";
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_LINK;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            ModelJobs.schedule(new IRunnable() {

              @Override
              public void run() throws Exception {
                getField2Field().setVisible(false);
              }
            }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                .withSchedulingDelay(3, TimeUnit.SECONDS));
          }
        }
      }

      @Order(30.0)
      public class FormField3GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10.0)
        public class Field3Field extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 3";
          }
        }

        @Order(20.0)
        public class HideField3Button extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Hide field in 3s";
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_LINK;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            ModelJobs.schedule(new IRunnable() {

              @Override
              public void run() throws Exception {
                getField3Field().setVisible(false);
              }
            }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                .withSchedulingDelay(3, TimeUnit.SECONDS));
          }
        }
      }

      @Order(40.0)
      public class FormField4GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10.0)
        public class Field4Field extends AbstractDateTimeField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 4";
          }
        }

        @Order(20.0)
        public class HideField4Button extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Hide field in 3s";
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_LINK;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            ModelJobs.schedule(new IRunnable() {

              @Override
              public void run() throws Exception {
                getField4Field().setVisible(false);
              }
            }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                .withSchedulingDelay(3, TimeUnit.SECONDS));
          }
        }
      }
    }

    @Order(30.0)
    public class LongRunningOperationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Long running operation";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Order(10.0)
      public class CancellationDurationField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Cancellation duration [s]";
        }

        @Override
        protected void execInitField() {
          setValue(2);
        }
      }

      @Order(20.0)
      public class StartLongRunningOperationButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return "Start long running operation";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          int cancellationDuration = (getCancellationDurationField().getValue() != null ? getCancellationDurationField().getValue() : 0);

          try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(30));
          }
          catch (InterruptedException e) {
            LOG.error("Interrupted", e);
          }

          try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(cancellationDuration));
          }
          catch (InterruptedException e) {
            LOG.error("Interrupted", e);
          }
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @ApplicationScoped
  public static class DisplayHintLookupCall extends LocalLookupCall<DisplayHint> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<DisplayHint>> execCreateLookupRows() {
      List<LookupRow<DisplayHint>> rows = new ArrayList<>();
      for (DisplayHint displayHint : DisplayHint.values()) {
        rows.add(new LookupRow<>(displayHint, displayHint.name()));
      }
      return rows;
    }

    public static enum DisplayHint {

      View(IForm.DISPLAY_HINT_VIEW), Dialog(IForm.DISPLAY_HINT_DIALOG), MessageBox(100), FileChooser(200);

      int m_value;

      private DisplayHint(int value) {
        m_value = value;
      }

      public int getValue() {
        return m_value;
      }
    }
  }

  @ApplicationScoped
  public static class DisplayParentLookupCall extends LocalLookupCall<DisplayParentLookupCall.DisplayParent> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<DisplayParent>> execCreateLookupRows() {
      List<LookupRow<DisplayParent>> rows = new ArrayList<>();
      for (DisplayParent displayParent : DisplayParent.values()) {
        rows.add(new LookupRow<>(displayParent, displayParent.name()));
      }
      return rows;
    }

    public static enum DisplayParent {
      Desktop() {

        @Override
        public IDisplayParent getValue() {
          return ClientRunContexts.copyCurrent().getDesktop();
        }

      },
      Outline() {

        @Override
        public IDisplayParent getValue() {
          return ClientRunContexts.copyCurrent().getOutline();
        }

      },
      Form() {

        @Override
        public IDisplayParent getValue() {
          return ClientRunContexts.copyCurrent().getForm();
        }

      },
      Auto() {

        @Override
        public IDisplayParent getValue() {
          return null;
        }
      };

      public abstract IDisplayParent getValue();
    }
  }
}
