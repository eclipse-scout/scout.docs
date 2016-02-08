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
package org.eclipse.scout.widgets.client.services.lookup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
public class YearsMonthsLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1l;

  @Override
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    List<ILookupRow<String>> rows = new ArrayList<>();

    int year = Calendar.getInstance().get(Calendar.YEAR);
    String[] months = new DateFormatSymbols().getMonths();

    for (int i = year; i < year + 5; i++) {
      String text = String.valueOf(i);
      String parentKey = String.valueOf(100 * i);

      rows.add(new LookupRow<String>(parentKey, text));

      for (int j = 0; j < 12; j++) {
        String key = String.valueOf(100 * i + j + 1);
        rows.add(new LookupRow<String>(key, months[j]).withParentKey(parentKey));
      }
    }

    return rows;
  }
}
