/*
 * Copyright (c) BSI Business Systems Integration AG. All rights reserved.
 * http://www.bsiag.com/
 */
package org.eclipse.scout.widgets.client.ui.tiles;

import org.eclipse.scout.rt.client.ui.tile.AbstractTilesAccordionGroupManager;
import org.eclipse.scout.rt.client.ui.tile.GroupTemplate;

public class SimpleTileGroupManager extends AbstractTilesAccordionGroupManager<ISimpleTile> {

  public static final Object ID = SimpleTileGroupManager.class;
  public static final String GROUP_ID_MARKED = "marked";
  public static final String GROUP_ID_NOT_MARKED = "notMarked";

  @Override
  public Object getGroupIdByTile(ISimpleTile tile) {
    return tile.getGroup();
  }

  @Override
  public GroupTemplate createGroupForTile(ISimpleTile tile) {
    return new GroupTemplate(tile.getGroup(), tile.getGroup());
  }

  @Override
  public Object getId() {
    return ID;
  }

}
