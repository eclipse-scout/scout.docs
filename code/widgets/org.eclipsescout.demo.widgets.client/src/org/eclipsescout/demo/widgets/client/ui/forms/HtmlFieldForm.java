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

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.UriBuilder;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationBox.HtmlSourceField;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.ConfigurationBox.UserHtmlField;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.SampleContentButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedCenterField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedRightField;
import org.eclipsescout.demo.widgets.client.util.ResourceUtility;

public class HtmlFieldForm extends AbstractForm implements IPageForm {

  public static final String HTML_DEFAULT = "HtmlFieldCustomHtml.html";
  public static final String HTML_DISABLED = "HelloWorld.html";
  public static final String HTML_USER = "ScoutHtml.html";

  public static final String IMAGE_LOGO = "eclipse_scout_logo.png";
  public static final String IMAGE_TEXT = "eclipse_scout_text.png";
  public static final String IMAGE_BIRD = "bird.jpg";

  public HtmlFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("HtmlField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  /**
   * @return the DefaultField
   */
  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the SampleContentButton
   */
  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  public HtmlSourceField getHtmlSourceField() {
    return getFieldByClass(HtmlSourceField.class);
  }

  public UserHtmlField getUserHtmlField() {
    return getFieldByClass(UserHtmlField.class);
  }

  /**
   * @return the AlignedCenterField
   */
  public AlignedCenterField getAlignedCenterField() {
    return getFieldByClass(AlignedCenterField.class);
  }

  /**
   * @return the AlignedRightField
   */
  public AlignedRightField getAlignedRightField() {
    return getFieldByClass(AlignedRightField.class);
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
      public class DefaultField extends AbstractHtmlField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          loadUserContent(this, htmlResourceToString(HTML_DEFAULT));
        }

        @Override
        protected void execHyperlinkAction(URL url, String path, boolean local) throws ProcessingException {
          processUserHyperlinkAction(url, path, local);
        }
      }

      @Order(20.0)
      public class DisabledField extends AbstractHtmlField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(htmlResourceToString(HTML_DISABLED));
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
      public class UserHtmlField extends AbstractHtmlField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UserHTML");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return HtmlFieldForm.MainBox.ConfigurationBox.HtmlSourceField.class;
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return true;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          loadUserContent(this, (String) newMasterValue);
        }

        @Override
        protected void execHyperlinkAction(URL url, String path, boolean local) throws ProcessingException {
          processUserHyperlinkAction(url, path, local);
        }
      }

      @Order(20.0)
      public class HtmlSourceField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HTMLSource");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 200000;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }
      }
    }

    @Order(30.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getHtmlSourceField().setValue(htmlResourceToString(HTML_USER));
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  private String htmlResourceToString(String fileName) throws ProcessingException {
    try {
      URL url = ResourceUtility.getHtmlResource(fileName);
      InputStreamReader isr = new InputStreamReader(url.openStream());
      String html = IOUtility.getContent(isr);
      return html;
    }
    catch (Exception e) {
      throw new ProcessingException("Can't load file '" + fileName + "'", e);
    }
  }

  private void loadUserContent(AbstractHtmlField field, String html) throws ProcessingException {
    List<RemoteFile> attachments = new ArrayList<RemoteFile>();
    attachments.add(new RemoteFile(ResourceUtility.getImageResource(IMAGE_LOGO), true));
    attachments.add(new RemoteFile(ResourceUtility.getImageResource(IMAGE_TEXT), true));
    attachments.add(new RemoteFile(ResourceUtility.getImageResource(IMAGE_BIRD), true));

    field.setValue(null);
    field.setAttachments(attachments);
    field.setValue(html);
  }

  private void processUserHyperlinkAction(URL url, String path, boolean local) throws ProcessingException {
    if (local) {
      Map<String, String> parameters = new UriBuilder(url).getParameters();
      String paramStr = "";

      if (parameters != null) {
        paramStr = parameters.toString();
      }

      MessageBox.showOkMessage(null, TEXTS.get("LocalUrlClicked"), TEXTS.get("Parameters") + ":\n" + paramStr);
    }
    else {
      SERVICES.getService(IShellService.class).shellOpen(url.toExternalForm());
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
