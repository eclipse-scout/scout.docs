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

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.IRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.EventTypeLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.GetValueField;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.RadioButtonGroup;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.RadioButtonGroup.No1Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.RadioButtonGroup.No2Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.RadioButtonGroup.No3Button;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.ValueButton1Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.ValueButton2Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ConfigurationBox.ValueButton3Field;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ExamplesBox.DefaultGroup;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.ExamplesBox.DisabledGroup;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm.MainBox.SampleContentButton;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType;

public class RadioButtonGroupFieldForm extends AbstractForm implements IPageForm {

  public RadioButtonGroupFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("RadioButtonGroup");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

//  /**
//   * @return the WarningButton
//   */
//  public ActiveButton getActiveButton() {
//    return getFieldByClass(ActiveButton.class);
//  }
//
//  /**
//   * @return the AllButton
//   */
//  public AllButton getAllButton() {
//    return getFieldByClass(AllButton.class);
//  }

  public No1Button getNo1Button() {
    return getFieldByClass(No1Button.class);
  }

  public No2Button getNo2Button() {
    return getFieldByClass(No2Button.class);
  }

  public No3Button getNo3Button() {
    return getFieldByClass(No3Button.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DefaultGroup
   */
  public DefaultGroup getDefaultGroup() {
    return getFieldByClass(DefaultGroup.class);
  }

  /**
   * @return the DisabledGroup
   */
  public DisabledGroup getDisabledGroup() {
    return getFieldByClass(DisabledGroup.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  /**
   * @return the GetCheckedKeysField
   */
  public GetValueField getGetValueField() {
    return getFieldByClass(GetValueField.class);
  }

//  /**
//   * @return the InactiveButton
//   */
//  public InactiveButton getInactiveButton() {
//    return getFieldByClass(InactiveButton.class);
//  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  /**
   * @return the RadioButtonGroup
   */
  public RadioButtonGroup getRadioButtonGroup() {
    return getFieldByClass(RadioButtonGroup.class);
  }

  /**
   * @return the SampleContentButton
   */
  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  public ValueButton1Field getValueButton1Field() {
    return getFieldByClass(ValueButton1Field.class);
  }

  public ValueButton2Field getValueButton2Field() {
    return getFieldByClass(ValueButton2Field.class);
  }

  public ValueButton3Field getValueButton3Field() {
    return getFieldByClass(ValueButton3Field.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class DefaultGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return EventTypeCodeType.class;
        }

        @Override
        protected void execInitField() {
          setValue(EventTypeCodeType.ExternalCode.ID);
        }
      }

      @Order(20)
      public class DisabledGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return EventTypeLookupCall.class;
        }

        @Override
        protected void execInitField() {
          getButtonFor(EventTypeCodeType.PublicCode.ID).setSelected(true);

          // TODO [5.2] jgu: https://bugs.eclipse.org/bugs/show_bug.cgi?id=436497
          for (IRadioButton<Long> button : getButtons()) {
            button.setEnabled(false);
          }
        }
      }

      @Order(30)
      public class StyledGroupBox extends AbstractRadioButtonGroup<Long> {

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Order(10)
        public class RotateLeftButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredIconId() {
            return Icons.RotateLeft;
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return Long.valueOf(-1L);
          }
        }

        @Order(20)
        public class RotateRightButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredIconId() {
            return Icons.RotateRight;
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return Long.valueOf(-2L);
          }
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return true;
      }

      @Order(10)
      public class RadioButtonGroup extends AbstractRadioButtonGroup<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RadioButtonGroup");
        }

        @Override
        protected void execInitField() {
          getNo1Button().setSelected(true);
        }

        @Order(10)
        public class No1Button extends AbstractRadioButton<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Active");
          }
        }

        @Order(20)
        public class No2Button extends AbstractRadioButton<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Inactive");
          }
        }

        @Order(30)
        public class No3Button extends AbstractRadioButton<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("All");
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("ThisIsATooltip");
          }

          @Override
          protected void execClickAction() {
            MessageBoxes.createOk().withHeader(TEXTS.get("RadioButtonSelected", getLabel())).withBody(TEXTS.get("RadioButtonExecClickAction")).show();
          }
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
          return TEXTS.get("GetValue");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return RadioButtonGroupFieldForm.MainBox.ConfigurationBox.RadioButtonGroup.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          Long value = getRadioButtonGroup().getValue();

          if (value != null) {
            setValue(value.toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(40)
      public class ValueButton1Field extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueButton1");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getNo1Button().setRadioValue(getValue());
        }
      }

      @Order(50)
      public class ValueButton2Field extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueButton2");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getNo2Button().setRadioValue(getValue());
        }
      }

      @Order(60)
      public class ValueButton3Field extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueButton3");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getNo3Button().setRadioValue(getValue());
        }
      }
    }

    @Order(30)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(40)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getValueButton1Field().setValue(1L);
        getValueButton2Field().setValue(-1L);
        getValueButton3Field().setValue(42L);
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
