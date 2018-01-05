package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.tile.ITileFilter;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.widgets.client.ui.tile.AbstractSimpleTile;

public class SimpleTileFilter implements ITileFilter<AbstractSimpleTile> {
  private String m_text;

  public SimpleTileFilter() {
    setText("");
  }

  public void setText(String text) {
    text = ObjectUtility.nvl(text, "");
    m_text = text.toLowerCase();
  }

  @Override
  public boolean accept(AbstractSimpleTile tile) {
    String label = ObjectUtility.nvl(tile.getLabel(), "");
    String filterText = label.trim().toLowerCase();
    return filterText.indexOf(m_text) > -1;
  }

}
