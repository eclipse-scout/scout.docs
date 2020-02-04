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
package org.eclipse.scout.contacts.client.common;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.PictureUrlForm.MainBox.UrlBox.InfoField;
import org.eclipse.scout.contacts.client.common.PictureUrlForm.MainBox.UrlBox.UrlField;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;

// tag::all[]
@ClassId("3b30ebf1-e8fe-4dd3-8124-5f5038b1d47c")
public class PictureUrlForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PictureURL");
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public UrlField getUrlField() {
    return getFieldByClass(UrlField.class);
  }

  public InfoField getInfoField() {
    return getFieldByClass(InfoField.class);
  }

  @Order(10)
  @ClassId("6c5e0da2-cf04-402f-9784-43e3a138796b")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("fdcc7087-a693-45e8-a889-3725b0995558")
    public class UrlBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("32b71aa6-1109-4b39-996f-f35a677faa06")
      public class UrlField extends AbstractStringField {

        @Override
        protected boolean getConfiguredLabelVisible() { // <1>
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }
      }

      @Order(20)
      @ClassId("999c32e9-ca87-4b5c-a907-29d7a7400abf")
      public class InfoField extends AbstractHtmlField {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @Override
        protected void execInitField() {
          setValue(HTML.fragment(HTML.icon(Icons.Info), HTML.bold(" " + TEXTS.get("PleaseNote") + ": "), TEXTS.get("SecurityUrlRestrictedMsg")).toHtml());
        }
      }
    }

    @Order(20)
    @ClassId("4e15ce0e-502c-4290-aeca-e83359f3bc5b")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30)
    @ClassId("f278815a-f4cf-4e86-a057-66cb7ce43fc3")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler { // <2>
  }
}
// end::all[]
