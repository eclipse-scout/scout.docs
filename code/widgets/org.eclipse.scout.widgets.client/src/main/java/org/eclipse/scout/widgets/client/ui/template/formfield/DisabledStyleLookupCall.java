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

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("1c303f17-674e-4f99-b94e-00ea4db42df8")
public class DisabledStyleLookupCall extends LocalLookupCall<Integer> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
    List<ILookupRow<Integer>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(IFormField.DISABLED_STYLE_DEFAULT, "default"));
    rows.add(new LookupRow<>(IFormField.DISABLED_STYLE_READ_ONLY, "read only"));
    return rows;
  }
}
