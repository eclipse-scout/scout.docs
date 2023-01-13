/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.wizards;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardFontForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardFontForm.MainBox.GroupBox.FontField;

@ClassId("55b647a1-dcde-431b-ae22-ced91ebd5309")
public class LabelWizardFontForm extends AbstractForm {

  public void startWizard() {
    startInternal(new WizardHandler());
  }

  public FontField getFontField() {
    return getFieldByClass(FontField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("eab028a0-12c4-4a91-8f81-d629a7bf64b1")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("8a52d531-2e86-4504-b303-051e93087556")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("1bcdad8c-30a2-4022-bd75-15d05e961bd9")
      public class FontField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Font");
        }

        @Override
        protected void execInitField() {
          setValue("Serif");
        }
      }
    }
  }

  public class WizardHandler extends AbstractFormHandler {
    @Override
    protected void execPostLoad() {
      getFontField().requestFocus();
    }
  }
}
