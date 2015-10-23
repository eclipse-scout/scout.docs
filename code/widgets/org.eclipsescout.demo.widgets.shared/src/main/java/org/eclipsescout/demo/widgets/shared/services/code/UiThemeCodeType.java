/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.shared.services.code;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class UiThemeCodeType extends AbstractCodeType<Long, String> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10000L;

  public UiThemeCodeType() {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("UiTheme");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
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

  @Order(20.0)
  public static class HighContrastCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "high-contrast";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("HighContrast");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
