/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.hybrid;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractWidgetField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.label.AbstractLabel;
import org.eclipse.scout.rt.client.ui.label.ILabel;
import org.eclipse.scout.rt.dataobject.IDataObjectMapper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.HybridDescriptionField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.HybridDescriptionField.HybridDescriptionLabel;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.OpenFormBox;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.OpenFormBox.OpenFormDescriptionField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.OpenFormBox.OpenFormDescriptionField.OpenFormDescriptionLabel;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.OpenFormBox.OpenPersonFormField;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.OpenFormBox.OpenPersonFormField.OpenPersonFormLabel;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridForm.MainBox.DetailBox.OpenFormBox.OpenPersonFormPersonDoField;
import org.json.JSONObject;

@ClassId("23172110-d101-4b1c-b4a7-7908c7c6a8c9")
public class HybridForm extends AbstractForm {

  public HybridForm() {
    this(true);
  }

  protected HybridForm(boolean callInitializer) {
    super(callInitializer);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  public HybridDescriptionField getHybridDescriptionField() {
    return getFieldByClass(HybridDescriptionField.class);
  }

  public HybridDescriptionLabel getHybridDescriptionLabel() {
    return getWidgetByClass(HybridDescriptionLabel.class);
  }

  public OpenFormBox getOpenFormBox() {
    return getFieldByClass(OpenFormBox.class);
  }

  public OpenFormDescriptionField getOpenFormDescriptionField() {
    return getFieldByClass(OpenFormDescriptionField.class);
  }

  public OpenFormDescriptionLabel getOpenFormDescriptionLabel() {
    return getWidgetByClass(OpenFormDescriptionLabel.class);
  }

  public OpenPersonFormField getOpenPersonFormField() {
    return getFieldByClass(OpenPersonFormField.class);
  }

  public OpenPersonFormLabel getOpenPersonFormLabel() {
    return getWidgetByClass(OpenPersonFormLabel.class);
  }

  public OpenPersonFormPersonDoField getOpenPersonFormPersonDoField() {
    return getFieldByClass(OpenPersonFormPersonDoField.class);
  }

  @Order(10)
  @ClassId("f86cebfb-87a9-45ee-9ef9-2863555976a4")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("4bebd09b-6295-4b44-b8df-28b2be3e7646")
    public class DetailBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("d1550a1a-0c97-4265-8e96-78e641a3c5aa")
      public class HybridDescriptionField extends AbstractWidgetField<ILabel> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @Order(10)
        @ClassId("7e59b07a-2a65-4422-9920-de04692869d1")
        public class HybridDescriptionLabel extends AbstractLabel {

          @Override
          protected String getConfiguredValue() {
            return HTML.fragment("The page you see here is implemented in Scout JS but its detailForm is implemented in ",
                HTML.bold("Scout Classic"), HTML.br(),
                "This view demonstrates several hybrid abilities like opening a Scout JS form from Scout Classic.")
                .toHtml();
          }

          @Override
          protected boolean getConfiguredHtmlEnabled() {
            return true;
          }
        }
      }

      @Order(20)
      @ClassId("333e0114-f507-4493-acbd-9551935e4a84")
      public class OpenFormBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Open Form";
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
        @ClassId("3b24ebcb-6032-4a59-8d4d-85e5e0950486")
        public class OpenFormDescriptionField extends AbstractWidgetField<ILabel> {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredGridUseUiHeight() {
            return true;
          }

          @Order(10)
          @ClassId("f1926a7e-5fc9-44c2-a55b-84dbab085f01")
          public class OpenFormDescriptionLabel extends AbstractLabel {

            @Override
            protected String getConfiguredValue() {
              return "Open a Scout JS form from Scout Classic. The Java code can wait for specific form events like save or close and work with the data provided by the form.";
            }
          }
        }

        @Order(20)
        @ClassId("d4f75416-eb01-471c-80a7-568ccae2e1fa")
        public class OpenPersonFormField extends AbstractWidgetField<ILabel> {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredGridUseUiHeight() {
            return true;
          }

          @Order(10)
          @ClassId("4cdb1ba1-0227-4cdd-8348-42b4351fd03b")
          public class OpenPersonFormLabel extends AbstractLabel {

            @Override
            protected String getConfiguredValue() {
              return HTML.fragment("Open a ",
                  HTML.appLink("openPersonForm", "PersonForm"),
                  " on the server.").toHtml();
            }

            @Override
            protected boolean getConfiguredHtmlEnabled() {
              return true;
            }

            @Override
            protected void execAppLinkAction(String ref) {
              if ("openPersonForm".equals(ref)) {
                PersonJsForm form = new PersonJsForm();
                IDataObjectMapper dataObjectMapper = BEANS.get(IDataObjectMapper.class);
                form.setInputData(dataObjectMapper.readValue(getOpenPersonFormPersonDoField().getValue(), PersonDo.class));
                form.start();
                form.waitFor();
                if (form.isFormStored()) {
                  getOpenPersonFormPersonDoField().setValue(dataObjectMapper.writeValue(form.getOutputData()));
                }
              }
            }
          }
        }

        @Order(30)
        @ClassId("0cd6add8-e726-41da-919d-f7cbf26c2421")
        public class OpenPersonFormPersonDoField extends AbstractStringField {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected double getConfiguredGridWeightY() {
            return 0;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 1048576;
          }

          @Override
          protected String getConfiguredCssClass() {
            return "json-field";
          }

          @Override
          protected String execValidateValue(String rawValue) {
            String result = super.execValidateValue(rawValue);
            if (!StringUtility.hasText(result)) {
              return result;
            }

            IDataObjectMapper dataObjectMapper = BEANS.get(IDataObjectMapper.class);
            PersonDo personDo = dataObjectMapper.readValue(result, PersonDo.class);
            result = dataObjectMapper.writeValue(personDo);

            JSONObject json = new JSONObject(result);
            result = json.toString(2);

            return result;
          }
        }
      }
    }
  }
}
