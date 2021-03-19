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

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.AttributeName;
import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.IValueFormatConstants;
import org.eclipse.scout.rt.dataobject.IgnoreConvenienceMethodGeneration;
import org.eclipse.scout.rt.dataobject.TypeName;
import org.eclipse.scout.rt.dataobject.ValueFormat;

@IgnoreConvenienceMethodGeneration
@TypeName("CustomAttributeEntity")
public class CustomAttributeEntityDo extends DoEntity {

  //tag::customAttributeName[]
  @AttributeName("myCustomName")
  public DoValue<String> name() {
    return doValue("myCustomName"); // <1>
  }
  //end::customAttributeName[]

  //tag::customValueFormat[]
  @ValueFormat(pattern = IValueFormatConstants.DATE_PATTERN)
  public DoValue<Date> date() {
    return doValue("date");
  }
  //end::customValueFormat[]

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public CustomAttributeEntityDo withName(String name) {
    name().set(name);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName() {
    return name().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public CustomAttributeEntityDo withDate(Date date) {
    date().set(date);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Date getDate() {
    return date().get();
  }
}
