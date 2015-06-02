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

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.UriBuilder;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.Activator;
import org.eclipsescout.demo.widgets.client.ui.forms.HTMLFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HTMLFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.HTMLFieldForm.MainBox.GroupBox.BlankButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HTMLFieldForm.MainBox.GroupBox.ScoutHtmlButton;
import org.eclipsescout.demo.widgets.client.ui.forms.HTMLFieldForm.MainBox.HTMLField;
import org.osgi.framework.Bundle;

public class HTMLFieldForm extends AbstractForm implements IPageForm {

  public HTMLFieldForm() throws ProcessingException {
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
    loadFile("ScoutHtml.html");
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

  private void loadFile(String simpleName, RemoteFile... attachments) throws ProcessingException {
    try {
      String s = IOUtility.getContent(new InputStreamReader(Activator.getDefault().getBundle().getResource("resources/html/" + simpleName).openStream()));
      getHTMLField().setValue(null);
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
      IClientSession clientSession = ClientSyncJob.getCurrentSession();
      IconSpec iconSpec = clientSession.getIconLocator().getIconSpec(iconName);
      if (iconSpec == null && !iconName.equals(baseIconName)) {
        iconSpec = clientSession.getIconLocator().getIconSpec(baseIconName);
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
      protected void execHyperlinkAction(URL url, String path, boolean local) throws ProcessingException {
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

      @Order(40.0)
      public class ScoutHtmlButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScoutHtml");
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          loadFile("ScoutHtml.html");
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
          List<RemoteFile> attachments = new ArrayList<RemoteFile>();
          Bundle clientBundle = Activator.getDefault().getBundle();
          RemoteFile iconFile = new RemoteFile(clientBundle.getResource("/resources/icons/scout_logo.jpg"), true);
          attachments.add(iconFile);
          loadFile("HtmlFieldCustomHtml.html", attachments.toArray(new RemoteFile[attachments.size()]));
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
