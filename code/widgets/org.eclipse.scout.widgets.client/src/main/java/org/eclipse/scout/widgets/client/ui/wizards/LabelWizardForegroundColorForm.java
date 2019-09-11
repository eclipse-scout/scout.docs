/*
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.wizards;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardForegroundColorForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardForegroundColorForm.MainBox.GroupBox.ForegroundColorField;

@ClassId("5baf1084-a0e7-4861-9b07-9cdce07cc005")
public class LabelWizardForegroundColorForm extends AbstractForm {

  public void startWizard() {
    startInternal(new WizardHandler());
  }

  public ForegroundColorField getForegroundColorField() {
    return getFieldByClass(ForegroundColorField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("078017ed-9279-48b1-9eee-eae23961d1ed")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("23cdf6a8-dbb6-430d-94e2-5fa7c32d1b67")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("61b0722d-b7a2-4074-bcc5-7c5c71e74d8e")
      public class ForegroundColorField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ForegroundColor");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("HexColorFormatTooltip");
        }

        @Override
        protected void execInitField() {
          setValue("1482A1");
        }

        @Override
        protected String execValidateValue(String rawValue) {
          clearErrorStatus();
          if (!rawValue.matches("[0-9A-Fa-f]{6}")) {
            throw new VetoException("\"" + rawValue + "\" " + TEXTS.get("NoColor"));
          }
          return rawValue;
        }
      }
    }
  }

  public class WizardHandler extends AbstractFormHandler {
    @Override
    protected void execPostLoad() {
      getForegroundColorField().requestFocus();
    }
  }
}
