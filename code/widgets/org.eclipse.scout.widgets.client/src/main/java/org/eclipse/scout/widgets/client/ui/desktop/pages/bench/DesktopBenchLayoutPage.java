/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;

/**
 * @author aho
 */
@ClassId("21291689-4b13-42a0-bfaa-79a2429e2b21")
public class DesktopBenchLayoutPage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Bench Layout";
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new FixedFooterNodePage());
    pageList.add(new FixedCenterColumnNodePage());
    pageList.add(new FixedTopNodePage());
    pageList.add(new ConfigurableColumnBenchLayoutNodePage());
    pageList.add(new ConfigurableRowBenchLayoutNodePage());
    pageList.add(new NullLayoutDataNodePage());
    pageList.add(new CachedBenchLayoutNodePage());
  }
}
