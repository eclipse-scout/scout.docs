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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.resource.BinaryResource;
import org.eclipse.scout.rt.client.services.common.icon.IconLocator;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.textfield.AbstractTextField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipsescout.demo.widgets.client.ResourceBase;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox.BlankButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox.ScoutHtmlButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm.MainBox.HTMLField;

public class HtmlFieldForm extends AbstractForm implements IPageForm {

  public HtmlFieldForm() throws ProcessingException {
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
  protected void execInitForm() throws ProcessingException {
    loadFile("ScoutHtml.html", Collections.<BinaryResource> emptySet());
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public BlankButton getBlankButton() {
    return getFieldByClass(BlankButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public HTMLField getHTMLField() {
    return getFieldByClass(HTMLField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ScoutHtmlButton getScoutHtmlButton() {
    return getFieldByClass(ScoutHtmlButton.class);
  }

  private void loadFile(String simpleName, Collection<? extends BinaryResource> attachments) throws ProcessingException {
    try {
      String s = IOUtility.getContent(new InputStreamReader(ResourceBase.class.getResource("html/" + simpleName).openStream()));
      getHTMLField().setValue(null);
      getHTMLField().setScrollToAnchor(null);
      getHTMLField().setAttachments(attachments);
      getHTMLField().setValue(s);
    }
    catch (Exception e) {
      throw new ProcessingException("Html-Field can't load file ", e);
    }
  }

  private RemoteFile loadIcon(String iconName) throws ProcessingException {
    try {
      // determine file format
      int index = iconName.lastIndexOf(".");
      String format = iconName.substring(iconName.lastIndexOf("."));
      // determine icon name
      iconName = iconName.substring(0, iconName.lastIndexOf("."));
      // determine icon base name
      String baseIconName = iconName;
      index = iconName.lastIndexOf("_");
      if (index > 0) {
        baseIconName = iconName.substring(0, index);
      }

      // load icon
      IconSpec iconSpec = IconLocator.instance().getIconSpec(iconName);
      if (iconSpec == null && !iconName.equals(baseIconName)) {
        iconSpec = IconLocator.instance().getIconSpec(baseIconName);
      }

      if (iconSpec != null) {
        RemoteFile iconFile = new RemoteFile(iconName + format, 0);
        ByteArrayInputStream is = new ByteArrayInputStream(iconSpec.getContent());
        iconFile.readData(is);
        is.close();
        return iconFile;
      }
    }
    catch (Exception e) {
      throw new ProcessingException("failed to load image for " + iconName, e);
    }
    return null;
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(5.0)
    public class ActionGroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class EnabledCheckBox extends AbstractCheckBox {
        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(true);
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getHTMLField().setEnabled(getValue());
        }
      }

      @Order(20)
      public class ScrollToAnchorField extends AbstractTextField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("JumpToAnchor");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getHTMLField().setScrollToAnchor(getValue());
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
        protected void execClickAction() throws ProcessingException {
          getHTMLField().scrollToEnd();
        }
      }
    }

    @Order(10.0)
    public class HTMLField extends AbstractHtmlField {

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
      protected void execAppLinkAction(String ref) throws ProcessingException {
        MessageBoxes.createOk().header(TEXTS.get("LocalUrlClicked")).body(TEXTS.get("Parameters") + ":\n" + ref).show();
      }
    }

    @Order(20.0)
    public class GroupBox extends AbstractGroupBox {
      @Order(30.0)
      public class BlankButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Blank");
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          getHTMLField().setAttachments(null);
          getHTMLField().setValue(null);
        }
      }

      @Order(60.0)
      public class CustomHTMLButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CustomHtml");
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          URL url = ResourceBase.class.getResource("icons/eclipse_scout_logo.png");
          byte[] content;
          try {
            content = IOUtility.getContent(new BufferedInputStream(url.openStream()));
            BinaryResource file = new BinaryResource("eclipse_scout_logo.png", content);
            loadFile("HtmlFieldCustomHtml.html", Collections.<BinaryResource> singleton(file));
          }
          catch (IOException e) {
            throw new ProcessingException("", e);
          }
        }
      }

      @Order(40.0)
      public class ScoutHtmlButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScoutHtml");
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          loadFile("ScoutHtml.html", Collections.<BinaryResource> emptySet());
        }
      }

      @Order(70.0)
      public class ALotOfContentHtmlButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ALotOfContentHtmlWithAnchor");
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          loadFile("ALotOfContent.html", Collections.<BinaryResource> emptySet());
        }
      }
    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
