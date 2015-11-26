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
package org.eclipsescout.demo.widgets.client.old.ui.desktop.pages;

import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.SearchFormsNodePage;
import org.eclipsescout.demo.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;

public class PageWithNodesNodePage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithNodes");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    IPage<?> pageWithTableTablePage = new PageWithTableTablePage(TEXTS.get("PageWithTable") + " 1");
    pageList.add(pageWithTableTablePage);

    IPage<?> pageWithTableTablePage0 = new SearchFormsNodePage();
    pageList.add(pageWithTableTablePage0);

    IPage<?> pageWithTableTablePage1 = new PageWithADetailformTablePage();
    pageList.add(pageWithTableTablePage1);
  }

  @Order(10)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithNodesNodePage.class;
    }
  }
}
