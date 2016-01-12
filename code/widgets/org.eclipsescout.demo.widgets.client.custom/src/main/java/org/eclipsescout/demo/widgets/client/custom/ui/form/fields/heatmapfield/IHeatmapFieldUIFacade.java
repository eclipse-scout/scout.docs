package org.eclipsescout.demo.widgets.client.custom.ui.form.fields.heatmapfield;

public interface IHeatmapFieldUIFacade {

  void setViewParameterFromUI(HeatmapViewParameter parameter);

  void handleClickFromUI(MapPoint point);
}
