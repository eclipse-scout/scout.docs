package org.eclipse.scout.heatmap.demo.server.helloworld;

import org.eclipse.scout.heatmap.demo.shared.helloworld.HeatmapFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHeatmapFormService;

/**
 * <h3>{@link HeatmapFormService}</h3>
 */
public class HeatmapFormService implements IHeatmapFormService {

  @Override
  public HeatmapFormData load(HeatmapFormData input) {
    return input;
  }
}
