package org.eclipse.scout.widgets.heatmap.ui.html;

import java.math.BigDecimal;
import java.util.Collection;

import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonEvent;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.HeatPoint;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.IHeatmapListener;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHeatmapField extends JsonFormField<IHeatmapField> {

  private static final String EVENT_HEAT_POINTS_ADDED = "heatPointsAdded";
  private static final String EVENT_MAP_CLICKED = "mapClicked";

  public JsonHeatmapField(IHeatmapField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
    super(model, uiSession, id, parent);
  }

  private final IHeatmapListener m_listener = new IHeatmapListener() {

    @Override
    public void heatPointsAdded(Collection<HeatPoint> points) {
      JSONObject json = new JSONObject();
      putProperty(json, "points", heatPointsToJson(points));
      addActionEvent(EVENT_HEAT_POINTS_ADDED, json);
    }

    @Override
    public void mapClicked(MapPoint point) {
      JSONObject json = new JSONObject();
      putProperty(json, "point", mapPointToJson(point));
      addActionEvent(EVENT_MAP_CLICKED, json);
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
    json.put("center", mapPointToJson(modelValue.getCenter()));
    json.put("zoomFactor", modelValue.getZoomFactor());
    return json;
  }

  protected JSONArray heatPointsToJson(Collection<HeatPoint> points) {
    if (points == null) {
      return null;
    }
    JSONArray jsonArray = new JSONArray();
    for (HeatPoint point : points) {
      jsonArray.put(heatPointToJson(point));
    }
    return jsonArray;
  }

  protected JSONObject heatPointToJson(HeatPoint heatPoint) {
    if (heatPoint == null) {
      return null;
    }
    JSONObject json = new JSONObject();
    json.put("x", heatPoint.getX());
    json.put("y", heatPoint.getY());
    json.put("intensity", heatPoint.getIntensity());
    return json;
  }

  protected JSONObject mapPointToJson(MapPoint mapPoint) {
    if (mapPoint == null) {
      return null;
    }
    JSONObject json = new JSONObject();
    json.put("x", mapPoint.getX());
    json.put("y", mapPoint.getY());
    return json;
  }

  protected MapPoint jsonToMapPoint(JSONObject jsonPoint) {
    BigDecimal x = NumberUtility.getBigDecimalValue(jsonPoint.get("x"));
    BigDecimal y = NumberUtility.getBigDecimalValue(jsonPoint.get("y"));
    return new MapPoint(x, y);
  }

  @Override
  public void handleUiEvent(JsonEvent event) {
    if ("viewParameterChange".equals(event.getType())) {
      handleUiViewParameterChange(event);
    }
    else if ("click".equals(event.getType())) {
      handleUiClick(event);
    }
    else {
      super.handleUiEvent(event);
    }
  }

  private void handleUiClick(JsonEvent event) {
    JSONObject data = event.getData();
    MapPoint point = jsonToMapPoint(data.optJSONObject("point"));
    getModel().getUIFacade().handleClickFromUI(new MapPoint(point.getX(), point.getY()));
  }

  private void handleUiViewParameterChange(JsonEvent event) {
    JSONObject data = event.getData();
    HeatmapViewParameter parameter = new HeatmapViewParameter(
        jsonToMapPoint(data.optJSONObject("center")),
        data.getInt("zoomFactor"));
    addPropertyEventFilterCondition(IHeatmapField.PROP_VIEW_PARAMETER, parameter);
    getModel().getUIFacade().setViewParameterFromUI(parameter);
  }
}
