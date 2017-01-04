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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationGroupBox.EnabledCheckBox;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationGroupBox.SetContentButtonsBox;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationGroupBox.SetContentButtonsBox.BlankButton;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationGroupBox.SetContentButtonsBox.ScoutHtmlButton;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox.HtmlField;
import org.eclipse.scout.widgets.shared.Icons;

@Order(6000.0)
public class HtmlFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public HtmlFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("HTMLField");
  }

  @Override
  protected void execInitForm() {
    // Load "Scout HTML" page by default
    getScoutHtmlButton().doClick();
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public BlankButton getBlankButton() {
    return getFieldByClass(BlankButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public SetContentButtonsBox getSetContentButtonsBox() {
    return getFieldByClass(SetContentButtonsBox.class);
  }

  public HtmlField getHtmlField() {
    return getFieldByClass(HtmlField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ScoutHtmlButton getScoutHtmlButton() {
    return getFieldByClass(ScoutHtmlButton.class);
  }

  public ConfigurationGroupBox getConfigurationGroupBox() {
    return getFieldByClass(ConfigurationGroupBox.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public EnabledCheckBox getEnabledCheckBox() {
    return getFieldByClass(EnabledCheckBox.class);
  }

  protected void loadFile(String simpleName) {
    loadFile(simpleName, null);
  }

  protected void loadFile(String simpleName, List<BinaryResource> attachments) {
    try (InputStream in = ResourceBase.class.getResource("html/" + simpleName).openStream()) {
      String s = IOUtility.readString(in, null);
      getHtmlField().setValue(null);
      getHtmlField().setScrollToAnchor(null);
      getHtmlField().setAttachments(attachments);
      getHtmlField().setValue(s);
    }
    catch (IOException e) {
      throw new ProcessingException("Html-Field can't load file ", e);
    }
  }

  protected BinaryResource loadResource(String resourceName, String targetName) {
    URL url = ResourceBase.class.getResource(resourceName);
    byte[] content;
    try (InputStream in = url.openStream()) {
      content = IOUtility.readBytes(in);
      return new BinaryResource(targetName, content);
    }
    catch (IOException e) {
      throw new ProcessingException("Error while loading resource: " + resourceName, e);
    }
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void execInitField() {
      setStatusVisible(false);
    }

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class HtmlField extends AbstractHtmlField {

        @Override
        protected int getConfiguredGridH() {
          return 12;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
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
        protected void execAppLinkAction(String ref) {
          MessageBoxes.createOk().withHeader(TEXTS.get("LocalUrlClicked")).withBody(TEXTS.get("Parameters") + ":\n" + ref).show();
        }
      }
    }

    @Order(20)
    public class ConfigurationGroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      public class EnabledCheckBox extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return true;
        }

        @Override
        protected void execInitField() {
          setValue(true);
        }

        @Override
        protected void execChangedValue() {
          getHtmlField().setEnabled(getValue());
        }
      }

      @Order(20)
      public class ScrollToAnchorField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("JumpToAnchor");
        }

        @Override
        protected void execChangedValue() {
          getHtmlField().setScrollToAnchor(getValue());
        }
      }

      @Order(30)
      public class ScrollToEndButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollToEnd");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          getHtmlField().scrollToEnd();
        }
      }

      @Order(40)
      public class SetContentButtonsBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return "Set content to";
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Order(10)
        public class BlankButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Blank");
          }

          @Override
          protected void execClickAction() {
            getHtmlField().setAttachments(null);
            getHtmlField().setValue(null);
          }
        }

        @Order(20)
        public class CustomHTMLButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CustomHtml");
          }

          @Override
          protected void execClickAction() {
            BinaryResource file = loadResource("icons/eclipse_scout_logo.png", "eclipse_scout_logo.png");
            loadFile("HtmlFieldCustomHtml.html", Collections.singletonList(file));
          }
        }

        @Order(40)
        public class ScoutHtmlButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ScoutHtml");
          }

          @Override
          protected void execClickAction() {
            List<BinaryResource> files = new ArrayList<>();
            files.add(loadResource("images/download.png", "download.png"));
            files.add(loadResource("images/s.png", "s.png"));
            files.add(loadResource("images/bird_1008.jpg", "bird.jpg"));
            loadFile("ScoutHtml.html", files);
          }
        }

        @Order(70)
        public class ALotOfContentHtmlButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ALotOfContentHtmlWithAnchor");
          }

          @Override
          protected void execClickAction() {
            loadFile("ALotOfContent.html");
          }
        }

        @Order(80)
        public class HtmlBuilderButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Programmatic value (with icon)";
          }

          @Override
          protected void execClickAction() {
            getHtmlField().setAttachments(null);
            String randomColor = "#"
                + StringUtility.lpad(Integer.toHexString(NumberUtility.randomInt(256)), "0", 2)
                + StringUtility.lpad(Integer.toHexString(NumberUtility.randomInt(256)), "0", 2)
                + StringUtility.lpad(Integer.toHexString(NumberUtility.randomInt(256)), "0", 2);
            getHtmlField().setValue(HTML.fragment(
                HTML.icon(Icons.Clock)
                    .style("font-size: 50px; padding-right: 10px; vertical-align: middle; color: " + randomColor),
                HTML.span(
                    "The current server time is: ",
                    HTML.bold(DateUtility.formatTime(new Date())))
                    .style("vertical-align: middle;"))
                .toHtml());
          }
        }
      }
    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
