/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableEvent;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.IListBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.FontStyleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.UserContentListLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.CheckUncheckBox;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.CheckUncheckBox.CheckAllButton;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.CheckUncheckBox.UncheckAllButton;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.FilterCheckedRowsValueField;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.IsEnabledField;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.ListBoxField;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ConfigurationBox.ListEntriesField;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.ListBoxForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipse.scout.widgets.shared.services.code.ColorsCodeType;
import org.eclipse.scout.widgets.shared.services.code.ColorsCodeType.BlueCode;
import org.eclipse.scout.widgets.shared.services.code.ColorsCodeType.GreenCode;

@Order(1000.0)
@ClassId("29d2feb8-8278-4d2b-8d4b-caf6e589d5e3")
public class ListBoxForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ListBox");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the CheckAllButton
   */
  public CheckAllButton getCheckAllButton() {
    return getFieldByClass(CheckAllButton.class);
  }

  /**
   * @return the CheckUncheckBox
   */
  public CheckUncheckBox getCheckUncheckBox() {
    return getFieldByClass(CheckUncheckBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public FilterCheckedRowsValueField getFilterCheckedRowsValueField() {
    return getFieldByClass(FilterCheckedRowsValueField.class);
  }

  public IsEnabledField getIsEnabledField() {
    return getFieldByClass(IsEnabledField.class);
  }

  public ListBoxField getListBoxField() {
    return getFieldByClass(ListBoxField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ListEntriesField getListEntriesField() {
    return getFieldByClass(ListEntriesField.class);
  }

  public UncheckAllButton getUncheckAllButton() {
    return getFieldByClass(UncheckAllButton.class);
  }

  @Order(10)
  @ClassId("c279ec81-ad6c-4e12-85db-e16a8f8b6be1")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("dc8845c4-6997-4205-b563-f1b86d5cb4ac")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("7ea859e8-eac9-4fce-9658-dfc6cc5a1cad")
      public class ListBoxWithCodeTypeContentField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected void execInitField() {
          setValue(TEXTS.get("ListBoxWithCodeTypeContent"));
        }
      }

      @Order(20)
      @ClassId("2bf72124-1793-4603-8a1c-c5ec0819fdd9")
      public class DefaultField extends AbstractListBox<Color> {

        @Override
        protected void execInitField() {
          getTable().addTableListener(evt -> {
            ITableRow checkedRow = evt.getFirstRow();
            if (!checkedRow.isChecked()) {
              return;
            }
            getTable().getRows().stream()
                .filter(row -> row != checkedRow)
                .forEach(row -> getTable().uncheckRow(row));
          }, TableEvent.TYPE_ROWS_CHECKED);
        }

        @Override
        protected Class<? extends ICodeType<?, Color>> getConfiguredCodeType() {
          return ColorsCodeType.class;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(30)
      @ClassId("e3f266b5-91c2-4fe4-b3d5-88ced81f1e3f")
      public class DisabledField extends AbstractListBox<Color> {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected Class<? extends ICodeType<?, Color>> getConfiguredCodeType() {
          return ColorsCodeType.class;
        }

        @Override
        protected void execInitField() {
          Set<Color> colors = new HashSet<>();

          colors.add(GreenCode.ID);
          colors.add(BlueCode.ID);
          // get a dynamically added code
          ICode<Color> red = BEANS.get(ColorsCodeType.class).getCode(Color.RED);
          colors.add(red.getId());

          setValue(colors);
          setFilterCheckedRowsValue(true);
        }
      }

      @Order(40)
      @ClassId("4fb7beb2-e810-497a-befd-56344adb8cee")
      public class ListBoxWithLookupCallContentField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EmptyString");
        }

        @Override
        protected String getConfiguredFont() {
          return "BOLD";
        }

        @Override
        protected void execInitField() {
          setValue(TEXTS.get("ListBoxWithLookupCallContent"));
        }
      }

      @Order(50)
      @ClassId("3e03a729-6d95-4a5c-a637-430c708c3be7")
      public class DefaultListBox extends AbstractListBox<Integer> {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return FontStyleLookupCall.class;
        }
      }

      @Order(60)
      @ClassId("28575db9-0811-4e78-a954-bf19bf73661f")
      public class DisabledListBox extends AbstractListBox<Integer> {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return FontStyleLookupCall.class;
        }

        @Override
        protected void execInitField() {
          Set<Integer> set = new HashSet<>();
          set.add(2);
          set.add(3);
          setValue(set);
          setFilterCheckedRowsValue(true);
        }
      }
    }

    @Order(20)
    @ClassId("7ae3d97a-d72f-4a7b-8592-c89398796490")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("05c60820-dde2-4c96-b18c-397095e9c56e")
      public class ListBoxField extends AbstractListBox<String> {

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ListBox");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return UserContentListLookupCall.class;
        }
      }

      @Order(20)
      @ClassId("73d14aef-8a4d-44e0-b50e-ed11b98d5ab4")
      public class CheckUncheckBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("b82207e6-0df7-42ea-8080-f3583351b39e")
        public class CheckAllButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CheckAll");
          }

          @Override
          protected void execClickAction() {
            getListBoxField().checkAllKeys();
          }
        }

        @Order(20)
        @ClassId("81714b3a-76b9-460c-a740-052edea90d35")
        public class UncheckAllButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("UncheckAll");
          }

          @Override
          protected void execClickAction() {
            getListBoxField().uncheckAllKeys();
          }
        }
      }

      @Order(30)
      @ClassId("6c49fe5a-e3ac-4ac0-a4c1-f1975c96d46c")
      public class GetCheckedKeysField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("getCheckedKeys");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ListBoxField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          Set<String> keys = getListBoxField().getCheckedKeys();
          setValue(StringUtility.join(";", keys.toArray(new String[0])));
        }
      }

      @Order(33)
      @ClassId("314c94ae-f74a-4549-b8cd-98f3e33da537")
      public class SetCheckedKeysBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("9f450c92-41c6-4069-b1bc-ac87980e0866")
        public class SetCheckedKeysField extends AbstractStringField {

          @Override
          protected boolean getConfiguredEnabled() {
            return true;
          }

          @Override
          protected String getConfiguredLabel() {
            return "setCheckedKeys()";
          }
        }

        @Order(20)
        @ClassId("d189c205-aed1-4ea9-98a2-cdc919e60e50")
        public class SetCheckedKeysButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Set";
          }

          @Override
          protected void execClickAction() {
            String[] keys = StringUtility.split(getFieldByClass(SetCheckedKeysField.class).getValue(), ";");
            getListBoxField().setValue(CollectionUtility.hashSet(keys));
          }
        }
      }

      @Order(40)
      @ClassId("c0f3f46e-3bbd-4266-aad1-ca4721d1b133")
      public class ListEntriesField extends AbstractUserTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 7;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ListContent");
        }

        @Override
        protected void execChangedValue() {
          List<Node> nodes = parseFieldValue(false);
          List<LookupRow<String>> rows = new ArrayList<>();

          addNodesToLookupRows(nodes, rows);
          ((UserContentListLookupCall) getListBoxField().getLookupCall()).setLookupRows(rows);

          getListBoxField().reinit();
        }
      }

      @Order(50)
      @ClassId("ed41b61c-140f-4226-9f56-b7e0f3c44a51")
      public class FilterCheckedRowsValueField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FilterCheckedRowsValue");
        }

        @Override
        protected void execChangedValue() {
          getListBoxField().setFilterCheckedRowsValue(getValue());
        }
      }

      @Order(60)
      @ClassId("6622e494-93e3-4eac-8b8c-cd2caff5762c")
      public class IsEnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IsEnabled");
        }

        @Override
        protected void execChangedValue() {
          getListBoxField().setEnabled(getValue(), true, true);
        }

        @Override
        protected void execInitField() {
          setValue(getListBoxField().isEnabled());
        }
      }
    }

    @Order(30)
    @ClassId("87c415b2-fc87-428f-9127-f241998d3594")
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        ListEntriesField listEntries = getListEntriesField();
        listEntries.setValue(TEXTS.get("ListBoxUserContent"));
      }
    }

    @Order(40)
    @ClassId("89205c52-eb1d-4faf-b947-98d209881b3c")
    public class ToggleFilterBoxesButton extends AbstractButton {

      private ToggleState m_toggleState = ToggleState.NONE;

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ToggleFilterBoxes");
      }

      @Override
      protected void execClickAction() {
        m_toggleState = m_toggleState.next();
        for (IFormField field : getAllFields()) {
          if (field instanceof IListBox<?>) {
            IListBox<?> listBox = (IListBox<?>) field;
            listBox.setFilterActiveRows(m_toggleState.isActive());
            listBox.setFilterCheckedRows(m_toggleState.isChecked());
          }
        }
      }
    }

    @Order(40)
    @ClassId("215035a0-b249-4233-913d-c427cf425cf9")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  private enum ToggleState {
    NONE(false, false),
    ACTIVE(true, false),
    CHECKED(false, true),
    BOTH(true, true);

    private final boolean m_active;
    private final boolean m_checked;

    ToggleState(boolean active, boolean checked) {
      m_active = active;
      m_checked = checked;
    }

    public ToggleState next() {
      switch (this) {
        case NONE:
          return ACTIVE;
        case ACTIVE:
          return CHECKED;
        case CHECKED:
          return BOTH;
        default:
          return NONE;
      }
    }

    public boolean isActive() {
      return m_active;
    }

    public boolean isChecked() {
      return m_checked;
    }
  }
}
