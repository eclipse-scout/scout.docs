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

import org.eclipse.scout.commons.NumberUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.AnswerOptionsLookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.ActionTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.AutoCloseMillisField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.CancelButtonTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.DefaultReturnValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.HiddenTextContentField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.IconIdField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.IntroTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.MessageBoxConfiguredButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.NoButtonTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.Place0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.Place1Field;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.ReturnValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.TitleField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.YesButtonTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.ExamplesBox.MessageBoxOkButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm.MainBox.SampleContentButton;

public class MessageBoxForm extends AbstractForm implements IPageForm {

  public MessageBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LabelField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the ActionTextField
   */
  public ActionTextField getActionTextField() {
    return getFieldByClass(ActionTextField.class);
  }

  /**
   * @return the AutoCloseMillisField
   */
  public AutoCloseMillisField getAutoCloseMillisField() {
    return getFieldByClass(AutoCloseMillisField.class);
  }

  /**
   * @return the CancelButtonTextField
   */
  public CancelButtonTextField getCancelButtonTextField() {
    return getFieldByClass(CancelButtonTextField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DefaultReturnValueField
   */
  public DefaultReturnValueField getDefaultReturnValueField() {
    return getFieldByClass(DefaultReturnValueField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  /**
   * @return the HiddenTextContentField
   */
  public HiddenTextContentField getHiddenTextContentField() {
    return getFieldByClass(HiddenTextContentField.class);
  }

  /**
   * @return the IconIdField
   */
  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  /**
   * @return the IntroTextField
   */
  public IntroTextField getIntroTextField() {
    return getFieldByClass(IntroTextField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the MessageBoxConfiguredButton
   */
  public MessageBoxConfiguredButton getMessageBoxConfiguredButton() {
    return getFieldByClass(MessageBoxConfiguredButton.class);
  }

  /**
   * @return the MessageBoxOkButton
   */
  public MessageBoxOkButton getMessageBoxOkButton() {
    return getFieldByClass(MessageBoxOkButton.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  /**
   * @return the NoButtonTextField
   */
  public NoButtonTextField getNoButtonTextField() {
    return getFieldByClass(NoButtonTextField.class);
  }

  /**
   * @return the Place0Field
   */
  public Place0Field getPlace0Field() {
    return getFieldByClass(Place0Field.class);
  }

  /**
   * @return the Place1Field
   */
  public Place1Field getPlace1Field() {
    return getFieldByClass(Place1Field.class);
  }

  /**
   * @return the ReturnValueField
   */
  public ReturnValueField getReturnValueField() {
    return getFieldByClass(ReturnValueField.class);
  }

  /**
   * @return the SampleContentButton
   */
  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  /**
   * @return the TitleField
   */
  public TitleField getTitleField() {
    return getFieldByClass(TitleField.class);
  }

  /**
   * @return the YesButtonTextField
   */
  public YesButtonTextField getYesButtonTextField() {
    return getFieldByClass(YesButtonTextField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class MessageBoxOkButton extends AbstractLinkButton {

        private static final String LABEL = "MessageBoxWithOkButton";

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get(LABEL);
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showOkMessage(TEXTS.get(LABEL), null, TEXTS.get("Lorem"));
        }
      }

      @Order(20.0)
      public class MessageBoxYesNoButton extends AbstractLinkButton {

        private static final String LABEL = "MessageBoxWithYesNoButton";

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get(LABEL);
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showYesNoMessage(TEXTS.get(LABEL), TEXTS.get("Lorem"), "Press \"Yes\" or \"No\"");
        }
      }

      @Order(30.0)
      public class MessageBoxYesNoCancelButton extends AbstractLinkButton {

        private static final String LABEL = "MessageBoxWithYesNoCancelButton";

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get(LABEL);
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showYesNoCancelMessage(TEXTS.get(LABEL), TEXTS.get("Lorem"), "Press \"Yes\", \"No\" or \"Cancel\"");
        }
      }

      @Order(40.0)
      public class MessageBoxHiddenText extends AbstractLinkButton {

        private static final String LABEL = "MessageBoxWithHiddenText";

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get(LABEL);
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox msgbox = new MessageBox(TEXTS.get(LABEL), TEXTS.get("Lorem"), TEXTS.get("HiddenTextInstruction"), null, null, TEXTS.get("CloseButton"), TEXTS.get("HiddenText"), null);
          msgbox.startMessageBox();
        }
      }

      @Order(50.0)
      public class DeleteConfirmationMessageButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DeleteConfirmationMessage");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showDeleteConfirmationMessage("Items", new String[]{"Item1", "Item2", "Item3"});
        }
      }

      @Order(60.0)
      public class VetoExceptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VetoException");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          throw new VetoException("This is a VetoException");
        }
      }

      @Order(70.0)
      public class ProcessingExceptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ProcessingException");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          throw new ProcessingException("This is a ProcessingException");
        }
      }

    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class MessageBoxConfiguredButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxConfigured");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          String title = getTitleField().getValue();
          String introText = getIntroTextField().getValue();
          String actionText = getActionTextField().getValue();
          String yesButtonText = getYesButtonTextField().getValue();
          String noButtonText = getNoButtonTextField().getValue();
          String cancelButtonText = getCancelButtonTextField().getValue();
          String hiddenText = getHiddenTextContentField().getValue();
          String iconId = getIconIdField().getValue();

