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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.keystroke.KeyStroke;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.ISplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox.FromField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox.PreviewField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.ModifiedField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.NameField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.SizeField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox.FileTableField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterPositionBox.MinSplitterPositionField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterVisibilityBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterVisibilityBox.CollapsibleFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterVisibilityBox.VerticalFieldConfigurationSequenceBox.MinimizeEnabledVerticalField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField.Table.DateModifiedColumn;

@ClassId("8f4e38b1-008b-411c-8d9e-da82bb8117b1")
public class SplitBoxForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SplitBox");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public DetailsBox getBottomBox() {
    return getFieldByClass(DetailsBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  public SplitterVisibilityBox getSplitterVisibilityBox() {
    return getFieldByClass(SplitterVisibilityBox.class);
  }

  public FileTableField getFileTableField() {
    return getFieldByClass(FileTableField.class);
  }

  public FilesBox getFilesBox() {
    return getFieldByClass(FilesBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ModifiedField getModifiedField() {
    return getFieldByClass(ModifiedField.class);
  }

  public MinSplitterPositionField getMinSplitterPositionField() {
    return getFieldByClass(MinSplitterPositionField.class);
  }

  public CollapsibleFieldBox getCollapsibleFieldBox() {
    return getFieldByClass(CollapsibleFieldBox.class);
  }

  public MinimizeEnabledVerticalField getMinimizeEnabledVerticalField() {
    return getFieldByClass(MinimizeEnabledVerticalField.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public PreviewField getPreviewField() {
    return getFieldByClass(PreviewField.class);
  }

  public PreviewBox getPreviewBox() {
    return getFieldByClass(PreviewBox.class);
  }

  public SizeField getSizeField() {
    return getFieldByClass(SizeField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public SplitHorizontalField getSplitHorizontalField() {
    return getFieldByClass(SplitHorizontalField.class);
  }

  public SplitVerticalField getSplitVerticalField() {
    return getFieldByClass(SplitVerticalField.class);
  }

  public FromField getfromField() {
    return getFieldByClass(FromField.class);
  }

  @Order(10)
  @ClassId("275f4151-0242-44e2-b9d5-737dabbe670c")
  public class MainBox extends AbstractGroupBox {

    @Order(20)
    @ClassId("a5a51b8e-11c1-4d38-9020-2ec46f40a4cf")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("3adffdc7-7c5e-4564-b920-7b6eb0a9cac6")
      public class SplitVerticalField extends AbstractSplitBox {

        @Override
        protected Class<? extends IFormField> getConfiguredCollapsibleField() {
          return PreviewBox.class;
        }

        @Override
        protected String getConfiguredSplitterPositionType() {
          return SPLITTER_POSITION_TYPE_RELATIVE_SECOND;
        }

        @Override
        protected String getConfiguredFirstCollapseKeyStroke() {
          return KeyStroke.combineKeyStrokes(IKeyStroke.CONTROL, IKeyStroke.SHIFT, IKeyStroke.COMMA);
        }

        @Override
        protected String getConfiguredSecondCollapseKeyStroke() {
          return KeyStroke.combineKeyStrokes(IKeyStroke.CONTROL, IKeyStroke.SHIFT, IKeyStroke.POINT);
        }

        @Order(10)
        @ClassId("29d27b36-6528-4b5b-8acf-22342a2f5e7d")
        public class SplitHorizontalField extends AbstractSplitBox {

          @Override
          protected boolean getConfiguredSplitHorizontal() {
            return false;
          }

          @Override
          protected double getConfiguredSplitterPosition() {
            return 0.6;
          }

          @Order(10)
          @ClassId("42a7305c-8dc8-4ee4-a097-e9a01360105d")
          public class FilesBox extends AbstractGroupBox {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Files");
            }

            @Order(10)
            @ClassId("ac14864e-ac4f-4cc3-bbf4-355bc80e4c3c")
            public class FileTableField extends AbstractFileTableField {

              @Override
              protected int getConfiguredGridH() {
                return 5;
              }

              @Override
              protected boolean getConfiguredLabelVisible() {
                return false;
              }

              @Override
              protected void execInitField() {
                super.execInitField();

                for (IColumn c : getTable().getColumns()) {
                  if (c instanceof DateModifiedColumn) {
                    c.setVisible(false);
                  }
                }
              }

              @Override
              protected void execResourceRowClick(BinaryResource resource) {
                reloadPreview(resource);
                reloadDetails(resource);
              }

              private void reloadPreview(BinaryResource resource) {
                if (isImage(resource)) {
                  getPreviewField().setImage(resource.getContent());
                }
                else {
                  getPreviewField().setImage(null);
                }
              }

              private boolean isImage(BinaryResource resource) {
                if (resource == null) {
                  return false;
                }
                String ext = IOUtility.getFileExtension(resource.getFilename()).toLowerCase();
                return ObjectUtility.isOneOf(ext, "jpg", "jpeg", "png");
              }

              private void reloadDetails(BinaryResource resource) {
                if (resource == null) {
                  getNameField().resetValue();
                  getSizeField().resetValue();
                  getModifiedField().resetValue();
                }
                else {
                  getNameField().setValue(resource.getFilename());
                  getSizeField().setValue(resource.getContent() == null ? 0L : resource.getContent().length);
                  getModifiedField().setValue(new Date(resource.getLastModified()));
                }
              }
            }
          }

          @Order(20)
          @ClassId("a893df2a-7892-4acc-abc3-3bfbba199360")
          public class DetailsBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
              return 1;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Details");
            }

            @Override
            protected void execInitField() {
              getNameField().setEnabled(false, true, true);
              getSizeField().setEnabled(false, true, true);
              getModifiedField().setEnabled(false, true, true);
            }

            @Order(10)
            @ClassId("cbcd6947-12d3-4c8f-9c99-cc05c7fdca14")
            public class NameField extends AbstractStringField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Name");
              }
            }

            @Order(20)
            @ClassId("aa694f1c-93b4-454b-92bb-89b39853237a")
            public class SizeField extends AbstractLongField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("SizeInBytes");
              }
            }

            @Order(40)
            @ClassId("4a6e11fd-664b-4d80-9989-5a5328de7a87")
            public class ModifiedField extends AbstractDateTimeField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Modified");
              }
            }
          }
        }

        @Order(30)
        @ClassId("100662f4-29a5-4522-a13f-44e92879ee1a")
        public class PreviewBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredBackgroundColor() {
            return "D6D6D6";
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredHorizontalAlignment() {
            return 0;
          }

          @Order(10)
          @ClassId("58294598-1032-47ee-a2f5-59895c913caa")
          public class PreviewField extends AbstractImageField {

            @Override
            protected boolean getConfiguredAutoFit() {
              return true;
            }

            @Override
            protected int getConfiguredGridH() {
              return 8;
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }
          }
        }
      }
    }

    @Order(40)
    @ClassId("534a7b26-fb20-424c-aa39-769a906b1d9d")
    public class SplitVisibleEnabledField extends AbstractSplitBox {

      @Order(10)
      @ClassId("ebd342b1-3db6-4f64-ba75-755d8ac3aeaa")
      public class SplitterVisibilityBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected double getConfiguredGridWeightY() {
          return 0.0;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Splitter configuration";
        }

        @Order(10)
        @ClassId("6bc729ed-eed0-469a-b6c8-66dfe49d2020")
        public class VisiblePreviewField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("VisiblePreview");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            getPreviewBox().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getPreviewBox().isVisible());
          }
        }

        @Order(20)
        @ClassId("23e49740-2167-43f2-bc44-8ca436e4260b")
        public class VisibleDetailsField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("VisibleDetails");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            getDetailsBox().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getDetailsBox().isVisible());
          }
        }

        @Order(30)
        @ClassId("7000c675-349c-43ca-b847-0acaa9607d0f")
        public class EnabledSequenceBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return "Splitter enabled";
          }

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(10)
          @ClassId("7f1091a8-0997-495c-9e0d-c458b75d1bd8")
          public class EnabledVerticalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Vertical";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setSplitterEnabled(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isSplitterEnabled());
            }
          }

          @Order(20)
          @ClassId("dbb53d71-642c-4190-98cd-fc6168a8d70b")
          public class EnabledHorizontalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Horizontal";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitHorizontalField().setSplitterEnabled(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitHorizontalField().isSplitterEnabled());
            }
          }
        }

        @Order(40)
        @ClassId("fa4063fd-ede6-4e80-8853-8371b78ee2d0")
        public class VerticalFieldConfigurationSequenceBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return "Field (V)";
          }

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(-1000)
          @ClassId("9ae83e85-603a-4d40-93f9-aab714e341a2")
          public class MinimizeEnabledVerticalField extends AbstractBooleanField {
            @Override
            protected String getConfiguredLabel() {
              return "Minimize Enabled";
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setMinimizeEnabled(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isMinimizeEnabled());

              getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_MINIMIZE_ENABLED, evt -> {
                if (!isValueChanging()) {
                  setValueChangeTriggerEnabled(false);
                  try {
                    setValue((Boolean) evt.getNewValue());
                  }
                  finally {
                    setValueChangeTriggerEnabled(true);
                  }
                }
              });
            }
          }

          @Order(10)
          @ClassId("cae2d2b7-c301-487c-94e3-4d4695542b71")
          public class MinimizedVerticalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Minimized";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setFieldMinimized(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isFieldMinimized());

              getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_FIELD_MINIMIZED, evt -> {
                if (!isValueChanging()) {
                  setValueChangeTriggerEnabled(false);
                  try {
                    setValue((Boolean) evt.getNewValue());
                  }
                  finally {
                    setValueChangeTriggerEnabled(true);
                  }
                }
              });
            }
          }

          @Order(20)
          @ClassId("85c6867f-7234-4af5-ad40-f4b2ac9723b1")
          public class CollapsedVerticalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Collapsed";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setFieldCollapsed(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isFieldCollapsed());

              getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_FIELD_COLLAPSED, evt -> {
                if (!isValueChanging()) {
                  setValueChangeTriggerEnabled(false);
                  try {
                    setValue((Boolean) evt.getNewValue());
                  }
                  finally {
                    setValueChangeTriggerEnabled(true);
                  }
                }
              });
            }
          }
        }

        @Order(50)
        @ClassId("28608587-a22c-4a7e-b7bf-38eb6e5a8566")
        public class CollapsibleFieldBox extends AbstractRadioButtonGroup<Class<? extends IFormField>> {
          @Override
          protected String getConfiguredLabel() {
            return "Collapsible field (V)";
          }

          @Override
          protected void execChangedValue() {
            if (getValue() != null) {
              getSplitVerticalField().setCollapsibleField(getForm().getFieldByClass(getValue()));
            }
            else {
              getSplitVerticalField().setCollapsibleField(null);
            }
          }

          @Override
          protected void execInitField() {
            setValue(getSplitVerticalField().getCollapsibleField().getClass());
          }

          @Order(1000)
          @ClassId("56d65119-9b5b-42ee-ab6c-0b2e854f1f43")
          public class FirstFieldButton extends AbstractRadioButton<Class<? extends IFormField>> {
            @Override
            protected Class<? extends IFormField> getConfiguredRadioValue() {
              return SplitHorizontalField.class;
            }

            @Override
            protected String getConfiguredLabel() {
              return "First";
            }
          }

          @Order(2000)
          @ClassId("c1ea9514-2730-40f1-88d6-23714baf05b0")
          public class SecondFieldButton extends AbstractRadioButton<Class<? extends IFormField>> {
            @Override
            protected Class<? extends IFormField> getConfiguredRadioValue() {
              return PreviewBox.class;
            }

            @Override
            protected String getConfiguredLabel() {
              return "Second";
            }
          }

          @Order(3000)
          @ClassId("5548008f-4c9a-49c3-a47e-61f6885813a8")
          public class NoneFieldButton extends AbstractRadioButton<Class> {
            @Override
            protected Class getConfiguredRadioValue() {
              return null;
            }

            @Override
            protected String getConfiguredLabel() {
              return "None";
            }
          }
        }
      }

      @Order(20)
      @ClassId("86bde43b-7e38-430b-b4f9-ca7d78c2d45b")
      public class SplitterPositionBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected double getConfiguredGridWeightY() {
          return 0.0;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Splitter position";
        }

        @Order(10)
        @ClassId("9bd299f5-5046-4ea6-ad6e-a469df363560")
        public class SplitterPositionTypeVField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Position type (V)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position type of vertical splitter";
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return P_SplitterPositionTypeLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(getSplitVerticalField().getSplitterPositionType());
            updateSplitterPositionVFieldBounds();
          }

          @Override
          protected String execValidateValue(String rawValue) {
            if (rawValue == null) {
              return getInitValue();
            }
            return rawValue;
          }

          @Override
          protected void execChangedValue() {
            if (getValue() != null) {
              getSplitVerticalField().setSplitterPositionType(getValue());
              updateSplitterPositionVFieldBounds();
            }
          }

          protected void updateSplitterPositionVFieldBounds() {
            if (ObjectUtility.isOneOf(getValue(), ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_FIRST, ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_SECOND)) {
              getFieldByClass(SplitterPositionVField.class).setMaxValue(BigDecimal.ONE);
              getFieldByClass(MinSplitterPositionField.class).setMaxValue(BigDecimal.ONE);
            }
            else {
              getFieldByClass(SplitterPositionVField.class).setMaxValue(null);
              getFieldByClass(MinSplitterPositionField.class).setMaxValue(null);
            }
          }
        }

        @Order(20)
        @ClassId("2fee57a4-6d6d-487c-9c12-1d91bb9ede99")
        public class SplitterPositionVField extends P_AbstractSplitterPositionField {

          @Override
          protected String getConfiguredLabel() {
            return "Position (V)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position of vertical splitter";
          }

          @Override
          protected ISplitBox getSplitBox() {
            return getSplitVerticalField();
          }
        }

        @Order(25)
        @ClassId("3b873c6a-280b-4944-bfef-307455d34e4c")
        public class MinSplitterPositionField extends AbstractBigDecimalField {
          @Override
          protected String getConfiguredLabel() {
            return "Min Position (V)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Minimum splitter position of vertical splitter";
          }

          @Override
          protected BigDecimal getConfiguredMinValue() {
            return BigDecimal.ZERO;
          }

          @Override
          protected void execInitField() {
            setValue(NumberUtility.toBigDecimal(getSplitVerticalField().getMinSplitterPosition()));

            getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_MIN_SPLITTER_POSITION, evt -> {
              if (!isValueChanging()) {
                setValueChangeTriggerEnabled(false);
                try {
                  setValue(NumberUtility.toBigDecimal((Double) evt.getNewValue()));
                }
                finally {
                  setValueChangeTriggerEnabled(true);
                }
              }
            });
          }

          @Override
          protected void execChangedValue() {
            getSplitVerticalField().setMinSplitterPosition(NumberUtility.toDouble(getValue()));
          }
        }

        @Order(30)
        @ClassId("e5818664-0564-4f28-87a3-ca387b1cf927")
        public class SplitterPositionTypeHField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Position type (H)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position type of horizontal splitter";
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return P_SplitterPositionTypeLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(getSplitHorizontalField().getSplitterPositionType());
            updateSplitterPositionHFieldBounds();
          }

          @Override
          protected void execChangedValue() {
            getSplitHorizontalField().setSplitterPositionType(getValue());
            updateSplitterPositionHFieldBounds();
          }

          protected void updateSplitterPositionHFieldBounds() {
            if (ObjectUtility.isOneOf(getValue(), ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_FIRST, ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_SECOND)) {
              getFieldByClass(SplitterPositionHField.class).setMaxValue(BigDecimal.ONE);
            }
            else {
              getFieldByClass(SplitterPositionHField.class).setMaxValue(null);
            }
          }

          @Override
          protected String execValidateValue(String rawValue) {
            if (rawValue == null) {
              return getInitValue();
            }
            return rawValue;
          }
        }

        @Order(40)
        @ClassId("4992a6bf-2691-455f-931c-b5650745bc6d")
        public class SplitterPositionHField extends P_AbstractSplitterPositionField {

          @Override
          protected String getConfiguredLabel() {
            return "Position (H)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position of horizontal splitter";
          }

          @Override
          protected ISplitBox getSplitBox() {
            return getSplitHorizontalField();
          }
        }
      }
    }

    @Order(50)
    @ClassId("fab5b1bd-1bc9-4eb1-9002-126c66abc860")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ClassId("59d47bca-0192-4176-8a0e-8d7f2b513cf8")
  protected abstract class P_AbstractSplitterPositionField extends AbstractBigDecimalField {

    protected abstract ISplitBox getSplitBox();

    @Override
    protected BigDecimal getConfiguredMinValue() {
      return BigDecimal.ZERO;
    }

    @Override
    protected void execChangedValue() {
      if (getValue() != null) {
        getSplitBox().setSplitterPosition(getValue().doubleValue());
      }
    }

    @Override
    protected void execInitField() {
      // set initial value
      setValueWithoutValueChangeTriggers(getSplitBox().getSplitterPosition());
      // add listener
      getSplitBox().addPropertyChangeListener(ISplitBox.PROP_SPLITTER_POSITION, evt -> {
        if (!P_AbstractSplitterPositionField.this.isValueChanging()) {
          setValueWithoutValueChangeTriggers((double) evt.getNewValue());
        }
      });
    }

    protected void setValueWithoutValueChangeTriggers(double value) {
      setValueChangeTriggerEnabled(false);
      try {
        setValue(BigDecimal.valueOf(value));
      }
      finally {
        setValueChangeTriggerEnabled(true);
      }
    }
  }

  @ClassId("fbbd7dfe-18a4-40fb-ab3b-cca07777c92b")
  public static class P_SplitterPositionTypeLookupCall extends LocalLookupCall<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      ArrayList<LookupRow<String>> rows = new ArrayList<>();
      rows.add(new LookupRow<>(ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_FIRST, "Relative (first field)"));
      rows.add(new LookupRow<>(ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_SECOND, "Relative (second field)"));
      rows.add(new LookupRow<>(ISplitBox.SPLITTER_POSITION_TYPE_ABSOLUTE_FIRST, "Absolute (first field)"));
      rows.add(new LookupRow<>(ISplitBox.SPLITTER_POSITION_TYPE_ABSOLUTE_SECOND, "Absolute (second field)"));
      return rows;
    }
  }
}
