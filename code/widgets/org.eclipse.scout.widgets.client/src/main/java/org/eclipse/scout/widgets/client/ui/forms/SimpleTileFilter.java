package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.client.ui.tile.ITileFilter;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.widgets.client.ui.tiles.AbstractSimpleTile;

public class SimpleTileFilter implements ITileFilter {
  private String m_text;

  public SimpleTileFilter() {
    setText("");
  }

  public void setText(String text) {
    text = ObjectUtility.nvl(text, "");
    m_text = text.toLowerCase();
  }

  @Override
  public boolean accept(ITile tile) {
    if (!(tile instanceof AbstractSimpleTile)) {
      return true;
    }
    AbstractSimpleTile simpleTile = (AbstractSimpleTile) tile;
    String label = ObjectUtility.nvl(simpleTile.getLabel(), "");
    String filterText = label.trim().toLowerCase();
    return filterText.indexOf(m_text) > -1;
  }

}
