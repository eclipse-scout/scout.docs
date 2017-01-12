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
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithDetailFormTablePage;
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithEditableTableTablePage;
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithNodesNodePage;
import org.eclipse.scout.widgets.old.client.ui.desktop.pages.PageWithTableRecTablePage;
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
    pageList.add(new PageWithTableTablePage());
    pageList.add(new PageWithEditableTableTablePage());
    pageList.add(new PageWithTableRecTablePage());
    pageList.add(new PageWithNodesNodePage());
    pageList.add(new PageWithDetailFormTablePage());
  }
}
