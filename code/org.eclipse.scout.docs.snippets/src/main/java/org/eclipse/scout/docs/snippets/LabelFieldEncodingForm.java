/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("f641be77-70ea-4301-bc4c-9e94a6b5b8b3")
public class LabelFieldEncodingForm extends AbstractForm {

  @Order(10)
  @ClassId("8187a547-a8a0-4393-8a4c-d81ac731a704")
  public class MainBox extends AbstractGroupBox {

    @Order(2000)
    @ClassId("22407a4c-aecc-4731-b0db-baddd3c1eb20")
    // tag::SecureOutput.encodingByDefault[]
    public class LabelField extends AbstractLabelField {
      @Override
      protected void execInitField() {
        setValue("...<b>Bold text</b>...");
      }
    }// end::SecureOutput.encodingByDefault[]

    @Order(2000)
    @ClassId("49675395-373a-4a70-bed1-8cd5272ad8c6")
    // tag::SecureOutput.noEncodingField[]
    public class NoEncodingLabelField extends AbstractLabelField {
      @Override
      protected boolean getConfiguredHtmlEnabled() {
        return true;
      }

      @Override
      protected void execInitField() {
        setValue("...<b>Bold text</b>...");
      }
    }// end::SecureOutput.noEncodingField[]

    @Order(40)
    @ClassId("5eca3b8d-6e46-425f-ab45-fcbb653af170")
    public class CloseButton extends AbstractCloseButton {
    }

  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
