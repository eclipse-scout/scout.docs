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
package org.eclipse.scout.contacts.client;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.client.OptionsForm.MainBox.GroupBox.ThemeRadioButtonGroup;
import org.eclipse.scout.contacts.shared.UiThemeCodeType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

public class OptionsForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Options");
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ThemeRadioButtonGroup getThemeRadioButtonGroup() {
    return getFieldByClass(ThemeRadioButtonGroup.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class ThemeRadioButtonGroup extends AbstractRadioButtonGroup<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UiTheme");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return UiThemeCodeType.class;
        }

        @Override
        protected int getConfiguredGridH() {
          return 1;
        }
      }
    }

    @Order(10.0)
    public class ApplyButton extends AbstractOkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Apply");
      }

      @Override
      protected void execClickAction() {
        getDesktop().setTheme(getThemeRadioButtonGroup().getValue());
      }
    }
  }

  @Override
  protected void execInitForm() {
    String theme = StringUtility.nvl(getDesktop().getTheme(), UiThemeCodeType.DefaultCode.ID);
    getThemeRadioButtonGroup().setValue(theme);
  }
}
