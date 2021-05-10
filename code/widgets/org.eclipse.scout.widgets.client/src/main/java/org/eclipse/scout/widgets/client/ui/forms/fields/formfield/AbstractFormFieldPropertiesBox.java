/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.formfield;

import java.util.List;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractProposalField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IMultiStatus;
import org.eclipse.scout.rt.platform.status.MultiStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.DisabledStyleLookupCall;
import org.eclipse.scout.widgets.client.ui.template.formfield.LabelPositionLookupCall;
import org.eclipse.scout.widgets.client.ui.template.formfield.LabelWidthInPixelLookupCall;
import org.eclipse.scout.widgets.client.ui.template.formfield.StatusSeverityLookupCall;

@ClassId("6dc4d5e6-c972-4d81-a218-a5f167363259")
public abstract class AbstractFormFieldPropertiesBox extends AbstractPropertiesBox<AbstractFormField> {

  @Override
  protected String getConfiguredLabel() {
    return "Form Field Properties";
  }

  @Override
  protected TriState getConfiguredResponsive() {
    return TriState.FALSE;
  }

  @Order(1000)
  @ClassId("c136fb40-c06e-4c76-8020-68e5d21b5f90")
  public class EnabledField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Enabled";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setEnabled(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isEnabled());
    }
  }

  @Order(2000)
  @ClassId("0341856c-de3a-4b18-b495-8fe667d3fc46")
  public class VisibleField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Visible";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setVisible(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isVisible());
    }
  }

  @Order(3000)
  @ClassId("90559bc5-6112-44eb-8f4d-f1ffe3480a7d")
  public class LabelVisibleField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Label Visible";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setLabelVisible(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isLabelVisible());
    }
  }

  @Order(3500)
  @ClassId("98aab58d-6019-45ad-bf29-722f732b9525")
  public class LabelUseUiWidthField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Label use UI width";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setLabelUseUiWidth(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isLabelUseUiWidth());
    }
  }

  @Order(4000)
  @ClassId("cc327ed7-cc2f-4ca7-8775-8a2b4408dcb3")
  public class StatusVisibleField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Status Visible";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setStatusVisible(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isStatusVisible());
    }
  }

  @Order(5000)
  @ClassId("d91da096-33f4-4ccd-bfb1-74b1100690ee")
  public class MandatoryField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Mandatory";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setMandatory(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isMandatory());
    }
  }

  @Order(5500)
  @ClassId("fb06a141-d26d-4913-a9a5-0226cfad7fc2")
  public class LoadingField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Loading";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setLoading(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isLoading());
    }
  }

  @Order(6000)
  @ClassId("cf9716c3-7d52-44f4-96e6-f80791b67e46")
  public class DisabledStyleField extends AbstractSmartField<Integer> {

    @Override
    protected String getConfiguredLabel() {
      return "Disabled Style";
    }

    @Override
    protected String getConfiguredDisplayStyle() {
      return DISPLAY_STYLE_DROPDOWN;
    }

    @Override
    protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
      return DisabledStyleLookupCall.class;
    }

    @Override
    protected void execChangedValue() {
      m_field.setDisabledStyle(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getDisabledStyle());
    }
  }

  @Order(7000)
  @ClassId("20bc9efe-b332-4a82-9965-2f0fb1a7ad11")
  public class LabelField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return "Label";
    }

    @Override
    protected void execChangedValue() {
      m_field.setLabel(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getLabel());
    }
  }

  @Order(8000)
  @ClassId("2fbba681-751c-4094-a440-eb35d731c619")
  public class LabelPositionField extends AbstractSmartField<Byte> {

    @Override
    protected String getConfiguredLabel() {
      return "Label Position";
    }

    @Override
    protected String getConfiguredDisplayStyle() {
      return DISPLAY_STYLE_DROPDOWN;
    }

    @Override
    protected Class<? extends ILookupCall<Byte>> getConfiguredLookupCall() {
      return LabelPositionLookupCall.class;
    }

    @Override
    protected void execChangedValue() {
      m_field.setLabelPosition(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getLabelPosition());
    }
  }

  @Order(9000)
  @ClassId("bc6fa04b-17bc-4ebf-a1df-4ddc1b99d5fe")
  public class LabelWidthInPixelField extends AbstractProposalField<String> {

    @Override
    protected String getConfiguredLabel() {
      return "Label Width in Pixel";
    }

    @Override
    protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
      return LabelWidthInPixelLookupCall.class;
    }

    @Override
    protected void execChangedValue() {
      int width = IFormField.LABEL_WIDTH_DEFAULT;
      String value = getValue();

      if (value != null) {
        List<? extends ILookupRow<String>> rows = getLookupCall().getDataByAll();
        boolean found = false;
        for (ILookupRow<String> row : rows) {
          if (row.getText().equals(value)) {
            width = Integer.parseInt(row.getKey());
            found = true;
            break;
          }
        }
        if (!found) {
          width = Integer.parseInt(value);
        }
      }

      m_field.setLabelWidthInPixel(width);
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getLabelWidthInPixel() + "");
    }
  }

  @Order(10000)
  @ClassId("2ff7fdfc-fe32-48ec-8007-9e948e4554cc")
  public class TooltipTextField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return "Tooltip Text";
    }

    @Override
    protected void execChangedValue() {
      m_field.setTooltipText(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getTooltipText());
    }
  }

  @Order(11000)
  @ClassId("39042fa7-8106-4794-92ff-f0fa47519f6a")
  public class ErrorStatusField extends AbstractSmartField<Integer> {

    @Override
    protected String getConfiguredLabel() {
      return "Error Status";
    }

    @Override
    protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
      return StatusSeverityLookupCall.class;
    }

    @Override
    protected void execChangedValue() {
      MultiStatus errorStatus = createStatus(getValue());
      if (errorStatus == null) {
        m_field.clearErrorStatus();
      }
      else {
        m_field.setErrorStatus(errorStatus);
      }
    }

    @Override
    protected void execInitField() {
      IMultiStatus errorStatus = m_field.getErrorStatus();
      if (errorStatus != null) {
        setValue(errorStatus.getSeverity());
      }
    }
  }

  protected MultiStatus createStatus(Integer severity) {
    if (severity == null) {
      return null;
    }

    Status status = new Status(TEXTS.get("FormFieldStatusMessage", getSeverityName(severity)), severity);
    MultiStatus multiStatus = new MultiStatus();
    multiStatus.add(status);
    return multiStatus;
  }

  protected String getSeverityName(int severity) {
    switch (severity) {
      case Status.OK: {
        return "OK";
      }
      case Status.INFO: {
        return "INFO";
      }
      case Status.WARNING: {
        return "WARNING";
      }
      case Status.ERROR: {
        return "ERROR";
      }
      default:
        return "undefined";
    }
  }
}
