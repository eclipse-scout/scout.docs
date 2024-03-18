/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.svg.client.SVGUtility;
import org.eclipse.scout.rt.svg.client.svgfield.AbstractSvgField;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.ConfigurationBox.SvgSourceField;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.ConfigurationBox.UserSvgField;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.SvgFieldForm.MainBox.SampleContentButton;
import org.w3c.dom.svg.SVGCircleElement;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGLength;

@Order(8000.0)
@ClassId("72f6e032-ce4e-4bf0-8851-e4fef40ea5a7")
public class SvgFieldForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SvgField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public SvgSourceField getSvgSourceField() {
    return getFieldByClass(SvgSourceField.class);
  }

  public UserSvgField getUserSvgField() {
    return getFieldByClass(UserSvgField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  @Order(10)
  @ClassId("8d623dc9-f589-4c19-b427-c52893d7f4b5")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("430d5700-b8cf-4538-b7cf-14b4b240672d")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("8e198d9a-122b-409c-b9c2-47e37880f842")
      public class DefaultField extends AbstractSvgField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected void execInitField() {
          setSvgDocument(loadDocument("svg/SvgLogo.svg"));
        }
      }

      @Order(20)
      @ClassId("db36a34a-9b06-4f25-8ba5-06d5521f484b")
      public class DisabledField extends AbstractSvgField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected void execInitField() {
          setSvgDocument(loadDocument("svg/World_map.svg"));
        }
      }
    }

    @Order(20)
    @ClassId("5c683021-fba8-4dd3-b56c-e43b28ccda85")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("37ad5044-6a07-49a9-a057-f5efe27dd693")
      public class UserSvgField extends AbstractSvgField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UserSVG");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return SvgSourceField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {

          getSvgSourceField().clearErrorStatus();

          try {
            getUserSvgField().setSvgDocument(parseDocument((String) newMasterValue));
          }
          catch (Exception e) {
            e.printStackTrace();
            getSvgSourceField().addErrorStatus(e.getMessage());
          }
        }

        private SVGDocument liveUpdateCircle(SVGDocument doc, String id) {
          SVGCircleElement circle = (SVGCircleElement) doc.getElementById(id);
          SVGLength x = circle.getCx().getBaseVal();
          x.setValue((x.getValue() + 40) % 400);
          return doc;
        }

        @Override
        protected void execAppLinkAction(String ref) {
          if ("circle2".equals(ref)) {
            SVGDocument doc = (SVGDocument) getSvgDocument().cloneNode(true);
            setSvgDocument(liveUpdateCircle(doc, "circle2"));
          }
          else {
            MessageBoxes.createOk().withHeader(TEXTS.get("SVGLink")).withBody(TEXTS.get("SVGLinkMessage")).withBody(ref).show();
          }
        }
      }

      @Order(20)
      @ClassId("abf26522-1331-414a-914a-57731d38ecc5")
      public class SvgSourceField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SvgDocument");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 200000;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }
      }
    }

    @Order(30)
    @ClassId("59bee675-9b18-40ce-9919-bdbe8bf95ca6")
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getSvgSourceField().setValue(TEXTS.get("SvgUserContent"));
      }
    }

    @Order(40)
    @ClassId("4b3b9851-989b-41b6-9405-c66e0c3d2bc2")
    public class CloseButton extends AbstractCloseButton {
    }

    private SVGDocument loadDocument(String resourceName) {
      if (StringUtility.isNullOrEmpty(resourceName)) {
        return null;
      }
      return SVGUtility.readSVGDocument(ResourceBase.class.getResourceAsStream(resourceName));
    }

    private SVGDocument parseDocument(String svgData) {
      if (StringUtility.isNullOrEmpty(svgData)) {
        return null;
      }
      return SVGUtility.readSVGDocument(new ByteArrayInputStream(svgData.getBytes(StandardCharsets.UTF_8)));
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
