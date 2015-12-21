package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

import java.math.BigDecimal;

import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.BEANS;

public class AbstractHeatmapField extends AbstractFormField implements IHeatmapField {

  private IHeatmapFieldUIFacade m_uiFacade;

  @Override
  protected void initConfig() {
    m_uiFacade = BEANS.get(ModelContextProxy.class).newProxy(new P_UIFacade(), ModelContext.copyCurrent());
    super.initConfig();
    setViewParameter(new HeatmapViewParameter(new MapPoint(BigDecimal.valueOf(47.39141), BigDecimal.valueOf(8.51180)), 12));
  }

  @Override
  public HeatmapViewParameter getViewParameter() {
    return (HeatmapViewParameter) getProperty(PROP_VIEW_PARAMETER);
  }

  @Override
  public void setViewParameter(HeatmapViewParameter parameter) {
    setProperty(PROP_VIEW_PARAMETER, parameter);
  }

  @Override
  public IHeatmapFieldUIFacade getUIFacade() {
    return m_uiFacade;
  }

  protected class P_UIFacade implements IHeatmapFieldUIFacade {

    @Override
    public void setViewParameterFromUI(HeatmapViewParameter parameter) {
      setViewParameter(parameter);
    }

  }
}
