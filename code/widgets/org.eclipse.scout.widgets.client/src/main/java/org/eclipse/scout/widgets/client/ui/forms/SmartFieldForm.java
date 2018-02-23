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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.columns.ColumnDescriptor;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractStatusMenuMapping;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractProposalField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.ISmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.AbstractLocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.AbstractLocaleLookupCall.LocaleTableRowData;
import org.eclipse.scout.widgets.client.services.lookup.HierarchicalLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.RemoteLocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.UserContentListLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.UserContentTreeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.BrowseAutoExpandAllField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.BrowseHierarchyField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.BrowseMaxRowCountField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.GetValueField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.ListEntriesField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.ListSmartField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.TreeEntriesField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ConfigurationBox.TreeSmartField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.ProposalFieldWithListContentBox;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.ProposalFieldWithListContentBox.DefaultProposalField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.ProposalFieldWithListContentBox.MandatoryProposalField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithListGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithListGroupBox.DefaultSmartField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithListGroupBox.DisabledField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithListGroupBox.MandatoryField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithListGroupBox.SmartFieldWithCustomTableField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithTreeGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithTreeGroupBox.DefaultTreeSmartField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithTreeGroupBox.DisabledTreeSmartField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.ExamplesBox.SmartFieldWithTreeGroupBox.MandatoryTreeSmartField;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SampleContentButton;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleExceptionOnLookupMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleLookupCallMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleRequestInputMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleThrottleSmartFieldMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleValidateValueMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.TreeSmartFieldTestMenu.ToggleHierarchicalLookupMenu;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipse.scout.widgets.shared.services.code.ColorsCodeType;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537;

