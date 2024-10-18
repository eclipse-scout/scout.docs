/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.toggleswitch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractWidgetField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.client.ui.toggleswitch.AbstractToggleSwitch;
import org.eclipse.scout.rt.client.ui.toggleswitch.IToggleSwitch;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.shared.CssClasses;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.WidgetNameAlias;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.SwitchAllCombinationsJsForm;
import org.eclipse.scout.widgets.client.ui.forms.toggleswitch.ToggleSwitchForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.toggleswitch.ToggleSwitchForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.toggleswitch.ToggleSwitchForm.MainBox.WidgetBox;
import org.eclipse.scout.widgets.client.ui.forms.toggleswitch.ToggleSwitchForm.MainBox.WidgetBox.WidgetField;
import org.eclipse.scout.widgets.client.ui.forms.toggleswitch.ToggleSwitchForm.MainBox.WidgetBox.WidgetField.ToggleSwitch;

@ClassId("dbef6814-6019-4277-9f50-e9283869787c")
@WidgetNameAlias("Switch")
public class ToggleSwitchForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Switch";
  }

  @Override
  public void startPageForm() {
    start();
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public WidgetBox getWidgetBox() {
    return getFieldByClass(WidgetBox.class);
  }

  public WidgetField getWidgetField() {
    return getFieldByClass(WidgetField.class);
  }

  public ToggleSwitch getToggleSwitch() {
    return getWidgetField().getWidgetByClass(ToggleSwitch.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  @Order(10)
  @ClassId("e1d3ea18-4563-4bf4-a023-3e9449ca73d6")
  public class MainBox extends AbstractGroupBox {

    // Additional box to increase the padding
    @Order(10)
    @ClassId("fa267c61-eb2e-4a91-ab24-6df34652e03a")
    public class NotificationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredCssClass() {
        return CssClasses.TOP_PADDING_INVISIBLE;
      }

      @Override
      protected void execInitField() {
        setNotification(new Notification("The Switch widget is called \"ToggleSwitch\" in Java."));
      }

      @Override
      protected boolean calcHasVisibleFieldsInternal() {
        return true;
      }
    }

    @Order(20)
    @ClassId("8d8e0843-176d-4a76-9f2e-655ef438567e")
    public class WidgetBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredCssClass() {
        return CssClasses.BOTTOM_PADDING_INVISIBLE;
      }

      @Order(20)
      @ClassId("6417df3b-51fb-4285-a37b-3b97aa4035e5")
      public class WidgetField extends AbstractWidgetField<IToggleSwitch> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredScrollable() {
          return false;
        }

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return false;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @ClassId("f52c960e-54c2-439d-a15d-bfb4ad267ff7")
        public class ToggleSwitch extends AbstractToggleSwitch {

          @Override
          protected String getConfiguredLabel() {
            return "Switch";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Tooltip";
          }
        }
      }
    }

    @Order(30)
    @ClassId("7f669b72-4df7-40b7-aa06-ea434fd2f783")
    public class ConfigurationBox extends AbstractTabBox {

      @Order(10)
      @ClassId("c5e80e7c-1d93-467e-a287-33fc2e2c6043")
      public class PropertiesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Properties";
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Order(10)
        @ClassId("4c5c85a0-caa9-41c0-8314-a87f5ec4c3c5")
        public class PropertiesLeftBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          @ClassId("b81eccf8-ddc0-4e06-8c42-29f0daa3beb8")
          public class EnabledField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Enabled";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setChecked(getToggleSwitch().isEnabled());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setEnabled(isChecked());
            }
          }

          @Order(20)
          @ClassId("7ad407bf-adf7-4a40-9b32-b843c2c31518")
          public class ActivatedField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Activated";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setChecked(getToggleSwitch().isActivated());
              getToggleSwitch().addPropertyChangeListener(IToggleSwitch.PROP_ACTIVATED, event -> {
                boolean activated = BooleanUtility.nvl((Boolean) event.getNewValue());
                try {
                  setValueChangeTriggerEnabled(false);
                  setChecked(activated);
                }
                finally {
                  setValueChangeTriggerEnabled(true);
                }
              });
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setActivated(isChecked());
            }
          }

          @Order(30)
          @ClassId("71a7d617-c972-4378-a879-01490f3ad772")
          public class LabelHtmlEnabledField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Label Html Enabled";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setChecked(getToggleSwitch().isLabelHtmlEnabled());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setLabelHtmlEnabled(isChecked());
            }
          }

          @Order(40)
          @ClassId("2ea10576-f981-4621-9672-d1458ec39aed")
          public class LabelVisibleField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Label Visible";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredTriStateEnabled() {
              return true;
            }

            @Override
            protected void execInitField() {
              setValue(getToggleSwitch().getLabelVisible());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setLabelVisible(getValue());
            }
          }

          @Order(50)
          @ClassId("a5c9a37f-9e84-436e-aa2e-5d6455a03759")
          public class IconVisibleField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Icon Visible";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setChecked(getToggleSwitch().isIconVisible());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setIconVisible(isChecked());
            }
          }

          @Order(60)
          @ClassId("1e491087-424a-4669-ad9a-dece50e305ca")
          public class TabbableField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Tabbable";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execInitField() {
              setChecked(getToggleSwitch().isTabbable());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setTabbable(isChecked());
            }
          }
        }

        @Order(20)
        @ClassId("78680c4f-3468-423d-a07d-a3052cccb430")
        public class PropertiesRightBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Order(10)
          @ClassId("46b53f29-a272-416b-b8a9-d8d9b628391d")
          public class LabelField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "Label";
            }

            @Override
            protected void execInitField() {
              setValue(getToggleSwitch().getLabel());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setLabel(getValue());
            }
          }

          @Order(20)
          @ClassId("915f2938-4169-4726-9b6b-398c26eb1614")
          public class TooltipTextField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "Tooltip Text";
            }

            @Override
            protected void execInitField() {
              setValue(getToggleSwitch().getTooltipText());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setTooltipText(getValue());
            }
          }

          @Order(30)
          @ClassId("e0e983b1-baf0-4d63-a51d-13c9e945ba8d")
          public class DisplayStyleField extends AbstractSmartField<String> {

            @Override
            protected String getConfiguredLabel() {
              return "Display Style";
            }

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return P_ToggleSwitchDisplayStyleLookupCall.class;
            }

            @Override
            protected String getConfiguredDisplayStyle() {
              return DISPLAY_STYLE_DROPDOWN;
            }

            @Override
            protected void execInitField() {
              setValue(getToggleSwitch().getDisplayStyle());
            }

            @Override
            protected void execChangedValue() {
              getToggleSwitch().setDisplayStyle(getValue());
            }
          }
        }
      }
    }

    @Order(40)
    @ClassId("336142a3-fca0-40c0-aa09-3b71d9bfb244")
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(50)
    @ClassId("2cf5d96d-5301-4217-88dc-fe347b1f5af9")
    public class ShowAllCombinationsMenu extends AbstractMenu {

      @Override
      protected byte getConfiguredHorizontalAlignment() {
        return HORIZONTAL_ALIGNMENT_RIGHT;
      }

      @Override
      protected String getConfiguredText() {
        return "Show all combinations";
      }

      @Override
      protected void execAction() {
        SwitchAllCombinationsJsForm form = new SwitchAllCombinationsJsForm();
        form.start();
      }
    }
  }

  @ClassId("f2db39e9-e644-4c10-87c2-fed58e781863")
  public static class P_ToggleSwitchDisplayStyleLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      List<ILookupRow<String>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(IToggleSwitch.DISPLAY_STYLE_DEFAULT, "Default"));
      rows.add(new LookupRow<>(IToggleSwitch.DISPLAY_STYLE_SLIDER, "Slider"));
      return rows;
    }
  }
}
