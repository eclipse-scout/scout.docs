package org.eclipse.scout.docs.snippets.codetype;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.code.ICode;

/**
 * <h3>{@link CodetypeExamples}</h3>
 */
public class CodetypeExamples {

  //tag::getCodeText[]
  public String getCodeText(boolean key) {
    ICode c = BEANS.get(YesOrNoCodeType.class).getCode(key);
    if (c != null) {
      return c.getText();
    }
    return null;
  }
  //end::getCodeText[]
}
