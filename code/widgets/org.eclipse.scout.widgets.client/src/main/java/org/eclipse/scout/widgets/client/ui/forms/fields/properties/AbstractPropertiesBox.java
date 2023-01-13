/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
