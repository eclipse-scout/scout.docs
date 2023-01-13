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

import org.eclipse.scout.rt.client.ui.IDisplayParent;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.CalendarComponentForm.MainBox.DescriptionField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarComponentForm.MainBox.EndDateField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarComponentForm.MainBox.LocationField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarComponentForm.MainBox.StartDateField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarComponentForm.MainBox.SubjectField;

@ClassId("09172d29-c400-4483-a94f-8e6c1a45f07e")
public class CalendarComponentForm extends AbstractForm {

  public CalendarComponentForm() {
  }

  @Override
  protected String getConfiguredTitle() {
    return "Appointment";
  }

  @Override
  protected IDisplayParent getConfiguredDisplayParent() {
    return getDesktop();
  }

  @Override
  protected int getConfiguredModalityHint() {
    return MODALITY_HINT_MODELESS;
  }

  public SubjectField getSubjectField() {
    return getFieldByClass(SubjectField.class);
  }

  public StartDateField getStartDateField() {
    return getFieldByClass(StartDateField.class);
  }

  public EndDateField getEndDateField() {
    return getFieldByClass(EndDateField.class);
  }

  public LocationField getLocationField() {
    return getFieldByClass(LocationField.class);
  }

  public DescriptionField getDescriptionField() {
    return getFieldByClass(DescriptionField.class);
  }

  @Order(1000)
  @ClassId("7a8b3206-3192-466b-9418-6c795625ceb4")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected String getConfiguredBorderDecoration() {
      return BORDER_DECORATION_EMPTY;
    }

    @Order(10)
    @ClassId("8253cdb4-7602-49c3-9eec-eb6b80ccc23e")
    public class SubjectField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return "Subject";
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Override
      protected int getConfiguredGridW() {
        return 2;
      }
    }

    @Order(1000)
    @ClassId("0b5afee6-857b-4ef4-b132-aff5442244f5")
    public class StartDateField extends AbstractDateTimeField {
      @Override
      protected String getConfiguredLabel() {
        return "Start";
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }
    }

    @Order(2000)
    @ClassId("c2d9d97f-f4d7-4cb5-a0aa-27994cb750c2")
    public class EndDateField extends AbstractDateTimeField {
      @Override
      protected String getConfiguredLabel() {
        return "End";
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }
    }

    @Order(3000)
    @ClassId("05e4cb65-46ca-4582-8d31-2fe447f1bc0f")
    public class LocationField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return "Location";
      }
    }

    @Order(4000)
    @ClassId("2e002996-3003-4744-ae36-1405ae7b5748")
    public class DescriptionField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return "Description";
      }

      @Override
      protected boolean getConfiguredMultilineText() {
        return true;
      }

      @Override
      protected int getConfiguredGridH() {
        return 3;
      }
    }

    @Order(9000)
    @ClassId("0b382dfc-042d-4992-bd4c-b32eabf1ee0d")
    public class OkButton extends AbstractOkButton {
    }

    @Order(10000)
    @ClassId("7090917f-b3c9-4470-99f5-fd21ab0b4d23")
    public class CloseButton extends AbstractCloseButton {
    }
  }
}
