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
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.template.formfield.FieldTypeLookupCall.FIELD_TYPE;

@ClassId("f9212475-3ad3-42d5-88ad-09ff5a37370a")
public class FieldTypeLookupCall extends LocalLookupCall<FIELD_TYPE> {
  private static final long serialVersionUID = 1L;

  public static enum FIELD_TYPE {
    DateField,
    IntegerField,
    SmartField,
    StringField
  }

  @Override
  protected List<? extends ILookupRow<FIELD_TYPE>> execCreateLookupRows() {
    List<ILookupRow<FIELD_TYPE>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(FIELD_TYPE.DateField, "DateField"));
    rows.add(new LookupRow<>(FIELD_TYPE.IntegerField, "IntegerField"));
    rows.add(new LookupRow<>(FIELD_TYPE.SmartField, "SmartField"));
    rows.add(new LookupRow<>(FIELD_TYPE.StringField, "StringField"));
    return rows;
  }
}
