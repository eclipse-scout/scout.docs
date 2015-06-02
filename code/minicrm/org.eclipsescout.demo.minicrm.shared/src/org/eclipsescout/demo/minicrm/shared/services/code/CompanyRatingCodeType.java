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
package org.eclipsescout.demo.minicrm.shared.services.code;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipsescout.demo.minicrm.shared.Icons;

public class CompanyRatingCodeType extends AbstractCodeType<Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10100L;

  public CompanyRatingCodeType() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("CompanyRating");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  public static class ACode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10101L;

    @Override
    protected String getConfiguredIconId() {
      return Icons.Star;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("A");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  public static class BCode extends AbstractCode<Long> {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Star;
    }

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10102L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("B");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30.0)
  public static class CCode extends AbstractCode<Long> {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Star;
    }

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10103L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("C");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(40.0)
  public static class DCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10104L;

    @Override
    protected String getConfiguredIconId() {
      return Icons.Star;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("D");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
