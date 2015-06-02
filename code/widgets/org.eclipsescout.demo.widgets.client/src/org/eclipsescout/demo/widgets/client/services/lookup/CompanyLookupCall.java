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
package org.eclipsescout.demo.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.widgets.shared.Icons;

public class CompanyLookupCall extends LocalLookupCall<Long> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<ILookupRow<Long>> execCreateLookupRows() throws ProcessingException {
    ArrayList<ILookupRow<Long>> rows = new ArrayList<ILookupRow<Long>>();
    rows.add(createLookupRow(1L, "Business Systems Integration AG", "World best company.", 3L, Icons.WeatherSun, true));
    rows.add(createLookupRow(2L, "Eclipse", "Open source stuff.", 1L, Icons.WeatherCloudy, false));
    rows.add(createLookupRow(3L, "Google", "Also a fancy company.", 1L, null, false));
    // create some more rows
    for (int i = 4; i < 120; i++) {
      rows.add(createLookupRow(4L, "company" + String.format("%03d", i), "A company with id " + String.format("%03d", i) + ".", 3L, null, false));
    }
    return rows;
  }

  private LookupRow<Long> createLookupRow(long id, String name, String detail, long companyType, String iconId, boolean bold) {
    CompanySmartTableData addTableData = new CompanySmartTableData(detail, companyType);
    LookupRow<Long> result = new LookupRow<Long>(id, name, iconId, null, null, null, bold ? FontSpec.parse("bold") : null);
    result.setAdditionalTableRowData(addTableData);
    return result;
  }
}
