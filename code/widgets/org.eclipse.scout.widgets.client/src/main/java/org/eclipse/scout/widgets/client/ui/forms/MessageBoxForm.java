/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.AnswerOptionsLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.AutoCloseMillisField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.BodyField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.CancelButtonTextField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.DefaultReturnValueField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.HeaderField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.HiddenTextContentField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.HtmlField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.IconIdField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.MessageBoxConfiguredButton;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.NoButtonTextField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.ReturnValueField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.SeverityField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ConfigurationBox.YesButtonTextField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ExamplesBox.MessageBoxOkButton;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.ExamplesBox.ResultField;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm.MainBox.SampleContentButton;
import org.eclipse.scout.widgets.client.ui.template.formfield.StatusSeverityLookupCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("f5f2af49-c779-4eda-b25a-a8fe964d368c")
public class MessageBoxForm extends AbstractForm implements IPageForm {
  private static final Logger LOG = LoggerFactory.getLogger(MessageBoxForm.class);

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("MessageBoxes");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public BodyField getBodyField() {
    return getFieldByClass(BodyField.class);
  }

  public HtmlField getHtmlField() {
    return getFieldByClass(HtmlField.class);
  }

  public AutoCloseMillisField getAutoCloseMillisField() {
    return getFieldByClass(AutoCloseMillisField.class);
  }

