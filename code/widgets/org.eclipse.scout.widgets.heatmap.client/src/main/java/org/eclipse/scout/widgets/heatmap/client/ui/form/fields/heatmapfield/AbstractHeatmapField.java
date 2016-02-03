package org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;

import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.annotations.ConfigProperty;
import org.eclipse.scout.rt.platform.util.EventListenerList;

public class AbstractHeatmapField extends AbstractFormField implements IHeatmapField {

  private IHeatmapFieldUIFacade m_uiFacade;

  private final EventListenerList m_listenerList = new EventListenerList();

  @ConfigProperty(ConfigProperty.OBJECT)
  public HeatmapViewParameter getConfiguredViewParameter() {
    return new HeatmapViewParameter(new MapPoint(BigDecimal.valueOf(48.39141), BigDecimal.valueOf(9.51180)), 5);
  }

  @Override
  protected void initConfig() {
    m_uiFacade = BEANS.get(ModelContextProxy.class).newProxy(new P_UIFacade(), ModelContext.copyCurrent());
    super.initConfig();
    setViewParameter(getConfiguredViewParameter());
    setHeatPoints(Collections.<HeatPoint> emptyList());
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

    @Override
    public void handleClickFromUI(MapPoint point) {
      handleClick(point);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<HeatPoint> getHeatPoints() {
    return Collections.unmodifiableList((List<HeatPoint>) getProperty(PROP_HEAT_POINT_LIST));
  }

  @Override
  public void setHeatPoints(Collection<HeatPoint> heatPoints) {
    setProperty(PROP_HEAT_POINT_LIST, new ArrayList<>(heatPoints));
  }

  @Override
  public void handleClick(MapPoint point) {
    fireMapClicked(point);
  }

  @Override
  public void addHeatPoint(HeatPoint heatPoint) {
    ArrayList<HeatPoint> points = new ArrayList<>();
    points.add(heatPoint);
    addHeatPoints(points);
  }

  @Override
  public void addHeatPoints(Collection<HeatPoint> heatPoints) {
    @SuppressWarnings("unchecked")
    List<HeatPoint> list = (List<HeatPoint>) getProperty(PROP_HEAT_POINT_LIST);
    list.addAll(heatPoints);
    fireHeatPointsAdded(heatPoints);
  }

  private void fireMapClicked(MapPoint point) {
    for (EventListener l : getHeatmapListeners()) {
      ((IHeatmapListener) l).mapClicked(point);
    }
  }

  private void fireHeatPointsAdded(Collection<HeatPoint> heatPoints) {
    for (EventListener l : getHeatmapListeners()) {
      ((IHeatmapListener) l).heatPointsAdded(heatPoints);
    }
  }

  private EventListener[] getHeatmapListeners() {
    return m_listenerList.getListeners(IHeatmapListener.class);
  }

  @Override
  public void addHeatmapListener(IHeatmapListener listener) {
    m_listenerList.add(IHeatmapListener.class, listener);
  }

  @Override
  public void removeHeatmapListener(IHeatmapListener listener) {
    m_listenerList.remove(IHeatmapListener.class, listener);
  }

}
