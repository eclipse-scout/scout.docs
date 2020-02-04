/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipsescout.demo.bahbah.shared.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("c91b9400-92ef-487e-9c2e-d25b7859e11b")
public class UserRoleCodeType extends AbstractCodeType<Long, Integer> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 1000L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("UserRole");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("9a0c9711-6e3e-43c1-9ca1-938627727cc7")
  public static class AdministratorCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final Integer ID = 100;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Administrator");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("a1c30dcb-c8b9-496e-8398-b0b35cf33566")
  public static class UserCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final Integer ID = 10;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("User");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }
}
