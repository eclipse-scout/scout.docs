/*******************************************************************************
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.custom.chartfield;

import java.net.URL;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;

public interface IChartField extends IFormField {

  String PROP_CONFIG = "config";
  String PROP_CHART_DATA = "chartData";
  String PROP_CONFIG_FILE = "configFile";

  String getConfig();

  /**
   * @param config
   *          JSON format of the chart configuration without data attribute.
   * @see {@link URL https://www.chartjs.org/docs/latest/}
   */
  void setConfig(String config);

  String getConfigFile();

  /**
   * calls {@link IChartField#setConfig(String)} with the parsed content of the given file.
   */
  void setConfigFile(String configFile);

  ChartDataDo getChartData();

  void setChartData(ChartDataDo chartData);

}
