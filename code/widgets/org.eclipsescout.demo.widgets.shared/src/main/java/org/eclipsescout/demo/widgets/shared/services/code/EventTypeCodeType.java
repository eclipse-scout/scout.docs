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
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

/**
 * @author mzi
 */
public class EventTypeCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10000L;

  public EventTypeCodeType() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("EventType");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  public static class PublicCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10010L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Public");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  public static class PrivateCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10020L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Private");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30.0)
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
    public Long getId() {
      return ID;
    }
  }
}
