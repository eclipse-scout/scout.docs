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
package org.eclipse.scout.contacts.shared;

import org.eclipse.scout.rt.platform.Order;
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

  @Order(10)
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
  public static class RayoCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "rayo";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Rayo");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(30)
  public static class DarkCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "dark";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Dark");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
