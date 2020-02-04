/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipsescout.demo.bahbah.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.AdministrationOutline;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.ChatOutline;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages.UserNodePage;

@ClassId("d297c6ef-e1ab-4242-9402-4922e17cd3cd")
public class Desktop extends AbstractDesktop {

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<>();
    outlines.add(ChatOutline.class);
    outlines.add(AdministrationOutline.class);
    return outlines;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BahBah");
  }

  @Override
  protected void execOpened() {
    IOutline firstOutline = CollectionUtility.firstElement(getAvailableOutlines());
    if (firstOutline != null) {
      activateOutline(firstOutline);
    }
  }

  public static Desktop get() {
    return (Desktop) ClientSessionProvider.currentSession().getDesktop();
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
    IPage<?> invisibleRootPage = getChatOutline().getRootPage();
    if (invisibleRootPage != null && invisibleRootPage.getChildNodeCount() > 0) {
      IPage<?> p = invisibleRootPage.getChildPage(0);
      if (p instanceof UserNodePage) {
        return (UserNodePage) p;
      }
    }
    return null;
  }

  @Order(10)
  @ClassId("380198da-cd9a-4e6e-8c18-7d295d6fbff7")
  public class AboutMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("AboutMenu");
    }

    @Override
    protected void execAction() {
      ScoutInfoForm form = new ScoutInfoForm();
      form.startModify();
    }
  }

  @Order(20)
  @ClassId("2d966502-7390-462a-a6c3-fad752f89415")
  public class LogoutMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Logout");
    }

    @Override
    protected void execInitAction() {
      setVisible(UserAgentUtility.isDesktopDevice());
    }

    @Override
    protected void execAction() {
      ClientSession.get().stop();
    }
  }

  @Order(10)
  @ClassId("d22e3a65-ca73-443a-bb90-68a680eda9e2")
  public class ChatOutlineViewButton extends AbstractOutlineViewButton {
    public ChatOutlineViewButton() {
      super(Desktop.this, ChatOutline.class);
    }

  }

  @Order(20)
  @ClassId("9180b2f7-dfba-407e-9787-7e6ec33e6260")
  public class AdministrationOutlineViewButton extends AbstractOutlineViewButton {
    public AdministrationOutlineViewButton() {
      super(Desktop.this, AdministrationOutline.class);
    }

  }
}
