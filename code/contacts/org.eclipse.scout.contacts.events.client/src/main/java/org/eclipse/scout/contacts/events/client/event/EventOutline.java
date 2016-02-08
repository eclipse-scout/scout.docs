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
package org.eclipse.scout.contacts.events.client.event;

import java.util.List;

import org.eclipse.scout.contacts.events.client.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

public class EventOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Events");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    EventTablePage eventsTablePage = new EventTablePage();
    pageList.add(eventsTablePage);
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Event;
  }
}
