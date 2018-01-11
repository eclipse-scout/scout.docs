/*******************************************************************************
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.template.formfield.FieldTypeLookupCall.FIELD_TYPE;

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
