/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.dataobject;

import jakarta.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntity2")
public class ExampleEntity2Do extends AbstractExampleEntityDo {
  public DoValue<String> name2Ex() {
    return doValue("name2Ex");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntity2Do withName2Ex(String name2Ex) {
    name2Ex().set(name2Ex);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName2Ex() {
    return name2Ex().get();
  }

  @Override
  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntity2Do withName(String name) {
    name().set(name);
    return this;
  }
}
//end::class[]
