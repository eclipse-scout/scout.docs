package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

public class HeatmapViewParameter {

  private MapPoint m_center;
  private int m_zoomFactor;

  public HeatmapViewParameter() {

  }

  public HeatmapViewParameter(MapPoint center, int zoomFactor) {
    this.m_center = center;
    this.m_zoomFactor = zoomFactor;
  }

  public MapPoint getCenter() {
    return m_center;
  }

  public void setCenter(MapPoint center) {
    this.m_center = center;
  }

  public int getZoomFactor() {
    return m_zoomFactor;
  }

  public void setZoomFactor(int zoomFactor) {
    this.m_zoomFactor = zoomFactor;
  }

}
