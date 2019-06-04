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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChartField extends AbstractFormField implements IChartField {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractChartField.class);

  // keep the reference since property support uses WeakReferences.
  private PropertyChangeListener m_propertyListener = new PropertyChangeListener() {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      if (PROP_CONFIG_FILE.equals(evt.getPropertyName())) {
        setConfig(readConfigFile((String) evt.getNewValue()));
      }
    }
  };

  @Override
  protected void initFieldInternal() {
    addPropertyChangeListener(m_propertyListener);
  }

  @Override
  protected void initConfig() {
    super.initConfig();
    setConfig(readConfigFile(getConfiguredConfigFile()));
  }

  protected String getConfiguredConfigFile() {
    return "DefaultChartConfig.json";
  }

  protected String readConfigFile(String configFile) {
    Assertions.assertTrue(StringUtility.hasText(configFile), "ChartFields must have a configuration!");
    InputStream is = null;
    try {
      is = AbstractChartField.class.getResourceAsStream(configFile);
      return new String(IOUtility.readBytes(is), Charset.forName("UTF-8"));
    }
    finally {
      try {
        if (is != null) {
          is.close();
        }
      }
      catch (IOException e) {
        LOG.error("Could not read chart config '" + configFile + "'.", e);
      }
    }
  }

  @Override
  public void setConfigFile(String configFile) {
    propertySupport.setPropertyString(PROP_CONFIG_FILE, configFile);
  }

  @Override
  public String getConfigFile() {
    return propertySupport.getPropertyString(PROP_CONFIG_FILE);
  }

  @Override
  public void setConfig(String config) {
    propertySupport.setPropertyString(PROP_CONFIG, config);
  }

  @Override
  public String getConfig() {
    return propertySupport.getPropertyString(PROP_CONFIG);
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
