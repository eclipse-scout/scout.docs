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
