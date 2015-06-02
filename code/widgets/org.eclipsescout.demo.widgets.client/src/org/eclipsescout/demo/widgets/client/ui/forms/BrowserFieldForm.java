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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.AbstractBrowserField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipsescout.demo.widgets.client.Activator;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.BrowserField;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.BsiagButton;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.EclipseScoutButton;

public class BrowserFieldForm extends AbstractForm implements IPageForm {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(BrowserFieldForm.class);

  public BrowserFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BrowserField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public BrowserField getBrowserField() {
    return getFieldByClass(BrowserField.class);
  }

  public BsiagButton getBsiagButton() {
    return getFieldByClass(BsiagButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public EclipseScoutButton getEclipseScoutButton() {
    return getFieldByClass(EclipseScoutButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  private void loadFile(String simpleName) throws ProcessingException {
    try {
      RemoteFile remoteFile = new RemoteFile("simpleName", 0);
      remoteFile.readData(new InputStreamReader(Activator.getDefault().getBundle().getResource("resources/html/" + simpleName).openStream()));
      getBrowserField().setValue(remoteFile);
    }
    catch (Exception e) {
      throw new ProcessingException("Html-Field can't load file ", e);
    }
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class BrowserField extends AbstractBrowserField {

      @Override
      protected boolean getConfiguredAutoDisplayText() {
        return false;
      }

      @Override
      protected int getConfiguredGridH() {
        return 20;
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
      protected void execLocationChanged(String location, String path, boolean local) throws ProcessingException {
        LOG.info("Location changed to " + location);
      }
    }

    @Order(20.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(30.0)
    public class EclipseScoutButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Www.eclipse.orgscout");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getBrowserField().setValue(null);
        getBrowserField().setLocation("http://www.eclipse.org/scout");
      }
    }

    @Order(40.0)
    public class BsiagButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Www.bsiag.com");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getBrowserField().setValue(null);
        getBrowserField().setLocation("http://www.bsiag.com");
      }
    }

    @Order(50.0)
    public class CustomHtmlButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CustomHtml");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        loadFile("BrowserFieldCustomHtml.html");
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
