/*******************************************************************************
 * Copyright (c) 2014-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.ui.html.json;

import java.net.URL;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.ui.html.json.IDefaultValuesConfigurationContributor;
import org.eclipse.scout.widgets.ui.html.ResourceBase;

@Order(100)
public class WidgetsDefaultValuesConfigurationContributor implements IDefaultValuesConfigurationContributor {

  @Override
  public URL contributeDefaultValuesConfigurationUrl() {
    return ResourceBase.class.getResource("json/defaultValues.json");
  }
}
