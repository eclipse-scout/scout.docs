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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.basic.tree.AutoCheckStyle;
import org.eclipse.scout.rt.client.ui.basic.tree.CheckableStyle;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.AbstractTreeBox;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.ITreeBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.UserContentTreeLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.YearsMonthsLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.AutoCheckStyleField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.CheckUncheckBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.CheckUncheckBox.CheckAllButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.CheckUncheckBox.UncheckAllButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.FilterCheckedRowsValueField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.IsEnabledField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.TreeBoxField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.TreeEntriesField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ExamplesBox.ExampleCodeTypeBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ExamplesBox.ExampleCodeTypeBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB8000;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB8000.ICB8500;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537;

@Order(2000.0)
@ClassId("c867404b-93a0-4726-9ef2-e91b7693a840")
public class TreeBoxForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TreeBox");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public AutoCheckStyleField getAutoCheckStyleField() {
    return getFieldByClass(AutoCheckStyleField.class);
  }

  public IsEnabledField getIsEnabledField() {
    return getFieldByClass(IsEnabledField.class);
  }

  public CheckAllButton getCheckAllButton() {
    return getFieldByClass(CheckAllButton.class);
  }

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

  public TreeBoxField getTreeBoxField() {
    return getFieldByClass(TreeBoxField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public TreeEntriesField getTreeEntriesField() {
    return getFieldByClass(TreeEntriesField.class);
  }

  public UncheckAllButton getUncheckAllButton() {
    return getFieldByClass(UncheckAllButton.class);
  }

  @Order(10)
  @ClassId("fbec2cf8-5209-4ef9-8748-73d581a25c43")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("dae4919e-6521-4208-84b9-88606edaad04")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredBorderVisible() {
        return false;
      }

      @Order(10)
      @ClassId("6b984ad9-1146-4910-b717-bbf670ed019b")
      public class ExampleCodeTypeBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Example with CodeType";
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(20)
        @ClassId("5068db32-45cd-47d3-8d47-7d6e850e9920")
        public class DefaultField extends AbstractTreeBox<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return IndustryICBCodeType.class;
          }

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected boolean getConfiguredFilterActiveNodes() {
            return true;
          }
        }

        @Order(30)
        @ClassId("c02152dc-97b6-42cd-98bf-4dae281e3b1a")
        public class DisabledField extends AbstractTreeBox<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return IndustryICBCodeType.class;
          }

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
          protected void execInitField() {
            Set<Long> codes = new HashSet<>();
            codes.add(ICB8000.ID);
            codes.add(ICB8500.ID);
            codes.add(ICB9537.ID);
            setValue(codes);
            setFilterCheckedNodesValue(true);
          }
        }
      }

      @Order(20)
      @ClassId("cf66703f-5cd3-44e8-8c73-273e4b7ac94a")
      public class ExampleLookupCallBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Example with LookupCall";
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(50)
        @ClassId("8f6323ec-442c-4fce-b734-bafdbd8d6170")
        public class DefaultTreeBoxField extends AbstractTreeBox<String> {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return YearsMonthsLookupCall.class;
          }
        }

        @Order(60)
        @ClassId("d80268fa-6720-4838-92b5-26951aa950fe")
        public class DisabledTreeBoxField extends AbstractTreeBox<String> {

          @Override
          protected boolean getConfiguredAutoExpandAll() {
            return true;
          }

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
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return YearsMonthsLookupCall.class;
          }

          @Override
          protected void execInitField() {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int key = 100 * year + month;
            Set<String> times = new HashSet<>();
            times.add(String.valueOf(key));
            times.add(String.valueOf(key + 1));
            times.add(String.valueOf(key + 2));
            setValue(times);
            setFilterCheckedNodesValue(true);
          }
        }
      }
    }

    @Order(20)
    @ClassId("2a89a974-2fd4-4a28-894f-91e1ec8b3580")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("632016bd-77b7-4234-80dc-3ce0e17a736f")
      public class TreeBoxField extends AbstractTreeBox<String> {

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeBox");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return UserContentTreeLookupCall.class;
        }

        @ClassId("0ecdf7fc-1f4e-44af-a158-47222b9e1425")
        public class TreeBoxTree extends DefaultTreeBoxTree {

          @Order(10)
          @ClassId("d0670cfa-0104-4b67-9705-24540af0bb88")
          public class ToggleNodeEnabledMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ToggleNodeEnabledState");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() {
              getSelectedNode().setEnabled(!getSelectedNode().isEnabled());
            }
          }

          @Order(20)
          @ClassId("92a8e930-d43f-4ebb-b009-8ce9b6f1abd3")
          public class ToggleNodeCheckedMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ToggleNodeCheckedState");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() {
              getSelectedNode().setChecked(!getSelectedNode().isChecked());
            }
          }
        }
      }

      @Order(20)
      @ClassId("2ce52dd3-9245-4840-b074-bc73f5cf0f63")
      public class CheckUncheckBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("10b52e75-ee11-4d8a-8baa-229ee4b3675a")
        public class CheckAllButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CheckAll");
          }

          @Override
          protected void execClickAction() {
            getTreeBoxField().checkAllKeys();
          }
        }

        @Order(20)
        @ClassId("0cb20756-1276-4f3c-b372-1bccb69fa1c5")
        public class UncheckAllButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("UncheckAll");
          }

          @Override
          protected void execClickAction() {
            getTreeBoxField().uncheckAllKeys();
          }
        }
      }

      @Order(30)
      @ClassId("8a3bd3eb-7a61-4787-8a1c-08c1f43e3bf9")
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
          return TreeBoxField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          Set<String> keys = getTreeBoxField().getCheckedKeys();
          setValue(StringUtility.join(";", keys.toArray(new String[0])));
        }
      }

      @Order(35)
      @ClassId("aceedf95-6963-43ce-83f1-1f370bc26b70")
      public class SetCheckedKeysBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("e9c34eb9-72ef-4dab-b0c1-5f56cebd13d6")
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
        @ClassId("e6d06b7d-b29a-4798-b1bd-a08a8805e1fe")
        public class SetCheckedKeysButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "Set";
          }

          @Override
          protected void execClickAction() {
            String[] keys = StringUtility.split(getFieldByClass(SetCheckedKeysField.class).getValue(), ";");
            getTreeBoxField().setValue(CollectionUtility.hashSet(keys));
          }
        }
      }

      @Order(40)
      @ClassId("19bc4d56-8f13-4205-8b31-0b7f4e150249")
      public class TreeEntriesField extends AbstractUserTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeContent");
        }

        @Override
        protected void execChangedValue() {
          List<Node> nodes = parseFieldValue(true);
          List<LookupRow<String>> rows = new ArrayList<>();

          addNodesToLookupRows(nodes, rows);
          ((UserContentTreeLookupCall) getTreeBoxField().getLookupCall()).setLookupRows(rows);

          getTreeBoxField().reinit();
        }
      }

      @Order(50)
      @ClassId("9a468ede-0a82-49a9-bf55-4fe300cc4dc7")
      public class FilterCheckedRowsValueField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FilterCheckedRowsValue");
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().setFilterCheckedNodesValue(getValue());
        }
      }

      @Order(70)
      @ClassId("3cf5e0c0-0f74-4688-80cc-9834ad3608b2")
      public class IsEnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().setEnabled(getValue(), true, true);
        }

        @Override
        protected void execInitField() {
          setValue(getTreeBoxField().isEnabled());
        }
      }

      @Order(80)
      @ClassId("d323abd4-260d-46c6-8f27-697e0caeb40b")
      public class CheckableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Checkable";
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().getTree().setCheckable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeBoxField().getTree().isCheckable());
        }
      }

      @Order(85)
      @ClassId("a126dcfe-fd25-42d8-810b-796403c96696")
      public class MulticheckField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Multicheck";
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().getTree().setMultiCheck(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeBoxField().getTree().isMultiCheck());
        }
      }

      @Order(90)
      @ClassId("eb962203-089c-4ac6-9f06-8d076bdb2889")
      public class AutoCheckStyleField extends AbstractSmartField<AutoCheckStyle> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoCheckStyle");
        }

        @Override
        protected String getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DROPDOWN;
        }

        @Override
        protected Class<? extends ILookupCall<AutoCheckStyle>> getConfiguredLookupCall() {
          return TreeAutoCheckStyleLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().getTree().setAutoCheckStyle(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeBoxField().getTree().getAutoCheckStyle());
        }
      }

      @Order(100)
      @ClassId("e6323fcf-5455-4148-bb64-b600bf884d44")
      public class CheckableStyleField extends AbstractSmartField<CheckableStyle> {

        @Override
        protected String getConfiguredLabel() {
          return "Checkable Style";
        }

        @Override
        protected String getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DROPDOWN;
        }

        @Override
        protected Class<? extends ILookupCall<CheckableStyle>> getConfiguredLookupCall() {
          return TreeCheckableStyleLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().getTree().setCheckableStyle(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeBoxField().getTree().getCheckableStyle());
        }
      }
    }

    @Order(30)
    @ClassId("a7723da3-ba76-42ca-bb9d-b7b823683722")
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getTreeEntriesField().setValue(TEXTS.get("TreeUserContent"));
      }
    }

    @Order(40)
    @ClassId("ad7921d1-9394-4baa-97e8-72ac69551c48")
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
          if (field instanceof ITreeBox<?>) {
            ITreeBox<?> treeBox = (ITreeBox<?>) field;
            treeBox.setFilterActiveNodes(m_toggleState.isActive());
            treeBox.setFilterCheckedNodes(m_toggleState.isChecked());
          }
        }
      }
    }

    @Order(40)
    @ClassId("a028246b-3bc0-48a8-9552-78262bcc7345")
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
