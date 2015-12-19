package org.eclipse.scout.heatmap.ui.html;

import java.math.BigDecimal;

import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonEvent;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.json.JSONObject;

public class JsonHeatmapField extends JsonFormField<IHeatmapField> {

  public JsonHeatmapField(IHeatmapField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
    super(model, uiSession, id, parent);
  }

  @Override
  public String getObjectType() {
    return "HeatmapField";
  }

  @Override
  protected void initJsonProperties(IHeatmapField model) {
    super.initJsonProperties(model);
    putJsonProperty(new JsonProperty<IHeatmapField>(IHeatmapField.PROP_VIEW_PARAMETER, model) {
      @Override
      protected HeatmapViewParameter modelValue() {
        return getModel().getViewParameter();
      }

      @Override
      public Object valueToJson() {
        JSONObject json = new JSONObject();
        MapPoint point = modelValue().getCenter();
        JSONObject center = new JSONObject();
        center.put("x", point.getX());
        center.put("y", point.getY());
        json.put("center", center);
        json.put("zoomFactor", modelValue().getZoomFactor());
        return json;
      }
    });
  }

  @Override
  public void handleUiEvent(JsonEvent event) {
    if ("viewParameterChanged".equals(event.getType())) {
      handleUiViewParameterChanged(event);
    }
    else {
      super.handleUiEvent(event);
    }
  }

  private void handleUiViewParameterChanged(JsonEvent event) {
    JSONObject data = event.getData();
    HeatmapViewParameter parameter = new HeatmapViewParameter();
    parameter.setCenter(jsonToMapPoint(data.optJSONObject("center")));
    parameter.setZoomFactor(data.getInt("zoomFactor"));

    // TODO ASA0
//    getModel().getUIFacade().setViewParameter(paramater);
  }

  private MapPoint jsonToMapPoint(JSONObject optJSONObject) {
    // TODO Auto-generated method stub
    return new MapPoint(BigDecimal.ONE, BigDecimal.ONE);
  }

}
