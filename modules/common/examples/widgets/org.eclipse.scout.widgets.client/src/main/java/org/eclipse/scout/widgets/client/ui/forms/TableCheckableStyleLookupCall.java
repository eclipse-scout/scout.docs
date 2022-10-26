/*
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.basic.table.CheckableStyle;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("dfe26726-b0c8-4fd9-8f1c-be2411a5435f")
public class TableCheckableStyleLookupCall extends LocalLookupCall<CheckableStyle> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<CheckableStyle>> execCreateLookupRows() {
    List<ILookupRow<CheckableStyle>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(CheckableStyle.CHECKBOX, "checkbox"));
    rows.add(new LookupRow<>(CheckableStyle.TABLE_ROW, "table_row"));
    rows.add(new LookupRow<>(CheckableStyle.CHECKBOX_TABLE_ROW, "checkbox_table_row"));
    return rows;
  }
}
