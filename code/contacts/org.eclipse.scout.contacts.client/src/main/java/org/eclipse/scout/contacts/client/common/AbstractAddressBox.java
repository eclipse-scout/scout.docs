/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.common;

import org.eclipse.scout.contacts.client.common.AbstractAddressBox.LocationBox.CityField;
import org.eclipse.scout.contacts.client.common.AbstractAddressBox.LocationBox.CountryField;
import org.eclipse.scout.contacts.shared.common.AbstractAddressBoxData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

// tag::template[]
@FormData(value = AbstractAddressBoxData.class, // <1>
    sdkCommand = FormData.SdkCommand.CREATE,
    defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractAddressBox extends AbstractGroupBox {
  // no modifications to the content of the address box necessary
  // end::template[]

  @Override
  protected boolean getConfiguredBorderVisible() {
    return false;
  }

  @Override
  protected int getConfiguredGridColumnCount() {
    return 1;
  }

  @Override
  protected int getConfiguredGridH() {
    return 3;
  }

  @Override
  protected int getConfiguredGridW() {
    return 1;
  }

  public StreetField getStreetField() {
    return getFieldByClass(StreetField.class);
  }

  public LocationBox getLocationBox() {
    return getFieldByClass(LocationBox.class);
  }

  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  public CountryField getCountryField() {
    return getFieldByClass(CountryField.class);
  }

  public ShowOnMapButton getShowOnMapButton() {
    return getFieldByClass(ShowOnMapButton.class);
  }

  @Order(1000)
  public class StreetField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Street");
    }

    @Override
    protected void execChangedValue() {
      verifyAllFields();
    }
  }

  @Order(2000)
  public class LocationBox extends AbstractSequenceBox {

    @Override
    protected boolean getConfiguredAutoCheckFromTo() {
      return false;
    }

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Location");
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

      @Override
      protected void execChangedValue() {
        verifyAllFields();
      }
    }

    @Order(2000)
    public class CountryField extends AbstractSmartField<String> {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Country");
      }

      @Override
      protected void execChangedValue() {
        verifyAllFields();
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

  @Order(3000)
  public class ShowOnMapButton extends AbstractLinkButton {

    @Override
    protected int getConfiguredHorizontalAlignment() {
      return 1;
    }

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("ShowOnMap");
    }

    @Override
    protected Class<? extends IValueField> getConfiguredMasterField() {
      return CountryField.class;
    }

    @Override
    protected boolean getConfiguredMasterRequired() {
      return true;
    }

    @Override
    protected boolean getConfiguredProcessButton() {
      return false;
    }

    @Override
    protected void execClickAction() {
      MapForm mapForm = new MapForm();
      mapForm.setStreet(getStreetField().getValue());
      mapForm.setCity(getCityField().getValue());
      mapForm.setCountry(getCountryField().getValue());
      mapForm.startModify();
    }
  }

  protected void verifyAllFields() {
    boolean hasStreet = StringUtility.hasText(getStreetField().getValue());
    boolean hasCity = StringUtility.hasText(getCityField().getValue());

    getCityField().setMandatory(hasStreet);
    getCountryField().setMandatory(hasStreet || hasCity);
  }
  // tag::template[]
}
// end::template[]
