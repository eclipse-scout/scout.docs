/*
 * Copyright (c) BSI Business Systems Integration AG. All rights reserved.
 * http://www.bsiag.com/
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
