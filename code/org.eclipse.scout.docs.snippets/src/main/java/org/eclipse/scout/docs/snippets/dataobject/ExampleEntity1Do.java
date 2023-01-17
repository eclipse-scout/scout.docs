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
