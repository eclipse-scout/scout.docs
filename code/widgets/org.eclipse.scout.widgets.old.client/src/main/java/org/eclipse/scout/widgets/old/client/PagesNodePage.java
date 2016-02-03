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
package org.eclipse.scout.widgets.old.client;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithADetailformTablePage;
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithNodesNodePage;
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithTableTablePage;

/**
 * @author mzi
 */
public class PagesNodePage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Pages");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    PageWithTableTablePage pageWithTableTablePage = new PageWithTableTablePage();
    pageList.add(pageWithTableTablePage);

    PageWithNodesNodePage pageWithNodesNodePage = new PageWithNodesNodePage();
    pageList.add(pageWithNodesNodePage);

    PageWithADetailformTablePage pageWithADetailformPage = new PageWithADetailformTablePage();
    pageList.add(pageWithADetailformPage);
  }
}
