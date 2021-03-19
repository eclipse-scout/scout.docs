/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.docs.snippets.dataobject;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntity1")
public class ExampleEntity1Do extends AbstractExampleEntityDo {
  public DoValue<String> name1Ex() {
    return doValue("name1Ex");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntity1Do withName1Ex(String name1Ex) {
    name1Ex().set(name1Ex);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName1Ex() {
    return name1Ex().get();
  }

  @Override
  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntity1Do withName(String name) {
    name().set(name);
    return this;
  }
}
//end::class[]
