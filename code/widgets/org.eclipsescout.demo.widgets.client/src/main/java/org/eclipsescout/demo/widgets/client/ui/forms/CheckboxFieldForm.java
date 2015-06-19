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

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.FontStyleLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ConfigurationBox.CheckboxField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ConfigurationBox.FontNameField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ConfigurationBox.FontStyleField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ConfigurationBox.IsCheckedField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm.MainBox.ExamplesBox.DisabledField;

public class CheckboxFieldForm extends AbstractForm implements IPageForm {

  public CheckboxFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LabelField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the DefaultField
   */
  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  /**
   * @return the CheckboxField
   */
  public CheckboxField getCheckboxField() {
    return getFieldByClass(CheckboxField.class);
  }

  /**
   * @return the FontNameField
   */
  public FontNameField getFontNameField() {
    return getFieldByClass(FontNameField.class);
  }

  /**
   * @return the FontStyleField
   */
  public FontStyleField getFontStyleField() {
    return getFieldByClass(FontStyleField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DisabledField
   */
  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  /**
   * @return the IsCheckedField
   */
  public IsCheckedField getIsCheckedField() {
    return getFieldByClass(IsCheckedField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class DefaultField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(20.0)
      public class DisabledField extends AbstractCheckBox {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setChecked(true);
        }
      }
    }

    @Order(30.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return true;
      }

      @Order(10.0)
      public class CheckboxField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Checkbox");
        }
      }

      @Order(20.0)
      public class IsCheckedField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IsChecked");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return CheckboxFieldForm.MainBox.ConfigurationBox.CheckboxField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          setValue(Boolean.toString(getCheckboxField().isChecked()));
        }
      }

      @Order(30.0)
      public class FontNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Font");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }
      }

      @Order(40.0)
      public class FontStyleField extends AbstractSmartField<Integer> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FontStyle");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<Integer>>) FontStyleLookupCall.class;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          updateFontAndColors();
        }
      }

      private void updateFontAndColors() {
        String name = StringUtility.nvl(getFontNameField().getValue(), "");
        int style = NumberUtility.nvl(getFontStyleField().getValue(), 0);
        FontSpec fs = new FontSpec(name, style, 0);

        getCheckboxField().setFont(fs);
      }
    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
