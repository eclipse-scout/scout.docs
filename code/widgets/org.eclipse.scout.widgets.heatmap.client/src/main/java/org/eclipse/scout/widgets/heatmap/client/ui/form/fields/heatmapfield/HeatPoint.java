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

public class HeatPoint extends MapPoint {

  private final Float m_intensity;

  public HeatPoint(BigDecimal x, BigDecimal y, Float intensity) {
    super(x, y);
    m_intensity = intensity;
  }

  public Float getIntensity() {
    return m_intensity;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((m_intensity == null) ? 0 : m_intensity.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    HeatPoint other = (HeatPoint) obj;
    if (m_intensity == null) {
      if (other.m_intensity != null) {
        return false;
      }
    }
    else if (!m_intensity.equals(other.m_intensity)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "HeatPoint [m_x=" + getX() + ", m_y=" + getY() + ", m_intensity=" + m_intensity + "]";
  }
}
