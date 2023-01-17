/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.events.client.event;

import java.util.List;

import org.eclipse.scout.contacts.events.client.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("956b0f47-b86d-479d-86be-0f2981ecc00c")
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
