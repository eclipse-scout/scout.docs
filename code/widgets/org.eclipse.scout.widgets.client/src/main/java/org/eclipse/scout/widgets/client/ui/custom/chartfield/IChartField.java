/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.custom.chartfield;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.dataobject.IDataObjectMapper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.Assertions;

public interface IChartField extends IFormField {

  String PROP_CHART_CONFIG = "chartConfig";
  String PROP_CHART_DATA = "chartData";

  ChartConfigDo getChartConfig();

  /**
   * @param config
   *          JSON format of the chart configuration without data attribute.
   * @see {@link URL https://www.chartjs.org/docs/latest/}
   */
  void setChartConfig(String chartConfig);

  void setChartConfig(ChartConfigDo chartConfig);

  ChartDataDo getChartData();

  void setChartData(ChartDataDo chartData);

  public static ChartConfigDo readChartConfig(InputStream is) {
    Assertions.assertNotNull(is);
    return BEANS.get(IDataObjectMapper.class).readValue(is, ChartConfigDo.class);
  }

}
