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

import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
@ClassId("aed2b172-c861-4025-9d7b-3cccd59670e1")
public class AnswerOptionsLookupCall extends LocalLookupCall<Integer> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<Integer>> execCreateLookupRows() {
    ArrayList<LookupRow<Integer>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(IMessageBox.YES_OPTION, "IMessageBox.YES_OPTION"));
    rows.add(new LookupRow<>(IMessageBox.NO_OPTION, "IMessageBox.NO_OPTION"));
    rows.add(new LookupRow<>(IMessageBox.CANCEL_OPTION, "IMessageBox.CANCEL_OPTION"));
    return rows;
  }
}
