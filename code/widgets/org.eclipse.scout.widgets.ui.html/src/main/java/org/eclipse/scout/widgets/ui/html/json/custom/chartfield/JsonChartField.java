package org.eclipse.scout.widgets.ui.html.json.custom.chartfield;

import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonValueField;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.IChartField;
import org.json.JSONArray;

public class JsonChartField extends JsonValueField<IChartField> {

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
    putJsonProperty(new JsonProperty<IChartField>(IChartField.PROP_CONFIG, model) {
      @Override
      protected String modelValue() {
        return getModel().getConfig();
      }
    });
    putJsonProperty(new JsonProperty<IChartField>(IChartField.PROP_VALUE, model) {
      @Override
      protected int[][] modelValue() {
        return getModel().getValue();
      }

      @Override
      public Object prepareValueForToJson(Object value) {
        return intArrayToJson((int[][]) value);
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
