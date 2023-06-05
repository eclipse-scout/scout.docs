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

import java.awt.*;
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
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.status.IMultiStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.AbstractLocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.AbstractLocaleLookupCall.LocaleTableRowData;
import org.eclipse.scout.widgets.client.services.lookup.HierarchicalLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.RemoteLocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.UserContentListLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.UserContentTreeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.CustomLocaleLookupCall.CustomLocaleTableRowData;
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
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleExceptionOnValidateMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleLookupCallMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleRequestInputMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleThrottleSmartFieldMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.SmartFieldTestMenu.ToggleValidateValueMenu;
import org.eclipse.scout.widgets.client.ui.forms.SmartFieldForm.MainBox.SeleniumTestMenu.TreeSmartFieldTestMenu.ToggleHierarchicalLookupMenu;
import org.eclipse.scout.widgets.client.ui.forms.menu.AbstractSeleniumTestMenu;
import org.eclipse.scout.widgets.client.ui.forms.menu.AbstractToggleMenu;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.ColorsCodeType;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537;

@Order(3000.0)
@ClassId("a9a153bf-8000-4977-bdb5-84c4673fda73")
public class SmartFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final Locale ALBANIAN = new Locale("sq");

  @ClassId("06e88da7-1810-4c58-862e-ddcf6e7a1877")
  public static class CustomLocaleLookupCall extends LocaleLookupCall {
    private static final long serialVersionUID = -4113831958135288489L;

    @Override
    protected LocaleTableRowData createTableRowData(Locale locale) {
      return new CustomLocaleTableRowData();
    }

    protected static class CustomLocaleTableRowData extends LocaleTableRowData {
      private static final long serialVersionUID = -3041359870377383920L;
      private String iconId;
      private boolean hasIso3Country;

      public String getIconId() {
        return iconId;
      }

      public CustomLocaleTableRowData withIconId(String iconId) {
        this.iconId = iconId;
        return this;
      }

      public boolean getHasIso3Country() {
        return hasIso3Country;
      }

      public CustomLocaleTableRowData withHasIso3Country(boolean hasIso3Country) {
        this.hasIso3Country = hasIso3Country;
        return this;
      }
    }
  }

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

  protected void showStateOfDefaultSmartField() {
    ISmartField<?> field = getDefaultSmartField();
    IMultiStatus errorStatus = field.getErrorStatus();
    StringBuilder text = new StringBuilder();
    text.append(field.getValue());
    if (errorStatus != null && !errorStatus.isOK()) {
      text.append("\n");
      text.append("[errorStatus.ok=false]");
    }
    MessageBoxes.createOk().withBody(text.toString()).show();
  }

  @Order(10)
  @ClassId("92cf7490-e423-417e-ac9c-38ffabbc16a2")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("49db90c6-22b7-4627-8d89-1ac8ab757c2c")
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
        @ClassId("fe6f12a6-e87c-4905-98a3-d37162f4d6eb")
        public class DefaultSmartField extends AbstractSmartField<Locale> {

          private boolean m_throwOnLookup = false;
          private boolean m_throwOnValidate = false;
          private boolean m_validateValue = false;
          private boolean m_formatValue = false;

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
            /*Remote*/
            return LocaleLookupCall.class;
          }

          @Override
          protected void execPrepareLookup(ILookupCall<Locale> call) {
            if (call instanceof LocaleLookupCall) { // for some tests the lookup class is changed dynamically
              ((AbstractLocaleLookupCall) call).setThrowVetoException(m_throwOnLookup);
            }
          }

          @Override
          protected Locale execValidateValue(Locale rawValue) {
            Locale validValue = rawValue;
            if (m_validateValue) {
              validValue = ALBANIAN;
            }
            else if (m_throwOnValidate && rawValue != null) {
              throw new VetoException("Veto exception");
            }
            return validValue;
          }

          @Override
          protected String execFormatValue(Locale value) {
            String displayText = super.execFormatValue(value);
            if (m_formatValue && StringUtility.hasText(displayText)) {
              displayText = "[Formated] " + displayText;
            }
            return displayText;
          }

          public void setThrowOnLookup(boolean throwExceptionOnLookup) {
            m_throwOnLookup = throwExceptionOnLookup;
          }

          public void setThrowOnValidate(boolean throwExceptionOnValidate) {
            m_throwOnValidate = throwExceptionOnValidate;
          }

          public void setValidateValue(boolean validateValue) {
            m_validateValue = validateValue;
          }

          public void setFormatValue(boolean formatValue) {
            m_formatValue = formatValue;
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
        @ClassId("2fd9b3c9-9504-4363-9e1c-cf8a58899948")
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
            updateFieldBackgroundColor();
          }

          /**
           * Sets the color of the field to the color of the selected lookup row.
           */
          protected void updateFieldBackgroundColor() {
            Color color = getValue();
            String hexColor = color == null ? null : Integer.toHexString(color.getRGB()).substring(2);
            setBackgroundColor(hexColor);
          }
        }

        @Order(50)
        @ClassId("8ce684f0-d180-467b-bc2a-e4fb7cad9a88")
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
            return CustomLocaleLookupCall.class;
          }

          @Override
          protected void execFilterLookupResult(ILookupCall<Locale> call, List<ILookupRow<Locale>> result) {
            super.execFilterLookupResult(call, result);
            String[] icons = {
                Icons.AngleLeft,
                Icons.AngleDown,
                Icons.AngleRight,
                Icons.AngleUp,
                null,
                ""
            };
            int count = 0;
            for (ILookupRow<Locale> lookupRow : result) {
              ((CustomLocaleTableRowData) lookupRow.getAdditionalTableRowData())
                  .withIconId(icons[count++ % icons.length])
                  .withHasIso3Country(StringUtility.hasText(((CustomLocaleTableRowData) lookupRow.getAdditionalTableRowData()).getCountry()));
            }
          }

          @Override
          protected ColumnDescriptor[] getConfiguredColumnDescriptors() {
            return new ColumnDescriptor[]{
                new ColumnDescriptor(null, "Text", 200),
                new ColumnDescriptor(LocaleTableRowData.country, TEXTS.get("Country"), 90),
                new ColumnDescriptor(LocaleTableRowData.language, TEXTS.get("Language"), 90),
                new ColumnDescriptor("iconId").withHeaderIconId(Icons.List).withObjectType("IconColumn"),
                new ColumnDescriptor("hasIso3Country").withHeaderIconId(Icons.World).withObjectType("BooleanColumn")
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
        @ClassId("4ee79743-6bd3-4625-8a59-e7bcd258bd0e")
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
        @ClassId("ee23f8f9-3488-4e17-b3d0-1a4996040a38")
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
        @ClassId("253bc8bb-c89e-4846-acba-4c85c6c03ab6")
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
        @ClassId("859f0a4f-0bc8-41e6-8edd-789938700009")
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
        @ClassId("a9567a16-fe8e-4403-b4d4-6406a3238a3e")
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
        @ClassId("194ccebf-8309-4cc4-866d-dcb48b82a7b5")
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
        @ClassId("759d786f-aa20-45e4-bf86-0e2dd677e266")
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
    @ClassId("cf51c415-8255-4fb7-a3e0-a9ef9b468cac")
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
      @ClassId("7c955621-6105-40b0-80c2-bae48fd6e437")
      public class ListSmartField extends AbstractSmartField<String> {

        private boolean m_throttleActive = false;

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ListSmartField");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return UserContentListLookupCall.class;
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

        @Order(1000)
        @ClassId("506fe412-e597-4789-b735-bf4388cbc85d")
        public class DetailsMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Details";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk()
                .withBody(TEXTS.get("DetailsMenuMessage", getValue(), getDisplayText()))
                .show();
          }
        }
      }

      @Order(20)
      @ClassId("8d3d3c1c-fa9e-4a6e-b9b1-5e449b23eed4")
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
      @ClassId("75584800-889e-4427-840c-060c3f6a3432")
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
      @ClassId("fe0a439e-2cb5-43ce-a1c6-8f39e4452128")
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
      @ClassId("375bffda-6c74-4f49-bef8-241574880453")
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

      @Order(60)
      @ClassId("2e17a95a-82df-485e-8468-8cc9a5aef32c")
      public class ListSearchRequiredFilterField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SearchRequired");
        }

        @Override
        protected void execChangedValue() {
          getListSmartField().setSearchRequired(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getListSmartField().isSearchRequired());
        }
      }

      @Order(70)
      @ClassId("528f9940-f1fe-458d-8286-7be8d2e5d9cd")
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
          return UserContentTreeLookupCall.class;
        }
      }

      @Order(80)
      @ClassId("3eadd220-9879-43a7-aaa8-f85bf1bf3e7c")
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
      @ClassId("ba4aa07c-6f42-440d-8713-fc461e7476f7")
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
      @ClassId("a07facd8-e76a-4c1f-bcc6-8d46a6422e54")
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
      @ClassId("451c785e-b116-403d-91bb-73c4cc7d166e")
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
      @ClassId("fc753b2a-fb53-44cf-8727-6e6805b88ccd")
      public class TreeSearchRequiredFilterField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SearchRequired");
        }

        @Override
        protected void execChangedValue() {
          getTreeSmartField().setSearchRequired(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeSmartField().isSearchRequired());
        }
      }

      @Order(130)
      @ClassId("4694bbc2-a62d-4d7f-acd3-0cb126b1d9a0")
      public class PlaceholderField extends AbstractPlaceholderField {

        @Override
        protected int getConfiguredGridH() {
          return 8;
        }
      }
    }

    @Order(30)
    @ClassId("f896974e-4889-4806-a6bc-fc27180b443f")
    public class SampleContentButton extends AbstractButton {

      private boolean m_showState;

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        if (m_showState) {
          showStateOfDefaultSmartField();
          return;
        }

        ListEntriesField listEntries = getListEntriesField();
        listEntries.setValue(TEXTS.get("ListBoxUserContent"));

        TreeEntriesField treeEntries = getTreeEntriesField();
        treeEntries.setValue(TEXTS.get("TreeUserContent"));
      }

      protected void setShowState(boolean showValue) {
        m_showState = showValue;
        setLabel(showValue ? "Show state" : TEXTS.get("SampleContent"));
      }
    }

    @Order(35)
    @ClassId("090bf901-98c2-4a22-aca0-f1353b29b982")
    public class SeleniumTestMenu extends AbstractSeleniumTestMenu {

      @Order(100)
      @ClassId("5143086a-d88d-419a-8df6-b6b91d56eb38")
      public class SmartFieldTestMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "SmartField";
        }

        @Order(10)
        @ClassId("d094b196-3f05-41f6-aefe-330b829d3702")
        public class WildcardMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("Wildcard");
          }

          @Order(10)
          @ClassId("b1a5915f-dabe-4ef7-9241-c541ff1ce6d8")
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
          @ClassId("83f6de02-6b5f-4f7b-84f5-c506b3bd78a7")
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
          @ClassId("52163265-270b-4ed5-8890-d86912632580")
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
          @ClassId("34c06929-92b1-45c4-b992-e0fdb50f7e75")
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
          @ClassId("78bc7d00-51c1-44ea-99a6-4d55ca70cb0a")
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
        @ClassId("31d66d80-d9f6-4b6a-88e0-1b4b84da5ead")
        public class ShowDefaultSmartFieldValueMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Show value of DefaultSmartField";
          }

          @Override
          protected void execAction() {
            showStateOfDefaultSmartField();
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
              ModelJobs.schedule(() -> getDefaultSmartField().requestInput(), ModelJobs.newInput(ClientRunContexts.copyCurrent()));
            }, Jobs.newInput().withRunContext(RunContext.CURRENT.get().copy()));
          }
        }

        @Order(40)
        @ClassId("09b26af9-3c83-44c5-80f5-b0131823635e")
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
        @ClassId("f9712513-971a-4631-941f-0661eba73590")
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
        @ClassId("889e1f3e-8b19-44f0-b93c-b081e4c659a6")
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
            getDefaultSmartField().setThrowOnLookup(isActive());
          }
        }

        @Order(70)
        @ClassId("78ebdb8d-7f86-4bcb-a5fd-65f72087f39f")
        public class ToggleExceptionOnValidateMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Throw VetoException on execValidateValue";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "DefaultSmartField throws a VetoException when execValidateValue is called";
          }

          @Override
          protected void execAlways() {
            getDefaultSmartField().setThrowOnValidate(isActive());
          }
        }

        @Order(90)
        @ClassId("d923e030-ccb6-4a42-a12d-eebc9f04398e")
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
            return "Sample button shows state";
          }

          @Override
          protected void execAlways() {
            getSampleContentButton().setShowState(isActive());
          }
        }

        @Order(130)
        @ClassId("08427559-411f-4d9a-b5ae-b8848b250716")
        public class ToggleFormatValueMenu extends AbstractToggleMenu {

          @Override
          protected String getConfiguredText() {
            return "Format value in execFormatValue";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Adjust display text with execFormatValue";
          }

          @Override
          protected void execAlways() {
            getDefaultSmartField().setFormatValue(isActive());
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
        @ClassId("391fa4f5-903f-4884-8294-faf1fd8c0ea2")
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
        @ClassId("800f443a-1a67-4a0f-9263-c038c6f59ad0")
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
      @ClassId("62c03c53-e71a-405a-8372-00447a326765")
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
              ToggleExceptionOnValidateMenu.class,
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
    @ClassId("c5ef9a73-39a0-4832-a548-d4bd41665fe8")
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
