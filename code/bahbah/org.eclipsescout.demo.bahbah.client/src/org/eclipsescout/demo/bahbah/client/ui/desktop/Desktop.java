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
package org.eclipsescout.demo.bahbah.client.ui.desktop;

import java.util.ArrayList;

import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.AdministrationOutline;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.ChatOutline;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages.UserNodePage;
import org.eclipsescout.demo.bahbah.shared.Icons;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTableForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTreeForm;
import org.eclipse.scout.rt.extension.client.ui.desktop.AbstractExtensibleDesktop;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

public class Desktop extends AbstractExtensibleDesktop implements IDesktop {

  public Desktop() {
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Class<? extends IOutline>[] getConfiguredOutlines() {
    ArrayList<Class> outlines = new ArrayList<Class>();
    outlines.add(ChatOutline.class);
    outlines.add(AdministrationOutline.class);
    return outlines.toArray(new Class[outlines.size()]);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BahBah");
  }

  @Override
  protected void execOpened() throws ProcessingException {
    // outline tree
    DefaultOutlineTreeForm treeForm = new DefaultOutlineTreeForm();
    treeForm.setIconId(Icons.EclipseScout);
    treeForm.startView();

    //outline table
    DefaultOutlineTableForm tableForm = new DefaultOutlineTableForm();
    tableForm.setIconId(Icons.EclipseScout);
    tableForm.startView();

    if (getAvailableOutlines().length > 0) {
      setOutline(getAvailableOutlines()[0]);
    }

  }

  public static Desktop get() {
    return (Desktop) ClientSyncJob.getCurrentSession().getDesktop();
  }

  private IOutline getChatOutline() {
    for (IOutline o : getAvailableOutlines()) {
      if (o.getClass().equals(ChatOutline.class)) {
        return o;
      }
    }
    return null;
  }

  public UserNodePage getUserNodePage() {
    IPage invisibleRootPage = getChatOutline().getRootPage();
    if (invisibleRootPage != null && invisibleRootPage.getChildNodeCount() > 0) {
      IPage p = invisibleRootPage.getChildPage(0);
      if (p instanceof UserNodePage) {
        return (UserNodePage) p;
      }
    }
    return null;
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
  public class LogoutMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Logout");
    }

    @Override
    protected void execAction() throws ProcessingException {
      ClientSession.get().stopSession();
    }

    @Override
    protected void execPrepareAction() throws ProcessingException {
      setVisible(UserAgentUtility.isDesktopDevice());
    }
  }

  @Order(10.0)
  public class ChatOutlineViewButton extends AbstractOutlineViewButton {

    public ChatOutlineViewButton() {
      super(Desktop.this, ChatOutline.class);
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Chat");
    }
  }

  @Order(20.0)
  public class AdministrationOutlineViewButton extends AbstractOutlineViewButton {

    public AdministrationOutlineViewButton() {
      super(Desktop.this, AdministrationOutline.class);
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Administration");
    }
  }
}
