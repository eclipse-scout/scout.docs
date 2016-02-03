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
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.ModelVariant;
import org.eclipse.scout.rt.client.ui.form.fields.beanfield.AbstractBeanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.AppLink;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.BeanFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.BeanFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.BeanFieldForm.MainBox.ExamplesBox.ExampleBeanField;

@Order(6500)
public class BeanFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public BeanFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BeanField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ExampleBeanField getExampleBeanField() {
    return getFieldByClass(ExampleBeanField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(1000)
      @ClassId("81b27554-42f2-4fb9-99f6-a86c17b91d1b")
      @ModelVariant("Example")
      public class ExampleBeanField extends AbstractBeanField<ExampleBean> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BeanField");
        }

        @Override
        protected void execInitField() {
          ExampleBean bean = new ExampleBean();
          bean.setHeader(TEXTS.get("ExampleBeanFieldHeader"));
          bean.setDescription(TEXTS.get("ExampleBeanFieldDescription"));
          bean.setAppLink(new AppLink("exampleRef", TEXTS.get("ExampleBeanFieldAppLink")));
          setValue(bean);
        }

        @Override
        protected void execAppLinkAction(String ref) {
          MessageBoxes.createOk().withBody(TEXTS.get("ExampleBeanFieldAppLinkClicked", ref)).show();
        }
      }

    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
