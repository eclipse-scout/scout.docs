/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.person;

import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.person.IPersonLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class PersonLookupService extends AbstractSqlLookupService<String> implements IPersonLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return SQLs.PERSON_LOOKUP;
  }
}
