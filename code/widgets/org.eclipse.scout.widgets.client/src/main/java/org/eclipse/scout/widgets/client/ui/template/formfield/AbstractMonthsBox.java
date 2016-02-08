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
package org.eclipse.scout.widgets.client.ui.template.formfield;

import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * @author mzi
 */
public abstract class AbstractMonthsBox extends AbstractGroupBox {

  /**
   * @return the AprilField
   */
  public AprilField getAprilField() {
    return getFieldByClass(AprilField.class);
  }

  /**
   * @return the FebruaryField
   */
  public FebruaryField getFebruaryField() {
    return getFieldByClass(FebruaryField.class);
  }

  /**
   * @return the JanuaryField
   */
  public JanuaryField getJanuaryField() {
    return getFieldByClass(JanuaryField.class);
  }

  /**
   * @return the JuneField
   */
  public JuneField getJuneField() {
    return getFieldByClass(JuneField.class);
  }

  /**
   * @return the MarchField
   */
  public MarchField getMarchField() {
    return getFieldByClass(MarchField.class);
  }

  /**
   * @return the MayField
   */
  public MayField getMayField() {
    return getFieldByClass(MayField.class);
  }

  @Order(10)
  public class JanuaryField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("January");
    }

    @Override
    protected int getConfiguredLabelWidthInPixel() {
      return 80;
    }
  }

  @Order(20)
  public class FebruaryField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("February");
    }

    @Override
    protected int getConfiguredLabelWidthInPixel() {
      return 80;
    }
  }

  @Order(30)
  public class MarchField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("March");
    }

    @Override
    protected int getConfiguredLabelWidthInPixel() {
      return 80;
    }
  }

  @Order(40)
  public class AprilField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("April");
    }

    @Override
    protected int getConfiguredLabelWidthInPixel() {
      return 80;
    }
  }

  @Order(50)
  public class MayField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("May");
    }

    @Override
    protected int getConfiguredLabelWidthInPixel() {
      return 80;
    }
  }

  @Order(60)
  public class JuneField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("June");
    }

    @Override
    protected int getConfiguredLabelWidthInPixel() {
      return 80;
    }
  }

}
