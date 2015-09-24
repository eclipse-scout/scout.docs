package org.eclipse.scout.contacts.client.outlines;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractSearchOutline;

@Order(2000)
public class SearchOutline extends AbstractSearchOutline {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(SearchOutline.class);

  @Override
  protected void execSearch(final String query) throws ProcessingException {
    LOG.info("Search started");
  }
}
