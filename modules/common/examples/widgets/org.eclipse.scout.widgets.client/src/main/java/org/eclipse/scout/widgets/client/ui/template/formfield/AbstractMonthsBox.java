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
package org.eclipse.scout.widgets.client.ui.template.formfield;

import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * @author mzi
 */
@ClassId("4dba3948-7754-4a6d-a833-1c63b92a3b21")
public abstract class AbstractMonthsBox extends AbstractGroupBox {

  public AprilField getAprilField() {
    return getFieldByClass(AprilField.class);
  }

  public FebruaryField getFebruaryField() {
    return getFieldByClass(FebruaryField.class);
  }

  public JanuaryField getJanuaryField() {
    return getFieldByClass(JanuaryField.class);
  }

  public JuneField getJuneField() {
    return getFieldByClass(JuneField.class);
  }

  public MarchField getMarchField() {
    return getFieldByClass(MarchField.class);
  }

  public MayField getMayField() {
    return getFieldByClass(MayField.class);
  }

  @Order(10)
  @ClassId("1c10dc2a-69e3-47a5-a10e-797bef282c3a")
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
  @ClassId("e34451c9-985b-4f13-8ce7-9b0db199be51")
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
  @ClassId("e2a96ff9-f437-4e95-8f90-0a21f7dd7cce")
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
  @ClassId("a3706d10-4320-44c0-b502-0cb25e1d0799")
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
  @ClassId("ad00582f-bbc9-45ac-a4f9-bc3ded004bef")
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
  @ClassId("bd60159a-dbe2-4d68-a6c0-304ab594871a")
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
