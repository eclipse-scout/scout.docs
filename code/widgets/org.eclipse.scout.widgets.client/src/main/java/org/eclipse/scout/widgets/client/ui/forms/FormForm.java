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
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.IDisplayParent;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
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
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.DisplayHintLookupCall.DisplayHint;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.DisplayParentLookupCall.DisplayParent;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.DisplayViewIdLookupCall.DisplayViewId;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.ClosableField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.CloseOnChildCloseField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayHintAndViewIdBox.DisplayHintField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayHintAndViewIdBox.DisplayViewIdField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.DisplayParentField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.IconIdField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.ModalityField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpenFormBox.OpenFormButton;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpeningDelayBox.BlockModelThreadField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.OpeningDelayBox.OpeningDelayField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.TitleBox.FormSubTitleField;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.MainBox.ControllerBox.TitleBox.FormTitleField;
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

@ClassId("b612310f-59b6-427d-93c9-57b384564a94")
public class FormForm extends AbstractForm implements IPageForm {

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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
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
      public class DisplayHintAndViewIdBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
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
            getDisplayViewIdField().setEnabled(displayHint == DisplayHint.View);
            getFormTitleField().setEnabled(CompareUtility.isOneOf(displayHint, DisplayHint.Dialog, DisplayHint.View, DisplayHint.PopupWindow));
          }
        }

        @Order(10)
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
      public class ModalityField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Modal";
        }
      }

      @Order(25)
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
      public class TitleBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
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
        public class FormSubTitleField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Subtitle";
          }

          @Override
          protected void execInitField() {
            String newSubTitle = StringUtility.nvl(getForm().getSubTitle(), "");
            if (newSubTitle.matches("^#\\d+$")) {
              newSubTitle = "#" + (Integer.parseInt(newSubTitle.substring(1)) + 1);
            }
            setValue(newSubTitle);
          }
        }
      }

      @Order(42)
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
      public class OpeningDelayBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
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
        public class BlockModelThreadField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Block model thread during open";
          }
        }
      }

      @Order(50)
      public class CloseOnChildCloseField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Close this form when child is closed";
        }
      }

      @Order(60)
      public class OpenFormBox extends AbstractSequenceBox {

        @Order(10)
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

            final IRunnable openFormRunnable = new IRunnable() {
              @Override
              public void run() throws Exception {
                DisplayHint displayHint = (getDisplayHintField().getValue() != null ? getDisplayHintField().getValue() : DisplayHint.Dialog);

                switch (displayHint) {
                  case Dialog:
                  case View:
                  case PopupWindow: {
                    FormForm form = new FormForm();
                    form.setTitle(getFormTitleField().getValue());
                    form.setSubTitle(getFormSubTitleField().getValue());
                    form.setDisplayHint(displayHint.getValue());
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
                Jobs.schedule(new IRunnable() {

                  @Override
                  public void run() throws Exception {
                    ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
                  }
                }, Jobs.newInput()
                    .withRunContext(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(openingDelay, TimeUnit.SECONDS)));
              }
            }
          }
        }
      }
    }

    @Order(20)
    public class FormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Form fields";
      }

      @Order(10)
      public class FormField1GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
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
      public class FormField2GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        public class Field2Field extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 2";
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
      public class FormField3GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        public class Field3Field extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 3";
          }
        }

        @Order(20)
        public class Buttons3Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField3Field();
          }
        }
      }

      @Order(40)
      public class FormField4GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        public class Field4Field extends AbstractDateTimeField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 4";
          }
        }

        @Order(20)
        public class Buttons4Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField4Field();
          }
        }
      }
    }

    @Order(30)
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

      View(IForm.DISPLAY_HINT_VIEW),
      Dialog(IForm.DISPLAY_HINT_DIALOG),
      PopupWindow(IForm.DISPLAY_HINT_POPUP_WINDOW),
      MessageBox(100),
      FileChooser(200);

      private final int m_value;

      private DisplayHint(int value) {
        m_value = value;
      }

      public int getValue() {
        return m_value;
      }
    }
  }

  @ApplicationScoped
  public static class DisplayViewIdLookupCall extends LocalLookupCall<DisplayViewId> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<DisplayViewId>> execCreateLookupRows() {
      List<LookupRow<DisplayViewId>> rows = new ArrayList<>();
      for (DisplayViewId displayViewId : DisplayViewId.values()) {
        rows.add(new LookupRow<>(displayViewId, displayViewId.getDisplayText()));
      }
      return rows;
    }

    public static enum DisplayViewId {

      N(IForm.VIEW_ID_N),
      NE(IForm.VIEW_ID_NE),
      E(IForm.VIEW_ID_E),
      SE(IForm.VIEW_ID_SE),
      S(IForm.VIEW_ID_S),
      SW(IForm.VIEW_ID_SW),
      W(IForm.VIEW_ID_W),
      NW(IForm.VIEW_ID_NW),
      Center(IForm.VIEW_ID_CENTER, "C (Center)"),
      Outline(IForm.VIEW_ID_OUTLINE, "[Deprecated] Outline"),
      OutlineSelector(IForm.VIEW_ID_OUTLINE_SELECTOR, "[Deprecated] OutlineSelector"),
      Detail(IForm.VIEW_ID_PAGE_DETAIL, "[Deprecated] PageDetail"),
      Search(IForm.VIEW_ID_PAGE_SEARCH, "[Deprecated] PageSearch"),
      Table(IForm.VIEW_ID_PAGE_TABLE, "[Deprecated] PageTable");

      private final String m_value;
      private final String m_displayText;

      private DisplayViewId(String value) {
        this(value, null);
      }

      private DisplayViewId(String value, String displayText) {
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

  @Order(15)
  public static abstract class AbstractFieldButtonsBox extends AbstractSequenceBox {
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
        ModelJobs.schedule(new IRunnable() {

          @Override
          public void run() throws Exception {
            getField().setVisible(false);
          }
        }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
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
