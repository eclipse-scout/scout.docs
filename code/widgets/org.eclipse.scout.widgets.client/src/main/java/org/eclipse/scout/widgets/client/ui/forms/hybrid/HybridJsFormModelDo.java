/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
