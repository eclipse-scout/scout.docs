/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.annotations.ConfigProperty;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.event.FastListenerList;
import org.eclipse.scout.rt.platform.util.event.IFastListenerList;

@ClassId("a30dbf44-cbfd-482b-ad86-f1d832c397bc")
public class AbstractHeatmapField extends AbstractFormField implements IHeatmapField {

  private IHeatmapFieldUIFacade m_uiFacade;

  private final FastListenerList<IHeatmapListener> m_listenerList = new FastListenerList<>();

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
    heatmapListeners().list().forEach(listener -> listener.mapClicked(point));
  }

  private void fireHeatPointsAdded(Collection<HeatPoint> heatPoints) {
    heatmapListeners().list().forEach(listener -> listener.heatPointsAdded(heatPoints));
  }

  @Override
  public IFastListenerList<IHeatmapListener> heatmapListeners() {
    return m_listenerList;
  }
}
