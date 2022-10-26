/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.docs.snippets.mobile;

import org.eclipse.scout.rt.client.transformation.IDeviceTransformationService;
import org.eclipse.scout.rt.client.transformation.MobileDeviceTransformation;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

public final class AdjustComponents {

  //tag::UserAgentUtility[]
  @Order(20)
  @ClassId("032f5ffb-bb1a-477a-95c8-f185e930a977")
  public class MyField extends AbstractStringField {

    @Override
    protected void execInitField() {
      if (UserAgentUtility.isMobileDevice()) {
        setVisibleGranted(false);
      }
    }
  }
  //end::UserAgentUtility[]

  @Order(20)
  @ClassId("e53ab29d-cbd0-47e2-b75d-80c3a9770c58")
  public class MyField2 extends AbstractStringField {
    //tag::ExcludeTransformation[]
    @Override
    protected void execInitField() {
      BEANS.get(IDeviceTransformationService.class).excludeFieldTransformation(this, MobileDeviceTransformation.REDUCE_GROUPBOX_COLUMNS_TO_ONE);
    }
    //end::ExcludeTransformation[]
  }

  @Order(20)
  @ClassId("f2c93960-0b8a-4b1a-b063-6a57b8f02b64")
  public class MyField3 extends AbstractStringField {
    //tag::ExcludeField[]
    @Override
    protected void execInitField() {
      BEANS.get(IDeviceTransformationService.class).excludeField(this);
    }
    //end::ExcludeField[]
  }

  @Order(20)
  @ClassId("81bbe250-a5bf-411a-9da1-b218e5b373ca")
  public class MyField4 extends AbstractStringField {
    //tag::CssClass[]
    @Override
    protected void execInitField() {
      if (UserAgentUtility.isMobileDevice()) {
        addCssClass("mobile");
      }
    }
    //end::CssClass[]
  }

  @Order(20)
  @ClassId("c372d52f-9d8b-4cf1-8052-9c3e95e1dd47")
  public class MyGroupBox extends AbstractGroupBox {
    //tag::Gaps[]
    @Override
    protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
      return super.getConfiguredBodyLayoutConfig()
          .withVGap(0);
    }
    //end::Gaps[]
  }
}
