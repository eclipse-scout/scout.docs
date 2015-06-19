/**
 *
 */
package org.eclipsescout.contacts.client.ui.template.formfield;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.shared.ui.template.formfield.AbstractPhoneFieldData;

/**
 * @author mzi
 */
@FormData(value = AbstractPhoneFieldData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractPhoneField extends AbstractStringField {

//  private PhoneNumberUtil m_util = PhoneNumberUtil.getInstance();
//  private PhoneNumber m_number = null;
  private String m_country = null;

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Phone");
  }

  @Override
  protected String execValidateValue(String rawValue) throws ProcessingException {
    clearErrorStatus();

    if (StringUtility.isNullOrEmpty(rawValue)) {
//      m_number = null;
      return null;
    }

//    try {
//      m_number = m_util.parse(rawValue, getCountry());
//      setCountry(m_util.getRegionCodeForNumber(m_number));
//    }
//    catch (NumberParseException e) {
//      throw new ProcessingException(TEXTS.get("PhoneNumberParseException", rawValue, e.getMessage()));
//    }
//
//    if (!m_util.isValidNumber(m_number)) {
//      setErrorStatus(new ProcessingStatus(TEXTS.get("NoValidPhoneNumberException", rawValue, m_util.getRegionCodeForNumber(m_number)), IProcessingStatus.WARNING));
//    }
//
//    return m_util.format(m_number, PhoneNumberFormat.INTERNATIONAL);

    return rawValue;
  }

  public String getCountry() {
    return m_country;
  }

  public void setCountry(String country) {
    m_country = country;
  }

}
