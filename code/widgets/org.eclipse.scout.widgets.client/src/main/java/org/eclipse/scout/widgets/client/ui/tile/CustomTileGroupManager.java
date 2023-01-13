/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.tile;

import org.eclipse.scout.rt.client.ui.tile.AbstractTileAccordionGroupManager;
import org.eclipse.scout.rt.client.ui.tile.GroupTemplate;

public class CustomTileGroupManager extends AbstractTileAccordionGroupManager<ICustomTile> {

  public static final Object ID = CustomTileGroupManager.class;

  private String m_iconId;

  @Override
  public Object getGroupIdByTile(ICustomTile tile) {
    return tile.getGroup();
  }

  public String getIconId() {
    return m_iconId;
  }

  public void setIconId(String iconId) {
    m_iconId = iconId;
  }

  @Override
  public GroupTemplate createGroupForTile(ICustomTile tile) {
    return new GroupTemplate(tile.getGroup(), tile.getGroup()).withIconId(getIconId());
  }

  @Override
  public Object getId() {
    return ID;
  }

}
