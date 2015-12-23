package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

import java.util.Collection;

public interface IHeatmapFieldUIFacade {

  void setViewParameterFromUI(HeatmapViewParameter parameter);

  void addHeatPointFromUI(HeatPoint heatPoint);

  void addHeatPointsFromUI(Collection<HeatPoint> heatPoints);

}
