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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.ProcessingStatus;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.ISequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.RandomValueBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.RandomValueBox.fromField;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.RandomValueBox.toField;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox.OkButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox.SomeFieldsHaventAValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox.WhichNumberWillTheComputerFindField;

public class SequenceBoxForm extends AbstractForm implements IPageForm {

  public SequenceBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SequenceBox");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public RandomValueBox getRandomValueBox() {
    return getFieldByClass(RandomValueBox.class);
  }

  public SequenceBox getSequenceBox() {
    return getFieldByClass(SequenceBox.class);
  }

  public SomeFieldsHaventAValueField getSomeFieldsHaventAValueField() {
    return getFieldByClass(SomeFieldsHaventAValueField.class);
  }

  public WhichNumberWillTheComputerFindField getWhichNumberWillTheComputerFindField() {
    return getFieldByClass(WhichNumberWillTheComputerFindField.class);
  }

  public fromField getfromField() {
    return getFieldByClass(fromField.class);
  }

  public toField gettoField() {
    return getFieldByClass(toField.class);
  }

  private <V> V defaultIfNull(V value, V defaultValue) {
    return value != null ? value : defaultValue;
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class RandomValueBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RandomValue");
        }

        @Order(10.0)
        public class fromField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }
        }

        @Order(20.0)
        public class toField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(20.0)
      public class SequenceBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Order(10.0)
        public class WhichNumberWillTheComputerFindField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("WhichNumberWillTheComputerFind");
          }
        }

        @Order(20.0)
        public class OkButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OkButton");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            if (getfromField().getValue() == null || gettoField().getValue() == null || getWhichNumberWillTheComputerFindField().getValue() == null) {
              getSomeFieldsHaventAValueField().setVisible(true);
            }
            else {
              getSomeFieldsHaventAValueField().setVisible(false);
              int value = new Random().nextInt(gettoField().getValue() - getfromField().getValue() + 1) + getfromField().getValue();
              if (value == getWhichNumberWillTheComputerFindField().getValue()) {
                MessageBox.showOkMessage("Correct", "Correct\nThe number is " + value, null);
              }
              else {
                MessageBox.showOkMessage("Wrong", "Wrong\nThe number is " + value, null);
              }
            }
          }
        }

        @Order(30.0)
        public class SomeFieldsHaventAValueField extends AbstractLabelField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SomeFieldsHaventAValue");
          }

          @Override
          protected boolean getConfiguredVisible() {
            return false;
          }
        }
      }

      @Order(30.0)
      public class CloseButton extends AbstractCloseButton {
      }

      @Order(30.0)
      public class Widget1GroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return "SequenceBox with default widget label position";
        }

        @Order(10.0)
        public class WidgetControlSequenceBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Override
          protected String getConfiguredLabel() {
            return "Change a widget";
          }

          @Override
          protected String execCreateLabelSuffix() {
            return null;
          }

          @Order(10.0)
          public class WidgetSmartField extends AbstractSmartField<IFormField> {

            @Override
            protected String getConfiguredLabel() {
              return "Widget";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }

            @Override
            protected void initConfig() {
              super.initConfig();
              setLookupCall(new WidgetLookupCall(Widget1GroupBox.WidgetSequenceBox.class));
            }
          }

          @Order(20.0)
          public class VisibleSmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Visible";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(true);
            }
          }

          @Order(30.0)
          public class MandatorySmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Mandatory";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(false);
            }
          }

          @Order(40.0)
          public class ErrorStatusSmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Error Status";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(false);
            }
          }

          @Order(50.0)
          public class ApplyButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Apply";
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return Widget1GroupBox.WidgetControlSequenceBox.WidgetSmartField.class;
            }

            @Override
            protected boolean getConfiguredMasterRequired() {
              return true;
            }

            @Override
            protected void execClickAction() throws ProcessingException {
              boolean visible = defaultIfNull(getFieldByClass(Widget1GroupBox.WidgetControlSequenceBox.VisibleSmartField.class).getValue(), false);
              boolean mandatory = defaultIfNull(getFieldByClass(Widget1GroupBox.WidgetControlSequenceBox.MandatorySmartField.class).getValue(), false);
              boolean errorStatus = defaultIfNull(getFieldByClass(Widget1GroupBox.WidgetControlSequenceBox.ErrorStatusSmartField.class).getValue(), false);

              IFormField field = getFieldByClass(Widget1GroupBox.WidgetControlSequenceBox.WidgetSmartField.class).getValue();
              field.setVisible(visible);
              field.setMandatory(mandatory);
              field.setErrorStatus(errorStatus ? new ProcessingStatus("error", IProcessingStatus.ERROR) : null);
            }
          }
        }

        @Order(20.0)
        public class WidgetSequenceBox extends AbstractSequenceBox {

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Order(10.0)
          public class WidgetStringField1 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF1";
            }
          }

          @Order(20.0)
          public class WidgetStringField2 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF2";
            }
          }

          @Order(30.0)
          public class WidgetBooleanField1 extends AbstractCheckBox {

            @Override
            protected String getConfiguredLabel() {
              return "BF1";
            }
          }

          @Order(40.0)
          public class WidgetBooleanField2 extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "BF2";
            }
          }

          @Order(50.0)
          public class WidgetStringField3 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF3";
            }
          }
        }
      }

      @Order(30.0)
      public class Widget2GroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return "SequenceBox with 'on-field' widget label position";
        }

        @Order(10.0)
        public class WidgetControlSequenceBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Override
          protected String getConfiguredLabel() {
            return "Change a widget";
          }

          @Override
          protected String execCreateLabelSuffix() {
            return null;
          }

          @Order(10.0)
          public class WidgetSmartField extends AbstractSmartField<IFormField> {

            @Override
            protected String getConfiguredLabel() {
              return "Widget";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }

            @Override
            protected void initConfig() {
              super.initConfig();
              setLookupCall(new WidgetLookupCall(Widget2GroupBox.WidgetSequenceBox.class));
            }
          }

          @Order(20.0)
          public class VisibleSmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Visible";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(true);
            }
          }

          @Order(30.0)
          public class MandatorySmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Mandatory";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(false);
            }
          }

          @Order(40.0)
          public class ErrorStatusSmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Error Status";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(false);
            }
          }

          @Order(50.0)
          public class ApplyButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Apply";
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return Widget2GroupBox.WidgetControlSequenceBox.WidgetSmartField.class;
            }

            @Override
            protected boolean getConfiguredMasterRequired() {
              return true;
            }

            @Override
            protected void execClickAction() throws ProcessingException {
              boolean visible = defaultIfNull(getFieldByClass(Widget2GroupBox.WidgetControlSequenceBox.VisibleSmartField.class).getValue(), false);
              boolean mandatory = defaultIfNull(getFieldByClass(Widget2GroupBox.WidgetControlSequenceBox.MandatorySmartField.class).getValue(), false);
              boolean errorStatus = defaultIfNull(getFieldByClass(Widget2GroupBox.WidgetControlSequenceBox.ErrorStatusSmartField.class).getValue(), false);

              IFormField field = getFieldByClass(Widget2GroupBox.WidgetControlSequenceBox.WidgetSmartField.class).getValue();
              field.setVisible(visible);
              field.setMandatory(mandatory);
              field.setErrorStatus(errorStatus ? new ProcessingStatus("error", IProcessingStatus.ERROR) : null);
            }
          }
        }

        @Order(20.0)
        public class WidgetSequenceBox extends AbstractSequenceBox {

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Order(10.0)
          public class WidgetStringField1 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF1";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }
          }

          @Order(20.0)
          public class WidgetStringField2 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF2";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }
          }

          @Order(30.0)
          public class WidgetBooleanField1 extends AbstractCheckBox {

            @Override
            protected String getConfiguredLabel() {
              return "BF1";
            }
          }

          @Order(40.0)
          public class WidgetBooleanField2 extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "BF2";
            }
          }

          @Order(50.0)
          public class WidgetStringField3 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF3";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }
          }
        }
      }

      @Order(30.0)
      public class Widget3GroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return "SequenceBox with mixed widget label position";
        }

        @Order(10.0)
        public class WidgetControlSequenceBox extends AbstractSequenceBox {

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Override
          protected String getConfiguredLabel() {
            return "Change a widget";
          }

          @Override
          protected String execCreateLabelSuffix() {
            return null;
          }

          @Order(10.0)
          public class WidgetSmartField extends AbstractSmartField<IFormField> {

            @Override
            protected String getConfiguredLabel() {
              return "Widget";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }

            @Override
            protected void initConfig() {
              super.initConfig();
              setLookupCall(new WidgetLookupCall(Widget3GroupBox.WidgetSequenceBox.class));
            }
          }

          @Order(20.0)
          public class VisibleSmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Visible";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(true);
            }
          }

          @Order(30.0)
          public class MandatorySmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Mandatory";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(false);
            }
          }

          @Order(40.0)
          public class ErrorStatusSmartField extends AbstractSmartField<Boolean> {

            @Override
            protected String getConfiguredLabel() {
              return "Error Status";
            }

            @Override
            protected Class<? extends ILookupCall<Boolean>> getConfiguredLookupCall() {
              return YesNoLookupCall.class;
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(false);
            }
          }

          @Order(50.0)
          public class ApplyButton extends AbstractButton {

            @Override
            protected String getConfiguredLabel() {
              return "Apply";
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return Widget3GroupBox.WidgetControlSequenceBox.WidgetSmartField.class;
            }

            @Override
            protected boolean getConfiguredMasterRequired() {
              return true;
            }

            @Override
            protected void execClickAction() throws ProcessingException {
              boolean visible = defaultIfNull(getFieldByClass(Widget3GroupBox.WidgetControlSequenceBox.VisibleSmartField.class).getValue(), false);
              boolean mandatory = defaultIfNull(getFieldByClass(Widget3GroupBox.WidgetControlSequenceBox.MandatorySmartField.class).getValue(), false);
              boolean errorStatus = defaultIfNull(getFieldByClass(Widget3GroupBox.WidgetControlSequenceBox.ErrorStatusSmartField.class).getValue(), false);

              IFormField field = getFieldByClass(Widget3GroupBox.WidgetControlSequenceBox.WidgetSmartField.class).getValue();
              field.setVisible(visible);
              field.setMandatory(mandatory);
              field.setErrorStatus(errorStatus ? new ProcessingStatus("error", IProcessingStatus.ERROR) : null);
            }
          }
        }

        @Order(20.0)
        public class WidgetSequenceBox extends AbstractSequenceBox {

          @Override
          protected int getConfiguredGridW() {
            return FULL_WIDTH;
          }

          @Order(10.0)
          public class WidgetStringField1 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF1";
            }

            @Override
            protected int getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }
          }

          @Order(20.0)
          public class WidgetStringField2 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF2";
            }
          }

          @Order(30.0)
          public class WidgetBooleanField1 extends AbstractCheckBox {

            @Override
            protected String getConfiguredLabel() {
              return "BF1";
            }
          }

          @Order(40.0)
          public class WidgetBooleanField2 extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "BF2";
            }
          }

          @Order(50.0)
          public class WidgetStringField3 extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return "SF3";
            }
          }
        }
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  public static class YesNoLookupCall extends LocalLookupCall<Boolean> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<Boolean>> execCreateLookupRows() throws ProcessingException {
      List<ILookupRow<Boolean>> rows = new ArrayList<ILookupRow<Boolean>>();
      rows.add(new LookupRow<Boolean>(Boolean.TRUE, "Yes"));
      rows.add(new LookupRow<Boolean>(Boolean.FALSE, "No"));
      return rows;
    }
  }

  public class WidgetLookupCall extends LocalLookupCall<IFormField> {

    private static final long serialVersionUID = 1L;
    private Class<? extends ISequenceBox> m_sequenceBox;

    public WidgetLookupCall(Class<? extends ISequenceBox> sequenceBox) {
      m_sequenceBox = sequenceBox;
    }

    @Override
    protected List<? extends ILookupRow<IFormField>> execCreateLookupRows() throws ProcessingException {
      List<ILookupRow<IFormField>> rows = new ArrayList<ILookupRow<IFormField>>();

      ISequenceBox sequenceBox = getFieldByClass(m_sequenceBox);
      for (IFormField field : sequenceBox.getFields()) {
        rows.add(new LookupRow<IFormField>(field, field.getLabel()));
      }

      rows.add(new LookupRow<IFormField>(sequenceBox, sequenceBox.getLabel()));

      return rows;
    }
  }
}
