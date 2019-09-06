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
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithDetailFormTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithEditableTableTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithNodesNodePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithSearchFormTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithTableRecTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithTableTablePage;

@Order(1400)
public class PagesOutline extends AbstractWidgetsOutline {

  @Override
  protected String getConfiguredTitle() {
    return "Pages";
  }

  @Override
  protected String getConfiguredIconId() {
    return null;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new PageWithTableTablePage());
    pageList.add(new PageWithEditableTableTablePage());
    pageList.add(new PageWithTableRecTablePage());
    pageList.add(new PageWithSearchFormTablePage());
    pageList.add(new PageWithNodesNodePage());
    pageList.add(new PageWithDetailFormTablePage());
  }
}
