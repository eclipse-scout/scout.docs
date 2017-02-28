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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public abstract class AbstractLocaleLookupCall extends LocalLookupCall<Locale> {
  private static final long serialVersionUID = 1L;

  public AbstractLocaleLookupCall() {
    super();
  }

  protected Locale[] sort(Locale[] locales) {
    Comparator<Locale> localeComparator = new Comparator<Locale>() {
      @Override
      public int compare(Locale locale1, Locale locale2) {
        String name1 = locale1.getDisplayName(NlsLocale.get());
        String name2 = locale2.getDisplayName(NlsLocale.get());
        return CompareUtility.compareTo(name1, name2);
      }
    };
    Arrays.sort(locales, localeComparator);
    return locales;
  }

  @Override
  protected List<LookupRow<Locale>> execCreateLookupRows() {
    List<LookupRow<Locale>> rows = new ArrayList<LookupRow<Locale>>();
    Locale[] locales = availableLocales();
    for (Locale locale : sort(locales)) {
      String displayName = locale.getDisplayName(NlsLocale.get());
      if (StringUtility.hasText(displayName)) {
        LookupRow<Locale> row = new LookupRow<Locale>(locale, displayName);
        LocaleTableRowData bean = new LocaleTableRowData();
        bean.setCountry(locale.getCountry());
        bean.setLanguage(locale.getLanguage());
        row.withAdditionalTableRowData(bean);
        rows.add(row);
      }
    }
    return rows;
  }

  protected abstract Locale[] availableLocales();

  public static class LocaleTableRowData extends AbstractTableRowData {

    private static final long serialVersionUID = 1L;
    public static final String country = "country";
    public static final String language = "language";

    private String m_country;
    private String m_language;

    public LocaleTableRowData() {
    }

    public String getLanguage() {
      return m_language;
    }

    public void setLanguage(String language) {
      m_language = language;
    }

    public String getCountry() {
      return m_country;
    }

    public void setCountry(String country) {
      m_country = country;
    }
  }
}