  public CancelButtonTextField getCancelButtonTextField() {
    return getFieldByClass(CancelButtonTextField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultReturnValueField getDefaultReturnValueField() {
    return getFieldByClass(DefaultReturnValueField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public HiddenTextContentField getHiddenTextContentField() {
    return getFieldByClass(HiddenTextContentField.class);
  }

  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  public HeaderField getHeaderField() {
    return getFieldByClass(HeaderField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MessageBoxConfiguredButton getMessageBoxConfiguredButton() {
    return getFieldByClass(MessageBoxConfiguredButton.class);
  }

  public MessageBoxOkButton getMessageBoxOkButton() {
    return getFieldByClass(MessageBoxOkButton.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public NoButtonTextField getNoButtonTextField() {
    return getFieldByClass(NoButtonTextField.class);
  }

  public ReturnValueField getReturnValueField() {
    return getFieldByClass(ReturnValueField.class);
  }

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  public YesButtonTextField getYesButtonTextField() {
    return getFieldByClass(YesButtonTextField.class);
  }

  public ResultField getResultField() {
    return getFieldByClass(ResultField.class);
  }

  public SeverityField getSeverityField() {
    return getFieldByClass(SeverityField.class);
  }

  @Order(10)
  @ClassId("666cb249-65ec-4e1d-ae74-5685e2652ed5")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("c39102ad-b3f2-4929-a257-0993bfd94ee7")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("2b259062-93a8-4826-8d2c-05ec30d81e3c")
      public class MessageBoxOkButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithOkButton");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.createOk().withHeader(TEXTS.get("MessageBoxWithOkButton")).withBody(TEXTS.get("Lorem")).show();
          getResultField().setMessageBoxResult(result);
        }
      }

      @Order(15)
      @ClassId("29e7c79a-b14d-4948-9f80-7153a459706c")
      public class MessageBoxHtmlButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithHtml");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.createOk()
              .withHeader("This is the header.")
              .withBody("This is the body.")
              .withHtml(HTML.fragment(
                  HTML.italic("This"),
                  " is ",
                  HTML.link("https://en.wikipedia.org/wiki/HTML", "HTML").addAttribute("target", "_blank"),
                  " content"))
              .show();
          getResultField().setMessageBoxResult(result);
        }
      }

      @Order(20)
      @ClassId("4f338f1a-e1a8-48bf-8cf5-5e0c9c4dddca")
      public class MessageBoxYesNoButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithYesNoButton");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.createYesNo().withHeader(TEXTS.get("Lorem")).withBody("Press \"Yes\" or \"No\"").show();
          getResultField().setMessageBoxResult(result);
        }
      }

      @Order(30)
      @ClassId("a740ad14-f027-4113-b7ff-2ee4fbacfa2e")
      public class MessageBoxYesNoCancelButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithYesNoCancelButton");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.createYesNoCancel().withHeader(TEXTS.get("Lorem")).withBody("Press \"Yes\", \"No\" or \"Cancel\"").show();
          getResultField().setMessageBoxResult(result);
        }
      }

      @Order(35)
      @ClassId("86328cf9-2e2d-4c2e-92ae-087049d4b639")
      public class MessageBoxWithLongText extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithVeryLongText");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.create()
              .withHeader(TEXTS.get("Lorem"))
              .withBody(TEXTS.get("Lorem") + "\n\n" + TEXTS.get("Lorem") + "\n\n" + TEXTS.get("Lorem"))
              .withYesButtonText(TEXTS.get("Lorem"))
              .show();
          getResultField().setMessageBoxResult(result);
        }
      }

      @Order(40)
      @ClassId("b3e32868-2bad-471a-bf17-37eb30aedc63")
      public class MessageBoxHiddenText extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithHiddenText");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.create()
              .withHeader(TEXTS.get("Lorem"))
              .withBody(TEXTS.get("HiddenTextInstruction"))
              .withCancelButtonText(TEXTS.get("CloseButton"))
              .withHiddenText(TEXTS.get("HiddenText"))
              .show();
          getResultField().setMessageBoxResult(result);
        }
      }

      @Order(50)
      @ClassId("94e0ec44-1473-40e8-a352-ea5de7504fce")
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
        protected void execClickAction() {
          boolean result = MessageBoxes.showDeleteConfirmationMessage("Items", new String[]{"Item1", "Item2", "Item3"});
          getResultField().setValue(result + "");
        }
      }

      @Order(60)
      @ClassId("6c7565fe-29f4-4774-8627-faf909c6ee06")
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
        protected void execClickAction() {
          getResultField().setValue("(VetoException)");
          throw new VetoException("This is a VetoException");
        }
      }

      @Order(70)
      @ClassId("ac6f3cd6-79cb-488f-9490-280c0f8b1d9e")
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
        protected void execClickAction() {
          getResultField().setValue("(ProcessingException)");
          throw new ProcessingException("This is a ProcessingException");
        }
      }

      @Order(80)
      @ClassId("c584b374-39a4-4b8a-8d01-85e63f265afa")
      public class MessageBoxFollowedByVetoExceptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return "MessageBox followed by VetoException";
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          int result = MessageBoxes.createYesNo().withBody("Do you like message boxes?").show();
          getResultField().setMessageBoxResult(result);
          throw new VetoException("You clicked " + (result == IMessageBox.YES_OPTION ? "YES" : "NO") + ".");
        }
      }

      @Order(90)
      @ClassId("680c642d-0f18-402a-8272-67cd3d8cb5e4")
      public class ResultField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Result");
        }

        public void setMessageBoxResult(int result) {
          String optionName = "" + result;
          try {
            Field[] f = IMessageBox.class.getDeclaredFields();
            for (Field field : f) {
              boolean isOptionConstant = Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && field.getName().endsWith("_OPTION");
              if (isOptionConstant) {
                int fieldValue = ((Number) field.get(null)).intValue();
                if (fieldValue == result) {
                  optionName = IMessageBox.class.getSimpleName() + "." + field.getName();
                  break;
                }
              }
            }
          }
          catch (Exception t) {
            LOG.warn("Error while reading fields from class", t);
          }
          setValue(optionName);
        }
      }
    }

    @Order(20)
    @ClassId("eda79ef9-3312-4ede-a335-aa31c5b2a4e1")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      protected void updateMessageBoxConfiguredButton() {
        String yesButtonText = getYesButtonTextField().getValue();
        String noButtonText = getNoButtonTextField().getValue();
        String cancelButtonText = getCancelButtonTextField().getValue();
        long autoCloseMillis = NumberUtility.nvl(getAutoCloseMillisField().getValue(), -1);

        // Prevent opening message boxes that cannot be closed again
        boolean messageBoxClosable = (yesButtonText != null || noButtonText != null || cancelButtonText != null || autoCloseMillis != -1);
        getMessageBoxConfiguredButton().setEnabled(messageBoxClosable, true, true);
      }

      @Order(10)
      @ClassId("a6b871fc-49af-424c-9771-f1413b14f3eb")
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
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected void execClickAction() {
          String header = getHeaderField().getValue();
          String body = getBodyField().getValue();
          IHtmlContent html = HTML.raw(getHtmlField().getValue());
          String yesButtonText = getYesButtonTextField().getValue();
          String noButtonText = getNoButtonTextField().getValue();
          String cancelButtonText = getCancelButtonTextField().getValue();
          String hiddenText = getHiddenTextContentField().getValue();
          String iconId = getIconIdField().getValue();

          long autoCloseMillis = NumberUtility.nvl(getAutoCloseMillisField().getValue(), -1);
          int defaultReturnValue = NumberUtility.nvl(getDefaultReturnValueField().getValue(), IMessageBox.CANCEL_OPTION);
          int severity = NumberUtility.nvl(getSeverityField().getValue(), IStatus.INFO);

          int result = MessageBoxes.create()
              .withSeverity(severity)
              .withHeader(header)
              .withBody(body)
              .withHtml(html)
              .withYesButtonText(yesButtonText)
              .withNoButtonText(noButtonText)
              .withCancelButtonText(cancelButtonText)
              .withHiddenText(hiddenText)
              .withIconId(iconId)
              .withAutoCloseMillis(autoCloseMillis)
              .show(defaultReturnValue);

          getReturnValueField().setValue(result);
        }
      }

      @Order(20)
      @ClassId("40592404-b106-4bf1-a556-dd93cc34dd7c")
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
          return AnswerOptionsLookupCall.class;
        }
      }

      @Order(30)
      @ClassId("55cad9fb-ae36-4e92-b9da-c4397c823aac")
      public class SeverityField extends AbstractSmartField<Integer> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Severity");
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return StatusSeverityLookupCall.class;
        }
      }

      @Order(40)
      @ClassId("dbfa0fcb-c9b0-4874-9e95-e2f690ce8e76")
      public class HeaderField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Header");
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(50)
      @ClassId("1c7871d0-4685-41e7-b92a-d2da6d5b5f95")
      public class BodyField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Body");
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(60)
      @ClassId("63829f35-10ba-4727-8073-c589a964cc36")
      public class HtmlField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Html");
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }

      @Order(70)
      @ClassId("dacfd5b0-6c06-4807-ae0f-97f1346e33a8")
      public class YesButtonTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("YesButtonText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          updateMessageBoxConfiguredButton();
        }
      }

      @Order(80)
      @ClassId("66d07478-0247-4ed3-a6b9-09e7f9cafbcb")
      public class NoButtonTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("NoButtonText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          updateMessageBoxConfiguredButton();
        }
      }

      @Order(90)
      @ClassId("824b6377-f09e-4c51-afe3-c65915541b77")
      public class CancelButtonTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CancelButtonText");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          updateMessageBoxConfiguredButton();
        }
      }

      @Order(110)
      @ClassId("0be889a6-e2e4-4605-bb2d-a67d7f04f74c")
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

      @Order(120)
      @ClassId("96e1b430-8d69-4267-a593-f904e281b2fa")
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
          return IconIdLookupCall.class;
        }
      }

      @Order(130)
      @ClassId("068de3eb-8f72-41e4-817c-225d5b9df325")
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
          return AnswerOptionsLookupCall.class;
        }
      }

      @Order(140)
      @ClassId("5452d22f-a21f-4bf8-8c6f-9cfd89b01c87")
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

        @Override
        protected void execChangedValue() {
          updateMessageBoxConfiguredButton();
        }
      }
    }

    @Order(30)
    @ClassId("74142797-831b-45dc-82dc-a2a4f675523c")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(40)
    @ClassId("533217d3-13cf-4476-a75e-d0d39870e740")
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getHeaderField().setValue(TEXTS.get("LoremQuestion"));
        getBodyField().setValue(TEXTS.get("LoremAction"));
        getHtmlField().setValue(TEXTS.get("LoremHtml"));
        getYesButtonTextField().setValue(TEXTS.get("YesButton"));
        getNoButtonTextField().setValue(TEXTS.get("NoButton"));
        getHiddenTextContentField().setValue(TEXTS.get("Lorem"));
        getIconIdField().setValue("status_info");
        getSeverityField().setValue(IStatus.INFO);
        getDefaultReturnValueField().setValue(IMessageBox.NO_OPTION);
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
