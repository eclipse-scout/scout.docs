/*******************************************************************************
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.scout.rt.client.ui.basic.breadcrumbbar.BreadcrumbItems;
import org.eclipse.scout.rt.client.ui.basic.breadcrumbbar.IBreadcrumbItem;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.breadcrumbbarfield.AbstractBreadcrumbBarField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.widgets.client.ui.forms.BreadcrumbBarFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.BreadcrumbBarFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.BreadcrumbBarFieldForm.MainBox.ConfigurationBox.BreadcrumbItemsField;
import org.eclipse.scout.widgets.client.ui.forms.BreadcrumbBarFieldForm.MainBox.ConfigurationBox.EnabledField;
import org.eclipse.scout.widgets.client.ui.forms.BreadcrumbBarFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.BreadcrumbBarFieldForm.MainBox.ExamplesBox.BreadcrumbBarField;

public class BreadcrumbBarFieldForm extends AbstractForm implements IPageForm {
  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  public EnabledField getEnabledField() {
    return getFieldByClass(EnabledField.class);
  }

  public BreadcrumbItemsField getBreadcrumbItemsField() {
    return getFieldByClass(BreadcrumbItemsField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public BreadcrumbBarField getBreadcrumbBarField() {
    return getFieldByClass(BreadcrumbBarField.class);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BreadcrumbBarField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  protected void execInitForm() {
    getBreadcrumbItemsField().setValue("Storyboard\nFolder\nSubfolder");
    getEnabledField().setValue(true);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 2;
    }

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      public class BreadcrumbBarField extends AbstractBreadcrumbBarField {
        @Override
        protected void execBreadcrumbItemAction(IBreadcrumbItem breadcrumbItem) {
          MessageBoxes.createOk()
              .withBody("Clicked breadcrumb with ref " + breadcrumbItem.getRef())
              .show();
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(0)
      @ClassId("432b5da0-0a5f-4f89-a0d8-3572ca4c0b58")
      public class BreadcrumbItemsField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BreadcrumbItems");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected void execChangedValue() {
          String[] items = StringUtility.split(getValue(), "\\n");

          List<IBreadcrumbItem> list = Stream.of(items).map(line -> BreadcrumbItems.create(line, line)).collect(Collectors.toList());
          getBreadcrumbBarField().setBreadcrumbItems(list);
        }
      }

      @Order(1000)
      @ClassId("7409c002-ebd7-4865-82b2-01f04fef3f73")
      public class EnabledField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Enabled");
        }

        @Override
        protected void execChangedValue() {
          getBreadcrumbBarField().setEnabled(this.getValue());
        }
      }
    }

    @Order(30)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
