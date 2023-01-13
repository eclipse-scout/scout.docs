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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

/**
 * This class is used to fake a remote lookup-service (even though the widgets-app does not have a server). It simply
 * calls the local locale-lookup-call and adds some sleeps to delay calls to the service.
 */
public class RemoteLocaleLookupService implements ILookupService<Locale> {

  AbstractLocaleLookupCall m_localLookupCall = new LocaleLookupCall();

  @Override
  public List<? extends ILookupRow<Locale>> getDataByKey(ILookupCall<Locale> call) {
    List<? extends ILookupRow<Locale>> results = Collections.emptyList();
    for (ILookupRow<Locale> row : m_localLookupCall.execCreateLookupRows()) {
      if (row.getKey().equals(call.getKey())) {
        results = Collections.singletonList(row);
        break;
      }
    }

    SleepUtil.sleepSafe(250, TimeUnit.MILLISECONDS);

    return results;
  }

  @Override
  public List<? extends ILookupRow<Locale>> getDataByText(ILookupCall<Locale> call) {
    List<ILookupRow<Locale>> results = new ArrayList<>();
    String callText = StringUtility.emptyIfNull(call.getText()).toLowerCase();
    for (ILookupRow<Locale> row : m_localLookupCall.execCreateLookupRows()) {
      String rowText = StringUtility.emptyIfNull(row.getText()).toLowerCase();
      if (rowText.matches(StringUtility.toRegExPattern(callText))) {
        results.add(row);
      }
    }

    SleepUtil.sleepSafe(1500, TimeUnit.MILLISECONDS);
    return results;
  }

  @Override
  public List<? extends ILookupRow<Locale>> getDataByAll(ILookupCall<Locale> call) {
    SleepUtil.sleepSafe(1500, TimeUnit.MILLISECONDS);
    return m_localLookupCall.execCreateLookupRows();
  }

  @Override
  public List<? extends ILookupRow<Locale>> getDataByRec(ILookupCall<Locale> call) {
    SleepUtil.sleepSafe(1500, TimeUnit.MILLISECONDS);
    return m_localLookupCall.execCreateLookupRows();
  }
}
