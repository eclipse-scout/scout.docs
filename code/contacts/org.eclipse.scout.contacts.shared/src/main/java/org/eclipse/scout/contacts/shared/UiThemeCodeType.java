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
package org.eclipse.scout.contacts.shared;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("90ce6c8f-aeb0-4433-8891-87c340126fa7")
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
  @ClassId("40ef44b8-dc5f-43e6-8640-bd8eccc84669")
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

  @Order(30)
  @ClassId("2256f97b-4e76-48f0-85fb-0a0f981e231c")
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
