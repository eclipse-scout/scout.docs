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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
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
  public List<? extends ILookupRow<Locale>> getDataByKey(ILookupCall<Locale> call) throws ProcessingException {
    List<? extends ILookupRow<Locale>> results = Collections.emptyList();
    for (ILookupRow<Locale> row : m_localLookupCall.execCreateLookupRows()) {
      if (row.getKey().equals(call.getKey())) {
        results = Collections.singletonList(row);
        break;
      }
    }
    sleepSafe(250);
    return results;
  }

  @Override
  public List<? extends ILookupRow<Locale>> getDataByText(ILookupCall<Locale> call) throws ProcessingException {
    List<ILookupRow<Locale>> results = new ArrayList<>();
    String callText = StringUtility.emptyIfNull(call.getText()).toLowerCase();
    for (ILookupRow<Locale> row : m_localLookupCall.execCreateLookupRows()) {
      String rowText = StringUtility.emptyIfNull(row.getText()).toLowerCase();
      if (rowText.matches(StringUtility.toRegExPattern(callText))) {
        results.add(row);
      }
    }
    sleepSafe(1500);
    return results;
  }

  @Override
  public List<? extends ILookupRow<Locale>> getDataByAll(ILookupCall<Locale> call) throws ProcessingException {
    sleepSafe(1500);
    return m_localLookupCall.execCreateLookupRows();
  }

  @Override
  public List<? extends ILookupRow<Locale>> getDataByRec(ILookupCall<Locale> call) throws ProcessingException {
    sleepSafe(1500);
    return m_localLookupCall.execCreateLookupRows();
  }

  private void sleepSafe(long millis) {
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
      // NOP
    }
  }

}
