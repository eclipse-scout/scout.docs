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
import org.eclipse.scout.rt.shared.TEXTS;
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

public class ButtonForm extends AbstractForm implements IPageForm {

  public ButtonForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Buttons");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the ButtonFieldButton
   */
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

  /**
   * @return the DefaultLinkButton
   */
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

  /**
   * @return the IconIdField
   */
  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  /**
   * @return the LabelField
   */
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
  public class MainBox extends AbstractGroupBox {

    @Order(10)
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
      public class ButtonGroupBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ButtonField");
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
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
          protected String getConfiguredKeyStroke() {
            return "ctrl-d";
          }
        }

        @Order(20)
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
          public class MenuItem1 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 1";
            }

            @Override
            protected void execAction() {
              System.out.println("'Menu item 1' clicked");
            }
          }

          @Order(20)
          public class MenuItem2 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 2";
            }

            @Override
            protected void execAction() {
              System.out.println("'Menu item 2' clicked");
            }
          }
        }

        @Order(40)
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
        }

      }

      @Order(20)
      public class ToggleButtonGroupBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToggleButton");
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
        public class ToggleButtonDefaultField extends AbstractButton {

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

        @Order(20)
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
      public class LinkButtonGroupBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LinkButton");
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
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
          protected void execClickAction() {
            MessageBoxes.createOk().withHeader(TEXTS.get("LinkClicked", getLabel())).withBody(TEXTS.get("LinkButtonExecClickAction")).show();
          }
        }

        @Order(20)
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
          public class MenuItem1 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 1";
            }

            @Override
            protected void execAction() {
              System.out.println("'Menu item 1' clicked");
            }
          }

          @Order(20)
          public class MenuItem2 extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Menu item 2";
            }

            @Override
            protected void execAction() {
              System.out.println("'Menu item 2' clicked");
            }
          }
        }

        @Order(40)
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
        }

      }
    }

    @Order(20)
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
      public class LabelField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Label");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getButtonFieldButton().setLabel(getValue());
        }
      }

      @Order(40)
      public class IconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IconId");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
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
      public class Place1Field extends AbstractPlaceholderField {
      }

      @Order(70)
      public class Place2Field extends AbstractPlaceholderField {
      }

      @Order(80)
      public class Place3Field extends AbstractPlaceholderField {
      }

      @Order(90)
      public class Place4Field extends AbstractPlaceholderField {
      }

      @Order(100)
      public class Place5Field extends AbstractPlaceholderField {
      }

      @Order(110)
      public class Place6Field extends AbstractPlaceholderField {
      }
    }

    @Order(70)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
