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
  @AttributeName("nameEx")
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
