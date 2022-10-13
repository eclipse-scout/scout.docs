/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.properties;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("145ab465-cfe5-4dd1-95b8-f5193cc01fcc")
public class AbstractPropertiesBox<F extends AbstractFormField> extends AbstractGroupBox implements IPropertiesBox<F> {

  protected F m_field;

  @Override
  public void setField(F field) {
    m_field = field;
  }

  @Override
  protected boolean getConfiguredExpandable() {
    return true;
  }
}
