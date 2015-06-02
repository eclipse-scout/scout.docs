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
package org.eclipsescout.demo.minifigcreator.client.ui.desktop;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.minifigcreator.client.ClientSession;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm;
import org.eclipsescout.demo.minifigcreator.shared.Icons;

public class Desktop extends AbstractDesktop implements IDesktop {

  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);
  private DesktopForm m_desktopForm;

  public Desktop() {
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected void execOpened() throws ProcessingException {
    // desktop form
    m_desktopForm = new DesktopForm();
    m_desktopForm.setIconId(Icons.EclipseScout);
    m_desktopForm.startView();
  }

  @Order(10.0)
  public class AboutMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("AboutMenu");
    }

    @Override
    public void execAction() throws ProcessingException {
      ScoutInfoForm form = new ScoutInfoForm();
      form.startModify();
    }
  }

  @Order(20.0)
  public class ServerMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("UpdateServer");
    }

    @Override
    public void execAction() throws ProcessingException {
      ServerForm form = new ServerForm();
      form.startModify();
      form.waitFor();
      if (form.isFormStored()) {
        m_desktopForm.reloadForm();
      }
    }
  }

  @Order(100.0)
  public class ExitMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ExitMenu");
    }

    @Override
    public void execAction() throws ProcessingException {
      ClientSyncJob.getCurrentSession(ClientSession.class).stopSession();
    }
  }

  @Order(10.0)
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "f5";
    }

    @Override
    protected void execAction() throws ProcessingException {
      if (getOutline() != null) {
        IPage page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }
}
