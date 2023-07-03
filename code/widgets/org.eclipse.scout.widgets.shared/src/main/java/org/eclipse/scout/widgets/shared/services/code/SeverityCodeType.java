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
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("2def8eec-197f-432d-9de2-5f248e422e92")
public class SeverityCodeType extends AbstractCodeType<Long, Integer> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10001L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Severity");
  }

  @Override
  protected String getConfiguredTextPlural() {
    return TEXTS.get("Severity");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("1c52e928-ba89-42df-bb54-7c52f3cedca2")
  public static class OkCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.OK;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Ok");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("a06cc1fc-2516-4a60-87f5-ad33bfcedebd")
  public static class InfoCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.INFO;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Info");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(30)
  @ClassId("e1e809b8-06ce-4367-b60f-3b1ac365bfa4")
  public static class WarningCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.WARNING;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Warning");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(40)
  @ClassId("dc34ce15-5391-4de3-a5f7-6af69c740077")
  public static class ErrorCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.ERROR;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Error");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }
}
