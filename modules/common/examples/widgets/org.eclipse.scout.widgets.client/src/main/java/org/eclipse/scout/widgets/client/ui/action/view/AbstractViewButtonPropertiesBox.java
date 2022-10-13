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
