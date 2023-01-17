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
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardLabelForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardLabelForm.MainBox.GroupBox.LoremField;

@ClassId("d3df2fa6-a1e4-41c6-9132-e5423fc4ca26")
public class LabelWizardLabelForm extends AbstractForm {

  public void startWizard() {
    startInternal(new WizardHandler());
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public LoremField getLoremField() {
    return getFieldByClass(LoremField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("89119e22-459a-43b7-9a44-e9507a3083b8")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("399ea64c-91e5-44e4-95fc-7278d3a0b0f2")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("e6dc11e8-b923-40e8-98c1-9b9c5cd780a0")
      public class LoremField extends AbstractLabelField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected void execInitField() {
          this.setValue(TEXTS.get("Lorem"));
        }
      }
    }
  }

  public class WizardHandler extends AbstractFormHandler {
  }
}
