/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.hybrid;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

@TypeName("widgets.HybridJsFormModel")
public class HybridJsFormModelDo extends DoEntity {

  public DoValue<String> pageTitle() {
    return doValue("pageTitle");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public HybridJsFormModelDo withPageTitle(String pageTitle) {
    pageTitle().set(pageTitle);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getPageTitle() {
    return pageTitle().get();
  }
}
