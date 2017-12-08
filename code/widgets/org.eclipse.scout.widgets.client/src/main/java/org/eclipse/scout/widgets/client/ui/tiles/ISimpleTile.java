package org.eclipse.scout.widgets.client.ui.tiles;

import org.eclipse.scout.rt.client.ui.tile.ITile;

public interface ISimpleTile extends ITile {

  String PROP_LABEL = "label";

  String getLabel();

  void setLabel(String label);

  String getGroup();

  void setGroup(String group);
}
