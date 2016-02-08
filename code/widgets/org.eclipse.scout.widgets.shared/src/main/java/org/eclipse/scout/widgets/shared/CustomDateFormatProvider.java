package org.eclipse.scout.widgets.shared;

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

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.date.DateFormatProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Replace
public class CustomDateFormatProvider extends DateFormatProvider {

  public static final int CUSTOM_MEDIUM = 101;

  private static final Logger LOG = LoggerFactory.getLogger(CustomDateFormatProvider.class);

  private final Set<Locale> m_customLocales;
  private final Map<String, Locale> m_countryDefaultLocaleMap;
  private final Map<Locale, PatternBean> m_localePatternMap;
  private Locale[] m_availableLocales;

  public CustomDateFormatProvider() {
    m_customLocales = new HashSet<Locale>();
    m_countryDefaultLocaleMap = new HashMap<String, Locale>();
    m_localePatternMap = new HashMap<Locale, CustomDateFormatProvider.PatternBean>();

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
    PatternBean patternBean = new PatternBean();
    patternBean.putDatePattern(DateFormat.LONG, "d' in 'MMMM' in 'yyyy");
    patternBean.putDatePattern(DateFormat.FULL, "EEEE d' in 'MMMM' in 'yyyy");
    init(new Locale("en", "ES"), "es", patternBean);
    // add locale support for fr_CH and it_CH
    init(new Locale("fr", "CH"), "de");
    init(new Locale("it", "CH"), "de");

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
  public DateFormat getTimeInstance(final int style, Locale locale) {
    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getTimeInstance(style, locale);
    }

    DateFormat df = DateFormat.getTimeInstance(style, defaultLocaleForCountry);
    return toLocalizedDateFormat(df, locale, new IPatternCallback() {

      @Override
      public String getPattern(PatternBean patternBean) {
        return patternBean.getTimePattern(style);
      }
    });
  }

  @Override
  public DateFormat getDateInstance(final int style, Locale locale) {
    if (CUSTOM_MEDIUM == style) {
      return createCustomMediumFormat(locale);
    }

    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getDateInstance(style, locale);
    }

    DateFormat df = DateFormat.getDateInstance(style, defaultLocaleForCountry);
    return toLocalizedDateFormat(df, locale, new IPatternCallback() {

      @Override
      public String getPattern(PatternBean patternBean) {
        return patternBean.getDatePattern(style);
      }
    });
  }

  /**
   * @param locale
   * @return
   */
  protected DateFormat createCustomMediumFormat(Locale locale) {
    if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
      return new SimpleDateFormat("MM/dd/yyyy", locale);
    }
    return new SimpleDateFormat("dd.MM.yyyy", locale);
  }

  @Override
  public DateFormat getDateTimeInstance(final int dateStyle, final int timeStyle, final Locale locale) {
    Locale defaultLocaleForCountry = getDefaultLocaleForCountry(locale);
    if (defaultLocaleForCountry == null) {
      return super.getDateTimeInstance(dateStyle, timeStyle, locale);
    }

    DateFormat df = DateFormat.getDateTimeInstance(dateStyle, timeStyle, defaultLocaleForCountry);
    return toLocalizedDateFormat(df, locale, new IPatternCallback() {

      @Override
      public String getPattern(PatternBean patternBean) {
        String datePattern = patternBean.getDatePattern(dateStyle);
        String timePattern = patternBean.getTimePattern(timeStyle);

        if (datePattern != null && timePattern != null) {
          return datePattern + " " + timePattern;
        }
        else if (datePattern != null) {
          DateFormat defaultTimeFormat = getTimeInstance(timeStyle, locale);
          if (defaultTimeFormat instanceof SimpleDateFormat) {
            return datePattern + " " + ((SimpleDateFormat) defaultTimeFormat).toPattern();
          }
        }
        else if (timePattern != null) {
          DateFormat defaultDateFormat = getDateInstance(dateStyle, locale);
          if (defaultDateFormat instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) defaultDateFormat).toPattern() + " " + timePattern;
          }
        }
        return null;
      }
    });
  }

  private void init(Locale locale, String primaryCountryLanguage) {
    init(locale, primaryCountryLanguage, null);
  }

  private void init(Locale locale, String primaryCountryLanguage, PatternBean patternBean) {
    m_customLocales.add(locale);
    Locale primaryCountryLocale = new Locale(primaryCountryLanguage, locale.getCountry());
    m_countryDefaultLocaleMap.put(primaryCountryLocale.getCountry(), primaryCountryLocale);
    if (patternBean != null) {
      m_localePatternMap.put(locale, patternBean);
    }
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

  private DateFormat toLocalizedDateFormat(DateFormat df, Locale locale, IPatternCallback patternCallback) {
    if (df == null) {
      return null;
    }
    if (!(df instanceof SimpleDateFormat)) {
      return df;
    }

    // replace localized texts such as months and days
    SimpleDateFormat sdf = (SimpleDateFormat) df;
    sdf.setDateFormatSymbols(new DateFormatSymbols(new Locale(locale.getLanguage())));

    PatternBean patternBean = m_localePatternMap.get(locale);
    if (patternBean == null) {
      return sdf;
    }

    String pattern = patternCallback.getPattern(patternBean);
    if (pattern != null && pattern.length() > 0) {
      try {
        sdf.applyPattern(pattern);
      }
      catch (IllegalArgumentException e) {
        LOG.error("Could not apply pattern '{}'", pattern, e);
      }
    }

    return sdf;
  }

  public static class PatternBean {
    Map<Integer, String> m_timePatterns;
    Map<Integer, String> m_datePatterns;

    public PatternBean() {
      m_timePatterns = new HashMap<Integer, String>();
      m_datePatterns = new HashMap<Integer, String>();
    }

    public void putTimePattern(int timeStyle, String pattern) {
      m_timePatterns.put(timeStyle, pattern);
    }

    public String getTimePattern(int timeStyle) {
      return m_timePatterns.get(timeStyle);
    }

    public void putDatePattern(int dateStyle, String pattern) {
      m_datePatterns.put(dateStyle, pattern);
    }

    public String getDatePattern(int dateStyle) {
      return m_datePatterns.get(dateStyle);
    }
  }

  private static interface IPatternCallback {
    public String getPattern(PatternBean patternBean);
  }
}
