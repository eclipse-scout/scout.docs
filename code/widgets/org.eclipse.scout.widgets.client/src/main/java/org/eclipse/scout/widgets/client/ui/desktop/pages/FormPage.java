/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import java.net.URI;
import java.util.Collections;
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
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.widgets.client.deeplink.FormPageDeepLinkHandler;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

@ClassId("8ee42f38-cb0b-455f-939a-860223eb6fca")
public class FormPage extends AbstractPageWithNodes implements IFormPage {

  private final Class<? extends IPageForm> m_formType;
  private final String m_text;

  public FormPage(Class<? extends IPageForm> formType) {
    this(formType, null);
  }

  public FormPage(Class<? extends IPageForm> formType, String text) {
    super(false, formType.getName());
    m_formType = formType;
    if (text != null) {
      m_text = text;
    }
    else {
      m_text = calculateDefaultText(formType);
    }
    callInitializer();
  }

  @Override
  public Class<? extends IPageForm> getFormType() {
    return m_formType;
  }

  public String getText() {
    return m_text;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return m_formType;
  }

  @Override
  protected void execInitTreeNode() {
    if (getCellForUpdate().getText() == null) {
      getCellForUpdate().setText(m_text);
    }
  }

  @Override
  protected void execInitPage() {
    setTableVisible(false);
  }

  @Override
  protected void execPageActivated() {
    FormPageDeepLinkHandler deepLinkHandler = BEANS.get(FormPageDeepLinkHandler.class);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    desktop.setBrowserHistoryEntry(deepLinkHandler.createBrowserHistoryEntry(this));
  }

  @Override
  protected void execInitDetailForm() {
    if (getDetailForm() != null) {
      if (getDetailForm().getCloseButton() != null) {
        getDetailForm().getCloseButton().setVisibleGranted(false);
      }
      else {
        getMenuByClass(OpenInADialogMenu.class).setVisibleGranted(false);
      }
    }
  }

  @Override
  public IPageForm getDetailForm() {
    return (IPageForm) super.getDetailForm();
  }

  @Override
  protected void startDetailForm() {
    getDetailForm().startPageForm();
  }

  protected String calculateDefaultText(Class<?> clazz) {
    return m_formType.getSimpleName()
        .replaceAll("Form$", "")
        .replaceAll("([a-z0-9])([A-Z])", "$1 $2")
        .replaceAll("([^0-9])([0-9])", "$1 $2");
  }

  @Order(1000)
  @ClassId("0967e30a-5bd1-4598-8493-275422f0194e")
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
  @ClassId("bfcf8562-8483-48e0-8761-c1ed1180a505")
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
  @ClassId("3e106c9d-ae1c-475b-b310-35c6f584b09b")
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return m_formType;
    }
  }

  public static void sort(List<IPage<?>> pageList) {
    Collections.sort(pageList, (o1, o2) -> {
      String s1 = null;
      String s2 = null;
      if (o1 != null) {
        s1 = ObjectUtility.nvl(o1.getCell().getText(), o1.getClass().getSimpleName());
      }
      if (o2 != null) {
        s2 = ObjectUtility.nvl(o2.getCell().getText(), o2.getClass().getSimpleName());
      }
      return ObjectUtility.compareTo(s1, s2);
    });
  }
}
