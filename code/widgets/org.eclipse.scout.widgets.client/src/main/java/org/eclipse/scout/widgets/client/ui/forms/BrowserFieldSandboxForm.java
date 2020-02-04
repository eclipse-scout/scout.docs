package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.IBrowserField;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.IBrowserField.SandboxPermission;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("2254095e-5b33-46bc-97d7-b0d03142e7ea")
public class BrowserFieldSandboxForm extends AbstractForm {

  private final IBrowserField m_browserField;

  public BrowserFieldSandboxForm(IBrowserField browserField) {
    m_browserField = browserField;
    setHandler(new DefaultFormHandler());
  }

  @Override
  protected String getConfiguredTitle() {
    return "BrowserField - Sandbox Settings";
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredModalityHint() {
    return MODALITY_HINT_MODELESS;
  }

  @Override
  protected boolean getConfiguredCacheBounds() {
    return true;
  }

  public IBrowserField getBrowserField() {
    return m_browserField;
  }

  @Order(10)
  @ClassId("dd423b35-bc67-404f-b069-e807142f5b5f")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void execInitField() {
      setStatusVisible(false);
    }

    @Order(10)
    @ClassId("86c0cd60-9119-4056-80bc-548cd9756dc9")
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridW() {
        return 1;
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(40)
      @ClassId("12f240e6-c9df-472b-af18-a592494efeac")
      public class SandboxEnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Sandbox enabled";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getBrowserField().setSandboxEnabled(isChecked());
        }

        @Override
        protected void execInitField() {
          setChecked(getBrowserField().isSandboxEnabled());
        }
      }

      @Order(99)
      @ClassId("ea73e43d-b0a8-4dfe-b99d-f5459a127f30")
      public class SandboxPermissionsField extends AbstractListBox<SandboxPermission> {

        @Override
        protected String getConfiguredLabel() {
          return "Sandbox permissions";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return true;
        }

        @Override
        protected byte getConfiguredLabelPosition() {
          return LABEL_POSITION_TOP;
        }

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected Class<? extends ILookupCall<SandboxPermission>> getConfiguredLookupCall() {
          return SandboxPermissionsLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          Set<SandboxPermission> value = getValue();
          if (value == null || value.isEmpty()) {
            getBrowserField().setSandboxPermissions(SandboxPermission.none());
          }
          else {
            getBrowserField().setSandboxPermissions(EnumSet.copyOf(value));
          }
        }

        @Override
        protected void execInitField() {
          setValue(getBrowserField().getSandboxPermissions());
        }
      }
    }

    @Order(40)
    @ClassId("d49f88d7-2bdb-46e9-9f44-0e9e778cf683")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class DefaultFormHandler extends AbstractFormHandler {
  }

  @ClassId("2f8f115d-5d67-4cf8-aa47-3a852961c3d5")
  public static class SandboxPermissionsLookupCall extends LocalLookupCall<SandboxPermission> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<SandboxPermission>> execCreateLookupRows() {
      List<ILookupRow<SandboxPermission>> rows = new ArrayList<>();
      for (SandboxPermission permission : SandboxPermission.values()) {
        rows.add(new LookupRow<>(permission, permission.name()));
      }
      return rows;
    }
  }
}
