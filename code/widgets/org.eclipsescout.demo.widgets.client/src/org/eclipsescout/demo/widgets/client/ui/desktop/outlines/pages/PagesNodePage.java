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
package org.eclipsescout.demo.widgets.client.ui.desktop.outlines.pages;

import java.util.Collection;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.template.menu.AbstractExportToExcelMenu;

public class PagesNodePage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.TreeNode;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Pages");
  }

  @Override
  protected void execCreateChildPages(Collection<IPage> pageList) throws ProcessingException {
    PageWithTableTablePage pageWithTableTablePage = new PageWithTableTablePage();
    pageList.add(pageWithTableTablePage);

    PageWithNodesNodePage pageWithNodesNodePage = new PageWithNodesNodePage();
    pageList.add(pageWithNodesNodePage);
    PageWithADetailformTablePage pageWithADetailformPage = new PageWithADetailformTablePage();
    pageList.add(pageWithADetailformPage);
  }

  @Order(10.0)
  public class ExportToExcelMenu extends AbstractExportToExcelMenu {

    @Override
    protected IPage providePage() {
      return PagesNodePage.this;
    }
  }
}
