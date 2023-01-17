/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.smartfield;

import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;

@ClassId("a2590adf-aae8-4a50-8d1d-6eb2f54800ef")
public abstract class AbstractSmartFieldPropertiesBox<V> extends AbstractPropertiesBox<AbstractSmartField<V>> {

  @Override
  protected String getConfiguredLabel() {
    return "Smart Field Properties";
  }

  @ClassId("1f3ad169-e55d-43da-9220-fddccd733893")
  public class ActiveFilterEnabledField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Active Filter Enabled";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isActiveFilterEnabled());
    }

    @Override
    protected void execChangedValue() {
      m_field.setActiveFilterEnabled(getValue());
    }
  }

}
