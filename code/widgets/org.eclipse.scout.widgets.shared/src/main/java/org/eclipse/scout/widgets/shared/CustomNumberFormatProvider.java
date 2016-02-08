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
package org.eclipse.scout.widgets.shared;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.NumberFormatProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Replace
public class CustomNumberFormatProvider extends NumberFormatProvider {

  private static final Logger LOG = LoggerFactory.getLogger(CustomNumberFormatProvider.class);

  private final Set<Locale> m_customLocales;
  private final Map<String, Locale> m_countryDefaultLocaleMap;
  private Locale[] m_availableLocales;

  public CustomNumberFormatProvider() {
    m_customLocales = new HashSet<Locale>();
    m_countryDefaultLocaleMap = new HashMap<String, Locale>();

    // add locale support for en_CH
    init(new Locale("en", "CH"), "de");
    // add locale support for en_DE
    init(new Locale("en", "DE"), "de");
    // add locale support for en_AT
    init(new Locale("en", "AT"), "de");
    // add locale support for en_FR
    init(new Locale("en", "FR"), "fr");
    // add locale support for en_IT
    init(new Locale("en", "IT"), "it");
    // add locale support for en_DK
    init(new Locale("en", "DK"), "da");
    // add locale support for en_ES
    init(new Locale("en", "ES"), "es");

    // available locales
    HashSet<Locale> availableLocales = new HashSet<Locale>();
    availableLocales.addAll(m_customLocales);
    availableLocales.addAll(Arrays.asList(super.getAvailableLocales()));
    m_availableLocales = availableLocales.toArray(new Locale[availableLocales.size()]);

  }

  @Override
  public Locale[] getAvailableLocales() {
    return m_availableLocales;
  }

  @Override
  public DecimalFormat getCurrencyInstance(Locale locale) {
    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getCurrencyInstance(locale);
    }
    return (DecimalFormat) NumberFormat.getCurrencyInstance(defaultLocaleForCountry);
  }

  @Override
  public NumberFormat getIntegerInstance(Locale locale) {
    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getIntegerInstance(locale);
    }
    return NumberFormat.getIntegerInstance(defaultLocaleForCountry);
  }

  @Override
  public DecimalFormat getNumberInstance(Locale locale) {
    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getNumberInstance(locale);
    }
    return (DecimalFormat) NumberFormat.getNumberInstance(defaultLocaleForCountry);
  }

  @Override
  public DecimalFormat getPercentInstance(Locale locale) {
    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getPercentInstance(locale);
    }
    return (DecimalFormat) NumberFormat.getPercentInstance(defaultLocaleForCountry);
  }

  private void init(Locale locale, String primaryCountryLanguage) {
    m_customLocales.add(locale);
    Locale primaryCountryLocale = new Locale(primaryCountryLanguage, locale.getCountry());
    m_countryDefaultLocaleMap.put(primaryCountryLocale.getCountry(), primaryCountryLocale);
  }

  private Locale getDefaultLocaleForCountry(Locale locale) {
    if (!m_customLocales.contains(locale)) {
      return null;
    }

    Locale countryDefaultLocale = m_countryDefaultLocaleMap.get(locale.getCountry());
    if (countryDefaultLocale == null) {
      LOG.warn("Unexpected: No default locale found for country '{}'", locale.getCountry());
      return null;
    }
    return countryDefaultLocale;
  }

}
