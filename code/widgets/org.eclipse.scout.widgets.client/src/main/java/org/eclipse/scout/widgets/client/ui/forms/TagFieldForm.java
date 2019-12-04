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

import java.util.Arrays;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tagfield.AbstractTagField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox;

@ClassId("a057c8a8-2294-47c8-b1bd-3536f2004dac")
public class TagFieldForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TagField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("eacc23c3-f401-4b25-b9a6-b2b66412adf3")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("27be1025-d786-40a7-9c67-875ac3dab6e3")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("10c86694-04a5-4beb-a266-9cd2ad2d1eed")
      public class DefaultField extends AbstractTagField {

        @Override
        protected String getConfiguredLabel() {
          return "Default";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return TagsLookupCall.class;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }
      }

      @Order(20)
      @ClassId("7b8a68b6-5505-4d8b-af1f-caed1b58b374")
      public class DisabledField extends AbstractTagField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }
      }
    }

    @Order(300)
    @ClassId("348f40aa-aef3-419b-aaa6-835a845ab8c9")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setFormField(getDefaultField());
      }
    }

    @Order(40)
    @ClassId("d8f5932a-245a-4fe1-a5c2-4916ef9e5653")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(50)
    @ClassId("99e132be-3de2-4ad9-b783-ee99d2bd27c8")
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getDefaultField().setTags(Arrays.asList("hello", "scout is awesome", "princess", "ՄИȈҀӪÐε ௵", "♥"));
        getDisabledField().setTags(Arrays.asList("hello", "scout is awesome", "princess", "ՄИȈҀӪÐε ௵", "♥"));
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
