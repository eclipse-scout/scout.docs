package org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield;

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

}
