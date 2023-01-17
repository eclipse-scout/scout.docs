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
import java.util.EventListener;

/**
 * Listener interface for clicks on the map of the heatmap field
 */
public interface IHeatmapListener extends EventListener {

  /**
   * Function is called when the user clicks somewhere on the map of the heatmap field.
   *
   * @param point
   *          holds the coordinates of the user's click on the map.
   */
  void mapClicked(MapPoint point);

  /**
   * Function is called when heat points are added to the heatmap field
   *
   * @param points
   *          list of points that have been added.
   */
  void heatPointsAdded(Collection<HeatPoint> points);

}
