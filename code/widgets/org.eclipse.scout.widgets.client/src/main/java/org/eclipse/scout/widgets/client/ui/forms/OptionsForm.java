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

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm.MainBox.GroupBox.DenseRadioButtonGroup;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm.MainBox.GroupBox.UiThemeField;
import org.eclipse.scout.widgets.shared.services.code.UiThemeCodeType;
import org.eclipse.scout.widgets.shared.services.code.UiThemeCodeType.DefaultCode;

public class OptionsForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Options");
  }

  @Override
  protected void execInitForm() {
    String theme = ObjectUtility.nvl(getDesktop().getTheme(), DefaultCode.ID);
    getUiThemeField().setValue(theme);
    getDenseRadioButtonGroup().setValue(getDesktop().isDense());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public DenseRadioButtonGroup getDenseRadioButtonGroup() {
    return getFieldByClass(DenseRadioButtonGroup.class);
  }

  public UiThemeField getUiThemeField() {
    return getFieldByClass(UiThemeField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class UiThemeField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UiTheme");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return UiThemeCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20)
      public class DenseRadioButtonGroup extends AbstractRadioButtonGroup<Boolean> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Layout");
        }

        @Order(10)
        public class DefaultButton extends AbstractRadioButton<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected Boolean getConfiguredRadioValue() {
            return Boolean.FALSE;
          }
        }

        @Order(20)
        public class DenseButton extends AbstractRadioButton<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Dense");
          }

          @Override
          protected Boolean getConfiguredRadioValue() {
            return Boolean.TRUE;
          }
        }
      }
    }

    @Order(10)
    public class OkButton extends AbstractOkButton {
    }

    @Order(20)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      if (getUiThemeField().isSaveNeeded()) {
        getDesktop().setTheme(getUiThemeField().getValue());
      }
      if (getDenseRadioButtonGroup().isSaveNeeded()) {
        getDesktop().setDense(getDenseRadioButtonGroup().getValue());
      }
    }
  }
}
