package org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield;

public class HeatmapViewParameter {

  private final MapPoint m_center;
  private final int m_zoomFactor;

  public HeatmapViewParameter(MapPoint center, int zoomFactor) {
    this.m_center = center;
    this.m_zoomFactor = zoomFactor;
  }

  public MapPoint getCenter() {
    return m_center;
  }

  public int getZoomFactor() {
    return m_zoomFactor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((m_center == null) ? 0 : m_center.hashCode());
    result = prime * result + m_zoomFactor;
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
    HeatmapViewParameter other = (HeatmapViewParameter) obj;
    if (m_center == null) {
      if (other.m_center != null) {
        return false;
      }
    }
    else if (!m_center.equals(other.m_center)) {
      return false;
    }
    if (m_zoomFactor != other.m_zoomFactor) {
      return false;
    }
    return true;
  }

}
