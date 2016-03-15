/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.scout.contacts.server.ConfigProperties;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.PlatformExceptionTranslator;
import org.eclipse.scout.rt.server.jdbc.derby.AbstractDerbySqlService;

@Order(1950)
// tag::derbyService[]
public class DerbySqlService extends AbstractDerbySqlService {

  private String jdbcMappingName = CONFIG.getPropertyValue(ConfigProperties.JdbcMappingNameProperty.class);

  @Override
  protected String getConfiguredJdbcMappingName() {
    return CONFIG.getPropertyValue(ConfigProperties.JdbcMappingNameProperty.class)
        + ";create=true";
  }
  // end::derbyService[]

  public void dropDB() {
    try {
      DriverManager.getConnection(jdbcMappingName + ";drop=true");
    }
    catch (SQLException e) {
      BEANS.get(PlatformExceptionTranslator.class).translate(e);
    }
  }
// tag::derbyService[]
}
// end::derbyService[]
