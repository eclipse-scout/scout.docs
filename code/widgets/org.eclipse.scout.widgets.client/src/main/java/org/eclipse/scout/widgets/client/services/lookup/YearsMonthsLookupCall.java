/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
@ClassId("7fa5640d-e4e3-46d0-a559-81341b3e45f8")
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

      rows.add(new LookupRow<>(parentKey, text));

      for (int j = 0; j < 12; j++) {
        String key = String.valueOf(100 * i + j + 1);
        rows.add(new LookupRow<>(key, months[j]).withParentKey(parentKey));
      }
    }

    return rows;
  }
}
