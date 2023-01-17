/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.stringfield;

import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;

@ClassId("b537dafd-23ab-4c68-8dee-70469351f145")
public abstract class AbstractStringFieldPropertiesBox extends AbstractPropertiesBox<AbstractStringField> {

  @Override
  protected String getConfiguredLabel() {
    return "String Field Properties";
  }

  @ClassId("46b28fa5-c41e-4bfb-aee3-c51629199bd8")
  public class WrapTextField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Wrap Text";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isWrapText());
    }

    @Override
    protected void execChangedValue() {
      m_field.setWrapText(getValue());
    }
  }

  @ClassId("ec51887c-c9dc-4375-a02b-a719c9b72be7")
  public class InputMaskedField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Input Masked";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isInputMasked());
    }

    @Override
    protected void execChangedValue() {
      m_field.setInputMasked(getValue());
    }
  }

}
