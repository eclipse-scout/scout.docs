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
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import java.net.URI;
import java.util.List;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.deeplink.FormPageDeepLinkHandler;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

public class FormPage extends AbstractPageWithNodes implements IFormPage {

  private final Class<? extends IPageForm> m_formType;
  private boolean m_enabled = true;

  public FormPage(Class<? extends IPageForm> formType) {
    super(false, formType.getName());
    m_formType = formType;
    callInitializer();
  }

  public FormPage(Class<? extends IPageForm> c, boolean enabled) {
    super(false, c.getName());
    m_formType = c;
    m_enabled = enabled;
    callInitializer();
  }

  @Override
  protected boolean getConfiguredEnabled() {
    return m_enabled;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execInitPage() {
    this.ensureText();
    setTableVisible(false);
  }

  protected void ensureText() {
    if (getCellForUpdate().getText() == null) {
      String s = m_formType.getSimpleName();
      s = s.replaceAll("Form$", "");
      String t = TEXTS.getWithFallback(s, null);
      if (t == null) {
        s = s.replaceAll("([a-z0-9])([A-Z])", "$1 $2");
      }
      getCellForUpdate().setText(s);
    }
  }

  @Override
  public String getText() {
    this.ensureText();
    return getCell().getText();
  }

  @Override
  protected void execPageActivated() {
    FormPageDeepLinkHandler deepLinkHandler = BEANS.get(FormPageDeepLinkHandler.class);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    desktop.setBrowserHistoryEntry(deepLinkHandler.createBrowserHistoryEntry(this));
  }

  @Override
  protected void ensureDetailFormCreated() {
    if (m_enabled) {
      super.ensureDetailFormCreated();
    }
  }

  @Override
  public IPageForm getDetailForm() {
    return (IPageForm) super.getDetailForm();
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return m_formType;
  }

  @Override
  public Class<? extends IPageForm> getFormType() {
    return m_formType;
  }

  @Override
  protected void execInitDetailForm() {
    if (getDetailForm() != null) {
      getDetailForm().getCloseButton().setVisibleGranted(false);
    }
  }

  @Override
  protected void startDetailForm() {
    getDetailForm().startPageForm();
  }

  @Order(1000)
  public class OpenInADialogMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("OpenInADialog");
    }

    @Override
    protected void execAction() {
      IPageForm form = (IPageForm) createDetailForm();
      form.setDisplayHint(IForm.DISPLAY_HINT_DIALOG);
      form.setAskIfNeedSave(false);
      form.startPageForm();
      form.waitFor();
    }
  }

  @Order(1500)
  public class OpenInNewSessionMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("OpenInNewSession");
    }

    @Override
    protected void execAction() {
      URI deepLink = BEANS.get(FormPageDeepLinkHandler.class)
          .createUriForPage(FormPage.this)
          .parameter("desktopStyle", IDesktop.DISPLAY_STYLE_BENCH)
          .parameter("forceNewClientSession", "true")
          .createURI();
      ClientSessionProvider.currentSession().getDesktop().openUri(deepLink.toString(), OpenUriAction.NEW_WINDOW);
    }
  }

  @Order(2000)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return m_formType;
    }
  }

  public static void sort(List<IPage<?>> pageList) {
    pageList.sort((o1, o2) -> {
      String s1 = "";
      String s2 = "";
      if (o1 != null) {
        s1 = ((IFormPage) o1).getText();
      }
      if (o2 != null) {
        s2 = ((IFormPage) o2).getText();
      }
      return s1.compareTo(s2);
    });
  }
}
