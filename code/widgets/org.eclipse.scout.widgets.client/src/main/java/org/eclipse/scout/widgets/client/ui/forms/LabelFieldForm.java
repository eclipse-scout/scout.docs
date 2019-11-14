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
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.LabelFieldForm.MainBox.CloseButton;

@ClassId("6d611416-0959-41d6-9230-44cf6bcdc102")
public class LabelFieldForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LabelField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @Order(10)
  @ClassId("ab93271a-d9b7-44d8-acc5-32b1c5215f8d")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 2;
    }

    @Order(10)
    @ClassId("3d01b817-448f-475d-ae4e-e8f8841b96b3")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("e72fd12f-f40c-4f32-9676-ed34b665d694")
      public class LabelField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(20)
      @ClassId("5d127d04-241c-47df-82be-cc8142243311")
      public class DisabledField extends AbstractLabelField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }
      }

      @Order(30)
      @ClassId("51d49100-34d7-45fc-9e29-45379cfdff23")
      public class StyledField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "BOLD-ITALIC";
        }

        @Override
        protected String getConfiguredLabelForegroundColor() {
          return "FF0000";
        }
      }

      @Order(40)
      @ClassId("977b4dce-d3d6-407a-88ae-be5d9a629316")
      public class HtmlField extends AbstractLabelField {

        @Override
        protected void execInitField() {
          setValue(HTML.fragment(
            "This field contains ",
            HTML.bold("HTML"),
            " and supports ",
            HTML.appLink("foo-ref", "App links")).toHtml());
        }

        @Override
        protected boolean getConfiguredHtmlEnabled() {
          return true;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execAppLinkAction(String ref) {
          MessageBoxes.createOk().withBody("App link with ref '" + ref + "' clicked").show();
        }
      }
    }

    @Order(30)
    @ClassId("b4ad4e83-5395-4874-b918-a644f879b0da")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("6b495bc0-ccd3-47f6-8656-58ec4dc790f7")
      public class TooLongLabelTextGetsTruncatedField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TooLongLabelTextGetsTruncated");
        }
      }

      @Order(20)
      @ClassId("c3fc696c-f21d-4a0d-9d79-7f05528d7937")
      public class SetValueTextField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return " ";
        }

        @Override
        protected void execInitField() {
          setValue(TEXTS.get("SetValueText"));
        }
      }

      @Order(30)
      @ClassId("c2971b37-22e4-4865-b4c9-5756147aafdd")
      public class MultilineLabelField extends AbstractLabelField {

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MultilineLabel");
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected void execInitField() {
          String value = TEXTS.get("Lorem");
          this.setValue(value);
        }
      }

      @Order(40)
      @ClassId("18baf59a-713d-4e9f-a09e-432c6f9732d4")
      public class VeryLongLabelTextField extends AbstractLabelField {

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }

        @Override
        protected void execInitField() {
          String value = TEXTS.get("Lorem");
          this.setValue(value);
        }
      }
    }

    @Order(40)
    @ClassId("66fe293d-684c-4508-aca6-a0cf9810e79a")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
