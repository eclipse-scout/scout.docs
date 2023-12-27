/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.shared;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
  private final Locale[] m_availableLocales;

  public CustomDateFormatProvider() {
    m_customLocales = new HashSet<>();
    m_countryDefaultLocaleMap = new HashMap<>();
    m_localePatternMap = new HashMap<>();

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
    HashSet<Locale> availableLocales = new HashSet<>();
    availableLocales.addAll(m_customLocales);
    availableLocales.addAll(getJRE11AvailableLocales());
    m_availableLocales = availableLocales.toArray(new Locale[0]);

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
    return toLocalizedDateFormat(df, locale, patternBean -> patternBean.getTimePattern(style));
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
    return toLocalizedDateFormat(df, locale, patternBean -> patternBean.getDatePattern(style));
  }

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
    return toLocalizedDateFormat(df, locale, patternBean -> {
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
    if (pattern != null && !pattern.isEmpty()) {
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
      m_timePatterns = new HashMap<>();
      m_datePatterns = new HashMap<>();
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

  @FunctionalInterface
  private interface IPatternCallback {
    String getPattern(PatternBean patternBean);
  }

  /**
   * Earlier implementations of this class collected the available locales by calling
   * {@link DateFormat#getAvailableLocales()}, but this is not stable.
   * <p>
   * Multiple tests depend on the fact that this list of available locales never changes. This is not the case as any
   * changes to the runtime might change the {@link Locale locales} returned by
   * {@link DateFormat#getAvailableLocales()}. This list represents the latest stable state given by a JRE running with
   * Java 11.
   *
   * @return a fixed list of {@link Locale locales}
   */
  private List<Locale> getJRE11AvailableLocales() {
    return Arrays.asList(
        new Locale.Builder()
            .setLanguageTag("und")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nn")
            .setLanguage("nn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-JO")
            .setLanguage("ar")
            .setRegion("JO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bg")
            .setLanguage("bg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kea")
            .setLanguage("kea")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nds")
            .setLanguage("nds")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zu")
            .setLanguage("zu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("am-ET")
            .setLanguage("am")
            .setRegion("ET")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-DZ")
            .setLanguage("fr")
            .setRegion("DZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ti-ET")
            .setLanguage("ti")
            .setRegion("ET")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bo-CN")
            .setLanguage("bo")
            .setRegion("CN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hsb")
            .setLanguage("hsb")
            .build(),
        new Locale.Builder()
            .setLanguageTag("qu-EC")
            .setLanguage("qu")
            .setRegion("EC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ta-SG")
            .setLanguage("ta")
            .setRegion("SG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lv")
            .setLanguage("lv")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NU")
            .setLanguage("en")
            .setRegion("NU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MS")
            .setLanguage("en")
            .setRegion("MS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hans-SG")
            .setLanguage("zh")
            .setRegion("SG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GG")
            .setLanguage("en")
            .setRegion("GG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-JM")
            .setLanguage("en")
            .setRegion("JM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vo")
            .setLanguage("vo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kkj")
            .setLanguage("kkj")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sv-SE")
            .setLanguage("sv")
            .setRegion("SE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-ME")
            .setLanguage("sr")
            .setRegion("ME")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-BO")
            .setLanguage("es")
            .setRegion("BO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dz-BT")
            .setLanguage("dz")
            .setRegion("BT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mer")
            .setLanguage("mer")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sah")
            .setLanguage("sah")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-ZM")
            .setLanguage("en")
            .setRegion("ZM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-ML")
            .setLanguage("fr")
            .setRegion("ML")
            .build(),
        new Locale.Builder()
            .setLanguageTag("br")
            .setLanguage("br")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ha-NG")
            .setLanguage("ha")
            .setRegion("NG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-SA")
            .setLanguage("ar")
            .setRegion("SA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fa-AF")
            .setLanguage("fa")
            .setRegion("AF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dsb-DE")
            .setLanguage("dsb")
            .setRegion("DE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sk")
            .setLanguage("sk")
            .build(),
        new Locale.Builder()
            .setLanguageTag("os-GE")
            .setLanguage("os")
            .setRegion("GE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ml")
            .setLanguage("ml")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MT")
            .setLanguage("en")
            .setRegion("MT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-LR")
            .setLanguage("en")
            .setRegion("LR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-TD")
            .setLanguage("ar")
            .setRegion("TD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GH")
            .setLanguage("en")
            .setRegion("GH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-IL")
            .setLanguage("en")
            .setRegion("IL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cs")
            .setLanguage("cs")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sv")
            .setLanguage("sv")
            .build(),
        new Locale.Builder()
            .setLanguageTag("el")
            .setLanguage("el")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tzm-MA")
            .setLanguage("tzm")
            .setRegion("MA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("af")
            .setLanguage("af")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sw-UG")
            .setLanguage("sw")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ses-ML")
            .setLanguage("ses")
            .setRegion("ML")
            .build(),
        new Locale.Builder()
            .setLanguageTag("smn")
            .setLanguage("smn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tk-TM")
            .setLanguage("tk")
            .setRegion("TM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Cyrl-ME")
            .setLanguage("sr")
            .setRegion("ME")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-EG")
            .setLanguage("ar")
            .setRegion("EG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dsb")
            .setLanguage("dsb")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lkt-US")
            .setLanguage("lkt")
            .setRegion("US")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vai-Latn-LR")
            .setLanguage("vai")
            .setRegion("LR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yi-001")
            .setLanguage("ji")
            .setRegion("001")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yo-NG")
            .setLanguage("yo")
            .setRegion("NG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("se-NO")
            .setLanguage("se")
            .setRegion("NO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("khq")
            .setLanguage("khq")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sw-CD")
            .setLanguage("sw")
            .setRegion("CD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vo-001")
            .setLanguage("vo")
            .setRegion("001")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-PW")
            .setLanguage("en")
            .setRegion("PW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pl-PL")
            .setLanguage("pl")
            .setRegion("PL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fil-PH")
            .setLanguage("fil")
            .setRegion("PH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("it-VA")
            .setLanguage("it")
            .setRegion("VA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-CS")
            .setLanguage("sr")
            .setRegion("CS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ne-IN")
            .setLanguage("ne")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-PH")
            .setLanguage("es")
            .setRegion("PH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-ES")
            .setLanguage("es")
            .setRegion("ES")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-CO")
            .setLanguage("es")
            .setRegion("CO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bg-BG")
            .setLanguage("bg")
            .setRegion("BG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yi")
            .setLanguage("ji")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-EH")
            .setLanguage("ar")
            .setRegion("EH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bs-Latn-BA")
            .setLanguage("bs")
            .setRegion("BA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-VC")
            .setLanguage("en")
            .setRegion("VC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nds-DE")
            .setLanguage("nds")
            .setRegion("DE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nb-SJ")
            .setLanguage("nb")
            .setRegion("SJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-US")
            .setLanguage("es")
            .setRegion("US")
            .build(),
        new Locale.Builder()
            .setLanguageTag("agq")
            .setLanguage("agq")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hsb-DE")
            .setLanguage("hsb")
            .setRegion("DE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-US-POSIX")
            .setLanguage("en")
            .setRegion("US")
            .setVariant("POSIX")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-150")
            .setLanguage("en")
            .setRegion("150")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-SD")
            .setLanguage("ar")
            .setRegion("SD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-KN")
            .setLanguage("en")
            .setRegion("KN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ha-NE")
            .setLanguage("ha")
            .setRegion("NE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-MO")
            .setLanguage("pt")
            .setRegion("MO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ebu")
            .setLanguage("ebu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ro-RO")
            .setLanguage("ro")
            .setRegion("RO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hans")
            .setLanguage("zh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lb-LU")
            .setLanguage("lb")
            .setRegion("LU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Latn-ME")
            .setLanguage("sr")
            .setRegion("ME")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-GT")
            .setLanguage("es")
            .setRegion("GT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("so-KE")
            .setLanguage("so")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dje-NE")
            .setLanguage("dje")
            .setRegion("NE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bas-CM")
            .setLanguage("bas")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-PM")
            .setLanguage("fr")
            .setRegion("PM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-KM")
            .setLanguage("ar")
            .setRegion("KM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MG")
            .setLanguage("fr")
            .setRegion("MG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-CL")
            .setLanguage("es")
            .setRegion("CL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mn")
            .setLanguage("mn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("agq-CM")
            .setLanguage("agq")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kam-KE")
            .setLanguage("kam")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("teo")
            .setLanguage("teo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tr-TR")
            .setLanguage("tr")
            .setRegion("TR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("eu")
            .setLanguage("eu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fa-IR")
            .setLanguage("fa")
            .setRegion("IR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MO")
            .setLanguage("en")
            .setRegion("MO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("wo")
            .setLanguage("wo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("shi-Tfng")
            .setLanguage("shi")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BZ")
            .setLanguage("en")
            .setRegion("BZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sq-AL")
            .setLanguage("sq")
            .setRegion("AL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-MR")
            .setLanguage("ar")
            .setRegion("MR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-DO")
            .setLanguage("es")
            .setRegion("DO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru")
            .setLanguage("ru")
            .build(),
        new Locale.Builder()
            .setLanguageTag("twq-NE")
            .setLanguage("twq")
            .setRegion("NE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("az")
            .setLanguage("az")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nmg-CM")
            .setLanguage("nmg")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fa")
            .setLanguage("fa")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kl-GL")
            .setLanguage("kl")
            .setRegion("GL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NR")
            .setLanguage("en")
            .setRegion("NR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nd")
            .setLanguage("nd")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kk")
            .setLanguage("kk")
            .build(),
        new Locale.Builder()
            .setLanguageTag("az-Cyrl")
            .setLanguage("az")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MP")
            .setLanguage("en")
            .setRegion("MP")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GD")
            .setLanguage("en")
            .setRegion("GD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tk")
            .setLanguage("tk")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hy")
            .setLanguage("hy")
            .build(),
        new Locale.Builder()
            .setLanguageTag("shi-Latn")
            .setLanguage("shi")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BW")
            .setLanguage("en")
            .setRegion("BW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-AU")
            .setLanguage("en")
            .setRegion("AU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CY")
            .setLanguage("en")
            .setRegion("CY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kab-DZ")
            .setLanguage("kab")
            .setRegion("DZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kde-TZ")
            .setLanguage("kde")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ta-MY")
            .setLanguage("ta")
            .setRegion("MY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ti-ER")
            .setLanguage("ti")
            .setRegion("ER")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nus-SS")
            .setLanguage("nus")
            .setRegion("SS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-RW")
            .setLanguage("en")
            .setRegion("RW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nd-ZW")
            .setLanguage("nd")
            .setRegion("ZW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sv-FI")
            .setLanguage("sv")
            .setRegion("FI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ksb")
            .setLanguage("ksb")
            .build(),
        new Locale.Builder()
            .setLanguageTag("luo")
            .setLanguage("luo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lb")
            .setLanguage("lb")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ne")
            .setLanguage("ne")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-IE")
            .setLanguage("en")
            .setRegion("IE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ln-CD")
            .setLanguage("ln")
            .setRegion("CD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-SG")
            .setLanguage("zh")
            .setRegion("SG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-KI")
            .setLanguage("en")
            .setRegion("KI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nnh-CM")
            .setLanguage("nnh")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("om-ET")
            .setLanguage("om")
            .setRegion("ET")
            .build(),
        new Locale.Builder()
            .setLanguageTag("no")
            .setLanguage("no")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ja-JP")
            .setLanguage("ja")
            .setRegion("JP")
            .build(),
        new Locale.Builder()
            .setLanguageTag("my")
            .setLanguage("my")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ka")
            .setLanguage("ka")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-IL")
            .setLanguage("ar")
            .setRegion("IL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mgh")
            .setLanguage("mgh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("or-IN")
            .setLanguage("or")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MF")
            .setLanguage("fr")
            .setRegion("MF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("shi")
            .setLanguage("shi")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kl")
            .setLanguage("kl")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SZ")
            .setLanguage("en")
            .setRegion("SZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rwk-TZ")
            .setLanguage("rwk")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh")
            .setLanguage("zh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mgh-MZ")
            .setLanguage("mgh")
            .setRegion("MZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-PE")
            .setLanguage("es")
            .setRegion("PE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("saq")
            .setLanguage("saq")
            .build(),
        new Locale.Builder()
            .setLanguageTag("az-Latn")
            .setLanguage("az")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ta")
            .setLanguage("ta")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GB")
            .setLanguage("en")
            .setRegion("GB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lag")
            .setLanguage("lag")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hant-HK")
            .setLanguage("zh")
            .setRegion("HK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-SY")
            .setLanguage("ar")
            .setRegion("SY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ksf-CM")
            .setLanguage("ksf")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bo")
            .setLanguage("bo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kk-KZ")
            .setLanguage("kk")
            .setRegion("KZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tt-RU")
            .setLanguage("tt")
            .setRegion("RU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-PA")
            .setLanguage("es")
            .setRegion("PA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("om-KE")
            .setLanguage("om")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-PS")
            .setLanguage("ar")
            .setRegion("PS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-AS")
            .setLanguage("en")
            .setRegion("AS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-VU")
            .setLanguage("fr")
            .setRegion("VU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bez")
            .setLanguage("bez")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-TW")
            .setLanguage("zh")
            .setRegion("TW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kln")
            .setLanguage("kln")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MC")
            .setLanguage("fr")
            .setRegion("MC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kw")
            .setLanguage("kw")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-MZ")
            .setLanguage("pt")
            .setRegion("MZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-NE")
            .setLanguage("fr")
            .setRegion("NE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vai-Latn")
            .setLanguage("vai")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ksb-TZ")
            .setLanguage("ksb")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ksh")
            .setLanguage("ksh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ur-IN")
            .setLanguage("ur")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ln")
            .setLanguage("ln")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-JE")
            .setLanguage("en")
            .setRegion("JE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gsw-CH")
            .setLanguage("gsw")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ln-CF")
            .setLanguage("ln")
            .setRegion("CF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CX")
            .setLanguage("en")
            .setRegion("CX")
            .build(),
        new Locale.Builder()
            .setLanguageTag("luy-KE")
            .setLanguage("luy")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt")
            .setLanguage("pt")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-AT")
            .setLanguage("en")
            .setRegion("AT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gl")
            .setLanguage("gl")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kkj-CM")
            .setLanguage("kkj")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Cyrl")
            .setLanguage("sr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yue-Hans-CN")
            .setLanguage("yue")
            .setRegion("CN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-GQ")
            .setLanguage("es")
            .setRegion("GQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kn-IN")
            .setLanguage("kn")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-YE")
            .setLanguage("ar")
            .setRegion("YE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("to")
            .setLanguage("to")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SX")
            .setLanguage("en")
            .setRegion("SX")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ga")
            .setLanguage("ga")
            .build(),
        new Locale.Builder()
            .setLanguageTag("qu")
            .setLanguage("qu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru-KZ")
            .setLanguage("ru")
            .setRegion("KZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-TZ")
            .setLanguage("en")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("et")
            .setLanguage("et")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-PR")
            .setLanguage("en")
            .setRegion("PR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mua")
            .setLanguage("mua")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ko-KP")
            .setLanguage("ko")
            .setRegion("KP")
            .build(),
        new Locale.Builder()
            .setLanguageTag("id")
            .setLanguage("in")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ps")
            .setLanguage("ps")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sn")
            .setLanguage("sn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-SR")
            .setLanguage("nl")
            .setRegion("SR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rof")
            .setLanguage("rof")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BS")
            .setLanguage("en")
            .setRegion("BS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("km")
            .setLanguage("km")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zgh")
            .setLanguage("zgh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-NC")
            .setLanguage("fr")
            .setRegion("NC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("be")
            .setLanguage("be")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gv")
            .setLanguage("gv")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es")
            .setLanguage("es")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dua")
            .setLanguage("dua")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gd-GB")
            .setLanguage("gd")
            .setRegion("GB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("jgo")
            .setLanguage("jgo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-BQ")
            .setLanguage("nl")
            .setRegion("BQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CM")
            .setLanguage("fr")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gsw")
            .setLanguage("gsw")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz-Cyrl-UZ")
            .setLanguage("uz")
            .setRegion("UZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pa-Guru-IN")
            .setLanguage("pa")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-KE")
            .setLanguage("en")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("guz")
            .setLanguage("guz")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mfe")
            .setLanguage("mfe")
            .build(),
        new Locale.Builder()
            .setLanguageTag("asa-TZ")
            .setLanguage("asa")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("teo-UG")
            .setLanguage("teo")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ja")
            .setLanguage("ja")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-SN")
            .setLanguage("fr")
            .setRegion("SN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("or")
            .setLanguage("or")
            .build(),
        new Locale.Builder()
            .setLanguageTag("brx")
            .setLanguage("brx")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MA")
            .setLanguage("fr")
            .setRegion("MA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-LU")
            .setLanguage("pt")
            .setRegion("LU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-BL")
            .setLanguage("fr")
            .setRegion("BL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NL")
            .setLanguage("en")
            .setRegion("NL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mgo-CM")
            .setLanguage("mgo")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ln-CG")
            .setLanguage("ln")
            .setRegion("CG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("te")
            .setLanguage("te")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ko-KR")
            .setLanguage("ko")
            .setRegion("KR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mr-IN")
            .setLanguage("mr")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ha")
            .setLanguage("ha")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sl")
            .setLanguage("sl")
            .build(),
        new Locale.Builder()
            .setLanguageTag("el-CY")
            .setLanguage("el")
            .setRegion("CY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-MX")
            .setLanguage("es")
            .setRegion("MX")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lrc-IR")
            .setLanguage("lrc")
            .setRegion("IR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gsw-FR")
            .setLanguage("gsw")
            .setRegion("FR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-HN")
            .setLanguage("es")
            .setRegion("HN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hu-HU")
            .setLanguage("hu")
            .setRegion("HU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ff-SN")
            .setLanguage("ff")
            .setRegion("SN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sbp")
            .setLanguage("sbp")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sq-MK")
            .setLanguage("sq")
            .setRegion("MK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Cyrl-BA")
            .setLanguage("sr")
            .setRegion("BA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fi")
            .setLanguage("fi")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz")
            .setLanguage("uz")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bs-Cyrl")
            .setLanguage("bs")
            .build(),
        new Locale.Builder()
            .setLanguageTag("et-EE")
            .setLanguage("et")
            .setRegion("EE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Latn")
            .setLanguage("sr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SS")
            .setLanguage("en")
            .setRegion("SS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sw")
            .setLanguage("sw")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bo-IN")
            .setLanguage("bo")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fy-NL")
            .setLanguage("fy")
            .setRegion("NL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-OM")
            .setLanguage("ar")
            .setRegion("OM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tr-CY")
            .setLanguage("tr")
            .setRegion("CY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nmg")
            .setLanguage("nmg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rm")
            .setLanguage("rm")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MG")
            .setLanguage("en")
            .setRegion("MG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-BI")
            .setLanguage("fr")
            .setRegion("BI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz-Latn-UZ")
            .setLanguage("uz")
            .setRegion("UZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bn")
            .setLanguage("bn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dua-CM")
            .setLanguage("dua")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-IT")
            .setLanguage("de")
            .setRegion("IT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lrc-IQ")
            .setLanguage("lrc")
            .setRegion("IQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vai-Vaii")
            .setLanguage("vai")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kn")
            .setLanguage("kn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-TN")
            .setLanguage("fr")
            .setRegion("TN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-RS")
            .setLanguage("sr")
            .setRegion("RS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-CH")
            .setLanguage("de")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bn-BD")
            .setLanguage("bn")
            .setRegion("BD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nnh")
            .setLanguage("nnh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-PF")
            .setLanguage("fr")
            .setRegion("PF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gu")
            .setLanguage("gu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-ZA")
            .setLanguage("en")
            .setRegion("ZA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-GQ")
            .setLanguage("pt")
            .setRegion("GQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vun-TZ")
            .setLanguage("vun")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("jmc-TZ")
            .setLanguage("jmc")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-TV")
            .setLanguage("en")
            .setRegion("TV")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lo")
            .setLanguage("lo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-FR")
            .setLanguage("fr")
            .setRegion("FR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-PN")
            .setLanguage("en")
            .setRegion("PN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MH")
            .setLanguage("en")
            .setRegion("MH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-BJ")
            .setLanguage("fr")
            .setRegion("BJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hant")
            .setLanguage("zh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cu-RU")
            .setLanguage("cu")
            .setRegion("RU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hans-HK")
            .setLanguage("zh")
            .setRegion("HK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-NL")
            .setLanguage("nl")
            .setRegion("NL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sah-RU")
            .setLanguage("sah")
            .setRegion("RU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GY")
            .setLanguage("en")
            .setRegion("GY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ps-AF")
            .setLanguage("ps")
            .setRegion("AF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bs-Latn")
            .setLanguage("bs")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ky")
            .setLanguage("ky")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mas")
            .setLanguage("mas")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dyo-SN")
            .setLanguage("dyo")
            .setRegion("SN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("os")
            .setLanguage("os")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bs-Cyrl-BA")
            .setLanguage("bs")
            .setRegion("BA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-CW")
            .setLanguage("nl")
            .setRegion("CW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-DZ")
            .setLanguage("ar")
            .setRegion("DZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sk-SK")
            .setLanguage("sk")
            .setRegion("SK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-CH")
            .setLanguage("pt")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-GQ")
            .setLanguage("fr")
            .setRegion("GQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ff-CM")
            .setLanguage("ff")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("am")
            .setLanguage("am")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NG")
            .setLanguage("en")
            .setRegion("NG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CI")
            .setLanguage("fr")
            .setRegion("CI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ki-KE")
            .setLanguage("ki")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-PK")
            .setLanguage("en")
            .setRegion("PK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-CN")
            .setLanguage("zh")
            .setRegion("CN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-LC")
            .setLanguage("en")
            .setRegion("LC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rw")
            .setLanguage("rw")
            .build(),
        new Locale.Builder()
            .setLanguageTag("brx-IN")
            .setLanguage("brx")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("wo-SN")
            .setLanguage("wo")
            .setRegion("SN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("he")
            .setLanguage("iw")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gv-IM")
            .setLanguage("gv")
            .setRegion("IM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mk-MK")
            .setLanguage("mk")
            .setRegion("MK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-TT")
            .setLanguage("en")
            .setRegion("TT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dav")
            .setLanguage("dav")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sl-SI")
            .setLanguage("sl")
            .setRegion("SI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-HT")
            .setLanguage("fr")
            .setRegion("HT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("te-IN")
            .setLanguage("te")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-SX")
            .setLanguage("nl")
            .setRegion("SX")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lrc")
            .setLanguage("lrc")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ses")
            .setLanguage("ses")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ce")
            .setLanguage("ce")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CG")
            .setLanguage("fr")
            .setRegion("CG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-BE")
            .setLanguage("fr")
            .setRegion("BE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("jgo-CM")
            .setLanguage("jgo")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mt-MT")
            .setLanguage("mt")
            .setRegion("MT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-VE")
            .setLanguage("es")
            .setRegion("VE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mg")
            .setLanguage("mg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mr")
            .setLanguage("mr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mer-KE")
            .setLanguage("mer")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ko")
            .setLanguage("ko")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nds-NL")
            .setLanguage("nds")
            .setRegion("NL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BM")
            .setLanguage("en")
            .setRegion("BM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nb-NO")
            .setLanguage("nb")
            .setRegion("NO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ak")
            .setLanguage("ak")
            .build(),
        new Locale.Builder()
            .setLanguageTag("seh")
            .setLanguage("seh")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kde")
            .setLanguage("kde")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dz")
            .setLanguage("dz")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kea-CV")
            .setLanguage("kea")
            .setRegion("CV")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mgo")
            .setLanguage("mgo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vi-VN")
            .setLanguage("vi")
            .setRegion("VN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-VU")
            .setLanguage("en")
            .setRegion("VU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-US")
            .setLanguage("en")
            .setRegion("US")
            .build(),
        new Locale.Builder()
            .setLanguageTag("to-TO")
            .setLanguage("to")
            .setRegion("TO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mfe-MU")
            .setLanguage("mfe")
            .setRegion("MU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("seh-MZ")
            .setLanguage("seh")
            .setRegion("MZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-BF")
            .setLanguage("fr")
            .setRegion("BF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pa-Guru")
            .setLanguage("pa")
            .build(),
        new Locale.Builder()
            .setLanguageTag("it-SM")
            .setLanguage("it")
            .setRegion("SM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-YT")
            .setLanguage("fr")
            .setRegion("YT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gu-IN")
            .setLanguage("gu")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ii-CN")
            .setLanguage("ii")
            .setRegion("CN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pa-Arab-PK")
            .setLanguage("pa")
            .setRegion("PK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ast")
            .setLanguage("ast")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-RE")
            .setLanguage("fr")
            .setRegion("RE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fi-FI")
            .setLanguage("fi")
            .setRegion("FI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yue-Hans")
            .setLanguage("yue")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ca-FR")
            .setLanguage("ca")
            .setRegion("FR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Latn-BA")
            .setLanguage("sr")
            .setRegion("BA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bn-IN")
            .setLanguage("bn")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-GP")
            .setLanguage("fr")
            .setRegion("GP")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pa")
            .setLanguage("pa")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zgh-MA")
            .setLanguage("zgh")
            .setRegion("MA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-DJ")
            .setLanguage("fr")
            .setRegion("DJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rn")
            .setLanguage("rn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tg")
            .setLanguage("tg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rwk")
            .setLanguage("rwk")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uk-UA")
            .setLanguage("uk")
            .setRegion("UA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NF")
            .setLanguage("en")
            .setRegion("NF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CH")
            .setLanguage("fr")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hu")
            .setLanguage("hu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("twq")
            .setLanguage("twq")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ha-GH")
            .setLanguage("ha")
            .setRegion("GH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Cyrl-XK")
            .setLanguage("sr")
            .setRegion("XK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bm")
            .setLanguage("bm")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-SS")
            .setLanguage("ar")
            .setRegion("SS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GU")
            .setLanguage("en")
            .setRegion("GU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-AW")
            .setLanguage("nl")
            .setRegion("AW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-BE")
            .setLanguage("de")
            .setRegion("BE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-AI")
            .setLanguage("en")
            .setRegion("AI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CM")
            .setLanguage("en")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("xog-UG")
            .setLanguage("xog")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cs-CZ")
            .setLanguage("cs")
            .setRegion("CZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ca-ES")
            .setLanguage("ca")
            .setRegion("ES")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tr")
            .setLanguage("tr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cgg")
            .setLanguage("cgg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rm-CH")
            .setLanguage("rm")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nyn-UG")
            .setLanguage("nyn")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru-MD")
            .setLanguage("ru")
            .setRegion("MD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ms-MY")
            .setLanguage("ms")
            .setRegion("MY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ta-LK")
            .setLanguage("ta")
            .setRegion("LK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ksf")
            .setLanguage("ksf")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-TO")
            .setLanguage("en")
            .setRegion("TO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cy")
            .setLanguage("cy")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-PG")
            .setLanguage("en")
            .setRegion("PG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CF")
            .setLanguage("fr")
            .setRegion("CF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-TL")
            .setLanguage("pt")
            .setRegion("TL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr")
            .setLanguage("fr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sq")
            .setLanguage("sq")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tg-TJ")
            .setLanguage("tg")
            .setRegion("TJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-ER")
            .setLanguage("en")
            .setRegion("ER")
            .build(),
        new Locale.Builder()
            .setLanguageTag("qu-PE")
            .setLanguage("qu")
            .setRegion("PE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-BA")
            .setLanguage("sr")
            .setRegion("BA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-PY")
            .setLanguage("es")
            .setRegion("PY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de")
            .setLanguage("de")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kok-IN")
            .setLanguage("kok")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-EC")
            .setLanguage("es")
            .setRegion("EC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lg-UG")
            .setLanguage("lg")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zu-ZA")
            .setLanguage("zu")
            .setRegion("ZA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-TG")
            .setLanguage("fr")
            .setRegion("TG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Latn-XK")
            .setLanguage("sr")
            .setRegion("XK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-PH")
            .setLanguage("en")
            .setRegion("PH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ig-NG")
            .setLanguage("ig")
            .setRegion("NG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-GN")
            .setLanguage("fr")
            .setRegion("GN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("prg-001")
            .setLanguage("prg")
            .setRegion("001")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cgg-UG")
            .setLanguage("cgg")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hans-MO")
            .setLanguage("zh")
            .setRegion("MO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ksh-DE")
            .setLanguage("ksh")
            .setRegion("DE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lg")
            .setLanguage("lg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru-RU")
            .setLanguage("ru")
            .setRegion("RU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("se-FI")
            .setLanguage("se")
            .setRegion("FI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ff")
            .setLanguage("ff")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-DM")
            .setLanguage("en")
            .setRegion("DM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CK")
            .setLanguage("en")
            .setRegion("CK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sd")
            .setLanguage("sd")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-MA")
            .setLanguage("ar")
            .setRegion("MA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BI")
            .setLanguage("en")
            .setRegion("BI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ga-IE")
            .setLanguage("ga")
            .setRegion("IE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-AG")
            .setLanguage("en")
            .setRegion("AG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-TD")
            .setLanguage("fr")
            .setRegion("TD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-WS")
            .setLanguage("en")
            .setRegion("WS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-LU")
            .setLanguage("fr")
            .setRegion("LU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ebu-KE")
            .setLanguage("ebu")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bem-ZM")
            .setLanguage("bem")
            .setRegion("ZM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("xog")
            .setLanguage("xog")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ewo-CM")
            .setLanguage("ewo")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CD")
            .setLanguage("fr")
            .setRegion("CD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("so")
            .setLanguage("so")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rn-BI")
            .setLanguage("rn")
            .setRegion("BI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NA")
            .setLanguage("en")
            .setRegion("NA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-ER")
            .setLanguage("ar")
            .setRegion("ER")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kab")
            .setLanguage("kab")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ms")
            .setLanguage("ms")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nus")
            .setLanguage("nus")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sn-ZW")
            .setLanguage("sn")
            .setRegion("ZW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("prg")
            .setLanguage("prg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("he-IL")
            .setLanguage("iw")
            .setRegion("IL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ug")
            .setLanguage("ug")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-EA")
            .setLanguage("es")
            .setRegion("EA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hi")
            .setLanguage("hi")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-SC")
            .setLanguage("fr")
            .setRegion("SC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ca-IT")
            .setLanguage("ca")
            .setRegion("IT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lag-TZ")
            .setLanguage("lag")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SL")
            .setLanguage("en")
            .setRegion("SL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("teo-KE")
            .setLanguage("teo")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ca-AD")
            .setLanguage("ca")
            .setRegion("AD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("no-NO")
            .setLanguage("no")
            .setRegion("NO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hant-MO")
            .setLanguage("zh")
            .setRegion("MO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SH")
            .setLanguage("en")
            .setRegion("SH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vai")
            .setLanguage("vai")
            .build(),
        new Locale.Builder()
            .setLanguageTag("qu-BO")
            .setLanguage("qu")
            .setRegion("BO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("haw-US")
            .setLanguage("haw")
            .setRegion("US")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vi")
            .setLanguage("vi")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-CA")
            .setLanguage("fr")
            .setRegion("CA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sq-XK")
            .setLanguage("sq")
            .setRegion("XK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dyo")
            .setLanguage("dyo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-LU")
            .setLanguage("de")
            .setRegion("LU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-KY")
            .setLanguage("en")
            .setRegion("KY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mt")
            .setLanguage("mt")
            .build(),
        new Locale.Builder()
            .setLanguageTag("it-CH")
            .setLanguage("it")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-DE")
            .setLanguage("de")
            .setRegion("DE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("si-LK")
            .setLanguage("si")
            .setRegion("LK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("luo-KE")
            .setLanguage("luo")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-DK")
            .setLanguage("en")
            .setRegion("DK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yav")
            .setLanguage("yav")
            .build(),
        new Locale.Builder()
            .setLanguageTag("so-DJ")
            .setLanguage("so")
            .setRegion("DJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("eo")
            .setLanguage("eo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("it-IT")
            .setLanguage("it")
            .setRegion("IT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lt-LT")
            .setLanguage("lt")
            .setRegion("LT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kam")
            .setLanguage("kam")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-SO")
            .setLanguage("ar")
            .setRegion("SO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-ZW")
            .setLanguage("en")
            .setRegion("ZW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ro")
            .setLanguage("ro")
            .build(),
        new Locale.Builder()
            .setLanguageTag("eo-001")
            .setLanguage("eo")
            .setRegion("001")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ee")
            .setLanguage("ee")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-UM")
            .setLanguage("en")
            .setRegion("UM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nn-NO")
            .setLanguage("nn")
            .setRegion("NO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MU")
            .setLanguage("fr")
            .setRegion("MU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("se-SE")
            .setLanguage("se")
            .setRegion("SE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pl")
            .setLanguage("pl")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-TK")
            .setLanguage("en")
            .setRegion("TK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SI")
            .setLanguage("en")
            .setRegion("SI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mua-CM")
            .setLanguage("mua")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ur")
            .setLanguage("ur")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz-Arab")
            .setLanguage("uz")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vai-Vaii-LR")
            .setLanguage("vai")
            .setRegion("LR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("saq-KE")
            .setLanguage("saq")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("se")
            .setLanguage("se")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-GW")
            .setLanguage("pt")
            .setRegion("GW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lo-LA")
            .setLanguage("lo")
            .setRegion("LA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("chr")
            .setLanguage("chr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("af-ZA")
            .setLanguage("af")
            .setRegion("ZA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-LB")
            .setLanguage("ar")
            .setRegion("LB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ms-SG")
            .setLanguage("ms")
            .setRegion("SG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ee-TG")
            .setLanguage("ee")
            .setRegion("TG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ln-AO")
            .setLanguage("ln")
            .setRegion("AO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ff-GN")
            .setLanguage("ff")
            .setRegion("GN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("be-BY")
            .setLanguage("be")
            .setRegion("BY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yue-Hant")
            .setLanguage("yue")
            .build(),
        new Locale.Builder()
            .setLanguageTag("id-ID")
            .setLanguage("in")
            .setRegion("ID")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-BZ")
            .setLanguage("es")
            .setRegion("BZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-AE")
            .setLanguage("ar")
            .setRegion("AE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hr-HR")
            .setLanguage("hr")
            .setRegion("HR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("luy")
            .setLanguage("luy")
            .build(),
        new Locale.Builder()
            .setLanguageTag("as")
            .setLanguage("as")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rof-TZ")
            .setLanguage("rof")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("it")
            .setLanguage("it")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-CV")
            .setLanguage("pt")
            .setRegion("CV")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ks-IN")
            .setLanguage("ks")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uk")
            .setLanguage("uk")
            .build(),
        new Locale.Builder()
            .setLanguageTag("my-MM")
            .setLanguage("my")
            .setRegion("MM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ur-PK")
            .setLanguage("ur")
            .setRegion("PK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mn-MN")
            .setLanguage("mn")
            .setRegion("MN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-FM")
            .setLanguage("en")
            .setRegion("FM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("da-DK")
            .setLanguage("da")
            .setRegion("DK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-PR")
            .setLanguage("es")
            .setRegion("PR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("wae-CH")
            .setLanguage("wae")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mzn")
            .setLanguage("mzn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BE")
            .setLanguage("en")
            .setRegion("BE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ii")
            .setLanguage("ii")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tt")
            .setLanguage("tt")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-WF")
            .setLanguage("fr")
            .setRegion("WF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru-BY")
            .setLanguage("ru")
            .setRegion("BY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mzn-IR")
            .setLanguage("mzn")
            .setRegion("IR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("naq")
            .setLanguage("naq")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fo-DK")
            .setLanguage("fo")
            .setRegion("DK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SG")
            .setLanguage("en")
            .setRegion("SG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ee-GH")
            .setLanguage("ee")
            .setRegion("GH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-BH")
            .setLanguage("ar")
            .setRegion("BH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kln-KE")
            .setLanguage("kln")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("tzm")
            .setLanguage("tzm")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fur")
            .setLanguage("fur")
            .build(),
        new Locale.Builder()
            .setLanguageTag("om")
            .setLanguage("om")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hi-IN")
            .setLanguage("hi")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CH")
            .setLanguage("en")
            .setRegion("CH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("asa")
            .setLanguage("asa")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yo-BJ")
            .setLanguage("yo")
            .setRegion("BJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fo-FO")
            .setLanguage("fo")
            .setRegion("FO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ast-ES")
            .setLanguage("ast")
            .setRegion("ES")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-KM")
            .setLanguage("fr")
            .setRegion("KM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bez-TZ")
            .setLanguage("bez")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MQ")
            .setLanguage("fr")
            .setRegion("MQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SD")
            .setLanguage("en")
            .setRegion("SD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-AR")
            .setLanguage("es")
            .setRegion("AR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MY")
            .setLanguage("en")
            .setRegion("MY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ja-JP-u-ca-japanese-x-lvariant-JP")
            .setLanguage("ja")
            .setRegion("JP")
            .setExtension('u', "ca-japanese").build(),
        new Locale.Builder()
            .setLanguageTag("es-SV")
            .setLanguage("es")
            .setRegion("SV")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-BR")
            .setLanguage("pt")
            .setRegion("BR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ml-IN")
            .setLanguage("ml")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sbp-TZ")
            .setLanguage("sbp")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fil")
            .setLanguage("fil")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-FK")
            .setLanguage("en")
            .setRegion("FK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz-Cyrl")
            .setLanguage("uz")
            .build(),
        new Locale.Builder()
            .setLanguageTag("is-IS")
            .setLanguage("is")
            .setRegion("IS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yue-Hant-HK")
            .setLanguage("yue")
            .setRegion("HK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hy-AM")
            .setLanguage("hy")
            .setRegion("AM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GM")
            .setLanguage("en")
            .setRegion("GM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-DG")
            .setLanguage("en")
            .setRegion("DG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fo")
            .setLanguage("fo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ne-NP")
            .setLanguage("ne")
            .setRegion("NP")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-ST")
            .setLanguage("pt")
            .setRegion("ST")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hr")
            .setLanguage("hr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ak-GH")
            .setLanguage("ak")
            .setRegion("GH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lt")
            .setLanguage("lt")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz-Arab-AF")
            .setLanguage("uz")
            .setRegion("AF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fur-IT")
            .setLanguage("fur")
            .setRegion("IT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ta-IN")
            .setLanguage("ta")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ccp")
            .setLanguage("ccp")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SE")
            .setLanguage("en")
            .setRegion("SE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-GF")
            .setLanguage("fr")
            .setRegion("GF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lkt")
            .setLanguage("lkt")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hans-CN")
            .setLanguage("zh")
            .setRegion("CN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("is")
            .setLanguage("is")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-419")
            .setLanguage("es")
            .setRegion("419")
            .build(),
        new Locale.Builder()
            .setLanguageTag("si")
            .setLanguage("si")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-AO")
            .setLanguage("pt")
            .setRegion("AO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-001")
            .setLanguage("en")
            .setRegion("001")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en")
            .setLanguage("en")
            .build(),
        new Locale.Builder()
            .setLanguageTag("guz-KE")
            .setLanguage("guz")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gsw-LI")
            .setLanguage("gsw")
            .setRegion("LI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ccp-BD")
            .setLanguage("ccp")
            .setRegion("BD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-IC")
            .setLanguage("es")
            .setRegion("IC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ca")
            .setLanguage("ca")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru-KG")
            .setLanguage("ru")
            .setRegion("KG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-MR")
            .setLanguage("fr")
            .setRegion("MR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-TN")
            .setLanguage("ar")
            .setRegion("TN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ks")
            .setLanguage("ks")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-Hant-TW")
            .setLanguage("zh")
            .setRegion("TW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bm-ML")
            .setLanguage("bm")
            .setRegion("ML")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kw-GB")
            .setLanguage("kw")
            .setRegion("GB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ug-CN")
            .setLanguage("ug")
            .setRegion("CN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("as-IN")
            .setLanguage("as")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-BR")
            .setLanguage("es")
            .setRegion("BR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("zh-HK")
            .setLanguage("zh")
            .setRegion("HK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("khq-ML")
            .setLanguage("khq")
            .setRegion("ML")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sw-KE")
            .setLanguage("sw")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SB")
            .setLanguage("en")
            .setRegion("SB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("rw-RW")
            .setLanguage("rw")
            .setRegion("RW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("chr-US")
            .setLanguage("chr")
            .setRegion("US")
            .build(),
        new Locale.Builder()
            .setLanguageTag("th-TH")
            .setLanguage("th")
            .setRegion("TH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("shi-Tfng-MA")
            .setLanguage("shi")
            .setRegion("MA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-IQ")
            .setLanguage("ar")
            .setRegion("IQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nyn")
            .setLanguage("nyn")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yue")
            .setLanguage("yue")
            .build(),
        new Locale.Builder()
            .setLanguageTag("jmc")
            .setLanguage("jmc")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MW")
            .setLanguage("en")
            .setRegion("MW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("naq-NA")
            .setLanguage("naq")
            .setRegion("NA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mk")
            .setLanguage("mk")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-IO")
            .setLanguage("en")
            .setRegion("IO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-QA")
            .setLanguage("ar")
            .setRegion("QA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-DE")
            .setLanguage("en")
            .setRegion("DE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pa-Arab")
            .setLanguage("pa")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CC")
            .setLanguage("en")
            .setRegion("CC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bs")
            .setLanguage("bs")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ro-MD")
            .setLanguage("ro")
            .setRegion("MD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-FI")
            .setLanguage("en")
            .setRegion("FI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("pt-PT")
            .setLanguage("pt")
            .setRegion("PT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fy")
            .setLanguage("fy")
            .build(),
        new Locale.Builder()
            .setLanguageTag("az-Cyrl-AZ")
            .setLanguage("az")
            .setRegion("AZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("th")
            .setLanguage("th")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dav-KE")
            .setLanguage("dav")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ckb-IQ")
            .setLanguage("ckb")
            .setRegion("IQ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("shi-Latn-MA")
            .setLanguage("shi")
            .setRegion("MA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-CU")
            .setLanguage("es")
            .setRegion("CU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar")
            .setLanguage("ar")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-SC")
            .setLanguage("en")
            .setRegion("SC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-VI")
            .setLanguage("en")
            .setRegion("VI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("haw")
            .setLanguage("haw")
            .build(),
        new Locale.Builder()
            .setLanguageTag("eu-ES")
            .setLanguage("eu")
            .setRegion("ES")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-UG")
            .setLanguage("en")
            .setRegion("UG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("dje")
            .setLanguage("dje")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-NZ")
            .setLanguage("en")
            .setRegion("NZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bas")
            .setLanguage("bas")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-UY")
            .setLanguage("es")
            .setRegion("UY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mas-KE")
            .setLanguage("mas")
            .setRegion("KE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ru-UA")
            .setLanguage("ru")
            .setRegion("UA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sg-CF")
            .setLanguage("sg")
            .setRegion("CF")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yav-CM")
            .setLanguage("yav")
            .setRegion("CM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("uz-Latn")
            .setLanguage("uz")
            .build(),
        new Locale.Builder()
            .setLanguageTag("el-GR")
            .setLanguage("el")
            .setRegion("GR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sg")
            .setLanguage("sg")
            .build(),
        new Locale.Builder()
            .setLanguageTag("da-GL")
            .setLanguage("da")
            .setRegion("GL")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-FJ")
            .setLanguage("en")
            .setRegion("FJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-LI")
            .setLanguage("de")
            .setRegion("LI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-BB")
            .setLanguage("en")
            .setRegion("BB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("km-KH")
            .setLanguage("km")
            .setRegion("KH")
            .build(),
        new Locale.Builder()
            .setLanguageTag("smn-FI")
            .setLanguage("smn")
            .setRegion("FI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("hr-BA")
            .setLanguage("hr")
            .setRegion("BA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("de-AT")
            .setLanguage("de")
            .setRegion("AT")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ckb-IR")
            .setLanguage("ckb")
            .setRegion("IR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl")
            .setLanguage("nl")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lu-CD")
            .setLanguage("lu")
            .setRegion("CD")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ca-ES-VALENCIA")
            .setLanguage("ca")
            .setRegion("ES")
            .setVariant("VALENCIA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-001")
            .setLanguage("ar")
            .setRegion("001")
            .build(),
        new Locale.Builder()
            .setLanguageTag("so-SO")
            .setLanguage("so")
            .setRegion("SO")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lv-LV")
            .setLanguage("lv")
            .setRegion("LV")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ckb")
            .setLanguage("ckb")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-CR")
            .setLanguage("es")
            .setRegion("CR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-GA")
            .setLanguage("fr")
            .setRegion("GA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-KW")
            .setLanguage("ar")
            .setRegion("KW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-LY")
            .setLanguage("ar")
            .setRegion("LY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr")
            .setLanguage("sr")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Cyrl-RS")
            .setLanguage("sr")
            .setRegion("RS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("bem")
            .setLanguage("bem")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-MU")
            .setLanguage("en")
            .setRegion("MU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("da")
            .setLanguage("da")
            .build(),
        new Locale.Builder()
            .setLanguageTag("wae")
            .setLanguage("wae")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gl-ES")
            .setLanguage("gl")
            .setRegion("ES")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-IM")
            .setLanguage("en")
            .setRegion("IM")
            .build(),
        new Locale.Builder()
            .setLanguageTag("az-Latn-AZ")
            .setLanguage("az")
            .setRegion("AZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-LS")
            .setLanguage("en")
            .setRegion("LS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ig")
            .setLanguage("ig")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-HK")
            .setLanguage("en")
            .setRegion("HK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-GI")
            .setLanguage("en")
            .setRegion("GI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ce-RU")
            .setLanguage("ce")
            .setRegion("RU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("gd")
            .setLanguage("gd")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-CA")
            .setLanguage("en")
            .setRegion("CA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ka-GE")
            .setLanguage("ka")
            .setRegion("GE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-SY")
            .setLanguage("fr")
            .setRegion("SY")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sw-TZ")
            .setLanguage("sw")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("fr-RW")
            .setLanguage("fr")
            .setRegion("RW")
            .build(),
        new Locale.Builder()
            .setLanguageTag("so-ET")
            .setLanguage("so")
            .setRegion("ET")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nl-BE")
            .setLanguage("nl")
            .setRegion("BE")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ar-DJ")
            .setLanguage("ar")
            .setRegion("DJ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mg-MG")
            .setLanguage("mg")
            .setRegion("MG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cy-GB")
            .setLanguage("cy")
            .setRegion("GB")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-VG")
            .setLanguage("en")
            .setRegion("VG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("cu")
            .setLanguage("cu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("os-RU")
            .setLanguage("os")
            .setRegion("RU")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sr-Latn-RS")
            .setLanguage("sr")
            .setRegion("RS")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-TC")
            .setLanguage("en")
            .setRegion("TC")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ky-KG")
            .setLanguage("ky")
            .setRegion("KG")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sv-AX")
            .setLanguage("sv")
            .setRegion("AX")
            .build(),
        new Locale.Builder()
            .setLanguageTag("af-NA")
            .setLanguage("af")
            .setRegion("NA")
            .build(),
        new Locale.Builder()
            .setLanguageTag("vun")
            .setLanguage("vun")
            .build(),
        new Locale.Builder()
            .setLanguageTag("en-IN")
            .setLanguage("en")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("lu")
            .setLanguage("lu")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ki")
            .setLanguage("ki")
            .build(),
        new Locale.Builder()
            .setLanguageTag("yo")
            .setLanguage("yo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("es-NI")
            .setLanguage("es")
            .setRegion("NI")
            .build(),
        new Locale.Builder()
            .setLanguageTag("nb")
            .setLanguage("nb")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ff-MR")
            .setLanguage("ff")
            .setRegion("MR")
            .build(),
        new Locale.Builder()
            .setLanguageTag("sd-PK")
            .setLanguage("sd")
            .setRegion("PK")
            .build(),
        new Locale.Builder()
            .setLanguageTag("mas-TZ")
            .setLanguage("mas")
            .setRegion("TZ")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ti")
            .setLanguage("ti")
            .build(),
        new Locale.Builder()
            .setLanguageTag("kok")
            .setLanguage("kok")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ewo")
            .setLanguage("ewo")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ms-BN")
            .setLanguage("ms")
            .setRegion("BN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("ccp-IN")
            .setLanguage("ccp")
            .setRegion("IN")
            .build(),
        new Locale.Builder()
            .setLanguageTag("br-FR")
            .setLanguage("br")
            .setRegion("FR")
            .build());
  }
}
