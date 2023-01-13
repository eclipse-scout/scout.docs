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

import org.eclipse.scout.contacts.client.organization.OrganizationNodePage;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.AbstractPageWithNodesExtension;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.PageWithNodesChains.PageWithNodesCreateChildPagesChain;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

public class EventPageExtension extends AbstractPageWithNodesExtension<OrganizationNodePage> {

  public EventPageExtension(OrganizationNodePage owner) {
    super(owner);
  }

  @Override
  public void execCreateChildPages(PageWithNodesCreateChildPagesChain chain, List<IPage<?>> pageList) {
    super.execCreateChildPages(chain, pageList);
    pageList.add(new EventTablePage());
  }
}
