/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest.pageWithTable;

import jakarta.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

@TypeName("jswidgets.SamplePageWithTableRow")
public class SamplePageWithTableRowDo extends DoEntity {
  public DoValue<Long> id() {
    return doValue("id");
  }

  public DoValue<String> string() {
    return doValue("string");
  }

  public DoValue<String> smartValue() {
    return doValue("smartValue");
  }

  public DoValue<Long> number() {
    return doValue("number");
  }

  public DoValue<Boolean> bool() {
    return doValue("bool");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public SamplePageWithTableRowDo withId(Long id) {
    id().set(id);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Long getId() {
    return id().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public SamplePageWithTableRowDo withString(String string) {
    string().set(string);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getString() {
    return string().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public SamplePageWithTableRowDo withSmartValue(String smartValue) {
    smartValue().set(smartValue);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getSmartValue() {
    return smartValue().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public SamplePageWithTableRowDo withNumber(Long number) {
    number().set(number);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Long getNumber() {
    return number().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public SamplePageWithTableRowDo withBool(Boolean bool) {
    bool().set(bool);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Boolean getBool() {
    return bool().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public boolean isBool() {
    return nvl(getBool());
  }
}
