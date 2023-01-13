/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.shared.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("1a0f33ad-b658-456d-a587-80d545124c60")
public class CountryCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 20000L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Country");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("b7838d98-cdab-42a4-b981-c5e13dbdf1af")
  public static class USACode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("USA");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("90614b12-3f20-4a6a-af6d-f4175f9d7545")
  public static class GreatBritainCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("GreatBritain");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30)
  @ClassId("c9205b1e-1098-4787-8be0-674240b8b85c")
  public static class GermanyCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20003L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Germany");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(40)
  @ClassId("ec4977d2-b536-4146-a77c-34427c306a3b")
  public static class FranceCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20004L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("France");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(50)
  @ClassId("39a6abc8-97cc-4778-8df0-5a2e0b97d90a")
  public static class SwitzerlandCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20005L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Switzerland");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
