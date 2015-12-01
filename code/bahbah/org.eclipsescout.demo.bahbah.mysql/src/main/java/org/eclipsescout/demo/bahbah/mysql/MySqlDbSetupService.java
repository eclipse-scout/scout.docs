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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipsescout.demo.bahbah.server.services.db.IDbSetupService;
import org.eclipsescout.demo.bahbah.server.util.UserUtility;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType;

/**
 * class that installs the bahbah DB schema
 */
@Order(2000)
public class MySqlDbSetupService implements IDbSetupService {
  @Override
  public void installDb() {
    Set<String> existingTables = getExistingTables();

    if (!existingTables.contains("tabusers")) {
      SQL.insert(" CREATE TABLE TABUSERS ("
          + " u_id BIGINT NOT NULL AUTO_INCREMENT, "
          + " username VARCHAR(32) NOT NULL, "
          + " pass VARCHAR(1024) NOT NULL, "
          + " salt VARCHAR(64) NOT NULL, "
          + " permission_id INT NOT NULL, "
          + " icon BLOB, "
          + " PRIMARY KEY (u_id)"
          + ")");
      SQL.commit();

      SQL.insert(" CREATE UNIQUE INDEX IX_USERNAME ON TABUSERS (username) ");
      SQL.commit();

      // create first admin account
      UserUtility.createNewUser("admin", "admin", UserRoleCodeType.AdministratorCode.ID);
      SQL.commit();
    }

  }

  private Set<String> getExistingTables() {
    Object[][] existingTables = SQL.select("SHOW TABLES");
    HashSet<String> result = new HashSet<String>(existingTables.length);
    for (Object[] row : existingTables) {
      result.add(row[0] + "");
    }
    return result;
  }
}
