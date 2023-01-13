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
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.imagefield.ImageFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm;

@ClassId("37721775-c06d-4e42-b263-209f331ef907")
public class FormLookupCall extends LocalLookupCall<Class<? extends IPageForm>> {
  private static final long serialVersionUID = 1L;

  @Override
  protected List<ILookupRow<Class<? extends IPageForm>>> execCreateLookupRows() {
    List<ILookupRow<Class<? extends IPageForm>>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(StringFieldForm.class, TEXTS.get("StringField")));
    rows.add(new LookupRow<>(SmartFieldForm.class, TEXTS.get("SmartField")));
    rows.add(new LookupRow<>(ImageFieldForm.class, TEXTS.get("ImageField")));
    return rows;
  }
}
