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
import java.util.Date;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.Holder;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.GroupBox.CalendarField;

public class CalendarFieldForm extends AbstractForm implements IPageForm {

  public CalendarFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CalendarField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public CalendarField getCalendarField() {
    return getFieldByClass(CalendarField.class);
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

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(20.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class CalendarField extends AbstractCalendarField<CalendarField.Calendar> {

        @Override
        protected int getConfiguredGridH() {
          return 20;
        }

        @Override
        protected int getConfiguredGridW() {
          return 0;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class Calendar extends AbstractCalendar {
          @Order(10)
          public class VisitsOfInternalPersonsItemProducer extends AbstractCalendarItemProvider {

            @Override
            protected void execLoadItems(Date minDate, Date maxDate, Holder<ICalendarItem[]> resultHolder) throws ProcessingException {
              ArrayList<ICalendarItem> items = new ArrayList<ICalendarItem>();

              java.util.Calendar cal = java.util.Calendar.getInstance();
              Date start = cal.getTime();
              Date end = cal.getTime();
              items.add(new CalendarAppointment(0L, 0L, start, end, true, "FULL DAY", "This appointment takes the full day", "FFFF00"));
              cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
              start = cal.getTime();
              cal.add(java.util.Calendar.HOUR, 2);
              end = cal.getTime();
              items.add(new CalendarAppointment(1L, 2L, start, end, false, "app1", "appointment1 body", "44FF00"));

              cal.add(java.util.Calendar.HOUR, 1);
              start = cal.getTime();
              cal.add(java.util.Calendar.MINUTE, 30);
              end = cal.getTime();
              items.add(new CalendarAppointment(1L, 2L, start, end, false, "app2", "appointment2 body", "44FF00"));

              // future
              cal.setTime(new Date());
              cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
              start = cal.getTime();
              cal.add(java.util.Calendar.HOUR, 48);
              end = cal.getTime();
              items.add(new CalendarAppointment(1L, 2L, start, end, false, "app3", "appointment3 body", "44FF00"));
              cal.add(java.util.Calendar.HOUR, 2);
              end = cal.getTime();
              items.add(new CalendarAppointment(1L, 2L, start, end, false, "app4", "appointment4 body", "44FF00"));
              resultHolder.setValue(items.toArray(new ICalendarItem[items.size()]));

            }
          }
        }
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
