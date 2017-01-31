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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
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

@Order(1000.0)
public class ListBoxForm extends AbstractForm implements IAdvancedExampleForm {

  public ListBoxForm() {
    super();
  }

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

  /**
   * @return the FilterCheckedRowsValueField
   */
  public FilterCheckedRowsValueField getFilterCheckedRowsValueField() {
    return getFieldByClass(FilterCheckedRowsValueField.class);
  }

  /**
   * @return the IsEnabledField
   */
  public IsEnabledField getIsEnabledField() {
    return getFieldByClass(IsEnabledField.class);
  }

  /**
   * @return the TreeField
   */
  public ListBoxField getListBoxField() {
    return getFieldByClass(ListBoxField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the TreeEntriesField
   */
  public ListEntriesField getListEntriesField() {
    return getFieldByClass(ListEntriesField.class);
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
      public class DefaultField extends AbstractListBox<Color> {

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
          Set<Color> colors = new HashSet<Color>();

          colors.add(ColorsCodeType.GreenCode.ID);
          colors.add(ColorsCodeType.BlueCode.ID);
          // get a dynamically added code
          ICode<Color> red = BEANS.get(ColorsCodeType.class).getCode(Color.RED);
          colors.add(red.getId());

          setValue(colors);
          setFilterCheckedRowsValue(true);
        }
      }

      @Order(40)
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
          return (Class<? extends ILookupCall<Integer>>) FontStyleLookupCall.class;
        }
      }

      @Order(60)
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
          return (Class<? extends ILookupCall<Integer>>) FontStyleLookupCall.class;
        }

        @Override
        protected void execInitField() {
          Set<Integer> set = new HashSet<Integer>();
          set.add(2);
          set.add(3);
          setValue(set);
          setFilterCheckedRowsValue(true);
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
          return (Class<? extends ILookupCall<String>>) UserContentListLookupCall.class;
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
            getListBoxField().checkAllKeys();
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
            getListBoxField().uncheckAllKeys();
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
          return ListBoxForm.MainBox.ConfigurationBox.ListBoxField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          Set<String> keys = getListBoxField().getCheckedKeys();
          setValue(StringUtility.join(";", keys.toArray(new String[0])));
        }
      }

      @Order(33)
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
            getListBoxField().setValue(CollectionUtility.hashSet(keys));
          }
        }
      }

      @Order(40)
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
          List<LookupRow<String>> rows = new ArrayList<LookupRow<String>>();

          addNodesToLookupRows(nodes, rows);
          ((UserContentListLookupCall) getListBoxField().getLookupCall()).setLookupRows(rows);

          getListBoxField().initField();
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
          getListBoxField().setFilterCheckedRowsValue(getValue());
        }
      }

      @Order(60)
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
          getListBoxField().setEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getListBoxField().isEnabled());
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
        ListEntriesField listEntries = getListEntriesField();
        listEntries.setValue(TEXTS.get("ListBoxUserContent"));
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
          if (field instanceof IListBox<?>) {
            IListBox<?> listBox = (IListBox<?>) field;
            listBox.setFilterActiveRows(m_toggleState.isActive());
            listBox.setFilterCheckedRows(m_toggleState.isChecked());
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
