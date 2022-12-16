/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.pages.HybridNodePage;

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
    pageList.add(new HybridNodePage());
  }
}
