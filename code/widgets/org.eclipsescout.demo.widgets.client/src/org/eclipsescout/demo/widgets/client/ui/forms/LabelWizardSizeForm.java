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
package org.eclipsescout.demo.widgets.client.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.LabelWizardSizeForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.LabelWizardSizeForm.MainBox.GroupBox.SizeField;

public class LabelWizardSizeForm extends AbstractForm {

  public LabelWizardSizeForm() throws ProcessingException {
    super();
  }

  public void startWizard() throws ProcessingException {
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

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected boolean getConfiguredGridUseUiWidth() {
        return true;
      }

      @Order(10.0)
      public class SizeField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Size");
        }
      }
    }
  }

  public class WizardHandler extends AbstractFormHandler {
  }
}
