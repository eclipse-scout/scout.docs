/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.client.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("4b011d3b-4a60-4777-823f-fa0cc09d3292")
public class AvailableLocaleLookupCall extends LocalLookupCall<Locale> {

  private static final Locale[] AVAILABLE_LOCALES = {Locale.US, new Locale("de", "CH")};
  private static final long serialVersionUID = 1L;

  protected Locale[] sort(Locale[] locales) {
    Arrays.sort(locales, (locale1, locale2) -> {
      String name1 = locale1.getDisplayName(NlsLocale.get());
      String name2 = locale2.getDisplayName(NlsLocale.get());
      return ObjectUtility.compareTo(name1, name2);
    });
    return locales;
  }

  @Override
  protected List<LookupRow<Locale>> execCreateLookupRows() {
    List<LookupRow<Locale>> rows = new ArrayList<>();
    for (Locale locale : sort(AVAILABLE_LOCALES)) {
      String displayName = locale.getDisplayName(NlsLocale.get());
      if (StringUtility.hasText(displayName)) {
        rows.add(new LookupRow<>(locale, displayName));
      }
    }
    return rows;
  }
}
