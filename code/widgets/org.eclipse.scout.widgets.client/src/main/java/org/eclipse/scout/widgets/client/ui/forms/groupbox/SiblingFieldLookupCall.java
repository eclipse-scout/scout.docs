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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.ui.form.fields.ICompositeField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class SiblingFieldLookupCall extends LocalLookupCall<IFormField> {
  private static final long serialVersionUID = 1L;

  private ICompositeField m_parent;
  private PropertyChangeListener m_compositeFieldPropertyListener;
  private List<? extends ILookupRow<IFormField>> m_lookupRows;

  public SiblingFieldLookupCall(ICompositeField parent) {
    m_parent = parent;
    m_compositeFieldPropertyListener = new P_CompositeFieldPropertyListener();
    rebuildLookupRows();
    m_parent.addPropertyChangeListener(m_compositeFieldPropertyListener);
  }

  public void dispose() {
    m_parent.removePropertyChangeListener(m_compositeFieldPropertyListener);
  }

  @Override
  protected List<? extends ILookupRow<IFormField>> execCreateLookupRows() {
    return m_lookupRows;
  }

  private void rebuildLookupRows() {
    m_lookupRows = m_parent.getFields().stream()
        .map(field -> new LookupRow<>(field, field.getLabel()))
        .collect(Collectors.toList());
  }

  private class P_CompositeFieldPropertyListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      if (ICompositeField.PROP_FIELDS.equals(evt.getPropertyName())) {
        rebuildLookupRows();
      }
    }
  }

}
