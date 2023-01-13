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
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.pagefield.AbstractPageField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.desktop.pages.PageWithDetailFormTablePage;
import org.eclipse.scout.widgets.client.ui.forms.PageFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.PageFieldForm.MainBox.DetailBox.PageBox;
import org.eclipse.scout.widgets.client.ui.forms.PageFieldForm.MainBox.GroupBoxPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.fields.groupbox.AbstractGroupBoxPropertiesBox;

@ClassId("2c4a6049-64f5-4b94-ba41-f36da90930da")
public class PageFieldForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GroupBoxPropertiesBox getGroupBoxPropertiesBox() {
    return getFieldByClass(GroupBoxPropertiesBox.class);
  }

  public PageBox getPageBox() {
    return getFieldByClass(PageBox.class);
  }

  @Order(10)
  @ClassId("7875768f-d721-4ddb-8eb5-806c6d9cd921")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("0abd8088-f898-4004-8def-504e0159979b")
    public class DetailBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("9347884e-b162-4cf0-8e7e-249786da4814")
      public class PageBox extends AbstractPageField<PageWithDetailFormTablePage> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setPage(new PageWithDetailFormTablePage());
          ((DetailForm) getPage().getDetailForm()).getNavigationButtonInfoField().setVisible(false);
        }
      }

    }

    @Order(150)
    @ClassId("3dcb783c-9171-4e4c-98c0-7d8ad899b299")
    public class GroupBoxPropertiesBox extends AbstractGroupBoxPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getPageBox());
        // Fields inside the page field are configured to use FULL_WIDTH -> changing the grid column count would not have any effect
        getFieldByClass(GridColumnCountField.class).setVisible(false);
      }

    }

    @ClassId("8674389c-6636-4752-9c72-10249de56dc4")
    @Order(200)
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getPageBox());
      }
    }

    @Order(300)
    @ClassId("04118b1d-2633-4f18-8b9a-bfd260bde2af")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
