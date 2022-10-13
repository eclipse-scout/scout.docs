/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.GridData;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.mode.AbstractMode;
import org.eclipse.scout.rt.client.ui.form.fields.mode.IMode;
import org.eclipse.scout.rt.client.ui.form.fields.modeselector.AbstractModeSelectorField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ConfigurationBox.ConfigurableModeSelectorGroup;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ConfigurationBox.ValueField;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ExamplesBox.DefaultGroup;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("c077aa41-6fac-4e0b-9e0d-7dcf7142b5b5")
public class ModeSelectorForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ModeSelector");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultGroup getDefaultGroup() {
    return getFieldByClass(DefaultGroup.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ValueField getValueField() {
    return getFieldByClass(ValueField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public ConfigurableModeSelectorGroup getConfigurableModeSelectorGroup() {
    return getFieldByClass(ConfigurableModeSelectorGroup.class);
  }

  public ConfigurationBox.FieldStyleField getFieldStyleField() {
    return getFieldByClass(ConfigurationBox.FieldStyleField.class);
  }

  @Order(10)
  @ClassId("510d1e1e-01b9-4c88-a170-a0e38b982610")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 2;
    }

    @Order(10)
    @ClassId("bdbfebfa-313f-424c-8b21-8722c8b9d8f9")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("9c2dff5e-9d02-4d46-bc9e-c470c465788a")
      public class DefaultModeSelectorGroup extends AbstractExampleModeSelectorGroup {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredFieldStyle() {
          return FIELD_STYLE_CLASSIC;
        }
      }

      @Order(20)
      @ClassId("bf300b23-d5e0-4bdc-8fb6-a428cd076269")
      public class AlternativeModeSelectorGroup extends AbstractExampleModeSelectorGroup {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Alternative");
        }

        @Override
        protected String getConfiguredFieldStyle() {
          return FIELD_STYLE_ALTERNATIVE;
        }
      }

      @Order(30)
      @ClassId("1768af74-bb07-40ac-87ed-dcda0d4e15a0")
      public class DisabledModeSelectorGroup extends AbstractExampleModeSelectorGroup {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(40)
      @ClassId("de0ac5ca-587c-44b5-a83f-00d44aaf14e2")
      public class StyledModeSelectorGroup extends AbstractExampleModeSelectorGroup {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Replace
        @ClassId("bfabde20-94ab-4b75-a5c6-4c8c2bf4aa03")
        public class Mode1Ex extends Mode1 {

          @Override
          protected String getConfiguredIconId() {
            return Icons.Alarmclock;
          }

          @Override
          protected String getConfiguredText() {
            return null;
          }
        }

        @Replace
        @ClassId("668efc37-43be-4843-a9a8-f4a727d10a8f")
        public class Mode2Ex extends Mode2 {

          @Override
          protected String getConfiguredIconId() {
            return Icons.Eye;
          }

          @Override
          protected String getConfiguredText() {
            return null;
          }
        }

        @Replace
        @ClassId("6143a1a1-9a09-4ad9-8cf7-cd25e2dab741")
        public class Mode3Ex extends Mode3 {

          @Override
          protected String getConfiguredIconId() {
            return Icons.Bookmark;
          }

          @Override
          protected String getConfiguredText() {
            return null;
          }
        }
      }
    }

    @Order(20)
    @ClassId("384c834b-edc3-4bdc-8826-bf2cc5773726")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("c4aebf1e-50e6-4079-a7ad-9de587154c83")
      public class ConfigurableModeSelectorGroup extends AbstractModeSelectorField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ModeSelector");
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Order(10)
        @ClassId("6345767c-2da7-4720-94f4-0455465f202f")
        public class Mode1 extends AbstractMode<String> {

          @Override
          protected String getConfiguredText() {
            return "Mode 1";
          }
        }

        @Order(20)
        @ClassId("528fb2f4-e1a1-4797-9d59-cdbd378eb95b")
        public class Mode2 extends AbstractMode<String> {

          @Override
          protected String getConfiguredText() {
            return "Mode 2";
          }
        }

        @Order(30)
        @ClassId("91b2e429-fee3-4ec6-aede-fbcd07e4e6d9")
        public class Mode3 extends AbstractMode<String> {

          @Override
          protected String getConfiguredText() {
            return "Mode 3";
          }
        }

        @Order(10)
        @ClassId("4fae83c5-96ac-46ed-9e61-4abe5d0751fc")
        public class DeselectMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Clear selection";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(
                ValueFieldMenuType.Null,
                ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execAction() {
            ConfigurableModeSelectorGroup.this.setValue(null);
          }
        }
      }

      @Order(30)
      @ClassId("54d7dbb6-8e79-43a5-a6de-a58e0e3679b1")
      public class ValueField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Selected value";
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ConfigurableModeSelectorGroup.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          setValue(getConfigurableModeSelectorGroup().getValue());
        }
      }

      @Order(40)
      @ClassId("5f23a110-08ad-4119-8822-1c05d9703aaa")
      public class WidthInPixelField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Width in pixel";
        }

        @Override
        protected void execInitField() {
          setValue(0);
        }

        @Override
        protected Integer execValidateValue(Integer rawValue) {
          if (rawValue == null || rawValue < 0) {
            return 0;
          }
          return super.execValidateValue(rawValue);
        }

        @Override
        protected void execChangedValue() {
          GridData gdh = getConfigurableModeSelectorGroup().getGridDataHints();
          gdh.widthInPixel = getValue();
          getConfigurableModeSelectorGroup().setGridDataHints(gdh);
          getConfigurationBox().rebuildFieldGrid();
        }
      }

      @Order(50)
      @ClassId("1c3b7ed7-84d9-453c-b17f-a97fa0f3c3c9")
      public class FillHorizontalField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Fill horizontal";
        }

        @Override
        protected void execInitField() {
          setValue(true);
        }

        @Override
        protected void execChangedValue() {
          GridData gdh = getConfigurableModeSelectorGroup().getGridDataHints();
          gdh.fillHorizontal = getValue();
          getConfigurableModeSelectorGroup().setGridDataHints(gdh);
          getConfigurationBox().rebuildFieldGrid();
        }
      }

      @Order(60)
      @ClassId("1870cbd8-0e23-4ae9-9978-579e3eddbe1f")
      public class EnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected void execChangedValue() {
          getConfigurableModeSelectorGroup().setEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getConfigurableModeSelectorGroup().isEnabled());
        }
      }

      @Order(80)
      @ClassId("d3cf37da-232d-4329-b849-6541c95d4924")
      public class FieldStyleField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Alternative";
        }

        @Override
        protected void execInitField() {
          setValue(IFormField.FIELD_STYLE_ALTERNATIVE.equals(getConfigurableModeSelectorGroup().getFieldStyle()));
        }

        @Override
        protected void execChangedValue() {
          getConfigurableModeSelectorGroup().setFieldStyle(getValue() ? IFormField.FIELD_STYLE_ALTERNATIVE : IFormField.FIELD_STYLE_CLASSIC);
        }
      }

      @Order(100)
      @ClassId("649224f6-23eb-44fb-8797-5fcd22ef40d4")
      public class ModeConfigurationBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 4;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
        @ClassId("7b719aa5-e547-4dc5-a385-2c5648ac11d0")
        public class ConfigurationTitleBox extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredBorderVisible() {
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

          @Override
          protected int getConfiguredWidthInPixel() {
            return 100;
          }

          @Override
          protected double getConfiguredGridWeightX() {
            return 0;
          }

          @Order(10)
          @ClassId("3886fc3e-0bff-455f-b4b6-e5b73a485414")
          public class TitleColumnField extends AbstractLabelField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue("");
            }
          }

          @Order(20)
          @ClassId("eb011a22-cb6e-4c2a-aa8b-e0e7f92a8065")
          public class ValueLabelField extends AbstractLabelField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(TEXTS.get("Value"));
            }
          }

          @Order(30)
          @ClassId("5c7f46d2-e407-4d4e-b52e-a0cedd46976f")
          public class IconLabelField extends AbstractLabelField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(TEXTS.get("Icon"));
            }
          }

          @Order(40)
          @ClassId("e8b45bdd-09cf-48e6-a20b-ddd921ef1759")
          public class TextlabelField extends AbstractLabelField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setValue(TEXTS.get("Text"));
            }
          }
        }

        @Order(20)
        @ClassId("6e493923-2bb7-448c-93ad-38f3b1ed3be5")
        public class Mode1ConfigurationBox extends AbstractModeConfigurationBox {

          @Override
          protected int getModeCount() {
            return 0;
          }
        }

        @Order(30)
        @ClassId("c9b94c32-864c-4bed-8007-5d54d959b807")
        public class Mode2ConfigurationBox extends AbstractModeConfigurationBox {

          @Override
          protected int getModeCount() {
            return 1;
          }
        }

        @Order(40)
        @ClassId("3caf3b1e-5a7d-4f98-a552-45786c9ea33a")
        public class Mode3ConfigurationBox extends AbstractModeConfigurationBox {

          @Override
          protected int getModeCount() {
            return 2;
          }
        }
      }
    }

    @Order(30)
    @ClassId("ac9eaf2a-a3ec-4482-a53f-911e142fec59")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  @ClassId("e668b8b7-39a2-4718-abfd-28f6083addd5")
  public abstract class AbstractExampleModeSelectorGroup extends AbstractModeSelectorField<Long> {

    @Override
    protected void execInitField() {
      setValue(1L);
    }

    @Override
    protected int getConfiguredWidthInPixel() {
      return 500;
    }

    @Override
    protected double getConfiguredGridWeightX() {
      return 0;
    }

    @Order(10)
    @ClassId("f4e9874d-7b28-4b5c-a1e4-d13b659dc0ac")
    public class Mode1 extends AbstractMode<Long> {

      @Override
      protected Long getConfiguredRef() {
        return 1L;
      }

      @Override
      protected String getConfiguredText() {
        return "Mode 1";
      }
    }

    @Order(20)
    @ClassId("f5fa384a-b975-4678-bb33-7687468d44ce")
    public class Mode2 extends AbstractMode<Long> {

      @Override
      protected Long getConfiguredRef() {
        return 2L;
      }

      @Override
      protected String getConfiguredText() {
        return "Mode 2";
      }
    }

    @Order(30)
    @ClassId("e8f0af37-71e7-4909-8c7b-5cac2c4eb07b")
    public class Mode3 extends AbstractMode<Long> {

      @Override
      protected Long getConfiguredRef() {
        return 3L;
      }

      @Override
      protected String getConfiguredText() {
        return "Mode 3";
      }
    }
  }

  @ClassId("db4000af-2efa-4a14-8c44-714b90516622")
  public abstract class AbstractModeConfigurationBox extends AbstractGroupBox {

    @Override
    protected boolean getConfiguredBorderVisible() {
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

    @Override
    protected void execInitField() {
      getFieldByClass(ValueModeField.class).setValue(Integer.toString(getModeCount() + 1));
    }

    protected abstract int getModeCount();

    @Order(10)
    @ClassId("cffbc190-8d65-467b-b523-bee4acc1622d")
    public class HeaderLabelField extends AbstractLabelField {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected String getConfiguredFont() {
        return "BOLD";
      }

      @Override
      protected void execInitField() {
        setValue("Mode " + (getModeCount() + 1));
      }
    }

    @Order(20)
    @ClassId("76811c29-e787-4082-8806-f36d7224d8e3")
    public class ValueModeField extends AbstractStringField {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected void execChangedValue() {
        IMode<String> mode = getConfigurableModeSelectorGroup().getModes().get(getModeCount());
        mode.setRef(getValue());
        if (mode.isSelected()) {
          getConfigurableModeSelectorGroup().setValue(getValue());
        }
      }
    }

    @Order(30)
    @ClassId("8679ce8d-3541-4141-9e67-886fe8c6d20d")
    public class IconIdField extends AbstractSmartField<String> {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return IconIdLookupCall.class;
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableModeSelectorGroup().getModes().get(getModeCount()).getIconId());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableModeSelectorGroup().getModes().get(getModeCount()).setIconId(getValue());
      }
    }

    @Order(40)
    @ClassId("6607ed9b-9cf4-44e6-b363-9725d28235aa")
    public class TextField extends AbstractStringField {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableModeSelectorGroup().getModes().get(getModeCount()).getText());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableModeSelectorGroup().getModes().get(getModeCount()).setText(getValue());
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
