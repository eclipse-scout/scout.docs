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
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardSizeForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizardSizeForm.MainBox.GroupBox.SizeField;

@ClassId("8c2fbb99-2ac5-44db-8ed5-501408ccc8aa")
public class LabelWizardSizeForm extends AbstractForm {

  public void startWizard() {
    startInternal(new WizardHandler());
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SizeField getSizeField() {
    return getFieldByClass(SizeField.class);
  }

  @Order(10)
  @ClassId("35939ede-02c0-4297-bb44-2b7e33d2d0a1")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("e79f7f03-e866-4966-89cb-e803e1fda5d6")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("ce6a976b-bd87-4326-9290-76944331b020")
      public class SizeField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Size");
        }

        @Override
        protected void execInitField() {
          setValue(14);
        }
      }
    }
  }

  public class WizardHandler extends AbstractFormHandler {
    @Override
    protected void execPostLoad() {
      getSizeField().requestFocus();
    }
  }
}
