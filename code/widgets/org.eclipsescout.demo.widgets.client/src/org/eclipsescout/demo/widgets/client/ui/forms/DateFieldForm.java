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

import java.util.Date;
import java.util.TimeZone;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.TimezonesLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm.MainBox.GroupBox.DateField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm.MainBox.GroupBox.DateTimeField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm.MainBox.GroupBox.TimeField;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm.MainBox.GroupBox.TimezoneField;

public class DateFieldForm extends AbstractForm implements IPageForm {

  public DateFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("DateField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DateField getDateField() {
    return getFieldByClass(DateField.class);
  }

  public DateTimeField getDateTimeField() {
    return getFieldByClass(DateTimeField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public TimeField getTimeField() {
    return getFieldByClass(TimeField.class);
  }

  public TimezoneField getTimezoneField() {
    return getFieldByClass(TimezoneField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(50.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class TimezoneField extends AbstractSmartField<TimeZone> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Timezone");
        }

        @Override
        protected Class<? extends LookupCall> getConfiguredLookupCall() {
          return TimezonesLookupCall.class;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          if (getValue() != null) {
            int offset = getValue().getRawOffset();
            offset = offset - TimeZone.getDefault().getRawOffset();
            for (IFormField f : getAllFields()) {
              if (f instanceof AbstractDateField) {
                ((AbstractDateField) f).setValue(new Date(System.currentTimeMillis() + offset));
              }
            }
          }
          else {
            for (IFormField f : getAllFields()) {
              if (f instanceof AbstractDateField) {
                ((AbstractDateField) f).setValue(new Date());
              }
            }
          }
        }
      }

      @Order(20.0)
      public class DateField extends AbstractDateField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateField");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(new Date());
        }
      }

      @Order(30.0)
      public class DateTimeField extends AbstractDateTimeField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateTimeField");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(new Date());
        }
      }

      @Order(40.0)
      public class TimeField extends AbstractTimeField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TimeField");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(new Date());
        }
      }
    }

    @Order(60.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
