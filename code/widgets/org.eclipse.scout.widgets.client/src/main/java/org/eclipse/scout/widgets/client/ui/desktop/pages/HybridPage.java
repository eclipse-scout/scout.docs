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

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridJsForm;

@ClassId("c7320996-3854-46ef-8177-9f179f48cb0a")
public class HybridPage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return HTML.span(
        HTML.span("Hybrid Page").addCssClass("text"),
        HTML.span("Java").addCssClass("type")).addCssClass("hybrid-type-indicator")
        .toHtml();
  }

  @Override
  protected void execInitPage() {
    getCellForUpdate().setHtmlEnabled(true);
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return HybridJsForm.class;
  }

  @Order(10)
  @ClassId("747f8fc2-ea3f-4c7a-8836-7a2246a213f7")
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return HybridPage.class;
    }
  }
}
