package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.AbstractJsonObjectFactory;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.widgets.client.ui.custom.chartfield.IChartField;
import org.eclipse.scout.widgets.client.ui.tile.ICustomTile;
import org.eclipse.scout.widgets.ui.html.json.custom.chartfield.JsonChartField;
import org.eclipse.scout.widgets.ui.html.json.tile.JsonCustomTile;

@Bean
@Order(100)
public class JsonObjectFactory extends AbstractJsonObjectFactory {

  @Override
  public IJsonAdapter<?> createJsonAdapter(Object model, IUiSession session, String id, IJsonAdapter<?> parent) {
    if (model instanceof ICustomTile) {
      return new JsonCustomTile<ICustomTile>((ICustomTile) model, session, id, parent);
    }
    if (model instanceof IChartField) {
      return new JsonChartField((IChartField) model, session, id, parent);
    }
    return null;
  }

}
