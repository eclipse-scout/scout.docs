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

import java.util.Collection;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.platform.util.event.IFastListenerList;

public interface IHeatmapField extends IFormField {

  String PROP_VIEW_PARAMETER = "viewParameter";
  String PROP_HEAT_POINT_LIST = "heatPointList";

  HeatmapViewParameter getViewParameter();

  Collection<HeatPoint> getHeatPoints();

  void handleClick(MapPoint point);

  void addHeatPoint(HeatPoint heatPoint);

  void addHeatPoints(Collection<HeatPoint> heatPoints);

  void setHeatPoints(Collection<HeatPoint> heatPoints);

  void setViewParameter(HeatmapViewParameter parameter);

  IHeatmapFieldUIFacade getUIFacade();

  IFastListenerList<IHeatmapListener> heatmapListeners();

  default void addHeatmapListener(IHeatmapListener listener) {
    heatmapListeners().add(listener);
  }

  default void removeHeatmapListener(IHeatmapListener listener) {
    heatmapListeners().remove(listener);
  }
}
