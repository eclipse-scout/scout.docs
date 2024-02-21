/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithDetailFormTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithEditableTableTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithHierarchicalTableTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithNodesNodePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithSearchFormTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithTableRecTablePage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithTableTablePage;

@Order(1400)
@ClassId("da98a2be-ab0a-44d0-a696-c13c7c9f2242")
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
    pageList.add(new PageWithHierarchicalTableTablePage());
  }
}
