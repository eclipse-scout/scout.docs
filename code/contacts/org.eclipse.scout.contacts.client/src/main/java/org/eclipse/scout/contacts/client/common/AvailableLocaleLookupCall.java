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
package org.eclipse.scout.contacts.client.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.platform.nls.LocaleUtility;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class AvailableLocaleLookupCall extends LocalLookupCall<Locale> {

  private static final Locale[] AVAILABLE_LOCALES = new Locale[]{Locale.US, LocaleUtility.parse("de_CH")};
  private static final long serialVersionUID = 1L;

  protected Locale[] sort(Locale[] locales) {
    Arrays.sort(locales, new Comparator<Locale>() {
      @Override
      public int compare(Locale locale1, Locale locale2) {
        String name1 = locale1.getDisplayName(NlsLocale.get());
        String name2 = locale2.getDisplayName(NlsLocale.get());
        return CompareUtility.compareTo(name1, name2);
      }
    });
    return locales;
  }

  @Override
  protected List<LookupRow<Locale>> execCreateLookupRows() {
    List<LookupRow<Locale>> rows = new ArrayList<LookupRow<Locale>>();
    for (Locale locale : sort(AVAILABLE_LOCALES)) {
      String displayName = locale.getDisplayName(NlsLocale.get());
      if (StringUtility.hasText(displayName)) {
        rows.add(new LookupRow<Locale>(locale, displayName));
      }
    }
    return rows;
  }
}
