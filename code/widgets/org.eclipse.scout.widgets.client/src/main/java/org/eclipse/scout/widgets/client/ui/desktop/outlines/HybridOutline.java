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
import org.eclipse.scout.widgets.client.ui.desktop.pages.HybridJsPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.HybridPage;

@Order(1500)
@ClassId("7b25add7-9964-41b6-ad62-1e714661be14")
public class HybridOutline extends AbstractWidgetsOutline {

  @Override
  protected String getConfiguredTitle() {
    return "Hybrid";
  }

  @Override
  protected String getConfiguredIconId() {
    return null;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new HybridPage());
    pageList.add(new HybridJsPage());
  }
}
