/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.organization;

import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.organization.IOrganizationLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

//tag::all[]
public class OrganizationLookupService
    extends AbstractSqlLookupService<String>
    implements IOrganizationLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return SQLs.ORGANIZATION_LOOKUP; // <1>
  }
}
//end::all[]
