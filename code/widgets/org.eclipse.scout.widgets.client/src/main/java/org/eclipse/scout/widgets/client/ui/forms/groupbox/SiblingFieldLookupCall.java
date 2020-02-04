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
package org.eclipse.scout.widgets.client.ui.forms.groupbox;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.ui.form.fields.ICompositeField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("8edfb8c9-a461-4497-96f5-e585378f6d4c")
public class SiblingFieldLookupCall extends LocalLookupCall<IFormField> {
  private static final long serialVersionUID = 1L;

  private ICompositeField m_parent;

  public void setParent(ICompositeField parent) {
    m_parent = parent;
  }

  public ICompositeField getParent() {
    return m_parent;
  }

  @Override
  protected List<? extends ILookupRow<IFormField>> execCreateLookupRows() {
    if (m_parent == null) {
      return Collections.emptyList();
    }
    return m_parent.getFields().stream()
        .map(field -> new LookupRow<>(field, field.getLabel()))
        .collect(Collectors.toList());
  }
}
