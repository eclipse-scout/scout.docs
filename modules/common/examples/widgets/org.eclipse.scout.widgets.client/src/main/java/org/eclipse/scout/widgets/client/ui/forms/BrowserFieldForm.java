/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.AbstractBrowserField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.dataobject.IDataObject;
import org.eclipse.scout.rt.dataobject.IDataObjectMapper;
import org.eclipse.scout.rt.dataobject.IValueFormatConstants;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.ReadOnlyProperty;
import org.eclipse.scout.widgets.client.WidgetsHelper;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.BrowserField;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.LinksBox;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.LinksBox.BingButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.LinksBox.BsiSoftwareButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.LinksBox.ExampleNetButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.LinksBox.PostMessageDemoButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.LinksBox.TestHtmlButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.SetFileBox.BinaryResourceField;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.SetFileBox.SetFileButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.SetUrlBox.SetUrlButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserFieldBox.SetUrlBox.URLField;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.TabBox.MessagesBox.ButtonsGroup.SendAsJsonButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.TabBox.MessagesBox.ButtonsGroup.SendAsTextButton;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.TabBox.MessagesBox.LogField;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.TabBox.MessagesBox.MessageField;
import org.eclipse.scout.widgets.client.ui.forms.BrowserFieldForm.MainBox.TabBox.MessagesBox.TargetOriginField;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * The following code-snippet shows what the content of an IFRAME must do to call the execPostMessage() callback. This
 * will only work, when the IFRAME is not restricted by the sandbox attribute.
 *
 * <pre>
 * &lt;script&gt;
 * function postMessage() {
 *   window.parent.postMessage('hello Scout application!', 'http://localhost:8082');
 * }
 * &lt;/script&gt;
 * &lt;button onclick="postMessage()"&gt;Post message&lt;/button&gt;
 * </pre>
 *
 * The second parameter (targetOrigin) of the postMessage function is important, it points to the domain where the Scout
 * application runs. When the IFRAME content is called from another domain than localhost:8082, the browser will NOT
 * execute the function. You could also use '*' as targetOrigin, when you don't care which domain exactly should be
 * allowed to receive post messages from the IFRAME content.
 */
