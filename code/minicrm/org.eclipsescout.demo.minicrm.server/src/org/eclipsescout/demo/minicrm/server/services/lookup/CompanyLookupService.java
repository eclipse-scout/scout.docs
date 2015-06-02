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
package org.eclipsescout.demo.minicrm.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;
import org.eclipsescout.demo.minicrm.shared.Icons;
import org.eclipsescout.demo.minicrm.shared.services.lookup.ICompanyLookupService;

public class CompanyLookupService extends AbstractSqlLookupService implements ICompanyLookupService {

  @Override
  public String getConfiguredSqlSelect() {
    return "SELECT  C.COMPANY_NR, " +
        "        C.NAME, " +
        "        '" + Icons.Building + "' " +
        "FROM    COMPANY C " +
        "WHERE   1=1 " +
        "  AND (C.TYPE_UID = :master OR :master IS NULL) " +
        "<key>   AND     C.COMPANY_NR = :key </key> " +
        "<text>  AND     UPPER(C.NAME) LIKE UPPER(:text||'%') </text> " +
        "<all> </all> ";
  }
}
