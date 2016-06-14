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

import java.util.regex.Pattern;

import org.eclipse.scout.contacts.shared.common.AbstractEmailFieldData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;

@FormData(value = AbstractEmailFieldData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractEmailField extends AbstractStringField {

  // http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Email");
  }

  @Override
  protected String execValidateValue(String rawValue) {
    if (rawValue != null && !Pattern.matches(EMAIL_PATTERN, rawValue)) {
      throw new VetoException(TEXTS.get("BadEmailAddress"));
    }

    return rawValue;
  }

}
