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

import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.ButtonFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.GetValueField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.IconIdField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.LabelField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ButtonGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ButtonGroupBox.CssStyledButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ButtonGroupBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ButtonGroupBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.LinkButtonGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.LinkButtonGroupBox.CssStyledLinkButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.LinkButtonGroupBox.DefaultLinkButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ToggleButtonGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ToggleButtonGroupBox.ToggleButtonDefaultField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ToggleButtonGroupBox.ToggleButtonDisabledField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ToggleButtonGroupBox.ToggleButtonStyledField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ExamplesBox.ToggleButtonGroupBox.ToggleCssStyledButton;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("6c56d115-b31e-4b0f-94cf-84e3330464dd")
public class ButtonForm extends AbstractForm implements IPageForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Buttons");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public ButtonFieldButton getButtonFieldButton() {
    return getFieldByClass(ButtonFieldButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public GetValueField getGetValueField() {
    return getFieldByClass(GetValueField.class);
  }

  public ButtonGroupBox getButtonGroupBox() {
    return getFieldByClass(ButtonGroupBox.class);
  }

  public DefaultLinkButton getDefaultLinkButton() {
    return getFieldByClass(DefaultLinkButton.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ToggleButtonGroupBox getToggleButtonGroupBox() {
    return getFieldByClass(ToggleButtonGroupBox.class);
  }

  public ToggleButtonDisabledField getToggleButtonDisabledField() {
    return getFieldByClass(ToggleButtonDisabledField.class);
  }

  public CssStyledButton getCssStyledButton() {
    return getFieldByClass(CssStyledButton.class);
  }

  public ToggleCssStyledButton getToggleCssStyledButton() {
    return getFieldByClass(ToggleCssStyledButton.class);
  }

  public CssStyledLinkButton getCssStyledLinkButton() {
    return getFieldByClass(CssStyledLinkButton.class);
  }

  public ToggleButtonDefaultField getToggleButtonDefaultField() {
    return getFieldByClass(ToggleButtonDefaultField.class);
  }

  public ToggleButtonStyledField getToggleButtonStyledField() {
    return getFieldByClass(ToggleButtonStyledField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  public LabelField getLabelField() {
    return getFieldByClass(LabelField.class);
  }

  public LinkButtonGroupBox getLinkButtonGroupBox() {
    return getFieldByClass(LinkButtonGroupBox.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10)
  @ClassId("5bb36d29-e075-49e2-aeb4-f365421eefcc")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("1daee4c6-d470-4836-b8b0-780fa1fab0bd")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("d7d0f345-7e5b-41b1-ba01-da7b4ab49756")
      public class ButtonGroupBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ButtonField");
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(5)
        @ClassId("41c9ec61-23fa-4f10-8423-d38ee99155a1")
        public class RegularField extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Regular");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Regular button has been clicked.").show();
          }
        }

        @Order(10)
        @ClassId("a1798e80-5736-4d94-8900-752cc1be9358")
        public class DefaultField extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected Boolean getConfiguredDefaultButton() {
            return true;
          }

          @Override
          protected String getConfiguredKeyStroke() {
            return "ctrl-d";
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Default button has been clicked.").show();
          }
        }

        @Order(20)
        @ClassId("ad309dcf-9698-4c6c-82fd-c2d83aaf3083")
        public class DisabledField extends AbstractButton {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Disabled");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }
        }

        @Order(30)
        @ClassId("569a557b-98c9-41e8-ae8a-508127c8beb8")
        public class ButtonWithMenuField extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ButtonWithMenu");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.Star;
          }

          @Order(10)
          @ClassId("9c78802e-4e0b-4c50-a0b7-8ed227946d0f")
          public class MenuItem1 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 1";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Menu item 1 has been clicked.").show();
            }
          }

          @Order(20)
          @ClassId("a97bc883-52c0-4f91-a05e-591584a48566")
          public class MenuItem2 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 2";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Menu item 2 has been clicked.").show();
            }
          }
        }

        @Order(40)
        @ClassId("9e922ab4-f6a6-46cd-9e0a-288740aca6b5")
        public class StyledField extends AbstractButton {

          @Override
          protected boolean getConfiguredFillHorizontal() {
            return true;
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Styled");
          }

          @Override
          protected String getConfiguredBackgroundColor() {
            return "FDEEEE";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Styled button has been clicked.").show();
          }
        }

        @Order(2000)
        @ClassId("d10308ef-877b-44ca-9782-6ec9361a4db1")
        public class CssStyledButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CSSStyled");
          }

          @Override
          protected boolean getConfiguredFillHorizontal() {
            return true;
          }

          @Override
          protected String getConfiguredCssClass() {
            return "highlight";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Styled button (CSS) has been clicked.").show();
          }
        }
      }

      @Order(20)
      @ClassId("b0145cad-cec3-42fb-876b-d08cc04ef33c")
      public class ToggleButtonGroupBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToggleButton");
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(5)
        @ClassId("07c3d701-782c-4e6d-819f-f871f8a383fa")
        public class ToggleButtonRegularField extends AbstractButton {

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("PushToSelect");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredKeyStroke() {
            return "F";
          }

          @Override
          protected void execSelectionChanged(boolean selected) {
            if (selected) {
              setLabel(TEXTS.get("Selected"));
            }
            else {
              setLabel(TEXTS.get("PushToSelect"));
            }
          }

          @ClassId("0abdd622-525d-49b1-b233-1834ad0062a2")
          public class FirstKeyStroke extends AbstractKeyStroke {

            @Override
            protected String getConfiguredKeyStroke() {
              return "A";
            }

            @Override
            protected void execAction() {
              System.out.println("A");
            }
          }

          @ClassId("e018f529-06fd-442c-aa4b-fe5939ad2482")
          public class SecondKeyStroke extends AbstractKeyStroke {
            @Override
            protected String getConfiguredKeyStroke() {
              return "B";
            }

            @Override
            protected void execAction() {
              System.out.println("B");
            }
          }
        }

        @Order(10)
        @ClassId("c0453897-5b9f-4211-a7bb-e1bd634e6762")
        public class ToggleButtonDefaultField extends AbstractButton {
          @Override
          protected Boolean getConfiguredDefaultButton() {
            return true;
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("PushToSelect");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execSelectionChanged(boolean selected) {
            if (selected) {
              setLabel(TEXTS.get("Selected"));
            }
            else {
              setLabel(TEXTS.get("PushToSelect"));
            }
          }
        }

        @Order(20)
        @ClassId("f842f04d-e47b-4f31-9fc4-41083440a90e")
        public class ToggleButtonDisabledField extends AbstractButton {

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected void execInitField() {
            setSelected(true);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Selected");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }
        }

        @Order(30)
        @ClassId("94e0d0f0-2e39-4ea2-96dd-5d84161e290a")
        public class ToggleButtonWithMenuField extends AbstractButton {

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Order(10)
          @ClassId("96697f0c-a24b-4743-ab63-ad823cf81e07")
          public class MenuItem extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Hello").show();
            }
          }
        }

        @Order(40)
        @ClassId("c2a36853-b240-409e-b32c-8cbc873debe9")
        public class ToggleButtonStyledField extends AbstractButton {

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredBackgroundColor() {
            return "FDEEEE";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("ButtonWithIconOnly");
          }
        }

        @Order(2000)
        @ClassId("8877629c-ec8c-47bf-af80-285572cb3723")
        public class ToggleCssStyledButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CSSStyled");
          }

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredCssClass() {
            return "highlight";
          }
        }
      }

      @Order(30)
      @ClassId("a588c3fc-cef9-4b1b-882e-2a9191c97bd2")
      public class LinkButtonGroupBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LinkButton");
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(5)
        @ClassId("241ede3c-9d8e-4bff-a9ca-c17a9263419d")
        public class RegularLinkButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Regular");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Regular link button has been clicked.").show();
          }
        }

        @Order(10)
        @ClassId("d8990852-35c3-455a-a61b-4cb3cf0edfd5")
        public class DefaultLinkButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected Boolean getConfiguredDefaultButton() {
            return true;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Default link button has been clicked.").show();
          }
        }

        @Order(20)
        @ClassId("f0e6276b-a76b-4c02-8008-a09d71b3457d")
        public class DisabledLinkButton extends AbstractLinkButton {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Disabled");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }
        }

        @Order(30)
        @ClassId("f1b6ab66-d8e1-4bd1-ad2d-18619b5f09e9")
        public class LinkButtonWithMenuField extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "With menu";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Order(10)
          @ClassId("a074b4a4-4dbc-42bd-a442-8f7b753f22ea")
          public class MenuItem1 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 1";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Menu item 1 has been clicked.").show();
            }
          }

          @Order(20)
          @ClassId("9c5f2971-bf12-45df-895f-fb9977ba3d92")
          public class MenuItem2 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 2";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Menu item 2 has been clicked.").show();
            }
          }
        }

        @Order(40)
        @ClassId("04d0391b-e571-447e-9c03-dacbcba2e632")
        public class StyledLinkButtonField extends AbstractLinkButton {

          @Override
          protected boolean getConfiguredFillHorizontal() {
            return true;
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Styled");
          }

          @Override
          protected String getConfiguredForegroundColor() {
            return "FF0000";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Styled link button has been clicked.").show();
          }
        }

        @Order(2000)
        @ClassId("d4c2acf8-c413-4730-ab53-3ab94c4f19f7")
        public class CssStyledLinkButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CSSStyled");
          }

          @Override
          protected String getConfiguredCssClass() {
            return "highlight";
          }

          @Override
          protected boolean getConfiguredFillHorizontal() {
            return true;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withBody("Styled link button (CSS) has been clicked.").show();
          }
        }
      }
    }

    @Order(20)
    @ClassId("95c8075c-417b-419f-980d-f5539839505e")
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
      @ClassId("a989c7f2-783f-4ad7-bb57-92a994415661")
      public class ButtonFieldButton extends AbstractButton {

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_TOGGLE;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToggleButton");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execSelectionChanged(boolean selected) {
          getGetValueField().setValue(Boolean.toString(selected));
        }
      }

      @Order(20)
      @ClassId("025327b6-a96f-4c36-a007-e2d5504a9f8c")
      public class GetValueField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IsSelected");
        }
      }

      @Order(30)
      @ClassId("72fabebc-e9f8-4ce1-91ff-5639ecc60939")
      public class LabelField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Label");
        }

        @Override
        protected void execChangedValue() {
          getButtonFieldButton().setLabel(getValue());
        }
      }

      @Order(40)
      @ClassId("f9b7286a-22e8-424a-82c7-4b7ce05e573b")
      public class IconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IconId");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getButtonFieldButton().setIconId(getValue());
        }
      }

      @Order(60)
      @ClassId("284e7ee0-b340-4a93-820b-8fadaa411999")
      public class Place1Field extends AbstractPlaceholderField {
      }

      @Order(70)
      @ClassId("c67fc9af-fe66-42e5-9244-bf2d275fd91c")
      public class Place2Field extends AbstractPlaceholderField {
      }

      @Order(80)
      @ClassId("b3916e44-972b-44c3-91eb-9a0084a04a9c")
      public class Place3Field extends AbstractPlaceholderField {
      }
    }

    @Order(70)
    @ClassId("e97e4f37-3ae9-4ec6-aa4b-41d1307edb58")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
