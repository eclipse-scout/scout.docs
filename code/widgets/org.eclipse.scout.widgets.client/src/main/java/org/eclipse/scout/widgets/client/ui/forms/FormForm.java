/*
 * Copyright (c) 2023 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.IDisplayParent;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.deeplink.FormDeepLinkHandler;
import org.eclipse.scout.widgets.client.services.lookup.DisplayViewIdLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.DisplayViewIdLookupCall.DisplayViewId;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.DisplayHintLookupCall.DisplayHint;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.DisplayParentLookupCall.DisplayParent;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.CacheBoundsField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.ClosableField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.CloseOnChildCloseField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayHintAndViewIdBox.DisplayHintField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayHintAndViewIdBox.DisplayViewIdField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayParentField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.IconIdField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.MaximizedField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.ModalityBox.ModalityField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.NotificationBadgeTextField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpenFormBox.OpenFormButton;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpenFormBox.OpenInNewSessionButton;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpeningDelayBox.BlockModelThreadField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpeningDelayBox.OpeningDelayField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.TitleBox.FormSubTitleField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.TitleBox.FormTitleField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.EditFormPropertiesButton;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField1GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField1GroupbBox.Buttons1Box;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField1GroupbBox.Field1Field;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField2GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField2GroupbBox.Buttons2Box;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField2GroupbBox.Field2Field;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField3GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField3GroupbBox.Field3Field;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField4GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.FormFieldBox.FormField4GroupbBox.Field4Field;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.LongRunningOperationBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.LongRunningOperationBox.LongRunningDurationField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.LongRunningOperationBox.StartLongRunningOperationButton;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractStatusButton;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("b612310f-59b6-427d-93c9-57b384564a94")
public class FormForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Form";
  }

  @Override
  protected String getConfiguredSubTitle() {
    return "#0";
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

  public DisplayViewIdField getDisplayViewIdField() {
    return getFieldByClass(DisplayViewIdField.class);
  }

  public ModalityField getModalityField() {
    return getFieldByClass(ModalityField.class);
  }

  public ClosableField getClosableField() {
    return getFieldByClass(ClosableField.class);
  }

  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  public NotificationBadgeTextField getNotificationBadgeTextField() {
    return getFieldByClass(NotificationBadgeTextField.class);
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

  public Buttons1Box getButtons1Box() {
    return getFieldByClass(Buttons1Box.class);
  }

  public Buttons2Box getButtons2Box() {
    return getFieldByClass(Buttons2Box.class);
  }

  public EditFormPropertiesButton getEditFormPropertiesButton() {
    return getFieldByClass(EditFormPropertiesButton.class);
  }

  public OpenInNewSessionButton getOpenInNewSessionButton() {
    return getFieldByClass(OpenInNewSessionButton.class);
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

  public FormField2GroupbBox getFormField2GroupbBox() {
    return getFieldByClass(FormField2GroupbBox.class);
  }

  public Field2Field getField2Field() {
    return getFieldByClass(Field2Field.class);
  }

  public FormField3GroupbBox getFormField3GroupbBox() {
    return getFieldByClass(FormField3GroupbBox.class);
  }

  public Field3Field getField3Field() {
    return getFieldByClass(Field3Field.class);
  }

  public FormField4GroupbBox getFormField4GroupbBox() {
    return getFieldByClass(FormField4GroupbBox.class);
  }

  public Field4Field getField4Field() {
    return getFieldByClass(Field4Field.class);
  }

  public LongRunningOperationBox getLongRunningOperationBox() {
    return getFieldByClass(LongRunningOperationBox.class);
  }

  public LongRunningDurationField getLongRunningDurationField() {
    return getFieldByClass(LongRunningDurationField.class);
  }

  public StartLongRunningOperationButton getStartLongRunningOperationButton() {
    return getFieldByClass(StartLongRunningOperationButton.class);
  }

  public FormTitleField getFormTitleField() {
    return getFieldByClass(FormTitleField.class);
  }

  public FormSubTitleField getFormSubTitleField() {
    return getFieldByClass(FormSubTitleField.class);
  }

  public CacheBoundsField getCacheBoundsField() {
    return getFieldByClass(CacheBoundsField.class);
  }

  public MaximizedField getMaximizedField() {
    return getFieldByClass(MaximizedField.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @Order(10)
  @ClassId("5e630b67-1e84-4f5e-a487-4bb6f0069faa")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("93355511-5eb5-4907-aaa0-d1b7f9a10c80")
    public class ControllerBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Control how to open new forms";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("5c6d94d0-c865-4bd6-bce1-5782287dce1a")
      public class DisplayHintAndViewIdBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
        @ClassId("3076479e-24b4-43c3-8d54-218a289b4614")
        public class DisplayHintField extends AbstractSmartField<DisplayHint> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DisplayHint");
          }

          @Override
          protected Class<? extends ILookupCall<DisplayHint>> getConfiguredLookupCall() {
            return DisplayHintLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(DisplayHint.Dialog);
            fireValueChanged(); // update button label
          }

          @Override
          protected void execChangedValue() {
            DisplayHint displayHint = (getValue() != null ? getValue() : DisplayHint.Dialog);
            getOpenFormButton().setLabel("Open " + displayHint.name());
            getDisplayViewIdField().setEnabled(displayHint == DisplayHint.View, true, true);
            getFormTitleField().setEnabled(ObjectUtility.isOneOf(displayHint, DisplayHint.Dialog, DisplayHint.View, DisplayHint.PopupWindow), true, true);
          }
        }

        @Order(10)
        @ClassId("4a02d0ef-7105-4c73-96bf-4d7461100d58")
        public class DisplayViewIdField extends AbstractSmartField<DisplayViewId> {

          @Override
          protected String getConfiguredLabel() {
            return "ViewId";
          }

          @Override
          protected Class<? extends ILookupCall<DisplayViewId>> getConfiguredLookupCall() {
            return DisplayViewIdLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(null);
          }
        }
      }

      @Order(20)
      @ClassId("76968db0-2ce6-4f97-afa2-21b2fffd81c7")
      public class ModalityBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
        @ClassId("6dac9c80-b3eb-4deb-8f20-b4d02e66b655")
        public class ModalityField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Modal";
          }
        }
      }

      @Order(25)
      @ClassId("efaaf31c-509a-4dcb-8b42-0db8609e4194")
      public class ClosableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Closable";
        }

        @Override
        protected void execInitField() {
          setChecked(true);
        }
      }

      @Order(30)
      @ClassId("48d88503-7505-4b59-ace2-9733824bbd4a")
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

      @Order(40)
      @ClassId("623eb795-76a9-4a4f-84ef-d4c39bf8cb27")
      public class TitleBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
        @ClassId("cbb71c43-2a25-440a-8b86-34e183e9fcdd")
        public class FormTitleField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Form title";
          }

          @Override
          protected void execInitField() {
            setValue(getForm().getTitle());
          }
        }

        @Order(20)
        @ClassId("176ded1b-365c-4dc1-8fec-a803505a6ab0")
        public class FormSubTitleField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Subtitle");
          }

          @Override
          protected void execInitField() {
            String newSubTitle = StringUtility.emptyIfNull(getForm().getSubTitle());
            if (newSubTitle.matches("^#\\d+$")) {
              newSubTitle = "#" + (Integer.parseInt(newSubTitle.substring(1)) + 1);
            }
            setValue(newSubTitle);
          }
        }
      }

      @Order(41)
      @ClassId("e646264e-959a-454c-b801-4b3e540697ac")
      public class NotificationBadgeTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "Notification badge text";
        }

        @Override
        protected void execInitField() {
          setValue(getForm().getNotificationBadgeText());
        }
      }

      @Order(42)
      @ClassId("9469f9f9-1fc6-46b3-b819-a34adfea2cbd")
      public class IconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return "Icon";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return IconIdLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(getForm().getIconId());
        }

        @Order(10)
        @ClassId("46dbd61a-840f-4042-885f-a05f3f26b3fd")
        public class ChangeCurrentFormIconIdMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Set to current form";
          }

          @Override
          protected void execAction() {
            getForm().setIconId(getIconIdField().getValue());
          }
        }
      }

      @Order(45)
      @ClassId("6bdcc32e-09ac-472a-b52c-e3a1079d2a79")
      public class OpeningDelayBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
        @ClassId("cab1fb20-421b-44a5-828b-8383ff8e1f8c")
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

        @Order(20)
        @ClassId("d858666f-746c-40f5-a455-9a8835f8ed4e")
        public class BlockModelThreadField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Block model thread during open";
          }
        }
      }

      @Order(50)
      @ClassId("179c35f6-eaf6-40bd-8cd1-bec0e3641277")
      public class CloseOnChildCloseField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Close this form when child is closed";
        }
      }

      @Order(55)
      @ClassId("8aa3a8d5-7c33-47a4-869d-f2a318fbf516")
      public class CacheBoundsField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Cache bounds";
        }

        @Override
        protected void execInitField() {
          setValue(getForm().isCacheBounds());
        }
      }

      @Order(57)
      @ClassId("a7ba962b-f5e9-41e2-bdd3-cc0b82a2c4ab")
      public class MaximizedField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Maximized";
        }

        @Override
        protected void execInitField() {
          setValue(getForm().isMaximized());
        }
      }

      @Order(60)
      @ClassId("961eaeba-6fc5-45c6-bc1c-b60484a0b3fd")
      public class OpenFormBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("adaeef40-bdd4-4afd-a60b-9556bcf388f4")
        public class OpenFormButton extends AbstractButton {

          // Note: Label is set by DisplayHintField

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredCssClass() {
            return "open-form-button";
          }

          @Override
          protected void execClickAction() {
            int openingDelay = (getOpeningDelayField().getValue() != null ? getOpeningDelayField().getValue() : 0);

            final IRunnable openFormRunnable = () -> {
              DisplayHint displayHint = (getDisplayHintField().getValue() != null ? getDisplayHintField().getValue() : DisplayHint.Dialog);

              switch (displayHint) {
                case Dialog:
                case View:
                case PopupWindow: {
                  FormForm form = new FormForm();
                  form.setTitle(getFormTitleField().getValue());
                  form.setSubTitle(getFormSubTitleField().getValue());
                  form.setNotificationBadgeText(getNotificationBadgeTextField().getValue());
                  form.setDisplayHint(displayHint.getValue());
                  form.setCacheBounds(getCacheBoundsField().getValue());
                  form.setMaximized(getMaximizedField().getValue());
                  DisplayViewId viewId = getDisplayViewIdField().getValue();
                  if (viewId != null) {
                    form.setDisplayViewId(viewId.getValue());
                  }
                  DisplayParent displayParent = (getDisplayParentField().getValue() != null ? getDisplayParentField().getValue() : DisplayParent.Auto);
                  if (displayParent != DisplayParent.Auto) {
                    form.setDisplayParent(displayParent.getValue());
                  }
                  form.setModal(getModalityField().isChecked());
                  form.setClosable(getClosableField().isChecked());
                  form.setIconId(getIconIdField().getValue());
                  form.start();
                  if (getCloseOnChildCloseField().getValue()) {
                    form.addFormListener(e -> {
                      if (e.getType() == FormEvent.TYPE_CLOSED) {
                        FormForm.this.doClose();
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
                  messageBox.withIconId(getIconIdField().getValue());
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
            };

            if (openingDelay == 0) {
              ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
            }
            else {
              if (getBlockModelThreadField().isChecked()) {
                SleepUtil.sleepElseThrow(openingDelay, TimeUnit.SECONDS);
                ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
              }
              else {
                Jobs.schedule(() -> {
                  ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
                }, Jobs.newInput()
                    .withRunContext(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(openingDelay, TimeUnit.SECONDS)));
              }
            }
          }
        }

        @Order(2000)
        @ClassId("61e5e631-0f04-4235-bc01-30b08830fbdf")
        public class OpenInNewSessionButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OpenInNewSession");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            String deepLink = BEANS.get(FormDeepLinkHandler.class)
                .createUriForForm(FormForm.class);
            ClientSessionProvider.currentSession().getDesktop().openUri(deepLink, OpenUriAction.NEW_WINDOW);
          }
        }
      }
    }

    @Order(20)
    @ClassId("655ef17e-8dde-467b-b868-d1bb15b8f87d")
    public class FormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Form fields";
      }

      @Order(10)
      @ClassId("66f13c69-c1eb-4d3f-9e2e-7d7b0f9e18b0")
      public class FormField1GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("703d9e4d-c0bd-48cc-982c-6525e48ad4a5")
        public class Field1Field extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 1";
          }
        }

        @Order(15)
        @ClassId("628359df-90ac-4954-bd69-aa38bbf1d2c6")
        public class Buttons1Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField1Field();
          }
        }
      }

      @Order(20)
      @ClassId("4b202cf2-8bc0-49e7-8d5d-905bcf23bfa1")
      public class FormField2GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("9fc02937-e9e7-4278-b5b1-b8069f54d6b0")
        public class Field2Field extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 2";
          }

          @Order(1000)
          @ClassId("0847a312-c6a5-4238-b588-10215c8c1425")
          public class ShowValueMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DisplayValue");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Value is " + getValue()).show();
            }

          }

          @Order(2000)
          @ClassId("1077cbce-f7c2-4097-b76f-f6482f6db08e")
          public class StyleMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("FieldStyle");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
            }

            @Order(1000)
            @ClassId("f13aa648-a3de-4cab-90e0-e7559ca1df7d")
            public class ClassicMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Classic";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
              }

              @Override
              protected void execAction() {
                getField2Field().setFieldStyle(IFormField.FIELD_STYLE_CLASSIC);
              }
            }

            @Order(2000)
            @ClassId("10861a5b-cb69-4dd7-ac8b-e33956386fe6")
            public class AlternativeMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Alternative");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
              }

              @Override
              protected void execAction() {
                getField2Field().setFieldStyle(IFormField.FIELD_STYLE_ALTERNATIVE);
              }
            }
          }
        }

        @ClassId("b057fa1f-d478-4007-896b-254a71d6dfba")
        @Order(20)
        public class Buttons2Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField2Field();
          }
        }
      }

      @Order(30)
      @ClassId("2f51ed36-d65b-42a6-aaf3-dc819004f6da")
      public class FormField3GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("a21bad2c-41f7-41f3-a7cc-6fbabf5fbc94")
        public class Field3Field extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 3";
          }
        }

        @Order(20)
        @ClassId("6b123018-d6f5-454a-a42d-481555fd58f2")
        public class Buttons3Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField3Field();
          }
        }
      }

      @Order(40)
      @ClassId("3c87fa87-bbec-44d6-bb1e-84141e627616")
      public class FormField4GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("761b72d8-9d7a-48a7-8ac6-3da261862303")
        public class Field4Field extends AbstractDateTimeField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 4";
          }
        }

        @Order(20)
        @ClassId("1026e1b7-cc05-4d12-a824-4cfb85775dc2")
        public class Buttons4Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField4Field();
          }
        }
      }
    }

    @Order(30)
    @ClassId("7fda4a32-5b38-453b-8d1c-b1b0b5654568")
    public class LongRunningOperationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Long running operation";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Order(10)
      @ClassId("dd30ea25-493d-41a2-ac9e-6f4d86513e06")
      public class LongRunningDurationField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Duration [s]";
        }

        @Override
        protected void execInitField() {
          setValue(30);
        }
      }

      @Order(20)
      @ClassId("bccd50d8-21fe-4086-b5b6-cf122703f97c")
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
          int duration = NumberUtility.nvl(getLongRunningDurationField().getValue(), 0);
          SleepUtil.sleepSafe(duration, TimeUnit.SECONDS);
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(40)
    @ClassId("39a355d4-f6bf-456c-ae2b-9984fab541e4")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(2000)
    @ClassId("c190fc80-5c69-4096-975c-3f3cc98f7deb")
    public class EditFormPropertiesButton extends AbstractButton {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("EditFormPropertiesOfCurrentForm");
      }

      @Override
      protected void execClickAction() {
        FormOptionsForm form = new FormOptionsForm(FormForm.this);
        form.start();
      }
    }

  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ApplicationScoped
  @ClassId("981e87fb-5680-45db-b6e9-53865b4ece31")
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

    public enum DisplayHint {

      View(IForm.DISPLAY_HINT_VIEW),
      Dialog(IForm.DISPLAY_HINT_DIALOG),
      PopupWindow(IForm.DISPLAY_HINT_POPUP_WINDOW),
      MessageBox(100),
      FileChooser(200);

      private final int m_value;

      DisplayHint(int value) {
        m_value = value;
      }

      public int getValue() {
        return m_value;
      }
    }
  }

  @ApplicationScoped
  @ClassId("ef83cba9-9f36-4ef3-8321-ad65aafa91f3")
  public static class DisplayParentLookupCall extends LocalLookupCall<DisplayParent> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<DisplayParent>> execCreateLookupRows() {
      List<LookupRow<DisplayParent>> rows = new ArrayList<>();
      for (DisplayParent displayParent : DisplayParent.values()) {
        rows.add(new LookupRow<>(displayParent, displayParent.name()));
      }
      return rows;
    }

    public enum DisplayParent {
      Desktop() {

        @Override
        public IDisplayParent getValue() {
          return IDesktop.CURRENT.get();
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

  @ApplicationScoped
  @ClassId("effa0008-9b49-4c98-9a4a-a2d6eef411a4")
  public static class IconIdLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      List<ILookupRow<String>> rows = new ArrayList<>();
      for (IconId iconId : IconId.values()) {
        rows.add(
            new LookupRow<>(iconId.getValue(), iconId.getDisplayText()).withIconId(iconId.getValue()));
      }
      return rows;
    }

    public enum IconId {
      Calendar(Icons.Calendar),
      Person(Icons.PersonSolid),
      Square(Icons.SquareSolid),
      Star(Icons.Star),
      Sum(Icons.Sum),
      World(Icons.World);

      private final String m_value;
      private final String m_displayText;

      IconId(String value) {
        this(value, null);
      }

      IconId(String value, String displayText) {
        m_value = value;
        m_displayText = (displayText == null ? name() : displayText);
      }

      public String getValue() {
        return m_value;
      }

      public String getDisplayText() {
        return m_displayText;
      }
    }
  }

  @Order(15)
  @ClassId("a50aa909-95e2-4503-bd09-a789b21801d0")
  public abstract static class AbstractFieldButtonsBox extends AbstractSequenceBox {
    @Override
    protected boolean getConfiguredAutoCheckFromTo() {
      return false;
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    protected abstract IFormField getField();

    @Order(20)
    @ClassId("b10082b5-a49d-419b-b112-875576b1c74d")
    public class HideFieldButton extends AbstractButton {

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
        ModelJobs.schedule(() -> getField().setVisible(false), ModelJobs.newInput(ClientRunContexts.copyCurrent())
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withStartIn(3, TimeUnit.SECONDS)));
      }
    }

    @Order(2000)
    @ClassId("e2e8be68-0b2e-47d9-a25e-155d2628a71b")
    public class StatusButton extends AbstractStatusButton {

      @Override
      protected IFormField getField() {
        return AbstractFieldButtonsBox.this.getField();
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }
    }
  }
}
