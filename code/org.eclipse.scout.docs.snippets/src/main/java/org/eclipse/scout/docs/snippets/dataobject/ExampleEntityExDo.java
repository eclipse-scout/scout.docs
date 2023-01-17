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

import java.util.Collection;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.AttributeName;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntityEx")
public class ExampleEntityExDo extends ExampleEntityDo {

  @Override
  @AttributeName("nameEx")
  public DoValue<String> name() { // <1>
    return doValue("nameEx");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Override
  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityExDo withName(String name) {
    name().set(name);
    return this;
  }

  @Override
  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityExDo withValues(Collection<? extends Integer> values) {
    values().updateAll(values);
    return this;
  }

  @Override
  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityExDo withValues(Integer... values) {
    values().updateAll(values);
    return this;
  }
}
//end::class[]
