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

@ClassId("e78ff6ce-39ef-4045-863e-3ef828fef02c")
public class EventTypeCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10000L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("EventType");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("7541d4ba-286e-4873-8091-a91c83a2bf7a")
  public static class PublicCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10010L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Public");
    }

    @Override
    protected String getConfiguredCssClass() {
      return "public";
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("7d8631f8-116f-4622-99c4-ff8912acacb1")
  public static class PrivateCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10020L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Private");
    }

    @Override
    protected String getConfiguredCssClass() {
      return "private";
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30)
  @ClassId("1149a481-bcfc-4fb7-849d-3e7dcc79fa1a")
  public static class ExternalCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10030L;

    @Override
    protected boolean getConfiguredActive() {
      return true;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("External");
    }

    @Override
    protected String getConfiguredCssClass() {
      return "external";
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
