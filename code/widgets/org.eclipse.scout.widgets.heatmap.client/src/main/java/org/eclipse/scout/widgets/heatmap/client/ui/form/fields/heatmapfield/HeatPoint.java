package org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield;

import java.math.BigDecimal;

public class HeatPoint extends MapPoint {

  private final Float m_intensity;

  public HeatPoint(BigDecimal x, BigDecimal y, Float intensity) {
    super(x, y);
    this.m_intensity = intensity;
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

}
