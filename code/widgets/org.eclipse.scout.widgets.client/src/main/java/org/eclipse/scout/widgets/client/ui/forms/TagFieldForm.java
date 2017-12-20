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

import java.util.Arrays;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tagfield.AbstractTagField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ConfigurationBox.EnabledField;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.TagFieldForm.MainBox.ExamplesBox.MandatoryField;

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

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public EnabledField getEnabledField() {
    return getFieldByClass(EnabledField.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class DefaultField extends AbstractTagField {

        @Override
        protected String getConfiguredLabel() {
          return "Default";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return TagsLookupCall.class;
        }
      }

      @Order(20)
      public class MandatoryField extends AbstractTagField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mandatory");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }
      }

      @Order(30)
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
        protected void execInitField() {
          setTags(Arrays.asList("frühling", "gemeinsam bsi 2017", "prinzessin"));
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(1000)
      @ClassId("e2b37a46-6e43-4f5e-9bfe-f0eddf31f123")
      public class EnabledField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected void execChangedValue() {
          getDisabledField().setEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(false);
        }
      }
    }

    @Order(40)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(50)
    public class SampleFormatButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleFormat");
      }

      @Override
      protected void execClickAction() {
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }
}
