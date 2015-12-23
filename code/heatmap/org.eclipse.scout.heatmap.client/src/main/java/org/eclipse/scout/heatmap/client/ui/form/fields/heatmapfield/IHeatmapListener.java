package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

import java.util.Collection;
import java.util.EventListener;

public interface IHeatmapListener extends EventListener {

  void heatPointsAdded(Collection<HeatPoint> points);

}
