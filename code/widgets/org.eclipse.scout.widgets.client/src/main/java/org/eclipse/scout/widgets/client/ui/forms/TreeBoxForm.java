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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
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
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.AbstractTreeBox;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.ITreeBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.UserContentTreeLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.YearsMonthsLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.AutoCheckChildNodesField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.CheckUncheckBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.CheckUncheckBox.CheckAllButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.CheckUncheckBox.UncheckAllButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.FilterCheckedRowsValueField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.IsEnabledField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.TreeBoxField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ConfigurationBox.TreeEntriesField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.forms.TreeBoxForm.MainBox.ExamplesBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;

@Order(2000.0)
public class TreeBoxForm extends AbstractForm implements IAdvancedExampleForm {

  public TreeBoxForm() {
    super();
  }

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

  /**
   * @return the AutoCheckChildNodesField
   */
  public AutoCheckChildNodesField getAutoCheckChildNodesField() {
    return getFieldByClass(AutoCheckChildNodesField.class);
  }

  /**
   * @return the IsEnabledField
   */
  public IsEnabledField getIsEnabledField() {
    return getFieldByClass(IsEnabledField.class);
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

  /**
   * @return the FilterCheckedRowsValueField
   */
  public FilterCheckedRowsValueField getFilterCheckedRowsValueField() {
    return getFieldByClass(FilterCheckedRowsValueField.class);
  }

  /**
   * @return the TreeField
   */
  public TreeBoxField getTreeBoxField() {
    return getFieldByClass(TreeBoxField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the TreeEntriesField
   */
  public TreeEntriesField getTreeEntriesField() {
    return getFieldByClass(TreeEntriesField.class);
  }

  /**
   * @return the UncheckAllButton
   */
  public UncheckAllButton getUncheckAllButton() {
    return getFieldByClass(UncheckAllButton.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class TreeBoxWithCodeTypeContentField extends AbstractLabelField {

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
          setValue(TEXTS.get("TreeBoxWithCodeTypeContent"));
        }
      }

      @Order(20)
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
          Set<Long> codes = new HashSet<Long>();
          codes.add(IndustryICBCodeType.ICB8000.ID);
          codes.add(IndustryICBCodeType.ICB8000.ICB8500.ID);
          codes.add(IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537.ID);
          setValue(codes);
          setFilterCheckedNodesValue(true);
        }
      }

      @Order(40)
      public class TreeBoxWithLookupCallContentField extends AbstractLabelField {

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
          setValue(TEXTS.get("TreeBoxWithLookupCallContent"));
        }
      }

      @Order(50)
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
          Set<String> times = new HashSet<String>();
          times.add(String.valueOf(key));
          times.add(String.valueOf(key + 1));
          times.add(String.valueOf(key + 2));
          setValue(times);
          setFilterCheckedNodesValue(true);
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
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

        public class TreeBoxTree extends DefaultTreeBoxTree {

          @Order(10)
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
      public class CheckUncheckBox extends AbstractSequenceBox {

        @Order(10)
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
          return TreeBoxForm.MainBox.ConfigurationBox.TreeBoxField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          Set<String> keys = getTreeBoxField().getCheckedKeys();
          setValue(StringUtility.join(";", keys.toArray(new String[0])));
        }
      }

      @Order(35)
      public class SetCheckedKeysBox extends AbstractSequenceBox {

        @Order(10)
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
          List<LookupRow<String>> rows = new ArrayList<LookupRow<String>>();

          addNodesToLookupRows(nodes, rows);
          ((UserContentTreeLookupCall) getTreeBoxField().getLookupCall()).setLookupRows(rows);

          getTreeBoxField().initField();
        }
      }

      @Order(50)
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
          getTreeBoxField().setFilterCheckedNodesValue(getValue());
        }
      }

      @Order(60)
      public class AutoCheckChildNodesField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoCheckChildNodes");
        }

        @Override
        protected void execChangedValue() {
          getTreeBoxField().setAutoCheckChildNodes(getValue());
        }
      }

      @Order(70)
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
          getTreeBoxField().setEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeBoxField().isEnabled());
        }
      }

      @Order(80)
      public class CheckableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

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
    }

    @Order(30)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        TreeEntriesField treeEntries = getTreeEntriesField();
        treeEntries.setValue(TEXTS.get("TreeUserContent"));
      }
    }

    @Order(40)
    public class ToggleFilterBoxesButton extends AbstractButton {

      private ToggleState m_toggleState = ToggleState.None;

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
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  private enum ToggleState {
    None(false, false),
    Active(true, false),
    Checked(false, true),
    Both(true, true);

    private final boolean m_active;
    private final boolean m_checked;

    private ToggleState(boolean active, boolean checked) {
      m_active = active;
      m_checked = checked;
    }

    public ToggleState next() {
      switch (this) {
        case None:
          return Active;
        case Active:
          return Checked;
        case Checked:
          return Both;
        default:
          return None;
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
