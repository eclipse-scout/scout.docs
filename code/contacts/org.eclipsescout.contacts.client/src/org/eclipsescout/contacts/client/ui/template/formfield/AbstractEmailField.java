/**
 * 
 */
package org.eclipsescout.contacts.client.ui.template.formfield;

import java.util.regex.Pattern;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.shared.ui.template.formfield.AbstractEmailFieldData;

/**
 * @author mzi
 */
@FormData(value = AbstractEmailFieldData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractEmailField extends AbstractStringField {
  // http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Email");
  }

  @Override
  protected String execValidateValue(String rawValue) throws ProcessingException {
    if (StringUtility.isNullOrEmpty(rawValue)) {
      return null;
    }

    if (Pattern.compile(EMAIL_PATTERN).matcher(rawValue).matches()) {
      return rawValue;
    }
    else {
      throw new ProcessingException("BadEmailAddress");
    }
  }

}