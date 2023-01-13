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

import org.eclipse.scout.rt.client.ui.basic.tree.CheckableStyle;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("01517465-c24a-4b02-b395-6961f9e19c5f")
public class TreeCheckableStyleLookupCall extends LocalLookupCall<CheckableStyle> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<CheckableStyle>> execCreateLookupRows() {
    List<ILookupRow<CheckableStyle>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(CheckableStyle.CHECKBOX, "CHECKBOX"));
    rows.add(new LookupRow<>(CheckableStyle.CHECKBOX_TREE_NODE, "CHECKBOX_TREE_NODE"));
    return rows;
  }
}