@Order(3000.0)
public class SmartFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final Locale ALBANIAN = new Locale("sq");

  private boolean m_requestInput = false;

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SmartField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public BrowseAutoExpandAllField getBrowseAutoExpandAllField() {
    return getFieldByClass(BrowseAutoExpandAllField.class);
  }

  public BrowseHierarchyField getBrowseHierarchyField() {
    return getFieldByClass(BrowseHierarchyField.class);
  }

  public BrowseMaxRowCountField getBrowseMaxRowCountField() {
    return getFieldByClass(BrowseMaxRowCountField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DisabledTreeSmartField getDisabledTreeSmartField() {
    return getFieldByClass(DisabledTreeSmartField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public DefaultSmartField getDefaultSmartField() {
    return getFieldByClass(DefaultSmartField.class);
  }

  public DisabledField getDisabledField() {
    return getFieldByClass(DisabledField.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public MandatoryTreeSmartField getMandatoryTreeSmartField() {
    return getFieldByClass(MandatoryTreeSmartField.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public DefaultTreeSmartField getDefaultTreeSmartField() {
    return getFieldByClass(DefaultTreeSmartField.class);
  }

  public DefaultProposalField getDefaultProposalField() {
    return getFieldByClass(DefaultProposalField.class);
  }

  public MandatoryProposalField getMandatoryProposalField() {
    return getFieldByClass(MandatoryProposalField.class);
  }

  public GetValueField getGetValueField() {
    return getFieldByClass(GetValueField.class);
  }

  public ListSmartField getListSmartField() {
    return getFieldByClass(ListSmartField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ListEntriesField getListEntriesField() {
    return getFieldByClass(ListEntriesField.class);
  }

  public TreeEntriesField getTreeEntriesField() {
    return getFieldByClass(TreeEntriesField.class);
  }

  public SmartFieldWithCustomTableField getSmartFieldWithCustomTableField() {
    return getFieldByClass(SmartFieldWithCustomTableField.class);
  }

  public SmartFieldWithListGroupBox getSmartFieldWithListGroupBox() {
    return getFieldByClass(SmartFieldWithListGroupBox.class);
  }

  public SmartFieldWithTreeGroupBox getSmartFieldWithTreeGroupBox() {
    return getFieldByClass(SmartFieldWithTreeGroupBox.class);
  }

  public ProposalFieldWithListContentBox getProposalFieldWithListContentBox() {
    return getFieldByClass(ProposalFieldWithListContentBox.class);
  }

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  public TreeSmartField getTreeSmartField() {
    return getFieldByClass(TreeSmartField.class);
  }

  protected void showValueOfDefaultSmartField() {
    MessageBoxes.createOk().withBody(getDefaultSmartField().getValue() + "").show();
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(1000)
      @ClassId("d7594941-dad5-4ce2-af9d-e5a9950d2303")
      public class SmartFieldWithListGroupBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SmartFieldWithListContent");
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Order(30)
        public class DefaultSmartField extends AbstractSmartField<Locale> {

          private boolean m_throwVetoException = false;
          private boolean m_validateValue = false;

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
            return (Class<? extends ILookupCall<Locale>>) /*Remote*/ LocaleLookupCall.class;
          }

          @Override
          protected void execPrepareLookup(ILookupCall<Locale> call) {
            if (call instanceof LocaleLookupCall) { // for some tests the lookup class is changed dynamically
              ((AbstractLocaleLookupCall) call).setThrowVetoException(m_throwVetoException);
            }
          }

          @Override
          protected Locale execValidateValue(Locale rawValue) {
            Locale validValue = rawValue;
            if (m_validateValue) {
              validValue = ALBANIAN;
            }
            return validValue;
          }

          public void setThrowVetoException(boolean throwVetoException) {
            m_throwVetoException = throwVetoException;
          }

          public void setValidateValue(boolean validateValue) {
            m_validateValue = validateValue;
          }

          @Order(1000)
          @ClassId("70082ca3-63d1-4f19-a9d3-c756907de0c0")
          public class NewMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Add new language";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.Null);
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk()
                  .withHeader(TEXTS.get("ThanksForClickingMe"))
                  .withBody(TEXTS.get("NewLanguageMessage"))
                  .show();
            }
          }

          public class NewMenuStatusMapping extends AbstractStatusMenuMapping {

            @Override
            protected Class<? extends IMenu> getConfiguredMenu() {
              return NewMenu.class;
            }

            @Override
            protected List<Integer> getConfiguredCodes() {
              return Arrays.asList(ISmartField.NO_RESULTS_ERROR_CODE);
            }

          }

        }

        @Order(40)
        public class MandatoryField extends AbstractSmartField<Color> {

          @Override
          protected Class<? extends ICodeType<?, Color>> getConfiguredCodeType() {
            return ColorsCodeType.class;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mandatory");
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            Color color = getValue();
            if (color == null) {
              setBackgroundColor(null);
            }
            else {
              String hex = Integer.toHexString(color.getRGB()).substring(2);
              setBackgroundColor(hex);
            }
          }
        }

        @Order(50)
        public class DisabledField extends AbstractSmartField<Color> {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
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
            setValue(new Color(255, 255, 255));
          }
        }

        @Order(55)
        @ClassId("75eed6c0-aee7-4632-80ec-e4768c9c4bdb")
        public class SmartFieldWithCustomTableField extends AbstractSmartField<Locale> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CustomTable");
          }

          @Override
          protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
            return (Class<? extends ILookupCall<Locale>>) /*Remote*/LocaleLookupCall.class;
          }

          @Override
          protected ColumnDescriptor[] getConfiguredColumnDescriptors() {
            return new ColumnDescriptor[]{
                new ColumnDescriptor(null, "Text", 200),
                new ColumnDescriptor(LocaleTableRowData.country, TEXTS.get("Country"), 90),
                new ColumnDescriptor(LocaleTableRowData.language, TEXTS.get("Language"), 90)
            };
          }
        }

        @Order(60)
        @ClassId("96674af1-a590-42e6-985c-6adb210e8504")
        public class SmartFieldDisplayStyleField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return "Dropdown";
          }

          protected boolean isDropdown() {
            return ISmartField.DISPLAY_STYLE_DROPDOWN.equals(getDefaultSmartField().getDisplayStyle());
          }

          @Override
          protected void execInitField() {
            setValue(isDropdown());
          }

          @Override
          protected void execChangedValue() {
            getDefaultSmartField().setDisplayStyle(getValue() ? ISmartField.DISPLAY_STYLE_DROPDOWN : ISmartField.DISPLAY_STYLE_DEFAULT);
          }
        }
      }

      @Order(2000)
      @ClassId("ce63629e-21ca-4d0c-aeeb-915aaf650636")
      public class SmartFieldWithTreeGroupBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SmartFieldWithTreeContent");
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Order(70)
        public class DefaultTreeSmartField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return HierarchicalLookupCall.class;
          }

          @Override
          protected boolean getConfiguredBrowseHierarchy() {
            return true;
          }

          @Override
          protected boolean getConfiguredBrowseLoadIncremental() {
            return true;
          }

          @Override
          protected void execPrepareBrowseLookup(ILookupCall<Long> call) {
            // instanceof is required because in this form the lookup can be switched at runtime
            if (call instanceof HierarchicalLookupCall) {
              ((HierarchicalLookupCall) call).setLoadIncremental(isBrowseLoadIncremental());
            }
          }
        }

        @Order(80)
        public class MandatoryTreeSmartField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mandatory");
          }

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return IndustryICBCodeType.class;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(90)
        public class DisabledTreeSmartField extends AbstractSmartField<Long> {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Disabled");
          }

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return IndustryICBCodeType.class;
          }

          @Override
          protected void execInitField() {
            setValue(ICB9537.ID);
          }
        }

        @Order(100)
        public class LoadIncrementalField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Load incremental";
          }

          @Override
          protected void execInitField() {
            setValue(getDefaultTreeSmartField().isBrowseLoadIncremental());
          }

          @Override
          protected void execChangedValue() {
            getDefaultTreeSmartField().setBrowseLoadIncremental(getValue());
          }
        }
      }

      @Order(3000)
      @ClassId("2a8481a4-6fcc-446d-8d10-384ac37cb739")
      public class ProposalFieldWithListContentBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ProposalFieldWithListContent");
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Order(110)
        public class DefaultProposalField extends AbstractProposalField<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return EventTypeCodeType.class;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }
        }

        @Order(120)
        public class MandatoryProposalField extends AbstractProposalField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mandatory");
          }

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return EventTypeCodeType.class;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(130)
        public class DisabledProposalField extends AbstractProposalField<Long> {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Disabled");
          }

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return EventTypeCodeType.class;
          }

          @Override
          protected void execInitField() {
            setValueAsString(TEXTS.get("Public"));
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
        return 3;
      }

      @Order(10)
      public class ListSmartField extends AbstractSmartField<String> {

        private boolean m_throttleActive = false;

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ListSmartField");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) UserContentListLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<String> call) {
          if (m_throttleActive) {
            SleepUtil.sleepSafe(1, TimeUnit.SECONDS);
          }
          super.execPrepareLookup(call);
        }

        @Override
        protected void execChangedValue() {
          if (m_throttleActive) {
            SleepUtil.sleepSafe(1, TimeUnit.SECONDS);
          }
          super.execChangedValue();
        }

        public void setThrottleActive(boolean throttleActive) {
          m_throttleActive = throttleActive;
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
          return ListSmartField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          if (newMasterValue != null) {
            setValue(newMasterValue.toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(30)
      public class ListEntriesField extends AbstractUserTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ListContent");
        }

        @Override
        protected void execChangedValue() {
          List<Node> nodes = parseFieldValue(false);
          ArrayList<LookupRow<String>> rows = new ArrayList<>();
          addNodesToLookupRows(nodes, rows);

          ((UserContentListLookupCall) getListSmartField().getLookupCall()).setLookupRows(rows);
          getListSmartField().reinit();
        }
      }

      @Order(40)
      public class BrowseMaxRowCountField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BrowseMaxRowCount");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Integer getConfiguredMinValue() {
          return 0;
        }

        @Override
        protected void execChangedValue() {
          getListSmartField().setBrowseMaxRowCount(NumberUtility.nvl(getValue(), 100));
          getTreeSmartField().setBrowseMaxRowCount(NumberUtility.nvl(getValue(), 100));
        }
      }

      @Order(50)
      public class EnableActiveFilterField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EnableActiveFilter");
        }

        @Override
        protected void execChangedValue() {
          getListSmartField().setActiveFilterEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getListSmartField().isActiveFilterEnabled());
        }
      }

      @Order(70)
      public class TreeSmartField extends AbstractSmartField<String> {

        @Override
        protected boolean getConfiguredBrowseAutoExpandAll() {
          return false;
        }

        @Override
        protected boolean getConfiguredBrowseHierarchy() {
          return true;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeSmartField");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) UserContentTreeLookupCall.class;
        }
      }

      @Order(80)
      public class GetValueTreeSmartFieldField extends AbstractStringField {

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
          return TreeSmartField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          if (newMasterValue != null) {
            setValue(newMasterValue.toString());
          }
          else {
            setValue(null);
          }
        }
      }

      @Order(90)
      public class TreeEntriesField extends AbstractUserTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 3;
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
          ((UserContentTreeLookupCall) getTreeSmartField().getLookupCall()).setLookupRows(rows);
        }
      }

      @Order(100)
      public class BrowseHierarchyField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BrowseHierarchy");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getTreeSmartField().setBrowseHierarchy(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeSmartField().isBrowseHierarchy());
        }
      }

      @Order(110)
      public class BrowseAutoExpandAllField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BrowseAutoExpandAll");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getTreeSmartField().setBrowseAutoExpandAll(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeSmartField().isBrowseAutoExpandAll());
        }
      }

      @Order(120)
      public class PlaceholderField extends AbstractPlaceholderField {

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }
      }
    }

    @Order(30)
    public class SampleContentButton extends AbstractButton {

      private boolean m_showValue;

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        if (m_showValue) {
          showValueOfDefaultSmartField();
          return;
        }

        ListEntriesField listEntries = getListEntriesField();
        listEntries.setValue(TEXTS.get("ListBoxUserContent"));

        TreeEntriesField treeEntries = getTreeEntriesField();
        treeEntries.setValue(TEXTS.get("TreeUserContent"));
      }

      protected void setShowValue(boolean showValue) {
        m_showValue = showValue;
        setLabel(showValue ? "Show value" : TEXTS.get("SampleContent"));
      }
    }

    @Order(35)
    public class SeleniumTestMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Selenium");
      }

      @Order(100)
      @ClassId("5143086a-d88d-419a-8df6-b6b91d56eb38")
      public class SmartFieldTestMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "SmartField";
        }

        @Order(10)
        public class WildcardMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("Wildcard");
          }

          @Order(10)
          public class AutoPrefixWildcardMenu extends AbstractMenu {

            private boolean m_active;

            @Override
            protected void execInitAction() {
              m_active = ClientSessionProvider.currentSession().getDesktop().isAutoPrefixWildcardForTextSearch();
              updateText();
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("EnableAutoPrefixWildcard");
            }

            @Override
            protected void execAction() {
              m_active = !m_active;
              ClientSessionProvider.currentSession().getDesktop().setAutoPrefixWildcardForTextSearch(m_active);
              updateText();
            }

            protected void updateText() {
              setText(TEXTS.get(m_active ? "DisableAutoPrefixWildcard" : "EnableAutoPrefixWildcard"));
            }
          }

          @Order(20)
          public class ChangeWildcard1Menu extends AbstractMenu {
            @Override
            protected void execAction() {
              changeWildcard("*");
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ChangeWildcard", "*");
            }
          }

          @Order(30)
          public class ChangeWildcard2Menu extends AbstractMenu {
            @Override
            protected void execAction() {
              changeWildcard(".*");
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ChangeWildcard", ".*");
            }
          }

          @Order(40)
          public class ChangeWildcard3Menu extends AbstractMenu {
            @Override
            protected void execAction() {
              changeWildcard("\\");
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ChangeWildcard", "\\");
            }
          }

          @Order(50)
          public class ChangeWildcard4Menu extends AbstractMenu {
            @Override
            protected void execAction() {
              changeWildcard("°");
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ChangeWildcard", "°");
            }
          }
        }

        @Order(20)
        public class ShowDefaultSmartFieldValueMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Show value of DefaultSmartField";
          }

          @Override
          protected void execAction() {
            showValueOfDefaultSmartField();
          }

          @Override
          protected String getConfiguredKeyStroke() {
            return "Ctrl-Alt-P";
          }
        }

        @Order(30)
        @ClassId("5f21c48b-433a-4743-b671-76982d01e3ef")
        public class RequestInputMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Request input in 2 secs.";
          }

          @Override
          protected void execAction() {
            Jobs.schedule(() -> {
              SleepUtil.sleepSafe(2, TimeUnit.SECONDS);
              ModelJobs.schedule(() -> {
                getDefaultSmartField().requestInput();
              }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
            }, Jobs.newInput().withRunContext(RunContext.CURRENT.get().copy()));
          }
        }

        @Order(40)
        public class ToggleLookupCallMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Use remote lookup call";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "When active DefaultSmartField uses a remote lookup-call instead of a local lookup-call";
          }

          @Override
          protected void execToggleActive() {
            getDefaultSmartField().setLookupCall(new RemoteLocaleLookupCall());
          }

          @Override
          protected void execToggleInactive() {
            getDefaultSmartField().setLookupCall(new LocaleLookupCall());
          }
        }

        @Order(50)
        public class ToggleThrottleSmartFieldMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Simulate slow connection";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Lookups on the ListSmartField are delayed by 1 second";
          }

          @Override
          protected void execAlways() {
            getListSmartField().setThrottleActive(isActive());
          }
        }

        @Order(60)
        public class ToggleExceptionOnLookupMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Throw VetoException on lookup";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "DefaultSmartField throws a VetoException on every lookup";
          }

          @Override
          protected void execAlways() {
            getDefaultSmartField().setThrowVetoException(isActive());
          }
        }

        @Order(90)
        public class ToggleValidateValueMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Change Locale in validateValue";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "DefaultSmartField sets the value to Locale 'ar' in validateValue";
          }

          @Override
          protected void execAlways() {
            getDefaultSmartField().setValidateValue(isActive());
          }
        }

        @Order(110)
        @ClassId("1aac2053-a0c1-4cc8-b5fb-5516ffcdb7b6")
        public class ToggleRequestInputMenu extends AbstractToggleMenu {
          @Override
          protected String getConfiguredText() {
            return "RequestInput on formActivated";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Form calls requestInput on formActivated";
          }

          @Override
          protected void execAlways() {
            m_requestInput = isActive();
          }
        }

        @Order(120)
        @ClassId("aab18e2f-f9aa-4f1d-93b3-3f3d450ef775")
        public class ToggleSampleContentButtonMenu extends AbstractToggleMenu {
          @Override
          protected String getConfiguredText() {
            return "Sample button shows value";
          }

          @Override
          protected void execAlways() {
            getSampleContentButton().setShowValue(isActive());
          }
        }
      }

      @Order(200)
      @ClassId("7b3bc826-de45-4076-925e-4523ff89fca2")
      public class ProposalFieldTestMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "ProposalField";
        }

        @Order(30)
        public class ShowDefaultProposalFieldValueMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Show value of DefaultProposalField";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withBody(getDefaultProposalField().getValue() + "").show();
          }

          @Override
          protected String getConfiguredKeyStroke() {
            return "Ctrl-Alt-Q";
          }
        }
      }

      @Order(300)
      @ClassId("9d4c6d2f-425c-4610-abdd-f3935549a360")
      public class TreeSmartFieldTestMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "TreeSmartField";
        }

        @Order(70)
        public class ToggleHierarchicalLookupMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Set hierarchical lookup-call";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "When active DefaultSmartField uses HierarchicalLookupCall instead of code-type IndustryICBCodeType";
          }

          @Override
          protected void execAlways() {
            getDefaultTreeSmartField().setValue(null);
          }

          @Override
          protected void execToggleActive() {
            getDefaultTreeSmartField().setCodeTypeClass(IndustryICBCodeType.class);
          }

          @Override
          protected void execToggleInactive() {
            getDefaultTreeSmartField().setLookupCall(BEANS.get(HierarchicalLookupCall.class));
          }
        }
      }

      @Order(400)
      public class SeleniumResetMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Reset");
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void execAction() {
          Class<?>[] menuClasses = {
              ToggleExceptionOnLookupMenu.class,
              ToggleHierarchicalLookupMenu.class,
              ToggleLookupCallMenu.class,
              ToggleRequestInputMenu.class,
              ToggleThrottleSmartFieldMenu.class,
              ToggleValidateValueMenu.class
          };
          for (Class<?> menuClass : menuClasses) {
            getMenuByClass((Class<AbstractToggleMenu>) menuClass).reset();
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

  protected void changeWildcard(String wildcard) {
    getDefaultSmartField().setWildcard(wildcard);
    getMandatoryField().setWildcard(wildcard);
    getDefaultTreeSmartField().setWildcard(wildcard);
    getMandatoryTreeSmartField().setWildcard(wildcard);
    getDefaultProposalField().setWildcard(wildcard);
    getMandatoryProposalField().setWildcard(wildcard);
    getListSmartField().setWildcard(wildcard);
    getTreeSmartField().setWildcard(wildcard);
  }

  @Override
  protected void execFormActivated() {
    if (m_requestInput) {
      getDefaultSmartField().requestInput();
    }
  }
}
