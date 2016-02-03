/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.ImageFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm;

public class FormLookupCall extends LocalLookupCall<Class<? extends IPageForm>> {
  private static final long serialVersionUID = 1L;

  @Override
  protected List<ILookupRow<Class<? extends IPageForm>>> execCreateLookupRows() {
    List<ILookupRow<Class<? extends IPageForm>>> rows = new ArrayList<ILookupRow<Class<? extends IPageForm>>>();
    rows.add(new LookupRow<Class<? extends IPageForm>>(StringFieldForm.class, TEXTS.get("StringField")));
    rows.add(new LookupRow<Class<? extends IPageForm>>(SmartFieldForm.class, TEXTS.get("SmartField")));
    rows.add(new LookupRow<Class<? extends IPageForm>>(ImageFieldForm.class, TEXTS.get("ImageField")));
    return rows;
  }
}
