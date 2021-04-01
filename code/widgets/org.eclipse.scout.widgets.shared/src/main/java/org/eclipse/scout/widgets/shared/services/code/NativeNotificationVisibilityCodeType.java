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
package org.eclipse.scout.widgets.shared.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("f77ed0df-bdd4-4c7f-9d1a-1e8ff2f5db7c")
public class NativeNotificationVisibilityCodeType extends AbstractCodeType<Long, String> {

  private static final long serialVersionUID = 1L;
  public static final long ID = 185655L;

  @Override
  protected String getConfiguredText() {
    return "Native Notification Visibility";
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("b80a2b5d-4440-49d2-87c0-89e2e1c2e831")
  public static class NoneCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "none";

    @Override
    protected String getConfiguredText() {
      return "None";
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("63aec357-c3eb-4822-b8fe-aeb14eb6cc05")
  public static class BackgroundCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "background";

    @Override
    protected String getConfiguredText() {
      return "Background";
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(30)
  @ClassId("43d4f431-f2bd-4b1c-bb02-bc52ed2681aa")
  public static class AlwaysCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "always";

    @Override
    protected String getConfiguredText() {
      return "Always";
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