@Order(8100)
@ClassId("8149752e-43cc-4b26-8b4e-e6e8e7877216")
public class BrowserFieldForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BrowserField");
  }

  @Override
  protected void execInitForm() {
    // Load default content
    getTestHtmlButton().doClick();
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public BrowserField getBrowserField() {
    return getFieldByClass(BrowserField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ExampleNetButton getExampleNetButton() {
    return getFieldByClass(ExampleNetButton.class);
  }

  public BingButton getBingButton() {
    return getFieldByClass(BingButton.class);
  }

  public BsiSoftwareButton getBsiSoftwareButton() {
    return getFieldByClass(BsiSoftwareButton.class);
  }

  public TestHtmlButton getTestHtmlButton() {
    return getFieldByClass(TestHtmlButton.class);
  }

  public BrowserFieldBox getBrowserFieldBox() {
    return getFieldByClass(BrowserFieldBox.class);
  }

  public PostMessageDemoButton getPostMessageDemoButton() {
    return getFieldByClass(PostMessageDemoButton.class);
  }

  public LinksBox getLinksBox() {
    return getFieldByClass(LinksBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public URLField getURLField() {
    return getFieldByClass(URLField.class);
  }

  public BinaryResourceField getBinaryResourceField() {
    return getFieldByClass(BinaryResourceField.class);
  }

  public SetUrlButton getSetUrlButton() {
    return getFieldByClass(SetUrlButton.class);
  }

  public SetFileButton getSetFileButton() {
    return getFieldByClass(SetFileButton.class);
  }

  public LogField getLogField() {
    return getFieldByClass(LogField.class);
  }

  public MessageField getMessageField() {
    return getFieldByClass(MessageField.class);
  }

  public TargetOriginField getTargetOriginField() {
    return getFieldByClass(TargetOriginField.class);
  }

  public SendAsTextButton getSendAsTextButton() {
    return getFieldByClass(SendAsTextButton.class);
  }

  public SendAsJsonButton getSendAsJsonButton() {
    return getFieldByClass(SendAsJsonButton.class);
  }

  protected void logMessage(String message) {
    String line = "[" + DateUtility.format(new Date(), IValueFormatConstants.TIMESTAMP_PATTERN) + "] " + message;
    getLogField().setValue(StringUtility.join("\n", line, getLogField().getValue()));
  }

  @Order(10)
  @ClassId("0a756ada-c387-4b75-9703-6a82cbe8c6a3")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
      BEANS.get(WidgetsHelper.class).injectReadOnlyMenu(menus);
    }

    @Override
    protected void execInitField() {
      setStatusVisible(false);
    }

    @Order(10)
    @ClassId("1194e7f6-3ba0-4e0f-975f-96e43c54319a")
    public class BrowserFieldBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("04b1775b-6cad-45fe-8f88-c7385dcecf7b")
      public class BrowserField extends AbstractBrowserField {

        @Override
        protected int getConfiguredGridH() {
          return 10;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return true;
        }

        @Override
        protected boolean getConfiguredSandboxEnabled() {
          return false;
        }

        @Override
        protected void execPostMessage(Object data, String origin) {
          logMessage("Received: " + data + " (origin: " + origin + ")");
        }

        @Override
        public void postMessage(Object message, String targetOrigin) {
          super.postMessage(message, targetOrigin);
          logMessage("Sent: " + message + " (targetOrigin: " + targetOrigin + ")");
        }
      }

      @Order(20)
      @ClassId("5eb5d910-85d2-4244-88c5-83bcbf04c796")
      public class SetUrlBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
        @ClassId("d36bbb39-93be-4145-969b-578f71984965")
        public class URLField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("URL");
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20)
        @ClassId("406f1863-24f2-4d4e-b07a-2834bea42332")
        public class SetUrlButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Set";
          }

          @Override
          protected void execClickAction() {
            try {
              getBrowserField().setLocation(getURLField().getValue());
            }
            catch (IllegalArgumentException e) {
              throw new VetoException(TEXTS.get("EnteredUrlInvalid"));
            }
          }
        }
      }

      @Order(30)
      @ClassId("f095737a-b591-41a2-a391-68d12e43f377")
      public class SetFileBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
        @ClassId("fed1f643-24c4-4bcd-950e-3e50ce98b232")
        public class BinaryResourceField extends AbstractFileChooserField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("File");
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }

          @Override
          protected void execInitField() {
            setEnabled(!CONFIG.getPropertyValue(ReadOnlyProperty.class));
          }
        }

        @Order(60)
        @ClassId("9df05795-7039-4ee4-acb1-834062c66775")
        public class SetFileButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Set";
          }

          @Override
          protected void execClickAction() {
            getBrowserField().setBinaryResource(getBinaryResourceField().getValue());
          }
        }
      }

      @Order(40)
      @ClassId("fc3817d4-0da0-4f5f-9ea2-ffc6808f1d43")
      public class LinksBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
        @ClassId("546fd072-99a0-40c9-b1a4-1aaa5c0c9827")
        public class ClearButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Blank";
          }

          @Override
          protected void execClickAction() {
            getBrowserField().setLocation(null);
          }
        }

        @Order(11)
        @ClassId("7310a659-470f-4a5e-8416-4fb04ab31dc3")
        public class ExampleNetButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "example.net";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getURLField().setValue("https://www.example.net");
            getSetUrlButton().doClick();
          }
        }

        @Order(15)
        @ClassId("cf2e3364-de78-41b1-af8d-df2394b7d5f7")
        public class BingButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "bing.com";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getURLField().setValue("https://www.bing.com/search?q=Eclipse%20Scout");
            getSetUrlButton().doClick();
          }
        }

        @Order(20)
        @ClassId("2a28c202-59f8-4ad3-a4e6-72e3fd126728")
        public class BsiSoftwareButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "bsi-software.com";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getURLField().setValue("https://www.bsi-software.com");
            getSetUrlButton().doClick();
          }
        }

        @Order(20)
        @ClassId("44220eea-3c74-41b7-b46d-a24717d25150")
        public class TestHtmlButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Scout.html";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.File;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            BinaryResource resource = IOUtility.readBinaryResource(ResourceBase.class.getResource("html/BrowserFieldCustomHtml.html"));
            getBinaryResourceField().setValue(resource);
            getSetFileButton().doClick();
          }
        }

        @Order(30)
        @ClassId("e0c39431-b56c-46af-b1d9-98e054e712d5")
        public class PostMessageDemoButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "PostMessageDemo.html";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.File;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            BinaryResource resource = IOUtility.readBinaryResource(ResourceBase.class.getResource("html/PostMessageDemo.html"));
            getBinaryResourceField().setValue(resource);
            getSetFileButton().doClick();
          }
        }
      }
    }

    @Order(20)
    @ClassId("555d9f5a-5a7c-4d2d-b471-4a89873e0944")
    public class TabBox extends AbstractTabBox {

      @Override
      protected int getConfiguredGridH() {
        return 10;
      }

      @Override
      protected double getConfiguredGridWeightY() {
        return 0;
      }

      @Override
      protected boolean getConfiguredGridUseUiHeight() {
        return false;
      }

      @Order(20)
      @ClassId("29122615-c832-42f0-996e-4e607999f9ca")
      public class ConfigurationBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Configure");
        }

        @Override
        protected TriState getConfiguredResponsive() {
          return TriState.FALSE;
        }

        @Order(10)
        @ClassId("7d386f57-447c-435e-acef-eb854fcd30af")
        public class ConfigurationLeftBox extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Order(30)
          @ClassId("49fe66bc-922d-4e83-9e3b-a6e5e0572c9f")
          public class ScrollableField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Scroll Bar Enabled";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getBrowserField().setScrollBarEnabled(isChecked());
            }

            @Override
            protected void execInitField() {
              setChecked(getBrowserField().isScrollBarEnabled());
            }
          }

          @Order(50)
          @ClassId("5967c688-f93c-4e92-8dd0-5bbbb203b344")
          public class ShowInExternalWindowField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Show in external window";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "If checked, the content will be displayed in a separate window.\n\n"
                  + "For this setting to have an effect, the page must be reloaded.";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getBrowserField().setShowInExternalWindow(isChecked());
            }

            @Override
            protected void execInitField() {
              setChecked(getBrowserField().isShowInExternalWindow());
            }
          }

          @Order(60)
          @ClassId("723924ac-c72e-4d9e-8426-f16b7bb29f85")
          public class AutoCloseExternalWindowField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Auto close external window";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getBrowserField().setAutoCloseExternalWindow(isChecked());
            }

            @Override
            protected void execInitField() {
              setChecked(getBrowserField().isAutoCloseExternalWindow());
            }
          }
        }

        @Order(20)
        @ClassId("e81172da-8077-4423-9a47-bd21b6d1a09a")
        public class ConfigurationRightBox extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Order(10)
          @ClassId("4125ea27-9698-499c-9fc1-4e204277935f")
          public class TrustedMessageOriginsFields extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "Trusted Message Origins";
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Comma-separated list of trusted origins for receiving posted messages. Leave empty to disable check of origin.";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected void execChangedValue() {
              List<String> tmo = Arrays.stream(StringUtility.split(getValue(), ","))
                  .map(StringUtility::trim)
                  .filter(StringUtility::hasText)
                  .collect(Collectors.toList());
              getBrowserField().setTrustedMessageOrigins(tmo);
            }

            @Override
            protected void execInitField() {
              setValue(CollectionUtility.format(getBrowserField().getTrustedMessageOrigins()));
            }
          }

          @Order(20)
          @ClassId("4be62e14-f463-4819-ac1f-0a5403e8b18d")
          public class SandboxPermissionsField extends AbstractLinkButton {

            private BrowserFieldSandboxForm m_sandboxForm;

            @Override
            protected String getConfiguredLabel() {
              return "Sandbox settings";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected void execClickAction() {
              if (m_sandboxForm == null) {
                m_sandboxForm = new BrowserFieldSandboxForm(getBrowserField());
                m_sandboxForm.addFormListener(e -> {
                  if (e.getType() == FormEvent.TYPE_CLOSED) {
                    m_sandboxForm = null;
                  }
                });
                m_sandboxForm.start();
              }
              else {
                m_sandboxForm.doClose();
              }
            }
          }
        }
      }

      @Order(30)
      @ClassId("94ff20f6-6d44-4a91-98ff-0e39adbfd950")
      public class MessagesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Messages";
        }

        @Order(5)
        @ClassId("2eea48fd-8d60-4e5a-93e6-fd75c6f8d013")
        public class MessageDescriptionField extends AbstractHtmlField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredGridUseUiHeight() {
            return true;
          }

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Override
          protected void execInitField() {
            setValue(HTML.fragment(
                "The browser field can communicate with an embedded page via the \"postMessage\" ",
                "method. Arbitrary messages can be sent to and received from the the <iframe> object. ",
                "To see it in action, load the ", HTML.italic(getPostMessageDemoButton().getLabel()), " page. ",
                HTML.br(),
                "More details can be found at ",
                HTML.link("https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage", "https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage"),
                ".").toHtml());
          }
        }

        @Order(10)
        @ClassId("249ba5dd-afb8-46e9-b279-d47aeee80780")
        public class LogField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Log";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 5 * 1024 * 1024; // 5 MB
          }

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }
        }

        @Order(20)
        @ClassId("d04d7b51-04ec-4a00-b441-dda4081d20c2")
        public class ClearLogButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Clear log";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getLogField().setValue(null);
          }
        }

        @Order(30)
        @ClassId("e46c6818-a8be-4a5a-8f05-136910717d52")
        public class MessageField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Message";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
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
          protected int getConfiguredGridH() {
            return 4;
          }
        }

        @Order(40)
        @ClassId("03546dbd-82cb-42f6-8809-5495719f477b")
        public class TargetOriginField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Target Origin";
          }

          @Override
          protected void execInitField() {
            setValue("/");
          }
        }

        @Order(50)
        @ClassId("9b570742-0545-45b5-a1d8-af8ed0356d0d")
        public class ButtonsGroup extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withHGap(5);
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Order(10)
          @ClassId("3219dc46-b141-4caf-a22f-ec75fb4d613d")
          public class SendAsTextButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Send as text";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected boolean getConfiguredFillHorizontal() {
              return true;
            }

            @Override
            protected void execClickAction() {
              getBrowserField().postMessage(
                  getMessageField().getValue(),
                  getTargetOriginField().getValue());
            }
          }

          @Order(20)
          @ClassId("e79417e6-03c3-4373-ab87-2c822b78fe73")
          public class SendAsJsonButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Send as JSON";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected boolean getConfiguredFillHorizontal() {
              return true;
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return MessageField.class;
            }

            @Override
            protected void execInitField() {
              checkValidDataObject();
            }

            @Override
            protected void execChangedMasterValue(Object newMasterValue) {
              checkValidDataObject();
            }

            @Override
            protected void execClickAction() {
              IDataObject dataObject = BEANS.get(IDataObjectMapper.class).readValue(getMessageField().getValue(), IDataObject.class);
              getBrowserField().postMessage(
                  dataObject,
                  getTargetOriginField().getValue());
            }

            protected void checkValidDataObject() {
              setEnabled(isValidDataObject(getMessageField().getValue()));
            }

            protected boolean isValidDataObject(String s) {
              if (s == null) {
                return false;
              }
              try {
                BEANS.get(IDataObjectMapper.class).readValueRaw(s);
              }
              catch (Exception e) { // NOSONAR
                return false;
              }
              return true;
            }
          }
        }
      }

      @Order(40)
      @ClassId("57d18d51-42a5-4f96-9b6e-0eccb16642b6")
      public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

        @Override
        protected void execInitField() {
          setField(getBrowserField());
        }
      }
    }

    @Order(60)
    @ClassId("f0910f85-3a9f-49e5-beed-23f61dbbf5eb")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
