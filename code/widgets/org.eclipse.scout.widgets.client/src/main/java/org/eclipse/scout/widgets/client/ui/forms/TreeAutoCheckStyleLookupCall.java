/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.basic.tree.AutoCheckStyle;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("7e366458-a7ad-4587-a076-320fcb289eff")
public class TreeAutoCheckStyleLookupCall extends LocalLookupCall<AutoCheckStyle> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<AutoCheckStyle>> execCreateLookupRows() {
    List<ILookupRow<AutoCheckStyle>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(AutoCheckStyle.NONE, "NONE"));
    rows.add(new LookupRow<>(AutoCheckStyle.AUTO_CHECK_CHILD_NODES, "AUTO_CHECK_CHILD_NODES"));
    rows.add(new LookupRow<>(AutoCheckStyle.SYNC_CHILD_AND_PARENT_STATE, "SYNC_CHILD_AND_PARENT_STATE"));
    return rows;
  }
}
