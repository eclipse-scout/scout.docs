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
package org.eclipse.scout.contacts.client.template;

import org.eclipse.scout.contacts.shared.template.AbstractPhoneFieldData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;

// TODO [mzi] fix this class
@FormData(value = AbstractPhoneFieldData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractPhoneField extends AbstractStringField {

//  private PhoneNumberUtil util = PhoneNumberUtil.getInstance();
//  private PhoneNumber number = null;
  private String country = null;

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Phone");
  }

  @Override
  protected String execValidateValue(String rawValue) {
    clearErrorStatus();

    if (StringUtility.isNullOrEmpty(rawValue)) {
//      number = null;
      return null;
    }

//    try {
//      number = util.parse(rawValue, getCountry());
//      setCountry(m_util.getRegionCodeForNumber(m_number));
//    }
//    catch (NumberParseException e) {
//      throw new ProcessingException(TEXTS.get("PhoneNumberParseException", rawValue, e.getMessage()));
//    }
//
//    if (!m_util.isValidNumber(m_number)) {
//      setErrorStatus(new ProcessingStatus(TEXTS.get("NoValidPhoneNumberException", rawValue, util.getRegionCodeForNumber(m_number)), IProcessingStatus.WARNING));
//    }
//
//    return util.format(m_number, PhoneNumberFormat.INTERNATIONAL);

    return rawValue;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
