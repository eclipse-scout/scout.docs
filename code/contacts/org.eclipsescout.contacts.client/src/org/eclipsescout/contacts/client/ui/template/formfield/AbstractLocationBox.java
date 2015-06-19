/**
 *
 */
package org.eclipsescout.contacts.client.ui.template.formfield;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.contacts.client.services.lookup.CountryLookupCall;
import org.eclipsescout.contacts.shared.ui.template.formfield.AbstractLocationBoxData;

/**
 * @author mzi
 */
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
  }

  @Order(0.0)
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
