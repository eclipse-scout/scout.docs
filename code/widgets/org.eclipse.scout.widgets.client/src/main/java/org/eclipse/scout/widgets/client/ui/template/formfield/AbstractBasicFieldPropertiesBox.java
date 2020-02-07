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
package org.eclipse.scout.widgets.client.ui.template.formfield;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractBasicField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("5fb63475-4bdb-48b3-b585-83e9c217d480")
public abstract class AbstractBasicFieldPropertiesBox extends AbstractGroupBox {

  private AbstractBasicField<?> m_field;

  @Override
  protected String getConfiguredLabel() {
    return "Basic Field Properties";
  }

  @Override
  protected boolean getConfiguredExpandable() {
    return true;
  }

  public void setField(AbstractBasicField<?> field) {
    m_field = field;
  }
}
