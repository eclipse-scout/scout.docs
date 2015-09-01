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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.pagefield.AbstractPageField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.widgets.client.services.lookup.PageLookupCall;
import org.eclipsescout.demo.widgets.client.ui.desktop.pages.PageWithDetailForm;
import org.eclipsescout.demo.widgets.client.ui.forms.PageFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.PageFieldForm.MainBox.PageFieldBox;
import org.eclipsescout.demo.widgets.client.ui.forms.PageFieldForm.MainBox.PageFieldBox.PageField;

public class PageFieldForm extends AbstractForm implements IPageForm {

  public PageFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public PageFieldBox getPageFieldBox() {
    return getFieldByClass(PageFieldBox.class);
  }

  public PageField getPageField() {
    return getFieldByClass(PageField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class SelectorBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("PageSelector");
      }

      @Order(10.0)
      public class InnerPagesField extends AbstractSmartField<IPageWithTable> {

        @Override
        protected int getConfiguredGridW() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InnerPage");
        }

        @Override
        protected Class<? extends ILookupCall<IPageWithTable>> getConfiguredLookupCall() {
          return PageLookupCall.class;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setDisplayText("PageWithDetailForm");
          getPageFieldBox().setInnerPage(new PageWithDetailForm());

          if (UserAgentUtility.isWebClient()) {
            setEnabled(false);
          }
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          if (getValue() instanceof AbstractPage) {
            getPageFieldBox().setInnerPage((AbstractPage) getValue());
          }
        }
      }
    }

    @Order(20.0)
    public class PageFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("PageWithADetailForm");
      }

      public void setInnerPage(IPage page) {
        setTitle(page.getClass().getName());
        getPageField().setPage(page);
      }

      @Order(10.0)
      public class PageField extends AbstractPageField<IPage> {

      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
