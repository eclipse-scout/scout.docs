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
package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;

public class StringFieldEncodingForm extends AbstractForm {

  public StringFieldEncodingForm() {
    super();
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(2000)
    // tag::SecureOutput.encodingByDefault[]
    public class StringField extends AbstractStringField {
      @Override
      protected void execInitField() {
        setValue("...<b>Bold text</b>...");
      }
    }// end::SecureOutput.encodingByDefault[]

    @Order(2000)
    // tag::SecureOutput.noEncodingField[]
    public class NoEncodingStringField extends AbstractStringField {
      @Override
      protected boolean getConfiguredHtmlEnabled() {
        return false;
      }

      @Override
      protected void execInitField() {
        setValue("...<b>Bold text</b>...");
      }
    }// end::SecureOutput.noEncodingField[]

    @Order(40)
    public class CloseButton extends AbstractCloseButton {
    }

  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
