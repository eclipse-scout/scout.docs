package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;

public interface IHeatmapField extends IFormField {

  String PROP_VIEW_PARAMETER = "viewParameter";

  String PROP_HEAT_LAYER_PARAMETER = "heatLayerParameter";

  HeatmapViewParameter getViewParameter();

}
