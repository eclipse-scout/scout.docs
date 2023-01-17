/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.sql;

import java.sql.SQLException;

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
    return CONFIG.getPropertyValue(JdbcMappingNameProperty.class);
  }

  public void createDB() {
    String mappingName = CONFIG.getPropertyValue(JdbcMappingNameProperty.class);
    try {
      runDerbyCommand(mappingName + ";create=true"); // <1>
    }
    catch (SQLException e) {
      throw BEANS.get(PlatformExceptionTranslator.class).translate(e);
    }
  }
}
// end::service[]
