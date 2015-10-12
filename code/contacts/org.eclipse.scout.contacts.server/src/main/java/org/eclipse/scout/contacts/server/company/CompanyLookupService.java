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
package org.eclipse.scout.contacts.server.company;

import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.contacts.shared.company.ICompanyLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

public class CompanyLookupService extends AbstractSqlLookupService<String> implements ICompanyLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return SQLs.COMPANY_LOOKUP;
  }
}
