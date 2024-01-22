/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.codetype;

import org.eclipse.scout.rt.api.data.ApiExposed;
import org.eclipse.scout.rt.api.data.ObjectType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

//tag::ExposedCodeType[]
@ApiExposed // <1>
@ObjectType("sample.ExposedCodeType") // <2>
@ClassId("00ee23a3-3a63-4c9e-80b9-25f0c0bf3c85")
public class ExposedCodeType extends AbstractCodeType<String, String> { // <3>
  private static final long serialVersionUID = 1L;
  public static final String ID = "10000";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  @ClassId("72cb3fd6-9fe6-4659-ab9b-96086b902c27")
  public static class FirstCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "1";

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  @ClassId("5621b905-4dc0-4ffa-a764-f6372ed36768")
  public static class SecondCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "2";

    @Override
    public String getId() {
      return ID;
    }
  }
}
//end::ExposedCodeType[]
