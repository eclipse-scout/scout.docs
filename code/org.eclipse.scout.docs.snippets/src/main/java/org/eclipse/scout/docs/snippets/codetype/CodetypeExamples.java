/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.codetype;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.code.ICode;

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
