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

import org.eclipse.scout.contacts.server.sql.DatabaseProperties.DatabaseAutoCreateProperty;
import org.eclipse.scout.contacts.server.sql.DatabaseProperties.JdbcMappingNameProperty;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.PlatformExceptionTranslator;
import org.eclipse.scout.rt.server.jdbc.derby.AbstractDerbySqlService;

@Order(1950)
// tag::service[]
public class DerbySqlService extends AbstractDerbySqlService {

  @Override
  protected String getConfiguredJdbcMappingName() {
    String mappingName = CONFIG.getPropertyValue(JdbcMappingNameProperty.class);

    // add create attribute if we need to autocreate the db
    if (CONFIG.getPropertyValue(DatabaseAutoCreateProperty.class)) {
      return mappingName + ";create=true"; // <1>
    }

    return mappingName;
  }
  // end::service[]

  public void dropDB() {
    try {
      String jdbcMappingName = CONFIG.getPropertyValue(DatabaseProperties.JdbcMappingNameProperty.class);
      DriverManager.getConnection(jdbcMappingName + ";drop=true");
    }
    catch (SQLException e) {
      BEANS.get(PlatformExceptionTranslator.class).translate(e);
    }
  }
// tag::service[]
}
// end::service[]
