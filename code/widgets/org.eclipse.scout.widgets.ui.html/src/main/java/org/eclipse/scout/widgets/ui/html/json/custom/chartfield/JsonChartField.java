package org.eclipse.scout.widgets.ui.html.json.custom.chartfield;

import org.eclipse.scout.rt.dataobject.IDataObjectMapper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartConfigDo;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.ChartDataDo;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.IChartField;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonChartField extends JsonFormField<IChartField> {

  public JsonChartField(IChartField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
    super(model, uiSession, id, parent);
  }

  @Override
  public String getObjectType() {
    return "widgets.ChartField";
  }

  @Override
  protected void initJsonProperties(IChartField model) {
    super.initJsonProperties(model);
    putJsonProperty(new JsonProperty<IChartField>(IChartField.PROP_CHART_CONFIG, model) {
      @Override
      protected ChartConfigDo modelValue() {
        return getModel().getChartConfig();
      }

      @Override
      public Object prepareValueForToJson(Object value) {
        return new JSONObject(BEANS.get(IDataObjectMapper.class).writeValue(value));
      }
    });
    putJsonProperty(new JsonProperty<IChartField>(IChartField.PROP_CHART_DATA, model) {
      @Override
      protected ChartDataDo modelValue() {
        return getModel().getChartData();
      }

      @Override
      public Object prepareValueForToJson(Object value) {
        return new JSONObject(BEANS.get(IDataObjectMapper.class).writeValue(value));
      }
    });
  }

  protected JSONArray intArrayToJson(int[][] value) {
    JSONArray res = new JSONArray();
    for (int[] subArr : value) {
      JSONArray subRes = new JSONArray();
      for (int v : subArr) {
        subRes.put(v);
      }
      res.put(subRes);
    }
    return res;
  }

}
