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
import java.util.List;
import java.util.TimeZone;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class TimezonesLookupCall extends LocalLookupCall {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow> execCreateLookupRows() throws ProcessingException {
    ArrayList<LookupRow> rows = new ArrayList<LookupRow>();
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-12:00"), "-12:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-11:00"), "-11:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-10:00"), "-10:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-09:00"), "-09:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-08:00"), "-08:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-07:00"), "-07:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-06:00"), "-06:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-05:00"), "-05:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-04:00"), "-04:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-03:00"), "-03:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-02:00"), "-02:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT-01:00"), "-01:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+00:00"), "+00:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+01:00"), "+01:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+02:00"), "+02:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+03:00"), "+03:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+03:30"), "+03:30"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+04:00"), "+04:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+04:30"), "+04:30"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+05:00"), "+05:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+05:30"), "+05:30"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+05:45"), "+05:45"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+06:00"), "+06:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+06:30"), "+06:30"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+07:00"), "+07:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+08:00"), "+08:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+09:00"), "+09:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+09:30"), "+09:30"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+10:00"), "+10:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+11:00"), "+11:00"));
    rows.add(new LookupRow(TimeZone.getTimeZone("GMT+12:00"), "+12:00"));
    return rows;
  }
}
