/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.sql;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.server.jdbc.derby.AbstractDerbySqlService;

@Order(1950)
public class DerbySqlService extends AbstractDerbySqlService {

  @Override
  protected String getConfiguredJdbcMappingName() {
    return "jdbc:derby:memory:contacts-database;create=true";
  }
}
