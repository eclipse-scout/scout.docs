package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

/**
 * @author aho
 */
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
