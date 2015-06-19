/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * @author mzi
 */
public class EventManagementOutline extends AbstractExtensibleOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("EventManagement");
  }

  @Override
  protected void execCreateChildPages(List<IPage> pageList) throws ProcessingException {
    EventsTablePage eventsTablePage = new EventsTablePage();
    pageList.add(eventsTablePage);
  }
}
