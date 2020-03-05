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

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.GridData;
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
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ConfigurationBox.ConfigurableModeSelectorGroup;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ConfigurationBox.ValueField;
import org.eclipse.scout.widgets.client.ui.forms.ModeSelectorForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm.MainBox.ExamplesBox.DefaultGroup;
import org.eclipse.scout.widgets.shared.Icons;

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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 2;
    }

    @Order(10)
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
      public class DefaultModeSelectorGroup extends AbstractExampleModeSelectorGroup {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(20)
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

      @Order(30)
      public class StyledModeSelectorGroup extends AbstractExampleModeSelectorGroup {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Styled");
        }

        @Replace
        public class Mode1Ex extends AbstractExampleModeSelectorGroup.Mode1 {

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
        public class Mode2Ex extends AbstractExampleModeSelectorGroup.Mode2 {

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
        public class Mode3Ex extends AbstractExampleModeSelectorGroup.Mode3 {

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
      public class ConfigurableModeSelectorGroup extends AbstractModeSelectorField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ModeSelector");
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected void execChangedValue() {
          IMode<String> mode = getModeFor(getValue());
          if (mode != null) {
            MessageBoxes.createOk().withHeader(TEXTS.get("ModeSelected", ObjectUtility.nvl(mode.getText(), mode.getIconId()))).show();
          }
        }

        @Order(10)
        public class Mode1 extends AbstractMode<String> {

          @Override
          protected String getConfiguredText() {
            return "Mode 1";
          }
        }

        @Order(20)
        public class Mode2 extends AbstractMode<String> {

          @Override
          protected String getConfiguredText() {
            return "Mode 2";
          }
        }

        @Order(30)
        public class Mode3 extends AbstractMode<String> {

          @Override
          protected String getConfiguredText() {
            return "Mode 3";
          }
        }

        @Order(10)
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

      @Order(100)
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
        public class Mode1ConfigurationBox extends AbstractModeConfigurationBox {

          @Override
          protected int getModeCount() {
            return 0;
          }
        }

        @Order(30)
        public class Mode2ConfigurationBox extends AbstractModeConfigurationBox {

          @Override
          protected int getModeCount() {
            return 1;
          }
        }

        @Order(40)
        public class Mode3ConfigurationBox extends AbstractModeConfigurationBox {

          @Override
          protected int getModeCount() {
            return 2;
          }
        }
      }
    }

    @Order(30)
    public class CloseButton extends AbstractCloseButton {
    }
  }

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

    @Override
    protected void execChangedValue() {
      IMode<Long> mode = getModeFor(getValue());
      if (mode != null) {
        MessageBoxes.createOk().withHeader(TEXTS.get("ModeSelected", ObjectUtility.nvl(mode.getText(), mode.getIconId()))).show();
      }
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
    public class IconIdField extends AbstractSmartField<String> {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
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
