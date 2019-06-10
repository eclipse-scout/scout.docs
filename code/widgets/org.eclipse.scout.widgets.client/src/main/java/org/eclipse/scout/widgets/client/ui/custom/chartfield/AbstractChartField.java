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

import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.dataobject.IDataObjectMapper;
import org.eclipse.scout.rt.platform.BEANS;

public abstract class AbstractChartField extends AbstractFormField implements IChartField {

  @Override
  protected void initConfig() {
    super.initConfig();
    setChartConfig(getConfiguredChartConfig());
  }

  protected ChartConfigDo getConfiguredChartConfig() {
    return IChartField.readChartConfig(IChartField.class.getResourceAsStream("DefaultChartConfig.json"));
  }

  @Override
  public void setChartConfig(ChartConfigDo config) {
    propertySupport.setProperty(PROP_CHART_CONFIG, config);
  }

  @Override
  public void setChartConfig(String config) {
    propertySupport.setProperty(PROP_CHART_CONFIG,
        BEANS.get(IDataObjectMapper.class).readValue(config, ChartConfigDo.class));
  }

  @Override
  public ChartConfigDo getChartConfig() {
    return (ChartConfigDo) propertySupport.getProperty(PROP_CHART_CONFIG);
  }

  @Override
  public void setChartData(ChartDataDo chartData) {
    propertySupport.setProperty(PROP_CHART_DATA, chartData);
  }

  @Override
  public ChartDataDo getChartData() {
    return (ChartDataDo) propertySupport.getProperty(PROP_CHART_DATA);
  }

}
