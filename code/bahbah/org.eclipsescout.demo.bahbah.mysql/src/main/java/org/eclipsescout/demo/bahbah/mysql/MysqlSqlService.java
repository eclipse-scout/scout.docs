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
package org.eclipsescout.demo.bahbah.mysql;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.server.jdbc.mysql.AbstractMySqlSqlService;

@Order(1900)
public class MysqlSqlService extends AbstractMySqlSqlService {

  @Override
  protected String getConfiguredJdbcMappingName() {
    return "jdbc:mysql://localhost:3306/bahbah";
  }

  @Override
  protected String getConfiguredUsername() {
    return "root";
  }

  @Override
  protected String getConfiguredPassword() {
    return "";
  }

}
