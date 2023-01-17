/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

//tag::all[]
@ClassId("37736ea5-e861-43d8-a6bc-144dad3c208f")
public class CountryLookupCall extends LocalLookupCall<String> { // <1>

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() { // <2>
    List<LookupRow<String>> rows = new ArrayList<>();

    for (String countryCode : Locale.getISOCountries()) {
      Locale country = new Locale("", countryCode);
      rows.add(new LookupRow<>(countryCode, country.getDisplayCountry())); // <3>
    }

    return rows;
  }
}
//end::all[]
