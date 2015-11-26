/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.template;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.shared.template.AbstractLocationBoxData;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = AbstractLocationBoxData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractLocationBox extends AbstractSequenceBox {
  @Override
  protected boolean getConfiguredAutoCheckFromTo() {
    return false;
  }

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Location");
  }

  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  public CountryField getCountryField() {
    return getFieldByClass(CountryField.class);
  }

  @Order(1000)
  public class CityField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("City");
    }

    @Override
    protected int getConfiguredLabelPosition() {
      return LABEL_POSITION_ON_FIELD;
    }
  }

  @Order(2000)
  public class CountryField extends AbstractSmartField<String> {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Country");
    }

    @Override
    protected int getConfiguredLabelPosition() {
      return LABEL_POSITION_ON_FIELD;
    }

    @Override
    protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
      return CountryLookupCall.class;
    }
  }
}
