package org.eclipse.scout.heatmap.ui.html;

import java.math.BigDecimal;
import java.util.Collection;

import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.HeatPoint;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.IHeatmapListener;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonEvent;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHeatmapField extends JsonFormField<IHeatmapField> {

  private static final String EVENT_HEAT_POINTS_ADDED = "heatPointsAdded";

  public JsonHeatmapField(IHeatmapField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
    super(model, uiSession, id, parent);
  }

  private IHeatmapListener m_listener = new IHeatmapListener() {

    @Override
    public void heatPointsAdded(Collection<HeatPoint> points) {
      JSONObject json = new JSONObject();
      putProperty(json, "points", heatPointsToJson(points));
      addActionEvent(EVENT_HEAT_POINTS_ADDED, json);
    }
  };

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
      public Object prepareValueForToJson(Object value) {
        return viewParameterToJson((HeatmapViewParameter) value);
      }

    });
    putJsonProperty(new JsonProperty<IHeatmapField>(IHeatmapField.PROP_HEAT_POINT_LIST, model) {
      @Override
      protected Collection<HeatPoint> modelValue() {
        return getModel().getHeatPoints();
      }

      @SuppressWarnings("unchecked")
      @Override
      public Object prepareValueForToJson(Object value) {
        return heatPointsToJson((Collection<HeatPoint>) value);
      }

    });
  }

  @Override
  protected void attachModel() {
    super.attachModel();
    getModel().addHeatmapListener(m_listener);
  }

  @Override
  protected void detachModel() {
    super.detachModel();
    getModel().removeHeatmapListener(m_listener);
  }

  private JSONObject viewParameterToJson(HeatmapViewParameter modelValue) {
    JSONObject json = new JSONObject();
    MapPoint point = modelValue.getCenter();
    JSONObject center = new JSONObject();
    center.put("x", point.getX());
    center.put("y", point.getY());
    json.put("center", center);
    json.put("zoomFactor", modelValue.getZoomFactor());
    return json;
  }

  private JSONArray heatPointsToJson(Collection<HeatPoint> points) {
    JSONArray array = new JSONArray();
    if (points != null) {
      for (HeatPoint point : points) {
        JSONArray jsonHeatPoint = new JSONArray();
        jsonHeatPoint.put(point.getX());
        jsonHeatPoint.put(point.getY());
        jsonHeatPoint.put(point.getIntensity());
        array.put(jsonHeatPoint);
      }
    }
    return array;
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
    HeatmapViewParameter parameter = new HeatmapViewParameter(
        jsonToMapPoint(data.optJSONObject("center")),
        data.getInt("zoomFactor"));
    addPropertyEventFilterCondition(IHeatmapField.PROP_VIEW_PARAMETER, parameter);
    getModel().getUIFacade().setViewParameterFromUI(parameter);
  }

  private MapPoint jsonToMapPoint(JSONObject center) {
    return new MapPoint(new BigDecimal(center.getString("x")), new BigDecimal(center.getString("y")));
  }

}
