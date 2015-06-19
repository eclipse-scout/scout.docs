/**
 *
 */
package org.eclipsescout.contacts.client.ui.template.formfield;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.contacts.client.services.lookup.CountryLookupCall;
import org.eclipsescout.contacts.client.ui.forms.MapForm;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox.LocationBox.CityField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox.LocationBox.CountryField;
import org.eclipsescout.contacts.shared.ui.template.formfield.AbstractAddressBoxData;

/**
 * @author mzi
 */
@FormData(value = AbstractAddressBoxData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractAddressBox extends AbstractGroupBox {
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

  /**
   * @return the StreetField
   */
  public StreetField getStreetField() {
    return getFieldByClass(StreetField.class);
  }

  /**
   * @return the StreetField
   */
  public LocationBox getLocationBox() {
    return getFieldByClass(LocationBox.class);
  }

  /**
   * @return the CityField
   */
  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  /**
   * @return the CountryField
   */
  public CountryField getCountryField() {
    return getFieldByClass(CountryField.class);
  }

  /**
   * @return the ShowOnMapButton
   */
  public ShowOnMapButton getShowOnMapButton() {
    return getFieldByClass(ShowOnMapButton.class);
  }

  @Order(500.0)
  public class StreetField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Street");
    }

    @Override
    protected void execChangedValue() throws ProcessingException {
      verifyAllFields();
    }
  }

  @Order(1250.0)
  public class LocationBox extends AbstractSequenceBox {

    @Override
    protected boolean getConfiguredAutoCheckFromTo() {
      return false;
    }

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Location");
    }

    @Order(-1000.0)
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
      protected void execChangedValue() throws ProcessingException {
        verifyAllFields();
      }
    }

    @Order(0.0)
    public class CountryField extends AbstractSmartField<String> {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Country");
      }

      @Override
      protected void execChangedValue() throws ProcessingException {
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

  private void verifyAllFields() {
    boolean hasStreet = StringUtility.hasText(getStreetField().getValue());
    boolean hasCity = StringUtility.hasText(getCityField().getValue());

    getCityField().setMandatory(hasStreet);
    getCountryField().setMandatory(hasStreet || hasCity);
  }

  @Order(1625.0)
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
    protected void execClickAction() throws ProcessingException {
      MapForm mapForm = new MapForm();
      mapForm.setStreet(getStreetField().getValue());
      mapForm.setCity(getCityField().getValue());
      mapForm.setCountry(getCountryField().getValue());
      mapForm.startModify();
    }
  }

}
