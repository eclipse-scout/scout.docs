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
package org.eclipse.scout.widgets.client.ui.forms.fields.button;

import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;

@ClassId("79d0c290-d368-4c6b-9c3b-9156a446bec2")
public abstract class AbstractButtonPropertiesBox extends AbstractPropertiesBox<AbstractButton> {

  @Override
  protected String getConfiguredLabel() {
    return "String Field Properties";
  }

  @ClassId("822ec62e-470c-4d01-afe8-fda463b5a3e0")
  public class DefaultButton extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Default Button";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getDefaultButton());
    }

    @Override
    protected void execChangedValue() {
      m_field.setDefaultButton(getValue());
    }
  }

  @ClassId("4a2c914a-fe30-467c-b021-36ad7a19e465")
  public class PreventDoubleClickField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Prevent Double Click";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isPreventDoubleClick());
    }

    @Override
    protected void execChangedValue() {
      m_field.setPreventDoubleClick(getValue());
    }
  }

}
