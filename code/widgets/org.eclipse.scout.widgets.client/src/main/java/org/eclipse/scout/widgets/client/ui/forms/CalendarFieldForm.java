/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import static java.util.Calendar.*;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.IClientSession;
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
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.CssClasses;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarDescriptor;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarItemDescriptionElement;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarDescriptor;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CalendarField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CalendarField.Calendar;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.calendar.ICalendarService;

@Order(8200)
@ClassId("0d17b2a7-99c6-4de4-b1fa-8e29a9482480")
public class CalendarFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final List<String> CALENDAR_COLORS = new ArrayList<>(List.of(
      "calendar-color-orange",
      "calendar-color-green",
      "calendar-color-blue",
      "calendar-color-red"));

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

      private List<ICalendarAppointment> m_configuredAppointments = new ArrayList<>();

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

        @Override
        protected boolean getConfiguredRangeSelectionAllowed() {
          return true;
        }

        @Override
        protected List<ICalendarDescriptor> getConfiguredCalendars() {
          return BEANS.get(ICalendarService.class).getAllCalendars();
        }

        /**
         * Provider for business calendar items, simulates a delay.
         */
        @Order(10)
        public class BusinessItemProvider extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItemsInBackground(IClientSession session, Date minDate, Date maxDate, Set<ICalendarItem> result) {
            SleepUtil.sleepSafe(5, TimeUnit.SECONDS); // simulate delay (DB-read or call external interface)

            String cssClass = "calendar-appointment-orange";
            java.util.Calendar cal = getInstance();
            cal.add(DAY_OF_YEAR, -2);
            DateUtility.truncCalendarToHour(cal);
            Date start = cal.getTime();
            Date end = cal.getTime();
            CalendarAppointment calendarAppointment = new CalendarAppointment(0L, 0L, start, end, true, null, "Scout Course", null, cssClass);
            calendarAppointment.setSubjectLabel("Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getBusinessCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Office Darmstadt").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Learn Scout basics. Full day appointment for a single day").withIconId(Icons.List));
            result.add(calendarAppointment);

            cal.add(DAY_OF_YEAR, -1);
            start = cal.getTime();
            cal.set(HOUR_OF_DAY, 14);
            end = cal.getTime();
            calendarAppointment = new CalendarAppointment(1L, 2L, start, end, false, null, "Software Architecture", null, cssClass);
            calendarAppointment.setSubjectLabel("Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getBusinessCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Office Bern").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Create awesome software architecture for new Scout app").withIconId(Icons.List));
            result.add(calendarAppointment);

            // Phone call
            cal.set(HOUR_OF_DAY, 13);
            start = cal.getTime();
            cal.add(MINUTE, 30);
            end = cal.getTime();
            calendarAppointment = new CalendarAppointment(2L, 2L, start, end, false, null, "Phone Call with Jeremy", null, cssClass);
            calendarAppointment.setSubjectLabel("Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getBusinessCalendar().getCalendarId());
            calendarAppointment.setSubjectIconId(Icons.Phone);
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Office Baar").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Inform Jeremy about news").withIconId(Icons.List));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Jeremy Stone").withIconId(Icons.PersonSolid));
            result.add(calendarAppointment);

            // Time blocker, spans over 2 days but it is not flagged as full-day appointment
            cal.setTime(new Date());
            DateUtility.truncCalendarToHour(cal);
            cal.set(HOUR_OF_DAY, 8);
            cal.add(DAY_OF_YEAR, 1);
            start = cal.getTime();
            cal.set(HOUR_OF_DAY, 17);
            cal.add(DAY_OF_YEAR, 1);
            end = cal.getTime();
            calendarAppointment = new CalendarAppointment(3L, 2L, start, end, false, null, "Time Blocker", null, cssClass);
            calendarAppointment.setSubjectLabel("Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getBusinessCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Office Zurich").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("No other appointments").withIconId(Icons.List));
            result.add(calendarAppointment);

            // Daily meeting
            cal.add(DAY_OF_YEAR, 1);
            cal.set(HOUR_OF_DAY, 9);
            start = cal.getTime();
            cal.add(HOUR_OF_DAY, 1);
            end = cal.getTime();
            calendarAppointment = new CalendarAppointment(4L, 2L, start, end, false, null, "Daily Meeting BSI", null, cssClass);
            calendarAppointment.setSubjectLabel("Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getBusinessCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Daily stand-up meeting").withIconId(Icons.List));
            result.add(calendarAppointment);

            // Education, full day appointment spans over 2 days
            cal.setTime(new Date());
            DateUtility.truncCalendarToHour(cal);
            cal.add(DAY_OF_YEAR, 7);
            start = cal.getTime();
            cal.add(DAY_OF_YEAR, 1);
            end = cal.getTime();
            calendarAppointment = new CalendarAppointment(5L, 2L, start, end, true, null, "Education", null, cssClass);
            calendarAppointment.setSubjectLabel("Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getBusinessCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Auditorium").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Learn everything about Scout JS. Full day appointment, spans over 2 days").withIconId(Icons.List));
            result.add(calendarAppointment);
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
            CalendarAppointment calendarAppointment = new CalendarAppointment(7L, 2L, start, end, false, null, "Mechanic", null, cssClass);
            calendarAppointment.setSubjectLabel("Private Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getPrivateCalendar().getCalendarId());
            calendarAppointment.setSubjectIconId(Icons.ExclamationMarkCircle);
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("@Home").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Fix broken fridge").withIconId(Icons.List));
            result.add(calendarAppointment);

            cal.add(DAY_OF_YEAR, 3);
            cal.set(HOUR_OF_DAY, 18);
            cal.set(MINUTE, 0);
            start = cal.getTime();
            cal.add(HOUR_OF_DAY, 3);
            end = cal.getTime();
            calendarAppointment = new CalendarAppointment(8L, 2L, start, end, false, null, "Dinner with Lisa", null, cssClass);
            calendarAppointment.setSubjectLabel("Private Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getPrivateCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Lucky Dumpling, Zurich").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Lisa Turner").withIconId(Icons.PersonSolid));
            result.add(calendarAppointment);

            result.addAll(m_configuredAppointments);
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

        public class ExternalItemProvider extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItems(Date minDate, Date maxDate, Set<ICalendarItem> result) {
            String cssClass = "calendar-task";
            java.util.Calendar cal = getInstance();
            DateUtility.truncCalendarToHour(cal);
            Date start, end;

            cal.set(HOUR_OF_DAY, 16);
            start = cal.getTime();
            cal.add(HOUR_OF_DAY, 1);
            end = cal.getTime();
            CalendarAppointment calendarAppointment = new CalendarAppointment(7L, 2L, start, end, false, null, "Hairdresser", null, cssClass);
            calendarAppointment.setSubjectLabel("Out of office");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getJohnDoeCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("THE LINE, Zurich").withIconId(Icons.World));
            result.add(calendarAppointment);

            cal.add(DAY_OF_YEAR, 3);
            cal.set(HOUR_OF_DAY, 18);
            cal.set(MINUTE, 0);
            start = cal.getTime();
            cal.add(HOUR_OF_DAY, 3);
            end = cal.getTime();
            String userName = CollectionUtility.firstElement(Subject.getSubject(AccessController.getContext()).getPrincipals()).getName();
            calendarAppointment = new CalendarAppointment(8L, 2L, start, end, false, null, "Dinner with " + userName, null, cssClass);
            calendarAppointment.setSubjectLabel("Private Appointment");
            calendarAppointment.setCalendarId(BEANS.get(ICalendarService.class).getLisaTurnerCalendar().getCalendarId());
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText("Lucky Dumpling, Zurich").withIconId(Icons.World));
            calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText(userName).withIconId(Icons.PersonSolid));
            result.add(calendarAppointment);

            result.addAll(m_configuredAppointments);
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

          @Override
          protected void execAction() {
            CalendarComponentForm form = new CalendarComponentForm();
            form.start();
            form.setCalendars(getCalendarDescriptors());
            if (getSelectedRange() != null) {
              form.getStartDateField().setValue(getSelectedRange().getFrom());
              form.getEndDateField().setValue(getSelectedRange().getTo());
            }
            if (getSelectedCalendarDescriptor() != null) {
              form.getCalendarField().setValue(getSelectedCalendarDescriptor());
            }
            form.waitFor();
            if (form.isFormStored()) {
              Date start = form.getStartDateField().getValue();
              Date end = form.getEndDateField().getValue();
              boolean fullDay = form.getFullDayField().getValue();
              ICalendarAppointment calendarAppointment = new CalendarAppointment(0L, 0L, start, end, fullDay, null, form.getSubjectField().getValue(), null, null);
              calendarAppointment.setSubjectLabel("Appointment");
              if (form.getLocationField().getValue() != null) {
                calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText(form.getLocationField().getValue()).withIconId(Icons.World));
              }
              if (form.getDescriptionField().getValue() != null) {
                calendarAppointment.getDescriptionElements().add(BEANS.get(CalendarItemDescriptionElement.class).withText(form.getDescriptionField().getValue()).withIconId(Icons.List));
              }
              if (form.getCalendarField().getValue() != null) {
                calendarAppointment.setCalendarId(form.getCalendarField().getValue().getCalendarId());
              }
              m_configuredAppointments.add(calendarAppointment);
              getCalendar().reloadCalendarItems();
              getCalendar().setSelectedRange(null);
            }
          }
        }

        @Order(2000)
        @ClassId("0918cbdd-c96d-4f4e-b280-1490ec28ad68")
        public class AddCalendarMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Add calendar";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
          }

          @Override
          protected void execAction() {
            List<ICalendarDescriptor> calendars = new ArrayList<>(getCalendarDescriptors());
            Collections.shuffle(CALENDAR_COLORS);
            calendars.add(new CalendarDescriptor()
                .withName("Dyn #" + calendars.size())
                .withCssClass(CALENDAR_COLORS.get(0)));
            setCalendarDescriptors(calendars);
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
