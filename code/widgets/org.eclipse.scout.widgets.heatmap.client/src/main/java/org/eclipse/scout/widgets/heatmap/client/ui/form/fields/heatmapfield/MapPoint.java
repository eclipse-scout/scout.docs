package org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield;

import java.math.BigDecimal;

public class MapPoint {
  private final BigDecimal m_x;
  private final BigDecimal m_y;

  public MapPoint(BigDecimal x, BigDecimal y) {
    this.m_x = x;
    this.m_y = y;
  }

  public BigDecimal getX() {
    return m_x;
  }

  public BigDecimal getY() {
    return m_y;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((m_x == null) ? 0 : m_x.hashCode());
    result = prime * result + ((m_y == null) ? 0 : m_y.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MapPoint other = (MapPoint) obj;
    if (m_x == null) {
      if (other.m_x != null) {
        return false;
      }
    }
    else if (!(m_x.compareTo(other.m_x) == 0)) {
      return false;
    }
    if (m_y == null) {
      if (other.m_y != null) {
        return false;
      }
    }
    else if (!(m_y.compareTo(other.m_y) == 0)) {
      return false;
    }
    return true;
  }

}
