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
package org.eclipsescout.demo.widgets.shared;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import org.eclipse.scout.commons.annotations.Replace;
import org.eclipse.scout.rt.platform.util.DateFormatProvider;

@Replace
public class WidgetsDateFormatProvider extends DateFormatProvider {

  private CustomDateFormatProvider m_customDateFormatProvider;
  private Locale[] m_availableLocales;

  public WidgetsDateFormatProvider() {
    m_customDateFormatProvider = new CustomDateFormatProvider();
    HashSet<Locale> availableLocales = new HashSet<Locale>();
    availableLocales.addAll(Arrays.asList(m_customDateFormatProvider.getAvailableLocales()));
    availableLocales.addAll(Arrays.asList(super.getAvailableLocales()));
    m_availableLocales = availableLocales.toArray(new Locale[availableLocales.size()]);
  }

  @Override
  public Locale[] getAvailableLocales() {
    return m_availableLocales;
  }

  @Override
  public DateFormat getTimeInstance(int style, Locale locale) {
    DateFormat df = m_customDateFormatProvider.getTimeInstance(style, locale);
    if (df != null) {
      return df;
    }
    return super.getTimeInstance(style, locale);
  }

  @Override
  public DateFormat getDateInstance(int style, Locale locale) {
    DateFormat df = m_customDateFormatProvider.getDateInstance(style, locale);
    if (df != null) {
      return df;
    }
    return super.getDateInstance(style, locale);
  }

  @Override
  public DateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
    DateFormat df = m_customDateFormatProvider.getDateTimeInstance(dateStyle, timeStyle, locale);
    if (df != null) {
      return df;
    }
    return super.getDateTimeInstance(dateStyle, timeStyle, locale);
  }

}
