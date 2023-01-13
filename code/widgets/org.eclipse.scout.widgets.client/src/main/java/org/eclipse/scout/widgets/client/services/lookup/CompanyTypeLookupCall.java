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
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("3e0f598a-c0c3-406d-ad52-8cbe4029cf7b")
public class CompanyTypeLookupCall extends LocalLookupCall<Long> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<ILookupRow<Long>> execCreateLookupRows() {
    ArrayList<ILookupRow<Long>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(1L, "Customer"));
    rows.add(new LookupRow<>(2L, "Supplier"));
    rows.add(new LookupRow<>(3L, "Other"));
    return rows;
  }
}
