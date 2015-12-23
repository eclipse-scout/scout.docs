package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

import java.util.Collection;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;

public interface IHeatmapField extends IFormField {

  String PROP_VIEW_PARAMETER = "viewParameter";

  String PROP_HEAT_POINT_LIST = "heatPointList";

  HeatmapViewParameter getViewParameter();

  Collection<HeatPoint> getHeatPoints();

  void addHeatPoint(HeatPoint heatPoint);

  void addHeatPoints(Collection<HeatPoint> heatPoint);

  void setHeatPoints(Collection<HeatPoint> heatPoints);

  void setViewParameter(HeatmapViewParameter parameter);

  IHeatmapFieldUIFacade getUIFacade();

  void addHeatmapListener(IHeatmapListener listener);

  void removeHeatmapListener(IHeatmapListener listener);

}
