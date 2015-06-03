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
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ConfigurationBox.ButtonFieldButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ConfigurationBox.GetValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ConfigurationBox.IconIdField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ConfigurationBox.LabelField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.ButtonColumnField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.DefaultLinkButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.DisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.LinkField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.ToggleButtonDefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.ToggleButtonDisabledField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.ToggleButtonField;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm.MainBox.ExamplesBox.ToggleButtonStyledField;
import org.eclipsescout.demo.widgets.shared.Icons;

public class ButtonLinkFieldsForm extends AbstractForm implements IPageForm {

  public ButtonLinkFieldsForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ButtonsLinks");
  }

  @Override
  public void startPageForm() throws ProcessingException {
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

  public ButtonColumnField getButtonColumnField() {
    return getFieldByClass(ButtonColumnField.class);
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

  public ToggleButtonField getToggleButtonField() {
    return getFieldByClass(ToggleButtonField.class);
  }

  public ToggleButtonDisabledField getToggleButtonDisabledField() {
    return getFieldByClass(ToggleButtonDisabledField.class);
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

  public LinkField getLinkField() {
    return getFieldByClass(LinkField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(50.0)
      public class ButtonColumnField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ButtonField");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "BOLD";
        }
      }

      @Order(60.0)
      public class DefaultField extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(70.0)
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

      @Order(80.0)
      public class StyledField extends AbstractButton {

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return true;
        }

        @Override
        protected String getConfiguredIconId() {
          return Icons.StarRed;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }

      @Order(90.0)
      public class ToggleButtonField extends AbstractLabelField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToggleButton");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "BOLD";
        }
      }

      @Order(100.0)
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
        protected void execSelectionChanged(boolean selected) throws ProcessingException {
          if (selected) {
            setLabel(TEXTS.get("Selected"));
          }
          else {
            setLabel(TEXTS.get("PushToSelect"));
          }
        }
      }

      @Order(110.0)
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
        protected void execInitField() throws ProcessingException {
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

      @Order(120.0)
      public class ToggleButtonStyledField extends AbstractButton {

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_TOGGLE;
        }

        @Override
        protected String getConfiguredIconId() {
          return Icons.StarRed;
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("ButtonWithIconOnly");
        }
      }

      @Order(130.0)
      public class LinkField extends AbstractLabelField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LinkButton");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "BOLD";
        }
      }

      @Order(140.0)
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
        protected void execClickAction() throws ProcessingException {
          MessageBox.showOkMessage(TEXTS.get("LinkClicked", getLabel()), null, TEXTS.get("LinkButtonExecClickAction"));
        }
      }

      @Order(160.0)
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
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
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
        protected void execSelectionChanged(boolean selected) throws ProcessingException {
          getGetValueField().setValue(Boolean.toString(selected));
        }
      }

      @Order(20.0)
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

      @Order(30.0)
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
        protected void execChangedValue() throws ProcessingException {
          getButtonFieldButton().setLabel(getValue());
        }
      }

      @Order(40.0)
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
        protected void execChangedValue() throws ProcessingException {
          getButtonFieldButton().setIconId(getValue());
        }
      }

      @Order(60.0)
      public class Place1Field extends AbstractPlaceholderField {
      }

      @Order(70.0)
      public class Place2Field extends AbstractPlaceholderField {
      }

      @Order(80.0)
      public class Place3Field extends AbstractPlaceholderField {
      }

      @Order(90.0)
      public class Place4Field extends AbstractPlaceholderField {
      }

      @Order(100.0)
      public class Place5Field extends AbstractPlaceholderField {
      }

      @Order(110.0)
      public class Place6Field extends AbstractPlaceholderField {
      }
    }

    @Order(70.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
