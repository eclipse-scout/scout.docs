package org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield;

public interface IHeatmapFieldUIFacade {

  void setViewParameterFromUI(HeatmapViewParameter parameter);

  void handleClickFromUI(MapPoint point);
}
