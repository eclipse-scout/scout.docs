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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.DateFormatProvider;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
public class DateFormatLocaleLookupCall extends LocalLookupCall<Locale> {

  private static final long serialVersionUID = 1L;

  private Locale[] sort(Locale[] locales) {
    Comparator<Locale> localeComparator = new Comparator<Locale>() {
      @Override
      public int compare(Locale locale1, Locale locale2) {
        return locale1.toString().compareTo(locale2.toString());
      }
    };

    Arrays.sort(locales, localeComparator);

    return locales;
  }

  @Override
  protected List<LookupRow<Locale>> execCreateLookupRows() throws ProcessingException {
    ArrayList<LookupRow<Locale>> rows = new ArrayList<LookupRow<Locale>>();
    Locale[] locales = BEANS.get(DateFormatProvider.class).getAvailableLocales();

    for (Locale locale : sort(locales)) {
      rows.add(new LookupRow<Locale>(locale, locale.getDisplayName()));
    }

    return rows;
  }
}
