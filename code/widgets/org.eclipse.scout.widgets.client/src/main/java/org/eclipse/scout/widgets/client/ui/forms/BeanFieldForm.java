/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.ModelVariant;
import org.eclipse.scout.rt.client.ui.form.fields.beanfield.AbstractBeanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.html.AppLink;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.BeanFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.BeanFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.BeanFieldForm.MainBox.ExamplesBox.ExampleBeanField;

@Order(6500)
@ClassId("2b143f26-75a5-4975-99b4-66673ff1d3ef")
public class BeanFieldForm extends AbstractForm implements IAdvancedExampleForm {

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

  public BinaryResource getSampleImage() {
    URL url = ResourceBase.class.getResource("images/bird_1008.jpg");
    try (InputStream in = url.openStream()) {
      byte[] content = IOUtility.readBytes(in);
      return new BinaryResource("bird.jpg", content);
    }
    catch (IOException e) {
      throw new ProcessingException("", e);
    }
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
  @ClassId("77e90765-7a95-40a9-8493-859d581bff03")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("4a7ff80a-8ba0-40e3-b967-ff5d0d96e61d")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(1000)
      @ClassId("81b27554-42f2-4fb9-99f6-a86c17b91d1b")
      @ModelVariant("widgets.Example")
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
          bean.setImage(getSampleImage());
          setValue(bean);
        }

        @Override
        protected void execAppLinkAction(String ref) {
          MessageBoxes.createOk().withBody(TEXTS.get("ExampleBeanFieldAppLinkClicked", ref)).show();
        }

        @Override
        protected int getConfiguredGridH() {
          return 12;
        }
      }
    }

    @Order(50)
    @ClassId("a3c85e0b-38e7-4471-9570-684f828aaa65")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
