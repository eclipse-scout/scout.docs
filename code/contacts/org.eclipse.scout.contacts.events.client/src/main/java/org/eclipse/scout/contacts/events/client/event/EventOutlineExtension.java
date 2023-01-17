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

import org.eclipse.scout.contacts.client.contact.ContactOutline;
import org.eclipse.scout.rt.client.extension.ui.basic.tree.TreeChains.TreeAutoCheckChildNodesChain;
import org.eclipse.scout.rt.client.extension.ui.basic.tree.TreeChains.TreeNodesCheckedChain;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.AbstractOutlineExtension;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.OutlineChains.OutlineCreateChildPagesChain;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.OutlineChains.OutlineCreateRootPageChain;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

public class EventOutlineExtension extends AbstractOutlineExtension<ContactOutline> {

  public EventOutlineExtension(ContactOutline owner) {
    super(owner);
  }

  @Override
  public IPage<?> execCreateRootPage(OutlineCreateRootPageChain chain) {
    return chain.execCreateRootPage();
  }

  @Override
  public void execNodesChecked(TreeNodesCheckedChain chain, List<ITreeNode> nodes) {
  }

  @Override
  public void execAutoCheckChildNodes(TreeAutoCheckChildNodesChain chain, List<ITreeNode> nodes, boolean checked, boolean enabledNodesOnly) {
  }

  @Override
  public void execCreateChildPages(OutlineCreateChildPagesChain chain, List<IPage<?>> pageList) {
    super.execCreateChildPages(chain, pageList);
    pageList.add(new EventTablePage());
  }

}
