package org.eclipse.scout.widgets.ui.html.json.tile;

import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.tile.JsonTile;
import org.eclipse.scout.widgets.client.ui.tile.ISimpleTile;

public class JsonSimpleTile<T extends ISimpleTile> extends JsonTile<T> {

  public JsonSimpleTile(T model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
    super(model, uiSession, id, parent);
  }

  @Override
  public String getObjectType() {
    return "widgets.SimpleTile";
  }

  @Override
  protected void initJsonProperties(T model) {
    super.initJsonProperties(model);
    putJsonProperty(new JsonProperty<T>(ISimpleTile.PROP_LABEL, model) {
      @Override
      protected String modelValue() {
        return getModel().getLabel();
      }
    });
  }

}
