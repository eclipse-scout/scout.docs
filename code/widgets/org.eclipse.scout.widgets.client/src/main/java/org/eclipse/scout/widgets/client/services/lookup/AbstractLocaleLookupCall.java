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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("772ec00b-8e1f-4df0-bb45-6fedac7ce4a5")
public abstract class AbstractLocaleLookupCall extends LocalLookupCall<Locale> {

  private static final long serialVersionUID = 1L;

  private boolean m_throwVetoException;

  protected Locale[] sort(Locale[] locales) {
    Comparator<Locale> localeComparator = (locale1, locale2) -> {
      String name1 = locale1.getDisplayName(NlsLocale.get());
      String name2 = locale2.getDisplayName(NlsLocale.get());
      return ObjectUtility.compareTo(name1, name2);
    };
    Arrays.sort(locales, localeComparator);
    return locales;
  }

  @Override
  protected List<LookupRow<Locale>> execCreateLookupRows() {
    if (m_throwVetoException) {
      throw new VetoException("Lookup failed");
    }
    List<LookupRow<Locale>> rows = new ArrayList<>();
    Locale[] locales = availableLocales();
    boolean browse = this.getAll() != null;
    for (Locale locale : sort(locales)) {
      String displayName = locale.getDisplayName(NlsLocale.get());
      if (StringUtility.hasText(displayName)) {
        LookupRow<Locale> row = new LookupRow<>(locale, displayName);
        LocaleTableRowData bean = createTableRowData(locale);
        bean.setCountry(locale.getCountry());
        bean.setLanguage(locale.getLanguage());
        row.withAdditionalTableRowData(bean);
        rows.add(row);
      }
      if (browse && rows.size() >= getMaxRowCount()) {
        break;
      }
    }
    return rows;
  }

  protected LocaleTableRowData createTableRowData(Locale locale) {
    return new LocaleTableRowData();
  }

  protected abstract Locale[] availableLocales();

  public static class LocaleTableRowData extends AbstractTableRowData {

    private static final long serialVersionUID = 1L;
    public static final String country = "country";
    public static final String language = "language";

    private String m_country;
    private String m_language;

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

  /**
   * Used to simulate a lookup that fails with a VetoException.
   */
  public void setThrowVetoException(boolean throwVetoException) {
    m_throwVetoException = throwVetoException;
  }
}
