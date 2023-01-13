/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("cfe2058f-bc4b-411e-8ff6-61eb8e7714e3")
public class StatusSeverityLookupCall extends LocalLookupCall<Integer> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
    List<ILookupRow<Integer>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(IStatus.OK, "ok"));
    rows.add(new LookupRow<>(IStatus.INFO, "info"));
    rows.add(new LookupRow<>(IStatus.WARNING, "warning"));
    rows.add(new LookupRow<>(IStatus.ERROR, "error"));
    return rows;
  }
}
