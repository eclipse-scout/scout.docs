/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.configuration;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("b4cc5f15-c506-42fd-96a7-c5451828a6ee")
public abstract class AbstractFormFieldConfigurationBox extends AbstractGroupBox {

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("ValueFieldProperties");
  }

  @Override
  protected boolean getConfiguredExpandable() {
    return true;
  }

  @Override
  protected void execInitField() {
    getVisibleField().setValue(getTargetField().isVisible());
    getEnabledField().setValue(getTargetField().isEnabled());
    getMandatoryField().setValue(getTargetField().isMandatory());

  }

  public EnabledField getEnabledField() {
    return getFieldByClass(EnabledField.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public VisibleField getVisibleField() {
    return getFieldByClass(VisibleField.class);
  }

  protected abstract IFormField getTargetField();

  @Order(1000)
  @ClassId("c8da802a-27f9-4521-82d0-9ac7b53594e4")
  public class VisibleField extends AbstractBooleanField {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Visible");
    }

    @Override
    protected void execChangedValue() {
      getTargetField().setVisible(getValue());
    }
  }

  @Order(2000)
  @ClassId("46867b89-f484-40f5-9f77-89d1a12f8e3a")
  public class EnabledField extends AbstractBooleanField {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Enabled");
    }

    @Override
    protected void execChangedValue() {
      getTargetField().setEnabled(getValue());
    }
  }

  @Order(3000)
  @ClassId("95c185bd-cbea-4d9b-a86f-50eda5d48f63")
  public class MandatoryField extends AbstractBooleanField {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Mandatory");
    }

    @Override
    protected void execChangedValue() {
      getTargetField().setMandatory(getValue());
    }
  }

}
