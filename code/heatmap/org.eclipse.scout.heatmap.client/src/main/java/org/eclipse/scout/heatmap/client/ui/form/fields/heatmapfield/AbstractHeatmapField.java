package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

import java.math.BigDecimal;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;

public class AbstractHeatmapField extends AbstractFormField implements IHeatmapField {

  private HeatmapViewParameter m_viewParameter;

  @Override
  protected void initConfig() {
    super.initConfig();
    // TODO create getConfigured
    m_viewParameter = new HeatmapViewParameter(new MapPoint(BigDecimal.valueOf(47.39141), BigDecimal.valueOf(8.51180)), 17);

  }

  @Override
  public HeatmapViewParameter getViewParameter() {
    return m_viewParameter;
  }
}
