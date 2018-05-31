package org.eclipse.scout.widgets.client.ui.tile;

import org.eclipse.scout.rt.client.ui.tile.ITileFilter;
import org.eclipse.scout.rt.platform.util.ObjectUtility;

public class CustomTileFilter implements ITileFilter<ICustomTile> {
  private String m_text;

  public CustomTileFilter() {
    setText("");
  }

  public void setText(String text) {
    text = ObjectUtility.nvl(text, "");
    m_text = text.toLowerCase();
  }

  @Override
  public boolean accept(ICustomTile tile) {
    String label = ObjectUtility.nvl(tile.getLabel(), "");
    String filterText = label.trim().toLowerCase();
    return filterText.indexOf(m_text) > -1;
  }

}
