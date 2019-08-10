/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.client.common;

import java.util.Locale;

import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MapHelper {

  private static final Logger LOG = LoggerFactory.getLogger(MapHelper.class);

  public static final String GOOGLE_MAPS_BASE_URL = "https://www.google.com/maps/search/?api=1";

  public void showMapInNewWindow(String country, String city, String street) {
    String address = StringUtility.join(", ",
        StringUtility.hasText(country) ? (new Locale("", country.trim())).getDisplayCountry() : null,
        StringUtility.hasText(city) ? city.trim() : null,
        StringUtility.hasText(street) ? street.trim() : null);

    // See https://developers.google.com/maps/documentation/urls/guide
    String url = addressToUrl(address);
    LOG.info("Map URL: {}", url);

    IDesktop desktop = IDesktop.CURRENT.get();
    if (url != null && desktop != null) {
      desktop.openUri(url, OpenUriAction.NEW_WINDOW);
    }
  }

  public String addressToUrl(String address) {
    String url = GOOGLE_MAPS_BASE_URL;
    if (StringUtility.hasText(url)) {
      url += "&query=" + IOUtility.urlEncode(address);
    }
    return url;
  }
}
