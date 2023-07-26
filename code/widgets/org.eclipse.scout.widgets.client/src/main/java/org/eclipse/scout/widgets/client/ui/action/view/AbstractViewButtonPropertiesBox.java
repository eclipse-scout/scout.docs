/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.action.view;

import java.util.Optional;

import org.eclipse.scout.rt.client.ui.action.view.IViewButton;
import org.eclipse.scout.rt.client.ui.action.view.IViewButton.DisplayStyle;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.ViewButtonDisplayStyleLookupCall;
import org.eclipse.scout.widgets.client.ui.action.AbstractActionPropertiesBox;

@ClassId("ff91bb52-659f-4acd-ad59-c02299a2e961")
public abstract class AbstractViewButtonPropertiesBox extends AbstractActionPropertiesBox<IViewButton> {

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("ViewButtonProperties");
  }

  @Override
  protected void updateFields() {
    super.updateFields();
    IViewButton action = getAction();
    if (action != null) {
      getDisplayStyleField().setEnabled(true);
      getDisplayStyleField().setValue(action.getDisplayStyle());
    }
    else {
      getDisplayStyleField().setEnabled(false);
      getDisplayStyleField().setValue(null);
    }
  }

  public DisplayStyleField getDisplayStyleField() {
    return getFieldByClass(DisplayStyleField.class);
  }

  @Order(10000)
  @ClassId("bb8e93de-d360-4226-9c6e-39eaddf4e1a8")
  public class DisplayStyleField extends AbstractSmartField<IViewButton.DisplayStyle> {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("DisplayStyle");
    }

    @Override
    protected Class<? extends ILookupCall<DisplayStyle>> getConfiguredLookupCall() {
      return ViewButtonDisplayStyleLookupCall.class;
    }

    @Override
    protected void execChangedValue() {
      Optional.ofNullable(getAction()).ifPresent(a -> a.setDisplayStyle(getValue()));
    }
  }
}
