/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.rt.shared.ui.IUiDeviceType;
import org.eclipse.scout.rt.shared.ui.UiDeviceType;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm.MainBox.GroupBox.DenseRadioButtonGroup;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm.MainBox.GroupBox.DeviceTypeField;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm.MainBox.GroupBox.UiThemeField;
import org.eclipse.scout.widgets.shared.services.code.UiThemeCodeType;
import org.eclipse.scout.widgets.shared.services.code.UiThemeCodeType.DefaultCode;

@ClassId("ebb5f5d1-6db4-449b-9250-020a1c5e06f8")
public class OptionsForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Options");
  }

  @Override
  protected void execInitForm() {
    String theme = ObjectUtility.nvl(getDesktop().getTheme(), DefaultCode.ID);
    getUiThemeField().setValue(theme);
    getDenseRadioButtonGroup().setValue(getDesktop().isDense());
    getDeviceTypeField().setValue(ObjectUtility.nvl(getDesktop().getEnforcedDeviceType(), UiDeviceType.AUTOMATIC));
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public DenseRadioButtonGroup getDenseRadioButtonGroup() {
    return getFieldByClass(DenseRadioButtonGroup.class);
  }

  public DeviceTypeField getDeviceTypeField() {
    return getFieldByClass(DeviceTypeField.class);
  }

  public UiThemeField getUiThemeField() {
    return getFieldByClass(UiThemeField.class);
  }

  @Order(10)
  @ClassId("b0a96b00-9ffd-4fac-99b4-cfc18497c53e")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("5c2ac525-e648-4378-af82-27baf5bf63de")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("2db606d2-694f-4af3-82a8-d528a0b5e98b")
      public class UiThemeField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UiTheme");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return UiThemeCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20)
      @ClassId("93a81498-f590-4de1-97e8-9cdda8ae7e6d")
      public class DenseRadioButtonGroup extends AbstractRadioButtonGroup<Boolean> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Layout");
        }

        @Order(10)
        @ClassId("5ee2095c-7598-4054-885b-75a264dbcdab")
        public class DefaultButton extends AbstractRadioButton<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected Boolean getConfiguredRadioValue() {
            return Boolean.FALSE;
          }
        }

        @Order(20)
        @ClassId("37050591-229a-46f8-8a31-27c74c186633")
        public class DenseButton extends AbstractRadioButton<Boolean> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Dense");
          }

          @Override
          protected Boolean getConfiguredRadioValue() {
            return Boolean.TRUE;
          }
        }
      }

      @Order(30)
      @ClassId("a4afe8a7-6e78-4486-8ac7-f9933337341a")
      public class DeviceTypeField extends AbstractSmartField<IUiDeviceType> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DeviceType");
        }

        @Override
        protected Class<? extends ILookupCall<IUiDeviceType>> getConfiguredLookupCall() {
          return P_DeviceTypeLookupCall.class;
        }
      }
    }

    @Order(10)
    @ClassId("77366bd7-a38f-40d2-97a7-04565d2f463e")
    public class OkButton extends AbstractOkButton {
    }

    @Order(20)
    @ClassId("8d0858ff-7044-4433-8df2-3450c83c0357")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      if (getUiThemeField().isSaveNeeded()) {
        getDesktop().setTheme(getUiThemeField().getValue());
      }
      if (getDenseRadioButtonGroup().isSaveNeeded()) {
        getDesktop().setDense(getDenseRadioButtonGroup().getValue());
      }
      if (getDeviceTypeField().isSaveNeeded()) {
        getDesktop().setEnforcedDeviceType(getDeviceTypeField().getValue());
      }
    }
  }

  @ClassId("0ecb7123-7dcd-4e60-8e16-449451f99722")
  public static class P_DeviceTypeLookupCall extends LocalLookupCall<IUiDeviceType> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<IUiDeviceType>> execCreateLookupRows() {
      var rows = new ArrayList<ILookupRow<IUiDeviceType>>();
      rows.add(new LookupRow<>(UiDeviceType.AUTOMATIC, TEXTS.get("AutomaticRecognition")));
      rows.add(new LookupRow<>(UiDeviceType.DESKTOP, TEXTS.get("Desktop")));
      rows.add(new LookupRow<>(UiDeviceType.TABLET, TEXTS.get("Tablet")));
      rows.add(new LookupRow<>(UiDeviceType.MOBILE, TEXTS.get("Mobile")));
      return rows;
    }
  }
}
