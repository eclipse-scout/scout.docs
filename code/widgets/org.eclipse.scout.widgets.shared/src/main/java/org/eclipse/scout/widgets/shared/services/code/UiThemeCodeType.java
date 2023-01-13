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

@ClassId("786562b0-4751-48d1-b14c-2629462b1d27")
public class UiThemeCodeType extends AbstractCodeType<Long, String> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10000L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("UiTheme");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("5c8e9af7-ce1b-4b98-8d1a-2ffc215651a7")
  public static class DefaultCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "default";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Default");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("b0d1d6ee-a9e5-4be8-9b33-df99364214f9")
  public static class DarkCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "dark";

    @Override
    protected String getConfiguredText() {
      return "Dark";
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
