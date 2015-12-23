package org.eclipse.scout.heatmap.demo.shared.helloworld;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

/**
 * <h3>{@link IHeatmapFormService}</h3>
 */
@TunnelToServer
public interface IHeatmapFormService extends IService {
  HeatmapFormData load(HeatmapFormData input);
}
