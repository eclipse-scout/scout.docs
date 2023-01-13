/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.wrappedform.AbstractWrappedFormField;
import org.eclipse.scout.rt.client.ui.wizard.IWizardContainerForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.WizardForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.WizardForm.MainBox.FormField;
import org.eclipse.scout.widgets.client.ui.wizards.LabelWizard;

@ClassId("c5120d6c-9167-4b70-a5f9-5a3863eedfe3")
public class WizardForm extends AbstractForm implements IAdvancedExampleForm {

  private LabelWizard m_wizard;

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  protected void execInitForm() {
    m_wizard = new LabelWizard();
    m_wizard.getContainerForm().setDisplayHint(IForm.DISPLAY_HINT_VIEW);
    m_wizard.getContainerForm().setDisplayViewId(IForm.VIEW_ID_PAGE_DETAIL);
    getFormField().setInnerForm(m_wizard.getContainerForm());
    m_wizard.start();
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public FormField getFormField() {
    return getFieldByClass(FormField.class);
  }

  @Order(100)
  @ClassId("084f0bcc-6b3d-435f-8eae-b88c25991f78")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredWidthInPixel() {
      return 675;
    }

    @Override
    protected int getConfiguredHeightInPixel() {
      return 420;
    }

    @Order(100)
    @ClassId("7a4d07d9-587d-4d52-933f-1071e3118662")
    public class FormField extends AbstractWrappedFormField<IWizardContainerForm> {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredStatusVisible() {
        return false;
      }
    }

    @Order(200)
    @ClassId("d7d9d2ae-e1cb-427f-bcc4-9c348df8eb96")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

}
