/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import static java.util.Calendar.*;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CalendarField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CalendarField.Calendar;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CloseButton;

@Order(8200)
@ClassId("0d17b2a7-99c6-4de4-b1fa-8e29a9482480")
public class CalendarFieldForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CalendarField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public CalendarField getCalendarField() {
    return getFieldByClass(CalendarField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("3c2d9d0c-047c-4cf9-9ee9-a3f751990687")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected TriState getConfiguredScrollable() {
      return TriState.FALSE;
    }

    @Override
    protected String getConfiguredCssClass() {
      return CssClasses.RIGHT_PADDING_INVISIBLE;
    }

    @Order(10)
    @ClassId("63311e33-b5d9-422c-915f-9aa7cd44a4f0")
    public class CalendarField extends AbstractCalendarField<Calendar> {

      @Override
      protected int getConfiguredGridW() {
        return 0;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredStatusVisible() {
        return false;
      }

      @Order(10)
      @ClassId("cec3a7ca-ea72-4402-8676-9cde7a119312")
      public class Calendar extends AbstractCalendar {

        @Override
        protected void execInitCalendar() {
          setMenuInjectionTarget(getMainBox());
        }

        /**
         * Provider for business calendar items, simulates a delay.
         */
        @Order(10)
        public class BusinessItemProvider extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItemsInBackground(IClientSession session, Date minDate, Date maxDate, Set<ICalendarItem> result) {
            SleepUtil.sleepSafe(5, TimeUnit.SECONDS); // simulate delay (DB-read or call external interface)

            String cssClass = "calendar-appointment";
            java.util.Calendar cal = getInstance();
            DateUtility.truncCalendarToHour(cal);
            Date start = cal.getTime();
            Date end = cal.getTime();
            result.add(new CalendarAppointment(0L, 0L, start, end, true, "Office Darmstadt", "Scout Course", "Learn Scout basics. Full day appointment for a single day", cssClass));

            cal.add(DAY_OF_YEAR, -1);
            start = cal.getTime();
            cal.set(HOUR_OF_DAY, 14);
            end = cal.getTime();
            result.add(new CalendarAppointment(1L, 2L, start, end, false, "Office Bern", "Software Architecture", "Create awesome software architecture for new Scout app", cssClass));

            // Phone call
            cal.set(HOUR_OF_DAY, 13);
            start = cal.getTime();
            cal.add(MINUTE, 30);
            end = cal.getTime();
            result.add(new CalendarAppointment(2L, 2L, start, end, false, "Office Baar", "Phone Call with Jeremy", "Inform Jeremy about news", cssClass));

            // Time blocker, spans over 2 days but it is not flagged as full-day appointment
            cal.setTime(new Date());
            DateUtility.truncCalendarToHour(cal);
            cal.set(HOUR_OF_DAY, 8);
            cal.add(DAY_OF_YEAR, 1);
            start = cal.getTime();
            cal.set(HOUR_OF_DAY, 17);
            cal.add(DAY_OF_YEAR, 1);
            end = cal.getTime();
            result.add(new CalendarAppointment(3L, 2L, start, end, false, "Office Zurich", "Time Blocker", "No other appointments", cssClass));

            // Daily meeting
            cal.add(DAY_OF_YEAR, 1);
            cal.set(HOUR_OF_DAY, 9);
            start = cal.getTime();
            cal.add(HOUR_OF_DAY, 1);
            end = cal.getTime();
            result.add(new CalendarAppointment(4L, 2L, start, end, false, null, "Daily Meeting BSI", "Daily stand-up meeting", cssClass));

            // Education, full day appointment spans over 2 days
            cal.setTime(new Date());
            DateUtility.truncCalendarToHour(cal);
            cal.add(DAY_OF_YEAR, 7);
            start = cal.getTime();
            cal.add(DAY_OF_YEAR, 1);
            end = cal.getTime();
            result.add(new CalendarAppointment(5L, 2L, start, end, true, "Auditorium", "Education", "Learn everything about Scout JS. Full day appointment, spans over 2 days", cssClass));
          }

          @Order(200)
          @ClassId("0ad6cbc8-eca2-4861-8de2-fe037a27b838")
          public class BusinessCalendarDetailMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Details";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
            }
          }
        }

        /**
         * Provider for private calendar items.
         */
        @Order(20)
        public class PrivateItemProvider extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItems(Date minDate, Date maxDate, final Set<ICalendarItem> result) {
            String cssClass = "calendar-task";
            java.util.Calendar cal = getInstance();
            DateUtility.truncCalendarToHour(cal);
            Date start, end;

            cal.set(HOUR_OF_DAY, 13);
            start = cal.getTime();
            cal.add(MINUTE, 30);
            end = cal.getTime();
            result.add(new CalendarAppointment(7L, 2L, start, end, false, "@Home", "Mechanic", "Fix broken fridge", cssClass));

            cal.add(DAY_OF_YEAR, 3);
            cal.set(HOUR_OF_DAY, 18);
            cal.set(MINUTE, 0);
            start = cal.getTime();
            cal.add(HOUR_OF_DAY, 3);
            end = cal.getTime();
            result.add(new CalendarAppointment(8L, 2L, start, end, false, "Lucky Dumpling, Zurich", "Dinner with Lisa", "Dinner with Lisa", cssClass));
          }

          @Order(200)
          @ClassId("324606df-cef5-4a06-b1c2-f87eedc32963")
          public class PrivateCalendarCreateMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Create to-do";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
            }
          }
        }

        @Order(200)
        @ClassId("dce702fa-492e-4d6b-aba7-7a409fef4261")
        public class CalendarEmptySpaceMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "New appointment";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
          }
        }
      }
    }

    @Order(30)
    @ClassId("bf306b56-a4ab-4173-8b28-8d877d695bb4")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
