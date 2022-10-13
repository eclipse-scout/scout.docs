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
package org.eclipse.scout.widgets.client.ui.forms.fields.valuefield;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.ClearableLookupCall;

@ClassId("1cfd6acd-23ac-4550-92b2-477581cceda2")
public abstract class AbstractValueFieldPropertiesBox<V> extends AbstractPropertiesBox<AbstractValueField<V>> {

  @Override
  protected String getConfiguredLabel() {
    return "Value Field Properties";
  }

  protected String valueToString(V value) {
    if (value == null) {
      return null;
    }
    return value.toString();
  }

  @Override
  public void setField(AbstractValueField<V> field) {
    m_field = field;
    m_field.addPropertyChangeListener((event) -> {
      switch (event.getPropertyName()) {
        case IValueField.PROP_VALUE:
          getFieldByClass(ValueField.class).setValue(valueToString(field.getValue()));
          break;
        case IValueField.PROP_DISPLAY_TEXT:
          getFieldByClass(DisplayTextField.class).setValue(field.getDisplayText());
          break;
      }
    });
  }

  @Order(100)
  @ClassId("3fb57672-ebde-433d-ba9f-642a976cfa43")
  public class ValueField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return "Value";
    }

    @Override
    protected void execInitField() {
      setValue(valueToString(m_field.getValue()));
    }
  }

  @Order(200)
  @ClassId("39f5235d-af95-483e-a606-ef8cd87c6359")
  public class DisplayTextField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return "Display Text";
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getDisplayText());
    }
  }

  @Order(300)
  @ClassId("804dc1ee-49cf-4fe1-a00b-4e0833211c2d")
  public class ClearableField extends AbstractSmartField<String> {

    @Override
    protected String getConfiguredLabel() {
      return "Clearable";
    }

    @Override
    protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
      return ClearableLookupCall.class;
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getClearable());
    }

    @Override
    protected void execChangedValue() {
      m_field.setClearable(getValue());
    }
  }

}