          long autoCloseMillis = NumberUtility.nvl(getAutoCloseMillisField().getValue(), -1);
          int defaultReturnValue = NumberUtility.nvl(getDefaultReturnValueField().getValue(), IMessageBox.CANCEL_OPTION);

          MessageBox msgbox = new MessageBox(title, introText, actionText, yesButtonText, noButtonText, cancelButtonText, hiddenText, iconId);
          msgbox.setAutoCloseMillis(autoCloseMillis);

          int result = msgbox.startMessageBox(defaultReturnValue);
          getReturnValueField().setValue(result);
        }
      }

      @Order(20.0)
      public class ReturnValueField extends AbstractSmartField<Integer> {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ReturnValue");
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<Integer>>) AnswerOptionsLookupCall.class;
        }

      }

      @Order(30.0)
      public class TitleField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Title");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(40.0)
      public class IntroTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IntroText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(50.0)
      public class ActionTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ActionText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(60.0)
      public class YesButtonTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("YesButtonText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(70.0)
      public class NoButtonTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("NoButtonText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(80.0)
      public class CancelButtonTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CancelButtonText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(90.0)
      public class Place1Field extends AbstractPlaceholderField {
      }

      @Order(100.0)
      public class HiddenTextContentField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HiddenTextContent");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(110.0)
      public class IconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IconId");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }
      }

      @Order(120.0)
      public class DefaultReturnValueField extends AbstractSmartField<Integer> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DefaultReturnValue");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<Integer>>) AnswerOptionsLookupCall.class;
        }

      }

      @Order(130.0)
      public class AutoCloseMillisField extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoCloseMillis");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Long getConfiguredMinValue() {
          return -1L;
        }
      }

      @Order(140.0)
      public class Place0Field extends AbstractPlaceholderField {
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(40.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getTitleField().setValue(TEXTS.get("LoremTitle"));
        getIntroTextField().setValue(TEXTS.get("LoremQuestion"));
        getActionTextField().setValue(TEXTS.get("LoremAction"));
        getYesButtonTextField().setValue(TEXTS.get("YesButton"));
        getNoButtonTextField().setValue(TEXTS.get("NoButton"));
        getHiddenTextContentField().setValue(TEXTS.get("Lorem"));
        getIconIdField().setValue("status_info");
        getDefaultReturnValueField().setValue(IMessageBox.NO_OPTION);
        getAutoCloseMillisField().setValue(5000L);
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
