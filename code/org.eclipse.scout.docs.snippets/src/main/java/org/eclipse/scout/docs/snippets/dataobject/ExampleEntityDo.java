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
import java.util.List;

import jakarta.annotation.Generated;

import org.eclipse.scout.docs.snippets.dataobject.LoremTypeVersions.Lorem_1_2_0;
import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoList;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;
import org.eclipse.scout.rt.dataobject.TypeVersion;

//tag::class[]
@TypeName("lorem.ExampleEntity")
@TypeVersion(Lorem_1_2_0.class)
public class ExampleEntityDo extends DoEntity {

  public DoValue<String> name() { // <1>
    return doValue("name");
  }

  public DoList<Integer> values() { // <2>
    return doList("values");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withName(String name) {
    name().set(name);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName() {
    return name().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withValues(Collection<? extends Integer> values) {
    values().updateAll(values);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withValues(Integer... values) {
    values().updateAll(values);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public List<Integer> getValues() {
    return values().get();
  }
}
//end::class[]
