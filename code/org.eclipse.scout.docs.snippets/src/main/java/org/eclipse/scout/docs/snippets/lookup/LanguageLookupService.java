/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.docs.snippets.ILanguageLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

@SuppressWarnings("all")
public class LanguageLookupService extends AbstractLookupService<String> implements ILanguageLookupService {

  @Override
  public List<? extends ILookupRow<String>> getDataByKey(ILookupCall<String> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByText(ILookupCall<String> call) {
    return null;
  }

  @SuppressWarnings("unused")
  //tag::getDataByAll[]
  @Override
  public List<? extends ILookupRow<String>> getDataByAll(ILookupCall<String> call) {
    LanguageLookupCall c = (LanguageLookupCall) call;
    Date validityFrom = c.getValidityFrom();
    Date validityTo = c.getValidityTo();

    List<? extends ILookupRow<String>> result = new ArrayList<>();
    //compute result: corresponding lookup rows (depending on validityFrom and validityTo).
    return result;
  }
  //end::getDataByAll[]

  @Override
  public List<? extends ILookupRow<String>> getDataByRec(ILookupCall<String> call) {
    return null;
  }
}
