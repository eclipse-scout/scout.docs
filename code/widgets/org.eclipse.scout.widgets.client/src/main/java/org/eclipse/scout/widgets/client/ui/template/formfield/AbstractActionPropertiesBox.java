/*******************************************************************************
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.util.Optional;

import org.eclipse.scout.rt.client.ui.action.IAction;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.IconIdLookupCall;

@ClassId("206530e2-cebb-4ae8-9624-20474982cfda")
public abstract class AbstractActionPropertiesBox<ACTION extends IAction> extends AbstractGroupBox {

  private ACTION m_action;

  public ACTION getAction() {
    return m_action;
  }

  public void setAction(ACTION action) {
    m_action = action;
    updateFields();
  }

  protected void updateFields() {
    ACTION action = getAction();
    if (action != null) {
      getVisibleField().setEnabled(true);
      getVisibleField().setValue(action.isVisible());
      getTextField().setEnabled(true);
      getTextField().setValue(action.getText());
      getIconIdField().setEnabled(true);
      getIconIdField().setValue(action.getIconId());
    }
    else {
      getVisibleField().setEnabled(false);
      getVisibleField().setValue(null);
      getTextField().setEnabled(false);
      getTextField().setValue(null);
      getIconIdField().setEnabled(false);
      getIconIdField().setValue(null);
    }
  }

  public AbstractActionPropertiesBox<?>.IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  public AbstractActionPropertiesBox<?>.TextField getTextField() {
    return getFieldByClass(TextField.class);
  }

  public AbstractActionPropertiesBox<?>.VisibleField getVisibleField() {
    return getFieldByClass(VisibleField.class);
  }

  @Order(1000)
  @ClassId("bc47bfeb-24ed-40e8-b8fe-55e80763cc9e")
  public class VisibleField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Visible";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected String getConfiguredFont() {
      return "ITALIC";
    }

    @Override
    protected void execChangedValue() {
      Optional.ofNullable(getAction()).ifPresent(a -> a.setVisible(getValue()));
    }

  }

  @Order(2000)
  @ClassId("94632305-5dbf-4b18-9e1d-d2d0334c85ff")
  public class TextField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Text");
    }

    @Override
    protected int getConfiguredMaxLength() {
      return 128;
    }

    @Override
    protected void execChangedValue() {
      Optional.ofNullable(getAction()).ifPresent(a -> a.setText(getValue()));
    }
  }

  @Order(3000)
  @ClassId("075c514d-7cc7-43bc-a347-63399381ce0a")
  public class IconIdField extends AbstractSmartField<String> {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("IconId");
    }

    @Override
    protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
      return IconIdLookupCall.class;
    }

    @Override
    protected void execChangedValue() {
      Optional.ofNullable(getAction()).ifPresent(a -> a.setIconId(getValue()));
    }
  }

}
